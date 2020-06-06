package com.example.library_common;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.library_base.base.BaseApplication;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.mmkv.MMKV;

/**
 * 初始化通用库，基础库
 */
public class CommonModuleInit implements IModuleInit {
    @Override
    public boolean onInitAhead(BaseApplication application) {
        // 初始化日志
        Logger.addLogAdapter(new AndroidLogAdapter()
        {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag)
            {
                return application.issDebug();
            }
        });
        if (application.issDebug())
        {
            ARouter.openLog(); // 开启日志
            ARouter.openDebug(); // 使用InstantRun的时候，需要打开该开关，上线之后关闭，否则有安全风险
        }
        ARouter.init(application);
        MMKV.initialize(application);
        Logger.i("基础层初始化完毕 -- onInitAhead");
        return false;
    }

    @Override
    public boolean onInitLow(BaseApplication application) {
        return false;
    }
}
