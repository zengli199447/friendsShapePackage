package com.example.administrator.friendshape.model.bean;

/**
 * 作者：真理 Created by Administrator on 2018/11/15.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class UserDetailsNetBean {

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

        private String userid;
        private String photo;
        private String sex;
        private String secondname;
        private String age;
        private String remark;
        private String iffriend;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
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

        public String getSecondname() {
            return secondname;
        }

        public void setSecondname(String secondname) {
            this.secondname = secondname;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getIffriend() {
            return iffriend;
        }

        public void setIffriend(String iffriend) {
            this.iffriend = iffriend;
        }
    }
}
