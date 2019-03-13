package com.android.superplayer.ui.activity.test;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.superplayer.R;
import com.android.superplayer.base.BaseActivity;
import com.android.superplayer.config.LogUtil;
import com.android.superplayer.eventbus.EBBean;
import com.android.superplayer.eventbus.EBConst;
import com.android.superplayer.ui.activity.MainActivity;
import com.android.superplayer.ui.activity.my.PictureActivity;
import com.android.superplayer.ui.widgit.FloatView2;
import com.android.superplayer.ui.widgit.VerticalMarqueeView;
import com.android.superplayer.util.ActivityUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import app.demo.com.autotextlib.AutoVerticalScrollTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.android.superplayer.util.Metrics.sp2px;

public class AppFloatActionButtonActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.skip)
    TextView skip;

    @BindView(R.id.vmView)
    VerticalMarqueeView vmView;
    @BindView(R.id.auto_text)
    AutoVerticalScrollTextView auto_text;


    private FloatView2 floatView2;



    int[] res = {R.id.imageView_a,R.id.imageView_b,R.id.imageView_c,R.id.imageView_d};
    // a list of ImageViews
    private List<ImageView> mImageViewList = new ArrayList<>();

    // a flag to control opening action or closing action
    private boolean opened = false;
    // 轨迹半径
    int r = 150;




    @Override
    protected int getLayoutId() {
        return R.layout.activity_app_float_action_button;
    }

    @Override
    protected void initViewAndData() {






        // 竖直方向 滚动textView
        String[] datas = new String[]{
                "南海又开始动荡了", "菲律宾到处都在肇事", "这次为了一张审判废纸，菲律宾投入了多少成本呢", "测试数据4", "测试数据5为了长度不一样", "就把这条当做测试数据吧"
        };
        vmView.color(getResources().getColor(android.R.color.black))
                .textSize(sp2px(this, 15))
                .datas(datas).commit();
        vmView.startScroll();
        vmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AppFloatActionButtonActivity.this, "当前的索引为：" + vmView.getCurrentPosition(), Toast.LENGTH_SHORT).show();
            }
        });

        // 竖直方向 滚动textView  引用aar包
        //auto_text = findViewById(R.id.auto_text);
        auto_text.setTextSources("一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十");
//        auto_text.setTextSources("1234567890123456789012345678901234567890");
        auto_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //扇形初始化
        initView();

//        floatView2 = new FloatView2(AppFloatActionButtonActivity.this);
//        floatView2.createFloatView();
//        floatView2.onFloatViewClick(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //这边是点击悬浮按钮的响应事件
//                LogUtil.e("点击悬浮按钮的响应事件");
//            }
//        });
        popupHandler.sendEmptyMessageDelayed(0, 1000*3);

        EventBus.getDefault().register(this);
    }

    private Handler popupHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    floatView2 = new FloatView2(AppFloatActionButtonActivity.this);
                    floatView2.createFloatView();
                    floatView2.onFloatViewClick(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //这边是点击悬浮按钮的响应事件
                            LogUtil.e("点击悬浮按钮的响应事件");
                        }
                    });
                    break;
            }
        }

    };

    //eventbug接受消息 根据类别区分处理 threadMode = ThreadMode.POSTING
    @Subscribe
    public void reEBBean(EBBean ebBean) {
        if (ebBean.getValue() == EBConst.app_start) {
            LogUtil.e("AppFloatActionButtonActivity === showFloatView");
            floatView2.showFloatView();

        }
        if (ebBean.getValue() == EBConst.app_stop) {
            LogUtil.e("AppFloatActionButtonActivity === hideFloatView");
            floatView2.hideFloatView();

        } else if (ebBean.getValue() == EBConst.app_destroy) {
            LogUtil.e("AppFloatActionButtonActivity === removeFloatView");
            floatView2.removeFloatView();

        }

    }


    @OnClick(R.id.skip)
    public void onViewClicked() {
       // ActivityUtil.getInstance().onNext(this, ViewPageIndicatorActivity.class);
        ActivityUtil.getInstance().onNext(this, PictureActivity.class);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //必须要调用，否则内存中会一直无限循环
        vmView.stopScroll();

        auto_text.destroyView();

        EventBus.getDefault().unregister(this);
    }




    //控件初始化
    private void initView() {
        for(int i=0;i<res.length;i++){
            ImageView imageView = (ImageView) findViewById(res[i]);
            mImageViewList.add(imageView);
            imageView.setOnClickListener(this);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageView_a:
                if(!opened) {
                    openAnimator();
                } else {
                    closeAnimator();
                }
                break;
            default:
                Toast.makeText(v.getContext(),"Id: "+v.getId(),Toast.LENGTH_SHORT).show();
                break;
        }

    }

    //控件关闭
    private void closeAnimator() {
        for(int i=0;i<res.length-1;i++){
            //计算每个控件展开的弧度
            moveBack(mImageViewList.get(i+1),(float) (Math.PI/2/(res.length-2))*(i+1));
        }
        opened = false;
    }

    // 控件打开
    private void openAnimator() {
        for(int i=0;i<res.length-1;i++){
            //计算每个控件展开的弧度
            moveTo(mImageViewList.get(i+1),(float) (Math.PI/2/(res.length-2))*(i+1));
        }
        opened = true;
    }

    /**
     * 扇形展开
     * @param objView 目标控件
     * @param angle 展开的角度
     */
    void moveTo(View objView, float angle){
//        ObjectAnimator translationX = ObjectAnimator.ofFloat(objView, "translationX", 0f, (float) Math.cos(angle) * r);
//        ObjectAnimator translationY = ObjectAnimator.ofFloat(objView, "translationY", 0f, (float) Math.sin(angle) * r);

        ObjectAnimator translationX = ObjectAnimator.ofFloat(objView, "translationX", 0f, -(float) Math.cos(angle) * r);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(objView, "translationY", 0f, -(float) Math.sin(angle) * r);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translationX,translationY);
        animatorSet.setDuration(500).start();
    }

    /**
     * 扇形关闭
     * @param objView 目标控件
     * @param angle 关闭的角度
     */
    void moveBack(View objView, float angle){
        ObjectAnimator translationX = ObjectAnimator.ofFloat(objView, "translationX", -(float) Math.cos(angle) * r,0f);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(objView, "translationY", -(float) Math.sin(angle) * r,0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translationX,translationY);
        animatorSet.setDuration(500).start();
    }


}
