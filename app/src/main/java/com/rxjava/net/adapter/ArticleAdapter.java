package com.rxjava.net.adapter;

import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rxjava.net.R;
import com.rxjava.net.bean.ArticleItem;

import java.util.List;

/**
 * Created by Lenovo on 2018/11/1.
 */

public class ArticleAdapter extends BaseQuickAdapter<ArticleItem, BaseViewHolder> {


    public ArticleAdapter(List<ArticleItem> data) {
        super(R.layout.item_layout_article, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleItem item) {
        helper.setText(R.id.article_title, Html.fromHtml(item.getTitle()));
        helper.setText(R.id.article_author, item.getAuthor());
        helper.setText(R.id.article_push_time, item.getNiceDate());
        if (item.getTags() != null && item.getTags().size() > 0) {
            helper.setText(R.id.article_tag, item.getTags().get(0).getName());
        } else {
            helper.getView(R.id.article_tag).setVisibility(View.GONE);
        }
        if (item.isTop())
            helper.setVisible(R.id.article_top, true);
        else
            helper.getView(R.id.article_top).setVisibility(View.GONE);

        if (item.isFresh())
            helper.setVisible(R.id.article_new, true);
        else
            helper.getView(R.id.article_new).setVisibility(View.GONE);

        if (!TextUtils.isEmpty(item.getsuperChapterName()) && !TextUtils.isEmpty(item.getChapterName())) {
            helper.setText(R.id.article_type_style, " / ");
        } else
            helper.setText(R.id.article_type_style, "");
        helper.setText(R.id.article_type_1, item.getsuperChapterName());
        helper.setText(R.id.article_type_2, item.getChapterName());
        if (item.isCollect())
            helper.setImageDrawable(R.id.article_collection, ContextCompat.getDrawable(mContext, R.drawable.ic_collection_selected));
        else
            helper.setImageDrawable(R.id.article_collection, ContextCompat.getDrawable(mContext, R.drawable.ic_collection_normal));


        helper.addOnClickListener(R.id.article_collection);

    }

    public String replaceTitle(String title) {
        if (title.contains("<em class='highlight'>")) {
            return Html.fromHtml(title).toString();
        } else
            return title;
    }
}
