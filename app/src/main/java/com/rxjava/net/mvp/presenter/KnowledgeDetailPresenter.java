package com.rxjava.net.mvp.presenter;

import com.rxjava.net.base.BasePresenter;
import com.rxjava.net.base.BaseResult;
import com.rxjava.net.base.IBaseView;
import com.rxjava.net.bean.ArticleMessage;
import com.rxjava.net.bean.Knowledge;
import com.rxjava.net.comm.api.ApiService;
import com.rxjava.net.http.NetDisposableObserver;
import com.rxjava.net.http.factory.ApiFactory;
import com.rxjava.net.mvp.contracts.KnowledgeContracts;
import com.rxjava.net.mvp.contracts.KnowledgeDetailContracts;

import java.util.List;

/**
 * Created by Lenovo on 2018/12/4.
 */

public class KnowledgeDetailPresenter extends BasePresenter implements KnowledgeDetailContracts.Presenter {


    @Override
    public void requestKnowledge(int page, int cid) {
        subscribeNetwork(ApiFactory.create(ApiService.class).requestKnowledgeArticle(page, cid)
                , new NetDisposableObserver<BaseResult<ArticleMessage>>((IBaseView) getView()) {
                    @Override
                    public void onNext(BaseResult<ArticleMessage> articleMessageBaseResult) {
                        ((KnowledgeDetailContracts.View) getView()).resultKnowledge(articleMessageBaseResult.getData());
                    }


                });
    }
}
