package com.android.superplayer.ui.activity.hign;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.superplayer.R;
import com.android.superplayer.util.socketutil.Config;

import org.java_websocket.WebSocket;

import rx.Subscriber;
import rx.functions.Action1;
import ua.naiksoftware.stomp.LifecycleEvent;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.client.StompClient;
import ua.naiksoftware.stomp.client.StompMessage;

/**
 * @time : 2019/1/22 11:33
 * @author : zcs
 * @description : 点对点 、
 * 接收  2-2
 */
public class CheatActivity extends Activity {


    private EditText cheat;
    private Button send;
    private LinearLayout message;
    private StompClient mStompClient;

    private static final String TAG = "zuo";
    private boolean mNeedConnect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        bindView();
        createStompClient();
        //registerStompTopic();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 向/app/cheat发送Json数据
                mStompClient.send("/app/cheat","{\"userId\":\"lincoln\",\"message\":\""+cheat.getText()+"\"}")
                        .subscribe(new Subscriber<Void>() {
                            @Override
                            public void onCompleted() {
                                toast("发送成功");
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

    }

    private void bindView() {
        cheat = (EditText) findViewById(R.id.cheat);
        send = (Button) findViewById(R.id.send);
        message = (LinearLayout) findViewById(R.id.message);
    }



    private void createStompClient() {
        mStompClient = Stomp.over(WebSocket.class, Config.WS_URI);//ws://192.168.0.46:8080/hello/websocket
        mStompClient.connect();
        //Toast.makeText(CheatActivity.this,"开始连接 https://ops.ledcas.com/os-transaction/messages",Toast.LENGTH_SHORT).show();
        mStompClient.lifecycle().subscribe(new Action1<LifecycleEvent>() {
            @Override
            public void call(LifecycleEvent lifecycleEvent) {
                //关注lifecycleEvent的回调来决定是否重连
                switch (lifecycleEvent.getType()) {
                    case OPENED:
                        mNeedConnect = false;
                        Log.e(TAG, "Stomp connection opened");
                        toast("连接已开启");
                        break;

                    case ERROR:
                        mNeedConnect = true;
                        Log.e(TAG, "Stomp Error", lifecycleEvent.getException());
                        toast("连接出错");
                        break;
                    case CLOSED:
                        mNeedConnect = true;
                        Log.e(TAG, "Stomp connection closed");
                        toast("连接关闭");
                        break;
                }
            }
        });
    }

    // 接收/user/xiaoli/message路径发布的消息
    private void registerStompTopic() {
        mStompClient.topic("/user/007JPqweasdqwert/message").subscribe(new Action1<StompMessage>() {
            @Override
            public void call(StompMessage stompMessage) {
                Log.e(TAG, "call: " +stompMessage.getPayload() );
                showMessage(stompMessage);
            }
        });
    }

    private void showMessage(final StompMessage stompMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView text = new TextView(CheatActivity.this);
                text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                text.setText(System.currentTimeMillis() +" body is --->"+stompMessage.getPayload());
                message.addView(text);
            }
        });
    }


    private void toast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(CheatActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        });
    }

}
