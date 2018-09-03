package com.android.supermarket.application;

import android.app.Application;
import android.content.Context;


/**
 *
 */
public class AndroidApplication extends Application {

    /**系统上下文*/
    private static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppContext = getApplicationContext();

    }

    /**获取系统上下文：用于ToastUtil类*/
    public static Context getAppContext() {
        return mAppContext;
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
