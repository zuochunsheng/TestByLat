package com.android.superplayer.takephoto;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.superplayer.R;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import java.util.List;

/**
 * anther: created by zuochunsheng on 2019/1/18 17 : 30
 * description :
 */
public class TakephotoUtil {


    public static final int RESULT_TAKEPHOTO_SUCCESS = 0 ;
    public static final int RESULT_TAKEPHOTO_ERROR = 1 ;
    public static final int RESULT_UPLOAD_SUCCESS = 2 ;
    public static final int RESULT_UPLOAD_ERROR = 3 ;



    private static IUploadEvent mUploadListener;

    private Context  context;
    private static TakephotoUtil instance;
    // 单例模式中获取唯一的MyApplication实例  synchronized
    public static  TakephotoUtil getInstance(Context context) {
        if (instance == null) {
            instance = new TakephotoUtil(context);
        }
        return instance;
    }
    public TakephotoUtil(Context context) {
        this.context = context;

    }

    private void setUploadListener(IUploadEvent nUploadListener){
        mUploadListener = nUploadListener;
    }


    //  6.0 检查 权限
    public void checkPermissions(IUploadEvent nUploadListener) {
        //mUploadListener = nUploadListener;
        AndPermission.with(context)
                .runtime()
                .permission(Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        //hasPermission = true;
                        setUploadListener(nUploadListener) ;
                        initBottomSheetDialog();
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        //hasPermission = false;
                        checkPermissions(nUploadListener);
                    }
                })
                .start();


    }

    // 底部弹框
    private void initBottomSheetDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialogStyle);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog);

        TextView tv_bottomsheet_photolist = (TextView) bottomSheetDialog.findViewById(R.id.tv_bottomsheet_photolist);
        TextView tv_bottomsheet_takephoto = (TextView) bottomSheetDialog.findViewById(R.id.tv_bottomsheet_takephoto);
        TextView tv_bottomsheet_cancel = (TextView) bottomSheetDialog.findViewById(R.id.tv_bottomsheet_cancel);

        //从相册选择一张
        tv_bottomsheet_photolist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startShadowActivity(0);
                bottomSheetDialog.cancel();

            }
        });
        // 拍照 一张
        tv_bottomsheet_takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startShadowActivity(1);
                bottomSheetDialog.cancel();
            }
        });

        tv_bottomsheet_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.cancel();
            }
        });
        bottomSheetDialog.show();

    }

    //跳转
    private void startShadowActivity(int index) {

        Intent intent = new Intent(context, ShadowActivity.class);
        intent.putExtra("permissions", index);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }





    //回调 ---十分巧妙
    public void onRequestPermissionsResult( int resultCode, String result) {

        Log.d("zuo", "mineFragment onActivityResult");

            switch (resultCode) {
                case RESULT_TAKEPHOTO_SUCCESS:
                    mUploadListener.takephotoSuccessEvent(result);
                    break;
                case RESULT_TAKEPHOTO_ERROR:
                    // 失败
                    mUploadListener.takephotoErrorEvent(result);
                    break;

                case RESULT_UPLOAD_SUCCESS:



                    break;
                case RESULT_UPLOAD_ERROR:

                    break;
            }


    }





}
