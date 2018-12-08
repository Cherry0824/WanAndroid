package com.rxjava.net.http.exception;

public class ResponseException extends Exception {

    private int status;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseException(Throwable paramThrowable, int paramInt) {
        super(paramThrowable);
        this.status = paramInt;
    }
}
