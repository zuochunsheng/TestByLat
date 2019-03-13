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
import okhttp3.WebSocket;
import rx.functions.Action1;
import ua.naiksoftware.stomp.LifecycleEvent;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.client.StompClient;
import ua.naiksoftware.stomp.client.StompMessage;

/**
 * @time : 2019/1/22 20:51
 * @author : zcs
 * @description : 跟 CheatActivity 一样的
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
        mStompClient = Stomp.over(WebSocket.class, Config.WS_URI);
        mStompClient.connect();
        mStompClient.lifecycle().subscribe(new Action1<LifecycleEvent>() {
            @Override
            public void call(LifecycleEvent lifecycleEvent) {

                //关注lifecycleEvent的回调来决定是否重连
                switch (lifecycleEvent.getType()) {
                    case OPENED:
                        mNeedConnect = false;
                        Log.d(TAG, "forlan debug stomp connection=========opened");
                        break;
                    case ERROR:
                        mNeedConnect = true;
                        Log.e(TAG, "forlan debug stomp connection error is ======", lifecycleEvent.getException());
                        Log.e(TAG, "forlan debug stomp connection error is ======"+lifecycleEvent.getMessage()+",getType :" +lifecycleEvent.getType());
                        break;
                    case CLOSED:
                        mNeedConnect = true;
                        Log.e(TAG, "forlan debug stomp connection closed======");
                        break;
                }
            }
        });
       // registerStompTopic();
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
        mStompClient.topic("user/007JP1358130001/msg").subscribe(new Action1<StompMessage>() {
            @Override
            public void call(StompMessage stompMessage) {
                Log.d(TAG, "forlan debug msg is " + stompMessage.getPayload());
            }
        });
    }

    @OnClick(R.id.send)
    public void onViewClicked() {

        createStompClient();
    }
}
