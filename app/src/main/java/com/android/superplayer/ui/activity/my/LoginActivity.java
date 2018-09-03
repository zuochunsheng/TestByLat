package com.android.superplayer.ui.activity.my;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RelativeLayout;

import com.android.superplayer.R;
import com.android.superplayer.adapter.ViewPagerAdapter;
import com.android.superplayer.base.BaseActivity;
import com.android.superplayer.config.Constant;
import com.android.superplayer.ui.activity.MainActivity;
import com.android.superplayer.ui.fragment.login.LoginFragment;
import com.android.superplayer.ui.fragment.login.RegistFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 登陆
 * @author zuochunsheng
 * @time 2018/8/30 18:04
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.viewpager)
    ViewPager viewpager;


    private ArrayList<String> titleList = new ArrayList<String>() {{
        add("登录");
        add("注册");

    }};

    private List<Fragment> fragmentList = new ArrayList<>();
    private int currentIndex = 0;
    private boolean booleanExtra; //进来是否登录过的
    private boolean isFromMainActivity = false;//是否从主页进来
    private SharedPreferences sp;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViewAndData() {

        initView();
    }


    private void initView() {

        Intent intent = getIntent();
        currentIndex = intent.getIntExtra(Constant.LOGINORREGIST_FLAG, 0);
        booleanExtra = intent.getBooleanExtra(Constant.SP_ISLOGIN, true);
        isFromMainActivity = intent.getBooleanExtra("isFromMainActivity", false);

        fragmentList.add(new LoginFragment());
        fragmentList.add(new RegistFragment());

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), titleList, fragmentList);
        viewpager.setAdapter(adapter);

        tab.setupWithViewPager(viewpager, true);
        tab.setTabsFromPagerAdapter(adapter);
        //设置保留几个Fragment，适当增大参数可防止Fragment频繁地被销毁和创建
        viewpager.setOffscreenPageLimit(2);


        viewpager.setCurrentItem(currentIndex);


    }


    @OnClick(R.id.rl_back)
    public void onViewClicked() {
        if(isFromMainActivity){
            goToMian();
        }else {
            finish();
        }


    }



    //回到主页 或者 其他
    private void goToMian() {

        //  产品全部
        Intent intent_invest = new Intent(this, MainActivity.class);
        intent_invest.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent_invest.putExtra("toMainActivityFrom", "0");
        startActivity(intent_invest);

        finish();

    }

}
