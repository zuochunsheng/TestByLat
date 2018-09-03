package com.android.superplayer.config;


import java.util.List;

/**
 *
 * @author zuochunsheng
 * @time 2018/8/29 16:31
 * 后台接口
 */
public class ApplicationInterface {

    private static final String RENT = IsTest.getRent();



    //根据类别，获取全部广告列表 banner
    public static final String URL_BANNERS_LIST = "api/banners/List";

    //公告，带分页 notice
    public static final String URL_NOTICE_LIST = "notice/List";

    //产品列表
    public static final String URL_PRODUCT_LIST = "product/List";

    //product/Details
    public static final String URL_PRODUCT_DETAILS = "product/Details";


    //users/login
    public static final String URL_USERS_LOGIN = "users/login";

    //users/reg
    public static final String URL_USERS_REG = "users/reg";

    //users/pushdata 交单
    public static final String URL_USERS_PUSHDATA = "users/pushdata";







}
