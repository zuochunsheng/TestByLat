package com.android.supermarket.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.android.supermarket.R;

import java.util.Random;


/**
 * authr : edz on 2017/11/27  上午11:08
 * describe ： 刷新的view 2
 */
public class ViewHeader extends RelativeLayout implements RefreshHeader {

    private Context mContext;
    private int mHeight;
    //private int mWidth;

    private Random random = new Random();
    private TextView state_tv;
    private ImageView pull_icon;
    private String[] Strings = new String[]{"正在刷新","正在刷新"};


    private static int index = 0;//选中第几个文字


    public ViewHeader(Context context) {
        super(context);
        this.mContext = context;
        init(context);
    }

    public ViewHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(context);
    }

    public ViewHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(context);
    }

    private void init(Context context) {

        View view = View.inflate(context, R.layout.refresh_head, this);

        pull_icon = view.findViewById(R.id.pull_icon);
        state_tv = view.findViewById(R.id.state_tv);
        //Strings = context.getResources().getStringArray(R.array.refresh_text);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

    }


    private void initView() {
        index = getNext();
        state_tv.setText(Strings[index]);
        pull_icon.setImageResource(R.mipmap.icon_xia);//icon_shuanxin_start
    }

    private int getNext() {
        int indexNext = 0 ;
        do {
            indexNext = random.nextInt(Strings.length);
        }
        while (indexNext == index );

        return indexNext;

    }

    /**
     * 松手，头部隐藏后会回调这个方法
     */
    @Override
    public void reset() {
        initView();
        //state_tv.setText("reset");
    }

    /**
     * 下拉出头部的一瞬间调用
     */
    @Override
    public void pull() {
        initView();
    }


    /**
     * 正在刷新的时候调用
     */
    @Override
    public void refreshing() {
        //state_tv.setText("refreshing");
        pull_icon.setImageResource(R.mipmap.icon_xin);
    }

    /**
     * 头部滚动的时候持续调用
     *
     * @param currentPos target当前偏移高度
     * @param lastPos    target上一次的偏移高度
     * @param refreshPos 可以松手刷新的高度
     * @param isTouch    手指是否按下状态（通过scroll自动滚动时需要判断）
     * @param state      当前状态
     */
    @Override
    public void onPositionChange(float currentPos, float lastPos, float refreshPos, boolean isTouch,
                                 RefreshLayout.State state) {
        //state_tv.setText("onPositionChange");

        if (currentPos > mHeight) {
            pull_icon.setImageResource(R.mipmap.icon_shang);
        } else {//下
            pull_icon.setImageResource(R.mipmap.icon_xia);
        }


    }

    /**
     * 刷新成功的时候调用
     */
    @Override
    public void complete() {
        //state_tv.setText("刷新成功");
        pull_icon.setImageResource(R.mipmap.icon_dui);
    }

    @Override
    public void fail() {
        //state_tv.setText("刷新失败");
        pull_icon.setImageResource(R.mipmap.icon_fail);
    }
}
