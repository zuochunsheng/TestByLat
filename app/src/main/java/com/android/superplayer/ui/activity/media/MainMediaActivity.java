package com.android.superplayer.ui.activity.media;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.aliyun.player.alivcplayerexpand.bean.LongVideoBean;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseHelper;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.aliyun.player.alivcplayerexpand.util.database.LongVideoDatabaseManager;
import com.android.superplayer.R;
import com.android.superplayer.base.BaseActivity;
import com.android.superplayer.bean.SingleSectionBean;
import com.android.superplayer.permission.IPermission;
import com.android.superplayer.permission.PermissionUtil;
import com.android.superplayer.util.ActivityUtil;
import com.google.gson.Gson;

import java.util.List;

public class MainMediaActivity extends BaseActivity {

    TextView tv1 ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_media;
    }

    @Override
    protected void initViewAndData() {
        tv1 =findViewById(R.id.tv1);

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermissions();

            }
        });
    }



    //  6.0 检查 权限
    public void checkPermissions() {
        PermissionUtil.getInstance(this)
                .requestRunTimePermission(new String[]{
                                Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        new IPermission() {
                            @Override
                            public void onGranted() {
                                initDataBase();
                                startActivity();
                            }

                            @Override
                            public void onDenied(List<String> deniedPermission) {
                               // UiUtils.showToast("授权未通过");
                            }
                        });

    }

    private void initDataBase(){
        DatabaseManager.getInstance().createDataBase(this, DatabaseHelper.DB_PATH);
        LongVideoDatabaseManager.getInstance().createDataBase(this);

    }
    private void startActivity(){
        //videoId
        String s = "{\"censorStatus\":\"success\",\"coverUrl\":\"http://alivc-demo-vod.aliyuncs.com/e91c8936f50b459d98b6b0ebe1edd7dd/snapshots/normal/F1F939D-16C7031F9C7-1103-1445-334-2638600001.jpg\",\"creationTime\":\"2019-08-08 15:46:58.0\",\"description\":\"\",\"dotList\":[],\"downloaded\":false,\"downloading\":false,\"duration\":\"32.0\",\"firstFrameUrl\":\"http://alivc-demo-vod.aliyuncs.com/e91c8936f50b459d98b6b0ebe1edd7dd/snapshots/normal/F1F939D-16C7031F9C7-1103-1445-334-2638600001.jpg\",\"isHomePage\":\"true\",\"isRecommend\":\"false\",\"isVip\":false,\"selected\":false,\"size\":\"39937363\",\"snapshotStatus\":\"success\",\"sort\":\"0\",\"tags\":\"今日推荐\",\"title\":\"探秘2018杭州云栖大会\",\"transcodeStatus\":\"success\",\"tvId\":\"\",\"tvName\":\"\",\"videoId\":\"e91c8936f50b459d98b6b0ebe1edd7dd\",\"watchPercent\":0}";

        Gson gson = new Gson();

        LongVideoBean longVideoBean = gson.fromJson(s,LongVideoBean.class) ;
        SingleSectionBean bean = new SingleSectionBean(longVideoBean);

        AlivcPlayerActivity.startAlivcPlayerActivity(this, bean.t);
    }
}
