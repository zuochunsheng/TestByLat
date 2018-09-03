package com.android.supermarket.util;

/**
 * Created by admin on 2016/7/5.
 */
public class PreventMultiClick {
    private static long lastClickTime;
    //判断是否连续点击
    /*
    * 判断点击间隔，不能小于规定的间隔
    * */
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if ( time - lastClickTime < 1500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

}
