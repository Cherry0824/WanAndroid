package com.rxjava.net.comm.event;

/**
 * Created by  on 2017/5/18.
 */
public class MessageEvent<T> {
    private int messageCode;
    private String message;
    private T messageData;

    public MessageEvent(int messageCode) {
        this.messageCode = messageCode;
    }

    public MessageEvent(int messageCode, String message) {
        this.messageCode = messageCode;
        this.message = message;
    }

    public MessageEvent(int messageCode, T t) {
        this.messageCode = messageCode;
        this.messageData = t;
    }

    public MessageEvent(int messageCode, String message, T t) {
        this.messageCode = messageCode;
        this.messageData = t;
        this.message = message;
    }

    public int getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(int messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getMessageData() {
        return messageData;
    }

    public void setMessageData(T messageData) {
        this.messageData = messageData;
    }
}
