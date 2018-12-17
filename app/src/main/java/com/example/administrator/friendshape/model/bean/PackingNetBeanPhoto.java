package com.example.administrator.friendshape.model.bean;

/**
 * 作者：真理 Created by Administrator on 2018/11/13.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class PackingNetBeanPhoto {

    String photo;
    int position;

    public PackingNetBeanPhoto(String photo, int position) {
        this.photo = photo;
        this.position = position;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int phone) {
        this.position = phone;
    }

}
