package com.android.superplayer.ui.activity.my;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;

import com.android.superplayer.R;
import com.android.superplayer.base.BaseActivity;
import com.android.superplayer.config.LogUtil;
import com.android.superplayer.ui.activity.my.ExoPlayerActivity;
import com.android.superplayer.ui.widgit.X5WebView;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

//BaseActivity
public class WebActivity extends BaseActivity {

    private X5WebView mX5WebView;
    private ProgressBar progressBar;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;

    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        String url = ExoPlayerActivity.url;//ok
//        playVideoByTbs(url);
//    }

    @Override
    protected void initViewAndData() {
        LogUtil.e("class Web");
        mX5WebView = findViewById(R.id.x5WebView);
        progressBar = findViewById(R.id.progressBar);


        //net
        //String url = ExoPlayerActivity.origin;//ok
        String url = ExoPlayerActivity.url;//ok
        //String url = ExoPlayerActivity.zzd_url; //ok

        //mp4
        String url4 = ExoPlayerActivity.mp4; // not



        //mX5WebView.loadUrl("https://blog.csdn.net/wuqingsen1");
        //mX5WebView.loadUrl(url);//网页
        startPlay(url);//视频


        initX5WebView();
        setProgressBar();
        setClick();




    }

    /**
     * 使用自定义webview播放视频
     * @param vedioUrl 视频地址
     */
    private void startPlay(String vedioUrl) {
        mX5WebView.loadUrl(vedioUrl);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        mX5WebView.getView().setOverScrollMode(View.OVER_SCROLL_ALWAYS);
        mX5WebView.setWebChromeClient(new WebChromeClient());
    }

    /**
     * 启用硬件加速
     */
    private void initX5WebView() {
        try {
            if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 11) {
                getWindow()
                        .setFlags(
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception e) {
        }
    }

    /**
     * 设置进度条
     */
    private void setProgressBar() {
        mX5WebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //显示进度条
                if (newProgress < 100) {
                    progressBar.setProgress(newProgress);
                    progressBar.setVisibility(View.VISIBLE);
                }else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 点击事件
     */
    private void setClick() {
        mX5WebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
                //点击事件
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                //加载错误
            }
        });
    }

    /**
     * 返回键监听
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mX5WebView != null && mX5WebView.canGoBack()) {
                mX5WebView.goBack();
                return true;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        //释放资源
        if (mX5WebView != null)
            mX5WebView.destroy();
        super.onDestroy();
    }







}
