package com.android.superplayer.adapter;


import android.view.View;
import android.widget.ImageView;

import com.aliyun.svideo.common.baseAdapter.BaseSectionQuickAdapter;
import com.aliyun.svideo.common.baseAdapter.BaseViewHolder;
import com.aliyun.svideo.common.utils.image.ImageLoaderImpl;
import com.aliyun.svideo.common.utils.image.ImageLoaderOptions;
import com.android.superplayer.R;
import com.android.superplayer.bean.PlayerSimilarSectionBean;

import java.util.List;

/**
 * 播放界面底部猜你喜欢、片花咨询 Adapter
 */
public class AlivcPlayerSimilarQuickAdapter extends BaseSectionQuickAdapter<PlayerSimilarSectionBean,BaseViewHolder> {


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public AlivcPlayerSimilarQuickAdapter(int layoutResId, int sectionHeadResId, List<PlayerSimilarSectionBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    public AlivcPlayerSimilarQuickAdapter(int layoutResId, int sectionHeadResId){
        super(layoutResId,sectionHeadResId,null);
    }


    @Override
    protected void convertHead(BaseViewHolder helper, PlayerSimilarSectionBean item) {
        helper.setText(R.id.alivc_tv_title,item.header);
        helper.getView(R.id.alivc_tv_more_title).setVisibility(View.GONE);
    }

    @Override
    protected void convert(BaseViewHolder helper, PlayerSimilarSectionBean item) {
        helper.setText(R.id.tv_video_title,item.t.getTitle());
        helper.setText(R.id.tv_video_description,item.t.getDescription());
        ImageView mCoverImageView = helper.getView(R.id.iv_cover);
        new ImageLoaderImpl().loadImage(mContext, item.t.getCoverUrl(), new ImageLoaderOptions.Builder()
                .crossFade()
                .roundCorner()
                .error(R.mipmap.ic_launcher)
                .build()).into(mCoverImageView);
    }
}
