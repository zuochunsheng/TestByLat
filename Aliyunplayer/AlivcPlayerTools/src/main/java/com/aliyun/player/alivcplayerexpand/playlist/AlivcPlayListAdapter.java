package com.aliyun.player.alivcplayerexpand.playlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aliyun.player.alivcplayerexpand.R;
import com.aliyun.player.alivcplayerexpand.util.Formatter;
import com.aliyun.svideo.common.utils.image.ImageLoaderImpl;
import com.aliyun.svideo.common.utils.image.ImageLoaderOptions;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * @author Mulberry
 *         create on 2018/5/17.
 */

public class AlivcPlayListAdapter extends RecyclerView.Adapter<AlivcPlayListAdapter.ViewHolder> {
    ArrayList<AlivcVideoInfo.Video> videoLists;
    WeakReference<Context> context;

    public AlivcPlayListAdapter(Context context, ArrayList<AlivcVideoInfo.Video> videoLists) {
        this.context = new WeakReference<Context>(context);
        this.videoLists = videoLists;

    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        ImageView coverImage;
        TextView title;
        TextView tvVideoDuration;
        LinearLayout alivcVideoInfoItemLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            alivcVideoInfoItemLayout = (LinearLayout)itemView.findViewById(R.id.alivc_video_info_item_layout);
            coverImage = (ImageView)itemView.findViewById(R.id.iv_video_cover);
            title = (TextView)itemView.findViewById(R.id.tv_video_title);
            tvVideoDuration = (TextView)itemView.findViewById(R.id.tv_video_duration);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.alivc_play_list_item, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (videoLists.size() > 0) {
            AlivcVideoInfo.Video video = videoLists.get(position);
            if (video != null) {
                holder.title.setText(video.getTitle());
                double dTime = Double.parseDouble(video.getDuration().toString());
                holder.tvVideoDuration.setText(Formatter.double2Date(dTime));

                new ImageLoaderImpl().loadImage(this.context.get(), video.getCoverURL(), new ImageLoaderOptions.Builder()
                                                .crossFade()
                                                .centerCrop().build()
                                               ).into(holder.coverImage);

            }
        }
        holder.alivcVideoInfoItemLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onVideoListItemClick != null) {
                    onVideoListItemClick.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoLists.size();
    }

    private OnVideoListItemClick onVideoListItemClick;

    public void setOnVideoListItemClick(
        OnVideoListItemClick onVideoListItemClick) {
        this.onVideoListItemClick = onVideoListItemClick;
    }

    public interface OnVideoListItemClick {
        void onItemClick(int position);
    }
}
