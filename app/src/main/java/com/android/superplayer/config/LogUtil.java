package com.android.superplayer.config;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

/**
 * authr : edz on 2017/8/28  上午11:45
 * describe :测试 打印日志，手动
 */


public class LogUtil {

    private static final boolean isTest =true;// IsTest.IS_TEST;
    private final static String TAG ="tag";

    public static  void e(String string){
        if(isTest){
            if(!TextUtils.isEmpty(string)){
                Log.e(TAG,string);
            }

        }

    }
    public static  void e(String tag, String string){
        if(isTest){
            if(!TextUtils.isEmpty(string)){
                Log.e(tag,string);
            }

        }

    }

    public static void e(Object object){

        if(isTest){
            Gson gson = new Gson();
            String s = gson.toJson(object);
            if(!TextUtils.isEmpty(s)){
                Log.e(TAG,s);
            }

        }

    }
    public static void e(String tag, Object object){

        if(isTest){
            Gson gson = new Gson();
            String s = gson.toJson(object);
            if(!TextUtils.isEmpty(s)){
                Log.e(tag,s);
            }

        }

    }


}
