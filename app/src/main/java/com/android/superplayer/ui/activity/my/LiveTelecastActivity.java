package com.android.superplayer.ui.activity.my;

import android.app.Activity;
import android.os.Bundle;

import com.android.superplayer.R;
import com.android.superplayer.base.BaseActivity;

public class LiveTelecastActivity extends BaseActivity {

//    public static final int DEFAULT_HEIGHT = 1280;
//    public static final int DEFAULT_WIDTH = 720;
//    public static final int DEFAULT_FPS = 15;

//    public static final Facing DEFAULT_FACING = Facing.FRONT;
//    public static final Orientation DEFAULT_ORIENTATION = Orientation.PORTRAIT;
//    public static final FocusMode DEFAULT_FOCUSMODE = FocusMode.AUTO;



    public static final int DEFAULT_HEIGHT = 640;
    public static final int DEFAULT_WIDTH = 360;
    public static final int DEFAULT_FPS = 15;
    public static final int DEFAULT_MAX_BPS = 1300;
    public static final int DEFAULT_MIN_BPS = 400;
    public static final int DEFAULT_IFI = 2;
    public static final String DEFAULT_MIME = "video/avc";




    @Override
    protected int getLayoutId() {
        return R.layout.activity_live_telecast;
    }

    @Override
    protected void initViewAndData() {

//        CameraConfiguration.Builder cameraBuilder = new CameraConfiguration.Builder();
//        cameraBuilder.setOrientation(CameraConfiguration.Orientation.LANDSCAPE)
//                .setFacing(CameraConfiguration.Facing.BACK).setPreview(720, 1280)
//                .setFps(24).setFocusMode(CameraConfiguration.FocusMode.TOUCH);
//        CameraConfiguration cameraConfiguration = cameraBuilder.build();
//        mLFLiveView.setCameraConfiguration(cameraConfiguration);
//
//
//
//
//        VideoConfiguration.Builder videoBuilder = new VideoConfiguration.Builder();
//        videoBuilder.setSize(640, 360).setMime(DEFAULT_MIME)
//                .setFps(15).setBps(300, 800).setIfi(2);
//        mVideoConfiguration = videoBuilder.build();
//        mLFLiveView.setVideoConfiguration(mVideoConfiguration);

    }


}
