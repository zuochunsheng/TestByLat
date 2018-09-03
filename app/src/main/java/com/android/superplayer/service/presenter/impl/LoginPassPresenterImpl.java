package com.android.superplayer.service.presenter.impl;

import android.content.Context;

import com.android.superplayer.config.LogUtil;
import com.android.superplayer.model.IModel;
import com.android.superplayer.model.impl.ModelImpl;
import com.android.superplayer.service.entity.LoginBean;
import com.android.superplayer.service.presenter.ILoginPassPresenter;
import com.android.superplayer.service.view.impl.ILoginPassView;
import com.android.superplayer.util.request.BaseService;
import com.android.superplayer.util.response.OnResponListener;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 直接返回
 * authr : edz on 2017/9/13  下午7:17
 * describe ：
 * 相同类型的参数类型，统一的返回，
 * retrofit 由于不同的请求体 ，doLogin 部分 ，在 此处直接返回结果，不用在添加model 层
 *
 * 可以添加 DataManager 管理层 进行调用 （对结果进行 统一处理）
 */


public class LoginPassPresenterImpl implements ILoginPassPresenter {

    private ILoginPassView activity;
    //private final IModel model;

    public LoginPassPresenterImpl(ILoginPassView activity) {
        this.activity = activity;
        //model = new ModelImpl();

    }

    // 返回全部信息
    @Override
    public void submitLoginPass(HashMap<String, String> requestBody) {
//        model.getCommonInfo(context, url, requestBody, new OnResponListener() {
//            @Override
//            public void onSuccess(String info) {
//                activity.loginPassResultInfo(info);
//            }
//
//            @Override
//            public void onFailure(String msg, Exception e) {
//
//            }
//        }, true);


        BaseService.getBaseService()
                .login(requestBody)
                .subscribeOn(Schedulers.newThread()) //子线程访问网络

                .observeOn(AndroidSchedulers.mainThread()) //回调到主线程
                .subscribe(new Subscriber<LoginBean>() {
                    @Override
                    public void onCompleted() {
                        //LogUtil.e("成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("网络请求失败：" + e.getMessage());
                    }

                    @Override
                    public void onNext(LoginBean responseBody) {
                        //网络请求成功 有返回 不管对错
                        LogUtil.e("next login");

                        try {
                            //String s = responseBody.string();//不能用 toString() ;

                            LogUtil.e(responseBody);// 值为null 的不会打印出来
                            activity.loginPassResultInfo(responseBody);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                });


    }


}
