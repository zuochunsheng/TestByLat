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


import org.reactivestreams.Subscriber;

import io.reactivex.CompletableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.WebSocket;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.LifecycleEvent;
import ua.naiksoftware.stomp.dto.StompMessage;


/**
 * @time : 2019/1/22 11:31
 * @author : zcs
 * @description : 接收广播实例：
 * 2 -1
 */

public class SocketStompActivity extends Activity {

    private TextView serverMessage;
    private Button start;
    private Button register;
    private Button stop;
    private Button send;
    private EditText editText;
    //private Button cheat;
    private Button stomp;

    private StompClient mStompClient;
    private static final String TAG = "stomp";

    protected CompletableTransformer applySchedulers() {
        return upstream -> upstream
                .unsubscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


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
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //订阅消息
                registerStompTopic();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mStompClient.send("/app/welcome", "{\"name\":\""+editText.getText()+"\"}")
//                        .compose(applySchedulers())
//                        .subscribe(() -> {
//                            Log.d(TAG, "REST echo send successfully");
//                        }, throwable -> {
//                            Log.e(TAG, "Error send REST echo", throwable);
//                            toast(throwable.getMessage());
//                        });

                mStompClient.send("/app/welcome","{\"name\":\""+editText.getText()+"\"}")
                        .subscribe(() -> {
                            Log.d(TAG, "REST echo send successfully");
                        }, throwable -> {
                            Log.e(TAG, "Error send REST echo", throwable);
                            toast(throwable.getMessage());
                        } );
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
        register = (Button) findViewById(R.id.register);
        stop = (Button) findViewById(R.id.stop);
        send = (Button) findViewById(R.id.send);
        editText = (EditText) findViewById(R.id.clientMessage);
       // cheat = (Button) findViewById(R.id.cheat);
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
        LogUtil.e("stomp","createStompClient");
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
                                Log.e(TAG, "Stomp connection opened");
                                toast("连接已开启");
                                break;
                            case ERROR:
                                Log.e(TAG, "Stomp Error", lifecycleEvent.getException());
                                toast("连接出错");
                                break;
                            case CLOSED:
                                Log.e(TAG, "Stomp connection closed");
                                toast("连接关闭");
                                break;
                            case FAILED_SERVER_HEARTBEAT:
                                Log.e(TAG, "Stomp FAILED_SERVER_HEARTBEAT");
                                toast("FAILED_SERVER_HEARTBEAT");
                                break;
                        }
                    }
                });

//        WebscoketUtil.init().createConnect();
//
//        WebscoketUtil.init().registerCallback(new WebscoketUtil.Callback() {
//            @Override
//            public void onGetNewMessage(WebSocketBean socketBean) {
//                LogUtil.e("registerCallback");
//            }
//        });

    }

    //订阅消息
    private void registerStompTopic() {
        //多次订阅
        LogUtil.e(TAG,"registerStompTopic");
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

    private void toast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SocketStompActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        });
    }


}
