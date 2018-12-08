package com.rxjava.net.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rxjava.net.R;
import com.rxjava.net.bean.ProjectClass;

import java.util.List;

/**
 * Created by Lenovo on 2018/12/4.
 */

public class ProjectTreeAdapter extends BaseQuickAdapter<ProjectClass, BaseViewHolder> {


    public ProjectTreeAdapter(List<ProjectClass> data) {
        super(R.layout.item_layout_project_tree, data);

    }


    @Override
    protected void convert(BaseViewHolder helper, ProjectClass item) {
        helper.setText(R.id.tree_name, item.getName());

    }
}
