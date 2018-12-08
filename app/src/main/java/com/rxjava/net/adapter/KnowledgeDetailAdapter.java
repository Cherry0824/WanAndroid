package com.rxjava.net.adapter;

import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rxjava.net.R;
import com.rxjava.net.bean.ArticleItem;
import com.rxjava.net.bean.ProjectClass;

import java.util.List;

/**
 * Created by Lenovo on 2018/12/4.
 */

public class KnowledgeDetailAdapter extends BaseQuickAdapter<ArticleItem, BaseViewHolder> {


    public KnowledgeDetailAdapter(List<ArticleItem> data) {
        super(R.layout.item_layout_knowledge_details, data);

    }


    @Override
    protected void convert(BaseViewHolder helper, ArticleItem item) {
        helper.setText(R.id.knowledge_author, item.getAuthor());
        helper.setText(R.id.knowledge_push_time, item.getNiceDate());
        helper.setText(R.id.knowledge_title, item.getTitle());
        if (item.isCollect())
            helper.setImageDrawable(R.id.knowledge_collection, ContextCompat.getDrawable(mContext, R.drawable.ic_collection_selected));
        else
            helper.setImageDrawable(R.id.knowledge_collection, ContextCompat.getDrawable(mContext, R.drawable.ic_collection_normal));

    }
}
