package com.rxjava.net.ui.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rxjava.net.R;
import com.rxjava.net.adapter.ArticleAdapter;
import com.rxjava.net.adapter.ProjectAdapter;
import com.rxjava.net.base.BaseFragment;
import com.rxjava.net.bean.ArticleItem;
import com.rxjava.net.bean.ArticleMessage;
import com.rxjava.net.bean.Banner;
import com.rxjava.net.bean.ProjectItem;
import com.rxjava.net.bean.ProjectMessage;
import com.rxjava.net.comm.event.EventBusCode;
import com.rxjava.net.comm.event.MessageEvent;
import com.rxjava.net.mvp.contracts.HomeContracts;
import com.rxjava.net.mvp.presenter.HomePresenter;
import com.rxjava.net.view.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

/**
 * Created by Lenovo on 2018/12/3.
 */

public class ProjectFragment extends BaseFragment<HomePresenter> implements HomeContracts.View {
    public static ProjectFragment newInstance() {
        ProjectFragment fragment = new ProjectFragment();
        return fragment;
    }

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ProjectAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;
    private int page = 0;

    @Override
    public int getLayoutId() {
        return R.layout.layout_recycleview;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        linearLayoutManager = new LinearLayoutManager(getActivity());
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
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.project_collection) {
                    if (mAdapter.getData().get(position).getCollect())
                        mPresenter.requestRemoveCollections(mAdapter.getData().get(position).getId());
                    else
                        mPresenter.requestAddCollections(mAdapter.getData().get(position).getId());
                }
            }
        });
        //mAdapter.setPreLoadNumber(5);
        mAdapter.bindToRecyclerView(recyclerView);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                mPresenter.requestHomeArticle(page);
            }
        }, recyclerView);
        mPresenter.requestHomeProject(page);
    }

    @Override
    public HomePresenter initPresenter() {
        return new HomePresenter();
    }

    @Override
    public void onMoonEvent(MessageEvent messageEvent) {
        super.onMoonEvent(messageEvent);
        if (messageEvent.getMessageCode() == EventBusCode.EVENT_REFRESH_USER) {
            page = 0;
            mPresenter.requestHomeProject(page);
        }
    }

    @Override
    public void resultBanner(List<Banner> banners) {

    }

    @Override
    public void resultHomeArticle(ArticleMessage articles) {

    }

    @Override
    public void resultHomeArticleTop(List<ArticleItem> articleItem) {

    }

    @Override
    public void resultHomeProject(ProjectMessage projectMessage) {
        if (page == 0) {
            mAdapter.setNewData(projectMessage.getDatas());
        } else
            mAdapter.getData().addAll(projectMessage.getDatas());
        if (projectMessage.getOver())
            mAdapter.setEnableLoadMore(false);
        else
            mAdapter.loadMoreComplete();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void resultAddCollections(int id) {
        Toasty.success(getActivity(), "收藏成功", Toast.LENGTH_SHORT, true).show();
        for (ProjectItem item : mAdapter.getData()) {
            if (item.getId() == id) {
                item.setCollect(true);
                mAdapter.notifyDataSetChanged();
                break;
            }
        }
    }

    @Override
    public void resultRemoveCollections(int id) {
        Toasty.success(getActivity(), "取消成功", Toast.LENGTH_SHORT, true).show();
        for (ProjectItem item : mAdapter.getData()) {
            if (item.getId() == id) {
                item.setCollect(false);
                mAdapter.notifyDataSetChanged();
                break;
            }
        }
    }
}
