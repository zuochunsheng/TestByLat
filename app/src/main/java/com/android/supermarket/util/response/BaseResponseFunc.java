package com.android.supermarket.util.response;

import com.android.supermarket.config.LogUtil;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by DELL on 2017/3/20.
 *  返回结果 ，筛选一次,
 */

public class BaseResponseFunc<T> implements Func1<ResponseBean<T>, Observable<T>> {
    @Override
    public Observable<T> call(ResponseBean<T> tBaseResponse) {
        //LogUtil.e("tt",tBaseResponse); //总 返回数据
        //遇到非200错误统一处理,将BaseResponse转换成您想要的对象
        if (tBaseResponse.getCode() != 200) {// 也是服务器端返回的信息
            return Observable.error(new Throwable(tBaseResponse.getCode()+"-" +tBaseResponse.getMsg()));
        }else{//正常
           // return Observable.just(tBaseResponse.getRes());
            return Observable.just(tBaseResponse.getData()).observeOn(Schedulers.io());//.subscribeOn(Schedulers.io());
        }
    }


}
