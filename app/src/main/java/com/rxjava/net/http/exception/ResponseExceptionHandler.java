package com.rxjava.net.http.exception;

import com.rxjava.net.comm.api.ApiStatus;

import retrofit2.HttpException;

public class ResponseExceptionHandler {

    public static ResponseException handleResponseException(Throwable throwable) {
        ResponseException responseException = null;
        if (throwable instanceof HttpException) {
            responseException = new ResponseException(throwable, ((HttpException) throwable).code());
            responseException.setMessage(((HttpException) throwable).message());
            return responseException;
        } else if (throwable instanceof ServerException) {
            if (((ServerException) throwable).getStatus() == ApiStatus.HTTP_TOKEN) {  //token失效
                responseException = new ResponseException(throwable, ApiStatus.HTTP_TOKEN);
            } else if (((ServerException) throwable).getStatus() != ApiStatus.HTTP_SUCCESS) {
                responseException = new ResponseException(throwable, ((ServerException) throwable).getStatus());
                responseException.setMessage(((ServerException) throwable).getMessage());
            }
            return responseException;//
        } else {
            responseException = new ResponseException(throwable, ApiStatus.HTTP_ERROR);
            responseException.setMessage(throwable.getMessage());
            return responseException;
        }
    }
}
