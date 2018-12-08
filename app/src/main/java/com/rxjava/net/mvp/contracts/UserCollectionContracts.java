package com.rxjava.net.mvp.contracts;

import com.rxjava.net.base.IBaseView;
import com.rxjava.net.bean.CollectionMessage;
import com.rxjava.net.bean.User;

/**
 * Created by Lenovo on 2018/12/6.
 */

public interface UserCollectionContracts {
    interface View extends IBaseView {
        void resultUserCollection(CollectionMessage collectionMessage);

        void resultRemoveCollection(int id);

    }

    interface Presenter {
        void requestUserCollection(int page);

        void requestRemoveCollection(int id,int originId);
    }
}
