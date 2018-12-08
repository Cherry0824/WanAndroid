package com.rxjava.net.ui.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rxjava.net.R;
import com.rxjava.net.adapter.ArticleAdapter;
import com.rxjava.net.adapter.CollectionAdapter;
import com.rxjava.net.base.BaseActivity;
import com.rxjava.net.base.BasePresenter;
import com.rxjava.net.bean.ArticleItem;
import com.rxjava.net.bean.CollectionItem;
import com.rxjava.net.bean.CollectionMessage;
import com.rxjava.net.mvp.contracts.UserCollectionContracts;
import com.rxjava.net.mvp.presenter.UserCollectionPresenter;
import com.rxjava.net.view.DividerItemDecoration;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class UserCollectionActivity extends BaseActivity<UserCollectionPresenter>
        implements UserCollectionContracts.View, OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.title_name)
    TextView title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    private CollectionAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;
    private int page = 0;

    @Override
    public UserCollectionPresenter initPresenter() {
        return new UserCollectionPresenter();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_user_collection;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        title.setText("我的收藏");
        refreshLayout.setEnableAutoLoadMore(false);//自动加载
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        refreshLayout.setEnableRefresh(true);//启用刷新
        refreshLayout.setEnableLoadMore(true);//启用加载
        mAdapter = new CollectionAdapter(new ArrayList<CollectionItem>());
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.collection_icon)
                    mPresenter.requestRemoveCollection(mAdapter.getData().get(position).getId()
                            , mAdapter.getData().get(position).getOriginId());
            }
        });

        mPresenter.requestUserCollection(page);
    }

    @OnClick(R.id.title_back)
    public void OnClick(View view) {
        finish();
    }

    @Override
    public void resultUserCollection(CollectionMessage collectionMessage) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        if (page == 0)
            mAdapter.setNewData(collectionMessage.getDatas());
        else
            mAdapter.getData().addAll(collectionMessage.getDatas());
        if (collectionMessage.getOver()) {
            refreshLayout.finishLoadMoreWithNoMoreData();
        }
    }

    @Override
    public void resultRemoveCollection(int id) {
        Toasty.success(this, "取消成功", Toast.LENGTH_SHORT, true).show();
        for (CollectionItem item : mAdapter.getData()) {
            if (item.getId() == id) {
                mAdapter.getData().remove(item);
                mAdapter.notifyDataSetChanged();
                break;
            }
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        page = 0;
        mPresenter.requestUserCollection(page);
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        page++;
        mPresenter.requestUserCollection(page);
    }
}
