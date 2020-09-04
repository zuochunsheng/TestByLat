package com.android.superplayer.ui.activity.hign;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.superplayer.R;
import com.android.superplayer.eventbus.EBBean;
import com.android.superplayer.eventbus.EBConst;
import com.android.superplayer.util.socketutil.TcpClient;
import com.android.superplayer.util.socketutil.TcpServer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @time : 2019/1/22 10:42
 * @author : zcs
 * @description : 1 实现tcp链接，把手机当做服务器跟客户端的综合体，可以不需要外网服务器，只需要手机连接wifi即可
 *  buyong
 */
public class SocketActivity extends Activity {

    @BindView(R.id.btn_start_server)
    Button btnStartServer;
    @BindView(R.id.btn_start_client)
    Button btnStartClient;
    @BindView(R.id.btn_send_client)
    Button btnSendClient;
    @BindView(R.id.btn_send_server)
    Button btnSendServer;

    @BindView(R.id.tv_server)
    TextView tvServer;
    @BindView(R.id.tv_client)
    TextView tvClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        ButterKnife.bind(this);


        Log.e("tcp" ,"ip地址:" + getIPAddress(this));

        //注册EventBus
        EventBus.getDefault().register(this);

    }

    @OnClick({R.id.btn_start_server, R.id.btn_start_client, R.id.btn_send_client,
            R.id.btn_send_server})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_start_server:
                //开启服务器
                TcpServer.startServer();
                break;
            case R.id.btn_start_client:
                //连接服务器
                Log.e("tcp" ,"ip地址:" + getIPAddress(getApplicationContext()));
                TcpClient.startClient( getIPAddress(getApplicationContext()) , 8080);//ip地址:192.168.232.2
                //TcpClient.startClient( TcpClient.WS_URI , TcpClient.PORT);
                break;
            case R.id.btn_send_client:
                //发送数据给客户端
                TcpServer.sendTcpMessage("321 发送数据给客户端");
                break;
            case R.id.btn_send_server:
                //发送数据给服务器
                TcpClient.sendTcpMessage("321 发送数据给服务器");
                break;

        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    //收到客户端的消息
    public void MessageClient(EBBean messageEvent) {
        if(messageEvent.getValue() == EBConst.app_sockct_client){
            tvClient.setText("客户端收到:" +messageEvent.getStringValue());
        }

    }

    //收到服务器的消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageServer(EBBean messageEvent) {
        if(messageEvent.getValue() == EBConst.app_socket_server){
            tvServer.setText("服务器收到:" +messageEvent.getStringValue());
        }

    }


    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
