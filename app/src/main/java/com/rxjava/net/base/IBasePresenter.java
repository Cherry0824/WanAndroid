package com.rxjava.net.base;


import com.rxjava.net.http.NetDisposableObserver;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;



public interface IBasePresenter<V extends IBaseView> {

    void attachView(V paramV);

    void detachView();

    void destroy();

    void subscribeNetwork(Observable paramObservable, NetDisposableObserver paramObserver);

    void subscribeNetwork(Observable paramObservable, NetDisposableObserver paramObserver, ObservableTransformer paramTransformer);
}