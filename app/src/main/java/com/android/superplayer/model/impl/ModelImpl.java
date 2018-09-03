package com.android.superplayer.model.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.superplayer.config.Constant;
import com.android.superplayer.config.LogUtil;
import com.android.superplayer.model.IModel;
import com.android.superplayer.ui.activity.my.LoginActivity;
import com.android.superplayer.util.ActivityUtil;
import com.android.superplayer.util.OkHttpUtil;
import com.android.superplayer.util.response.OnResponListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * authr : edz on 2017/8/28  下午4:54
 * describe
 * 相同类型的参数类型，统一的返回，retrofit 由于不同的请求体 doLogin 部分
 */


public class ModelImpl implements IModel {

    // 返回 data 信息
    @Override
    public void getCommonInfo(final Context context, final String url, final HashMap<String,String> map,
                              final OnResponListener listener){

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                DataRequester.withDefaultHttps(context)
//                        .setUrl(url)
//                        .setMethod(DataRequester.Method.POST)
//                        .setHeader(RequestParams.getRequestHeaders(context))
//                        .setBody(map)
//                        .setStringResponseListener(new DataRequester.StringResponseListener() {
//                            @Override
//                            public void onResponse(String response) {
//                                LogUtil.e("res",response);
//
//                                //listener.onSuccess(response);
//                                dealResponse(response,listener,context);
//
//                            }
//                        }).setResponseErrorListener(new DataRequester.ResponseErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            //返回失败的原因
//                            Log.e("%%%%%%%%%%%%%", VolleyErrorHelper.getMessage(error,context));
//                            listener.onFailure(VolleyErrorHelper.getMessage(error,context),error);
//                        }
//                }).requestString();
//            }
//        }).start();


    }



    /*
     *  统一处理返回数据
     *  200  作为成功返回 ，其它情况 都做失败返回（并返回失败信息）
     */
    private void dealResponse(String response, OnResponListener listener, Context context){
        try{
            JSONObject jsonObject = new JSONObject(response);
            int code = jsonObject.getInt("code");
            if(code ==200){
                JSONObject data = jsonObject.getJSONObject("data");
                LogUtil.e("data",data.toString());
                listener.onSuccess(data.toString());//data.toString()
            }else {
                String msg = jsonObject.getString("msg");
                LogUtil.e("fail",code + "-" +msg);
                if(code ==401 ){//跳转登录 失效

                    @SuppressLint("WrongConstant")
                    SharedPreferences sp = context.getSharedPreferences(Constant.SP_NAME, Context.MODE_APPEND);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.clear();
                    editor.apply();
                    ActivityUtil.getInstance().onNextClearTop(context, LoginActivity.class);


                }else {
                    listener.onFailure(msg,null);

                }


            }

        }
        catch (Exception ignored){

        }



    }


    /**
     * 网络请求  + 返回 全部的信息
     * @param context
     * @param url
     * @param map
     * @param listener
     * @param refullInfo
     */
    @Override
    public void getCommonInfo(final Context context, final String url, final HashMap<String,String> map,
                              final OnResponListener listener, boolean refullInfo){

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                DataRequester.withDefaultHttps(context)
//                        .setUrl(url)
//                        .setMethod(DataRequester.Method.POST)
//                        .setHeader(RequestParams.getRequestHeaders(context))
//                        .setBody(map)
//                        .setStringResponseListener(new DataRequester.StringResponseListener() {
//                            @Override
//                            public void onResponse(String response) {
//                                LogUtil.e("res",response);
//
//                                listener.onSuccess(response);
//
//
//                            }
//                        }).setResponseErrorListener(new DataRequester.ResponseErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        //返回失败的原因
//                        try {
//                            LogUtil.e("%%%%%%%%%%%%%", error.networkResponse.statusCode);
//                        }catch (Exception ignored){
//
//                        }
//                        listener.onFailure(VolleyErrorHelper.getMessage(error,context),error);
//                    }
//                }).requestString();
//            }
//        }).start();


        OkHttpUtil.httpPost(url, map, new OnResponListener() {
            @Override
            public void onSuccess(String info) {
                listener.onSuccess(info);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                listener.onFailure(msg,e);
            }
        });


    }






}
