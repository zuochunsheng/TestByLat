package com.android.superplayer.bean;


import com.aliyun.player.alivcplayerexpand.bean.LongVideoBean;
import com.aliyun.svideo.common.baseAdapter.entity.SectionEntity;

public class SingleSectionBean extends SectionEntity<LongVideoBean> {


    public SingleSectionBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public SingleSectionBean(LongVideoBean videoListBean) {
        super(videoListBean);
    }
}
