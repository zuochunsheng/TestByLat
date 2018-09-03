package com.android.superplayer.ui.fragment.login;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.superplayer.R;
import com.android.superplayer.base.BaseFragment;
import com.android.superplayer.config.Constant;
import com.android.superplayer.config.LogUtil;
import com.android.superplayer.service.entity_wrap.LoginWBean;
import com.android.superplayer.service.presenter.impl.RegPassPresenterImpl;
import com.android.superplayer.service.view.impl.IRegPassView;
import com.android.superplayer.ui.activity.MainActivity;
import com.android.superplayer.util.ActivityUtil;
import com.android.superplayer.util.PreventMultiClick;
import com.android.superplayer.util.ResourceUtil;
import com.android.superplayer.util.ToastUtil;
import com.android.superplayer.util.request.ParamRequest;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * mvp 模版 ，结果筛选一次
 */
public class RegistFragment extends BaseFragment implements TextWatcher ,IRegPassView {


    @BindView(R.id.login_name)
    EditText loginName;
    @BindView(R.id.login_passWord)
    EditText loginPassWord;
    @BindView(R.id.login_btn)
    TextView loginBtn;
    @BindView(R.id.linearLayout_login)
    RelativeLayout linearLayoutLogin;

    private String phone;
    private boolean isSeletAgree = true;//默认选中协议
    private RegPassPresenterImpl regPassPresenter;

    @Override
    protected View fetchLayoutRes(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_regist, container, false);
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        initView();
    }

    private void initView(){

        linearLayoutLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // fragment 里面调用 getSystemService
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                imm.hideSoftInputFromWindow(loginName.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(loginPassWord.getWindowToken(), 0);
            }
        });

        loginName.addTextChangedListener(this);
        loginPassWord.addTextChangedListener(this);

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {


        String  phone = loginName.getText().toString().trim();
        String password = loginPassWord.getText().toString().trim();

        if (isSeletAgree && (phone.length() == 11)  &&  (password.length() >= 8) ) {
            loginBtn.setEnabled(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                loginBtn.setBackground(ResourceUtil.getDrawable(activity, R.drawable.bg_button_blue));
            }

        }else{

            loginBtn.setEnabled(false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                loginBtn.setBackground(ResourceUtil.getDrawable(activity, R.drawable.bg_button_gray));
            }
        }
    }





    @OnClick(R.id.login_btn)
    public void onViewClicked() {
        if (PreventMultiClick.isFastClick()) {
            return;
        }
        phone = loginName.getText().toString().trim();

        if (phone.length() == 11) {
            String loginPassword = loginPassWord.getText().toString().trim();
            int length = loginPassword.length();
            if (length >= 8 && length <= 10) {
                if (isSeletAgree) {// 发起请求

                    HashMap<String, String> requestParams = ParamRequest.getRequestDefaultHash(this.getContext());

                    requestParams.put("userName", phone);
                    requestParams.put("loginPwd", loginPassword);
                    requestParams.put("vcCode", "");

                    regPassPresenter = new RegPassPresenterImpl(this);
                    regPassPresenter.submitRegPass(this.getActivity(),requestParams);


                } else {

                    ToastUtil.showToast("您尚未确认已阅读");
                }
            } else {
                ToastUtil.showToast("请输入正确格式的密码");
            }
        } else {

            ToastUtil.showToast("请输入正确的手机号");
        }



    }

    @Override
    public void regPassResultInfo(LoginWBean info) {
        String token = info.getToken();
        String userId = info.getUserId();
        if(TextUtils.isEmpty(token) || TextUtils.isEmpty(userId)){
            ToastUtil.showToast("token为失效");
            return;
        }

        SharedPreferences sp = getActivity().getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        LogUtil.e("web", "登录新的 token = " +token);
        LogUtil.e("web", "登录新的 userId = " + userId);

        editor.putString("token", token);
        editor.putString("userId",userId);

        editor.putBoolean(Constant.SP_ISLOGIN, true);
        editor.apply();


        goMainActivity();

    }
    private void goMainActivity() {
        ActivityUtil.getInstance().goMainFragment(this.getActivity(), MainActivity.class, "0");
        //finish();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (regPassPresenter != null) {
            regPassPresenter.onDestroy();
        }
    }
}
