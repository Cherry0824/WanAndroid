package com.rxjava.net.base;

/**
 * Created by Lenovo on 2018/12/3.
 */

public class BaseTabEntity {
    private String title;
    private int code;

    public BaseTabEntity(int code, String title) {
        this.code = code;
        this.title = title;
    }

    public BaseTabEntity(String title) {
        this.title = title;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
