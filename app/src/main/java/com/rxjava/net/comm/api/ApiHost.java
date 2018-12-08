package com.rxjava.net.comm.api;

import com.rxjava.net.BuildConfig;
import com.rxjava.net.comm.Constants;
import com.rxjava.net.http.RetrofitHost;


public enum ApiHost implements RetrofitHost {

    WAN_ANDROID {
        public String getHost() {
            return Constants.WAN_ANDROID;
        }
    },
    OTHER {
        public String getHost() {
            return Constants.WAN_ANDROID;
        }
    };

}
