package com.android.superplayer.ui.activity.media;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.aliyun.player.IPlayer;
import com.aliyun.player.alivcplayerexpand.bean.DotBean;
import com.aliyun.player.alivcplayerexpand.bean.LongVideoBean;
import com.aliyun.player.alivcplayerexpand.constants.PlayParameter;
import com.aliyun.player.alivcplayerexpand.listener.OnScreenCostingSingleTagListener;
import com.aliyun.player.alivcplayerexpand.util.AliyunScreenMode;
import com.aliyun.player.alivcplayerexpand.util.NetWatchdog;
import com.aliyun.player.alivcplayerexpand.util.ScreenUtils;
import com.aliyun.player.alivcplayerexpand.util.database.LongVideoDatabaseManager;
import com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadInfoListener;
import com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager;
import com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadMediaInfo;
import com.aliyun.player.alivcplayerexpand.view.choice.AlivcShowMoreDialog;
import com.aliyun.player.alivcplayerexpand.view.control.ControlView;
import com.aliyun.player.alivcplayerexpand.view.dlna.callback.OnDeviceItemClickListener;
import com.aliyun.player.alivcplayerexpand.view.dot.AlivcDotMsgDialogFragment;
import com.aliyun.player.alivcplayerexpand.view.dot.DotView;
import com.aliyun.player.alivcplayerexpand.view.gesturedialog.BrightnessDialog;
import com.aliyun.player.alivcplayerexpand.view.more.AliyunShowMoreValue;
import com.aliyun.player.alivcplayerexpand.view.more.DanmakuSettingView;
import com.aliyun.player.alivcplayerexpand.view.more.ScreenCostView;
import com.aliyun.player.alivcplayerexpand.view.more.ShowMoreView;
import com.aliyun.player.alivcplayerexpand.view.more.SpeedValue;
import com.aliyun.player.alivcplayerexpand.view.quality.QualityItem;
import com.aliyun.player.alivcplayerexpand.view.softinput.SoftInputDialogFragment;
import com.aliyun.player.alivcplayerexpand.view.tipsview.TrailersView;
import com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView;
import com.aliyun.player.bean.ErrorCode;
import com.aliyun.player.bean.InfoBean;
import com.aliyun.player.bean.InfoCode;
import com.aliyun.player.nativeclass.MediaInfo;
import com.aliyun.player.source.UrlSource;
import com.aliyun.player.source.VidSts;
import com.aliyun.svideo.common.base.AlivcListSelectorDialogFragment;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.aliyun.svideo.common.okhttp.AlivcOkHttpClient;
import com.aliyun.svideo.common.utils.ToastUtils;
import com.aliyun.utils.VcPlayerLog;
import com.android.superplayer.R;
import com.android.superplayer.adapter.AlivcPlayerSimilarQuickAdapter;
import com.android.superplayer.bean.LongVideoStsBean;
import com.android.superplayer.bean.PlayerSimilarSectionBean;
import com.android.superplayer.bean.SimilarVideoBean;
import com.android.superplayer.ui.widgit.AlivcShareDialogFragment;
import com.android.superplayer.util.SettingSpUtils;
import com.android.superplayer.util.UserSpUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

import static com.android.superplayer.util.GlobalNetConstants.GET_LONGVIDEO_STS;
import static com.android.superplayer.util.GlobalNetConstants.GET_SERIES_INFO_BY_ID;
import static com.android.superplayer.util.GlobalNetConstants.GET_SIMILAR_LONG_VIDEOS_LIST;
import static com.android.superplayer.util.GlobalNetConstants.GET_SIMILAR_VIDEOS_LIST;
import static com.android.superplayer.util.GlobalNetConstants.GET_VIDEOS_LIST_BY_ID;

public class AlivcPlayerActivity extends AppCompatActivity implements AliyunDownloadInfoListener {


    public static final int ALL_SERIES_REQUEST_CODE = 0x0001;

    public static final String INTENT_PLAY_MEDIA = "intent_play_media";
    public static final String INTENT_SERIES_VIDEOS = "intent_series_videos";
    public static final String INTENT_SERIES_TV_COVERURL = "intent_series_tv_coverurl";
    public static final String INTENT_CURRENT_LONG_VIDEO_INFO = "intent_current_long_video_bean";
    public static final String INTENT_CURRENT_VIDEO_VOD = "intent_current_video_vod";
    public static final String INTENT_CURRENT_PLAYING_POSITION = "intent_current_playing_position";


    /**
     * 开启Activity
     *
     * @param longVideoBean 播放实体类
     */
    public static void startAlivcPlayerActivity(Context context, LongVideoBean longVideoBean) {
        Intent intent = new Intent(context, AlivcPlayerActivity.class);
        intent.putExtra(INTENT_PLAY_MEDIA, longVideoBean);
        context.startActivity(intent);
    }

    /**
     * 下载管理类
     */
    private AliyunDownloadManager mAliyunDownloadManager;
    /**
     * 播放视频的实体类
     */
    private LongVideoBean mLongVideoBean;

    /**
     * 当前正在播放的集数
     */
    private String mCurrentEpisode = "";

    /**
     * 播放器的View
     */
    private AliyunVodPlayerView mAliyunVodPlayerView;
    /**
     * 下载,分享
     */
    private ImageView mDownloadImageView, mShareImageView;
    /**
     * 系列的集数,片花咨询和猜你喜欢
     */
    private RecyclerView mSeriesEpisodeRecyclerView, mPlayerSimilarRecyclerView;
    /**
     * 全部剧集,视频标题,VIP标识
     */
    private TextView mSeriesExpisodeAllTextView, mVideoTitleTextView, mVipTextView;
    /**
     * 剧集根布局
     */
    private RelativeLayout mSeriesRootRelativeLayout;
    /**
     * 剧集Adapter
     */
    //private AlivcSeriesPlayerEpisodeQuickAdapter mAlivcSeriesPlayerEpisodeQuickAdapter;
    /**
     * 片花咨询,猜你喜欢 Adapter
     */
    private AlivcPlayerSimilarQuickAdapter mAlivcPlayerSimilarQuickAdapter;
    /**
     * Vid,TvId,视频标题,封面,剧集封面,sp默认清晰度
     */
    private String mVid, mTvId, mVideoTitle, mCoverUrl, mTvCoverUrl, mSettingSpUtilsVideoQuantity;
    /**
     * 剧集List
     */
    private ArrayList<LongVideoBean> mSeriesVideoList;
    /**
     * sts实体类
     */
    private LongVideoStsBean.DataBean stsInfoBean;
    /**
     * 长视频观看历史缓存
     */
    private LongVideoDatabaseManager mLongVideoDatabaseManager;
    /**
     * 判断是不是单集，系列，vip，本地视频，sp是否是vip，sp默认清晰度，sp是否开启硬解，sp 4g下载是否打开,sp是否运营商自动运行
     */
    private boolean mIsSingle, mIsSerier, mIsVip, mIsLocal, mSettingSpUtilsVip,
            mSettingSpUtilsHardDecoding, mSettingSpUtilsOperatorDownload, mSettingSpUtilsOperatorPlay;

    /**
     * 设置sp工具类
     */
    private SettingSpUtils mSettingSpUtils;
    /**
     * 打点信息DialogFragment
     */
    private AlivcDotMsgDialogFragment mAlivcDotMsgDialogFragment;

    /**
     * 防止快速点击
     */
    private long oldTime;

    /**
     * 全屏展示更多Dialog,弹幕设置Dialog
     */
    private AlivcShowMoreDialog showMoreDialog, danmakuShowMoreDialog;
    /**
     * 投屏选择Dialog
     */
    private AlivcShowMoreDialog screenShowMoreDialog;

