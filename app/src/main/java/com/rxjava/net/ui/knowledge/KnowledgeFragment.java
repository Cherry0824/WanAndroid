package com.rxjava.net.ui.knowledge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rxjava.net.R;
import com.rxjava.net.adapter.KnowledgeAdapter;
import com.rxjava.net.adapter.ProjectAdapter;
import com.rxjava.net.adapter.ProjectTreeAdapter;
import com.rxjava.net.base.BaseFragment;
import com.rxjava.net.base.BasePresenter;
import com.rxjava.net.bean.Knowledge;
import com.rxjava.net.bean.KnowledgeSection;
import com.rxjava.net.bean.ProjectClass;
import com.rxjava.net.bean.ProjectItem;
import com.rxjava.net.mvp.contracts.KnowledgeContracts;
import com.rxjava.net.mvp.presenter.KnowledgePresenter;
import com.rxjava.net.ui.project.ProjectFragment;
import com.rxjava.net.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Lenovo on 2018/12/4.
 */

public class KnowledgeFragment extends BaseFragment<KnowledgePresenter> implements KnowledgeContracts.View {

    public static KnowledgeFragment newInstance() {
        Bundle bundle = new Bundle();
        KnowledgeFragment fragment = new KnowledgeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    private KnowledgeAdapter mAdapter;

    private LinearLayoutManager linearLayoutManager;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_knowledge;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new KnowledgeAdapter(new ArrayList<Knowledge>(), LayoutInflater.from(getActivity()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setKnowledgeSingerClickListener(new KnowledgeAdapter.KnowledgeSingerClickListener() {
            @Override
            public void click(Knowledge knowledge) {
                Intent intent = new Intent(getActivity(), KnowledgeDetailsActivity.class);
                intent.putExtra("title", knowledge.getName());
                intent.putExtra("cid", knowledge.getId());
                startActivity(intent);
            }
        });

        mPresenter.requestKnowledge();
    }

    @Override
    public KnowledgePresenter initPresenter() {
        return new KnowledgePresenter();
    }

    @Override
    public void resultKnowledge(List<Knowledge> knowledge) {
        mAdapter.setNewData(knowledge);
    }
}
