package com.android.superplayer.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.android.superplayer.service.entity.MusicResult;

import java.util.ArrayList;
import java.util.List;

/**
 * anther: created by zuochunsheng on 2018/9/5 11 : 49
 * descript :
 */
public class MusicUtil {


    // 获取手机中的音乐文件
    public static List<MusicResult> getMusicDate(Context context) {

        List<MusicResult> olist = new ArrayList<>();

        ContentResolver resolver = context.getContentResolver();

        /*
         * 需要存储权限
         * 第一个参数 uri 地址
         *
         * 第五个参数 排序
         */
        Cursor cursor = resolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER
        );

        while (cursor.moveToNext()) {

            MusicResult music = new MusicResult();

            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            String author = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));

            //铃声  音乐
            if(TextUtils.equals(author,"<unknown>")){
                author = "未知艺术家";
            }

            if(duration >20000){
                music.setName(name);
                music.setAuthor(author);
                music.setPath(path);
                music.setDuration(duration);

                olist.add(music);
            }



        }


        return olist;

    }


}
