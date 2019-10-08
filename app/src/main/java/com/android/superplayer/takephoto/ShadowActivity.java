package com.android.superplayer.takephoto;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.aliyun.svideo.common.utils.FileUtils;
import com.android.superplayer.R;
import com.yalantis.ucrop.UCrop;
import com.yanzhenjie.permission.FileProvider;
import java.io.File;



/**
 * anther: created by zuochunsheng on 2019/1/18 18 : 24
 * description :
 */
public class ShadowActivity  extends Activity {


    private static final int SELECT_FILE = 0;          // 选择照片
    private static final int REQUEST_CAMERA = 1;       // 拍照

    // 7.0 以上的uri
    private Uri mProviderUri;
    // 7.0 以下的uri
    private Uri mUri;
    // 图片路径
    private String mFilepath = Environment.getExternalStorageDirectory() + "/Pictures/" + "AndroidSamples";
    // Environment.getExternalStorageDirectory() + "/Pictures/"   这个是要共享的路径 不能改变



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            handleIntent(getIntent());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        int index = intent.getIntExtra("permissions",0);
        startTakePhotos(index);
    }


    private void startTakePhotos(int index) {

        if (index == 0) {//相册
            selectImg();
        } else {//拍照
            camera();
        }

    }

    /**
     * 拍照
     */
    private void camera() {
        File file = new File(mFilepath, System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Android7.0以上URI
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //通过FileProvider创建一个content类型的Uri
            mProviderUri = FileProvider.getUriForFile(this, FileUtils.getFileProviderName(this), file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mProviderUri);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            mUri = Uri.fromFile(file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
        }
        try {
           startActivityForResult(intent, REQUEST_CAMERA);
        } catch (ActivityNotFoundException anf) {
            Toast.makeText(this, "摄像头未准备好", Toast.LENGTH_SHORT).show();
        }
    }

    //相册选图
    private void selectImg() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(pickIntent, SELECT_FILE);

    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        Log.d("zuo", "mineFragment onActivityResult");
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CAMERA:
                    // 拍照
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        // 调用裁剪方法
                        cropRawPhoto(mProviderUri);

                    } else {
                        cropRawPhoto(mUri);
                    }
                    break;
                case SELECT_FILE:

                    //Log.d("zuo", "SELECT_FILE: " + data.getData());//content://media/external/images/media/1384759
                    cropRawPhoto(data.getData());
                    break;

                case UCrop.REQUEST_CROP:
                    // 成功（返回的是文件地址）
                    Uri resultUri = UCrop.getOutput(data);
                    Log.e("zuo", "REQUEST_UCROP:" + resultUri.toString());

                    //mUploadListener.takephotoSuccessEvent(resultUri);
                    TakephotoUtil.getInstance(this).onRequestPermissionsResult(TakephotoUtil.RESULT_TAKEPHOTO_SUCCESS,resultUri.toString());
                    finish();
                    break;
                case UCrop.RESULT_ERROR:
                    // 失败
                    //mUploadListener.takephotoErrorEvent(UCrop.getError(data) + "");
                    TakephotoUtil.getInstance(this).onRequestPermissionsResult(TakephotoUtil.RESULT_TAKEPHOTO_ERROR, UCrop.getError(data) + "");
                    finish();
                    break;
            }
        }

    }


    public void cropRawPhoto(Uri uri) {
        Log.e("zuo", "cropRawPhoto: " + uri.toString());
        // 修改配置参数（我这里只是列出了部分配置，并不是全部）
        UCrop.Options options = new UCrop.Options();
        // 修改标题栏颜色
        options.setToolbarColor(getResources().getColor(R.color.black));
        // 修改状态栏颜色
        options.setStatusBarColor(getResources().getColor(R.color.blue));
        // 隐藏底部工具
        options.setHideBottomControls(true);
        // 图片格式
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        // 设置图片压缩质量
        options.setCompressionQuality(100);
        // 是否让用户调整范围(默认false)，如果开启，可能会造成剪切的图片的长宽比不是设定的
        // 如果不开启，用户不能拖动选框，只能缩放图片
        options.setFreeStyleCropEnabled(true);

        // 设置源uri及目标uri
        UCrop.of(uri, Uri.fromFile(new File(mFilepath, System.currentTimeMillis() + ".jpg")))
                // 长宽比
                .withAspectRatio(1, 1)
                // 图片大小
                .withMaxResultSize(300, 300)
                // 配置参数
                .withOptions(options)
                .start(this);
    }

}
