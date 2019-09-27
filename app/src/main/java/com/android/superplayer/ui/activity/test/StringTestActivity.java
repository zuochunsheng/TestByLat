package com.android.superplayer.ui.activity.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.superplayer.R;
import com.android.superplayer.config.LogUtil;
import com.android.superplayer.util.ActivityUtil;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StringTestActivity extends Activity {


    @BindString(R.string.app_name)
    String app_name;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ButterKnife.bind(this);
        setContentView(R.layout.activity_string_test);



        TextView textView1 = (TextView) findViewById(R.id.textView);
        //textView.setText("text----1");

        //LogUtil.e("需要注解当前的布局 app_name =" + app_name);

        //textView1.setText(app_name);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.getInstance().onNext(StringTestActivity.this, BActivity.class);
            }
        });

        LogUtil.e("A Activity ----onCreate");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.e("A Activity ----onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.e("A Activity ----onStart");
    }
    //在重新创建Activity的时候在onStart之后回调onRestoreInstanceState。
    // 其中Bundle数据会传到onCreate（不一定有数据）和onRestoreInstanceState（一定有数据）
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        LogUtil.e("A Activity ----onRestoreInstanceState");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.e("A Activity ----onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.e("A Activity ----onPause");
    }


    //onStop 之前 执行
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtil.e("A Activity ----onSaveInstanceState");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.e("A Activity ----onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.e("A Activity ----onDestroy");
    }



}
