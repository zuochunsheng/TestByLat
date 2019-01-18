package com.android.superplayer.takephoto;

import android.net.Uri;

/**
 * anther: created by zuochunsheng on 2019/1/18 18 : 14
 * description :
 */
public interface IUploadEvent {


    //拍照裁剪成功
    //void takephotoSuccessEvent(Uri uri);
    void takephotoSuccessEvent(String uri);
    //拍照裁剪失败
    void takephotoErrorEvent(String error);


    //下面是扩展 图片网路提交返回

    //上传成功后的回调 图片路径
    void uploadSuccessEvent(String result);
    void uploadErrorEvent(String error);
}
