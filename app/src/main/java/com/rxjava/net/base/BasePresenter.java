package com.rxjava.net.base;

import com.rxjava.net.http.NetDisposableObserver;
import com.rxjava.net.http.factory.ModelTransformerFactory;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class BasePresenter<V extends IBaseView> implements IBasePresenter<V> {

    private CompositeDisposable compositeDisposable = new CompositeDisposable(); //容器
    private V mView;

    @Override
    public void attachView(V paramV) {
        this.mView = paramV;
    }

    @Override
    public void detachView() {
        if (isViewAttached()) {
            this.mView = null;
        }
    }

    @Override
    public void destroy() {
        detachView();
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.clear();  //切断所有水管
        }
    }

    @Override
    public void subscribeNetwork(Observable paramObservable, NetDisposableObserver paramObserver) {
        paramObservable
                .compose(ModelTransformerFactory.getBaseModelTransformer())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(paramObserver);
        compositeDisposable.add(paramObserver);
    }

    @Override
    public void subscribeNetwork(Observable paramObservable, NetDisposableObserver paramObserver, ObservableTransformer paramTransformer) {
        paramObservable.compose(paramTransformer)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(paramObserver);
        compositeDisposable.add(paramObserver);
    }

    public IBaseView getView() {
        return this.mView;
    }

    public boolean isViewAttached() {
        return this.mView != null;
    }
}
