package com.rxjava.net.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.rxjava.net.R;
import com.rxjava.net.adapter.HomeFragmentPageAdapter;
import com.rxjava.net.base.BaseFragment;
import com.rxjava.net.base.BaseTabEntity;
import com.rxjava.net.bean.ArticleItem;
import com.rxjava.net.bean.ArticleMessage;
import com.rxjava.net.bean.ProjectMessage;
import com.rxjava.net.comm.AppCode;
import com.rxjava.net.comm.BannerImageLoader;
import com.rxjava.net.comm.Constants;
import com.rxjava.net.comm.event.EventBusCode;
import com.rxjava.net.comm.event.MessageEvent;
import com.rxjava.net.mvp.contracts.HomeContracts;
import com.rxjava.net.mvp.presenter.HomePresenter;
import com.rxjava.net.ui.WebViewActivity;
import com.rxjava.net.ui.user.UserCollectionActivity;
import com.rxjava.net.utils.SPUtils;
import com.rxjava.net.view.HackyViewPager;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Lenovo on 2018/12/2.
 */

public class HomeFragment extends BaseFragment<HomePresenter>
        implements HomeContracts.View, OnBannerListener, OnRefreshListener {
    @BindView(R.id.banner)
    Banner banner;
    private List<com.rxjava.net.bean.Banner> banners = new ArrayList<>();


    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    HackyViewPager viewPager;

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;


    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_name_home)
    TextView userNameHome;

    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    private List<BaseTabEntity> tabEntities = new ArrayList<>();

    public static HomeFragment newInstance() {
        Bundle bundle = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private HomeFragmentPageAdapter mPageAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        refreshLayout.setEnableAutoLoadMore(false);//自动加载
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setEnableRefresh(true);//启用刷新
        refreshLayout.setEnableLoadMore(false);//启用加载
        banner.updateBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setImageLoader(new BannerImageLoader());
        banner.setOnBannerListener(this);
        banner.setDelayTime(3000);
        tabEntities.add(new BaseTabEntity("最新博文"));
        tabEntities.add(new BaseTabEntity("最新项目"));
        mPageAdapter = new HomeFragmentPageAdapter(getChildFragmentManager(), tabEntities);
        viewPager.setAdapter(mPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        if (!SPUtils.getInstance(Constants.USER).getBoolean(Constants.LOGIN)) {
            userNameHome.setText("未登录");
            userName.setText("未登录");
        } else {
            userName.setText(SPUtils.getInstance(Constants.USER).getString(Constants.USERNAME));
            userNameHome.setText(SPUtils.getInstance(Constants.USER).getString(Constants.USERNAME));
        }
        mPresenter.requestBanner();
    }

    @Override
    public HomePresenter initPresenter() {
        return new HomePresenter();
    }


    @OnClick({R.id.left_menu, R.id.user_layout, R.id
            .user_name, R.id.user_image, R.id.search, R.id.user_collection,R.id.about})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.left_menu:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.user_name:
            case R.id.user_image:
                if (!SPUtils.getInstance(Constants.USER).getBoolean(Constants.LOGIN))
                    intentLogin();
                break;
            case R.id.user_layout://防止事件穿透
                break;
            case R.id.search:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            case R.id.user_collection:
                if (SPUtils.getInstance(Constants.USER).getBoolean(Constants.LOGIN))
                    startActivity(new Intent(getActivity(), UserCollectionActivity.class));
                 else
                    intentLogin();
                break;
            case R.id.about:
                startActivity(new Intent(getActivity(),WebViewActivity.class).putExtra("url","https://www.wanandroid.com/about"));
                break;
        }
    }


    @Override
    public void OnBannerClick(int position) {
        if (banners.size() > 0) {
            intentWebView(banners.get(position).getUrl());
        }
    }

    @Override
    public void resultBanner(List<com.rxjava.net.bean.Banner> banners) {
        this.banners.addAll(banners);
        List<String> bannerImage = new ArrayList<>();
        List<String> bannerTitle = new ArrayList<>();
        for (com.rxjava.net.bean.Banner b : banners) {
            bannerImage.add(b.getImagePath());
            bannerTitle.add(b.getTitle());
        }
        banner.setImages(bannerImage);
        banner.setBannerTitles(bannerTitle);
        banner.start();
    }

    @Override
    public void onMoonEvent(MessageEvent messageEvent) {
        super.onMoonEvent(messageEvent);
        switch (messageEvent.getMessageCode()) {
            case EventBusCode.EVENT_REFRESH_USER:
                userName.setText(SPUtils.getInstance(Constants.USER).getString(Constants.USERNAME));
                userNameHome.setText(SPUtils.getInstance(Constants.USER).getString(Constants.USERNAME));
                break;
            case EventBusCode.EVENT_HOME_REFRESH_CLOSE:
                refreshLayout.finishRefresh();
                break;

        }

    }


    @Override
    public void resultHomeArticle(ArticleMessage articles) {

    }

    @Override
    public void resultHomeArticleTop(List<ArticleItem> articleItem) {

    }

    @Override
    public void resultHomeProject(ProjectMessage projectMessage) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void resultAddCollections(int id) {

    }

    @Override
    public void resultRemoveCollections(int id) {

    }


    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        EventBus.getDefault().post(new MessageEvent(EventBusCode.EVENT_HOME_REFRESH));
    }
}
