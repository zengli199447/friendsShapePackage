package com.example.administrator.friendshape.model.bean;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/5.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class MerchantsTypeFormNetBean {

    private String message;
    private int status;
    private List<ResultBean> result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {

        private String servicecategoryid;
        private String title;

        public String getServicecategoryid() {
            return servicecategoryid;
        }

        public void setServicecategoryid(String servicecategoryid) {
            this.servicecategoryid = servicecategoryid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
