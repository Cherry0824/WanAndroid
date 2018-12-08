package com.rxjava.net.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.rxjava.net.R;
import com.rxjava.net.comm.event.MessageEvent;
import com.rxjava.net.ui.WebViewActivity;
import com.rxjava.net.ui.main.MainActivity;
import com.rxjava.net.ui.user.UserLoginActivity;

import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Lenovo on 2018/11/30.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IBaseView {
    protected P mPresenter;
    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        mUnBinder = ButterKnife.bind(this);
        setStatusColor();
        if (initPresenter() != null) {
            mPresenter = initPresenter();
            mPresenter.attachView(this);
        }
        initViews(savedInstanceState);

    }

    public abstract P initPresenter();

    public abstract int getLayoutRes();

    public abstract void initViews(Bundle savedInstanceState);

    @Subscribe
    public void onMoonEvent(MessageEvent messageEvent) {
    }

    protected void setStatusColor() {
        StatusBarUtil.setColorNoTranslucent(this, ContextCompat.getColor(this, R.color.main_style_blue));
        StatusBarUtil.setDarkMode(this);//白
    }


    public void intentWebView(String url) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    public void intentLogin() {
        Intent intent = new Intent(this, UserLoginActivity.class);
        startActivity(intent);
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
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void tokenInvalid() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnBinder != null)
            mUnBinder.unbind();
        if (mPresenter != null && mPresenter.isViewAttached()) {
            mPresenter.destroy();
            mPresenter = null;
        }
    }
}
