package com.android.superplayer.bean;

import com.aliyun.player.alivcplayerexpand.bean.LongVideoBean;

import java.util.ArrayList;
import java.util.List;

public class SimilarVideoBean {

    private String result;
    private String message;
    private String code;
    private DataBean data;

    public static class DataBean {
        private int total;
        private List<LongVideoBean> videoList;
        private List<LongVideoBean> tvPlayList;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<LongVideoBean> getVideoList() {
            if (videoList == null) {
                videoList = new ArrayList<>();
            }
            return videoList;
        }

        public void setVideoList(List<LongVideoBean> videoList) {
            this.videoList = videoList;
        }

        public List<LongVideoBean> getTvPlayList() {
            if (tvPlayList == null) {
                tvPlayList = new ArrayList<>();
            }
            return tvPlayList;
        }

        public void setTvPlayList(List<LongVideoBean> tvPlayList) {
            this.tvPlayList = tvPlayList;
        }
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }
}
