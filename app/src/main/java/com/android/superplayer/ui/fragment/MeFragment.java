package com.android.superplayer.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.superplayer.R;
import com.android.superplayer.base.BaseFragment;
import com.android.superplayer.ui.activity.my.ExoPlayerActivity;
import com.android.superplayer.ui.activity.my.FFmpegActivity;
import com.android.superplayer.ui.activity.my.LiveTelecastActivity;
import com.android.superplayer.ui.activity.my.MediaPlayerActivity;
import com.android.superplayer.util.ActivityUtil;
import com.google.android.exoplayer2.ExoPlayer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MeFragment extends BaseFragment {


    @BindView(R.id.tv_goMediaPlay)
    TextView tvGoMediaPlay;
    Unbinder unbinder;
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

    }




    @OnClick({R.id.tv_goliveTelecast, R.id.tv_goFFmpeg, R.id.tv_exoPlayer, R.id.tv_goMediaPlay})
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


        }
    }
}
