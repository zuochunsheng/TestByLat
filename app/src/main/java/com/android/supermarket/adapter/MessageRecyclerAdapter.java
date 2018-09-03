package com.android.supermarket.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.supermarket.R;
import com.android.supermarket.config.LogUtil;
import com.android.supermarket.service.entity.NoticeListBean;
import com.android.supermarket.ui.activity.MainActivity;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * authr : edz on 2017/9/21  上午11:36
 * describe  收益 适配器
 */
public class MessageRecyclerAdapter extends RecyclerView.Adapter<MessageRecyclerAdapter.MessageViewHolder> {


    private Context context;
    private List<NoticeListBean.DataBean> list;

    public MessageRecyclerAdapter(Context context, List<NoticeListBean.DataBean> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,
                parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        NoticeListBean.DataBean dataBean = list.get(position);

//        title (string, optional): 标题 ,
//                details (string, optional): 详情 ,
//                category (integer, optional): 类别， 0 系统公告，1 产品信息， 2，推送 ,
//                assertId (string, optional): 相关内容的ID ,
        String createdTime = dataBean.getCreatedTime();
        holder.tv_message_time.setText(createdTime);

        String title = dataBean.getTitle();
        String details = dataBean.getDetails();
        holder.tv_message_content.setText(title);



        // 单项  点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {// 跳转详情

                LogUtil.e("跳转详情");
//                Intent intent_invest = new Intent(context, MainActivity.class);
//                intent_invest.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                context.startActivity(intent_invest);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MessageViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.tv_message_time)
        TextView tv_message_time;
        @BindView(R.id.tv_message_content)
        TextView tv_message_content;



        public MessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
    public void setNotifyDataSetChanged(List<NoticeListBean.DataBean> data) {
        this.list = data;
        notifyDataSetChanged();

    }


}
