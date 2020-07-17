package com.android.superplayer.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.superplayer.R;
import com.android.superplayer.base.BaseFragment;
import com.android.superplayer.config.ApplicationInterface;
import com.android.superplayer.config.LogUtil;
import com.android.superplayer.util.request.BaseRetrofit;
import com.android.superplayer.util.request.BaseService;
import com.android.superplayer.util.request.ParamRequest;
import com.jcodecraeer.xrecyclerview.XRecyclerView;


//import org.reactivestreams.Subscriber;
//import org.reactivestreams.Subscription;

import java.util.HashMap;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class InvestmentFragment extends BaseFragment {


    @BindView(R.id.investment_recyclerview)
    XRecyclerView xRecyclerView;


    private static final String pageSize = "10";//每页几条
    private int pageIndex  = 1 ;//第几页

    @Override
    protected View fetchLayoutRes(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_investment, container, false);
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {



        request();

    }

    private void request() {
        HashMap<String, String> requestDefaultHash = ParamRequest.getRequestDefaultHash(getContext());

        requestDefaultHash.put("pageIndex", String.valueOf(pageIndex));
        requestDefaultHash.put("pageSize", pageSize);
//        isHot (integer, optional): 是否热门,0 全部， 1 热门 ,
//                isUp (integer, optional): 是否置顶， 0 全部，1 置顶 ,
//                period (integer, optional): 周期， 0 全部， 1： 3个月以下；2： 3到6个月； 3：其他 ,
//                name (string, optional): 搜索名称 ,
//                status (integer, optional): 状态， 0 全部；1 进行中， 2 一结束 ,

//        BaseRetrofit.getInstance()
//                .post(ApplicationInterface.URL_PRODUCT_LIST,requestDefaultHash,ResponseBody.class)
//                .subscribe(new Subscriber<ResponseBody>() {
//                    @Override
//                    public void onSubscribe(Subscription s) {
//
//                    }
//
//                    @Override
//                    public void onNext(ResponseBody responseBody) {
//                        LogUtil.e("next 产品列表");
//                        try {
//                            LogUtil.e(responseBody.string());
//                            //dealWelfareSuccess(discoverBean);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//                        LogUtil.e("失败");
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        LogUtil.e("成功");
//                    }
//                });

//        BaseService.getBaseService()
//                .getProductList(requestDefaultHash)
//
//                .subscribeOn(Schedulers.newThread()) //子线程访问网络
//
//                .observeOn(AndroidSchedulers.mainThread()) //回调到主线程
//                .subscribe(new Subscriber<ResponseBody>() {
//                    @Override
//                    public void onCompleted() {
//
//                        LogUtil.e("成功");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                        LogUtil.e("失败");
//                    }
//
//                    @Override
//                    public void onNext(ResponseBody ResponseBody) {
//
//                        LogUtil.e("next 产品列表");
//                        try {
//                            LogUtil.e(ResponseBody.string());
//                            //dealWelfareSuccess(discoverBean);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//                });


    }

}
