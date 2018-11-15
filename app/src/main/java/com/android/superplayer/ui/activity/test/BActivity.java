package com.android.superplayer.ui.activity.test;

import android.app.Activity;
import android.os.Bundle;

import com.android.superplayer.R;
import com.android.superplayer.config.LogUtil;

public class BActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);


        LogUtil.e("B Activity ----onCreate" );
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.e("B Activity ----onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.e("B Activity ----onStart");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        LogUtil.e("B Activity ----onRestoreInstanceState");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.e("B Activity ----onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.e("B Activity ----onPause");
    }


    //onStop
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtil.e("B Activity ----onSaveInstanceState");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.e("B Activity ----onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.e("B Activity ----onDestroy");
    }


}
