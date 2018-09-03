package com.android.supermarket.util.request;

import android.content.Context;
import com.android.supermarket.config.IsTest;
import com.android.supermarket.service.manager.ApiService;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by elu on 2018/3/30.
 */

public class BaseRetrofit {

    public static final String API_SERVER = IsTest.getRent();//域名

    private static OkHttpClient mOkHttpClient  = new OkHttpClient();//联网客户端配置
    private static Retrofit mRetrofitNoCache = null ;
    private static ApiService apiService ;

    private Context mCntext;
    private static BaseRetrofit instance = null;
    public static BaseRetrofit getInstance(Context context){
        if (instance == null){
            instance = new BaseRetrofit(context);
        }
        return instance;
    }
    private BaseRetrofit(Context mContext){
        mCntext = mContext;
        init();
    }




    private void init() {
        resetApp();
    }
    /*
    * 获取无缓存的Retrofit
    * */
    private void resetApp() {
        //构建Retrofit
        mRetrofitNoCache = new Retrofit.Builder()
                //配置服务器路径
                .baseUrl(API_SERVER)
                //配置转化库，默认是Gson
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                //配置回调库，采用RxJava
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                //设置OKHttpClient为网络客户端
                .client(mOkHttpClient)
                .build();

        apiService = mRetrofitNoCache.create(ApiService.class);
    }

    /*
    * 获取无缓存的Retrofit
    * */
    public static Retrofit getNoCacheRetrofit() {
        if (mRetrofitNoCache == null) {
            //构建Retrofit
            mRetrofitNoCache = new Retrofit.Builder()
                    //配置服务器路径
                    .baseUrl(API_SERVER)
                    //配置转化库，默认是Gson
                    .addConverterFactory(GsonConverterFactory.create())
                    //配置回调库，采用RxJava
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    //设置OKHttpClient为网络客户端
                    .client(mOkHttpClient)
                    .build();
        }

        return mRetrofitNoCache;
    }



    public ApiService getService(){
        return apiService ;
    }
}
