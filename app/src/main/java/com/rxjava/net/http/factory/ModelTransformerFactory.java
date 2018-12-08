package com.rxjava.net.http.factory;


import com.rxjava.net.base.BaseResult;
import com.rxjava.net.comm.api.ApiStatus;
import com.rxjava.net.http.exception.ResponseExceptionHandler;
import com.rxjava.net.http.exception.ServerException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

public class ModelTransformerFactory {

    public ModelTransformerFactory() {
    }

    public static <T> ObservableTransformer<BaseResult<T>, T> getBaseModelTransformer() {
        return new ObservableTransformer<BaseResult<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseResult<T>> baseModelObservable) {
                return baseModelObservable.map(new Function<BaseResult<T>, T>() {
                    @Override
                    public T apply(BaseResult<T> result) throws Exception {
                        if (result.getCode() != ApiStatus.HTTP_SUCCESS) {
                            throw new ServerException(result.getCode(), result.getMessage().toString());
                        } else {
                            return (T) result;
                        }
                    }
                }).onErrorResumeNext(new Function<Throwable, ObservableSource<? extends T>>() {
                    @Override
                    public ObservableSource<? extends T> apply(Throwable throwable) throws Exception {
                        return Observable.<T>error(ResponseExceptionHandler.handleResponseException(throwable));
                    }
                });
            }
        };
    }

    public static <T> ObservableTransformer<T, T> getNonStandardModelTransformer() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> tObservable) {
                return tObservable.map(new Function<T, T>() {
                    @Override
                    public T apply(T t) throws Exception {
                        return t;
                    }
                }).onErrorResumeNext(new Function<Throwable, ObservableSource<? extends T>>() {
                    @Override
                    public ObservableSource<? extends T> apply(Throwable throwable) throws Exception {
                        return Observable.error(ResponseExceptionHandler.handleResponseException(throwable));
                    }
                });
            }
        };
    }
}

