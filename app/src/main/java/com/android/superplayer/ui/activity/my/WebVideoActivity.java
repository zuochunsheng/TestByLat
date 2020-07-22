package com.android.superplayer.ui.activity.my;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.android.superplayer.R;
import com.android.superplayer.base.BaseActivity;
import com.android.superplayer.config.LogUtil;
import com.tencent.smtt.sdk.TbsVideo;

import java.lang.reflect.Method;

import butterknife.BindView;

import static android.view.KeyEvent.KEYCODE_BACK;

public class WebVideoActivity extends BaseActivity {

    @BindView(R.id.mWebView)
    WebView mWebView;
    @BindView(R.id.flVideoContainer)
    FrameLayout flVideoContainer;

    @Override
    public int getLayoutId() {
        return R.layout.activity_web_video;
    }


    @Override
    protected void initViewAndData() {
        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        initWebSettings();
    }


    private void initWebSettings() {
        //声明WebSettings子类
        WebSettings webSettings = mWebView.getSettings();


        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setAllowFileAccess(true);// 允许访问文件
        webSettings.setSupportZoom(true);// 支持缩放
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容

        //允许混合加载
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        try {
            if (Build.VERSION.SDK_INT >= 16) {
                Class<?> clazz = webSettings.getClass();
                Method method = clazz.getMethod("setAllowUniversalAccessFromFileURLs", boolean.class);
                if (method != null) {
                    method.invoke(webSettings, true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setDomStorageEnabled(true);// 必须保留，否则无法播放优酷视频，其他的OK
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);// 排版适应屏幕


        mWebView.setWebChromeClient(new MyWebChromeClient());// 重写一下，有的时候可能会出现问题
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                //  忽略SSL证书错误，继续加载页面
                handler.proceed();

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtil.e("web", "shouldOverrideUrlLoading url=" + url);
                // 这里需要判断sdk的版本号，小于等于20才执行这里
                LogUtil.e("sdk = " + Build.VERSION.SDK_INT);
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT_WATCH) {
                    if (url.startsWith("http://") || url.startsWith("https://")) {
                        view.loadUrl(url);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    }
                    return true;
                }
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                return super.shouldOverrideUrlLoading(view, url);


            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                LogUtil.e("sdk = " + Build.VERSION.SDK_INT);
                // 这里需要判断sdk的版本号，大于等于21才执行这里
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Uri uri = request.getUrl();
                    String url = uri.toString();
                    if (url.startsWith("http://") || url.startsWith("https://")) {
                        view.loadUrl(url);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    }
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, request);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(webSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        // Enable remote debugging via chrome://inspect
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWebView.setWebContentsDebuggingEnabled(true);
        }


        //String url = "http://v.163.com/paike/V8H1BIE6U/VAG52A1KT.html";
        //String url = "https://iflow.uc.cn/ucnews/video?app=ucnewsvideo-iflow&aid=4730814266504815307&cid=10016&zzd_from=ucnewsvideo-iflow&uc_param_str=dndsfrvesvntnwpfgibi&recoid=9227656673978313173&rd_type=reco&original_url=http%3A%2F%2Fv.ums.uc.cn%2Fvideo%2Fv_869a40619a58b7ec.html&uc_biz_str=S%3Acustom%7CC%3Aiflow_video_hide&ums_id=869a40619a58b7ec&activity=1&activity2=1";
        //String url = "http://smarticle.video.ums.uc.cn/video/wemedia/1be9492089234337aff792d86f548a75/3e5d4a35d2b68009c89e17acc8c6b771-2527362409-2-0-2.mp4?auth_key=1595344636-01c4d088108341d0b9d4c2413c9d0906-0-b5ccd797d213efd309ee85206c4c9e01";

        // String url = getIntent().getStringExtra("keyWord");
        //net
        //String url = ExoPlayerActivity.origin;//ok
        // String url = ExoPlayerActivity.url;//ok
        //String url = ExoPlayerActivity.html; //not
        String url = ExoPlayerActivity.zzd_url; //ok

        //mp4
        //String url = ExoPlayerActivity.mp4; // not

        CookieManager cookieManager = CookieManager.getInstance();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("android");

        cookieManager.setCookie(url, stringBuffer.toString());
        cookieManager.setAcceptCookie(true);

        if (!TextUtils.isEmpty(url)) {
            mWebView.loadUrl(url);
        }

    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        switch (config.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                break;
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        CustomViewCallback mCallback;

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            Log.e("ToVmp", "onShowCustomView");
            fullScreen();

            mWebView.setVisibility(View.GONE);
            flVideoContainer.setVisibility(View.VISIBLE);
            flVideoContainer.addView(view);
            mCallback = callback;
            super.onShowCustomView(view, callback);
        }

        @Override
        public void onHideCustomView() {
            Log.e("ToVmp", "onHideCustomView");
            fullScreen();

            mWebView.setVisibility(View.VISIBLE);
            flVideoContainer.setVisibility(View.GONE);
            flVideoContainer.removeAllViews();
            super.onHideCustomView();

        }
        // //视频加载添加默认图标
// @Override
// public Bitmap getDefaultVideoPoster() {
// //Log.i(LOGTAG, "here in on getDefaultVideoPoster");
// if (xdefaltvideo == null) {
// xdefaltvideo = BitmapFactory.decodeResource(getResources(), R.drawable.seach_icon);
// }
// return xdefaltvideo;
// }

        //网页标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            (WebVideoActivity.this).setTitle(title);
        }

    }


    @SuppressLint("SourceLockedOrientationActivity")
    private void fullScreen() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            Log.e("ToVmp", "横屏");
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            Log.e("ToVmp", "竖屏");
        }
    }


    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//        int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//
//        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {//音量+
//            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume + 1, 1);
//        }
//        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {//音量-
//            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume - 1, 1);
//        }
//        return true;

        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
