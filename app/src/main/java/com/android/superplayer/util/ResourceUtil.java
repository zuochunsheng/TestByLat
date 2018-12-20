package com.android.superplayer.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;

/**
 * authr : edz on 2017/10/16  下午3:20
 * describe
 */


public class ResourceUtil {
    //getColor(int id)在API版本23时(Android 6.0) 已过时
    public static final int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    public static final Drawable getDrawable(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 22) {//5.0  21之后
            return ContextCompat.getDrawable(context, id);
        } else {
            return context.getResources().getDrawable(id);
        }
    }


    public static float dp2px(Resources resources, float dp) {
        final float scale = resources.getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public static float sp2px(Resources resources, float sp) {
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

}
