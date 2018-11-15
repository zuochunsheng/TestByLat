package com.android.superplayer.ui.activity.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.superplayer.R;
import com.android.superplayer.util.duckdesign.OtherObiect;
import com.android.superplayer.util.duckdesign.SubDuck;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RxJavaActivity extends Activity {

    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.text2)
    TextView text2;
    @BindView(R.id.text3)
    TextView text3;
    @BindView(R.id.text4)
    TextView text4;
    @BindView(R.id.text5)
    TextView text5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        ButterKnife.bind(this);
    }



    @OnClick({R.id.text, R.id.text1, R.id.text2, R.id.text3, R.id.text4, R.id.text5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text:
                // 鸭子问题
                //https://www.cnblogs.com/mengdd/archive/2012/12/26/2834884.html
                SubDuck subDuck = new SubDuck();

                //
                OtherObiect otherObiect = new OtherObiect();
                subDuck.setFlyBehavior(otherObiect);
                subDuck.setQuackBehavior(otherObiect);

                subDuck.performFly();
                subDuck.performQuack();
                break;
            case R.id.text1:



                break;
            case R.id.text2:
                break;
            case R.id.text3:
                break;
            case R.id.text4:
                break;
            case R.id.text5:
                break;
        }
    }
}
