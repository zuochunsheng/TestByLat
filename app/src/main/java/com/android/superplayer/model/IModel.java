package com.android.superplayer.model;

import android.content.Context;

import com.android.superplayer.util.response.OnResponListener;

import java.util.HashMap;
import java.util.Map;

/**
 * authr : edz on 2017/8/29  下午1:48
 * describe :统一网络请求 model
 * 不用
 */
public interface IModel {


    //获取 我的第一界面 信息
     void getCommonInfo(final Context context, final String url, final HashMap<String, String> map,
                        final OnResponListener listener);

     // 返回全部信息
     void getCommonInfo(final Context context, final String url, final HashMap<String, String> map,
                        final OnResponListener listener, boolean refullInfo);

}

