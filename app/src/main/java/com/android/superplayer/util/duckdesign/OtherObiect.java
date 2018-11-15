package com.android.superplayer.util.duckdesign;

import com.android.superplayer.config.LogUtil;

/**
 * anther: created by zuochunsheng on 2018/11/15 21 : 06
 * descript :
 */
public class OtherObiect implements FlyBehavior,QuackBehavior{

    @Override
    public void fly() {
        LogUtil.e("OtherObiect fly");
    }

    @Override
    public void quack() {
        LogUtil.e("OtherObiect quack");
    }
}
