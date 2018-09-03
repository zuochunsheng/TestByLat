package com.android.supermarket.service.presenter;

import rx.subscriptions.CompositeSubscription;

/**
 * anther: created by zuochunsheng on 2018/8/31 14 : 33
 * descript :
 */
public class BasePresenter {

    //声明一个CompositeSubscription对象，注意是protected修饰符，便于子类进行调用
    protected CompositeSubscription mCompositeSubscription;

    public void onCreate() {
        //在基础类中对CompositeSubscription进行初始化，子类中就不用再写一次
        //子类如果需要对onCreate进行重写，记得先调用super.onCreate();
        mCompositeSubscription = new CompositeSubscription();
    }

    public void onDestroy() {
        //释放CompositeSubscription，否则会造成内存泄漏
        if (mCompositeSubscription.hasSubscriptions()){
            mCompositeSubscription.unsubscribe();
        }
    }
}
