package com.example.library_common.config;

import androidx.annotation.Nullable;

import com.example.library_base.base.BaseApplication;
import com.example.library_common.IModuleInit;

public class ModuleLifecycleConfig {

    private ModuleLifecycleConfig()
    {

    }

    private static class SingleHolder
    {
        private static ModuleLifecycleConfig instance =
                new ModuleLifecycleConfig();
    }

    public static ModuleLifecycleConfig getInstance()
    {
        return SingleHolder.instance;
    }

    /** 优先初始化 */
    public void initModuleAhead(@Nullable BaseApplication application)
    {
        for (String moduleName : ModuleLifecycleReflexs.initModuleNames)
        {
            try
            {
                Class<?> clazz = Class.forName(moduleName);
                IModuleInit init = (IModuleInit)clazz.newInstance();
                init.onInitAhead(application);
            }
            catch (ClassNotFoundException | InstantiationException
                    | IllegalAccessException e)
            {
                e.printStackTrace();
            }

        }

    }

    /** 后初始化 */
    public void initModuleLow(@Nullable BaseApplication application)
    {
        for (String moduleName : ModuleLifecycleReflexs.initModuleNames)
        {
            try
            {
                Class<?> clazz = Class.forName(moduleName);
                IModuleInit init = (IModuleInit)clazz.newInstance();
                // 调用初始化方法
                init.onInitLow(application);
            }
            catch (ClassNotFoundException | InstantiationException
                    | IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }
    }
}
