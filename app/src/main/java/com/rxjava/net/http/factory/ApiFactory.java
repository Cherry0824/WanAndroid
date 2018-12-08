package com.rxjava.net.http.factory;

import com.rxjava.net.comm.Constants;
import com.rxjava.net.http.RetrofitConfig;


public class ApiFactory {

    public static <T> T create(Class<T> paramClass) {
        return RetrofitConfig.getInstance().getRetrofit(Constants.WAN_ANDROID).create(paramClass);
    }

}
