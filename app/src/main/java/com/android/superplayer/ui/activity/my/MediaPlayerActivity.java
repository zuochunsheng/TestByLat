package com.android.superplayer.ui.activity.my;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.superplayer.R;
import com.android.superplayer.adapter.MusicAdapter;
import com.android.superplayer.base.BaseActivity;
import com.android.superplayer.permission.IPermission;
import com.android.superplayer.permission.PermissionUtil;
import com.android.superplayer.service.MusicService;
import com.android.superplayer.service.entity.MusicResult;
import com.android.superplayer.util.MusicUtil;
import com.android.superplayer.util.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @author zuochunsheng
 * @time 2018/9/5 11:12
 * <p>
 * 播放模式 ： 列表循环， 单曲循环，随机播放
 */

public class MediaPlayerActivity extends BaseActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.list)
    ListView listView;
    @BindView(R.id.seekBar)
    SeekBar seekBar;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.btn_top)
    ImageButton btnTop;
    @BindView(R.id.btn_play)
    ImageButton btnPlay;
    @BindView(R.id.btn_bottom)
    ImageButton btnBottom;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;

    private static final String flag_activity = "com.android.superplayer.Activity";
    private MusicAdapter musicAdapter;
    private List<MusicResult> oList;

    private Context context = this;
    private MusicResult music;

    private int index = 0;

    private int state = 0x11; // 0x11 : 为第一次播放歌曲  ；  0x12 : 暂停 ； 0x13  继续播放

    @Override
    protected int getLayoutId() {
        return R.layout.activity_media_player;
    }

    @Override
    protected void initViewAndData() {


        checkExternalStoragePermission();

    }

    // 检查 权限
    private void checkExternalStoragePermission() {

        PermissionUtil
                .getInstance(this)
                .requestRunTimePermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        new IPermission() {
                            @Override
                            public void onGranted() {
                                initData();
                            }

                            @Override
                            public void onDenied(List<String> deniedPermission) {
                                ToastUtil.showToast("存储权限 已被禁止");
                            }
                        }
                );


    }

    // 初始化数据
    private void initData() {
        oList = MusicUtil.getMusicDate(this);
        musicAdapter = new MusicAdapter(oList, this);

        listView.setAdapter(musicAdapter);
        listView.setOnItemClickListener(oClickListener);

        seekBarOnChange();

        MyBroadcastActivity receiver = new MyBroadcastActivity();
        IntentFilter intentFilter = new IntentFilter(flag_activity);
        registerReceiver(receiver, intentFilter);

        // 启动服务
        Intent intent = new Intent(context, MusicService.class);
        startService(intent);


    }

    private void seekBarOnChange(){

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            // 当拖动条正在拖动 调用该方法
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            // 当开始 拖动 拖动条 后 调用该方法
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            // 当拖动条停止后 调用该方法
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Intent intent = new Intent("com.android.superplayer.Service");
               // intent.putExtra("music", music);
               // intent.putExtra("newmusic", 1);// 1 新音乐 ；
                intent.putExtra("progress", seekBar.getProgress());//进度条位置 ；
                sendBroadcast(intent);

            }
        });

    }


    // list 单项单击事件
    private AdapterView.OnItemClickListener oClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            index = position;//当前下标
            music = oList.get(position);

            Intent intent = new Intent("com.android.superplayer.Service");
            intent.putExtra("music", music);
            intent.putExtra("newmusic", 1);// 1 新音乐 ；
            sendBroadcast(intent);


        }
    };


    /*
     * 服务  发过来的广播
     */
    public class MyBroadcastActivity extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            int state = intent.getIntExtra("state", -1);
            switch (state) {
                case 0x12: // 暂停的图片

                    btnPlay.setImageResource(R.mipmap.all_no);
                    break;

                case 0x11:
                case 0x13:// 播放的图片

                    btnPlay.setImageResource(R.mipmap.all);
                    break;
            }

            int curposition = intent.getIntExtra("curposition", -1);
            int duration = intent.getIntExtra("duration", -1);

            if(curposition != -1){
               seekBar.setProgress( (int)(curposition*1.0 /duration * 100));


               time.setText(inittime(curposition,duration));

            }

            boolean over = intent.getBooleanExtra("over", false);
            if (over) {//
                //播放模式  下一首
                btnPlay.performClick() ;//触发事件

            }
        }

    }
    // 将毫秒 转为 分秒
    private String inittime(int cur ,int dur){
        int cur_fen = cur / 1000 / 60 ;// 分
        int cur_miao = cur / 1000 % 60 ;// 秒

        int dur_fen = cur / 1000 / 60 ;// 分
        int dur_miao = cur / 1000 % 60 ;// 分

        // 01：20  ，10：21
        return  getT(cur_fen)+":" + getT(cur_miao)  + "/"  + getT(dur_fen)+":" + getT(dur_miao);
    }
    private String getT(int time){
        if(time < 10){
           return  "0" +time;
        }else {
            return  "" +time;
        }

    }


    @OnClick({R.id.rl_back, R.id.tv_right})
    public void onViewOtherClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_right://播放模式

                break;
        }
    }

    @OnClick({R.id.btn_top, R.id.btn_play, R.id.btn_bottom})
    public void onViewClicked(View view) {
        Intent intent = new Intent("com.android.superplayer.Service");

        switch (view.getId()) {
            case R.id.btn_top://上一曲
                if (index == 0) {
                    index = oList.size() - 1;
                } else {
                    index--;

                }
                music = oList.get(index);

                intent.putExtra("music", music);
                intent.putExtra("newmusic", 1);// 1 新音乐 ；


                break;
            case R.id.btn_play:// 播放 暂停 ,
                if (music == null) {//如果当前没有播放歌曲 即第一次进入  播放第一首歌曲
                    music = oList.get(index);// 0
                    intent.putExtra("music", music);
                    // intent.putExtra("newmusic", 1);// 1 新音乐 ；
                }

                intent.putExtra("isPlay", 1);//当前是否正在播放歌曲

                break;
            case R.id.btn_bottom://下一曲
                if (index == (oList.size() - 1)) {
                    index = 0;
                } else {
                    index++;
                }
                music = oList.get(index);

                intent.putExtra("music", music);
                intent.putExtra("newmusic", 1);// 1 新音乐 ；


                break;


        }
        sendBroadcast(intent);
    }


}
