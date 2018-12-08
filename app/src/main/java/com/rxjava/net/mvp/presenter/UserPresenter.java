package com.rxjava.net.mvp.presenter;

import com.rxjava.net.base.BasePresenter;
import com.rxjava.net.base.BaseResult;
import com.rxjava.net.base.IBaseView;
import com.rxjava.net.bean.User;
import com.rxjava.net.comm.api.ApiHost;
import com.rxjava.net.comm.api.ApiService;
import com.rxjava.net.http.NetDisposableObserver;
import com.rxjava.net.http.factory.ApiFactory;
import com.rxjava.net.mvp.contracts.UserContracts;

/**
 * Created by Lenovo on 2018/12/1.
 */

public class UserPresenter extends BasePresenter implements UserContracts.Presenter {
    @Override
    public void requestLogin(String username, String password) {
        subscribeNetwork(ApiFactory.create(ApiService.class)
                .userLogin(username, password), new NetDisposableObserver<BaseResult<User>>((IBaseView) getView()) {
            @Override
            public void onNext(BaseResult<User> userBaseResult) {
                ((UserContracts.View) getView()).resultLogin(userBaseResult.getData());
            }
        });
    }

    @Override
    public void requestRegister(String username, String password, String repassword) {
        subscribeNetwork(ApiFactory.create(ApiService.class)
                .userLogin(username, password), new NetDisposableObserver<BaseResult<User>>((IBaseView) getView()) {
            @Override
            public void onNext(BaseResult<User> userBaseResult) {
                ((UserContracts.View) getView()).resultRegister(userBaseResult.getData());
            }
        });
    }

    @Override
    public void requestLogout() {
        subscribeNetwork(ApiFactory.create(ApiService.class)
                .userLogout(), new NetDisposableObserver<BaseResult<User>>((IBaseView) getView()) {
            @Override
            public void onNext(BaseResult<User> userBaseResult) {
                ((UserContracts.View) getView()).resultLogout();
            }
        });
    }
}
