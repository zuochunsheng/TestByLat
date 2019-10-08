package com.android.superplayer.util;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.android.superplayer.config.LogUtil;
import com.bumptech.glide.request.Request;
//import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;

//public class GlideSimpleTarget extends SimpleTarget<Bitmap> {
//    ImageView imageView;
//    public GlideSimpleTarget(ImageView imageView) {
//       this.imageView = imageView;
//    }
//
//    @Override
//    public void onLoadStarted(Drawable placeholder) {
//        LogUtil.e("当前专辑的图片 onLoadStarted <<thread>>"+Thread.currentThread().getName());
//    }
//
//    @Override
//    public void onLoadCleared(Drawable placeholder) {
//        LogUtil.e("当前专辑的图片 onLoadCleared");
//    }
//
////    @Override
////    public void getSize(SizeReadyCallback cb) {
////        LogUtil.e("当前专辑的图片 getSize");
////    }
//
//    @Override
//    public void setRequest(Request request) {
//        LogUtil.e("当前专辑的图片 setRequest");
//    }
//
//    @Override
//    public void onLoadFailed(Exception e, Drawable errorDrawable) {
//        LogUtil.e("当前专辑的图片 onLoadFailed");
//    }
//
//    @Override
//    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//        LogUtil.e("当前专辑的图片 onResourceReady");
//        imageView.setImageBitmap( resource );
//    }
//
//    @Override
//    public Request getRequest() {
//        LogUtil.e("当前专辑的图片 getRequest");
//        return null;
//    }
//
//    @Override
//    public void onStart() {
//        LogUtil.e("当前专辑的图片 onStart");
//    }
//
//    @Override
//    public void onStop() {
//        LogUtil.e("当前专辑的图片 onStop");
//    }
//
//    @Override
//    public void onDestroy() {
//        LogUtil.e("当前专辑的图片 onDestroy");
//    }
//}
