package com.rxjava.net.mvp.contracts;

import com.rxjava.net.base.IBaseView;
import com.rxjava.net.bean.ArticleMessage;
import com.rxjava.net.bean.Knowledge;

import java.util.List;

/**
 * Created by Lenovo on 2018/12/4.
 */

public interface KnowledgeDetailContracts {
    interface View extends IBaseView {
        void resultKnowledge(ArticleMessage articleMessage);
    }

    interface Presenter {
        void requestKnowledge(int page, int cid);
    }
}
