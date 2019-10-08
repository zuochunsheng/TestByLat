package com.android.superplayer.util;

/**
 * 网络接口请求URL
 */
public class GlobalNetConstants {
    //*****************************列表请求默认参数*******************************************//
    /**
     * 起始页数，默认1
     */
    public final static String KEY_PAGE_INDEX = "pageIndex";
    /**
     * 一页默认10个数据
     */
    public final static String KEY_PAGE_SIZE = "pageSize";
    /**
     * 默认token的key
     */
    public final static String KEY_TOKEN = "token";

    /**
     * 标签类型（1，长视频 2，电视剧3，vip视频）
     */
    public final static String KEY_TYPE = "type";


     public final static String BASE_URL = "https://alivc-demo.aliyuncs.com";
//    public final static String BASE_URL = "http://47.100.18.52:8081";


    //*****************************列表请求地址*******************************************//

    /**
     * 获取随机用户
     */
    public final static String GET_USER_INFO = BASE_URL + "/longVideoUser/randomUser";

    /**
     * 获取推荐电视剧列表
     */
    public final static String GET_RECOMMNED_TV_PLAY_LIST = BASE_URL + "/longVideo/getRecommnedTvPlayList";

    /**
     * 获取首页电视剧列表
     */
    public final static String GET_HOME_PAGE_TV_PLAY_LIST = BASE_URL + "/longVideo/getHomePageTvPlayList";

    /**
     * 获取推荐长视频列表
     */
    public final static String GET_RECOMMNED_LONG_VIDEOS_LIST = BASE_URL + "/longVideo/getRecommendLongVideosList";

    /**
     * 获取首页各类别长视频列表
     */
    public final static String GET_LONG_VIDEOS_LIST = BASE_URL + "/longVideo/getLongVideosList";

    /**
     * 获取vip视频列表
     */
    public final static String GET_VIP_LONG_VIDEOS_LIST = BASE_URL + "/longVideo/getVipLongVideosList";


    /**
     * 根据电视剧id查询长视频列表
     */
    public final static String GET_VIDEOS_LIST_BY_ID = BASE_URL + "/longVideo/getLongVideoById";

    /**
     * 根据tvid获取剧集信息
     */
    public final static String GET_SERIES_INFO_BY_ID = BASE_URL + "/longVideo/tvPlayList";

    /**
     * 获取相似电视剧列表
     */
    public final static String GET_SIMILAR_VIDEOS_LIST = BASE_URL + "/longVideo/getSimilarTvPlayList";

    /**
     * 获取相似长视频列表
     */
    public final static String GET_SIMILAR_LONG_VIDEOS_LIST = BASE_URL + "/longVideo/getSimilarLongVideosList";
    /**
     * 根据类型获取标签
     */
    public final static String GET_VIP_TYPE_LIST = BASE_URL + "/longVideo/getTagsListByType";
    /**
     * 根据标签获取vip长视频列表
     */
    public final static String GET_VIP_LIST_BY_TAG = BASE_URL + "/longVideo/getVipLongVideosListbyTag";

    /**
     * 获取sts,一般2个小时过期，过期需要重新请求
     */
    public final static String GET_LONGVIDEO_STS = BASE_URL + "/demo/getSts";
    /**
    *更新版本
    */
    public final static String GET_UPDATE_VERSION = BASE_URL + "/tool/getToolKit";
}
