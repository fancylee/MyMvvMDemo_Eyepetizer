package com.example.library_base.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.library_base.loadsir.EmptyCallback;
import com.example.library_base.loadsir.ErrorCallback;
import com.example.library_base.loadsir.LoadingCallback;
import com.example.library_base.utils.ToastUtil;
import com.example.library_base.viewmodel.MvmBaseViewModel;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

public  abstract class MvvmBaseActivity<V extends ViewDataBinding,VM extends MvmBaseViewModel> extends AppCompatActivity implements IBaseView {

    protected VM viewModel;

    protected V viewDataBinding;

    protected LoadService mLoadService;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
        performDataBinding();
    }

    private void initViewModel()
    {
        viewModel = getViewModel();
        if (null != viewModel)
        {
            viewModel.attachUi(this);
        }
    }
    private void performDataBinding()
    {
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        this.viewModel = viewModel == null ? getViewModel() : viewModel;
        if (getBindingVariable() > 0)
        {
            viewDataBinding.setVariable(getBindingVariable(), viewModel);
        }
        viewDataBinding.executePendingBindings();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != viewModel && viewModel.isUiAttached())
        {
            viewModel.detachUi();
        }
    }

    /**
     * 注册LoadSir
     *
     * @param view 替换视图
     */
    public void setLoadSir(View view)
    {
        if (mLoadService == null){
            mLoadService = LoadSir.getDefault()
                    .register(view, (Callback.OnReloadListener) v -> onRetryBtnClick());
        }

    }

    private boolean isShowedContent = false;
    @Override
    public void showContent() {
        if (null != mLoadService)
        {
            isShowedContent = true;
            mLoadService.showSuccess();
        }
    }

    @Override
    public void showLoading() {
        if (null != mLoadService)
        {
            mLoadService.showCallback(LoadingCallback.class);
        }
    }

    @Override
    public void showEmpty() {
        if (null != mLoadService)
        {
            mLoadService.showCallback(EmptyCallback.class);
        }
    }

    @Override
    public void showFailure(String message) {
        if (null != mLoadService)
        {
            if (!isShowedContent)
            {
                mLoadService.showCallback(ErrorCallback.class);
            }
            else
            {
                ToastUtil.show(this, message);
            }
        }
    }


    /**
     * 获取viewModel
     */
    protected abstract VM getViewModel();

    /**
     * 获取参数Variable
     */
    protected abstract int getBindingVariable();

    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * 失败重试,重新加载事件
     */
    protected abstract void onRetryBtnClick();
}
