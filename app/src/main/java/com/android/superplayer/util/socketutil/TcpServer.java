package com.android.superplayer.util.socketutil;

import android.util.Log;

import com.android.superplayer.eventbus.EBBean;
import com.android.superplayer.eventbus.EBConst;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * anther: created by zuochunsheng on 2019/1/22 10 : 17
 * description :
 */
public class TcpServer {

    private static ServerSocket serverSocket;
    private static Socket socket;

    public static void startServer(){
        if (serverSocket == null){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        serverSocket = new ServerSocket(8080);
                        Log.e("tcp" , "服务器等待连接中");
                        socket = serverSocket.accept();
                        Log.e("tcp" , "客户端连接上来了");
                        InputStream inputStream = socket.getInputStream();
                        byte[] buffer = new byte[1024];
                        int len = -1;
                        while ((len = inputStream.read(buffer)) != -1) {
                            String data = new String(buffer, 0, len);
                            Log.e("tcp" , "收到客户端的数据-----------------------------:" + data);
                            EventBus.getDefault().post(new EBBean(EBConst.app_socket_server,data));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();

                    }finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            serverSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        socket = null;
                        serverSocket = null;
                    }
                }
            }).start();
        }
    }

    public static void sendTcpMessage(final String msg){
        if (socket != null && socket.isConnected()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        socket.getOutputStream().write(msg.getBytes());
                        socket.getOutputStream().flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

}
