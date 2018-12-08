package com.rxjava.net.ui.project;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rxjava.net.R;
import com.rxjava.net.adapter.ProjectAdapter;
import com.rxjava.net.adapter.ProjectTreeAdapter;
import com.rxjava.net.base.BaseFragment;
import com.rxjava.net.base.BasePresenter;
import com.rxjava.net.bean.ProjectClass;
import com.rxjava.net.bean.ProjectItem;
import com.rxjava.net.bean.ProjectMessage;
import com.rxjava.net.comm.event.EventBusCode;
import com.rxjava.net.comm.event.MessageEvent;
import com.rxjava.net.mvp.contracts.ProjectContracts;
import com.rxjava.net.mvp.presenter.ProjectPresenter;
import com.rxjava.net.view.DividerItemDecoration;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Lenovo on 2018/12/4.
 */

public class ProjectFragment extends BaseFragment<ProjectPresenter>
        implements ProjectContracts.View, OnRefreshListener, OnLoadMoreListener {

    public static ProjectFragment newInstance() {
        Bundle bundle = new Bundle();
        ProjectFragment fragment = new ProjectFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerViewTree)
    RecyclerView recyclerViewTree;

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.project_title)
    TextView title;
    @BindView(R.id.project_now_title)
    TextView nowTitle;
    private ProjectTreeAdapter mAdapterTree;
    private ProjectAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayoutManager linearLayoutManagerTree;
    private int page = 0;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_project;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        refreshLayout.setEnableAutoLoadMore(false);//自动加载
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        refreshLayout.setEnableRefresh(true);//启用刷新
        refreshLayout.setEnableLoadMore(true);//启用加载
        linearLayoutManagerTree = new LinearLayoutManager(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());
        mAdapterTree = new ProjectTreeAdapter(new ArrayList<ProjectClass>());
        recyclerViewTree.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        recyclerViewTree.setLayoutManager(linearLayoutManagerTree);
        recyclerViewTree.setAdapter(mAdapterTree);
        mAdapterTree.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                page = 0;
                mPresenter.requestProjectList(page, mAdapterTree.getData().get(position).getId());
                title.setText(mAdapterTree.getData().get(position).getName());
                nowTitle.setText(mAdapterTree.getData().get(position).getName());
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });

        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new ProjectAdapter(new ArrayList<ProjectItem>());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                intentWebView(mAdapter.getData().get(position).getLink());
            }
        });


        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {//打开时
            }

            @Override
            public void onDrawerClosed(View drawerView) {//关闭时候

            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });


        mPresenter.requestProjectTree();
    }

    @OnClick({R.id.project_title, R.id.project_title_icon})
    public void OnClick(View v) {
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    @Override
    public void onMoonEvent(MessageEvent messageEvent) {
        super.onMoonEvent(messageEvent);
        if (messageEvent.getMessageCode() == EventBusCode.EVENT_REFRESH_USER) {
            page = 0;
            mPresenter.requestProjectTree();
        }
    }

    @Override
    public ProjectPresenter initPresenter() {
        return new ProjectPresenter();
    }

    private int nowCheckId;

    @Override
    public void resultProjectTree(List<ProjectClass> projectClasses) {

        mAdapterTree.setNewData(projectClasses);
        title.setText(projectClasses.get(0).getName());
        nowTitle.setText("当前：" + projectClasses.get(0).getName());
        nowCheckId = projectClasses.get(0).getId();
        mPresenter.requestProjectList(page, nowCheckId);
    }

    @Override
    public void resultProjectList(ProjectMessage projectMessage) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        if (page == 0) {
            mAdapter.setNewData(projectMessage.getDatas());
        } else {
            mAdapter.getData().addAll(projectMessage.getDatas());
            mAdapter.notifyDataSetChanged();
        }
        if (projectMessage.getOver()) {
            refreshLayout.finishLoadMoreWithNoMoreData();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        page++;
        mPresenter.requestProjectList(page, nowCheckId);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        page = 0;
        mPresenter.requestProjectList(page, nowCheckId);
    }
}
