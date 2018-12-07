package com.android.superplayer.ui.activity.test;

import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.widget.TextView;

import com.android.superplayer.R;
import com.android.superplayer.base.BaseActivity;
import com.android.superplayer.ui.widgit.DecelerateAccelerateInterpolator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AnimatorActivity extends BaseActivity {

    @BindView(R.id.tv_xMove)
    TextView tvXMove;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_animator;
    }

    @Override
    protected void initViewAndData() {

    }




    @OnClick(R.id.tv_xMove)
    public void onViewClicked() {

        float curTranslationX = tvXMove.getTranslationX();
        // 获得当前按钮的位置

        ObjectAnimator animator = ObjectAnimator.ofFloat(tvXMove, "translationX",
                curTranslationX, 300, curTranslationX);
        // 创建动画对象 & 设置动画
        // 表示的是:
        // 动画作用对象是mButton
        // 动画作用的对象的属性是X轴平移
        // 动画效果是:从当前位置平移到 x=1500 再平移到初始位置
        animator.setDuration(5000);
        animator.setInterpolator(new DecelerateAccelerateInterpolator());
        // 设置插值器
        animator.start();
        // 启动动画





       // ObjectAnimator anim = ObjectAnimator.ofObject(tvXMove, "height", new IntEvaluator(),1,3);
// 在第4个参数中传入对应估值器类的对象
// 系统内置的估值器有3个：
// IntEvaluator：以整型的形式从初始值 - 结束值 进行过渡
// FloatEvaluator：以浮点型的形式从初始值 - 结束值 进行过渡
// ArgbEvaluator：以Argb类型的形式从初始值 - 结束值 进行过渡


    }


}
