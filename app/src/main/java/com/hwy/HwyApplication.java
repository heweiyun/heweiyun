package com.hwy;

import android.app.Application;
import android.content.Context;

import com.hwy.common.util.CrashHandler;
import com.hwy.di.component.ApplicationComponent;
import com.hwy.di.component.DaggerApplicationComponent;
import com.hwy.di.module.ApplicationModule;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/8 19:44
 * 修改人：heweiyun
 * 修改时间：2016/11/8 19:44
 * 修改备注：
 */

public class HwyApplication extends Application {
    private ApplicationComponent mApplicationComponent;
    private static HwyApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mApplication = this;
        CrashHandler.getInstance().setCustomCrashHanler(this);
    }

    public static HwyApplication getApplication(){
        return mApplication;
    }

    public ApplicationComponent getApplicationComponent(){
        if (null == mApplicationComponent){
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    public static HwyApplication get(Context context){
        return (HwyApplication) context.getApplicationContext();
    }



}
