package com.android.superplayer.util.request;


import com.android.superplayer.service.manager.ApiService;

/**
 * Created by DELL on 2017/3/28.
 */

public class BaseService {


    private static ApiService apiService;

    public static ApiService getBaseService(){
        if(apiService == null){
            apiService = BaseRetrofit.getNoCacheRetrofit().create(ApiService.class);
        }
        return apiService;
    }

}
