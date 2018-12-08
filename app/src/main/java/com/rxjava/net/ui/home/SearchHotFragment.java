package com.rxjava.net.ui.home;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rxjava.net.R;
import com.rxjava.net.adapter.SearchHistoryAdapter;
import com.rxjava.net.base.BaseFragment;
import com.rxjava.net.base.BasePresenter;
import com.rxjava.net.bean.ArticleMessage;
import com.rxjava.net.bean.HotTerms;
import com.rxjava.net.bean.Knowledge;
import com.rxjava.net.mvp.contracts.SearchContracts;
import com.rxjava.net.mvp.presenter.SearchPresenter;
import com.rxjava.net.view.DividerItemDecoration;
import com.rxjava.net.view.flowlayout.FlowLayout;
import com.rxjava.net.view.flowlayout.TagAdapter;
import com.rxjava.net.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import butterknife.BindView;

/**
 * Created by Lenovo on 2018/12/6.
 */

public class SearchHotFragment extends BaseFragment<SearchPresenter> implements SearchContracts.View {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private SearchHistoryAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;
    private int[] color = new int[]{R.drawable.round_bg_blue
            , R.drawable.round_bg_green
            , R.drawable.round_bg_orange
            , R.drawable.round_bg_pink
            , R.drawable.round_bg_yellow};

    public static SearchHotFragment newInstance() {
        SearchHotFragment fragment = new SearchHotFragment();
        return fragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.layout_recycleview;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mAdapter = new SearchHistoryAdapter(new ArrayList<String>());
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setHeaderView(getHeadView());
        mPresenter.requestHotTerms();
    }


    private TagFlowLayout flowLayout;
    private LayoutInflater mInflater;
    private Random random = new Random();
    private List<HotTerms> hotTerms;

    private View getHeadView() {
        View headView = getLayoutInflater().inflate(R.layout.layout_search_top, (ViewGroup) recyclerView.getParent(), false);
        mInflater = LayoutInflater.from(getActivity());
        flowLayout = (TagFlowLayout) headView.findViewById(R.id.tag_flow_layout);
        flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {

                return false;
            }
        });
        return headView;
    }

    @Override
    public SearchPresenter initPresenter() {
        return new SearchPresenter();
    }

    @Override
    public void resultHotTerms(List<HotTerms> hotTerms) {
        this.hotTerms = hotTerms;
        flowLayout.setAdapter(new TagAdapter<HotTerms>(hotTerms) {
            @Override
            public View getView(FlowLayout parent, int position, HotTerms hotTerms) {
                TextView tv = (TextView) mInflater.inflate(R.layout.item_layout_knowledge,
                        flowLayout, false);
                tv.setBackground(ContextCompat.getDrawable(getActivity(), color[random.nextInt(color.length)]));
                tv.setText(hotTerms.getName());
                return tv;
            }
        });
    }

    @Override
    public void resultSearch(ArticleMessage message) {

    }
}
