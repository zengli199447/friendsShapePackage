package com.example.administrator.friendshape.model.bean;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/10.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class OredeContentNetBean {

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

        private GroupBean group;
        private int ifcanjoin;
        private OrderBean order;
        private List<UserBean> user;

        public GroupBean getGroup() {
            return group;
        }

        public void setGroup(GroupBean group) {
            this.group = group;
        }

        public int getIfcanjoin() {
            return ifcanjoin;
        }

        public void setIfcanjoin(int ifcanjoin) {
            this.ifcanjoin = ifcanjoin;
        }

        public OrderBean getOrder() {
            return order;
        }

        public void setOrder(OrderBean order) {
            this.order = order;
        }

        public List<UserBean> getUser() {
            return user;
        }

        public void setUser(List<UserBean> user) {
            this.user = user;
        }

        public static class GroupBean {
            String groupid;
            String userid;
            String shopid;
            String money_avg;
            String linkman;
            String linkphone;
            String actiontime;
            String people_boy;
            String iffree_boy;
            String people_girl;
            String iffree_girl;
            String money;
            String endtime;
            String remark;
            String createdate;
            String state;
            String time_success;
            String time_used;
            String iftoaction;
            String tribe_id;
            String secondname;
            String phone;
            String photo;
            String sex;
            String brithday;
            String amount_boy;
            String amount_girl;
            String amount_total;
            String money_total;
            String age;
            String people_total_need;
            String boy_need;
            String girl_need;
            String money_need;
            String shopname;
            String shopphoto;
            String address;
            String longitude;
            String latitude;
            String shopphone;

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

            public String getMoney_avg() {
                return money_avg;
            }

            public void setMoney_avg(String money_avg) {
                this.money_avg = money_avg;
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

            public String getSecondname() {
                return secondname;
            }

            public void setSecondname(String secondname) {
                this.secondname = secondname;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
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

            public String getBrithday() {
                return brithday;
            }

            public void setBrithday(String brithday) {
                this.brithday = brithday;
            }

            public String getAmount_boy() {
                return amount_boy;
            }

            public void setAmount_boy(String amount_boy) {
                this.amount_boy = amount_boy;
            }

            public String getAmount_girl() {
                return amount_girl;
            }

            public void setAmount_girl(String amount_girl) {
                this.amount_girl = amount_girl;
            }

            public String getAmount_total() {
                return amount_total;
            }

            public void setAmount_total(String amount_total) {
                this.amount_total = amount_total;
            }

            public String getMoney_total() {
                return money_total;
            }

            public void setMoney_total(String money_total) {
                this.money_total = money_total;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getPeople_total_need() {
                return people_total_need;
            }

            public void setPeople_total_need(String people_total_need) {
                this.people_total_need = people_total_need;
            }

            public String getBoy_need() {
                return boy_need;
            }

            public void setBoy_need(String boy_need) {
                this.boy_need = boy_need;
            }

            public String getGirl_need() {
                return girl_need;
            }

            public void setGirl_need(String girl_need) {
                this.girl_need = girl_need;
            }

            public String getMoney_need() {
                return money_need;
            }

            public void setMoney_need(String money_need) {
                this.money_need = money_need;
            }

            public String getShopname() {
                return shopname;
            }

            public void setShopname(String shopname) {
                this.shopname = shopname;
            }

            public String getShopphoto() {
                return shopphoto;
            }

            public void setShopphoto(String shopphoto) {
                this.shopphoto = shopphoto;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
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

            public String getShopphone() {
                return shopphone;
            }

            public void setShopphone(String shopphone) {
                this.shopphone = shopphone;
            }
        }

        public static class UserBean{
            String userid;
            String secondname;
            String photo;

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
        }

        public static class OrderBean {
            private String createdate;
            private String groupid;
            private String ifcommented;
            private String ifcreater;
            private String iffree;
            private String money_back;
            private String moneypay_online;
            private String ordercode;
            private String orderid;
            private String paydate;
            private String paytype;
            private String sex;
            private String state;
            private String state_pay;
            private String time_cancel;
            private String time_money_back;
            private String userid;
            private String zhifunumber;

            public String getCreatedate() {
                return createdate;
            }

            public void setCreatedate(String createdate) {
                this.createdate = createdate;
            }

            public String getGroupid() {
                return groupid;
            }

            public void setGroupid(String groupid) {
                this.groupid = groupid;
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

            public String getIffree() {
                return iffree;
            }

            public void setIffree(String iffree) {
                this.iffree = iffree;
            }

            public String getMoney_back() {
                return money_back;
            }

            public void setMoney_back(String money_back) {
                this.money_back = money_back;
            }

            public String getMoneypay_online() {
                return moneypay_online;
            }

            public void setMoneypay_online(String moneypay_online) {
                this.moneypay_online = moneypay_online;
            }

            public String getOrdercode() {
                return ordercode;
            }

            public void setOrdercode(String ordercode) {
                this.ordercode = ordercode;
            }

            public String getOrderid() {
                return orderid;
            }

            public void setOrderid(String orderid) {
                this.orderid = orderid;
            }

            public String getPaydate() {
                return paydate;
            }

            public void setPaydate(String paydate) {
                this.paydate = paydate;
            }

            public String getPaytype() {
                return paytype;
            }

            public void setPaytype(String paytype) {
                this.paytype = paytype;
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

            public String getState_pay() {
                return state_pay;
            }

            public void setState_pay(String state_pay) {
                this.state_pay = state_pay;
            }

            public String getTime_cancel() {
                return time_cancel;
            }

            public void setTime_cancel(String time_cancel) {
                this.time_cancel = time_cancel;
            }

            public String getTime_money_back() {
                return time_money_back;
            }

            public void setTime_money_back(String time_money_back) {
                this.time_money_back = time_money_back;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getZhifunumber() {
                return zhifunumber;
            }

            public void setZhifunumber(String zhifunumber) {
                this.zhifunumber = zhifunumber;
            }
        }
    }
}
