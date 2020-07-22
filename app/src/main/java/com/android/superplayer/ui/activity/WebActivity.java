package com.android.superplayer.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.android.superplayer.R;
import com.android.superplayer.base.BaseActivity;
import com.android.superplayer.ui.activity.my.ExoPlayerActivity;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class WebActivity extends BaseActivity {

    private WebView mWebView;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }


    @Override
    protected void initViewAndData() {
        mWebView = findViewById(R.id.web_view);
        //防止跳转到系统浏览器
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                loadurlLocalMethod(view, url);
//                return false;
//            }
//        });

        mWebView.getSettings().setJavaScriptEnabled(true);// 支持js
        mWebView.setWebViewClient(new WebViewClient());//防止加载网页时调起系统浏览器
        String url= mWebView.getUrl();
        //net
        //String url = ExoPlayerActivity.origin;//ok
         // String url = ExoPlayerActivity.url;//ok
        //String url = ExoPlayerActivity.zzd_url; //ok

        //mp4
        //String url = ExoPlayerActivity.mp4; // not

        //webView.loadUrl("http://www.baidu.com");
        //webView.loadUrl(url);
    }
//    public void loadurlLocalMethod(final WebView webView, final String url) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                webView.loadUrl(url);
//            }
//        });
//    }





}
