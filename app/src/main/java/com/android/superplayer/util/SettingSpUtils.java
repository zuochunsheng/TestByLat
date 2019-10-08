package com.android.superplayer.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * 保存设置页面的状态
 */
public class SettingSpUtils {

    private  final static String KEY_SETTING = "Setting_key";
    /**
     * 下载个数
     */
    private final static String KEY_VIDEO_NUMBER = "video_number";
    /**
     * 下载清晰度
     */
    private final static String KEY_QUANTITY = "video_quantity";
    /**
     * 运营商网络下载
     */
    private final static String KEY_IS_OPERATOR_DOWNLOAD = "is_operator_download";
    /**
     * 运营商网络自动播放
     */
    private final static String KEY_IS_AUTO_PLAY = "is_oprator_auto_play";
    /**
     * 启用硬解码
     */
    private final static String KEY_IS_HARD_DECODING = "is_hard_decoding";
    /**
     * 是否是vip
     */
    private final static String KEY_IS_VIP = "is_vip";

    private SharedPreferences sp;

    /**
     * 请先初始化
     */
    private void init(Context context) {
        sp = context.getApplicationContext().getSharedPreferences(KEY_SETTING, Context.MODE_PRIVATE);
    }

    public void saveVideoNumber(String videoNumber) {
        put(KEY_VIDEO_NUMBER, videoNumber);
    }

    /**
     * 获取下载个数
     */
    public String getVideoNumber() {
        String number = getString(KEY_VIDEO_NUMBER);

        return TextUtils.isEmpty(number) ? "5" : number;
    }

    public void saveVideoQUantity(String videoQuantity) {
        put(KEY_QUANTITY, videoQuantity);
    }

    /**
     * 获取下载清晰度
     */
    public String getVideoQuantity() {

        String quality = getString(KEY_QUANTITY);

        return TextUtils.isEmpty(quality) ? "LD" : quality;
    }

    /**
     * 获取是否开启运营商网络下载
     */
    public void saveOperatorDownload(boolean isOperatorDownload) {
        put(KEY_IS_OPERATOR_DOWNLOAD, isOperatorDownload);
    }

    public boolean getOperatorDownload() {
        return getBoolean(KEY_IS_OPERATOR_DOWNLOAD, false);
    }

    /**
     * 获取是否开启运营商自动播放
     */

    public void saveOperatorPlay(boolean isOperatorPlay) {
        put(KEY_IS_AUTO_PLAY, isOperatorPlay);
    }


    public boolean getOperatorPlay() {
        return getBoolean(KEY_IS_AUTO_PLAY, false);
    }

    public void saveHardDecoding(boolean isHardDecoding) {
        put(KEY_IS_HARD_DECODING, isHardDecoding);
    }

    /**
     * 获取是否开启硬解码
     */
    public boolean getHardDecoding() {
        return getBoolean(KEY_IS_HARD_DECODING, true);
    }

    public void saveVip(boolean isVip) {
        put(KEY_IS_VIP, isVip);
    }

    /**
     * 获取是否开vip
     */
    public boolean getVip() {
        return getBoolean(KEY_IS_VIP, false);
    }

    public void put(@NonNull final String key, final String value) {

        sp.edit().putString(key, value).apply();
    }

    public void put(@NonNull final String key, final boolean value) {
        sp.edit().putBoolean(key, value).apply();
    }

    public String getString(@NonNull final String key) {
        return getString(key, "");
    }

    public String getString(@NonNull final String key, final String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    public boolean getBoolean(@NonNull final String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(@NonNull final String key, final boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    public static class Builder {
        private SettingSpUtils helperUtils = new SettingSpUtils();

        public Builder(Context context) {
            helperUtils.init(context);
        }

        public Builder saveVideoNumber(String videoNumber) {
            helperUtils.saveVideoNumber(videoNumber);
            return this;
        }

        public Builder saveVideoQuantity(String quantity) {
            helperUtils.saveVideoQUantity(quantity);
            return this;
        }

        public Builder saveIsOperatorDownload(boolean isOperatorDownload) {
            helperUtils.saveOperatorDownload(isOperatorDownload);
            return this;
        }

        public Builder saveIsOperatorPlay(boolean isOperatorPlay) {
            helperUtils.saveOperatorPlay(isOperatorPlay);
            return this;
        }

        public Builder saveIsHardDecoding(boolean isHardDecoding) {
            helperUtils.saveHardDecoding(isHardDecoding);
            return this;
        }

        public Builder saveIsVip(boolean isVip) {
            helperUtils.saveVip(isVip);
            return this;
        }

        public SettingSpUtils create() {
            return helperUtils;
        }
    }


}
