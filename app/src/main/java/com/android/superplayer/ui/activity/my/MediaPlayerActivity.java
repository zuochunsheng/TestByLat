package com.android.superplayer.ui.activity.my;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
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
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @author zuochunsheng
 * @time 2018/9/5 11:12
 * <p>
 * 播放模式 ： 列表循环， 单曲循环，随机播放
 */

public class MediaPlayerActivity extends BaseActivity implements SensorEventListener {

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

    @BindView(R.id.playaccelerometer)
    TextView playaccelerometer;

    private static final String flag_activity = "com.android.superplayer.Activity";
    private MusicAdapter musicAdapter;
    private List<MusicResult> oList;

    private Context context = this;
    private MusicResult music;

    private int index = 0;

    private int state = 0x11; // 0x11 : 为第一次播放歌曲  ；  0x12 : 暂停 ； 0x13  继续播放

    private int flag = 0;//播放模式  0 列表 , 1 单曲,  2 随机

    private SharedPreferences sp;
    private SharedPreferences.Editor edit;
    private MyBroadcastActivity receiver;


    private SensorManager sensorManager = null;//传感器
    private Vibrator vibrator = null;//震动

    private int clicktime = 0;//摇一摇 accelerometer 切换

    @Override
    protected int getLayoutId() {
        return R.layout.activity_media_player;
    }

    @Override
    protected void initViewAndData() {

        sp = getSharedPreferences("com.android.superplayer.data", MODE_PRIVATE);
        edit = sp.edit();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //取得震动服务的句柄
        vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        //加速度传感器（accelerometer）、陀螺仪（gyroscope）、环境光照传感器（light）、磁力传感器（magnetic field）、方向传感器（orientation）、压力传感器（pressure）、距离传感器（proximity）和温度传感器（temperature）。
// http://www.open-open.com/lib/view/open1378259498734.html
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        //摇一摇
        if (sp.getInt("play_accelerometer", 0) == 0) {
            //默认摇一摇是打开的
            clicktime = 0;
            playaccelerometer.setText("摇一摇关闭");
            // playaccelerometer.setBackgroundResource(R.drawable.ic_alarm_on_black_24dp);
        } else {
            clicktime = 1;
            playaccelerometer.setText("摇一摇打开");
            // playaccelerometer.setBackgroundResource(R.drawable.ic_alarm_off_black_24dp);
        }

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

        receiver = new MyBroadcastActivity();
        IntentFilter intentFilter = new IntentFilter(flag_activity);
        registerReceiver(receiver, intentFilter);

        // 启动服务
        Intent intent = new Intent(context, MusicService.class);
        startService(intent);


    }

