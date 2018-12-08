package com.rxjava.net.http;

import com.rxjava.net.R;
import com.rxjava.net.RxApplication;
import com.rxjava.net.base.IBaseView;
import com.rxjava.net.comm.api.ApiStatus;
import com.rxjava.net.http.exception.ResponseException;

import io.reactivex.observers.DisposableObserver;

public abstract class NetDisposableObserver<T> extends DisposableObserver<T> {

    private IBaseView mBaseMvpView;
    private boolean isShowLoadingView;

    public NetDisposableObserver(IBaseView baseMvpView) {
        this.mBaseMvpView = baseMvpView;
        this.isShowLoadingView = true;
    }

    public NetDisposableObserver(IBaseView baseMvpView, boolean isShowLoadingView) {
        this.mBaseMvpView = baseMvpView;
        this.isShowLoadingView = isShowLoadingView;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (this.mBaseMvpView != null && this.isShowLoadingView) {
            this.mBaseMvpView.showLoadingView();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        if (this.mBaseMvpView != null) {
            this.mBaseMvpView.hideLoadingView();
            this.mBaseMvpView.hideRefreshView();
            if (throwable instanceof ResponseException) {
                ResponseException responseException = (ResponseException) throwable;
                if (responseException.getStatus() == ApiStatus.HTTP_TOKEN) {
                    this.mBaseMvpView.tokenInvalid();
                } else if (responseException.getStatus() != ApiStatus.HTTP_SUCCESS) {
                    this.mBaseMvpView.showToast(responseException.getMessage());
                } else {
                    this.mBaseMvpView.showToast(RxApplication.getInstances().getResources().getString(R.string.http_error));
                }
            }
        }
    }

    @Override
    public void onComplete() {
        if (this.mBaseMvpView != null) {
            this.mBaseMvpView.hideLoadingView();
            this.mBaseMvpView.hideRefreshView();
        }
    }
}
