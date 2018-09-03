package com.android.supermarket.ui.fragment.login;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
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

import com.android.supermarket.R;
import com.android.supermarket.base.BaseFragment;
import com.android.supermarket.config.Constant;
import com.android.supermarket.config.LogUtil;
import com.android.supermarket.service.entity.LoginBean;
import com.android.supermarket.service.presenter.ILoginPassPresenter;
import com.android.supermarket.service.presenter.impl.LoginPassPresenterImpl;
import com.android.supermarket.service.view.impl.ILoginPassView;
import com.android.supermarket.ui.activity.MainActivity;
import com.android.supermarket.util.ActivityUtil;
import com.android.supermarket.util.PreventMultiClick;
import com.android.supermarket.util.ResourceUtil;
import com.android.supermarket.util.ToastUtil;
import com.android.supermarket.util.request.ParamRequest;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginFragment extends BaseFragment implements TextWatcher,ILoginPassView {


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


    @Override
    protected View fetchLayoutRes(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
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

        String password = loginPassWord.getText().toString().trim();
        String  phone = loginName.getText().toString().trim();

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

                    ILoginPassPresenter loginPassSetPresenter = new LoginPassPresenterImpl(this);
                    loginPassSetPresenter.submitLoginPass(requestParams);


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


    /**
     *  返回全部信息
     * @param info
     */
    @Override
    public void loginPassResultInfo(LoginBean info) {
        int code = info.getCode();
        
        if(code == 200){
            LoginBean.DataBean data = info.getData();
            String token = data.getToken();
            String userId = data.getUserId();
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

    }

    private void goMainActivity() {
        ActivityUtil.getInstance().goMainFragment(this.getActivity(), MainActivity.class, "0");
        //finish();
    }

}
