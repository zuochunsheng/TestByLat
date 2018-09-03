package com.android.superplayer.ui.activity.my;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;

import com.android.superplayer.R;
import com.android.superplayer.adapter.MessageRecyclerAdapter;
import com.android.superplayer.base.BaseActivity;
import com.android.superplayer.config.LogUtil;
import com.android.superplayer.service.entity.NoticeListBean;
import com.android.superplayer.service.presenter.impl.NoticeListPresenterImpl;
import com.android.superplayer.service.view.impl.INoticeListView;
import com.android.superplayer.ui.widgit.LinearLayoutManagerWrapperManager;
import com.android.superplayer.util.ToastUtil;
import com.android.superplayer.util.request.ParamRequest;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoticeListActivity extends BaseActivity implements INoticeListView , XRecyclerView.LoadingListener{


    @BindView(R.id.recyclerview)
    XRecyclerView xRecyclerView;

    private static final String pageSize = "20";//每页几条
    private int pageIndex = 1;//第几页
    private String action = "refresh";//refresh loadmore
    private NoticeListPresenterImpl noticeListPresenter;

    List<NoticeListBean.DataBean> dataTotal  = new ArrayList();
    private MessageRecyclerAdapter recyclerViewAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notice_list;
    }

    @Override
    protected void initViewAndData() {
        noticeListPresenter = new NoticeListPresenterImpl(this);

        initRecyclerView();

        getNoticeList();
    }

    private void initRecyclerView() {
        xRecyclerView.setLayoutManager(new LinearLayoutManagerWrapperManager(this,
                LinearLayoutManager.VERTICAL, false));
        xRecyclerView.setHasFixedSize(true);
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this,R.drawable.custom_divider));
        xRecyclerView.addItemDecoration(divider);

        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        xRecyclerView.setRefreshProgressStyle(ProgressStyle.Pacman);
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreEnabled(true);
        xRecyclerView.setLoadingListener(this);



        if (recyclerViewAdapter == null) {
            recyclerViewAdapter = new MessageRecyclerAdapter(this, dataTotal);
            xRecyclerView.setAdapter(recyclerViewAdapter);
        }


    }

    private void getNoticeList() {
        HashMap<String, String> requestParams = ParamRequest.getRequestDefaultHash(this);
        requestParams.put("pageIndex", String.valueOf(pageIndex));
        requestParams.put("pageSize", String.valueOf(pageSize));


        noticeListPresenter.getNoticeList(requestParams);

    }


    @Override
    public void getNoticeListInfo(NoticeListBean info) {
        if(info.getCode() == 200 ){
            List<NoticeListBean.DataBean> data = info.getData();

            if (TextUtils.equals(action, "loadmore")) {//加载更多
                if (data.size() > 0) {
                    dataTotal.addAll(data);
                } else {
                    pageIndex--;
                    ToastUtil.showToast("消息已加载完毕");
                }

                recyclerViewAdapter.notifyDataSetChanged();

                xRecyclerView.loadMoreComplete();


            } else {
                if (data.size() > 0) {
                    dataTotal.clear();
                    dataTotal.addAll(data);

                }
                else {
                    dataTotal.clear();
                }

                recyclerViewAdapter.notifyDataSetChanged();
                xRecyclerView.refreshComplete();
            }
            recyclerViewAdapter.setNotifyDataSetChanged(dataTotal);

        }

    }

    @Override
    public void onRefresh() {
        LogUtil.e("refresh");
        action = "refresh";
        pageIndex = 1;
        getNoticeList();
    }

    @Override
    public void onLoadMore() {
        action = "loadmore";
        pageIndex++;
        getNoticeList();

    }


}
