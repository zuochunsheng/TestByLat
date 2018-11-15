package com.android.superplayer.util.request;

import android.content.Context;
import com.android.superplayer.config.IsTest;
import com.android.superplayer.service.manager.ApiService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by elu on 2018/3/30.
 */

public class BaseRetrofit {

    public static final String API_SERVER = IsTest.getRent();//域名

    private static OkHttpClient mOkHttpClient  = new OkHttpClient();//联网客户端配置
    private static Retrofit mRetrofitNoCache = null ;
    private static ApiService apiService ;
    private Gson gson;


    private Context mCntext;
    private static BaseRetrofit instance = null;
    public static BaseRetrofit getInstance(Context context){
        if (instance == null){
            instance = new BaseRetrofit(context);
        }
        return instance;
    }


    private static class SingletonHolder {
        private static BaseRetrofit INSTANCE = new BaseRetrofit();
    }

    public static BaseRetrofit getInstance() {

        return SingletonHolder.INSTANCE;
    }



    private BaseRetrofit() {
        this(null);
        init();
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
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    //设置OKHttpClient为网络客户端
                    .client(mOkHttpClient)
                    .build();
        }

        return mRetrofitNoCache;
    }



    public ApiService getService(){
        return apiService ;
    }





    //get请求
    public <T> Flowable<T> get(String url, Map<String, String> params, final Class<T> clazz) {
        return apiService.executeGet(url, params)
                .onBackpressureDrop()//背压丢弃策略
                .map(new Function<ResponseBody, T>() {
                    @Override
                    public T apply(ResponseBody responseBody) {
                        T t = null;
                        try {
                            String body = responseBody.string();
                            t = getGson().fromJson(body, clazz);
                        } catch (Exception e) {
                            new Exception(e.getMessage());
                        } finally {
                            if (null != responseBody) {
                                responseBody.close();
                            }
                        }
                        return t;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    //get请求   占位符 有问题
//    public <T> Flowable<T> getPath(String url, String id, final Class<T> clazz) {
//        return mApiServices.executePahtGet(url, id)
//                .onBackpressureDrop()//背压丢弃策略
//                .map(new Function<ResponseBody, T>() {
//                    @Override
//                    public T apply(ResponseBody responseBody) throws Exception {
//                        T t = null;
//                        try {
//                            String body = responseBody.string();
//                            t = getGson().fromJson(body, clazz);
//                        } catch (Exception e) {
//                            new Exception(e.getMessage());
//                        } finally {
//                            if (null != responseBody) {
//                                responseBody.close();
//                            }
//                        }
//                        return t;
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//    }

    //post请求
    public <T> Flowable<T> post(String urlField, Map<String, String> params, final Class<T> clazz) {
        return apiService.executePost(urlField, params)
                .onBackpressureDrop()//背压丢弃策略
                .map(new Function<ResponseBody, T>() {
                    @Override
                    public T apply(ResponseBody responseBody) {
                        T t = null;
                        try {
                            String body = responseBody.string();
                            t = getGson().fromJson(body, clazz);
                        } catch (Exception e) {
                            new Exception(e.getMessage());
                        } finally {
                            if (null != responseBody) {
                                responseBody.close();
                            }
                        }
                        return t;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public <T> Flowable<T> uploadFile(String urlField, File file, String fileDesc, final Class<T> clazz) {

        RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody fileDescBody = RequestBody.create(MediaType.parse("multipart/form-data"), fileDesc);
        MultipartBody.Part formData = MultipartBody.Part.createFormData("file", file.getName(), fileBody);//keyName是跟后台约定好的

        return apiService.upLoadFile(urlField, fileDescBody, formData)
                .onBackpressureDrop()//背压丢弃策略
                .map(new Function<ResponseBody, T>() {
                    @Override
                    public T apply(ResponseBody responseBody) {
                        T t = null;
                        try {
                            String body = responseBody.string();
                            t = getGson().fromJson(body, clazz);
                        } catch (Exception e) {
                            new Exception(e.getMessage());
                        } finally {
                            if (null != responseBody) {
                                responseBody.close();
                            }
                        }
                        return t;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    private Gson getGson() {
        if (null == gson) {
            gson = new Gson();
        }
        return gson;
    }

}
