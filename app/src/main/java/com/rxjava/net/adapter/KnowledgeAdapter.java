package com.rxjava.net.adapter;

import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rxjava.net.R;
import com.rxjava.net.bean.Knowledge;
import com.rxjava.net.bean.ProjectClass;
import com.rxjava.net.view.flowlayout.FlowLayout;
import com.rxjava.net.view.flowlayout.TagAdapter;
import com.rxjava.net.view.flowlayout.TagFlowLayout;

import java.util.List;
import java.util.Random;

/**
 * Created by Lenovo on 2018/12/4.
 */

public class KnowledgeAdapter extends BaseQuickAdapter<Knowledge, BaseViewHolder> {
    private LayoutInflater mInflater;

    private int[] color;

    private Random random = new Random();

    public KnowledgeAdapter(List<Knowledge> data, LayoutInflater mInflater) {
        super(R.layout.item_layout_knowledge_header, data);
        this.mInflater = mInflater;
        color = new int[]{R.drawable.round_bg_blue
                , R.drawable.round_bg_green
                , R.drawable.round_bg_orange
                , R.drawable.round_bg_pink
                , R.drawable.round_bg_yellow};
    }


    public interface KnowledgeSingerClickListener {
        void click(Knowledge knowledge);
    }

    private KnowledgeSingerClickListener knowledgeSingerClickListener;

    public void setKnowledgeSingerClickListener(KnowledgeSingerClickListener updateListener) {
        this.knowledgeSingerClickListener = updateListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, final Knowledge item) {
        helper.setText(R.id.knowledge_name, item.getName());
        final TagFlowLayout flowLayout = (TagFlowLayout) helper.getView(R.id.tag_flow_layout);
        flowLayout.setAdapter(new TagAdapter<Knowledge>(item.getChildren()) {
            @Override
            public View getView(FlowLayout parent, int position, final Knowledge knowledge) {
                TextView tv = (TextView) mInflater.inflate(R.layout.item_layout_knowledge,
                        flowLayout, false);
                tv.setBackground(ContextCompat.getDrawable(mContext, color[random.nextInt(color.length)]));
                tv.setText(knowledge.getName());
                return tv;
            }

        });
        flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                knowledgeSingerClickListener.click(item.getChildren().get(position));
                return false;
            }
        });
    }
}
