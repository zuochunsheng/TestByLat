package com.android.superplayer.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.superplayer.R;
import com.android.superplayer.base.BaseFragment;
import com.android.superplayer.pulltorefresh.RefreshLayout;
import com.android.superplayer.service.entity.BannerListBean;
import com.android.superplayer.service.presenter.impl.BannerListPresenterImpl;
import com.android.superplayer.service.view.impl.IBannerListView;
import com.android.superplayer.ui.activity.WebActivity;
import com.android.superplayer.ui.activity.my.LoginActivity;
import com.android.superplayer.ui.activity.my.NoticeListActivity;
import com.android.superplayer.ui.widgit.MyScrollView;
import com.android.superplayer.util.ActivityUtil;
import com.android.superplayer.util.request.ParamRequest;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment implements IBannerListView {


    @BindView(R.id.hot_fm_viewPager)
    ConvenientBanner hotFmViewPager;
    @BindView(R.id.hom_fm_detail)
    TextView homFmDetail;
    @BindView(R.id.hot_fm_close)
    ImageView hotFmClose;
    @BindView(R.id.hom_fm_notice)
    RelativeLayout homFmNotice;

    @BindView(R.id.nestedScrollView)
    MyScrollView nestedScrollView;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;


    private List<String> networkImages = new ArrayList<>();
    private List<BannerListBean.DataBean> mBannerInfo = new ArrayList<>();
    private SharedPreferences sp;
    private BannerListPresenterImpl bannerListPresenter;

    @Override
    protected View fetchLayoutRes(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {

        initView();

        initData();
    }

    private void initData() {
        // banner ,公告 和 产品数据

        HashMap<String, String> requestParams = ParamRequest.getRequestDefaultHash(this.getContext());

        bannerListPresenter = new BannerListPresenterImpl(this);
        bannerListPresenter.getBannerList(getActivity(),requestParams);




    }

    private void initView() {

        nestedScrollView.getViewTreeObserver()
                .addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        if (refreshLayout != null) {
                            refreshLayout.setEnabled(nestedScrollView.getScrollY() <= 0);

                        }
                    }
                });
        refreshLayout.setRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
    }


    @OnClick({R.id.hot_fm_close, R.id.home_news_noticemore})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.hot_fm_close:// 公告 关闭
                homFmNotice.setVisibility(View.GONE);
                break;
            case R.id.home_news_noticemore:// 公告 --》更多
                ActivityUtil.getInstance().onNext(HomeFragment.this.getActivity(), NoticeListActivity.class);
                break;
        }
    }




    @Override
    public void bannerListInfo(BannerListBean bannerListBean) {
       if( bannerListBean.getCode() == 200){
           List<BannerListBean.DataBean> data = bannerListBean.getData();
           if (data != null && data.size() > 0) {
               if (mBannerInfo == null) {
                   mBannerInfo = new ArrayList<>();
               }
               mBannerInfo.clear();
               networkImages.clear();
               mBannerInfo.addAll(data);
               for (int i = 0; i < mBannerInfo.size(); i++) {
                   networkImages.add(i, mBannerInfo.get(i).getPicUrl());

               }
               initConvenien();


           }


       }

    }


    //请求到数据后  banner 初始化
    private void initConvenien(){
        hotFmViewPager.setPages(
                new CBViewHolderCreator<NetworkImageHolderView>() {
                    @Override
                    public NetworkImageHolderView createHolder() {
                        return new NetworkImageHolderView();
                    }
                }, networkImages)
                //.startTurning(2000)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{ R.drawable.shape_dot,R.drawable.shape_nodot})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        hotFmViewPager.setManualPageable(true);//设置不能手动影响

        // banner 点击事件
        hotFmViewPager.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String href = mBannerInfo.get(position).getHref();

                Intent intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra("url", href);

                startActivity(intent);
            }
        });

    }






    private class NetworkImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, String data) {

            if (getContext() != null && imageView != null) {
                Glide.with(getContext())
                        .load(data)
                        .thumbnail(0.1f)
                        .into(imageView);
            }

        }
    }



    private void goLogin(){
        ActivityUtil.getInstance().onNext(HomeFragment.this.getActivity(), LoginActivity.class,
                "isFromMainActivity",true);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
       if(bannerListPresenter !=null ) {
           bannerListPresenter.onDestroy();
       }

    }
}
