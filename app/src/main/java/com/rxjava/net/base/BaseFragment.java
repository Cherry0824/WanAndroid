package com.rxjava.net.base;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rxjava.net.comm.event.MessageEvent;
import com.rxjava.net.ui.WebViewActivity;
import com.rxjava.net.ui.user.UserLoginActivity;

import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Lenovo on 2018/11/30.
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements IBaseView {

    public P mPresenter;
    private View rootView;
    protected Unbinder mUnBinder;
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        mUnBinder = ButterKnife.bind(this, rootView);
        if (initPresenter() != null) {
            mPresenter = initPresenter();
            mPresenter.attachView(this);
        }
        initViews(savedInstanceState);
        return rootView;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    public void intentWebView(String url) {
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }
    public void intentLogin(){
        Intent intent = new Intent(getActivity(), UserLoginActivity.class);
        startActivity(intent);
    }
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    public abstract int getLayoutId();

    public abstract void initViews(Bundle savedInstanceState);

    public abstract P initPresenter();

    @Subscribe
    public void onMoonEvent(MessageEvent messageEvent) {
    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }

    @Override
    public void hideRefreshView() {

    }

    @Override
    public void tokenInvalid() {

    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null && mPresenter.isViewAttached()) {
            mPresenter.destroy();
            mPresenter = null;
        }

    }
}
