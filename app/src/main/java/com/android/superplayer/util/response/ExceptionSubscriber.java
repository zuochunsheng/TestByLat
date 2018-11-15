package com.android.superplayer.util.response;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.superplayer.application.AndroidApplication;
import com.android.superplayer.config.Constant;
import com.android.superplayer.ui.activity.my.LoginActivity;
import com.android.superplayer.util.ActivityUtil;
import com.android.superplayer.util.request.RetrofitApiManager;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.net.ConnectException;
import java.net.SocketTimeoutException;


/**
 * Created by DELL on 2017/3/20.
 * 网络请求返回错误 统一处理的订阅类
 * 错误统一处理
 */

public class ExceptionSubscriber<T> implements Subscriber<T> {


    private SimpleCallback<T> simpleCallback;
    private Context application = AndroidApplication.getAppContext();

    private static final String error_401 = "401" ;//链接超时 ，重新登录
    private String mRequestTag;

    public ExceptionSubscriber(SimpleCallback simpleCallback) {
        this.simpleCallback = simpleCallback;
        //this.application = application;
    }

    public ExceptionSubscriber(String requestTag) {
        this.mRequestTag = requestTag;

    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        if (simpleCallback != null) {
//            simpleCallback.onStart();
//        }
//    }



    @Override
    public void onError(Throwable e) {

        if (e instanceof SocketTimeoutException || e instanceof ConnectException) {
            Toast.makeText(application, "网络异常，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        }
        else {//返回500 时的 code + "-" + msg

            if(TextUtils.equals(e.getMessage().split("-")[0],error_401)){
                //goLogin();

            }
            Toast.makeText(application, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (simpleCallback != null) {
            simpleCallback.onComplete();
            RetrofitApiManager.getInstance().remove(mRequestTag);
        }
    }

    @Override
    public void onComplete() {
        if (simpleCallback != null) {
            simpleCallback.onComplete();
        }
        RetrofitApiManager.getInstance().remove(mRequestTag);
    }


    @Override
    public void onSubscribe(Subscription s) {
        s.request(1);//网络请求一般也只有发送一次
        RetrofitApiManager.getInstance().add(mRequestTag, s);//添加的网络请求管理中，方便取消或者 页面销毁时取消
    }

    //这里 T 具体的类型,只要有异常 就会 执行onError
    @Override
    public void onNext(T t) {
        //LogUtil.e("tt ", t);
        if (simpleCallback != null) {
            simpleCallback.onNext(t);
        }
    }






    private void goLogin() {

        @SuppressLint("WrongConstant")
        SharedPreferences sp = application.getSharedPreferences(Constant.SP_NAME, Context.MODE_APPEND);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
        ActivityUtil.getInstance().onNextClearTop(application, LoginActivity.class);


    }


}
