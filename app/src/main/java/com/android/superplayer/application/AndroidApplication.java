package com.android.superplayer.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.util.Log;

import com.android.superplayer.config.LogUtil;
import com.android.superplayer.eventbus.EBBean;
import com.android.superplayer.eventbus.EBConst;

import org.greenrobot.eventbus.EventBus;


/**
 *
 */
public class AndroidApplication extends Application {

    public static final String TAG = "AndroidApplication";
    /**系统上下文*/
    private static Context mAppContext;

    // 当前屏幕的高宽
    public int screenW = 0;
    public int screenH = 0;

    private int count = 0 ;
    private static AndroidApplication instance;
    // 单例模式中获取唯一的MyApplication实例
    public static AndroidApplication getInstance() {
        if (instance == null) {
            instance = new AndroidApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mAppContext = getApplicationContext();

        // 得到屏幕的宽度和高度
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenW = dm.widthPixels;
        screenH = dm.heightPixels;


        initActivityListener();



    }

    /**获取系统上下文：用于ToastUtil类*/
    public static Context getAppContext() {
        return mAppContext;
    }


//    在Application里面做了一个监听，当退到后台发送一个广播到MainActivity里面调用hide的方法进行隐藏，
//    回到前台再调用show的方法进行显示
    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void initActivityListener() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {


            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
               // LogUtil.e(TAG,"onActivityCreated  " + activity.getClass().getSimpleName());
            }

            @Override
            public void onActivityStarted(Activity activity) {
                if(count ==0) {
                    LogUtil.e(TAG, "onActivityStarted >>>>>>>>>>>>>>>>>>>切到前台  lifecycle");
                    EventBus.getDefault().post(new EBBean(EBConst.app_start));

                }
                count++ ;
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                count--;
                if(count ==0){
                    EventBus.getDefault().post(new EBBean(EBConst.app_stop));
                    LogUtil.e(TAG, "onActivityStopped >>>>>>>>>>>>>>>>>>>切到后台  lifecycle");

                }

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                //EventBus.getDefault().post(new EBBean(EBConst.app_destroy));
                //LogUtil.e(TAG,"onActivityDestroyed  " + activity.getClass().getSimpleName());
            }
        });
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
