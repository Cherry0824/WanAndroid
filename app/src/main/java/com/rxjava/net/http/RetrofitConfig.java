package com.rxjava.net.http;

import com.google.gson.GsonBuilder;
import com.rxjava.net.http.cookie.CookiesManager;
import com.rxjava.net.http.factory.NoBodyConvertFactory;
import com.rxjava.net.http.intercept.LoggerInterceptor;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class RetrofitConfig {
    private static final int CONN_TIME_OUT = 15;
    private static final int READ_TIME_OUT = 15;
    private static final int WRITE_TIME_OUT = 15;
    private static volatile RetrofitConfig mRtf;
    private static HashMap<String, Retrofit> mRtfContainer;
    private Retrofit.Builder mRtfBuilder;


    private RetrofitConfig() {

        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        OkHttpClient.Builder mOkHttpBuilder = new OkHttpClient.Builder()
                .connectTimeout(CONN_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .addInterceptor(new LoggerInterceptor())
                .cookieJar(new CookiesManager())
                .retryOnConnectionFailure(true)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true; //信任所有Https
                    }
                });
        this.mRtfBuilder = new Retrofit.Builder().client(mOkHttpBuilder.build()).
                addConverterFactory(ScalarsConverterFactory.create()).//针对基本类型返回添加
                addConverterFactory(NoBodyConvertFactory.create()).//无响应体类型
                addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create())).//针对Json返回添加
                addCallAdapterFactory(RxJava2CallAdapterFactory.create());//针对Rx返回Observable添加


        mRtfContainer = new HashMap();
    }

    public static RetrofitConfig getInstance() {
        if (mRtf == null) {
            synchronized (RetrofitConfig.class) {
                if (mRtf == null) {
                    mRtf = new RetrofitConfig();
                }
            }
        }
        return mRtf;
    }


    public Retrofit getRetrofit(String paramString) {
        if (this.mRtfContainer.get(paramString) == null)
            mRtfContainer.put(paramString, this.mRtfBuilder.baseUrl(paramString).build());
        return this.mRtfContainer.get(paramString);
    }
}
