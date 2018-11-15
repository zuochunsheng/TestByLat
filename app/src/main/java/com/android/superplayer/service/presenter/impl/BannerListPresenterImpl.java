package com.android.superplayer.service.presenter.impl;

import android.content.Context;

import com.android.superplayer.config.ApplicationInterface;
import com.android.superplayer.config.LogUtil;
import com.android.superplayer.service.entity.BannerListBean;
import com.android.superplayer.service.manager.DataManager;
import com.android.superplayer.service.presenter.BasePresenter;
import com.android.superplayer.service.presenter.IBannerListPresenter;
import com.android.superplayer.service.view.impl.IBannerListView;
import com.android.superplayer.util.request.BaseRetrofit;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * anther: created by zuochunsheng on 2018/9/3 09 : 54
 * descript :
 * mCompositeSubscription 方式 要 onCreate 和 onDestroy
 * BaseService.getBaseService() 方式不需要
 */
public class BannerListPresenterImpl extends BasePresenter implements IBannerListPresenter {


    private IBannerListView activity;

    public BannerListPresenterImpl(IBannerListView activity) {
        this.activity = activity;
        super.onCreate();
    }

    @Override
    public void getBannerList(Context context, HashMap<String, String> requestBody) {

        BaseRetrofit.getInstance()
                .post(ApplicationInterface.URL_BANNERS_LIST,requestBody, BannerListBean.class)
                .subscribe(new Subscriber<BannerListBean>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                    }

                    @Override
                    public void onNext(BannerListBean bannerListBean) {
                        //网络请求成功 有返回 不管对错
                        LogUtil.e("next bannerList mCompositeSubscription");

                        try {
                            //String s = responseBody.string();//不能用 toString() ;

                            LogUtil.e(bannerListBean);// 值为null 的不会打印出来
                            activity.bannerListInfo(bannerListBean);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        LogUtil.e("网络请求失败：" + t.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

//        super.mCompositeSubscription.add(DataManager.getInstance(context)
//
//                .getBannerList(requestBody)
//                .subscribeOn(Schedulers.io())
//
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<BannerListBean>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        LogUtil.e("网络请求失败：" + e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(BannerListBean responseBody) {
//                        //网络请求成功 有返回 不管对错
//                        LogUtil.e("next bannerList mCompositeSubscription");
//
//                        try {
//                            //String s = responseBody.string();//不能用 toString() ;
//
//                            LogUtil.e(responseBody);// 值为null 的不会打印出来
//                            activity.bannerListInfo(responseBody);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                })
//        );
//


//        BaseService.getBaseService()
//                .getBannerList(requestBody)
//                .subscribeOn(Schedulers.newThread()) //子线程访问网络
//
//                .observeOn(AndroidSchedulers.mainThread()) //回调到主线程
//                .subscribe(new Subscriber<BannerListBean>() {
//                    @Override
//                    public void onCompleted() {
//                        //LogUtil.e("成功");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        LogUtil.e("网络请求失败：" + e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(BannerListBean responseBody) {
//                        //网络请求成功 有返回 不管对错
//                        LogUtil.e("next bannerList");
//
//                        try {
//                            //String s = responseBody.string();//不能用 toString() ;
//
//                            LogUtil.e(responseBody);// 值为null 的不会打印出来
//                            activity.bannerListInfo(responseBody);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//                });
//

    }
}