    private ScreenCostView mScreenCostView;
    /**
     * 弹幕透明度、显示区域、速率progress
     */
    private int mAlphProgress = 0, mRegionProgress = 0, mSpeedProgress = 30;

    /**
     * 是否正在加载下载信息
     */
    private boolean mIsLoadDownloadInfo = false;

    /**
     * 当前正在播放视频的所有清晰度
     */
    private ArrayList<String> mCurrentVideoVodDefinition = new ArrayList<>();

    /**
     * 点击发送弹幕的画笔弹出的dialog
     */
    private SoftInputDialogFragment mSoftInputDialogFragment;
    /**
     * 猜你喜欢
     */
    private List<LongVideoBean> mSimilarVideoList;
    /**
     * VidSts
     */
    private VidSts mVidSts;
    /**
     * 猜你喜欢当前播放的position
     */
    private int mCurrentSimilarVideoPosition = 0;
    /**
     * loading进度
     */
    private ProgressBar mLoadingProgressBar;
    /**
     * 是否需要刷新播放界面底部猜你喜欢视频列表,默认是需要刷新
     */
    private boolean mNeedToRefreshVideoLists = true;
    /**
     * 猜你喜欢视频列表
     */
    private List<PlayerSimilarSectionBean> mSectionEntities = new ArrayList<>();
    /**
     * 当前视频
     */
    private PlayerSimilarSectionBean mCurrentLongVideoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alivc_player);


        mLongVideoBean = (LongVideoBean) getIntent().getSerializableExtra(INTENT_PLAY_MEDIA);
        mAliyunDownloadManager = AliyunDownloadManager.getInstance(getApplicationContext());
        mLongVideoDatabaseManager = LongVideoDatabaseManager.getInstance();
        mSettingSpUtils = new SettingSpUtils.Builder(this).create();
        initSetting();

        initView();
        initSeriseRecyclerView();
        initSimlarRecyclerView();

        initListener();
        initData();

        if ((!mIsSerier || !TextUtils.isEmpty(mVid)) && !mIsLocal) {
            //如果VideoType是系列,则传递过来的vid是不存在的,则需要根据tvId,重新请求改系列下的所有视频,再进行播放
            requestSts(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initSetting();
        if (mAliyunDownloadManager != null) {
            mAliyunDownloadManager.addDownloadInfoListener(this);
        }

        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.onResume();
            mAliyunVodPlayerView.setOperatorPlay(mSettingSpUtilsOperatorPlay);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.pause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.onStop();
        }
        if (mAliyunDownloadManager != null) {
            mAliyunDownloadManager.removeDownloadInfoListener(this);
        }
    }

    @Override
    protected void onDestroy() {
        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.onDestroy();
            mAliyunVodPlayerView = null;
        }
        if (mScreenCostView != null) {
            mScreenCostView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.setMultiWindow(isInMultiWindowMode);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (mAliyunVodPlayerView != null && mAliyunVodPlayerView.getIsCreenCosting()) {
                    int screenCostingVolume = mAliyunVodPlayerView.getScreenCostingVolume() + 5;
                    mAliyunVodPlayerView.setScreenCostingVolume(screenCostingVolume);
                    return true;
                }
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (mAliyunVodPlayerView != null && mAliyunVodPlayerView.getIsCreenCosting()) {
                    int screenCostingVolume = mAliyunVodPlayerView.getScreenCostingVolume() - 5;
                    mAliyunVodPlayerView.setScreenCostingVolume(screenCostingVolume);
                    return true;
                }
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 初始化数据
     */
    private void initData() {
        mCoverUrl = mLongVideoBean.getCoverUrl();
        mVideoTitle = mLongVideoBean.getTitle();
        judgeVideoType();
        List<DotBean> dotList = mLongVideoBean.getDot();
        if (dotList != null && dotList.size() > 0) {
            mAliyunVodPlayerView.setDotInfo(dotList);
        }
        mVid = mLongVideoBean.getVideoId();
        if(mCurrentLongVideoBean == null){
            mCurrentLongVideoBean = new PlayerSimilarSectionBean(mLongVideoBean);
        }else{
            mCurrentLongVideoBean.setT(mLongVideoBean);
        }
        if (mIsLocal) {
            //本地
            judgeVideoPlayerType();
            UrlSource urlSource = new UrlSource();
            urlSource.setUri(mLongVideoBean.getSaveUrl());
            mAliyunVodPlayerView.setLocalSource(urlSource);
            mAliyunVodPlayerView.changeScreenMode(AliyunScreenMode.Full, false);
        } else {
            if (mIsSingle) {
                //单集
                getSimilarLongVideoList();
            } else if (mIsSerier) {
                //系列
                mTvId = mLongVideoBean.getTvId();
                //initSeriesInfo();
                //initSerierData();
                getSimilarLongVideoList();
            } else {
                //VIP
                mVid = mLongVideoBean.getVideoId();
                getSimilarLongVideoList();
            }
        }

        refreshView();
    }

    private void refreshView(){
        mSeriesRootRelativeLayout.setVisibility(mIsSerier ? View.VISIBLE : View.GONE);
        mVipTextView.setVisibility(mIsVip ? View.VISIBLE : View.GONE);
        mVideoTitleTextView.setText(mVideoTitle);
        mAliyunVodPlayerView.setCoverUri(mCoverUrl);
        mAliyunVodPlayerView.clearFrameWhenStop(true);
    }


    /**
     * 初始化设置界面的信息
     */
    private void initSetting() {
        mSettingSpUtilsVip = mSettingSpUtils.getVip();
        mSettingSpUtilsHardDecoding = mSettingSpUtils.getHardDecoding();
        mSettingSpUtilsVideoQuantity = mSettingSpUtils.getVideoQuantity();
        mSettingSpUtilsOperatorPlay = mSettingSpUtils.getOperatorPlay();
        mSettingSpUtilsOperatorDownload = mSettingSpUtils.getOperatorDownload();
    }

    private void initView() {
        mVipTextView = findViewById(R.id.tv_vip);
        mShareImageView = findViewById(R.id.iv_share);
        mDownloadImageView = findViewById(R.id.iv_download);
        mAliyunVodPlayerView = findViewById(R.id.video_view);
        mVideoTitleTextView = findViewById(R.id.tv_video_title);
        mSeriesRootRelativeLayout = findViewById(R.id.rl_series);
        mLoadingProgressBar = findViewById(R.id.loading_progress);
        mSeriesExpisodeAllTextView = findViewById(R.id.tv_series_episode_all);
        mSeriesEpisodeRecyclerView = findViewById(R.id.recyclerview_series_episode);
        mPlayerSimilarRecyclerView = findViewById(R.id.recyclerview_player_similar);

        //保持屏幕敞亮
        mAliyunVodPlayerView.setKeepScreenOn(true);
        //设置硬解码开关
        mAliyunVodPlayerView.setEnableHardwareDecoder(mSettingSpUtilsHardDecoding);

        initScreenView();
        initSoftDialogFragment();

    }
    /**
     * 初始化投屏view
     */
    private void initScreenView() {
        mScreenCostView = new ScreenCostView(this);
        screenShowMoreDialog = new AlivcShowMoreDialog(this);
        screenShowMoreDialog.setContentView(mScreenCostView);
        mScreenCostView.setOnDeviceItemClickListener(new OnDeviceItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (mAliyunVodPlayerView != null) {
                    mAliyunVodPlayerView.screenCostPlay();
                }
                if (screenShowMoreDialog != null) {
                    screenShowMoreDialog.dismiss();
                }
            }
        });
    }

    private void initSoftDialogFragment() {
        mSoftInputDialogFragment = SoftInputDialogFragment.newInstance();
        mSoftInputDialogFragment.setOnBarrageSendClickListener(new SoftInputDialogFragment.OnBarrageSendClickListener() {
            @Override
            public void onBarrageSendClick(String danmu) {
                if (mAliyunVodPlayerView != null) {
                    mAliyunVodPlayerView.setmDanmaku(danmu);
                    mSoftInputDialogFragment.dismiss();
                }
            }
        });
    }


    private void initListener() {
        mDownloadImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentVideoVodDefinition != null) {
                    mCurrentVideoVodDefinition.clear();
                }

                //4g网络下，4g下载设置关闭，则不会开始下载
                boolean is4gConnected = NetWatchdog.is4GConnected(getApplicationContext());
                if (is4gConnected && !mSettingSpUtilsOperatorDownload) {
                    ToastUtils.show(AlivcPlayerActivity.this, getString(R.string.alivc_longvideo_cache_toast_4g));
                    return;
                }

                if (mIsVip && !mSettingSpUtilsVip) {
                    //非vip无法缓存vip视频
                    ToastUtils.show(AlivcPlayerActivity.this, getString(R.string.alivc_longvideo_player_vip_cache_toast));
                } else {
                    mLoadingProgressBar.setVisibility(View.VISIBLE);
                    downloadVideo();
                }
            }
        });

        //全部剧集
        mSeriesExpisodeAllTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(AlivcPlayerActivity.this, AlivcAllSeriesActivity.class);
