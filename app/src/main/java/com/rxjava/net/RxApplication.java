package com.rxjava.net;

import android.app.Application;

/**
 * Created by Lenovo on 2018/11/30.
 */

public class RxApplication extends Application {
    public static RxApplication instances;

    public static RxApplication getInstances() {
        return instances;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instances = this;
    }

}
