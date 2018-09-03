package com.android.superplayer.base;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.superplayer.R;

import butterknife.ButterKnife;

/**
 * authr : edz on 2017/8/24  下午5:46
 * describe
 */


public abstract class BaseActivity extends FragmentActivity {

    public Context mContext;
    protected ProgressDialog progressDialog;
    private Dialog dialog_tip;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;

        //单例模式的吐司 。。。日志

        setContentView(getLayoutId());

        bindButterKnife();
        initViewAndData();

        Log.e("class", "类名:<> " + getClass().getSimpleName() + " ||  栈taskId <> " + getTaskId());

    }

    // 初始化控件 和 数据
    protected abstract void initViewAndData();

    //setContent 布局文件
    protected abstract int getLayoutId();

    // bind 控件 待测试
    protected void bindButterKnife() {
        ButterKnife.bind(this);
    }


    //统一的弹出框
    public void showProgressDialog(String msg){

        /*if(this.getWindow().){

        }*/
        if(progressDialog ==null ){
            progressDialog = new ProgressDialog(this);

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



    // 可变参数
    //输错 交易密码 弹出框
//    public void showTips(String content, int leftCount, final EditText... editText) {
//        if(dialog_tip !=null){
//            dialog_tip =null;
//        }
//        dialog_tip = new Dialog(this, R.style.ActionSheetDialogStyle);
//        // 填充对话框的布局
//        @SuppressLint("InflateParams")
//        View inflateTips = LayoutInflater.from(this).inflate(R.layout.dialog_pass_tip, null);
//
//        TextView tvContent = ((TextView) inflateTips.findViewById(R.id.dialog_pass_content));
//        Button dialog_pass_reset = ((Button) inflateTips.findViewById(R.id.dialog_pass_reset));
//        Button dialog_pass_again = ((Button) inflateTips.findViewById(R.id.dialog_pass_again));
//        TextView dialog_pass_define = ((TextView) inflateTips.findViewById(R.id.dialog_pass_define));
//        LinearLayout dialog_pass_linearLayout = ((LinearLayout) inflateTips.findViewById(R.id.dialog_pass_linearLayout));
//        if (leftCount > 0) {
//            dialog_pass_define.setVisibility(View.GONE);
//            dialog_pass_linearLayout.setVisibility(View.VISIBLE);
//            tvContent.setText("交易密码错误，还可以再输入" + leftCount + "次");
//
//        } else {
//            dialog_pass_linearLayout.setVisibility(View.GONE);
//            dialog_pass_define.setVisibility(View.VISIBLE);
//            tvContent.setText(content);
//        }
//        //忘记密码
//        dialog_pass_reset.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for (EditText i : editText){
//                    i.setText("");
//                }
//
//                dialog_tip.dismiss();
//                //重置密码
//                startActivity(new Intent(mContext, RePassWordActivity.class));
//            }
//        });
//        //重新输入
//        dialog_pass_again.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {//下单 和充值 要 清空   2  赎回 提现不用
//                for (EditText i : editText){
//                    i.setText("");
//                }
//
//                dialog_tip.dismiss();
//            }
//        });
//        //确定
//        dialog_pass_define.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog_tip.dismiss();
//            }
//        });
//        dialog_tip.setContentView(inflateTips);
//        // 获取当前Activity所在的窗体
//        Window dialogWindow = dialog_tip.getWindow();
//        // 设置Dialog从窗体底部弹出
//        dialogWindow.setGravity(Gravity.CENTER);
//        // 获得代表当前window属性的对象
//        WindowManager.LayoutParams params = dialogWindow.getAttributes();
//        Point point = new Point();
//        Display display = getWindowManager().getDefaultDisplay();
//        // 将window的宽高信息保存在point中
//        display.getSize(point);
//        // 将设置后的大小赋值给window的宽高
//        params.width = point.x / 3 * 2;
//        // 方式一：设置属性
//        dialogWindow.setAttributes(params);
//        dialog_tip.show();// 显示对话框
//
//    }
//
//
//
//    //错误框
//    public void showFailDialog(String errMsg) {
//        new CommomDialog(this, errMsg, "", "", new CommomDialog.OnCloseListener() {
//            @Override
//            public void onClick(Dialog dialog, boolean confirm) {
//                if (confirm) {
//                    dialog.dismiss();
//                }
//            }
//        }, true).show();
//
//    }
//


    //弹出软键盘
    public void showKeyboard(EditText editText) {
        if(editText!=null){
            //设置可获得焦点
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            //请求获得焦点
            editText.requestFocus();
            //调用系统输入法
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(editText, 0);
        }
    }


    //隐藏软键盘
    public void hideKeyboard(){
        //系统键盘 如果已弹出 则隐藏
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            IBinder windowToken = currentFocus.getWindowToken();
            if (windowToken != null) {
                imm.hideSoftInputFromWindow(windowToken, 0);
            }
        }

    }


    public void formatEditTextStart0(EditText editText){
        // 02 -》2
        String text = editText.getText().toString();
        if(!TextUtils.isEmpty(text) && text.startsWith("0")){
            if(text.length()>=2 ){
                String substring = text.substring(1, 2);//第二位数
                if(!TextUtils.equals(substring,".")){
                    editText.setText(substring);
                    editText.setSelection(substring.length());
                }
            }
        }

    }


    /*
     *  不聚焦 ，隐藏软键盘
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            IBinder windowToken = currentFocus.getWindowToken();
            if (windowToken != null) {
                imm.hideSoftInputFromWindow(windowToken, 0);
            }
        }

        return super.onTouchEvent(event);
    }

//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        MobclickAgent.onPageStart(this.getClass().getSimpleName());//统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
//        MobclickAgent.onResume(this);//统计时长
//
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
//        MobclickAgent.onPause(this);
//
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(progressDialog !=null){
            progressDialog =null;
        }
//        if(dialog_tip !=null){
//            dialog_tip =null;
//        }
    }
}

