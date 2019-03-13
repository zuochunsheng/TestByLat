package com.android.superplayer.ui.activity.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.superplayer.R;
import com.android.superplayer.config.LogUtil;
import com.android.superplayer.ui.widgit.FutureStudioView;
import com.android.superplayer.util.GlideSimpleTarget;
import com.android.superplayer.util.duckdesign.OtherObiect;
import com.android.superplayer.util.duckdesign.SubDuck;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;


import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class RxJavaActivity extends Activity {

    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.text2)
    TextView text2;
    @BindView(R.id.text3)
    TextView text3;
    @BindView(R.id.text4)
    FutureStudioView imageView;
    @BindView(R.id.text5)
    TextView text5;


    int startTime = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.text, R.id.text1, R.id.text2, R.id.text3, R.id.text4, R.id.text5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text:
                // 鸭子问题
                //https://www.cnblogs.com/mengdd/archive/2012/12/26/2834884.html
                SubDuck subDuck = new SubDuck();

                //
                OtherObiect otherObiect = new OtherObiect();
                subDuck.setFlyBehavior(otherObiect);
                subDuck.setQuackBehavior(otherObiect);

                subDuck.performFly();
                subDuck.performQuack();
                break;
            case R.id.text1:






//                Observable.just(1, 2, 3, 4)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Consumer<Integer>() {
//                            @Override
//                            public void accept(Integer integer) throws Exception {
//                                 LogUtil.e(integer+"");
//                            }
//                        });

                //2秒后输出日志“hello world”，然后结束
//                Observable.timer(2, TimeUnit.SECONDS)
//                        //.subscribeOn(Schedulers.io())
//                        //.observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Consumer<Long>() {
//                            @Override
//                            public void accept(Long aLong) throws Exception {
//                                System.out.println("hello world 2" );
//                                LogUtil.e("hello world 2");
//                            }
//                        });


                //每隔2秒输出日志
               // Observable.interval()


               // throttleFirst防止按钮重复点击
//                Observable.just(1, 2, 3, 4)
//                        .throttleFirst(1,TimeUnit.SECONDS)
//                        .subscribe(new Observer<Integer>() {
//                            @Override
//                            public void onSubscribe(Disposable d) {
//
//                            }
//
//                            @Override
//                            public void onNext(Integer value) {
//
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//
//                            }
//
//                            @Override
//                            public void onComplete() {
//
//                            }
//                        });


                //schedulePeriodically做轮询请求
//                Observable.create(new ObservableOnSubscribe<String>() {
//                    @Override
//                    public void subscribe(final ObservableEmitter<String> e) throws Exception {
//                        Schedulers.newThread().createWorker()
//                                .schedulePeriodically(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        e.onNext(doNetworkCallAndGetStringResult());
//                                    }
//                                },200,2000,TimeUnit.MILLISECONDS);
//                    }
//                }).subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) throws Exception {
//                        LogUtil.e("polling... s=" +s);
//                    }
//                });

//                Observable.range(10,2) //  10,  11
//                        .scan(new BiFunction<Integer, Integer, Integer>() { //第一，二个参数传进的参数， 第三个 参数 是返回值
//                            @Override
//                            public Integer apply(Integer integer, Integer integer2) throws Exception {
//                                //第一个值是不参与func2的操作，当然你也可以传递一个init值进入作为默认操作。他的第一个参数是上一次计算的结果传入
//                                LogUtil.e( integer +"==="+integer2);
//                                return integer+integer2;
//                            }
//                        }).subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        LogUtil.e(integer +"----Subacriber");
//                    }
//                });

                break;
            case R.id.text2:
                final FutureTask<String> futureTask =  new FutureTask<String>(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                       // 执行异步操作并返回数据
                        return "执行异步操作并返回数据";
                    }
                });

                Scheduler.Worker worker = Schedulers.io().createWorker();
                worker.schedule(new Runnable() {
                    @Override
                    public void run() {
                        futureTask.run();
                    }
                }) ;

                Observable<String> stringObservable = Observable.fromFuture(futureTask);

                stringObservable.subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        LogUtil.e("s=" + s);
                    }
                });


                Observable.just(2)
                        .map(new Function<Integer, String>() {

                            @Override
                            public String apply(Integer integer) throws Exception {
                                return String.valueOf(String.format("原始数据的两倍为: %s", integer * 2));
                            }
                        });




                break;
            case R.id.text3:

                // 不能连续点击
                Observable
                        .interval(0,1,TimeUnit.SECONDS)
                        .take(startTime + 1)
                        .map(new Function<Long, Long>() {

                            @Override
                            public Long apply(Long time) throws Exception {
                                return startTime - time;
                            }
                        })

                       .subscribe(new Observer<Long>() {
                           @Override
                           public void onSubscribe(Disposable d) {

                           }

                           @Override
                           public void onNext(Long value) {
                               //text3.setEnabled(false);
                               System.out.println(String.format("倒计时: %s s", value));
                               //text3.setText(value+"");
                           }

                           @Override
                           public void onError(Throwable e) {
                               //text3.setEnabled(true);
                               System.out.println("倒计时出现异常");
                               e.printStackTrace();

                               //text3.setText("倒计时出现异常");
                           }

                           @Override
                           public void onComplete() {
                               //text3.setEnabled(true);
                               System.out.println("倒计时结束");
                               //text3.setText("倒计时结束");
                           }
                       });



                break;
            case R.id.text4://glide
                String sss = "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2895986097,3609514076&fm=173&app=25&f=JPEG?w=600&h=400&s=DBACB7475B8662D2062E5B6D0300E068";
//                Glide.with(getApplicationContext())
//                        .load(sss)
//                        .asBitmap()
//                        .into(new GlideSimpleTarget(imageView));


                FutureStudioView customView = (FutureStudioView) findViewById( R.id.text4 );

                ViewTarget viewTarget = new ViewTarget<FutureStudioView, GlideDrawable>( customView ) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        this.view.setImage( resource.getCurrent() );
                    }
                };

                Glide.with( this.getApplicationContext() ) // safer!
                        .load( sss)
                        .into( viewTarget );

                break;
            case R.id.text5:
                break;
        }
    }


    //网络请求 返回数据
    private String doNetworkCallAndGetStringResult(){

        return "abcdding";
    }
}
