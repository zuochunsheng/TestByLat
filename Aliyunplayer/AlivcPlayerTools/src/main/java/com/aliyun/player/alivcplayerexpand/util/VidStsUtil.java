package com.aliyun.player.alivcplayerexpand.util;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.aliyun.player.BuildConfig;
import com.aliyun.player.alivcplayerexpand.playlist.vod.core.AliyunVodHttpCommon;
import com.aliyun.player.source.VidSts;
import com.aliyun.utils.VcPlayerLog;

import org.json.JSONObject;

/**
 * Created by pengshuang on 31/08/2017.
 */
public class VidStsUtil {
    public final static String BASE_URL = "http://47.100.18.52:8081";


    private static final String TAG = VidStsUtil.class.getSimpleName();

    public static VidSts getVidSts(String videoId) {

        try {

            String stsUrl = BASE_URL + "/demo/getSts";
            String response = HttpClientUtil.doGet(stsUrl);
            JSONObject jsonObject = new JSONObject(response);

            JSONObject securityTokenInfo = jsonObject.getJSONObject("data");
            if (securityTokenInfo == null) {
                return null;
            }

            String accessKeyId = securityTokenInfo.getString("accessKeyId");
            String accessKeySecret = securityTokenInfo.getString("accessKeySecret");
            String securityToken = securityTokenInfo.getString("securityToken");
            String expiration = securityTokenInfo.getString("expiration");

            VidSts vidSts = new VidSts();
            vidSts.setVid(videoId);
            vidSts.setAccessKeyId(accessKeyId);
            vidSts.setAccessKeySecret(accessKeySecret);
            vidSts.setSecurityToken(securityToken);
            return vidSts;

        } catch (Exception e) {
            VcPlayerLog.e(TAG, "e = " + e.getMessage());
            return null;
        }
    }

    public interface OnStsResultListener {
        void onSuccess(String vid, String akid, String akSecret, String token);

        void onFail();
    }

    public static void getVidSts(final String vid, final OnStsResultListener onStsResultListener) {
        AsyncTask<Void, Void, VidSts> asyncTask = new AsyncTask<Void, Void, VidSts>() {

            @Override
            protected VidSts doInBackground(Void... params) {
                return getVidSts(vid);
            }

            @Override
            protected void onPostExecute(VidSts s) {
                if (s == null) {
                    onStsResultListener.onFail();
                } else {
                    onStsResultListener.onSuccess(s.getVid(), s.getAccessKeyId(), s.getAccessKeySecret(), s.getSecurityToken());
                }
            }
        };
        asyncTask.execute();

        return;
    }


}