////                intent.putExtra(INTENT_SERIES_VIDEOS, mSeriesVideoList);
////                intent.putExtra(INTENT_CURRENT_PLAYING_POSITION, mCurrentEpisode);
////                startActivityForResult(intent, ALL_SERIES_REQUEST_CODE);
            }
        });

        //播放界面剧集item点击回调
//        mAlivcSeriesPlayerEpisodeQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                mLongVideoBean = (LongVideoBean) adapter.getData().get(position);
//                refreshActivity();
//            }
//        });

        //分享
        mShareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlivcShareDialogFragment alivcVideoShareDialogFragment = AlivcShareDialogFragment.newInstance();
                alivcVideoShareDialogFragment.show(getSupportFragmentManager(), "AlivcShareDialogFragment");

            }
        });

        //播放完成监听
        mAliyunVodPlayerView.setOnPreparedListener(new PlayerPreparedListener(this));
        mAliyunVodPlayerView.setOnCompletionListener(new PlayerCompletionListener(this));

        mAliyunVodPlayerView.setOnInfoListener(new PlayerInfoListener(this));

        mAliyunVodPlayerView.setOnDotViewClickListener(new PlayerDotViewClickListener(this));

        mAliyunVodPlayerView.setOnControlViewHideListener(new PlayerControlViewHideListener(this));
        //屏幕切换
        mAliyunVodPlayerView.setOrientationChangeListener(new PlayerOrientationChangeListner(this));
        mAliyunVodPlayerView.setOnTrailerViewClickListener(new PlayerTrailerViewClickListener(this));
        mAliyunVodPlayerView.setOnScreenBrightness(new PlayerControlViewScreenBrightnessListener(this));
        //ControlView 横屏展示更多
        mAliyunVodPlayerView.setOnShowMoreClickListener(new PlayerControlViewShowMoreClickListener(this));
        //投屏状态下的单击事件
        mAliyunVodPlayerView.setOnScreenCostingSingleTagListener(new MyOnScreenCostingSingleTagListener(this));
        //广告点击事件 退出页面
        mAliyunVodPlayerView.setOnFinishListener(new PlayerControlViewFinishListener(this));
        //发送弹幕
        mAliyunVodPlayerView.setSoftKeyHideListener(new MynewOnSoftKeyHideListener(this));
        //投屏时,视频播放完成回调
        mAliyunVodPlayerView.setOnScreenCostingVideoCompletionListener(new OnScreenCostingVideoCompletionListener(this));

        mAliyunVodPlayerView.setScreenBrightness(BrightnessDialog.getActivityBrightness(AlivcPlayerActivity.this));
    }

    /**
     * 判断Video类型
     */
    private void judgeVideoType() {
        if (mLongVideoBean != null) {
            mIsLocal = !TextUtils.isEmpty(mLongVideoBean.getSaveUrl());
            if (mIsLocal) {
                mIsVip = false;
                mIsSerier = false;
            } else {
                mIsSingle = TextUtils.isEmpty(mLongVideoBean.getTvId());
                mIsSerier = !TextUtils.isEmpty(mLongVideoBean.getTvId());
                mIsVip = mLongVideoBean.getIsVip();
            }

        }
    }

    /**
     * 图片广告，视频广告与是否vip之间的逻辑梳理
     * 1.Vip用户所有视频不展示 视频广告，图片广告
     * 2.非vip用户观看非vip视频，如果视频支持视频广告就放视频广告，否则默认用图片广告
     * 3.非vip用户观看vip视频，先展示视频广告，展示之后展示试看功能，暂停的时候展示图片广告
     * 弹幕,跑马灯,水印功能出现逻辑梳理：
     * 弹幕,跑马灯,水印功能与是否VIP没有任何关系
     * 1.弹幕存在所有的视频当中
     *  2.跑马灯出现逻辑：当视频标题含有“跑马灯”且含有“阿里云演示”字样时
     * 3.水印出现逻辑：当视频标题含有“水印”且含有“阿里云演示”字样时
     * 出现视频广告的条件是：标题包含'阿里云演示' 字符
     * 跑马灯，水印，出现的逻辑跟视频广告一致
     */
    private void judgeVideoPlayerType() {
        if (!mIsLocal) {
            if (mIsVip) {
                //vip视频,根据设置vip的开关,判断是否需要试看，Vip用户所有视频不展示 视频广告，图片广告
                if (mSettingSpUtilsVip) {

                    PlayParameter.IS_VIDEO = false;
                    PlayParameter.IS_TRAILER = false;
                    PlayParameter.IS_PICTRUE = false;

                    //非vip用户观看vip视频，先展示视频广告，展示之后展示试看功能，暂停的时候展示图片广告
                } else {
                    //满足视频广告和试看,不满足的话展示图片广告和试看
                    PlayParameter.IS_VIDEO = true;
                    PlayParameter.IS_TRAILER = true;
                    PlayParameter.IS_PICTRUE = false;
                }
                //非vip用户观看非vip视频，如果视频支持视频广告就放视频广告，否则默认用图片广告
            } else {
                //非vip视频,图片广告
                if (mSettingSpUtilsVip) {
                    PlayParameter.IS_VIDEO = false;
                    PlayParameter.IS_TRAILER = false;
                    PlayParameter.IS_PICTRUE = false;
                } else {
                    //包含字符串，那么播放视频广告，不包含字符串，播放图片广告
                    PlayParameter.IS_PICTRUE = true;
                    PlayParameter.IS_TRAILER = false;
                    PlayParameter.IS_VIDEO = false;

                }
            }
            //显示跑马灯
            PlayParameter.IS_MARQUEE = isTitleContainMarquee();
            //显示水印
            PlayParameter.IS_WATERMARK = isTitleContainWaterMark();
        } else {
            //本地视频
            PlayParameter.IS_VIDEO = false;
            PlayParameter.IS_TRAILER = false;
            PlayParameter.IS_PICTRUE = false;
        }
    }


    /**
     * 判断标题是否存在，阿里云演示字符串
     */
    private boolean isTitleContain() {
        if (TextUtils.isEmpty(mVideoTitle)) {
            return false;
        }
        int result = mVideoTitle.indexOf("阿里云演示");
        return result != -1;
    }

    /**
     * 判断标题是否存在，跑马灯并且含有阿里云演示字符串
     */
    private boolean isTitleContainMarquee() {
        if (TextUtils.isEmpty(mVideoTitle)) {
            return false;
        }
        int result = mVideoTitle.indexOf("阿里云演示");
        int result1 = mVideoTitle.indexOf("跑马灯");
        return result != -1 && result1 != -1;
    }

    /**
     * 判断标题是否存在，水印并且含有阿里云演示字符串
     */
    private boolean isTitleContainWaterMark() {
        if (TextUtils.isEmpty(mVideoTitle)) {
            return false;
        }
        int result = mVideoTitle.indexOf("阿里云演示");
        int result1 = mVideoTitle.indexOf("水印");
        return result != -1 && result1 != -1;
    }

    /**
     * 请求sts信息
     */
    private void requestSts(final boolean isDownload) {
        AlivcOkHttpClient.getInstance().get(GET_LONGVIDEO_STS, new AlivcOkHttpClient.HttpCallBack() {

            @Override
            public void onError(Request request, IOException e) {
                mIsLoadDownloadInfo = false;
                ToastUtils.show(AlivcPlayerActivity.this, e.getMessage());
            }

            @Override
            public void onSuccess(Request request, String result) {
                Gson gson = new Gson();
                LongVideoStsBean longVideoStsBean = gson.fromJson(result, LongVideoStsBean.class);
                stsInfoBean = longVideoStsBean.getData();
                mVidSts = new VidSts();
                mVidSts.setVid(mVid);
                mVidSts.setAccessKeyId(stsInfoBean.getAccessKeyId());
                mVidSts.setSecurityToken(stsInfoBean.getSecurityToken());
                mVidSts.setAccessKeySecret(stsInfoBean.getAccessKeySecret());
                if (isDownload) {
                    mAliyunDownloadManager.prepareDownload(mVidSts);
                } else {
                    mAliyunVodPlayerView.setAutoPlay(!mAliyunVodPlayerView.getIsCreenCosting());
                    judgeVideoPlayerType();
                    int watchPercent = mLongVideoBean.getWatchPercent();
                    if (watchPercent < 100 && !TextUtils.isEmpty(mLongVideoBean.getWatchDuration())) {
                        mAliyunVodPlayerView.seekTo(Integer.valueOf(mLongVideoBean.getWatchDuration()));
                    }
                    //在准备视频之前，需要先设置功能
                    mAliyunVodPlayerView.setVidSts(mVidSts);
                }
            }
        });
    }

    /**
     * 初始化系列的每一集
     */
    private void initSeriseRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mSeriesEpisodeRecyclerView.setLayoutManager(linearLayoutManager);

        //mAlivcSeriesPlayerEpisodeQuickAdapter = new AlivcSeriesPlayerEpisodeQuickAdapter(R.layout.alivc_long_video_series_player_episode_item, mCurrentEpisode);
        //mSeriesEpisodeRecyclerView.setAdapter(mAlivcSeriesPlayerEpisodeQuickAdapter);
    }

    /**
     * 片花咨询，猜你喜欢
     */
    private void initSimlarRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mPlayerSimilarRecyclerView.setLayoutManager(linearLayoutManager);

        mAlivcPlayerSimilarQuickAdapter = new AlivcPlayerSimilarQuickAdapter(R.layout.alivc_long_video_player_similar_item, R.layout.alivc_rv_header_item);
        mPlayerSimilarRecyclerView.setAdapter(mAlivcPlayerSimilarQuickAdapter);
    }

    private void refreshActivity() {
        mNeedToRefreshVideoLists = false;
        initData();
        requestSts(false);
        mCurrentEpisode = mLongVideoBean.getSort();
//        if (mAlivcSeriesPlayerEpisodeQuickAdapter != null) {
//            mAlivcSeriesPlayerEpisodeQuickAdapter.setCurrentEpisode(mCurrentEpisode);
//            mAlivcSeriesPlayerEpisodeQuickAdapter.notifyDataSetChanged();
//        }
    }

    /**
     * 下载视频
     */
    private void downloadVideo() {
        if (mIsSingle) {
            //单集
            if (!mIsLoadDownloadInfo) {
                mIsLoadDownloadInfo = true;
                if (mVidSts == null) {
                    downloadVideoSingle();
                } else {
                    mAliyunDownloadManager.prepareDownload(mVidSts);
                }
            }

        } else if (mIsSerier) {
            //系列
            if (!mIsLoadDownloadInfo) {
                mIsLoadDownloadInfo = true;
                if (mVidSts == null) {
                    downloadVideoSeries();
                } else {
                    mAliyunDownloadManager.prepareDownload(mVidSts);
                }
            }

        } else {
            //本地无法下载
            ToastUtils.show(AlivcPlayerActivity.this, getString(R.string.alivc_longvideo_player_loacl_cache_toast));
        }
    }

    /**
     * 下载单集
     */
    private void downloadVideoSingle() {
        requestSts(true);
    }

    /**
     * 下载系列
     */
    private void downloadVideoSeries() {
        requestSts(true);
    }

    /**
     * 初始化剧集信息
     */
