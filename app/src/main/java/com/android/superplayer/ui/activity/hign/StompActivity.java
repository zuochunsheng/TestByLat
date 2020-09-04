package com.android.superplayer.ui.activity.hign;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.android.superplayer.R;
import com.android.superplayer.util.socketutil.Config;



import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.LifecycleEvent;
import ua.naiksoftware.stomp.dto.StompMessage;


/**
 * @time : 2019/1/22 20:51
 * @author : zcs
 * @description : 跟 CheatActivity 一样的
 * buyong
 */
public class StompActivity extends Activity {

    private EditText cheat;
    private Button send;
    private static final String TAG = "zuo";

    private boolean mNeedConnect;
    private StompClient mStompClient;
    private Timer mTimer = new Timer();;
    private int RECONNECT_TIME_INTERVAL = 3000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stomp);
        ButterKnife.bind(this);
        bindView();


    }

    private void bindView() {
        cheat = (EditText) findViewById(R.id.cheat);
        send = (Button) findViewById(R.id.send);
    }


    private void connect() {
        //mStompClient = Stomp.over(WebSocket.class, Config.WS_URI);
        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, Config.WS_URI);
        mStompClient.connect();

        mStompClient.lifecycle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LifecycleEvent>() {
                    @Override
                    public void accept(LifecycleEvent lifecycleEvent) throws Exception {
                        switch (lifecycleEvent.getType()) {
                            case OPENED:
                                mNeedConnect = false;
                                Log.e(TAG, "Stomp connection opened");
                               // toast("连接已开启");
                                break;

                            case ERROR:
                                mNeedConnect = true;
                                Log.e(TAG, "Stomp Error", lifecycleEvent.getException());
                               // toast("连接出错");
                                break;
                            case CLOSED:
                                mNeedConnect = true;
                                Log.e(TAG, "Stomp connection closed");
                               // toast("连接关闭");
                                break;
                        }
                    }
                });

        registerStompTopic();
    }

    //创建长连接，服务器端没有心跳机制的情况下，启动timer来检查长连接是否断开，如果断开就执行重连
    private void createStompClient() {
        connect();

        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.d(TAG, "forlan debug in timer ======================");
                if (mNeedConnect ) {//&& NetworkUtil.isNetworkConnected(getApplicationContext())
                    mStompClient = null;
                    connect();
                    Log.d(TAG, "forlan debug start connect WS_URI  ---------&&&&&&&&&&&---------");
                }
            }
        }, RECONNECT_TIME_INTERVAL, RECONNECT_TIME_INTERVAL);
    }

    //点对点订阅，根据用户名来推送消息
    private void registerStompTopic() {
        String mid = "007JP1358130001";
        mStompClient.topic("/topic/foodorder-merchant-" + mid )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<StompMessage>() {
                    @Override
                    public void accept(StompMessage stompMessage) throws Exception {
                        // {"orderDetails":{"Client9260133611":{"34":{"49":{"price":"10","num":1,"name":"name of 34 49","id":49}}}},"processer":"adfasdfsfsfsdfsfs","notifyText":"","preorderId":"100"}
                        //LogUtil.e("stompMessage",stompMessage);
                        Log.e(TAG, "stompMessage = " + stompMessage.getPayload());
                        //showMessage(stompMessage);
                    }
                });
    }

    @OnClick(R.id.send)
    public void onViewClicked() {

        createStompClient();
    }
}
