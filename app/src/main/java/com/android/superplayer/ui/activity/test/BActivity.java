package com.android.superplayer.ui.activity.test;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.superplayer.R;
import com.android.superplayer.config.LogUtil;
import com.king.view.giftsurfaceview.GiftSurfaceView;
import com.king.view.giftsurfaceview.util.PointUtils;


import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import jp.wasabeef.glide.transformations.MaskTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ContrastFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.InvertFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.KuwaharaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.PixelationFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SwirlFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ToonFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation;



public class BActivity extends Activity {


    @BindView(R.id.blurTransformation)
    ImageView BlurTransformation_ImageView;
    @BindView(R.id.cropCircleTransformation)
    ImageView CropCircleTransformation_ImageView;
    @BindView(R.id.roundedCornersTransformation)
    ImageView RoundedCornersTransformation_ImageView;
    @BindView(R.id.maskTransformation)
    ImageView MaskTransformation_ImageView;
    @BindView(R.id.grayscaleTransformation)
    ImageView GrayscaleTransformation_ImageView;

    @BindView(R.id.toonFilterTransformation)
    ImageView ToonFilterTransformation_ImageView;
    @BindView(R.id.sepiaFilterTransformation)
    ImageView SepiaFilterTransformation_ImageView;
    @BindView(R.id.contrastFilterTransformation)
    ImageView ContrastFilterTransformation_ImageView;
    @BindView(R.id.invertFilterTransformation)
    ImageView InvertFilterTransformation_ImageView;
    @BindView(R.id.pixelationFilterTransformation)
    ImageView PixelationFilterTransformation_ImageView;
    @BindView(R.id.sketchFilterTransformation)
    ImageView SketchFilterTransformation_ImageView;

    @BindView(R.id.swirlFilterTransformation)
    ImageView SwirlFilterTransformation_ImageView;
    @BindView(R.id.brightnessFilterTransformation)
    ImageView BrightnessFilterTransformation_ImageView;
    @BindView(R.id.kuwaharaFilterTransformation)
    ImageView KuwaharaFilterTransformation_ImageView;
    @BindView(R.id.vignetteFilterTransformation)
    ImageView VignetteFilterTransformation_ImageView;


    String url = "https://goss.veer.com/creative/vcg/veer/800water/veer-312234640.jpg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        ButterKnife.bind(this);


        LogUtil.e("B Activity ----onCreate");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.e("B Activity ----onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.e("B Activity ----onStart");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        LogUtil.e("B Activity ----onRestoreInstanceState");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.e("B Activity ----onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.e("B Activity ----onPause");
    }


    //onStop
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtil.e("B Activity ----onSaveInstanceState");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.e("B Activity ----onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.e("B Activity ----onDestroy");
    }


    public void onViewClicked() {



//        其他效果
//                ToonFilterTransformation
//        SepiaFilterTransformation
//                ContrastFilterTransformation
//        InvertFilterTransformation
//                PixelationFilterTransformation

//        SketchFilterTransformation
//                SwirlFilterTransformation
//        BrightnessFilterTransformation
//                KuwaharaFilterTransformation
//        VignetteFilterTransformation
//                ---------------------
//                作者：马伟奇
//        来源：CSDN
//        原文：https://blog.csdn.net/mwq384807683/article/details/71436643
//        版权声明：本文为博主原创文章，转载请附上博文链接！

        // 混合使用
//        Glide.with(this).load(url)
//                .bitmapTransform(new BlurTransformation(this, 25),
//                        new CropCircleTransformation(this))
//                .into(BlurTransformation_ImageView);




//        MultiTransformation multi = new MultiTransformation(
//                new BlurTransformation(25),
//                new RoundedCornersTransformation(128, 0, RoundedCornersTransformation.CornerType.ALL));
//
//        Glide.with(this)
//                .load(url)
//                .apply(bitmapTransform(multi))
//                .into(BlurTransformation_ImageView);


//        ---------------------
//                作者：Errol_King
//        来源：CSDN
//        原文：https://blog.csdn.net/u010356768/article/details/78455117
//        版权声明：本文为博主原创文章，转载请附上博文链接！
    }

