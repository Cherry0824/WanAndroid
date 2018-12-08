package com.rxjava.net.mvp.contracts;

import com.rxjava.net.base.IBaseView;
import com.rxjava.net.bean.Knowledge;
import com.rxjava.net.bean.Navigation;

import java.util.List;

/**
 * Created by Lenovo on 2018/12/4.
 */

public interface NavigationContracts {
    interface View extends IBaseView {
        void resultNavigation(List<Navigation> navigationList);
    }

    interface Presenter {
        void requestNavigation();
    }
}
