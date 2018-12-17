package com.example.administrator.friendshape.model.bean;

/**
 * 作者：真理 Created by Administrator on 2018/11/13.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class RefundNetBean {

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

        private CancelinfoBean cancelinfo;

        public CancelinfoBean getCancelinfo() {
            return cancelinfo;
        }

        public void setCancelinfo(CancelinfoBean cancelinfo) {
            this.cancelinfo = cancelinfo;
        }

        public static class CancelinfoBean {

            private String orderid;
            private String moneypay_online;
            private String time_cancel;
            private String state_pay;

            public String getOrderid() {
                return orderid;
            }

            public void setOrderid(String orderid) {
                this.orderid = orderid;
            }

            public String getMoneypay_online() {
                return moneypay_online;
            }

            public void setMoneypay_online(String moneypay_online) {
                this.moneypay_online = moneypay_online;
            }

            public String getTime_cancel() {
                return time_cancel;
            }

            public void setTime_cancel(String time_cancel) {
                this.time_cancel = time_cancel;
            }

            public String getState_pay() {
                return state_pay;
            }

            public void setState_pay(String state_pay) {
                this.state_pay = state_pay;
            }
        }
    }
}
