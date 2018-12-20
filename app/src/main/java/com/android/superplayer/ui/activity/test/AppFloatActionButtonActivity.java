package com.android.superplayer.ui.activity.test;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.superplayer.R;
import com.android.superplayer.base.BaseActivity;
import com.android.superplayer.config.LogUtil;
import com.android.superplayer.eventbus.EBBean;
import com.android.superplayer.eventbus.EBConst;
import com.android.superplayer.ui.widgit.FloatView2;
import com.android.superplayer.util.ActivityUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AppFloatActionButtonActivity extends BaseActivity {

    @BindView(R.id.skip)
    TextView skip;
    private FloatView2 floatView2;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_app_float_action_button;
    }

    @Override
    protected void initViewAndData() {

        floatView2 = new FloatView2(this);
        floatView2.createFloatView();
        floatView2.onFloatViewClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这边是点击悬浮按钮的响应事件
                LogUtil.e("点击悬浮按钮的响应事件");
            }
        });

        EventBus.getDefault().register(this);



    }

    //eventbug接受消息 根据类别区分处理 threadMode = ThreadMode.POSTING
    @Subscribe
    public void reEBBean(EBBean ebBean) {
        if (ebBean.getValue() == EBConst.app_start) {
            LogUtil.e("AppFloatActionButtonActivity === showFloatView");
            floatView2.showFloatView();

        }if (ebBean.getValue() == EBConst.app_stop) {
            LogUtil.e("AppFloatActionButtonActivity === hideFloatView");
            floatView2.hideFloatView();

        } else if (ebBean.getValue() == EBConst.app_destroy) {
            LogUtil.e("AppFloatActionButtonActivity === removeFloatView");
            floatView2.removeFloatView();

        }

    }




    @OnClick(R.id.skip)
    public void onViewClicked() {
        ActivityUtil.getInstance().onNext(this,ViewPageIndicatorActivity.class);

    }
}
