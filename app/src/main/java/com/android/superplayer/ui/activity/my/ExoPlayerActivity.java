package com.android.superplayer.ui.activity.my;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.android.superplayer.R;
import com.android.superplayer.base.BaseActivity;
import com.android.superplayer.config.LogUtil;
import com.android.superplayer.ui.activity.MainActivity;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.Formatter;
import java.util.Locale;

/**
 * 视频播放
 * @author zuochunsheng
 * @time 2018/9/14 16:44
 */
public class ExoPlayerActivity extends BaseActivity {


    private SimpleExoPlayer mSimpleExoPlayer;

    private SimpleExoPlayerView mExoPlayerView;
    private ProgressBar mProgressBar;
    private Context context = this ;


    // 视频网络地址
    Uri playerUri = Uri.parse("https://storage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%20Hangin'%20with%20the%20Google%20Search%20Bar.mp4");
    @Override
    protected int getLayoutId() {
        return R.layout.activity_exo_player;
    }

    @Override
    protected void initViewAndData() {

        initPlayer();

        playVideo();
    }


    /**
     * 初始化player
     */
    private void initPlayer() {
        mExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.exoView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);


        //1. 创建一个默认的 TrackSelector
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTackSelectionFactory);
        LoadControl loadControl = new DefaultLoadControl();

        //2.创建ExoPlayer
        mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
        //3.创建SimpleExoPlayerView

        //4.为SimpleExoPlayer设置播放器
        mExoPlayerView.setPlayer(mSimpleExoPlayer);

    }

    //开始播放:
    private void playVideo() {
        //测量播放过程中的带宽。 如果不需要,可以为null。
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        // 生成加载媒体数据的DataSource实例。
        DataSource.Factory dataSourceFactory
                = new DefaultDataSourceFactory(ExoPlayerActivity.this,
                Util.getUserAgent(ExoPlayerActivity.this, "useExoplayer"), bandwidthMeter);
        // 生成用于解析媒体数据的Extractor实例。
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        // MediaSource代表要播放的媒体。
        MediaSource videoSource = new ExtractorMediaSource(playerUri, dataSourceFactory, extractorsFactory,
                null, null);
        //Prepare the player with the source.
        mSimpleExoPlayer.prepare(videoSource);
        //添加监听的listener
        // mSimpleExoPlayer.setVideoListener(mVideoListener);
        mSimpleExoPlayer.addListener(eventListener);
       // mSimpleExoPlayer.setTextOutput(mOutput);
        mSimpleExoPlayer.setPlayWhenReady(true);



    }



    //监听播放器的状态:
    private ExoPlayer.EventListener eventListener = new ExoPlayer.EventListener() {
        @Override
        public void onTimelineChanged(Timeline timeline, Object manifest) {
            LogUtil.e("onTimelineChanged");
        }
        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            LogUtil.e("onTracksChanged");
        }
        @Override
        public void onLoadingChanged(boolean isLoading) {
            LogUtil.e("onLoadingChanged isLoading = " + isLoading);
        }
        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            LogUtil.e("onPlayerStateChanged: playWhenReady = "+playWhenReady +" playbackState = "+playbackState);

            switch (playbackState){
                case ExoPlayer.STATE_ENDED:
                    LogUtil.e("Playback ended!");
                    //Stop playback and return to start position
                    setPlayPause(false);
                    mSimpleExoPlayer.seekTo(0);
                    break;
                case ExoPlayer.STATE_READY:
                    mProgressBar.setVisibility(View.GONE);
                    LogUtil.e("ExoPlayer ready! pos: "+mSimpleExoPlayer.getCurrentPosition()
                            +" max: "+stringForTime((int)mSimpleExoPlayer.getDuration()));
                    setProgress(0);
                    break;
                case ExoPlayer.STATE_BUFFERING:
                    LogUtil.e("Playback buffering!");
                    mProgressBar.setVisibility(View.VISIBLE);
                    break;
                case ExoPlayer.STATE_IDLE:
                    LogUtil.e("ExoPlayer idle!");
                    break;
            }
        }

        @Override
        public void onRepeatModeChanged(int repeatMode) {
            LogUtil.e("onRepeatModeChanged repeatMode =" +repeatMode);
        }

        @Override
        public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
            LogUtil.e("onShuffleModeEnabledChanged shuffleModeEnabled = " + shuffleModeEnabled);
        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {
            LogUtil.e("onPlaybackError: "+error.getMessage());
        }

        @Override
        public void onPositionDiscontinuity(int reason) {
            LogUtil.e("onPositionDiscontinuity : reason = " + reason);
        }

        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            LogUtil.e("onPlaybackParametersChanged  playbackParameters = "+ playbackParameters.toString());
        }

        @Override
        public void onSeekProcessed() {
            LogUtil.e("onSeekProcessed");

        }
    };


    private void setPlayPause(boolean play){
        mSimpleExoPlayer.setPlayWhenReady(play);
    }

    private String stringForTime(int timeMs) {
        StringBuilder mFormatBuilder;
        Formatter mFormatter;
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }





    @Override
    protected void onPause() {
        LogUtil.e("MainActivity.onPause.");
        super.onPause();
        mSimpleExoPlayer.stop();
    }
    @Override
    protected void onStop() {
        LogUtil.e("MainActivity.onStop.");
        super.onStop();
        mSimpleExoPlayer.release();
    }


}
