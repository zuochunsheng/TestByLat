package com.android.superplayer.service.entity;

import java.io.Serializable;

/**
 * anther: created by zuochunsheng on 2018/9/5 11 : 50
 * descript :
 */
public class MusicResult implements Serializable{

    private static final  long serialVersionUID = 1L;

    private String name;//歌曲名
    private String author;//演唱者



    private String path;//歌曲路径
    private long duration;//歌曲时长


    private long id;
    private String  album;
    private int  albumId;
    private long  size;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
