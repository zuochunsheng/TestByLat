package com.android.superplayer.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

/**
 * 用户信息保存类
 */
public class UserSpUtils {

    private static final String KEY_USER = "key_user";
    /**
     * 用户ID
     */
    private static final String KEY_USER_ID = "key_user_id";

    /**
     * 用户TOKEN
     */
    private static final String KEY_TOEKN = "key_user_token";

    /**
     * 用户头像
     */
    private static final String KEY_AVATAR_URL = "key_avatar_url";

    /**
     * 用户昵称
     */
    private static final String KEY_NICK_NAME = "key_nick_name";

    private SharedPreferences sp;

    /**
     * 请先初始化
     */
    private void init(Context context) {
        sp = context.getSharedPreferences(KEY_USER, Context.MODE_PRIVATE);
    }

    public void put(@NonNull final String key, final String value) {

        sp.edit().putString(key, value).apply();
    }

    public String getString(@NonNull final String key) {
        return getString(key, "");
    }

    public String getString(@NonNull final String key, final String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    public void saveUserID(String userId) {
        put(KEY_USER_ID, userId);
    }

    public void saveUserToken(String token) {
        put(KEY_TOEKN, token);
    }

    public void saveAvatar(String avatar) {
        put(KEY_AVATAR_URL, avatar);
    }

    public void saveNickName(String nickName) {
        put(KEY_NICK_NAME, nickName);
    }

    public String getUserID() {
        return getString(KEY_USER_ID);

    }

    public String getUserToken() {
        return getString(KEY_TOEKN);

    }

    public String getAvatar() {
        return getString(KEY_AVATAR_URL);

    }

    public String getNickName() {
        return getString(KEY_NICK_NAME);
    }

    public static class Builder {
        private UserSpUtils utils = new UserSpUtils();

        public Builder(Context context) {
            utils.init(context);
        }

        public Builder userToken(String token) {
            utils.saveUserToken(token);
            return this;
        }

        public Builder userID(String id) {
            utils.saveUserID(id);
            return this;
        }

        public Builder userAvatar(String avatar) {
            utils.saveAvatar(avatar);
            return this;
        }

        public Builder userNickName(String nickName) {
            utils.saveNickName(nickName);
            return this;
        }

        public UserSpUtils create() {
            return utils;
        }
    }
}