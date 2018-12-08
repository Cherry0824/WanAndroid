package com.rxjava.net.ui.main;

import android.Manifest;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rxjava.net.R;
import com.rxjava.net.base.BaseActivity;
import com.rxjava.net.base.BasePresenter;
import com.rxjava.net.bean.User;
import com.rxjava.net.comm.Constants;
import com.rxjava.net.mvp.contracts.UserContracts;
import com.rxjava.net.mvp.presenter.UserPresenter;
import com.rxjava.net.utils.SPUtils;

import java.lang.ref.WeakReference;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class WelcomeActivity extends BaseActivity<UserPresenter>
        implements EasyPermissions.PermissionCallbacks, UserContracts.View {
    private String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private WelcomeActivity.MessageHandler mMessageHandler;


    @Override
    public UserPresenter initPresenter() {
        return new UserPresenter();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mMessageHandler = new MessageHandler(this);
        if (SPUtils.getInstance(Constants.USER).getBoolean(Constants.LOGIN)) {
            mPresenter.requestLogin(SPUtils.getInstance(Constants.USER).getString(Constants.USERNAME)
                    , SPUtils.getInstance(Constants.USER).getString(Constants.PASSWORD));
        } else
            mMessageHandler.postDelayed(runnable, 3000);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };

    @Override
    public void resultLogin(User user) {
        mMessageHandler.postDelayed(runnable, 3000);
    }

    @Override
    public void resultRegister(User user) {

    }

    @Override
    public void resultLogout() {

    }

    private static class MessageHandler extends Handler {

        private final WeakReference<WelcomeActivity> mActivity;

        MessageHandler(WelcomeActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message message) {
            WelcomeActivity activity = mActivity.get();
            if (activity == null) {
                return;
            }
            activity.handleMessage(message);
        }
    }

    public void handleMessage(Message message) {

    }

    /**
     * EasyPermissions 权限处理请求结果
     *
     * @param requestCode
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * 权限获取成功
     *
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }
}
