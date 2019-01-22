package com.android.superplayer.util.socketutil;


import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.android.superplayer.config.LogUtil;
import com.android.superplayer.util.socketutil.bean.WebSocketBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * @author xyjian
 * @version V1.0.0
 * @Description: {websocket长连接}
 * @date 2018/9/29
 */
public class WebscoketUtil extends WebSocketListener {
    private static final int DEFAULT_TIMEOUT = 20;
    private static WebscoketUtil webscoketUtil;
    private WebSocket webSocket;
    private List<Callback> mCallbacks = new ArrayList<>(2);
    private int reConnectTotalTimes = 10;
    private int reConnectTimes = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 111:
                    LogUtil.e("websocket  handleMessage" + mCallbacks.size());
                    for (WebscoketUtil.Callback callback : mCallbacks) {
                        callback.onGetNewMessage(socketBean);
                    }
                    break;
                case 222:
                    cancleConnect();
                    createConnect();
                    break;
            }
        }
    };
    private WebSocketBean socketBean;

    public static WebscoketUtil init() {
        synchronized (WebscoketUtil.class) {
            if (webscoketUtil == null) {
                webscoketUtil = new WebscoketUtil();
            }
        }
        return webscoketUtil;
    }

    private WebscoketUtil() {
    }

    public void createConnect() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
        Request request = new Request.Builder()
                //.url("ws://echo.websocket.org")
                .url(Config.WS_URI)
                //.url(API.BASE_URL_WS)
                .build();
        webSocket = okHttpClient.newWebSocket(request, this);
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
        LogUtil.e("websocket  onOpen=" + response.toString());
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        super.onMessage(webSocket, text);
        LogUtil.e("websocket  onMessage " + text);
        if (!TextUtils.isEmpty(text)) {

            try {
                //socketBean = new Gson().fromJson(text, WebSocketBean.class);
                handler.sendEmptyMessage(111);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        super.onMessage(webSocket, bytes);
        LogUtil.e("websocket  onMessage " + bytes.toString());

    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        super.onClosing(webSocket, code, reason);
        LogUtil.e("websocket  onClosing" + reason);
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        super.onClosed(webSocket, code, reason);
        LogUtil.e("websocket  onClosed" + reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, @Nullable Response response) {
        super.onFailure(webSocket, t, response);
        t.printStackTrace();
        LogUtil.e("websocket  onFailure" + t.getMessage());
        if (reConnectTimes <= reConnectTotalTimes) {
            reConnectTimes++;
            handler.sendEmptyMessageDelayed(222, 6000);
        }

    }

    public void cancleConnect() {
        if (webSocket != null) {
            webSocket.close(4000, "activity destroy");
//            webSocket.cancel();
        }
        handler.removeMessages(111);
        handler.removeMessages(222);
        webSocket = null;
    }

    public void sendMsg(String msg) {
        if (webSocket != null) {
            webSocket.send(msg);
            LogUtil.e("<sendMsg>" + msg);
        }
    }

    private void reConnect() {

        cancleConnect();
        createConnect();
    }


    // Callbacks

    public void registerCallback(Callback callback) {
        mCallbacks.add(callback);
    }

    public void unregisterCallback(Callback callback) {
        mCallbacks.remove(callback);
    }

    public void removeCallbacks() {
        mCallbacks.clear();
    }


    public interface Callback {
        void onGetNewMessage(WebSocketBean socketBean);
    }
}
