package com.android.superplayer.util.request;

import org.reactivestreams.Subscription;

/**
 * 网络请求管理 抽出来的方法类
 */

public interface RxActionManager<T> {
    void add(T tag, Subscription subscription);

    void remove(T tag);

    void removeAll();

    void cancel(T tag);

    void cancelAll();
}
