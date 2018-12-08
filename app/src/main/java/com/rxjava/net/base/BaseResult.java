package com.rxjava.net.base;


import com.google.gson.annotations.SerializedName;

public class BaseResult<T> {


    @SerializedName("errorCode")
    private int code;

    @SerializedName("errorMsg")
    private Object message;

    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
