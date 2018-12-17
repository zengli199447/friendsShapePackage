package com.example.administrator.friendshape.model.bean;

/**
 * 作者：真理 Created by Administrator on 2018/11/8.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class GroupOrderNetBean {

    private int status;
    private String result;
    private String message;
    private String needpay;
    private String orderid;
    private String ordercode;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNeedpay() {
        return needpay;
    }

    public void setNeedpay(String needpay) {
        this.needpay = needpay;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getOrdercode() {
        return ordercode;
    }

    public void setOrdercode(String ordercode) {
        this.ordercode = ordercode;
    }
}
