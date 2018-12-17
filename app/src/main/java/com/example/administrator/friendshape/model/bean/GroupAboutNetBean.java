package com.example.administrator.friendshape.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/15.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class GroupAboutNetBean implements Serializable {

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

    public static class ResultBean implements Serializable{
        private List<GroupBean> group;
        private List<ServicecategoryBean> servicecategory;

        public List<GroupBean> getGroup() {
            return group;
        }

        public void setGroup(List<GroupBean> group) {
            this.group = group;
        }

        public List<ServicecategoryBean> getServicecategory() {
            return servicecategory;
        }

        public void setServicecategory(List<ServicecategoryBean> servicecategory) {
            this.servicecategory = servicecategory;
        }

        public static class GroupBean implements Serializable{

            private String age;
            private String createdate;
            private String distance;
            private String endtime;
            private String groupid;
            private String ifcanjoin;
            private String iffree_boy;
            private String iffree_girl;
            private String money;
            private String money_avg;
            private String money_total;
            private String need_boy;
            private String need_girl;
            private String photo;
            private String secondname;
            private String sex;
            private String state;
            private String title;
            private String tribe_id;
            private String userid;

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getCreatedate() {
                return createdate;
            }

            public void setCreatedate(String createdate) {
                this.createdate = createdate;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getEndtime() {
                return endtime;
            }

            public void setEndtime(String endtime) {
                this.endtime = endtime;
            }

            public String getGroupid() {
                return groupid;
            }

            public void setGroupid(String groupid) {
                this.groupid = groupid;
            }

            public String getIfcanjoin() {
                return ifcanjoin;
            }

            public void setIfcanjoin(String ifcanjoin) {
                this.ifcanjoin = ifcanjoin;
            }

            public String getIffree_boy() {
                return iffree_boy;
            }

            public void setIffree_boy(String iffree_boy) {
                this.iffree_boy = iffree_boy;
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

            public String getMoney_avg() {
                return money_avg;
            }

            public void setMoney_avg(String money_avg) {
                this.money_avg = money_avg;
            }

            public String getMoney_total() {
                return money_total;
            }

            public void setMoney_total(String money_total) {
                this.money_total = money_total;
            }

            public String getNeed_boy() {
                return need_boy;
            }

            public void setNeed_boy(String need_boy) {
                this.need_boy = need_boy;
            }

            public String getNeed_girl() {
                return need_girl;
            }

            public void setNeed_girl(String need_girl) {
                this.need_girl = need_girl;
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

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTribe_id() {
                return tribe_id;
            }

            public void setTribe_id(String tribe_id) {
                this.tribe_id = tribe_id;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }
        }

        public static class ServicecategoryBean implements Serializable{

            private String servicecategoryid;
            private String title;

            public ServicecategoryBean(String servicecategoryid, String title) {
                this.servicecategoryid = servicecategoryid;
                this.title = title;
            }

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
}
