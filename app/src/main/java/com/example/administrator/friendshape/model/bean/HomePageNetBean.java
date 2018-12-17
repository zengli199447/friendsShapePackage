package com.example.administrator.friendshape.model.bean;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/1.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class HomePageNetBean {

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

        private String message;
        private NewversionBean newversion;
        private String RootPath;
        private List<BannerBean> banner;
        private List<CategoryBean> category;
        private List<NoticeBean> notice;
        private List<ShopTopBean> shop_top;
        private List<ShopsBean> shops;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public NewversionBean getNewversion() {
            return newversion;
        }

        public void setNewversion(NewversionBean newversion) {
            this.newversion = newversion;
        }

        public String getRootPath() {
            return RootPath;
        }

        public void setRootPath(String RootPath) {
            this.RootPath = RootPath;
        }

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<CategoryBean> getCategory() {
            return category;
        }

        public void setCategory(List<CategoryBean> category) {
            this.category = category;
        }

        public List<NoticeBean> getNotice() {
            return notice;
        }

        public void setNotice(List<NoticeBean> notice) {
            this.notice = notice;
        }

        public List<ShopTopBean> getShop_top() {
            return shop_top;
        }

        public void setShop_top(List<ShopTopBean> shop_top) {
            this.shop_top = shop_top;
        }

        public List<ShopsBean> getShops() {
            return shops;
        }

        public void setShops(List<ShopsBean> shops) {
            this.shops = shops;
        }

        public static class NewversionBean {

            private String txtinfoid;
            private String content;
            private String txtinfotype;
            private String versionvalue;
            private String versiontxt;
            private String downsite;

            public String getTxtinfoid() {
                return txtinfoid;
            }

            public void setTxtinfoid(String txtinfoid) {
                this.txtinfoid = txtinfoid;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getTxtinfotype() {
                return txtinfotype;
            }

            public void setTxtinfotype(String txtinfotype) {
                this.txtinfotype = txtinfotype;
            }

            public String getVersionvalue() {
                return versionvalue;
            }

            public void setVersionvalue(String versionvalue) {
                this.versionvalue = versionvalue;
            }

            public String getVersiontxt() {
                return versiontxt;
            }

            public void setVersiontxt(String versiontxt) {
                this.versiontxt = versiontxt;
            }

            public String getDownsite() {
                return downsite;
            }

            public void setDownsite(String downsite) {
                this.downsite = downsite;
            }
        }

        public static class BannerBean {

            private String textInfoid;
            private String title;
            private String jianjie;
            private String photo;
            private String content;
            private String infotype;
            private String createdate;
            private String numamount;
            private String intamount;
            private String intamount2;


            public String getIntamount2() {
                return intamount2;
            }

            public void setIntamount2(String intamount2) {
                this.intamount2 = intamount2;
            }

            public String getTextInfoid() {
                return textInfoid;
            }

            public void setTextInfoid(String textInfoid) {
                this.textInfoid = textInfoid;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getJianjie() {
                return jianjie;
            }

            public void setJianjie(String jianjie) {
                this.jianjie = jianjie;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getInfotype() {
                return infotype;
            }

            public void setInfotype(String infotype) {
                this.infotype = infotype;
            }

            public String getCreatedate() {
                return createdate;
            }

            public void setCreatedate(String createdate) {
                this.createdate = createdate;
            }

            public String getNumamount() {
                return numamount;
            }

            public void setNumamount(String numamount) {
                this.numamount = numamount;
            }

            public String getIntamount() {
                return intamount;
            }

            public void setIntamount(String intamount) {
                this.intamount = intamount;
            }
        }

        public static class CategoryBean {

            private String servicecategoryid;
            private String title;
            private String img;

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

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }

        public static class NoticeBean {

            private String textinfoid;
            private String title;

            public String getTextinfoid() {
                return textinfoid;
            }

            public void setTextinfoid(String textinfoid) {
                this.textinfoid = textinfoid;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        public static class ShopTopBean {

            private String shopgroupid;
            private String img;

            public String getShopgroupid() {
                return shopgroupid;
            }

            public void setShopgroupid(String shopgroupid) {
                this.shopgroupid = shopgroupid;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
    }

    public static class ShopsBean{
        String address;
        String distance;
        String money_avg;
        String photo;
        String remark;
        String score;
        String shopid;
        String shopname;

        public ShopsBean(String address, String distance, String money_avg, String photo, String remark, String score, String shopid, String shopname) {
            this.address = address;
            this.distance = distance;
            this.money_avg = money_avg;
            this.photo = photo;
            this.remark = remark;
            this.score = score;
            this.shopid = shopid;
            this.shopname = shopname;
        }

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
