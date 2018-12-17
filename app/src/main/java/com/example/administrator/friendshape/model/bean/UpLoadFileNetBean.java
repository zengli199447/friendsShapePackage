package com.example.administrator.friendshape.model.bean;

/**
 * 作者：真理 Created by Administrator on 2018/11/20.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class UpLoadFileNetBean {

    private int status;
    private ResultBean result;
    private int message;
    private String src;

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

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public static class ResultBean {

        private String src;

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }
    }

}
