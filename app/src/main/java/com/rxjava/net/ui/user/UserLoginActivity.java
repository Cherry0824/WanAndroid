package com.rxjava.net.ui.user;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rxjava.net.R;
import com.rxjava.net.base.BaseActivity;
import com.rxjava.net.base.BasePresenter;
import com.rxjava.net.bean.User;
import com.rxjava.net.comm.Constants;
import com.rxjava.net.comm.event.EventBusCode;
import com.rxjava.net.comm.event.MessageEvent;
import com.rxjava.net.mvp.contracts.UserContracts;
import com.rxjava.net.mvp.presenter.UserPresenter;
import com.rxjava.net.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class UserLoginActivity extends BaseActivity<UserPresenter> implements UserContracts.View {
    @BindView(R.id.title_name)
    TextView titleName;

    @BindView(R.id.login_name)
    EditText loginName;
    @BindView(R.id.login_pwd)
    EditText loginPwd;
    @BindView(R.id.login_pwd_rp)
    EditText loginPwdRp;

    @BindView(R.id.login_name_line)
    View nameLine;
    @BindView(R.id.login_pwd_line)
    View pwdLine;
    @BindView(R.id.login_pwd_line_rp)
    View pwdLineRp;
    @BindView(R.id.login_clear_name)
    ImageView clearName;
    @BindView(R.id.login_clear_pwd)
    ImageView clearPwd;
    @BindView(R.id.login_clear_pwd_rp)
    ImageView clearPwdRp;

    private boolean isrRegister = false;

    @Override
    public UserPresenter initPresenter() {
        return new UserPresenter();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_user_login;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        loginName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    nameLine.setBackgroundColor(ContextCompat.getColor(UserLoginActivity.this, R.color.main_style_blue));
                    clearName.setVisibility(View.VISIBLE);
                } else {
                    nameLine.setBackgroundColor(ContextCompat.getColor(UserLoginActivity.this, R.color.gary_f5f5f9));
                    clearName.setVisibility(View.GONE);
                }


            }
        });
        loginPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    pwdLine.setBackgroundColor(ContextCompat.getColor(UserLoginActivity.this, R.color.main_style_blue));
                    clearPwd.setVisibility(View.VISIBLE);
                } else {
                    pwdLine.setBackgroundColor(ContextCompat.getColor(UserLoginActivity.this, R.color.gary_f5f5f9));
                    clearPwd.setVisibility(View.GONE);
                }
            }
        });
        loginPwdRp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    pwdLineRp.setBackgroundColor(ContextCompat.getColor(UserLoginActivity.this, R.color.main_style_blue));
                    clearPwdRp.setVisibility(View.VISIBLE);
                } else {
                    pwdLineRp.setBackgroundColor(ContextCompat.getColor(UserLoginActivity.this, R.color.gary_f5f5f9));
                    clearPwdRp.setVisibility(View.GONE);
                }
            }
        });
    }


    @OnClick({R.id.title_back, R.id.login_submit, R.id.login_register
            , R.id.login_forget_pwd, R.id.login_clear_name
            , R.id.login_clear_pwd, R.id.login_clear_pwd_rp})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.login_clear_name:
                loginName.setText("");
                break;
            case R.id.login_clear_pwd:
                loginPwd.setText("");
                break;
            case R.id.login_clear_pwd_rp:
                loginPwdRp.setText("");
                break;
            case R.id.login_submit:
                String name = loginName.getText().toString().trim();
                String password = loginPwd.getText().toString().trim();
                String passwordRp = loginPwdRp.getText().toString().trim();
                if (TextUtils.isEmpty(name) || name.length() < 6) {
                    Toasty.error(this, "用户名最少6位", Toast.LENGTH_SHORT, true).show();
                    return;
                }
                if (TextUtils.isEmpty(password) || password.length() < 6 || password.length() > 50) {
                    Toasty.error(this, "密码6~50位且为数字、字母、-、_", Toast.LENGTH_SHORT, true).show();
                    return;
                }
                if (isrRegister) {
                    if (TextUtils.isEmpty(passwordRp) || TextUtils.equals(password, passwordRp)) {
                        Toasty.error(this, "两次密码输入不一致", Toast.LENGTH_SHORT, true).show();
                        return;
                    }
                }
                if (isrRegister)
                    mPresenter.requestRegister(name, password, passwordRp);
                else
                    mPresenter.requestLogin(name, password);

                break;
        }
    }

    @Override
    public void resultLogin(User user) {
        Toasty.success(this, "登录成功", Toast.LENGTH_SHORT, true).show();
        SPUtils.getInstance(Constants.USER).put(Constants.LOGIN, true);
        SPUtils.getInstance(Constants.USER).put(Constants.USERNAME, user.getUsername());
        SPUtils.getInstance(Constants.USER).put(Constants.PASSWORD, user.getPassword());
        EventBus.getDefault().post(new MessageEvent(EventBusCode.EVENT_REFRESH_USER));

        finish();
    }

    @Override
    public void resultRegister(User user) {
        Toasty.success(this, "注册成功，顺便帮你登录了", Toast.LENGTH_SHORT, true).show();
        SPUtils.getInstance(Constants.USER).put(Constants.LOGIN, true);
        SPUtils.getInstance(Constants.USER).put(Constants.USERNAME, user.getUsername());
        SPUtils.getInstance(Constants.USER).put(Constants.PASSWORD, user.getPassword());
        EventBus.getDefault().post(new MessageEvent(EventBusCode.EVENT_REFRESH_USER));
        finish();
    }

    @Override
    public void resultLogout() {

    }
}
