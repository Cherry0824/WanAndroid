package com.rxjava.net.ui.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rxjava.net.R;
import com.rxjava.net.adapter.ArticleAdapter;
import com.rxjava.net.base.BaseFragment;
import com.rxjava.net.base.BasePresenter;
import com.rxjava.net.bean.ArticleItem;
import com.rxjava.net.bean.ArticleMessage;
import com.rxjava.net.bean.HotTerms;
import com.rxjava.net.comm.event.EventBusCode;
import com.rxjava.net.comm.event.MessageEvent;
import com.rxjava.net.mvp.contracts.SearchContracts;
import com.rxjava.net.mvp.presenter.SearchPresenter;
import com.rxjava.net.view.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Lenovo on 2018/12/6.
 */

public class SearchResultFragment extends BaseFragment<SearchPresenter> implements SearchContracts.View {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ArticleAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;
    private int page = 0;

    public static SearchResultFragment newInstance() {
        SearchResultFragment fragment = new SearchResultFragment();
        return fragment;
    }

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
        mAdapter = new ArticleAdapter(new ArrayList<ArticleItem>());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                intentWebView(mAdapter.getData().get(position).getLink());
            }
        });

    }

    @Override
    public SearchPresenter initPresenter() {
        return new SearchPresenter();
    }

    @Override
    public void onMoonEvent(MessageEvent messageEvent) {
        super.onMoonEvent(messageEvent);
        if (messageEvent.getMessageCode() == EventBusCode.EVENT_SEARCH) {
            page = 0;
            mPresenter.requestRegister(page, messageEvent.getMessage());
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void resultHotTerms(List<HotTerms> hotTerms) {

    }

    @Override
    public void resultSearch(ArticleMessage message) {
        mAdapter.getData().addAll(message.getDatas());
        mAdapter.notifyDataSetChanged();
    }
}
