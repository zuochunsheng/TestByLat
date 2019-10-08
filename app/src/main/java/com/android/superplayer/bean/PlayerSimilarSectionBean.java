package com.android.superplayer.bean;

import com.aliyun.player.alivcplayerexpand.bean.LongVideoBean;
import com.aliyun.svideo.common.baseAdapter.entity.SectionEntity;

public class PlayerSimilarSectionBean extends SectionEntity<LongVideoBean> {

    public PlayerSimilarSectionBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public PlayerSimilarSectionBean(LongVideoBean longVideoBean) {
        super(longVideoBean);
    }
}
