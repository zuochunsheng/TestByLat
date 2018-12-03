package com.android.superplayer.util.rxtest;

import com.android.superplayer.config.LogUtil;

import org.reactivestreams.Publisher;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * anther: created by zuochunsheng on 2018/11/16 13 : 43
 * descript :测试类
 */
public class Test {


    //RxJava1 升级到 RxJava2 所踩过的坑
    //https://www.jianshu.com/p/6d644ca1678f


    //psv
    public static void main(String[] args) {
        //System.out.println("sout");

        //tt();
        abc();


    }
    private static void tt(){

//        ObservableEmitter 可以理解为发射器，是用来发出事件的，它可以发出三种类型的事件，
//        通过调用emitter的onNext(T value)、onComplete()和onError(Throwable error)可以分别发出next事件、
//        complete事件和error事件。 如果只关心next事件的话，只需单独使用onNext()即可。
//
//        需要特别注意，emitter的onComplete()调用后，Consumer不再接收任何next事件。


        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("hello");
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });
        
        
        


    }
    
    
    /**
     *  跟compose()配合使用,比如ObservableUtils.wrap(obj).compose(toMain())
     * @author zuochunsheng
     * @time 2018/11/16 14:02
     */
//    public static <T>ObservableTransformer<T,T> toMain(){
//
//        return new ObservableTransformer<T, T>() {
//            @Override
//            public ObservableSource<T> apply(Observable<T> upstream) {
//                return upstream.subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread());
//            }
//        };
//
//
//    }
//
//    public static <T>FlowableTransformer<T,T> toMain(){
//        return new FlowableTransformer<T, T>() {
//            @Override
//            public Publisher<T> apply(Flowable<T> upstream) {
//                return upstream.subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread());
//            }
//        };
//    }
    


    private static void  abc (){

    }

}
