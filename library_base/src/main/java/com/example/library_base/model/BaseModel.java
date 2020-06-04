package com.example.library_base.model;

import java.lang.ref.WeakReference;

public abstract class BaseModel<T>  extends  SuperBaseModel<T>{

    /**
     * 网络数据加载成功,通知所有注册者加载数据
     *
     * @param data 数据bean
     */
    public void loadSucess(T data){
        synchronized (this){
            mUiHander.postDelayed(() ->{
                for (WeakReference<IBaseModelListener> weakListener : mWeakReferenceDeque)
                {
                    if (weakListener.get() instanceof IModelListener){
                        IModelListener listenerItem = (IModelListener)weakListener.get();
                        if (null != listenerItem)
                        {
                            listenerItem.onLoadFinish(BaseModel.this, data);
                        }
                    }
                }
            },0);
        }
    }

    /**
     * 加载数据失败,通知所有注册者
     */
    protected void loadFail(String prompt)
    {
        synchronized (this)
        {
            mUiHander.postDelayed(() -> {
                for (WeakReference<IBaseModelListener> weakListener : mWeakReferenceDeque)
                {
                    if (weakListener.get() instanceof IModelListener)
                    {
                        IModelListener listenerItem =
                                (IModelListener)weakListener.get();
                        if (null != listenerItem)
                        {
                            listenerItem.onLoadFail(BaseModel.this, prompt);
                        }
                    }
                }
            }, 0);
        }
    }

    @Override
    protected void notifyCacheData(T data)
    {
        loadSucess(data);
    }
}
