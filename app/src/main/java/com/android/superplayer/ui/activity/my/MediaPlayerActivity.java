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
    private MusicAdapter musicAdapter ;
    private List<MusicResult> oList;

    private Context context = this;
    private MusicResult music;

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
        oList = MusicUtil.getMusicDate(this) ;
        musicAdapter = new MusicAdapter(oList,this) ;

        listView.setAdapter(musicAdapter);
        listView.setOnItemClickListener(oClickListener);

        MyBroadcastActivity receiver = new MyBroadcastActivity();
        IntentFilter intentFilter = new IntentFilter(flag_activity);
        registerReceiver(receiver,intentFilter);

        // 启动服务
        Intent intent = new Intent(context, MusicService.class);
        startService(intent) ;


    }


    private AdapterView.OnItemClickListener oClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            music = oList.get(position);

            Intent intent = new Intent("com.android.superplayer.Service");
            intent.putExtra("music",music);
            intent.putExtra("newmusic" ,1) ;// 1 新音乐 ；
            sendBroadcast(intent);


        }
    } ;




    /*
     *
     */
    public class MyBroadcastActivity extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }



    @OnClick({R.id.rl_back, R.id.tv_right, R.id.btn_top, R.id.btn_play, R.id.btn_bottom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_right://播放模式

                break;
            case R.id.btn_top://上一曲

                break;
            case R.id.btn_play://刚进入播放 播放 暂停

                break;
            case R.id.btn_bottom://下一曲

                break;
        }
    }
}