//    private void initSeriesInfo() {
//        HashMap<String, String> params = new HashMap<>();
//        params.put("token", new UserSpUtils.Builder(this).create().getUserToken());
//        params.put("tvId", mTvId);
//        AlivcOkHttpClient.getInstance().get(GET_SERIES_INFO_BY_ID, params, new AlivcOkHttpClient.HttpCallBack() {
//            @Override
//            public void onError(Request request, IOException e) {
//                ToastUtils.show(AlivcPlayerActivity.this, e.getMessage());
//            }
//
//            @Override
//            public void onSuccess(Request request, String result) {
//                Gson gson = new Gson();
//                SeriesInfoBean seriesInfoBean = gson.fromJson(result, SeriesInfoBean.class);
//                if (seriesInfoBean != null && seriesInfoBean.getData() != null) {
//                    List<LongVideoBean> tvPlayList = seriesInfoBean.getData().getTvPlayList();
//                    if (tvPlayList != null && tvPlayList.size() > 0) {
//                        mTvCoverUrl = tvPlayList.get(0).getCoverUrl();
//                    }
//                }
//
//            }
//        });
//    }

    /**
     * 初始化剧集系列
     */
//    private void initSerierData() {
//        HashMap<String, String> params = new HashMap<>();
//        params.put("token", new UserSpUtils.Builder(this).create().getUserToken());
//        params.put("tvId", mTvId);
//        AlivcOkHttpClient.getInstance().get(GET_VIDEOS_LIST_BY_ID, params, new AlivcOkHttpClient.HttpCallBack() {
//
//            @Override
//            public void onError(Request request, IOException e) {
//                ToastUtils.show(AlivcPlayerActivity.this, e.getMessage());
//            }
//
//            @Override
//            public void onSuccess(Request request, String result) {
//                Gson gson = new Gson();
//                SimilarVideoBean similarVideoBean = gson.fromJson(result, SimilarVideoBean.class);
//                if (similarVideoBean.getData() == null) {
//                    return;
//                }
//                int total = similarVideoBean.getData().getTotal();
//                mSeriesExpisodeAllTextView.setText(String.format(getResources().getString(R.string.alivc_longvideo_series_all_number_list), total));
//                mSeriesVideoList = (ArrayList<LongVideoBean>) similarVideoBean.getData().getVideoList();
//                if (mSeriesVideoList == null || mSeriesVideoList.size() == 0) {
//                    return;
//                }
//                /*
//                    如果是系列,并且vid不是空,则进入播放详情界面直接播放,并且只刷新下面部分的列表
//                    如果是系列,并且vid是空,则进入播放详情界面,需要根据tvid请求一次数据,然后取系列的第0个数据开始播放
//                 */
//                if (!TextUtils.isEmpty(mVid)) {
//                    mCurrentEpisode = mLongVideoBean.getSort();
//                    mAlivcSeriesPlayerEpisodeQuickAdapter.setCurrentEpisode(mCurrentEpisode);
//                    mAlivcSeriesPlayerEpisodeQuickAdapter.setNewData(mSeriesVideoList);
//                    return;
//                }
//                if (TextUtils.isEmpty(mCurrentEpisode)) {
//                    mLongVideoBean = mSeriesVideoList.get(0);
//                } else {
//                    for (LongVideoBean videoBean : mSeriesVideoList) {
//                        if (videoBean.getSort().equals(mCurrentEpisode)) {
//                            mLongVideoBean = videoBean;
//                            break;
//                        }
//                    }
//                }
//                if (mLongVideoBean == null) {
//                    return;
//                }
//
//                mVid = mLongVideoBean.getVideoId();
//                mCurrentEpisode = mLongVideoBean.getSort();
//                mAlivcSeriesPlayerEpisodeQuickAdapter.setCurrentEpisode(mCurrentEpisode);
//                mAlivcSeriesPlayerEpisodeQuickAdapter.setNewData(mSeriesVideoList);
//                judgeVideoType();
//                refreshView();
//                requestSts(false);
//            }
//        });
//    }

    /**
     * 获取相似视频列表
     */
    private void getSimilarLongVideoList() {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", new UserSpUtils.Builder(this).create().getUserToken());
        String requestUrl = "";
        if (mIsSerier) {
            //系列
            params.put("tvId", mTvId);
            requestUrl = GET_SIMILAR_VIDEOS_LIST;
        } else {
            params.put("videoId", mVid);
            requestUrl = GET_SIMILAR_LONG_VIDEOS_LIST;
        }
        AlivcOkHttpClient.getInstance().get(requestUrl, params, new AlivcOkHttpClient.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                ToastUtils.show(AlivcPlayerActivity.this, e.getMessage());
            }

            @Override
            public void onSuccess(Request request, String result) {
                Gson gson = new Gson();
                SimilarVideoBean similarVideoBean = gson.fromJson(result, SimilarVideoBean.class);
                mSimilarVideoList = new ArrayList<>();
                if (mIsSerier) {
                    mSimilarVideoList = similarVideoBean.getData().getTvPlayList();
                } else {
                    mSimilarVideoList = similarVideoBean.getData().getVideoList();
                }

                if (mSimilarVideoList == null) {
                    mSimilarVideoList = new ArrayList<>();
                }
                //当首次进入播放界面的时候,请求猜你喜欢视频列表,否则不会更新猜你喜欢视频列表
                if(mNeedToRefreshVideoLists){
                    //当前视频header
                    mSectionEntities.add(new PlayerSimilarSectionBean(true, getResources().getString(R.string.alivc_longvideo_play_consult)));
                    //当前视频item 展示当前的视频
                    mSectionEntities.add(mCurrentLongVideoBean);
                    //猜你喜欢 header
                    mSectionEntities.add(new PlayerSimilarSectionBean(true, getResources().getString(R.string.alivc_longvideo_play_similar_videolist)));
                    //猜你喜欢 item
                    for (LongVideoBean longVideoBean : mSimilarVideoList) {
                        mSectionEntities.add(new PlayerSimilarSectionBean(longVideoBean));
                    }
                    mAlivcPlayerSimilarQuickAdapter.setNewData(mSectionEntities);
                }
                mAlivcPlayerSimilarQuickAdapter.notifyDataSetChanged();

                mAlivcPlayerSimilarQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                        PlayerSimilarSectionBean playerSimilarSectionBean = (PlayerSimilarSectionBean) adapter.getItem(position);
                        if (playerSimilarSectionBean == null || playerSimilarSectionBean.isHeader) {
                            return;
                        }
                        mLongVideoBean = playerSimilarSectionBean.t;
                        refreshActivity();
                    }
                });

            }
        });
    }


    protected boolean isStrangePhone() {
        //隐藏状态栏时异常机型处理
        boolean strangePhone = "mx5".equalsIgnoreCase(Build.DEVICE)
                || "Redmi Note2".equalsIgnoreCase(Build.DEVICE)
                || "Z00A_1".equalsIgnoreCase(Build.DEVICE)
                || "hwH60-L02".equalsIgnoreCase(Build.DEVICE)
                || "hermes".equalsIgnoreCase(Build.DEVICE)
                || ("V4".equalsIgnoreCase(Build.DEVICE) && "Meitu".equalsIgnoreCase(Build.MANUFACTURER))
                || ("m1metal".equalsIgnoreCase(Build.DEVICE) && "Meizu".equalsIgnoreCase(Build.MANUFACTURER));

        VcPlayerLog.e("lfj1115 ", " Build.Device = " + Build.DEVICE + " , isStrange = " + strangePhone);
        return strangePhone;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //旋转屏幕,需要隐藏打点的信息
        if (mAlivcDotMsgDialogFragment != null) {
            mAlivcDotMsgDialogFragment.dismiss();
        }
        if (mAliyunVodPlayerView != null) {
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                //转为竖屏了。
                //显示状态栏
                this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                mAliyunVodPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                //设置view的布局，宽高之类
                LinearLayout.LayoutParams aliVcVideoViewLayoutParams = (LinearLayout.LayoutParams) mAliyunVodPlayerView
                        .getLayoutParams();
                aliVcVideoViewLayoutParams.height = (int) (ScreenUtils.getWidth(this) * 9.0f / 16);
                aliVcVideoViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                //转到横屏了。
                //隐藏状态栏
                if (!isStrangePhone()) {
                    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    mAliyunVodPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                }
                //设置view的布局，宽高
                LinearLayout.LayoutParams aliVcVideoViewLayoutParams = (LinearLayout.LayoutParams) mAliyunVodPlayerView
                        .getLayoutParams();
                aliVcVideoViewLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                aliVcVideoViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            }
        }
    }

    /**
     * 设置屏幕亮度
     */
    private void setWindowBrightness(int brightness) {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = brightness / 255.0f;
        window.setAttributes(lp);
    }

    /**
     * 下载准备完成的回调
     */
    private void onDownloadPrepared(List<AliyunDownloadMediaInfo> infos) {
        mLoadingProgressBar.setVisibility(View.GONE);
        if (mIsSingle) {
            showVodDefinitionDialog(infos);
        } else {
            for (AliyunDownloadMediaInfo info : infos) {
                if (info != null && info.getTrackInfo() != null) {
                    mCurrentVideoVodDefinition.add(info.getTrackInfo().getVodDefinition());
                }
            }
           // AlivcSeriesCacheActivity.startAlivcSeriesCacheActivity(this, mSeriesVideoList, mCurrentEpisode, mCurrentVideoVodDefinition, mTvCoverUrl);
        }
    }

    /**
     * 展示清晰度Dialog
     */
    private void showVodDefinitionDialog(final List<AliyunDownloadMediaInfo> infos) {
        boolean is4gConnected = NetWatchdog.is4GConnected(getApplicationContext());
        if (is4gConnected) {
            ToastUtils.show(AlivcPlayerActivity.this, getString(R.string.alivc_longvideo_doawload_operator));
        }
        //数据源
        ArrayList<String> selectors = new ArrayList<>();
        for (AliyunDownloadMediaInfo info : infos) {
            info.setTvId(mLongVideoBean.getTvId());
            selectors.add(QualityItem.getItem(this, info.getQuality(), false).getName());
        }

        //清晰度DialogFragment
        AlivcListSelectorDialogFragment mAlivcListSelectorDialogFragment = new AlivcListSelectorDialogFragment.Builder(getSupportFragmentManager())
                .setGravity(Gravity.BOTTOM)
                .setCancelableOutside(true)
                .setItemColor(ContextCompat.getColor(this, R.color.alivc_common_font_red_wine))
                .setUnItemColor(ContextCompat.getColor(this, R.color.alivc_common_font_black))
                .setNewData(selectors)
                .setDialogAnimationRes(R.style.Dialog_Animation)
                .setOnListItemSelectedListener(new AlivcListSelectorDialogFragment.OnListItemSelectedListener() {
                    @Override
                    public void onClick(String position) {
                        boolean is4gConnected = NetWatchdog.is4GConnected(getApplicationContext());
                        if (is4gConnected && !mSettingSpUtilsOperatorDownload) {
                            ToastUtils.show(AlivcPlayerActivity.this, getString(R.string.alivc_longvideo_cache_toast_4g));
                            return;
                        }
                        for (AliyunDownloadMediaInfo info : infos) {
                            if (QualityItem.getItem(AlivcPlayerActivity.this, info.getQuality(), false).getName().equals(position)) {
                                mAliyunDownloadManager.startDownload(info);
                                break;
                            }
                        }
                    }
                })
                .create()
                .show();
        mAlivcListSelectorDialogFragment.setPosition(QualityItem.getItem(this, mSettingSpUtilsVideoQuantity, false).getName());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ALL_SERIES_REQUEST_CODE && resultCode != Activity.RESULT_CANCELED) {
            mLongVideoBean = (LongVideoBean) data.getSerializableExtra("result");
            refreshActivity();
        }
    }


    /**
     * ------------------------------------------下载回调 ------------------------------------------
     */
    @Override
    public void onPrepared(List<AliyunDownloadMediaInfo> infos) {
        mIsLoadDownloadInfo = false;
        onDownloadPrepared(infos);
    }

    @Override
    public void onAdd(AliyunDownloadMediaInfo info) {

    }

    @Override
    public void onStart(AliyunDownloadMediaInfo info) {
        if (!mIsSerier) {
            ToastUtils.show(AlivcPlayerActivity.this, getResources().getString(R.string.alivc_longvideo_toast_add_download));
        }
    }

    @Override
    public void onProgress(AliyunDownloadMediaInfo info, int percent) {

    }

    @Override
    public void onStop(AliyunDownloadMediaInfo info) {

    }

    @Override
    public void onCompletion(AliyunDownloadMediaInfo info) {

    }

    @Override
    public void onError(AliyunDownloadMediaInfo info, ErrorCode code, String msg, String requestId) {
        mLoadingProgressBar.setVisibility(View.GONE);
        mIsLoadDownloadInfo = false;
        if (mIsSingle) {
            ToastUtils.show(AlivcPlayerActivity.this, msg + "---" + code);
        }
    }

    @Override
    public void onWait(AliyunDownloadMediaInfo outMediaInfo) {

    }

    @Override
    public void onDelete(AliyunDownloadMediaInfo info) {

    }

    @Override
    public void onDeleteAll() {

    }

    @Override
    public void onFileProgress(AliyunDownloadMediaInfo info) {

    }

    /**
     * ------------------------------------------下载回调 end--------------------------------------
     */



    /**
     * ------------------------------------------播放器View的相关回调 -------------------------------
     */
    //播放准备完成的回调
    public static class PlayerPreparedListener implements IPlayer.OnPreparedListener {

        private WeakReference<AlivcPlayerActivity> mWeakReference;

        public PlayerPreparedListener(AlivcPlayerActivity alivcPlayerActivity) {
            mWeakReference = new WeakReference<>(alivcPlayerActivity);
        }

        @Override
        public void onPrepared() {
            AlivcPlayerActivity alivcPlayerActivity = mWeakReference.get();
            if (alivcPlayerActivity != null) {
                alivcPlayerActivity.onPlayerPrepared();
            }
        }
    }

    private void onPlayerPrepared() {
        //获取当前播放视频的duration
        MediaInfo mediaInfo = mAliyunVodPlayerView.getMediaInfo();
        int duration = mediaInfo.getDuration();
        mLongVideoBean.setDuration(duration + "");
        //如果是投屏中,播放结束后,自动播放下一级集数
        if (mAliyunVodPlayerView != null && mAliyunVodPlayerView.getIsCreenCosting()) {
            //先停止,再重新投屏
            mAliyunVodPlayerView.screenCostStop();
            mAliyunVodPlayerView.screenCostPlay();
        }
    }

    public static class PlayerInfoListener implements IPlayer.OnInfoListener {

        private WeakReference<AlivcPlayerActivity> mWeakReference;

        public PlayerInfoListener(AlivcPlayerActivity alivcPlayerActivity) {
            mWeakReference = new WeakReference<>(alivcPlayerActivity);
        }

        @Override
        public void onInfo(InfoBean infoBean) {
            AlivcPlayerActivity alivcPlayerActivity = mWeakReference.get();
            if (alivcPlayerActivity != null) {
                if (infoBean.getCode() == InfoCode.CurrentPosition) {
                    alivcPlayerActivity.onPlayerCurrentPositionChanged(infoBean.getExtraValue());
                }
            }
        }
    }

    /**
     * 更新当前播放进度
     */
    private void onPlayerCurrentPositionChanged(long currentPosition) {
        //计算当前播放进度的百分比
        double percent = 0;
        String duration = mLongVideoBean.getDuration();
        if (!TextUtils.isEmpty(duration)) {
            percent = (currentPosition >= Double.valueOf(duration)) ? 100 : (currentPosition * 1.00 / Double.valueOf(duration) * 100.00);
        }
        //当百分比更新的时候,才更新数据库
        if ((int) percent != mLongVideoBean.getWatchPercent()) {
            mLongVideoBean.setWatchPercent((int) percent);
            mLongVideoBean.setWatchDuration(currentPosition + "");

            mLongVideoDatabaseManager.updateWatchHistory(mLongVideoBean);
        }
    }

    //播放完成的回调
    public static class PlayerCompletionListener implements IPlayer.OnCompletionListener {

        private WeakReference<AlivcPlayerActivity> mWeakReference;

        public PlayerCompletionListener(AlivcPlayerActivity alivcPlayerActivity) {
            mWeakReference = new WeakReference<>(alivcPlayerActivity);
        }

        @Override
        public void onCompletion() {
            AlivcPlayerActivity alivcPlayerActivity = mWeakReference.get();
            if (alivcPlayerActivity != null) {
                alivcPlayerActivity.onPlayerCompletion();
            }
        }
    }

    /**
     * 视频播放完成的处理
     */
    private void onPlayerCompletion() {
        if (mIsSerier) {
            //剧集播放完成,自动播放下一级
            if (mSeriesVideoList != null && mSeriesVideoList.size() > 0 && mSeriesVideoList.contains(mLongVideoBean)) {
                int mCurrentIndex = mSeriesVideoList.indexOf(mLongVideoBean);
                if (mCurrentIndex >= 0 && mSeriesVideoList.size() > mCurrentIndex + 1) {
                    mLongVideoBean = mSeriesVideoList.get(mCurrentIndex + 1);
                    refreshActivity();
                }
                if (mCurrentIndex + 1 >= mSeriesVideoList.size()) {
                    mLongVideoBean = mSeriesVideoList.get(0);
                    refreshActivity();
                }
            }
        } else {
            //非剧集
            if (mSimilarVideoList != null && mSimilarVideoList.size() > 0) {
                if (mCurrentSimilarVideoPosition >= mSimilarVideoList.size() || mCurrentSimilarVideoPosition < 0) {
                    mCurrentSimilarVideoPosition = 0;
                }
                mLongVideoBean = mSimilarVideoList.get(mCurrentSimilarVideoPosition);
                refreshActivity();
                mCurrentSimilarVideoPosition++;
            }
        }
    }

    /**
     * 打点点击事件
     */
    public static class PlayerDotViewClickListener implements ControlView.OnDotViewClickListener {

        private WeakReference<AlivcPlayerActivity> weakReference;

        public PlayerDotViewClickListener(AlivcPlayerActivity alivcPlayerActivity) {
            weakReference = new WeakReference<>(alivcPlayerActivity);
        }

        @Override
        public void onDotViewClick(int x, int y, DotView dotView) {
            AlivcPlayerActivity alivcPlayerActivity = weakReference.get();
            if (alivcPlayerActivity != null) {
                alivcPlayerActivity.showDotViewMsgDialogFragment(x, y, dotView);
            }
        }
    }

    /**
     * 展示DialogFragment的内容信息
     */
    private void showDotViewMsgDialogFragment(int x, int y, DotView dotView) {
        mAlivcDotMsgDialogFragment = new AlivcDotMsgDialogFragment();
        mAlivcDotMsgDialogFragment.setX(x);
        mAlivcDotMsgDialogFragment.setY(y);
        mAlivcDotMsgDialogFragment.setDotView(dotView);
        mAlivcDotMsgDialogFragment.setOnDotViewMsgClickListener(new AlivcDotMsgDialogFragment.OnDotViewMsgClickListener() {
            @Override
            public void onDotViewMsgClick() {
                if (mAlivcDotMsgDialogFragment != null) {
                    mAlivcDotMsgDialogFragment.dismiss();
                    DotView mDotView = mAlivcDotMsgDialogFragment.getDotView();
                    if (mDotView != null && !TextUtils.isEmpty(mDotView.getDotTime())) {
                        mAliyunVodPlayerView.seekTo(Integer.valueOf(mDotView.getDotTime()) * 1000);
                    }
                }
            }
        });

        mAlivcDotMsgDialogFragment.show(getSupportFragmentManager(), "AlivcDotMsgDialogFragment");
    }

    /**
     * 广告点击事件 退出页面
     */
    public static class PlayerControlViewFinishListener implements AliyunVodPlayerView.OnFinishListener {

        private WeakReference<AlivcPlayerActivity> weakReference;

        public PlayerControlViewFinishListener(AlivcPlayerActivity alivcPlayerActivity) {
            weakReference = new WeakReference<>(alivcPlayerActivity);
        }


        @Override
        public void onFinishClick() {
            AlivcPlayerActivity alivcPlayerActivity = weakReference.get();
            if (alivcPlayerActivity != null) {
                alivcPlayerActivity.finish();
            }
        }
    }

    /**
     * ControlView隐藏事件
     */
    public static class PlayerControlViewHideListener implements ControlView.OnControlViewHideListener {

        private WeakReference<AlivcPlayerActivity> weakReference;

        public PlayerControlViewHideListener(AlivcPlayerActivity alivcPlayerActivity) {
            weakReference = new WeakReference<>(alivcPlayerActivity);
        }

        @Override
        public void onControlViewHide() {
            AlivcPlayerActivity alivcPlayerActivity = weakReference.get();
            if (alivcPlayerActivity != null) {
                if (alivcPlayerActivity.mAlivcDotMsgDialogFragment != null) {
                    alivcPlayerActivity.mAlivcDotMsgDialogFragment.dismiss();
                }
            }
        }
    }

    /**
     * ControlView 横屏展示更多
     */
    public static class PlayerControlViewShowMoreClickListener implements ControlView.OnShowMoreClickListener {

        private WeakReference<AlivcPlayerActivity> weakReference;

        public PlayerControlViewShowMoreClickListener(AlivcPlayerActivity alivcPlayerActivity) {
            weakReference = new WeakReference<>(alivcPlayerActivity);
        }

        @Override
        public void showMore() {
            AlivcPlayerActivity alivcPlayerActivity = weakReference.get();
            if (alivcPlayerActivity != null) {
                long currentClickTime = System.currentTimeMillis();
                // 防止快速点击
                if (currentClickTime - alivcPlayerActivity.oldTime <= 1000) {
                    return;
                }
                alivcPlayerActivity.oldTime = currentClickTime;
                alivcPlayerActivity.showMore(alivcPlayerActivity);
            }
        }
    }

    private void showMore(AlivcPlayerActivity activity) {
        showMoreDialog = new AlivcShowMoreDialog(activity);
        AliyunShowMoreValue moreValue = new AliyunShowMoreValue();
        moreValue.setSpeed(mAliyunVodPlayerView.getCurrentSpeed());
        moreValue.setVolume((int) mAliyunVodPlayerView.getCurrentVolume());

        ShowMoreView showMoreView = new ShowMoreView(activity, moreValue);
        showMoreDialog.setContentView(showMoreView);
        showMoreDialog.show();
        showMoreView.setOnDownloadButtonClickListener(new ShowMoreView.OnDownloadButtonClickListener() {
            @Override
            public void onDownloadClick() {

            }
        });

        //倍速
        showMoreView.setOnSpeedCheckedChangedListener(new ShowMoreView.OnSpeedCheckedChangedListener() {
            @Override
            public void onSpeedChanged(RadioGroup group, int checkedId) {
                // 点击速度切换
                if (checkedId == R.id.rb_speed_normal) {
                    mAliyunVodPlayerView.changeSpeed(SpeedValue.One);
                } else if (checkedId == R.id.rb_speed_onequartern) {
                    mAliyunVodPlayerView.changeSpeed(SpeedValue.OneQuartern);
                } else if (checkedId == R.id.rb_speed_onehalf) {
                    mAliyunVodPlayerView.changeSpeed(SpeedValue.OneHalf);
                } else if (checkedId == R.id.rb_speed_twice) {
                    mAliyunVodPlayerView.changeSpeed(SpeedValue.Twice);
                }
            }
        });

        /**
         * 初始化亮度
         */
        if (mAliyunVodPlayerView != null) {
            showMoreView.setBrightness(mAliyunVodPlayerView.getScreenBrightness());
        }
        // 亮度seek
        showMoreView.setOnLightSeekChangeListener(new ShowMoreView.OnLightSeekChangeListener() {
            @Override
            public void onStart(SeekBar seekBar) {

            }

            @Override
            public void onProgress(SeekBar seekBar, int progress, boolean fromUser) {
                setWindowBrightness(progress);
                if (mAliyunVodPlayerView != null) {
                    mAliyunVodPlayerView.setScreenBrightness(progress);
                }
            }

            @Override
            public void onStop(SeekBar seekBar) {

            }
        });

        /**
         * 初始化音量
         */
        if (mAliyunVodPlayerView != null) {
            showMoreView.setVoiceVolume(mAliyunVodPlayerView.getCurrentVolume());
        }
        showMoreView.setOnVoiceSeekChangeListener(new ShowMoreView.OnVoiceSeekChangeListener() {
            @Override
            public void onStart(SeekBar seekBar) {

            }

            @Override
            public void onProgress(SeekBar seekBar, int progress, boolean fromUser) {
                mAliyunVodPlayerView.setCurrentVolume(progress / 100.00f);
            }

            @Override
            public void onStop(SeekBar seekBar) {

            }
        });

        showMoreView.setOnBarrageButtonClickListener(new ShowMoreView.OnBarrageButtonClickListener() {
            @Override
            public void onBarrageClick() {
                if (showMoreDialog != null && showMoreDialog.isShowing()) {
                    showMoreDialog.dismiss();
                }
                showDanmakuSettingView();
            }
        });

        showMoreView.setOnScreenCastButtonClickListener(new ShowMoreView.OnScreenCastButtonClickListener() {
            @Override
            public void onScreenCastClick() {
                //如果是本地视频,则不支持投屏
                if (mIsLocal) {
                    ToastUtils.show(AlivcPlayerActivity.this, getString(R.string.alivc_player_local_not_support_screencost));
                    return;
                }
                if (showMoreDialog != null && showMoreDialog.isShowing()) {
                    showMoreDialog.dismiss();
                }
                showScreenCastView();
            }
        });
    }

    /**
     * 显示投屏对话框
     */
    private void showScreenCastView() {
        screenShowMoreDialog.show();
    }

    /**
     * 显示弹幕设置对话框
     */
    private void showDanmakuSettingView() {
        danmakuShowMoreDialog = new AlivcShowMoreDialog(this);
        DanmakuSettingView mDanmakuSettingView = new DanmakuSettingView(this);
        mDanmakuSettingView.setAlphaProgress(mAlphProgress);
        mDanmakuSettingView.setSpeedProgress(mSpeedProgress);
        mDanmakuSettingView.setRegionProgress(mRegionProgress);
        danmakuShowMoreDialog.setContentView(mDanmakuSettingView);
        danmakuShowMoreDialog.show();

        //透明度
        mDanmakuSettingView.setOnAlphaSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mAlphProgress = progress;
                if (mAliyunVodPlayerView != null) {
                    mAliyunVodPlayerView.setDanmakuAlpha(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //显示区域
        mDanmakuSettingView.setOnRegionSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mRegionProgress = progress;
                if (mAliyunVodPlayerView != null) {
                    mAliyunVodPlayerView.setDanmakuRegion(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //速率
        mDanmakuSettingView.setOnSpeedSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mSpeedProgress = progress;
                if (mAliyunVodPlayerView != null) {
                    mAliyunVodPlayerView.setDanmakuSpeed(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //恢复默认
        mDanmakuSettingView.setOnDefaultListener(new DanmakuSettingView.OnDefaultClickListener() {
            @Override
            public void onDefaultClick() {
                if (mAliyunVodPlayerView != null) {
                    mAliyunVodPlayerView.setDanmakuDefault();
                }
            }
        });

    }

    /**
     * 试看点击事件
     */
    public static class PlayerTrailerViewClickListener implements TrailersView.OnTrailerViewClickListener {

        private WeakReference<AlivcPlayerActivity> weakReference;

        public PlayerTrailerViewClickListener(AlivcPlayerActivity alivcPlayerActivity) {
            weakReference = new WeakReference<>(alivcPlayerActivity);
        }

        @Override
        public void onTrailerPlayAgainClick() {
            AlivcPlayerActivity alivcPlayerActivity = weakReference.get();
            if (alivcPlayerActivity != null) {
                alivcPlayerActivity.requestSts(false);
            }
        }

        @Override
        public void onOpenVipClick() {
            AlivcPlayerActivity alivcPlayerActivity = weakReference.get();
            if (alivcPlayerActivity != null) {
                alivcPlayerActivity.onOpenVipClick();
            }
        }
    }


    private void onOpenVipClick() {
//        Intent intent = new Intent(this, AlivcSettingActivity.class);
//        startActivity(intent);
    }

    /**
     * 发送弹幕
     */
    private class MynewOnSoftKeyHideListener implements AliyunVodPlayerView.OnSoftKeyHideListener {

        private WeakReference<AlivcPlayerActivity> weakReference;

        private MynewOnSoftKeyHideListener(AlivcPlayerActivity aliyunPlayerSkinActivity) {
            weakReference = new WeakReference<>(aliyunPlayerSkinActivity);
        }

        @Override
        public void softKeyHide() {

        }

        @Override
        public void onClickPaint() {
            AlivcPlayerActivity aliyunPlayerActivity = weakReference.get();
            if (aliyunPlayerActivity != null) {
                aliyunPlayerActivity.mSoftInputDialogFragment.show(getSupportFragmentManager(), "SoftInputDialogFragment");

            }

        }
    }

    private static class MyOnScreenCostingSingleTagListener implements OnScreenCostingSingleTagListener {

        private WeakReference<AlivcPlayerActivity> weakReference;

        private MyOnScreenCostingSingleTagListener(AlivcPlayerActivity aliyunPlayerSkinActivity) {
            weakReference = new WeakReference<>(aliyunPlayerSkinActivity);
        }

        @Override
        public void onScreenCostingSingleTag() {
            AlivcPlayerActivity aliyunPlayerSkinActivity = weakReference.get();
            if (aliyunPlayerSkinActivity != null) {
                aliyunPlayerSkinActivity.screenCostingSingleTag();
            }
        }
    }

    private void screenCostingSingleTag() {
        if (screenShowMoreDialog != null && screenShowMoreDialog.isShowing()) {
            screenShowMoreDialog.dismiss();
        }
    }

    /**
     * 投屏时,视频播放完成回调
     */
    private static class OnScreenCostingVideoCompletionListener implements AliyunVodPlayerView.OnScreenCostingVideoCompletionListener {

        private WeakReference<AlivcPlayerActivity> weakReference;

        public OnScreenCostingVideoCompletionListener(AlivcPlayerActivity alivcPlayerActivity) {
            weakReference = new WeakReference<>(alivcPlayerActivity);
        }

        @Override
        public void onScreenCostingVideoCompletion() {
            AlivcPlayerActivity alivcPlayerActivity = weakReference.get();
            if (alivcPlayerActivity != null) {
                alivcPlayerActivity.screenCostingVideoCompletion();
            }
        }
    }

    private void screenCostingVideoCompletion() {
        onPlayerCompletion();
    }

    /**
     * 横竖屏切换监听
     */
    public class PlayerOrientationChangeListner implements AliyunVodPlayerView.OnOrientationChangeListener {

        private WeakReference<AlivcPlayerActivity> weakReference;

        public PlayerOrientationChangeListner(AlivcPlayerActivity alivcPlayerActivity) {
            weakReference = new WeakReference<>(alivcPlayerActivity);
        }

        @Override
        public void orientationChange(boolean from, AliyunScreenMode currentMode) {
            AlivcPlayerActivity alivcPlayerActivity = weakReference.get();
            if (alivcPlayerActivity != null) {
                if (alivcPlayerActivity.mIsLocal && currentMode == AliyunScreenMode.Small) {
                    finish();
                } else {
                    alivcPlayerActivity.hideShowMoreDialog(from, currentMode);
                    alivcPlayerActivity.hideDanmakuSettingDialog(from, currentMode);
                    alivcPlayerActivity.hideScreenSostDialog(from, currentMode);
//                alivcPlayerActivity.currentScreenMode = currentMode;
                }
            }
        }
    }

    private void hideShowMoreDialog(boolean from, AliyunScreenMode currentMode) {
        if (showMoreDialog != null) {
            if (currentMode == AliyunScreenMode.Small) {
                showMoreDialog.dismiss();
            }
        }
    }

    private void hideDanmakuSettingDialog(boolean fromUser, AliyunScreenMode currentMode) {
        if (danmakuShowMoreDialog != null) {
            if (currentMode == AliyunScreenMode.Small) {
                danmakuShowMoreDialog.dismiss();
            }
        }
    }

    private void hideScreenSostDialog(boolean fromUser, AliyunScreenMode currentMode) {
        if (screenShowMoreDialog != null) {
            if (currentMode == AliyunScreenMode.Small) {
                screenShowMoreDialog.dismiss();
            }
        }
    }

    /**
     * 屏幕亮度改变监听
     */
    public static class PlayerControlViewScreenBrightnessListener implements AliyunVodPlayerView.OnScreenBrightnessListener {

        private WeakReference<AlivcPlayerActivity> weakReference;

        public PlayerControlViewScreenBrightnessListener(AlivcPlayerActivity alivcPlayerActivity) {
            weakReference = new WeakReference<>(alivcPlayerActivity);
        }

        @Override
        public void onScreenBrightness(int brightness) {
            AlivcPlayerActivity alivcPlayerActivity = weakReference.get();
            if (alivcPlayerActivity != null) {
                alivcPlayerActivity.onScreenBrightness(brightness);
            }
        }
    }

    private void onScreenBrightness(int brightness) {
        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.setScreenBrightness(brightness);
            setWindowBrightness(brightness);
        }
    }

    /**
     * ------------------------------------------播放器View的相关回调 end------------------------------------------
     */


}
