package com.example.administrator.friendshape.model.db.entity;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by Administrator on 2018/3/3 0003.
 */

@Entity
public class LoginUserInfo {
    //主键自增长
    @Id(autoincrement = true)
    private Long id;
    //用户名 (设计原因，当前只需要保存userId   用户名则采取统一的命名  即 admin)
    @Unique
    private String username;
    //用户id
    private String userid;
    //用户名称
    private String userNiceName;
    //用户电话
    private String userPhoneNumber;
    //用户密码
    private String userPassWord;
    //用户性别
    private String userGender;

    public LoginUserInfo(String username, String userid, String userNiceName, String userPhoneNumber, String userPassWord, String userGender) {
        this.username = username;
        this.userid = userid;
        this.userNiceName = userNiceName;
        this.userPhoneNumber = userPhoneNumber;
        this.userPassWord = userPassWord;
        this.userGender = userGender;
    }

    @Generated(hash = 568269392)
    public LoginUserInfo(Long id, String username, String userid, String userNiceName, String userPhoneNumber, String userPassWord,
            String userGender) {
        this.id = id;
        this.username = username;
        this.userid = userid;
        this.userNiceName = userNiceName;
        this.userPhoneNumber = userPhoneNumber;
        this.userPassWord = userPassWord;
        this.userGender = userGender;
    }

    @Generated(hash = 436417725)
    public LoginUserInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserNiceName() {
        return this.userNiceName;
    }

    public void setUserNiceName(String userNiceName) {
        this.userNiceName = userNiceName;
    }

    public String getUserPhoneNumber() {
        return this.userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserPassWord() {
        return this.userPassWord;
    }

    public void setUserPassWord(String userPassWord) {
        this.userPassWord = userPassWord;
    }

    public String getUserGender() {
        return this.userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }



}
