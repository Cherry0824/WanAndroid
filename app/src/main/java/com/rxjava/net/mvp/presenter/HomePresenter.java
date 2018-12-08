package com.rxjava.net.mvp.presenter;

import android.util.Log;

import com.rxjava.net.base.BasePresenter;
import com.rxjava.net.base.BaseResult;
import com.rxjava.net.base.IBaseView;
import com.rxjava.net.bean.ArticleItem;
import com.rxjava.net.bean.ArticleMessage;
import com.rxjava.net.bean.Banner;
import com.rxjava.net.bean.ProjectMessage;
import com.rxjava.net.comm.api.ApiService;
import com.rxjava.net.http.NetDisposableObserver;
import com.rxjava.net.http.factory.ApiFactory;
import com.rxjava.net.mvp.contracts.HomeContracts;

import java.util.List;

/**
 * Created by Lenovo on 2018/12/2.
 */

public class HomePresenter extends CollectionPresenter implements HomeContracts.Presenter {
    @Override
    public void requestBanner() {
        subscribeNetwork(ApiFactory.create(ApiService.class).requestBanner()
                , new NetDisposableObserver<BaseResult<List<Banner>>>((IBaseView) getView()) {
                    @Override
                    public void onNext(BaseResult<List<Banner>> listBaseResult) {
                        ((HomeContracts.View) getView()).resultBanner(listBaseResult.getData());
                    }
                });
    }

    @Override
    public void requestHomeArticle(int page) {
        subscribeNetwork(ApiFactory.create(ApiService.class).requestHomeArticle(page)
                , new NetDisposableObserver<BaseResult<ArticleMessage>>((IBaseView) getView()) {
                    @Override
                    public void onNext(BaseResult<ArticleMessage> listBaseResult) {
                        ((HomeContracts.View) getView()).resultHomeArticle(listBaseResult.getData());
                    }
                });
    }

    @Override
    public void requestHomeArticleTop() {
        subscribeNetwork(ApiFactory.create(ApiService.class).requestHomeArticleTop()
                , new NetDisposableObserver<BaseResult<List<ArticleItem>>>((IBaseView) getView()) {
                    @Override
                    public void onNext(BaseResult<List<ArticleItem>> listBaseResult) {
                        ((HomeContracts.View) getView()).resultHomeArticleTop(listBaseResult.getData());
                    }
                });
    }

    @Override
    public void requestHomeProject(int page) {
        subscribeNetwork(ApiFactory.create(ApiService.class).requestHomeProject(page)
                , new NetDisposableObserver<BaseResult<ProjectMessage>>((IBaseView) getView()) {
                    @Override
                    public void onNext(BaseResult<ProjectMessage> listBaseResult) {
                        ((HomeContracts.View) getView()).resultHomeProject(listBaseResult.getData());
                    }
                });
    }
}
