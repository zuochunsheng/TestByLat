package com.android.superplayer.util.socketutil;

import android.util.Log;

import com.android.superplayer.eventbus.EBBean;
import com.android.superplayer.eventbus.EBConst;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * anther: created by zuochunsheng on 2019/1/22 10 : 16
 * description :
 */
public class TcpClient {

    public static final String IP_ADDR = "https://ops.ledcas.com/os-transaction/messages";//服务器地址
    public static final int PORT = 80;//服务器端口号


    public static Socket socket;

    public static void startClient(final String address ,final int port){
        if (address == null){
            return;
        }
        if (socket == null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.e("tcp", "启动客户端");
                        socket = new Socket(address, port);
                        Log.e("tcp", "客户端连接成功");
                        PrintWriter pw = new PrintWriter(socket.getOutputStream());

                        InputStream inputStream = socket.getInputStream();

                        byte[] buffer = new byte[1024];
                        int len = -1;
                        while ((len = inputStream.read(buffer)) != -1) {
                            String data = new String(buffer, 0, len);
                            Log.e("tcp", "收到服务器的数据---------------------------------------------:" + data);
                            EventBus.getDefault().post(new EBBean(EBConst.app_sockct_client,data));
                        }
                        Log.e("tcp", "客户端断开连接");
                        pw.close();

                    } catch (Exception EE) {
                        EE.printStackTrace();
                        Log.e("tcp", "客户端无法连接服务器");

                    }finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        socket = null;
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
