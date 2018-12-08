package com.rxjava.net.mvp.contracts;

import com.rxjava.net.base.IBaseView;
import com.rxjava.net.bean.ArticleMessage;
import com.rxjava.net.bean.HotTerms;

import java.util.List;

/**
 * Created by Lenovo on 2018/12/6.
 */

public interface CollectionContracts {
    interface View extends IBaseView {
        void resultAddCollections(int id);

        void resultRemoveCollections(int id);

    }

    interface Presenter {
        void requestAddCollections(int id);

        void requestRemoveCollections(int id);

    }
}
