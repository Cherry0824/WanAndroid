package com.rxjava.net.mvp.contracts;

import com.rxjava.net.base.IBaseView;
import com.rxjava.net.bean.User;

/**
 * Created by Lenovo on 2018/12/1.
 */

public interface UserContracts {
    interface View extends IBaseView {
        void resultLogin(User user);

        void resultRegister(User user);

        void resultLogout();
    }

    interface Presenter {
        void requestLogin(String username, String password);

        void requestRegister(String username, String password, String repassword);

        void requestLogout();
    }
}
