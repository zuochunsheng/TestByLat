package com.android.superplayer.util.request;

import android.content.Context;

import com.android.superplayer.config.UrlCommon;

import java.util.HashMap;

/**
 * anther: created by zuochunsheng on 2018/8/30 14 : 41
 * descript :公共参数
 */
public class ParamRequest {

    /*
     * 公共参数
     */
    public static HashMap<String,String> getRequestDefaultHash(Context context){

        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("platform", UrlCommon.platform) ;
        hashMap.put("appVersion", UrlCommon.getVersionName(context)) ;
        hashMap.put("category", UrlCommon.category) ;

        return hashMap;
    }

}
