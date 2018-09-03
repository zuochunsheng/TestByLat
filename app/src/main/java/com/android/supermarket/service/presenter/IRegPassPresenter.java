package com.android.supermarket.service.presenter;

import android.content.Context;

import java.util.HashMap;

/**
 * authr : edz on 2017/9/13  上午10:29
 * describe
 */


public interface IRegPassPresenter {
    void  submitRegPass(Context context,HashMap<String, String> requestBody);
}
