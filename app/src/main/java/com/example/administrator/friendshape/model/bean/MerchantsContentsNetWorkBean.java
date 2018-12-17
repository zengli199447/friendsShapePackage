package com.example.administrator.friendshape.model.bean;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/6.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class MerchantsContentsNetWorkBean {

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

        private ShopBean shop;
        private List<GroupBean> group;
        private List<CommentBean> comment;

        public ShopBean getShop() {
            return shop;
        }

        public void setShop(ShopBean shop) {
            this.shop = shop;
        }

        public List<GroupBean> getGroup() {
            return group;
        }

        public void setGroup(List<GroupBean> group) {
            this.group = group;
        }

        public List<CommentBean> getComment() {
            return comment;
        }

        public void setComment(List<CommentBean> comment) {
            this.comment = comment;
        }

        public static class ShopBean {

            private String shopid;
            private String photo;
            private String img;
            private String shopname;
            private String score;
            private String money_avg;
            private String address;
            private String state;
            private String phone;
            private String openingtime;
            private String remark;
            private String distance;
            private String imgs;
            private List<String> imgarr;

            public String getShopid() {
                return shopid;
            }

            public void setShopid(String shopid) {
                this.shopid = shopid;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getShopname() {
                return shopname;
            }

            public void setShopname(String shopname) {
                this.shopname = shopname;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getMoney_avg() {
                return money_avg;
            }

            public void setMoney_avg(String money_avg) {
                this.money_avg = money_avg;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getOpeningtime() {
                return openingtime;
            }

            public void setOpeningtime(String openingtime) {
                this.openingtime = openingtime;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getImgs() {
                return imgs;
            }

            public void setImgs(String imgs) {
                this.imgs = imgs;
            }

            public List<String> getImgarr() {
                return imgarr;
            }

            public void setImgarr(List<String> imgarr) {
                this.imgarr = imgarr;
            }
        }

        public static class GroupBean {

            private String groupid;
            private String photo;
            private String secondname;
            private String endtime;
            private String need_boy;
            private String need_girl;

            public String getGroupid() {
                return groupid;
            }

            public void setGroupid(String groupid) {
                this.groupid = groupid;
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

            public String getEndtime() {
                return endtime;
            }

            public void setEndtime(String endtime) {
                this.endtime = endtime;
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
        }

        public static class CommentBean {

            private String commentid;
            private String userid;
            private String orderid;
            private String shopid;
            private String score;
            private String createdate;
            private String remark;
            private String secondname;
            private String photo;

            public String getCommentid() {
                return commentid;
            }

            public void setCommentid(String commentid) {
                this.commentid = commentid;
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

            public String getShopid() {
                return shopid;
            }

            public void setShopid(String shopid) {
                this.shopid = shopid;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getCreatedate() {
                return createdate;
            }

            public void setCreatedate(String createdate) {
                this.createdate = createdate;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
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
