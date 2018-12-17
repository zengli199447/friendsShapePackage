package com.example.administrator.friendshape.model.bean;

/**
 * 作者：真理 Created by Administrator on 2018/11/21.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class OtherContentNetBean {

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

        public String getIntamount2() {
            return intamount2;
        }

        public void setIntamount2(String intamount2) {
            this.intamount2 = intamount2;
        }
    }
}
