package com.android.superplayer.service.manager;


import com.android.superplayer.config.ApplicationInterface;
import com.android.superplayer.service.entity.BannerListBean;
import com.android.superplayer.service.entity.LoginBean;
import com.android.superplayer.service.entity.NoticeListBean;
import com.android.superplayer.service.entity_wrap.LoginWBean;
import com.android.superplayer.util.response.ResponseBean;
import com.squareup.okhttp.ResponseBody;

import java.util.HashMap;

import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by elu on 2018/3/29.
 * ResponseBody
 */

public interface ApiService {


    //  产品列表
    @FormUrlEncoded
    @POST(ApplicationInterface.URL_PRODUCT_LIST)
    Observable<ResponseBody> getProductList(@FieldMap HashMap<String, String> hashMap);

    // 登陆
    @FormUrlEncoded
    @POST(ApplicationInterface.URL_USERS_LOGIN)
    Observable<LoginBean> login(@FieldMap HashMap<String, String> hashMap);

    // 注册 --结果筛选一次
    @FormUrlEncoded
    @POST(ApplicationInterface.URL_USERS_REG)
    Observable<ResponseBean<LoginWBean>> reg(@FieldMap HashMap<String, String> hashMap);

    //
    @FormUrlEncoded
    @POST(ApplicationInterface.URL_BANNERS_LIST)
    Observable<BannerListBean> getBannerList(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST(ApplicationInterface.URL_NOTICE_LIST)
    Observable<NoticeListBean> getNoticeList(@FieldMap HashMap<String, String> hashMap);


}
