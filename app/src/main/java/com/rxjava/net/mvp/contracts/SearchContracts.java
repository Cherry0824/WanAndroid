package com.rxjava.net.mvp.contracts;

import com.rxjava.net.base.IBaseView;
import com.rxjava.net.bean.ArticleMessage;
import com.rxjava.net.bean.HotTerms;
import com.rxjava.net.bean.User;

import java.util.List;

/**
 * Created by Lenovo on 2018/12/6.
 */

public interface SearchContracts {
    interface View extends IBaseView {
        void resultHotTerms(List<HotTerms> hotTerms);

        void resultSearch(ArticleMessage message);

    }

    interface Presenter {
        void requestHotTerms();

        void requestRegister(int page, String key);

    }
}
