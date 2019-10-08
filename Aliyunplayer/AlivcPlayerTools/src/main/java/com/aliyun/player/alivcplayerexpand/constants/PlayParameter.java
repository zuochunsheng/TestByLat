package com.aliyun.player.alivcplayerexpand.constants;


import com.aliyun.player.alivcplayerexpand.playlist.AlivcVideoInfo;

/**
 * 播放参数, 包含:
 * vid, vidSts, akId, akSecre, scuToken
 */
public class PlayParameter {


    /**
     * type, 用于区分播放类型, 默认为vidsts播放
     * vidsts: vid类型
     * localSource: url类型
     */
    public static String PLAY_PARAM_TYPE = "vidsts";

    private static final String PLAY_PARAM_VID_DEFAULT = "9fb028c29acb421cb634c77cf4ebe078";
    /**
     * vid, 初始为: 9fb028c29acb421cb634c77cf4ebe078
     */
    public static String PLAY_PARAM_VID = "";

    public static String PLAY_PARAM_REGION = "cn-shanghai";

    /**
     * akId
     */
    public static String PLAY_PARAM_AK_ID = "";

    /**
     * akSecre
     */
    public static String PLAY_PARAM_AK_SECRE = "";

    /**
     * scuToken
     */
    public static String PLAY_PARAM_SCU_TOKEN = "";

    /**
     * url类型的播放地址, 初始为:http://player.alicdn.com/video/aliyunmedia.mp4
     */
    public static String PLAY_PARAM_URL = "http://player.alicdn.com/video/aliyunmedia.mp4";

    /**
     * 视频可展示的功能
     */
//    public static AlivcVideoInfo.VIDEO_FUNCTION VIDEO_FUNCTION = AlivcVideoInfo.VIDEO_FUNCTION.NONE;


    /**********************************************播放器能力开关****************************************************************/

    /**
     * 是否vip用户
     */
//    public static boolean IS_USER_VIP = false;

    /**
     * 非vip用户观看非vip视频，如果视频支持视频广告就放视频广告，否则默认用图片广告
     */
    public static boolean IS_VIDEO = false;

    /**
     * 非vip用户观看vip视频，先展示视频广告，展示之后展示试看功能，暂停的时候展示图片广告
     */
    public static boolean IS_PICTRUE = false;
    /**
     * 非vip用户观看vip视频，先展示视频广告，展示之后展示试看功能，暂停的时候展示图片广告
     */
    public static boolean IS_TRAILER = false;
    /**
     * 是否开启弹幕
     * 弹幕默认开启
     */
    public static boolean IS_BARRAGE = true;

    /**
     * 是否开启水印
     */
    public static boolean IS_WATERMARK = false;

    /**
     * 是否开启跑马灯
     */
    public static boolean IS_MARQUEE = false;

}
