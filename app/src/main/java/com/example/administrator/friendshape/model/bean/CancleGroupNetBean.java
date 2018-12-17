package com.example.administrator.friendshape.model.bean;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/12.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class CancleGroupNetBean {

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

        private DetailBean detail;
        private List<UsersBean> users;

        public DetailBean getDetail() {
            return detail;
        }

        public void setDetail(DetailBean detail) {
            this.detail = detail;
        }

        public List<UsersBean> getUsers() {
            return users;
        }

        public void setUsers(List<UsersBean> users) {
            this.users = users;
        }

        public static class DetailBean {

            private String orderid;
            private String groupid;
            private String iffree;
            private String userid;
            private String secondname;
            private String photo;
            private String sex;
            private String age;
            private String title;
            private String state;
            private String moneypay_online;
            private String ifcreater;

            public String getOrderid() {
                return orderid;
            }

            public void setOrderid(String orderid) {
                this.orderid = orderid;
            }

            public String getGroupid() {
                return groupid;
            }

            public void setGroupid(String groupid) {
                this.groupid = groupid;
            }

            public String getIffree() {
                return iffree;
            }

            public void setIffree(String iffree) {
                this.iffree = iffree;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getSecondname() {
                return secondname;
            }

            public void setSecondname(String secondname) {
                this.secondname = secondname;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getMoneypay_online() {
                return moneypay_online;
            }

            public void setMoneypay_online(String moneypay_online) {
                this.moneypay_online = moneypay_online;
            }

            public String getIfcreater() {
                return ifcreater;
            }

            public void setIfcreater(String ifcreater) {
                this.ifcreater = ifcreater;
            }
        }

        public static class UsersBean {

            private String userid;
            private String orderid;
            private String secondname;
            private String photo;
            private boolean status;

            public boolean isStatus() {
                return status;
            }

            public void setStatus(boolean status) {
                this.status = status;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getOrderid() {
                return orderid;
            }

            public void setOrderid(String orderid) {
                this.orderid = orderid;
            }

            public String getSecondname() {
                return secondname;
            }

            public void setSecondname(String secondname) {
                this.secondname = secondname;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }
        }
    }
}
