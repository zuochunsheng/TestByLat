package com.android.superplayer.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.android.superplayer.service.entity.MusicResult;

import java.io.IOException;

/**
 * anther: created by zuochunsheng on 2018/9/5 14 : 31
 * descript :
 */
public class MusicService extends Service {


    private static final String flag_service = "com.android.superplayer.Service";
    private MediaPlayer player = new MediaPlayer();
    private MusicResult music;

    @Override
    public void onCreate() {// 进行初始化操作
        MyBroadcastReceiver receiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(flag_service);
        registerReceiver(receiver,intentFilter);


        super.onCreate();


    }

    public class MyBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int newmusic = intent.getIntExtra("newmusic", -1);// 1 新音乐 , -1 暂停 在播放

            if(newmusic != -1 ){
                music = (MusicResult) intent.getSerializableExtra("music");
                if(music != null){
                    playMusic(music);
                }

            }


        }
    }

    /*
     * 播放歌曲
     */
    public void playMusic(MusicResult music){
        if(player != null ){
            // 停止播放
            player.stop();
            player.reset();

            try {
                // 获取播放歌曲路径
                player.setDataSource(music.getPath());
                //  准备
                player.prepare();
                // 播放
                player.start();

            } catch (Exception e) {
                e.printStackTrace();
            }


        }


    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
