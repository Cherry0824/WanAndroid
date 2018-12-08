package com.rxjava.net.ui.knowledge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rxjava.net.R;
import com.rxjava.net.adapter.ArticleAdapter;
import com.rxjava.net.adapter.KnowledgeDetailAdapter;
import com.rxjava.net.base.BaseActivity;
import com.rxjava.net.base.BasePresenter;
import com.rxjava.net.bean.ArticleItem;
import com.rxjava.net.bean.ArticleMessage;
import com.rxjava.net.mvp.contracts.KnowledgeDetailContracts;
import com.rxjava.net.mvp.presenter.KnowledgeDetailPresenter;
import com.rxjava.net.view.DividerItemDecoration;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class KnowledgeDetailsActivity extends BaseActivity<KnowledgeDetailPresenter>
        implements KnowledgeDetailContracts.View, OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.title_name)
    TextView title;
    private KnowledgeDetailAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;

    private int page = 0;
    private int cid;
    @Override
    public KnowledgeDetailPresenter initPresenter() {
        return new KnowledgeDetailPresenter();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_knowledge_details;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        refreshLayout.setEnableAutoLoadMore(false);//自动加载
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        refreshLayout.setEnableRefresh(true);//启用刷新
        refreshLayout.setEnableLoadMore(true);//启用加载
        cid = getIntent().getIntExtra("cid", 60);
        title.setText(getIntent().getStringExtra("title"));
        mAdapter = new KnowledgeDetailAdapter(new ArrayList<ArticleItem>());
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                intentWebView(mAdapter.getData().get(position).getLink());
            }
        });
        mPresenter.requestKnowledge(page, cid);
    }

    @OnClick(R.id.title_back)
    public void OnClick(View view) {
        finish();
    }

    @Override
    public void resultKnowledge(ArticleMessage articleMessage) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        if (page == 0) {
            mAdapter.setNewData(articleMessage.getDatas());
        } else {
            mAdapter.getData().addAll(articleMessage.getDatas());
            mAdapter.notifyDataSetChanged();
        }
        if (articleMessage.isOver()) {
            refreshLayout.finishLoadMoreWithNoMoreData();
        }
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        page++;
        mPresenter.requestKnowledge(page,cid);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        page=0;
        mPresenter.requestKnowledge(page,cid);
    }
}
