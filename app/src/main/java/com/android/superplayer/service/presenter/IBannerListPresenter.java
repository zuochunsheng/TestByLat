package com.android.superplayer.service.presenter;

import android.content.Context;

import java.util.HashMap;

/**
 * authr : edz on 2017/9/13  上午10:29
 * describe
 */


public interface IBannerListPresenter {
    void  getBannerList(Context context, HashMap<String, String> requestBody);
}
