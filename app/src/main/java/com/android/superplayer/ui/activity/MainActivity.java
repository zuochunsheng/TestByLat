package com.android.superplayer.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.superplayer.BuildConfig;
import com.android.superplayer.R;
import com.android.superplayer.config.Constant;
import com.android.superplayer.ui.activity.my.LoginActivity;
import com.android.superplayer.ui.fragment.FindFragment;
import com.android.superplayer.ui.fragment.HomeFragment;
import com.android.superplayer.ui.fragment.InvestmentFragment;
import com.android.superplayer.ui.fragment.MeFragment;
import com.android.superplayer.ui.widgit.NoScrollViewPager;
import com.android.superplayer.util.ActivityManager;
import com.android.superplayer.util.ActivityUtil;
import com.android.superplayer.util.ToastUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.ra_viewPager)
    NoScrollViewPager raViewPager;
    @BindView(R.id.rb_hot)
    RadioButton rbHot;
    @BindView(R.id.rb_all)
    RadioButton rbAll;
    @BindView(R.id.rb_discovery)
    RadioButton rbDiscovery;
    @BindView(R.id.rb_me)
    RadioButton rbMe;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;


    @BindDimen(R.dimen.activity_action_bar)
    int actionBarSize;


    private long exitTime;
    private SharedPreferences sp;
    private int currentIndex = 0;//当前显示第几页

    private HomeFragment homeFragment;
    private InvestmentFragment investmentFragment;
    private FindFragment findFragment;
    private MeFragment meFragment;
    private List<Fragment> listFragment = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (sp == null) {
            sp = getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
        }

        Intent intent = getIntent();

        if (intent != null) {
            //跳转主页 下的fragment
            String activity = intent.getStringExtra("toMainActivityFrom");
            if (TextUtils.equals(activity, "3")) {
                currentIndex = 3;//
            } else if (TextUtils.equals(activity, "2")) {
                currentIndex = 2;
            } else if (TextUtils.equals(activity, "1")) {
                currentIndex = 1;
            }  else {// 0
                currentIndex = 0;
            }
        }

        initView();


        try {   //BuildConfig.APPLICATION_ID   当前应用包名
            PackageInfo packageInfo = getPackageManager().getPackageInfo(BuildConfig.APPLICATION_ID,
                    PackageManager.GET_SIGNATURES);
            String signValidString = getSignValidString(packageInfo.signatures[0].toByteArray());
            Log.e("获取应用签名", BuildConfig.APPLICATION_ID + "__" + signValidString);
        } catch (Exception e) {
            Log.e("获取应用签名", "异常__" + e);
        }

    }


    private String getSignValidString(byte[] paramArrayOfByte) throws NoSuchAlgorithmException {
        MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
        localMessageDigest.update(paramArrayOfByte);
        return toHexString(localMessageDigest.digest());
    }
    public String toHexString(byte[] paramArrayOfByte) {
        if (paramArrayOfByte == null) {
            return null;
        }
        StringBuilder localStringBuilder = new StringBuilder(2 * paramArrayOfByte.length);
        for (int i = 0; ; i++) {
            if (i >= paramArrayOfByte.length) {
                return localStringBuilder.toString();
            }
            String str = Integer.toString(0xFF & paramArrayOfByte[i], 16);
            if (str.length() == 1) {
                str = "0" + str;
            }
            localStringBuilder.append(str);
        }
    }






    private void initView() {
        homeFragment = new HomeFragment();
        investmentFragment = new InvestmentFragment();
        findFragment = new FindFragment();
        meFragment = new MeFragment();

        listFragment.add(homeFragment);
        listFragment.add(investmentFragment);
        listFragment.add(findFragment);
        listFragment.add(meFragment);


        //getSupportFragmentManager   在 AppCompatActivity
        raViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return listFragment.get(position);
            }

            @Override
            public int getCount() {
                return listFragment.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                // super.destroyItem(container, position, object);
            }
        });

        raViewPager.setOffscreenPageLimit(4);

        raViewPager.setCurrentItem(currentIndex);

    }

    @OnClick({R.id.rb_hot, R.id.rb_all, R.id.rb_discovery, R.id.rb_me})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_hot:
                raViewPager.setCurrentItem(0, false);
                break;
            case R.id.rb_all:
                raViewPager.setCurrentItem(1, false);
                break;
            case R.id.rb_discovery:
                raViewPager.setCurrentItem(2, false);
                break;
            case R.id.rb_me:
//                SharedPreferences sp = getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
//                boolean aBoolean = sp.getBoolean(Constant.SP_ISLOGIN, false);
//
//                if (!aBoolean) {
//                    goLogin();
//                    return;
//                }

                raViewPager.setCurrentItem(3, false);
                break;
        }
    }

    private void goLogin(){
        ActivityUtil.getInstance().onNext(MainActivity.this,
                LoginActivity.class,"isFromMainActivity",true);
    }







    @Override
    public void onBackPressed() {
        exit();
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtil.showToast("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            ActivityManager.getInstance().killAllActivity();
            System.exit(0);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        homeFragment = null;
        investmentFragment = null;
        findFragment = null;
        meFragment = null;
    }
}
