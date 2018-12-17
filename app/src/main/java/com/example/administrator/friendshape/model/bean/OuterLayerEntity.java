package com.example.administrator.friendshape.model.bean;


import java.util.List;

/**
 * Created by Administrator on 2018/2/12.
 */

public class OuterLayerEntity {

    String message;
    int status;

    public Result result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result {
        String RootPath;
        String ifhavemessage;
        boolean newversion;
        int totaluser_online;
        int totaluser_regist;

        public String getRootPath() {
            return RootPath;
        }

        public void setRootPath(String rootPath) {
            RootPath = rootPath;
        }

        public String getIfhavemessage() {
            return ifhavemessage;
        }

        public void setIfhavemessage(String ifhavemessage) {
            this.ifhavemessage = ifhavemessage;
        }

        public boolean isNewversion() {
            return newversion;
        }

        public void setNewversion(boolean newversion) {
            this.newversion = newversion;
        }

        public int getTotaluser_online() {
            return totaluser_online;
        }

        public void setTotaluser_online(int totaluser_online) {
            this.totaluser_online = totaluser_online;
        }

        public int getTotaluser_regist() {
            return totaluser_regist;
        }

        public void setTotaluser_regist(int totaluser_regist) {
            this.totaluser_regist = totaluser_regist;
        }

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<newsBean> getNews() {
            return news;
        }

        public void setNews(List<newsBean> news) {
            this.news = news;
        }

        private List<BannerBean> banner;
        private List<newsBean> news;

        public class BannerBean {
            String content;
            int infotype;
            String intamount;
            String jianjie;
            String numamount;
            String photo;
            int textInfoid;
            String title;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getInfotype() {
                return infotype;
            }

            public void setInfotype(int infotype) {
                this.infotype = infotype;
            }

            public String getIntamount() {
                return intamount;
            }

            public void setIntamount(String intamount) {
                this.intamount = intamount;
            }

            public String getJianjie() {
                return jianjie;
            }

            public void setJianjie(String jianjie) {
                this.jianjie = jianjie;
            }

            public String getNumamount() {
                return numamount;
            }

            public void setNumamount(String numamount) {
                this.numamount = numamount;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public int getTextInfoid() {
                return textInfoid;
            }

            public void setTextInfoid(int textInfoid) {
                this.textInfoid = textInfoid;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        public class newsBean {
            int amount_read;
            int amount_share;
            String createdate;
            int ifcanmoney;
            String listimg;
            int newsid;
            int newstype;
            int starbean;
            String title;
            int viewtype;

            public int getAmount_read() {
                return amount_read;
            }

            public void setAmount_read(int amount_read) {
                this.amount_read = amount_read;
            }

            public int getAmount_share() {
                return amount_share;
            }

            public void setAmount_share(int amount_share) {
                this.amount_share = amount_share;
            }

            public String getCreatedate() {
                return createdate;
            }

            public void setCreatedate(String createdate) {
                this.createdate = createdate;
            }

            public int getIfcanmoney() {
                return ifcanmoney;
            }

            public void setIfcanmoney(int ifcanmoney) {
                this.ifcanmoney = ifcanmoney;
            }

            public String getListimg() {
                return listimg;
            }

            public void setListimg(String listimg) {
                this.listimg = listimg;
            }

            public int getNewsid() {
                return newsid;
            }

            public void setNewsid(int newsid) {
                this.newsid = newsid;
            }

            public int getNewstype() {
                return newstype;
            }

            public void setNewstype(int newstype) {
                this.newstype = newstype;
            }

            public int getStarbean() {
                return starbean;
            }

            public void setStarbean(int starbean) {
                this.starbean = starbean;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getViewtype() {
                return viewtype;
            }

            public void setViewtype(int viewtype) {
                this.viewtype = viewtype;
            }
        }

    }

}
