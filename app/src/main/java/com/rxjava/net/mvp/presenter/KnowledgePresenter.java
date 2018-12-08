package com.rxjava.net.mvp.presenter;

import com.rxjava.net.base.BasePresenter;
import com.rxjava.net.base.BaseResult;
import com.rxjava.net.base.IBaseView;
import com.rxjava.net.bean.Knowledge;
import com.rxjava.net.comm.api.ApiService;
import com.rxjava.net.http.NetDisposableObserver;
import com.rxjava.net.http.factory.ApiFactory;
import com.rxjava.net.mvp.contracts.KnowledgeContracts;

import java.util.List;

/**
 * Created by Lenovo on 2018/12/4.
 */

public class KnowledgePresenter extends BasePresenter implements KnowledgeContracts.Presenter {
    @Override
    public void requestKnowledge() {
        subscribeNetwork(ApiFactory.create(ApiService.class).requestKnowledge()
                , new NetDisposableObserver<BaseResult<List<Knowledge>>>((IBaseView) getView()) {
                    @Override
                    public void onNext(BaseResult<List<Knowledge>> listBaseResult) {
                        ((KnowledgeContracts.View) getView()).resultKnowledge(listBaseResult.getData());
                    }
                });
    }
}
