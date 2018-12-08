package com.rxjava.net.ui.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.rxjava.net.R;
import com.rxjava.net.adapter.KnowledgeAdapter;
import com.rxjava.net.adapter.NavigationAdapter;
import com.rxjava.net.base.BaseFragment;
import com.rxjava.net.base.BasePresenter;
import com.rxjava.net.bean.Knowledge;
import com.rxjava.net.bean.Navigation;
import com.rxjava.net.bean.NavigationContent;
import com.rxjava.net.mvp.contracts.NavigationContracts;
import com.rxjava.net.mvp.presenter.NavigationPresenter;
import com.rxjava.net.ui.knowledge.KnowledgeDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Lenovo on 2018/12/5.
 */

public class NavigationFragment extends BaseFragment<NavigationPresenter> implements NavigationContracts.View {

    public static NavigationFragment newInstance() {
        Bundle bundle = new Bundle();
        NavigationFragment fragment = new NavigationFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    private NavigationAdapter mAdapter;

    private LinearLayoutManager linearLayoutManager;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_navigation;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new NavigationAdapter(new ArrayList<Navigation>(), LayoutInflater.from(getActivity()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setNavigationSingerClickListener(new NavigationAdapter.NavigationSingerClickListener() {
            @Override
            public void click(NavigationContent navigationContent) {
                intentWebView(navigationContent.getLink());
            }
        });
        mPresenter.requestNavigation();
    }

    @Override
    public NavigationPresenter initPresenter() {
        return new NavigationPresenter();
    }

    @Override
    public void resultNavigation(List<Navigation> navigationList) {
        mAdapter.setNewData(navigationList);
    }
}
