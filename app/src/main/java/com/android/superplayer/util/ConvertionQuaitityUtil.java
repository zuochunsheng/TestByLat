package com.android.superplayer.util;

import android.content.Context;

import com.android.superplayer.R;


/**
 * 清晰度转换类
 * 传入字母返回字符
 * 传入字符返回字母
 * 主要用于从sp取出来的显示状态（sp存的是字母）
 */
public class ConvertionQuaitityUtil {

    /**
     * 判断一个字符串的首字符是否为字母
     */
    private static boolean isLetter(String s) {
        char c = s.charAt(0);
        int i = (int) c;
        if ((i >= 65 && i <= 90) || (i >= 97 && i <= 122)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 字符转换方法
     */
    public static String letterOrChinese(String str, Context context) {
        /*如果是字母，返回中文*/
        boolean b = isLetter(str);
        if (b) {
            return letterMapChinese(str, context);
        } else {
            return chineseMapLetter(str, context);
        }
    }

    /**
     * 字母转换中文
     */
    private static String letterMapChinese(String letter, Context context) {

        if (letter.equals(context.getResources().getString(R.string.alivc_longvideo_quality_sd))) {
            return context.getResources().getString(R.string.alivc_longvideo_quality_chinese_sd);
        } else if (letter.equals(context.getResources().getString(R.string.alivc_longvideo_quality_hd))) {
            return context.getResources().getString(R.string.alivc_longvideo_quality_chinese_hd);
        } else if (letter.equals(context.getResources().getString(R.string.alivc_longvideo_quality_ssd))) {
            return context.getResources().getString(R.string.alivc_longvideo_quality_chinese_ssd);
        } else {
            return context.getResources().getString(R.string.alivc_longvideo_quality_chinese_sd);
        }
    }

    /**
     * 中文转换字母
     */
    private static String chineseMapLetter(String chinese, Context context) {
        if (chinese.equals(context.getResources().getString(R.string.alivc_longvideo_quality_chinese_sd))) {
            return context.getResources().getString(R.string.alivc_longvideo_quality_sd);
        } else if (chinese.equals(context.getResources().getString(R.string.alivc_longvideo_quality_chinese_hd))) {
            return context.getResources().getString(R.string.alivc_longvideo_quality_hd);
        } else if (chinese.equals(context.getResources().getString(R.string.alivc_longvideo_quality_chinese_ssd))) {
            return context.getResources().getString(R.string.alivc_longvideo_quality_ssd);
        } else {
            return context.getResources().getString(R.string.alivc_longvideo_quality_sd);
        }
    }
}
