package com.android.superplayer.eventbus;


/**
 * @author Xyjian
 * @Description: TODO(){放  index的常量类，便于统一管理，也不容易混淆}
 * @date: 2018/4/20 9:08
 */
public class EBConst {
    public static final int mainactivity_change_fragment = 1000000;
    public static final int mainactivity_change_other = 1000001;
    public static final int change_song_stop_play = 1000002;
    public static final int change_song_next = 1000003;
    public static final int change_song_last = 1000004;
    public static final int change_song_fast_forward = 1000005;
    public static final int change_song_fast_back = 1000006;
    public static final int change_song_set_progress = 1000007;
    public static final int change_play_list = 1000008;
    public static final int change_playing_status = 1000009;


    public static final int download_all_download = 1000015;//已经全部下载完成了
    public static final int change_progress_bar_type = 1000016;//切换了 进度章节信息

    public static final int load_loacal_data_complete = 1000018;//加载完缓存的数据

    //Login out
    public static final int login_out_index_all = 1000011;//退出登录 发出的通知
    //Login in
    //登陆的index 根据index 发送消息回调方便做登陆后的后续工作
    public static final int login_index_main = 1000010;//首页登陆
    public static final int login_index_topic = 1000012;//专题或者频道详情
    public static final int login_index_search = 1000013;//搜索
    public static final int login_index_book_detail = 1000014;//详情
    public static final int login_index_new_login = 1000017;//新的登录



    public static final int app_start = 1000021;//应用 启动
    public static final int app_stop = 1000022;//应用 退回到后台
    public static final int app_destroy = 1000023;//应用销毁


}
