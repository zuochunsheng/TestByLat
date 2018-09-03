package com.android.superplayer.util.response;

/**
 * authr : edz on 2017/8/28  下午5:33
 * describe : 返回结果的额监听
 * 不用
 */


public interface OnResponListener{
    void onSuccess(String info);
    void onFailure(String msg, Exception e);
}
