package com.android.supermarket.base;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * authr : edz on 2017/8/24  下午9:32
 * describe
 */
public abstract class BaseFragment extends Fragment {
    View root ;
    Unbinder unbinder;
    protected Activity activity ;
    protected ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if(root ==null){
            root = fetchLayoutRes(inflater,container,savedInstanceState);
        }
        unbinder = ButterKnife.bind(this, root);
        activity = this.getActivity();

        return root;

    }


    protected abstract View fetchLayoutRes(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize(savedInstanceState);
    }

    protected abstract void initialize(Bundle savedInstanceState);

    public void showProgressDialog(String msg){

        if(progressDialog ==null ){
            progressDialog = new ProgressDialog(activity);

            progressDialog.setCanceledOnTouchOutside(false);
            //progressDialog.setCancelable(false);


        }

        if(!TextUtils.isEmpty(msg)){
            progressDialog.setMessage(msg);
        }

        progressDialog.show();


    }
    public void hideProgressDialog(){

        if(progressDialog !=null ){
            progressDialog.dismiss();
        }
    }

//
//    @Override
//    public void onResume() {
//        super.onResume();
//        MobclickAgent.onPageStart(this.getClass().getSimpleName());
//        MobclickAgent.onResume(activity);     //统计时长
//
//    }
//    @Override
//    public void onPause() {
//        super.onPause();
//        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
//        MobclickAgent.onPause(activity);
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(root !=null && root.getParent() !=null) {

            ((ViewGroup) root.getParent()).removeView(root);

        }
        unbinder.unbind();

       // AndroidApplication.getRefWatcher().watch(this);


    }
}
