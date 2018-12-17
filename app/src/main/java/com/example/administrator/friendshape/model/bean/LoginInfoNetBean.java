package com.example.administrator.friendshape.model.bean;

/**
 * Created by Administrator on 2017/10/31.
 * 登陆获取个人账号信息
 */

public class LoginInfoNetBean {

    private int status;
    private ResultBean result;
    private String message;
    private ImtestBean imtest;

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

    public ImtestBean getImtest() {
        return imtest;
    }

    public void setImtest(ImtestBean imtest) {
        this.imtest = imtest;
    }

    public static class ResultBean {

        private String userid;
        private String phone;
        private String pwd;
        private String secondname;
        private String sex;
        private String photo;
        private String state;
        private String province;
        private String city;
        private String createdate;
        private String weixin;
        private String qq;
        private String brithday;
        private String longitude;
        private String latitude;
        private String remark;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        public String getSecondname() {
            return secondname;
        }

        public void setSecondname(String secondname) {
            this.secondname = secondname;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }

        public String getWeixin() {
            return weixin;
        }

        public void setWeixin(String weixin) {
            this.weixin = weixin;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getBrithday() {
            return brithday;
        }

        public void setBrithday(String brithday) {
            this.brithday = brithday;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }

    public static class ImtestBean {

        private FailMsgBean fail_msg;
        private UidFailBean uid_fail;

        public FailMsgBean getFail_msg() {
            return fail_msg;
        }

        public void setFail_msg(FailMsgBean fail_msg) {
            this.fail_msg = fail_msg;
        }

        public UidFailBean getUid_fail() {
            return uid_fail;
        }

        public void setUid_fail(UidFailBean uid_fail) {
            this.uid_fail = uid_fail;
        }

        public static class FailMsgBean {

            private String string;

            public String getString() {
                return string;
            }

            public void setString(String string) {
                this.string = string;
            }
        }

        public static class UidFailBean {

            private String string;

            public String getString() {
                return string;
            }

            public void setString(String string) {
                this.string = string;
            }
        }
    }
}
