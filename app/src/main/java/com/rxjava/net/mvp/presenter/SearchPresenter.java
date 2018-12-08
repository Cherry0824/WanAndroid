package com.rxjava.net.mvp.presenter;

import com.rxjava.net.base.BasePresenter;
import com.rxjava.net.base.BaseResult;
import com.rxjava.net.base.IBaseView;
import com.rxjava.net.bean.ArticleMessage;
import com.rxjava.net.bean.HotTerms;
import com.rxjava.net.comm.api.ApiService;
import com.rxjava.net.http.NetDisposableObserver;
import com.rxjava.net.http.factory.ApiFactory;
import com.rxjava.net.mvp.contracts.SearchContracts;

import java.util.List;

/**
 * Created by Lenovo on 2018/12/6.
 */

public class SearchPresenter extends BasePresenter implements SearchContracts.Presenter {
    @Override
    public void requestHotTerms() {
        subscribeNetwork(ApiFactory.create(ApiService.class).searchHotTerms()
                , new NetDisposableObserver<BaseResult<List<HotTerms>>>((IBaseView) getView()) {
                    @Override
                    public void onNext(BaseResult<List<HotTerms>> userBaseResult) {
                        ((SearchContracts.View) getView()).resultHotTerms(userBaseResult.getData());
                    }
                });
    }

    @Override
    public void requestRegister(int page, String key) {
        subscribeNetwork(ApiFactory.create(ApiService.class).requestSearch(page, key)
                , new NetDisposableObserver<BaseResult<ArticleMessage>>((IBaseView) getView()) {
                    @Override
                    public void onNext(BaseResult<ArticleMessage> userBaseResult) {
                        ((SearchContracts.View) getView()).resultSearch(userBaseResult.getData());
                    }
                });
    }
}
