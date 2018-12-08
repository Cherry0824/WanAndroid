package com.rxjava.net.mvp.presenter;

import com.rxjava.net.base.BasePresenter;
import com.rxjava.net.base.BaseResult;
import com.rxjava.net.base.IBaseView;
import com.rxjava.net.bean.Knowledge;
import com.rxjava.net.bean.Navigation;
import com.rxjava.net.bean.NavigationContent;
import com.rxjava.net.comm.api.ApiService;
import com.rxjava.net.http.NetDisposableObserver;
import com.rxjava.net.http.factory.ApiFactory;
import com.rxjava.net.mvp.contracts.KnowledgeContracts;
import com.rxjava.net.mvp.contracts.NavigationContracts;

import java.util.List;

/**
 * Created by Lenovo on 2018/12/4.
 */

public class NavigationPresenter extends BasePresenter implements NavigationContracts.Presenter {

    @Override
    public void requestNavigation() {
        subscribeNetwork(ApiFactory.create(ApiService.class).requestNavigationData()
                , new NetDisposableObserver<BaseResult<List<Navigation>>>((IBaseView) getView()) {
                    @Override
                    public void onNext(BaseResult<List<Navigation>> listBaseResult) {
                        ((NavigationContracts.View) getView()).resultNavigation(listBaseResult.getData());
                    }
                });
    }
}
