package com.example.administrator.friendshape.model.bean;

/**
 * 作者：真理 Created by Administrator on 2018/11/24.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ImUserContentNetBean {

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
        private String secondname;
        private int iffriend;

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

        public String getSecondname() {
            return secondname;
        }

        public void setSecondname(String secondname) {
            this.secondname = secondname;
        }

        public int getIffriend() {
            return iffriend;
        }

        public void setIffriend(int iffriend) {
            this.iffriend = iffriend;
        }
    }
}
