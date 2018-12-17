package com.example.administrator.friendshape.model.bean;

/**
 * 作者：真理 Created by Administrator on 2018/11/20.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class GroupContentNetBean {

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

        private GroupBean group;

        public GroupBean getGroup() {
            return group;
        }

        public void setGroup(GroupBean group) {
            this.group = group;
        }

        public static class GroupBean {

            private String groupid;
            private String userid;
            private String shopid;
            private String linkman;
            private String linkphone;
            private String actiontime;
            private String people_boy;
            private String iffree_boy;
            private String people_girl;
            private String iffree_girl;
            private String money;
            private String endtime;
            private String remark;
            private String createdate;
            private String state;
            private String time_success;
            private String time_used;
            private String iftoaction;
            private String tribe_id;
            private String money_avg;
            private String people_total;
            private String photo;
            private String shopname;

            public String getGroupid() {
                return groupid;
            }

            public void setGroupid(String groupid) {
                this.groupid = groupid;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getShopid() {
                return shopid;
            }

            public void setShopid(String shopid) {
                this.shopid = shopid;
            }

            public String getLinkman() {
                return linkman;
            }

            public void setLinkman(String linkman) {
                this.linkman = linkman;
            }

            public String getLinkphone() {
                return linkphone;
            }

            public void setLinkphone(String linkphone) {
                this.linkphone = linkphone;
            }

            public String getActiontime() {
                return actiontime;
            }

            public void setActiontime(String actiontime) {
                this.actiontime = actiontime;
            }

            public String getPeople_boy() {
                return people_boy;
            }

            public void setPeople_boy(String people_boy) {
                this.people_boy = people_boy;
            }

            public String getIffree_boy() {
                return iffree_boy;
            }

            public void setIffree_boy(String iffree_boy) {
                this.iffree_boy = iffree_boy;
            }

            public String getPeople_girl() {
                return people_girl;
            }

            public void setPeople_girl(String people_girl) {
                this.people_girl = people_girl;
            }

            public String getIffree_girl() {
                return iffree_girl;
            }

            public void setIffree_girl(String iffree_girl) {
                this.iffree_girl = iffree_girl;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getEndtime() {
                return endtime;
            }

            public void setEndtime(String endtime) {
                this.endtime = endtime;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getCreatedate() {
                return createdate;
            }

            public void setCreatedate(String createdate) {
                this.createdate = createdate;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getTime_success() {
                return time_success;
            }

            public void setTime_success(String time_success) {
                this.time_success = time_success;
            }

            public String getTime_used() {
                return time_used;
            }

            public void setTime_used(String time_used) {
                this.time_used = time_used;
            }

            public String getIftoaction() {
                return iftoaction;
            }

            public void setIftoaction(String iftoaction) {
                this.iftoaction = iftoaction;
            }

            public String getTribe_id() {
                return tribe_id;
            }

            public void setTribe_id(String tribe_id) {
                this.tribe_id = tribe_id;
            }

            public String getMoney_avg() {
                return money_avg;
            }

            public void setMoney_avg(String money_avg) {
                this.money_avg = money_avg;
            }

            public String getPeople_total() {
                return people_total;
            }

            public void setPeople_total(String people_total) {
                this.people_total = people_total;
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
        }
    }
}
