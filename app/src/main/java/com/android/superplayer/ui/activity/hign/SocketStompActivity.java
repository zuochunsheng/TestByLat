package com.android.superplayer.ui.activity.hign;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.superplayer.R;
import com.android.superplayer.config.LogUtil;
import com.android.superplayer.util.socketutil.Config;
import com.android.superplayer.util.socketutil.WebscoketUtil;
import com.android.superplayer.util.socketutil.bean.WebSocketBean;


import okhttp3.WebSocket;
import rx.Subscriber;
import rx.functions.Action1;
import ua.naiksoftware.stomp.ConnectionProvider;
import ua.naiksoftware.stomp.LifecycleEvent;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.client.StompClient;
import ua.naiksoftware.stomp.client.StompMessage;

/**
 * @time : 2019/1/22 11:31
 * @author : zcs
 * @description : 接收广播实例：
 * 2 -1
 */

public class SocketStompActivity extends Activity {

    private TextView serverMessage;
    private Button start;
    private Button stop;
    private Button send;
    private EditText editText;
    private Button cheat;
    private Button stomp;

    private StompClient mStompClient;
    private static final String TAG = "zuo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_stomp);
        bindView();


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建client 实例
                createStompClient();
                //订阅消息
                //registerStompTopic();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStompClient.send("/app/welcome","{\"name\":\""+editText.getText()+"\"}")
                        .subscribe(new Subscriber<Void>() {
                            @Override
                            public void onCompleted(){

                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                toast("发送错误");
                            }

                            @Override
                            public void onNext(Void aVoid) {

                            }
                        });
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStompClient.disconnect();
            }
        });


        // 另一个页面
        stomp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SocketStompActivity.this,StompActivity.class));
                if(mStompClient != null) {
                    mStompClient.disconnect();
                }
                //finish();
            }
        });

    }
    private void bindView() {
        serverMessage = (TextView) findViewById(R.id.serverMessage);
        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);
        send = (Button) findViewById(R.id.send);
        editText = (EditText) findViewById(R.id.clientMessage);
        cheat = (Button) findViewById(R.id.cheat);
        stomp = (Button) findViewById(R.id.stomp);
    }




    private void showMessage(final StompMessage stompMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                serverMessage.setText("stomp command is --->"+stompMessage.getStompCommand() +" body is --->"+stompMessage.getPayload());
            }
        });
    }

    //创建client 实例
    private void createStompClient() {

        mStompClient = Stomp.over(WebSocket.class, Config.WS_URI);

        //mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, Config.WS_URI);
        mStompClient.connect();

        mStompClient.lifecycle().subscribe(new Action1<LifecycleEvent>() {
            @Override
            public void call(LifecycleEvent lifecycleEvent) {
                switch (lifecycleEvent.getType()) {
                    case OPENED:
                        Log.d(TAG, "Stomp connection opened");
                        toast("连接已开启");
                        break;

                    case ERROR:
                        Log.e(TAG, "Stomp Error", lifecycleEvent.getException());
                        toast("连接出错");
                        break;
                    case CLOSED:
                        Log.d(TAG, "Stomp connection closed");
                        toast("连接关闭");
                        break;
                }
            }
        });

        WebscoketUtil.init().createConnect();

        WebscoketUtil.init().registerCallback(new WebscoketUtil.Callback() {
            @Override
            public void onGetNewMessage(WebSocketBean socketBean) {
                LogUtil.e("");
            }
        });

    }

    //订阅消息
    private void registerStompTopic() {
        mStompClient.topic("/topic/getResponse").subscribe(new Action1<StompMessage>() {
            @Override
            public void call(StompMessage stompMessage) {
                Log.e(TAG, "call: " +stompMessage.getPayload() );
                showMessage(stompMessage);
            }
        });

    }

    private void toast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SocketStompActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        });
    }


}
