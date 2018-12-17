package com.example.administrator.friendshape.model.bean;

/**
 * 作者：真理 Created by Administrator on 2018/11/21.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class VerificationCodeNetWork {

    private int status;
    private ResultBean result;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class ResultBean {

        private String smscode;

        public String getSmscode() {
            return smscode;
        }

        public void setSmscode(String smscode) {
            this.smscode = smscode;
        }
    }
}
