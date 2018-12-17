package com.example.administrator.friendshape.model.bean;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/13.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class DynamicNetBean {

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
        private List<NewsBean> news;

        public List<NewsBean> getNews() {
            return news;
        }

        public void setNews(List<NewsBean> news) {
            this.news = news;
        }

        public static class NewsBean {

            private String newsid;
            private String userid;
            private String groupid;
            private String content;
            private String imgs;
            private String state;
            private String createdate;
            private String photo;
            private String secondname;
            private String distance;
            private String zan_total;
            private int ifzan_cleck;
            private List<String> imgarr;
            private List<ReplyBean> reply;
            private boolean supportStatus;

            public boolean isSupportStatus() {
                return supportStatus;
            }

            public void setSupportStatus(boolean supportStatus) {
                this.supportStatus = supportStatus;
            }

            public String getNewsid() {
                return newsid;
            }

            public void setNewsid(String newsid) {
                this.newsid = newsid;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getGroupid() {
                return groupid;
            }

            public void setGroupid(String groupid) {
                this.groupid = groupid;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getImgs() {
                return imgs;
            }

            public void setImgs(String imgs) {
                this.imgs = imgs;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getCreatedate() {
                return createdate;
            }

            public void setCreatedate(String createdate) {
                this.createdate = createdate;
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

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getZan_total() {
                return zan_total;
            }

            public void setZan_total(String zan_total) {
                this.zan_total = zan_total;
            }

            public int getIfzan_cleck() {
                return ifzan_cleck;
            }

            public void setIfzan_cleck(int ifzan_cleck) {
                this.ifzan_cleck = ifzan_cleck;
            }

            public List<String> getImgarr() {
                return imgarr;
            }

            public void setImgarr(List<String> imgarr) {
                this.imgarr = imgarr;
            }

            public List<ReplyBean> getReply() {
                return reply;
            }

            public void setReply(List<ReplyBean> reply) {
                this.reply = reply;
            }

            public static class ReplyBean {

                private String text;
                private FromUserBean fromUser;
                private ToUserBean toUser;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public FromUserBean getFromUser() {
                    return fromUser;
                }

                public void setFromUser(FromUserBean fromUser) {
                    this.fromUser = fromUser;
                }

                public ToUserBean getToUser() {
                    return toUser;
                }

                public void setToUser(ToUserBean toUser) {
                    this.toUser = toUser;
                }

                public static class FromUserBean {

                    private String id;
                    private String name;

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }
                }

                public static class ToUserBean {

                    private String id;
                    private String name;

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }
                }
            }
        }
    }
}
