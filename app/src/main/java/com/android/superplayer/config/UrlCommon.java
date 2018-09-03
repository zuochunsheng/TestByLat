package com.android.superplayer.config;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;



/**
 *
 * @author zuochunsheng
 * @time 2018/8/29 16:37
 *   公共的 请求参数
 */
public class UrlCommon {

    public static String platform = "ANDROID";//平台名称,IOS, ANDROID,PC,H5,ADMIN;暂时没用 ,
    public static String category = "0";// 类别，0 app，1 微信，2 pc ,

    private static String appVersion = "";//appVersion 版本号




    // ANDROID -->
   /* public static String getPlatform(Context context) {

        if(TextUtils.isEmpty(platform)){
            ApplicationInfo info= null;
            try {
                info = context.getPackageManager()
                        .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                platform = info.metaData.getString("UMENG_CHANNEL");
                if(platform == null){
                    platform = "ANDROID";
                }

            } catch (PackageManager.NameNotFoundException e) {
                platform = "ANDROID";
                e.printStackTrace();
            }
        }
        //Log.e("tag",platform);
        return platform;

    }*/

    // 整数
    /*public static String getVersionCode(Context context){
        if(TextUtils.isEmpty(versionCode)){
            PackageManager packageManager=context.getPackageManager();
            PackageInfo packageInfo;

            try {
                packageInfo = packageManager.getPackageInfo(context.getPackageName(),0);
                versionCode = String.valueOf(packageInfo.versionCode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return versionCode;
    }
*/


    // 1.0.0
    public static String getVersionName(Context context){
        if(TextUtils.isEmpty(appVersion)){
            PackageManager packageManager=context.getPackageManager();
            PackageInfo packageInfo;

            try {
                packageInfo = packageManager.getPackageInfo(context.getPackageName(),0);
                appVersion = String.valueOf(packageInfo.versionName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return appVersion;
    }
}
