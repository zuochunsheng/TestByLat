package com.android.superplayer.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.util.Log;

import com.android.superplayer.R;
import com.android.superplayer.config.LogUtil;
import com.android.superplayer.eventbus.EBBean;
import com.android.superplayer.eventbus.EBConst;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.tencent.smtt.sdk.QbSdk;

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
        // 应用程序入口处调用,避免手机内存过小,杀死后台进程后通过历史intent进入Activity造成SpeechUtility对象为null
        // 注意：此接口在非主进程调用会返回null对象，如需在非主进程使用语音功能，请增加参数：SpeechConstant.FORCE_LOGIN+"=true"
        // 参数间使用“,”分隔。
        // 设置你申请的应用appid

        // 注意： appid 必须和下载的SDK保持一致，否则会出现10407错误

        StringBuffer param = new StringBuffer();
        param.append("appid="+getString(R.string.app_id));
        param.append(",");
        // 设置使用v5+
        param.append(SpeechConstant.ENGINE_MODE+"="+SpeechConstant.ENG_TTS);
        SpeechUtility.createUtility(AndroidApplication.this, param.toString());
        super.onCreate();

        mAppContext = getApplicationContext();

        // 得到屏幕的宽度和高度
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenW = dm.widthPixels;
        screenH = dm.heightPixels;


        initActivityListener();

        initX5();


    }

    private void initX5() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                if (arg0) {
                    Log.e("ArticleSystem", "X5 内核加载成功");
                } else {
                    Log.e("ArticleSystem", "X5 内核加载失败");
                }
            }

            @Override
            public void onCoreInitFinished() {

            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);
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
                //LogUtil.e(TAG,"onActivitySaveInstanceState  " + activity.getClass().getSimpleName());
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
