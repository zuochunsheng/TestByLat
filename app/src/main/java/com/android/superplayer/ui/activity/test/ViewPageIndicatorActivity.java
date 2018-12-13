package com.android.superplayer.ui.activity.test;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.superplayer.R;
import com.android.superplayer.base.BaseActivity;
import com.android.superplayer.ui.widgit.ViewPagerIndicate;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewPageIndicatorActivity extends BaseActivity {

    @BindView(R.id.indicate)
    ViewPagerIndicate mIndicate;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;



    //private ViewPager mViewPager;
    //private ViewPagerIndicate mIndicate;
    private int[] mTextColors = {0xFFA0A0A0, 0xFF000000};
    private int mUnderlineColor = 0xFF168EFF;
    private String[] mTitles = new String[] {
            "要闻", "奥运", "视频", "娱乐", "体育", "NBA",
            "财经", "汽车", "科技", "社会", "军事", "国际",
            "时尚", "文化", "游戏", "图片", "数码", "星座",
            "电影", "教育", "美容", "动漫", "理财", "民生"};
    private ArrayList<TextView> mTextViews;




    @Override
    protected int getLayoutId() {
        return R.layout.activity_view_page_indicator;
    }

    @Override
    protected void initViewAndData() {
        initViewPgaer();
        initViewPagerIndicate();

    }

    private void initViewPgaer() {
        mTextViews = new ArrayList<TextView>();
        for (int i = 0; i < mTitles.length; i++) {
            TextView tv = new TextView(this);
            tv.setGravity(Gravity.CENTER);
            tv.setText(mTitles[i]);
            mTextViews.add(tv);
        }

        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                TextView tv = mTextViews.get(position);
                container.addView(tv);
                return tv;
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView(mTextViews.get(position));
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return mTextViews.size();
            }
        });



//        FragmentPagerAdapter adapter = new TabPageIndicatorAdapter(getSupportFragmentManager());
//        mViewPager.setAdapter(adapter);


    }

    private void initViewPagerIndicate() {
        //mIndicate = (ViewPagerIndicate) findViewById(R.id.indicate);
        //设置标签样式、文本和颜色
        mIndicate.resetText(R.layout.layout_text_indicate, mTitles, mTextColors);
        //设置下划线粗细和颜色
        mIndicate.resetUnderline(4, mUnderlineColor);
        //设置ViewPager
        mIndicate.resetViewPager(mViewPager);
        //设置初始化完成
        mIndicate.setOk();
    }




}
