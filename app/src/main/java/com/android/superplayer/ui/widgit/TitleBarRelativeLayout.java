package com.android.superplayer.ui.widgit;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.superplayer.R;


/**
 * authr : edz on 2017/8/25  下午2:44
 * describe  标题栏 返回按钮 默认返回前一个界面 ，可以重写
 */


public class TitleBarRelativeLayout extends RelativeLayout {

    RelativeLayout rl_back;
    TextView tvText;
    TextView tvRightText;


    public TitleBarRelativeLayout(Context context) {
        this(context, null);
    }

    public TitleBarRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBarRelativeLayout(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.view_header, this, true);
        rl_back = findViewById(R.id.rl_back);
        tvText = findViewById(R.id.tv_text);
        tvRightText = findViewById(R.id.tv_right_text);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.titleBarRelativeLayout);
        if (ta != null) {
            //标题
            String text = ta.getString(R.styleable.titleBarRelativeLayout_tv_title_text);
            if (!TextUtils.isEmpty(text)) {
                tvText.setText(text);
            }
            rl_back.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity) context).finish();
                }
            });
            //右边文字
            String text_right = ta.getString(R.styleable.titleBarRelativeLayout_tv_right_title_text);
            if (!TextUtils.isEmpty(text_right)) {
                tvRightText.setText(text_right);
                int color = ta.getColor(R.styleable.titleBarRelativeLayout_tv_right_title_text_color, -1);
                if (color != -1) {
                    tvRightText.setTextColor(color);
                }
            }


        }
        assert ta != null;
        ta.recycle();
    }


    public void setBackClickListener(OnClickListener onClickListener) {
        if (onClickListener != null) {
            rl_back.setOnClickListener(onClickListener);

        }
    }

    public void setRightButtonClickListener(OnClickListener onClickListener) {
        if (onClickListener != null) {
            tvRightText.setOnClickListener(onClickListener);

        }
    }

    public void setTitleText(String text) {

        tvText.setText(text);

    }

    //会赋空值
    public void setRightTitleText(String text) {

        tvRightText.setText(text);

    }


}
