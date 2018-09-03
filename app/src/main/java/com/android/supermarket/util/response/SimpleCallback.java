package com.android.supermarket.util.response;

/**
 * Created by DELL on 2017/3/20.
 * ui调用网络请求回调的简单的接口
 */

public interface SimpleCallback<T> {
    void onStart();
    void onNext(T t);
    void onComplete();

    void onError();

}
