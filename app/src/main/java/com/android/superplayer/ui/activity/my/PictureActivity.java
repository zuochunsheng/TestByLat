package com.android.superplayer.ui.activity.my;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.superplayer.R;
import com.android.superplayer.takephoto.IUploadEvent;
import com.android.superplayer.takephoto.TakephotoUtil;
import com.bumptech.glide.Glide;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PictureActivity extends Activity {

    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.iv)
    ImageView iv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        ButterKnife.bind(this);



    }

    @OnClick(R.id.tv)
    public void onViewClicked() {
        TakephotoUtil.getInstance(this).checkPermissions(new IUploadEvent() {
            @Override
            public void takephotoSuccessEvent(String uri) {
                Log.e("zuo", "takephotoSuccessEvent:" + uri);
                Glide.with(PictureActivity.this)
                        .load(uri)
                        .into(iv);
            }

            @Override
            public void takephotoErrorEvent(String error) {
                Log.e("zuo","takephotoErrorEvent =" +error) ;
            }

            @Override
            public void uploadSuccessEvent(String result) {

            }

            @Override
            public void uploadErrorEvent(String error) {
                Log.e("zuo","uploadErrorEvent =" +error) ;
            }
        });
    }






}
