package com.android.superplayer.config;

/**
 * Created by elu on 2018/3/28.
 * 区别正式 测试 环境
 */

public class IsTest {

    public static final boolean IS_TEST = true;


    public static String getRent() {
        if(IS_TEST){
            return  "http://testapi.meipaiguan.com/";//测试
        }else {
            return  "http://testapi.meipaiguan.com/";
        }

    }
}
