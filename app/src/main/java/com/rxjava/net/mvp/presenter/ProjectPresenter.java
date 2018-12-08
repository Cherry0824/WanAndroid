package com.rxjava.net.mvp.presenter;

import com.rxjava.net.base.BasePresenter;
import com.rxjava.net.base.BaseResult;
import com.rxjava.net.base.IBaseView;
import com.rxjava.net.bean.ProjectClass;
import com.rxjava.net.bean.ProjectMessage;
import com.rxjava.net.comm.api.ApiService;
import com.rxjava.net.http.NetDisposableObserver;
import com.rxjava.net.http.factory.ApiFactory;
import com.rxjava.net.mvp.contracts.ProjectContracts;

import java.util.List;

/**
 * Created by Lenovo on 2018/12/4.
 */

public class ProjectPresenter extends BasePresenter implements ProjectContracts.Presenter {
    @Override
    public void requestProjectTree() {
        subscribeNetwork(ApiFactory.create(ApiService.class).requestProjectClass()
                , new NetDisposableObserver<BaseResult<List<ProjectClass>>>((IBaseView) getView()) {
                    @Override
                    public void onNext(BaseResult<List<ProjectClass>> listBaseResult) {
                        ((ProjectContracts.View) getView()).resultProjectTree(listBaseResult.getData());
                    }
                });
    }

    @Override
    public void requestProjectList(int page, int cid) {
        subscribeNetwork(ApiFactory.create(ApiService.class).requestProjectItem(page, cid)
                , new NetDisposableObserver<BaseResult<ProjectMessage>>((IBaseView) getView()) {
                    @Override
                    public void onNext(BaseResult<ProjectMessage> listBaseResult) {
                        ((ProjectContracts.View) getView()).resultProjectList(listBaseResult.getData());
                    }
                });
    }
}
