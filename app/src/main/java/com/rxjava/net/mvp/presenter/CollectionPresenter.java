package com.rxjava.net.mvp.presenter;

import com.rxjava.net.base.BasePresenter;
import com.rxjava.net.base.BaseResult;
import com.rxjava.net.base.IBaseView;
import com.rxjava.net.bean.ArticleItem;
import com.rxjava.net.comm.api.ApiService;
import com.rxjava.net.http.NetDisposableObserver;
import com.rxjava.net.http.factory.ApiFactory;
import com.rxjava.net.mvp.contracts.CollectionContracts;
import com.rxjava.net.mvp.contracts.HomeContracts;

import java.util.List;

/**
 * Created by Lenovo on 2018/12/6.
 */

public class CollectionPresenter extends BasePresenter implements CollectionContracts.Presenter {
    @Override
    public void requestAddCollections(final int id) {
        subscribeNetwork(ApiFactory.create(ApiService.class).requestAddCollect(id)
                , new NetDisposableObserver<BaseResult>((IBaseView) getView()) {
                    @Override
                    public void onNext(BaseResult listBaseResult) {
                        ((CollectionContracts.View) getView()).resultAddCollections(id);
                    }
                });
    }

    @Override
    public void requestRemoveCollections(final int id) {
        subscribeNetwork(ApiFactory.create(ApiService.class).requestRemoveCollect(id)
                , new NetDisposableObserver<BaseResult>((IBaseView) getView()) {
                    @Override
                    public void onNext(BaseResult listBaseResult) {
                        ((CollectionContracts.View) getView()).resultRemoveCollections(id);
                    }
                });
    }
}
