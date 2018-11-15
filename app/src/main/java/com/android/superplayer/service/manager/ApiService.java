package com.android.superplayer.service.manager;


import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import retrofit2.Response;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;


/**
 * Created by elu on 2018/3/29.
 * ResponseBody
 */

public interface ApiService {


    //  产品列表
//    @FormUrlEncoded
//    @POST(ApplicationInterface.URL_PRODUCT_LIST)
//    Observable<ResponseBody> getProductList(@FieldMap HashMap<String, String> hashMap);
//
//    // 登陆
//    @FormUrlEncoded
//    @POST(ApplicationInterface.URL_USERS_LOGIN)
//    Observable<LoginBean> login(@FieldMap HashMap<String, String> hashMap);
//
//    // 注册 --结果筛选一次
//    @FormUrlEncoded
//    @POST(ApplicationInterface.URL_USERS_REG)
//    Observable<ResponseBean<LoginWBean>> reg(@FieldMap HashMap<String, String> hashMap);
//
//    //
//    @FormUrlEncoded
//    @POST(ApplicationInterface.URL_BANNERS_LIST)
//    Observable<BannerListBean> getBannerList(@FieldMap HashMap<String, String> hashMap);
//
//    @FormUrlEncoded
//    @POST(ApplicationInterface.URL_NOTICE_LIST)
//    Observable<NoticeListBean> getNoticeList(@FieldMap HashMap<String, String> hashMap);




    //get请求
    @GET("{url}")
    Flowable<ResponseBody> executeGet(
            @Path(value = "url", encoded = true) String url,
            @QueryMap Map<String, String> maps);


    //post请求
    @FormUrlEncoded
    @POST("{url}")
    Flowable<ResponseBody> executePost(
            @Path(value = "url", encoded = true) String url,
            @FieldMap Map<String, String> maps);

    //单文件或图片上传
    @Multipart
    @POST("{url}")
    Flowable<ResponseBody> upLoadFile(
            @Path(value = "url", encoded = true) String url,
            @Part("description") RequestBody description,
            @Part MultipartBody.Part filer);

    //多文件或者图片上传
    @POST("{url}")
    Observable<ResponseBody> uploadFiles(
            @Path(value = "url", encoded = true) String url,
            @Path("headers") Map<String, String> headers,
            @Part("filename") String description,
            @PartMap() Map<String, RequestBody> maps);

    //下载文件
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);


    @GET
    @Streaming
    Observable<Response<ResponseBody>> download(@Header("Range") String range, @Url String url);

    //@GET("{url}")
    //Call<DownUrlBean> getDownloadUrl(@Path(value = "url", encoded = true) String url);


}
