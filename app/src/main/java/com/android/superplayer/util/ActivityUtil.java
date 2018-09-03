package com.android.superplayer.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.android.superplayer.R;


/**
 * Created by elu on 2018/3/30.
 */

public class ActivityUtil {

    private static ActivityUtil activityUtil;

    private ActivityUtil() {
    }

    public static synchronized ActivityUtil getInstance() {
        if (activityUtil == null) {
            activityUtil = new ActivityUtil();
        }
        return activityUtil;
    }

    //页面跳转
    public void onNext(Context context, Class clazz) {

        context.startActivity(new Intent(context, clazz));

    }

    public void onNextClearTop(Context context, Class clazz) {

        context.startActivity(new Intent(context, clazz).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

    }

    // 跳转  传值
    public void onNext(Context context, Class clazz, String extraKey, String extryValue) {

        context.startActivity(new Intent(context, clazz).putExtra(extraKey, extryValue));

    }
    public void onNext(Context context, Class clazz, String extraKey, int extryValue) {
        context.startActivity(new Intent(context, clazz).putExtra(extraKey, extryValue));
    }
    public void onNext(Context context, Class clazz, String extraKey, boolean extryValue) {
        context.startActivity(new Intent(context, clazz).putExtra(extraKey, extryValue));
    }


    //跳转 主页
    public void goMainFragment(Context context, Class clazz, String index) {
        Intent intent = new Intent(context, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("toMainActivityFrom", index);
        context.startActivity(intent);

    }


    //打电话
//    public void gotoCall(Context context) {
//
//        String phone = context.getString(R.string.tv_mobile_num);
//
//        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
//
//
//    }

}
