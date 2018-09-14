package com.android.superplayer.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.android.superplayer.service.entity.MusicResult;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * anther: created by zuochunsheng on 2018/9/5 11 : 49
 * descript :
 */


//MediaPlayer 常用方法介绍
//        方法：create(Context context, Uri uri)
//        解释：静态方法，通过Uri创建一个多媒体播放器。
//        方法：create(Context context, int resid)
//        解释：静态方法，通过资源ID创建一个多媒体播放器
//        方法：create(Context context, Uri uri, SurfaceHolder holder)
//        解释：静态方法，通过Uri和指定 SurfaceHolder 【抽象类】 创建一个多媒体播放器

//        方法： getCurrentPosition()
//        解释：返回 Int， 得到当前播放位置
//        方法： getDuration()
//        解释：返回 Int，得到文件的时间
//        方法：getVideoHeight()
//        解释：返回 Int ，得到视频的高度
//        方法：getVideoWidth()
//        解释：返回 Int，得到视频的宽度

//        方法：isLooping()
//        解释：返回 boolean ，是否循环播放
//        方法：isPlaying()
//        解释：返回 boolean，是否正在播放

//        方法：pause()
//        解释：无返回值 ，暂停
//        方法：prepare()
//        解释：无返回值，准备同步
//        方法：prepareAsync()
//        解释：无返回值，准备异步
//        方法：release()
//        解释：无返回值，释放 MediaPlayer  对象
//        方法：reset()
//        解释：无返回值，重置 MediaPlayer  对象

//        方法：seekTo(int msec)
//        解释：无返回值，指定播放的位置（以毫秒为单位的时间）
//        方法：setAudioStreamType(int streamtype)
//        解释：无返回值，指定流媒体的类型

//        方法：setDataSource(String path)
//        解释：无返回值，设置多媒体数据来源【根据 路径】
//        方法：setDataSource(FileDescriptor fd, long offset, long length)
//        解释：无返回值，设置多媒体数据来源【根据 FileDescriptor】
//        方法：setDataSource(FileDescriptor fd)
//        解释：无返回值，设置多媒体数据来源【根据 FileDescriptor】
//        方法：setDataSource(Context context, Uri uri)
//        解释：无返回值，设置多媒体数据来源【根据 Uri】
//        方法：setDisplay(SurfaceHolder sh)
//        解释：无返回值，设置用 SurfaceHolder 来显示多媒体

//        方法：setLooping(boolean looping)
//        解释：无返回值，设置是否循环播放
//        事件：setOnBufferingUpdateListener(MediaPlayer.OnBufferingUpdateListener listener)
//        解释：监听事件，网络流媒体的缓冲监听
//        事件：setOnCompletionListener(MediaPlayer.OnCompletionListener listener)
//        解释：监听事件，网络流媒体播放结束监听
//        事件：setOnErrorListener(MediaPlayer.OnErrorListener listener)
//        解释：监听事件，设置错误信息监听
//        事件：setOnVideoSizeChangedListener(MediaPlayer.OnVideoSizeChangedListener listener)
//        解释：监听事件，视频尺寸监听
//        方法：setScreenOnWhilePlaying(boolean screenOn)
//        解释：无返回值，设置是否使用 SurfaceHolder 显示

//        方法：setVolume(float leftVolume, float rightVolume)
//        解释：无返回值，设置音量
//        方法：start()
//        解释：无返回值，开始播放
//        方法：stop()
//        解释：无返回值，停止播放
//

public class MusicUtil {


    /*加载媒体库里的音频*/
    public static List<MusicResult> getMusicDate(Context context) {

        List<MusicResult> olist = new ArrayList<>();

        ContentResolver resolver = context.getContentResolver();


        /*查询媒体数据库  需要存储权限
        参数分别为（路径，要查询的列名，条件语句，条件参数，排序）
        视频：MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        图片;MediaStore.Images.Media.EXTERNAL_CONTENT_URI
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

            //歌曲编号
            long id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
            //歌曲标题
            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));

            //歌曲的专辑名：MediaStore.Audio.Media.ALBUM
            String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
            int albumId = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));

            //歌曲的歌手名： MediaStore.Audio.Media.ARTIST
            String author = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            //歌曲文件的路径 ：MediaStore.Audio.Media.DATA
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            //歌曲的总播放时长 ：MediaStore.Audio.Media.DURATION
            long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));


            //歌曲文件的大小 ：MediaStore.Audio.Media.SIZE
            Long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));

            //铃声  音乐
            if (TextUtils.equals(author, "<unknown>")) {
                author = "未知艺术家";
            }

            if (duration > 20000) {
                music.setName(name);
                music.setAuthor(author);
                music.setPath(path);
                music.setDuration(duration);

                music.setId(id);
                music.setAuthor(album);
                music.setAlbumId(albumId);
                music.setSize(size);

                olist.add(music);
            }


        }


        return olist;

    }





}
