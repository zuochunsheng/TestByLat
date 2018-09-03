package com.android.supermarket.service.presenter.impl;

import android.content.Context;

import com.android.supermarket.config.LogUtil;
import com.android.supermarket.service.entity_wrap.LoginWBean;
import com.android.supermarket.service.manager.DataManager;
import com.android.supermarket.service.presenter.BasePresenter;
import com.android.supermarket.service.presenter.IRegPassPresenter;
import com.android.supermarket.service.view.impl.IRegPassView;
import com.android.supermarket.util.response.ExceptionSubscriber;
import com.android.supermarket.util.response.SimpleCallback;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 对结果筛选一次
 * authr : edz on 2017/9/13  下午7:17
 * describe ：注册
 * 相同类型的参数类型，统一的返回，
 * retrofit 由于不同的请求体 ，doLogin 部分 ，在 此处直接返回结果，不用在添加model 层
 * <p>
 * 可以添加 DataManager 管理层 进行调用 （对结果进行 统一处理）
 */


public class RegPassPresenterImpl extends BasePresenter implements IRegPassPresenter {

    private IRegPassView activity;
    //private final IModel model;

    // protected CompositeSubscription mCompositeSubscription;

    private LoginWBean loginNWBean;

    public RegPassPresenterImpl(IRegPassView activity) {
        this.activity = activity;
        //model = new ModelImpl();

        //用继承 子类中不用多些几次
        //mCompositeSubscription = new CompositeSubscription();
        super.onCreate();
    }

//    @Override
//    public void onDestroy() {
//       super.onDestroy();
//    }

    // 返回全部信息
    @Override
    public void submitRegPass(Context context, HashMap<String, String> requestBody) {
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


//        BaseService.getBaseService()
//                .reg(requestBody)
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.newThread())
//                .flatMap(new BaseResponseFunc<LoginWBean>())//结果帅选一次
//                .subscribeOn(Schedulers.io())
//
//                .observeOn(AndroidSchedulers.mainThread()) //回调到主线程
//                .subscribe(new ExceptionSubscriber<LoginWBean>(new SimpleCallback<LoginWBean>() {
//                    @Override
//                    public void onStart() {
//
//                    }
//
//                    @Override
//                    public void onNext(LoginWBean loginNWBean) {
//                        LogUtil.e("onNext reg 返回解刨200 之后的数据");
//                        LogUtil.e(loginNWBean);
//                        activity.regPassResultInfo(loginNWBean);
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//
//                    @Override
//                    public void onError() {
//
//                    }
//                }));


        super.mCompositeSubscription.add(DataManager.getInstance(context)
                .reg(requestBody)
                .subscribeOn(Schedulers.io())

                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<LoginWBean>(new SimpleCallback<LoginWBean>() {// 统一异常处理
                    @Override
                    public void onStart() {// 请求之前 的操作 比如弹框

                    }

                    @Override
                    public void onNext(LoginWBean loginNWBean1) {
                        LogUtil.e("onNext reg 返回解刨200 之后的数据");
                        LogUtil.e(loginNWBean1);
                        loginNWBean = loginNWBean1;
                    }

                    @Override
                    public void onComplete() {
                        if (loginNWBean != null) {
                            activity.regPassResultInfo(loginNWBean);
                        }
                    }

                    @Override
                    public void onError() {//异常 在统一异常中处理 吐司

                    }
                }))
        );


    }


}
