package com.rxjava.net.base;

/**
 * Created by Administrator on 2017/3/21 0021.
 */

public interface IBaseView  {

    void showLoadingView();

    void hideLoadingView();

    void hideRefreshView();

    void tokenInvalid();

    void showToast(String msg);

}