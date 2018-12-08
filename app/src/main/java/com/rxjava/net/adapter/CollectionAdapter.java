package com.rxjava.net.adapter;

import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rxjava.net.R;
import com.rxjava.net.bean.ArticleItem;
import com.rxjava.net.bean.CollectionItem;

import java.util.List;

/**
 * Created by Lenovo on 2018/11/1.
 */

public class CollectionAdapter extends BaseQuickAdapter<CollectionItem, BaseViewHolder> {


    public CollectionAdapter(List<CollectionItem> data) {
        super(R.layout.item_layout_collection, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, CollectionItem item) {
        helper.setText(R.id.collection_title, Html.fromHtml(item.getTitle()));
        helper.setText(R.id.collection_author, item.getAuthor());
        helper.setText(R.id.collection_push_time, item.getNiceDate());
        helper.setText(R.id.collection_type, item.getChapterName());
        helper.setImageDrawable(R.id.collection_icon, ContextCompat.getDrawable(mContext, R.drawable.ic_collection_selected));
        helper.addOnClickListener(R.id.collection_icon);

    }

    public String replaceTitle(String title) {
        if (title.contains("<em class='highlight'>")) {
            return Html.fromHtml(title).toString();
        } else
            return title;
    }
}
