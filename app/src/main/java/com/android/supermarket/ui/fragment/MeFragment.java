package com.android.supermarket.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.supermarket.R;
import com.android.supermarket.base.BaseFragment;

public class MeFragment extends BaseFragment {




    @Override
    protected View fetchLayoutRes(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {

    }

}