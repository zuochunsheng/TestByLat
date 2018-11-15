package com.android.superplayer.util.request;

import android.support.v4.util.ArrayMap;
import android.util.Log;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.Set;


/**
 * 网络请求管理的具体实现类 实现对当前Rx的取消订阅功能
 */
public class RetrofitApiManager implements RxActionManager<String> {
    private final String TAG = this.getClass().getSimpleName();
    private static RetrofitApiManager sInstance = new RetrofitApiManager();//当前对象的实例
    private ArrayMap<String, Subscription> maps = new ArrayMap();//存放网络请求的键值对

    public static RetrofitApiManager getInstance() {
        return sInstance;
    }

    /**
     * 添加到管理中
     */
    @Override
    public void add(String tag, Subscription subscription) {
        Log.d(TAG, "add: " + tag);
        maps.put(tag, subscription);
    }

    /**
     * 从管理中移除
     */
    @Override
    public void remove(String tag) {
        if (!maps.isEmpty()) {
            Log.d(TAG, "remove: " + tag);
            maps.remove(tag);
        }
    }

    /**
     * 从管理中移除所有
     */
    @Override
    public void removeAll() {
        if (!maps.isEmpty()) {
            maps.clear();
        }
    }

    /**
     * 取消tag对应的订阅
     */
    @Override
    public void cancel(String tag) {
        if (maps.isEmpty()) {
            return;
        }
        if (maps.get(tag) == null) {
            return;
        }
        maps.get(tag).cancel();
        maps.remove(tag);
        Log.d(TAG, "cancel: " + tag);
    }

    /**
     * 取消所有的订阅
     */
    @Override
    public void cancelAll() {
        if (maps.isEmpty()) {
            return;
        }
        Set<String> keys = maps.keySet();
        ArrayList<String> keyList = new ArrayList<>(keys);
        for (String apiKey : keyList) {
            cancel(apiKey);
        }

    }

}