    @OnClick({R.id.blurTransformation, R.id.cropCircleTransformation, R.id.roundedCornersTransformation,
            R.id.maskTransformation, R.id.grayscaleTransformation, R.id.toonFilterTransformation,
            R.id.sepiaFilterTransformation, R.id.contrastFilterTransformation, R.id.invertFilterTransformation,
            R.id.pixelationFilterTransformation, R.id.sketchFilterTransformation, R.id.swirlFilterTransformation,
            R.id.brightnessFilterTransformation, R.id.kuwaharaFilterTransformation, R.id.vignetteFilterTransformation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.blurTransformation://模糊效果


//                Glide.with(this).load(url)
//                        .apply(bitmapTransform(new BlurTransformation(25)))
//                        .into(BlurTransformation_ImageView);




                break;
            case R.id.cropCircleTransformation://图片最终会展示出圆形区域

//                Glide.with(this)
//                        .load(url)
//                        .apply(bitmapTransform(new CropCircleTransformation()))
//                        .into(CropCircleTransformation_ImageView);

                break;
            case R.id.roundedCornersTransformation:// 圆角 效果
//                Glide.with(this).load(url)
//                        .apply(bitmapTransform(new RoundedCornersTransformation( 24, 0,
//                                RoundedCornersTransformation.CornerType.ALL)))
//                        .into(RoundedCornersTransformation_ImageView);
                break;
            case R.id.maskTransformation://涂层


//                Glide.with(this).load(url)
//                        .apply(bitmapTransform(new MaskTransformation( R.mipmap.ic_launcher)))
//                        .into(MaskTransformation_ImageView);
                break;
            case R.id.grayscaleTransformation: //灰度效果

//                Glide.with(this)
//                        .load(url)
//                        .apply(bitmapTransform(new GrayscaleTransformation()))
//                        .into(GrayscaleTransformation_ImageView);
                break;


            case R.id.toonFilterTransformation:
//                Glide.with(this)
//                        .load(url)
//                        .apply(bitmapTransform(new ToonFilterTransformation()))
//                        .into(ToonFilterTransformation_ImageView);
                break;
            case R.id.sepiaFilterTransformation:
//                Glide.with(this)
//                        .load(url)
//                        .apply(bitmapTransform(new SepiaFilterTransformation()))
//                        .into(SepiaFilterTransformation_ImageView);
                break;
            case R.id.contrastFilterTransformation:
//                Glide.with(this)
//                        .load(url)
//                        .apply(bitmapTransform(new ContrastFilterTransformation()))
//                        .into(ContrastFilterTransformation_ImageView);
                break;
            case R.id.invertFilterTransformation:
//                Glide.with(this)
//                        .load(url)
//                        .apply(bitmapTransform(new InvertFilterTransformation()))
//                        .into(InvertFilterTransformation_ImageView);
                break;
            case R.id.pixelationFilterTransformation:
//                Glide.with(this)
//                        .load(url)
//                        .apply(bitmapTransform(new PixelationFilterTransformation()))
//                        .into(PixelationFilterTransformation_ImageView);
                break;
            case R.id.sketchFilterTransformation:
//                Glide.with(this)
//                        .load(url)
//                        .apply(bitmapTransform(new ToonFilterTransformation()))
//                        .into(ToonFilterTransformation_ImageView);
                break;


            case R.id.swirlFilterTransformation:
//                Glide.with(this)
//                        .load(url)
//                        .apply(bitmapTransform(new SwirlFilterTransformation()))
//                        .into(SwirlFilterTransformation_ImageView);
                break;
            case R.id.brightnessFilterTransformation:
//                Glide.with(this)
//                        .load(url)
//                        .apply(bitmapTransform(new BrightnessFilterTransformation()))
//                        .into(BrightnessFilterTransformation_ImageView);
                break;
            case R.id.kuwaharaFilterTransformation:
//                Glide.with(this)
//                        .load(url)
//                        .apply(bitmapTransform(new KuwaharaFilterTransformation()))
//                        .into(KuwaharaFilterTransformation_ImageView);
                break;
            case R.id.vignetteFilterTransformation:
//                Glide.with(this)
//                        .load(url)
//                        .apply(bitmapTransform(new VignetteFilterTransformation()))
//                        .into(VignetteFilterTransformation_ImageView);
                break;
        }
    }


//    private void glideUtilByTransform(Transformation<Bitmap> transformation ,ImageView imageView){
//
//
//        Glide.with(this).load(url)
//                .bitmapTransform(transformation)
//
//                .into(imageView);
//
//    }




}
