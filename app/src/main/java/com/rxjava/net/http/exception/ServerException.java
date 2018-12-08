package com.rxjava.net.http.exception;



public class ServerException extends RuntimeException {

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

    public ServerException(int status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }
}
