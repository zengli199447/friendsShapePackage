package com.example.administrator.friendshape.model.bean;

/**
 * 作者：真理 Created by Administrator on 2018/11/22.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ZfbPayContentNetBean {

    private int status;
    private String result;
    private String message;
    private PrepayinfoBean prepayinfo;
    private OrderdataBean orderdata;

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

    public PrepayinfoBean getPrepayinfo() {
        return prepayinfo;
    }

    public void setPrepayinfo(PrepayinfoBean prepayinfo) {
        this.prepayinfo = prepayinfo;
    }

    public OrderdataBean getOrderdata() {
        return orderdata;
    }

    public void setOrderdata(OrderdataBean orderdata) {
        this.orderdata = orderdata;
    }

    public static class PrepayinfoBean {

        private String totalmoney;
        private String OrderCode;
        private String partner;
        private String seller;
        private String private_key;
        private String url_notify;

        public String getTotalmoney() {
            return totalmoney;
        }

        public void setTotalmoney(String totalmoney) {
            this.totalmoney = totalmoney;
        }

        public String getOrderCode() {
            return OrderCode;
        }

        public void setOrderCode(String OrderCode) {
            this.OrderCode = OrderCode;
        }

        public String getPartner() {
            return partner;
        }

        public void setPartner(String partner) {
            this.partner = partner;
        }

        public String getSeller() {
            return seller;
        }

        public void setSeller(String seller) {
            this.seller = seller;
        }

        public String getPrivate_key() {
            return private_key;
        }

        public void setPrivate_key(String private_key) {
            this.private_key = private_key;
        }

        public String getUrl_notify() {
            return url_notify;
        }

        public void setUrl_notify(String url_notify) {
            this.url_notify = url_notify;
        }
    }

    public static class OrderdataBean {

        private String orderid;
        private String userid;
        private String groupid;
        private String ordercode;
        private String paytype;
        private String paydate;
        private String zhifunumber;
        private String moneypay_online;
        private String createdate;
        private String iffree;
        private String ifcreater;
        private String state_pay;
        private String state;
        private String ifcommented;
        private String money_back;
        private String time_money_back;
        private String time_cancel;
        private String sex;

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getGroupid() {
            return groupid;
        }

        public void setGroupid(String groupid) {
            this.groupid = groupid;
        }

        public String getOrdercode() {
            return ordercode;
        }

        public void setOrdercode(String ordercode) {
            this.ordercode = ordercode;
        }

        public String getPaytype() {
            return paytype;
        }

        public void setPaytype(String paytype) {
            this.paytype = paytype;
        }

        public String getPaydate() {
            return paydate;
        }

        public void setPaydate(String paydate) {
            this.paydate = paydate;
        }

        public String getZhifunumber() {
            return zhifunumber;
        }

        public void setZhifunumber(String zhifunumber) {
            this.zhifunumber = zhifunumber;
        }

        public String getMoneypay_online() {
            return moneypay_online;
        }

        public void setMoneypay_online(String moneypay_online) {
            this.moneypay_online = moneypay_online;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }

        public String getIffree() {
            return iffree;
        }

        public void setIffree(String iffree) {
            this.iffree = iffree;
        }

        public String getIfcreater() {
            return ifcreater;
        }

        public void setIfcreater(String ifcreater) {
            this.ifcreater = ifcreater;
        }

        public String getState_pay() {
            return state_pay;
        }

        public void setState_pay(String state_pay) {
            this.state_pay = state_pay;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getIfcommented() {
            return ifcommented;
        }

        public void setIfcommented(String ifcommented) {
            this.ifcommented = ifcommented;
        }

        public String getMoney_back() {
            return money_back;
        }

        public void setMoney_back(String money_back) {
            this.money_back = money_back;
        }

        public String getTime_money_back() {
            return time_money_back;
        }

        public void setTime_money_back(String time_money_back) {
            this.time_money_back = time_money_back;
        }

        public String getTime_cancel() {
            return time_cancel;
        }

        public void setTime_cancel(String time_cancel) {
            this.time_cancel = time_cancel;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }
    }
}
