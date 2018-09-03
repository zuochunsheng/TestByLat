package com.android.supermarket.service.presenter.impl;

import android.content.Context;

import com.android.supermarket.config.LogUtil;
import com.android.supermarket.service.entity.BannerListBean;
import com.android.supermarket.service.entity.NoticeListBean;
import com.android.supermarket.service.manager.DataManager;
import com.android.supermarket.service.presenter.BasePresenter;
import com.android.supermarket.service.presenter.IBannerListPresenter;
import com.android.supermarket.service.presenter.INoticeListPresenter;
import com.android.supermarket.service.view.impl.IBannerListView;
import com.android.supermarket.service.view.impl.INoticeListView;
import com.android.supermarket.util.request.BaseService;

import java.util.HashMap;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * anther: created by zuochunsheng on 2018/9/3 09 : 54
 * descript :
 * mCompositeSubscription 方式 要 onCreate 和 onDestroy
 * BaseService.getBaseService() 方式不需要
 */
public class NoticeListPresenterImpl extends BasePresenter implements INoticeListPresenter {


    private INoticeListView activity;

    public NoticeListPresenterImpl(INoticeListView activity) {
        this.activity = activity;
    }

    @Override
    public void getNoticeList(HashMap<String, String> requestBody) {

        BaseService.getBaseService()
                .getNoticeList(requestBody)
                .subscribeOn(Schedulers.newThread()) //子线程访问网络

                .observeOn(AndroidSchedulers.mainThread()) //回调到主线程
                .subscribe(new Subscriber<NoticeListBean>() {
                    @Override
                    public void onCompleted() {
                        //LogUtil.e("成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("网络请求失败：" + e.getMessage());
                    }

                    @Override
                    public void onNext(NoticeListBean responseBody) {
                        //网络请求成功 有返回 不管对错
                        LogUtil.e("next noticeList");

                        try {
                            //String s = responseBody.string();//不能用 toString() ;

                            LogUtil.e(responseBody);// 值为null 的不会打印出来
                            activity.getNoticeListInfo(responseBody);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                });


    }


}
