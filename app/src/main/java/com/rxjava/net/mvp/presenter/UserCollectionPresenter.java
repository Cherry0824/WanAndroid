package com.rxjava.net.mvp.presenter;

import com.rxjava.net.base.BasePresenter;
import com.rxjava.net.base.BaseResult;
import com.rxjava.net.base.IBaseView;
import com.rxjava.net.bean.CollectionMessage;
import com.rxjava.net.comm.api.ApiService;
import com.rxjava.net.http.NetDisposableObserver;
import com.rxjava.net.http.factory.ApiFactory;
import com.rxjava.net.mvp.contracts.UserCollectionContracts;

/**
 * Created by Lenovo on 2018/12/6.
 */

public class UserCollectionPresenter extends BasePresenter implements UserCollectionContracts.Presenter {
    @Override
    public void requestUserCollection(int page) {
        subscribeNetwork(ApiFactory.create(ApiService.class).requestCollection(page)
                , new NetDisposableObserver<BaseResult<CollectionMessage>>((IBaseView) getView()) {
                    @Override
                    public void onNext(BaseResult<CollectionMessage> listBaseResult) {
                        ((UserCollectionContracts.View) getView()).resultUserCollection(listBaseResult.getData());
                    }
                });
    }

    @Override
    public void requestRemoveCollection(final int id, int originId) {
        subscribeNetwork(ApiFactory.create(ApiService.class).requestRemoveCollectList(id, originId)
                , new NetDisposableObserver<BaseResult>((IBaseView) getView()) {
                    @Override
                    public void onNext(BaseResult listBaseResult) {
                        ((UserCollectionContracts.View) getView()).resultRemoveCollection(id);
                    }
                });
    }
}
