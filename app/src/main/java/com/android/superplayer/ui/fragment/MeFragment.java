package com.android.superplayer.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.superplayer.R;
import com.android.superplayer.base.BaseFragment;
import com.android.superplayer.ui.activity.my.MediaPlayerActivity;
import com.android.superplayer.util.ActivityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MeFragment extends BaseFragment {


    @BindView(R.id.tv_goMediaPlay)
    TextView tvGoMediaPlay;
    Unbinder unbinder;

    @Override
    protected View fetchLayoutRes(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {

    }




    @OnClick(R.id.tv_goMediaPlay)
    public void onViewClicked() {

        ActivityUtil.getInstance().onNext(this.getActivity() ,MediaPlayerActivity.class);
    }
}
