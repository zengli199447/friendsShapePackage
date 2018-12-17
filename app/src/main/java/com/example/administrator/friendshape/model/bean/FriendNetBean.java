package com.example.administrator.friendshape.model.bean;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/12.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class FriendNetBean {

    private String message;
    private ResultBean result;
    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class ResultBean {
        private List<FriendBean> friend;

        public List<FriendBean> getFriend() {
            return friend;
        }

        public void setFriend(List<FriendBean> friend) {
            this.friend = friend;
        }

        public static class FriendBean {

            private String age;
            private String friendid;
            private String photo;
            private String secondname;
            private String sex;
            private String userid;
            private String userid_friend;
            private boolean select;

            public boolean isSelect() {
                return select;
            }

            public void setSelect(boolean select) {
                this.select = select;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getFriendid() {
                return friendid;
            }

            public void setFriendid(String friendid) {
                this.friendid = friendid;
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

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getUserid_friend() {
                return userid_friend;
            }

            public void setUserid_friend(String userid_friend) {
                this.userid_friend = userid_friend;
            }
        }
    }
}
