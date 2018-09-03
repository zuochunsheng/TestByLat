package com.android.superplayer.util;

import android.app.Activity;
import android.widget.Toast;

import com.android.superplayer.application.AndroidApplication;


/**
 * Created by ling on 2017/8/18.
 */

public class ToastUtil {
    private static Toast mToast = null;//实现不管我们触发多少次Toast调用，都只会持续一次Toast显示的时长

    private ToastUtil(){}

    public static void showToast(CharSequence text) {
        if(mToast == null) {
            mToast = Toast.makeText(AndroidApplication.getAppContext(), text, Toast.LENGTH_SHORT);

        } else {
            mToast.setText(text);
            //mToast.setDuration(Toast.LENGTH_SHORT);
        }

        mToast.show();

    }


    /**
     * 创建运行在UI线程中的Toast
     *
     * @param activity
     * @param msg
     */
    public static void showToastInUiThread(final Activity activity, final String msg) {
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    showToast(msg);
                }
            });
        }
    }


    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }


}