    /*
     *进度条
     */
    private void seekBarOnChange() {

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

            if (curposition != -1) {
                seekBar.setProgress((int) (curposition * 1.0 / duration * 100));


                time.setText(inittime(curposition, duration));

            }

            boolean over = intent.getBooleanExtra("over", false);
            if (over) {//
                //播放模式  0 列表 , 1 单曲,  2 随机
                setQiegeByFlag();

            }
        }

    }

    // 根据模式 切歌
    private void setQiegeByFlag() {
        Intent intent3 = new Intent("com.android.superplayer.Service");
        switch (flag) {// 改变样式 ，作用 在播放完歌曲后 起作用
            case 0:
                if (index == (oList.size() - 1)) {
                    index = 0;
                } else {
                    index++;
                }

                break;

            case 1:

                break;

            case 2:
                index = new Random().nextInt(oList.size());

                break;

        }
        music = oList.get(index);

        intent3.putExtra("music", music);
        intent3.putExtra("newmusic", 1);// 1 新音乐 ；
        sendBroadcast(intent3);

        edit.putInt("index", index);
        edit.commit();


    }

    // 将毫秒 转为 分秒
    private String inittime(int cur, int dur) {
        int cur_fen = cur / 1000 / 60;// 分
        int cur_miao = cur / 1000 % 60;// 秒

        int dur_fen = dur / 1000 / 60;// 分
        int dur_miao = dur / 1000 % 60;// 分

        // 01：20  ，10：21
        return getT(cur_fen) + ":" + getT(cur_miao) + "/" + getT(dur_fen) + ":" + getT(dur_miao);
    }

    private String getT(int time) {
        if (time < 10) {
            return "0" + time;
        } else {
            return "" + time;
        }

    }


    @OnClick({R.id.rl_back, R.id.tv_right,R.id.playaccelerometer,R.id.click_share})
    public void onViewOtherClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_right://播放模式  0 列表 , 1 单曲,  2 随机
                flag++;
                if (flag > 2) {
                    flag = 0;
                }

                switch (flag) {// 改变样式 ，作用 在播放完歌曲后 起作用
                    case 0:
                        ToastUtil.showToastInUiThread(this, "列表循环");
                        tvRight.setText("列表循环");
                        break;

                    case 1:
                        ToastUtil.showToastInUiThread(this, "单曲循环");
                        tvRight.setText("单曲循环");
                        break;

                    case 2:
                        ToastUtil.showToastInUiThread(this, "随机播放");
                        tvRight.setText("随机播放");
                        break;

                }

                break;
            case R.id.playaccelerometer:
                if(clicktime == 0){
                    //当前是摇一摇打开的状态--> 关闭摇一摇
                    clicktime = 1;
                    //playaccelerometer.setBackgroundResource(R.drawable.ic_alarm_off_black_24dp);
                    playaccelerometer.setText("摇一摇打开");
                    edit.putInt("play_accelerometer", 1).commit();
                }else {
                    //关闭-->打开
                    clicktime = 0;
                    //playaccelerometer.setBackgroundResource(R.drawable.ic_alarm_on_black_24dp);
                    playaccelerometer.setText("摇一摇关闭");
                    edit.putInt("play_accelerometer", 0).commit();
                }
                break;

            case R.id.click_share:// 分享
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT,"我的博客地址：https://github.com/zuochunsheng/supermarket-java");
                shareIntent.setType("text/plain");
                //设置分享列表
                startActivity(Intent.createChooser(shareIntent,"分享到"));
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


    // 作用待测试
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { // 返回键 执行 home键的 功能
            edit.putInt("state", state);
            edit.putInt("index", index);
            edit.commit();

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            return true;

        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 0, 1, "退出");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("提示");
                builder.setMessage("你确定要退出应用么？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(context, MusicService.class);
                        stopService(intent);
                        edit.clear();
                        edit.commit();

                        // 如果有线程 也要销毁
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
                break;

        }
        return super.onMenuItemSelected(featureId, item);
    }

    // 注销广播
//    @Override
//    public void unregisterReceiver(BroadcastReceiver receiver) {
//        unregisterReceiver(receiver);
//        super.unregisterReceiver(receiver);
//
//    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sp.getInt("play_accelerometer", 0) == 0) {
            // 传感器报告新的值
            int sensorType = sensorEvent.sensor.getType();
            //values[0]:X轴，values[1]：Y轴，values[2]：Z轴
            float[] values = sensorEvent.values;
            if (sensorType == Sensor.TYPE_ACCELEROMETER) {
                if ((Math.abs(values[0]) > 17 || Math.abs(values[1]) > 17 || Math
                        .abs(values[2]) > 17)) {
                    Log.e("slack", "sensor x values[0] = " + values[0]);
                    Log.e("slack", "sensor y values[1] = " + values[1]);
                    Log.e("slack", "sensor z values[2] = " + values[2]);

                    setQiegeByFlag();
                    //摇动手机后，再伴随震动提示~~
                    vibrator.vibrate(500);
                }

            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //传感器精度的改变
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        sensorManager.unregisterListener(this);
    }
}
