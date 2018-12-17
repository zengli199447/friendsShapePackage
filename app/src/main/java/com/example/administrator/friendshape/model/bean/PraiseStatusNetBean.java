package com.example.administrator.friendshape.model.bean;

/**
 * Created by Administrator on 2018/9/11.
 * 提交状态
 */

public class PraiseStatusNetBean {

    private String message;
    private Object result;
    private int status;
    private int ifzan_cleck;

    public int getIfzan_cleck() {
        return ifzan_cleck;
    }

    public void setIfzan_cleck(int ifzan_cleck) {
        this.ifzan_cleck = ifzan_cleck;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
