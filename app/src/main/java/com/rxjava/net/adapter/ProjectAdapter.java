package com.rxjava.net.adapter;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rxjava.net.R;
import com.rxjava.net.bean.ArticleItem;
import com.rxjava.net.bean.ProjectItem;
import com.rxjava.net.utils.ImageUtils;

import java.util.List;

/**
 * Created by Lenovo on 2018/12/3.
 */

public class ProjectAdapter extends BaseQuickAdapter<ProjectItem, BaseViewHolder> {


    public ProjectAdapter(List<ProjectItem> data) {
        super(R.layout.item_layout_project, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectItem item) {
        helper.setText(R.id.project_title, item.getTitle());
        helper.setText(R.id.project_author, item.getAuthor());
        helper.setText(R.id.project_push_time, item.getNiceDate());
        helper.setText(R.id.project_desc, item.getDesc());
        ImageUtils.show(mContext, item.getEnvelopePic(), (ImageView) helper.getView(R.id.project_image));
        if (item.getCollect())
            helper.setImageDrawable(R.id.project_collection, ContextCompat.getDrawable(mContext, R.drawable.ic_collection_selected));
        else
            helper.setImageDrawable(R.id.project_collection, ContextCompat.getDrawable(mContext, R.drawable.ic_collection_normal));
        helper.addOnClickListener(R.id.project_collection);
    }

}
