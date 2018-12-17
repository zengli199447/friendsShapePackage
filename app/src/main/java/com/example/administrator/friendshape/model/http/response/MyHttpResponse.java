package com.example.administrator.friendshape.model.http.response;


/**
 * Created by Administrator on 2017/10/31.
 */

public class MyHttpResponse<T> {

    private int status;
    private String message;
    private T result;

    public MyHttpResponse(int status, String message, T result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "MyHttpResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
