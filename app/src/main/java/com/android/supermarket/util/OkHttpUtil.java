package com.android.supermarket.util;

import android.os.Handler;

import com.android.supermarket.util.response.OnResponListener;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * anther: created by zuochunsheng on 2018/8/31 10 : 45
 * descript : okhttp 简易封装
 */
public class OkHttpUtil {

    //保证OkHttpClient是唯一的
    private static OkHttpClient okHttpClient;

    static Handler mHandler = new Handler();

    static {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient();
        }
    }

    /**
     * Get请求
     * @param url
     * @param callback 回调函数
     */
    public static void httpGet(String url, final OnResponListener callback) {

        if (callback == null) {
            throw new NullPointerException("callback is null");
        }

        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure("",e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(result);
                    }
                });
                response.body().close();
            }
        });
    }

    /**
     * Post请求
     * @param url
     * @param params 参数
     * @param callback 回调函数
     */
    public static void httpPost(String url, Map<String,String> params, final OnResponListener callback) {
        if (callback == null) {
            throw new NullPointerException("callback is null");
        }
        if (params == null) {
            throw new NullPointerException("params is null");
        }

        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        Set<String> keySet = params.keySet();
        for(String key:keySet) {
            String value = params.get(key);
            formBodyBuilder.add(key,value);
        }
        FormBody formBody = formBodyBuilder.build();

        Request request = new Request
                .Builder()
                .post(formBody)
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure("",e);
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(result);
                    }
                });
                response.body().close();
            }
        });
    }
}
