package com.android.superplayer.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.superplayer.R;
import com.android.superplayer.base.BaseFragment;
import com.android.superplayer.config.LogUtil;
import com.android.superplayer.ui.activity.media.AlivcPlayerActivity;
import com.android.superplayer.ui.activity.media.MainMediaActivity;
import com.android.superplayer.ui.activity.my.ExoPlayerActivity;
import com.android.superplayer.ui.activity.my.FFmpegActivity;
import com.android.superplayer.ui.activity.my.LiveTelecastActivity;
import com.android.superplayer.ui.activity.my.MediaPlayerActivity;
import com.android.superplayer.ui.activity.my.TTSActivity;
import com.android.superplayer.ui.activity.my.TtsDemo;
import com.android.superplayer.ui.activity.my.WebVideoActivity;
import com.android.superplayer.util.ActivityUtil;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MeFragment extends BaseFragment {


    @BindView(R.id.tv_goMediaPlay)
    TextView tvGoMediaPlay;

    @BindView(R.id.tv_goliveTelecast)
    TextView tvGoliveTelecast;
    @BindView(R.id.tv_goFFmpeg)
    TextView tvGoFFmpeg;


    @Override
    protected View fetchLayoutRes(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {

        //获取Activity传递过来的参数
        Bundle mBundle = getArguments();
        if (mBundle != null) {
            String title = mBundle.getString("arg");
            LogUtil.e("title = " + title);
        }


    }


    @OnClick({R.id.tv_goliveTelecast, R.id.tv_goFFmpeg, R.id.tv_exoPlayer, R.id.tv_goMediaPlay,
            R.id.tv_tts,R.id.tv_tts_xunfei,R.id.tv_third,R.id.webVideo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_goMediaPlay://音乐播放器
                ActivityUtil.getInstance().onNext(this.getActivity(), MediaPlayerActivity.class);
                break;
            case R.id.tv_goliveTelecast://直播
                ActivityUtil.getInstance().onNext(this.getActivity(), LiveTelecastActivity.class);
                break;
            case R.id.tv_goFFmpeg://视频处理
                ActivityUtil.getInstance().onNext(this.getActivity(), FFmpegActivity.class);
                break;
            case R.id.tv_exoPlayer: //ExoPlayer
                ActivityUtil.getInstance().onNext(this.getActivity(), ExoPlayerActivity.class);
                break;
            case R.id.tv_third: //AlivcPlayerActivity
               // ActivityUtil.getInstance().onNext(this.getActivity(), AlivcPlayerActivity.class);
                ActivityUtil.getInstance().onNext(this.getActivity(), MainMediaActivity.class);
                break;
           case R.id.webVideo:
                ActivityUtil.getInstance().onNext(this.getActivity(), WebVideoActivity.class);
                break;
            case R.id.tv_tts: //tts
                ActivityUtil.getInstance().onNext(this.getActivity(), TTSActivity.class);
                break;
            case R.id.tv_tts_xunfei: //tts
                ActivityUtil.getInstance().onNext(this.getActivity(), TtsDemo.class);
                break;


        }
    }
}
