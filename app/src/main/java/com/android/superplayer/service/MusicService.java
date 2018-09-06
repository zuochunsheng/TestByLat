package com.android.superplayer.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.superplayer.config.LogUtil;
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

    private int state = 0x11; // 0x11 : 为第一次播放歌曲  ；  0x12 : 暂停 ； 0x13  继续播放

    private int curposition;
    private int duration;
    private MyBroadcastReceiver receiver;

    @Override
    public void onCreate() {// 进行初始化操作
        receiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(flag_service);
        registerReceiver(receiver, intentFilter);


        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Intent intent = new Intent("com.android.superplayer.Activity");
                intent.putExtra("over", true);

                sendBroadcast(intent);
                curposition = 0;
                duration = 0;

            }
        });
        super.onCreate();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int newmusic = intent.getIntExtra("newmusic", -1);// 1 新音乐


            if (newmusic != -1) {
                music = (MusicResult) intent.getSerializableExtra("music");
                if (music != null) {
                    playMusic(music);
                    state = 0x12;

                }

            }

            int progress = intent.getIntExtra("progress", -1);
            if (progress != -1) {

                curposition = (int) (progress * 1.0 / 100 * duration);// 将当前位置转换成毫秒

                player.seekTo(curposition);

            }


            int isPlay = intent.getIntExtra("isPlay", -1);//  三种状态
            if (isPlay != -1) {
                //0x11 : 为第一次播放歌曲  ；  0x12 : 暂停 ； 0x13  继续播放
                switch (state) {
                    case 0x11:
                        music = (MusicResult) intent.getSerializableExtra("music");
                        playMusic(music);// 里面有改变状态的 action
                        state = 0x12;
                        break;

                    case 0x12:
                        player.pause();//暂停播放
                        state = 0x13;

                        // 将当前状态发送给 activity
                        sendBroadToActivity();
                        break;
                    case 0x13:

                        player.start();//继续播放
                        state = 0x12;
                        // 将当前状态发送给 activity
                        sendBroadToActivity();
                        break;

                }


            }


        }
    }

    // 将当前状态发送给 activity
    private void sendBroadToActivity() {

        Intent intent2 = new Intent("com.android.superplayer.Activity");
        intent2.putExtra("state", state);
        sendBroadcast(intent2);

    }


    /*
     * 播放歌曲
     */
    public void playMusic(MusicResult music) {
        if (player != null) {
            // 停止播放
            player.stop();
            player.reset();

            try {
                // 获取播放歌曲路径  重新设置要播放的音频
                player.setDataSource(music.getPath());

               // player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                //  准备 预加载音频
                player.prepare();
                // 开始播放
                player.start();

                duration = player.getDuration();//获取当前歌曲时长


                //将进度条 实时更新
                //
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        LogUtil.e(Thread.currentThread().getName()+"......run...");

                        while (curposition < duration) {

                            try {
                                // 每秒更新一下
                                sleep(1000);

                                curposition = player.getCurrentPosition();

                                Intent intent = new Intent("com.android.superplayer.Activity");
                                intent.putExtra("curposition", curposition);
                                intent.putExtra("duration", duration);
                                intent.putExtra("state", state);
                                sendBroadcast(intent);
                            } catch (Exception e) {


                            }

                        }


                    }
                }.start();

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


//    @Override
//    public void unregisterReceiver(BroadcastReceiver receiver) {
//        unregisterReceiver(receiver);
//        super.unregisterReceiver(receiver);
//    }


    // 设置音量
    private void setVolume() {
        player.setVolume(0, 100);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        if (player.isPlaying()) {
            player.stop();//停止音频的播放
        }
        player.release();//释放资源
        player = null;


        //关闭线程
        Thread.currentThread().interrupt();
        stopForeground(true);


    }


}
