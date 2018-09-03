package com.android.supermarket.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.widget.TextView;

/**
 * authr : edz on 2017/8/25  下午3:53
 * describe  获取屏幕的宽度和高度
 *  单位转换
 */


public class Metrics {
    public static int getScreenWidth(Activity activity){

        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
    //获取屏幕高度
    public static int getScreenHeight(Activity activity){
        DisplayMetrics displayMetrics=new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }



    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    public static float getContentWidth(String content, TextView tView)
    {
        if (null == tView && TextUtils.isEmpty(content))
        {
            return 0f;
        }
        return getContentWidthWithSize(content, tView.getTextSize()) * tView.getTextScaleX();
    }


    private static float getContentWidthWithSize(String content, float textSize)
    {
        float width = 0f;

        Paint tPaint = new Paint();
        tPaint.setTextSize(textSize);
        width = tPaint.measureText(content);

        return width;
    }
}
