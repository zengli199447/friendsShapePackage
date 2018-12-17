package com.example.administrator.friendshape.model.bean;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/8.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class OrderaAllTypeNetBean {

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
        private List<OrderBean> order;

        public List<OrderBean> getOrder() {
            return order;
        }

        public void setOrder(List<OrderBean> order) {
            this.order = order;
        }

        public static class OrderBean {

            private String orderid;
            private String ordercode;
            private String photo;
            private String shopname;
            private String date_show;
            private String moneypay_show;
            private String moneypay_online;
            private String state;
            private String state_pay;
            private String state_group;
            private String ifcommented;
            private String ifcreater;
            private String groupid;

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

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public String getShopname() {
                return shopname;
            }

            public void setShopname(String shopname) {
                this.shopname = shopname;
            }

            public String getDate_show() {
                return date_show;
            }

            public void setDate_show(String date_show) {
                this.date_show = date_show;
            }

            public String getMoneypay_show() {
                return moneypay_show;
            }

            public void setMoneypay_show(String moneypay_show) {
                this.moneypay_show = moneypay_show;
            }

            public String getMoneypay_online() {
                return moneypay_online;
            }

            public void setMoneypay_online(String moneypay_online) {
                this.moneypay_online = moneypay_online;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getState_pay() {
                return state_pay;
            }

            public void setState_pay(String state_pay) {
                this.state_pay = state_pay;
            }

            public String getState_group() {
                return state_group;
            }

            public void setState_group(String state_group) {
                this.state_group = state_group;
            }

            public String getIfcommented() {
                return ifcommented;
            }

            public void setIfcommented(String ifcommented) {
                this.ifcommented = ifcommented;
            }

            public String getIfcreater() {
                return ifcreater;
            }

            public void setIfcreater(String ifcreater) {
                this.ifcreater = ifcreater;
            }

            public String getGroupid() {
                return groupid;
            }

            public void setGroupid(String groupid) {
                this.groupid = groupid;
            }
        }
    }
}
