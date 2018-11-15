package com.android.superplayer.service.manager;

import android.content.Context;


import com.android.superplayer.util.request.BaseRetrofit;






/**
 * Created by elu on 2018/3/30.
 */
//该类用来管理RetrofitApiService中对应的各种API接口，
// 当做Retrofit和presenter中的桥梁，Activity就不用直接和retrofit打交道了
public class DataManager {
    private ApiService mRetrofitService;
    private volatile static DataManager instance;

    private DataManager(Context context) {
        this.mRetrofitService = BaseRetrofit.getInstance(context).getService();
    }

    //由于该对象会被频繁调用，采用单例模式，下面是一种线程安全模式的单例写法
    public static DataManager getInstance(Context context) {
        if (instance == null) {
            synchronized (DataManager.class) {
                if (instance == null) {
                    instance = new DataManager(context);
                }
            }
        }
        return instance;
    }


    //将retrofit的业务方法映射到DataManager中，以后统一用该类来调用业务方法
    //以后再retrofit中增加业务方法的时候，相应的这里也要添加，比如添加一个getOrder

//    public Observable<LoginWBean> reg(HashMap hashMap) {
//        return mRetrofitService.reg(hashMap)
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.newThread())
//                .flatMap(new BaseResponseFunc<LoginWBean>());//结果筛选一次
//
//    }
//
//
//
//    public Observable<BannerListBean> getBannerList(HashMap hashMap) {
//        return mRetrofitService.getBannerList(hashMap)
//                .subscribeOn(Schedulers.io());
//
//    }






}
