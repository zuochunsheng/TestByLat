package com.android.supermarket.service.presenter;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * authr : edz on 2017/9/13  上午10:29
 * describe
 */


public interface ILoginPassPresenter {
    void  submitLoginPass(HashMap<String, String> requestBody);
}
