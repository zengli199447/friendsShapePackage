package com.example.administrator.friendshape.model.bean;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/5.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class MerchantsNetBean {

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
        private List<ShopsBean> shops;

        public List<ShopsBean> getShops() {
            return shops;
        }

        public void setShops(List<ShopsBean> shops) {
            this.shops = shops;
        }

        public static class ShopsBean {
            /**
             * address : 光谷意大利风情街教堂北外街1号楼1楼F1117一楼门面
             * distance : 499
             * money_avg : 0
             * photo : ./upload/2018/10/23/d2dd40a4d0b1984c16697b3c693263e7.jpg
             * remark :
             * score : 0
             * shopid : 85
             * shopname : 荷尔蒙KTV音乐清吧
             */

            private String address;
            private String distance;
            private String money_avg;
            private String photo;
            private String remark;
            private String score;
            private String shopid;
            private String shopname;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getMoney_avg() {
                return money_avg;
            }

            public void setMoney_avg(String money_avg) {
                this.money_avg = money_avg;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getShopid() {
                return shopid;
            }

            public void setShopid(String shopid) {
                this.shopid = shopid;
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
