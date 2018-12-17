package com.example.administrator.friendshape.model.db.entity;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by Administrator on 2018/3/3 0003.
 * APP基础信息
 */

@Entity
public class AppDBInfo {
    //主键自增长
    @Id(autoincrement = true)
    private Long id;
    //登陆状态
    private String loginStatus;
    //是否第一次登陆
    private boolean isFristLogin;

    public AppDBInfo(String loginStatus, boolean isFristLogin) {
        this.loginStatus = loginStatus;
        this.isFristLogin = isFristLogin;
    }

    @Generated(hash = 1908467881)
    public AppDBInfo(Long id, String loginStatus, boolean isFristLogin) {
        this.id = id;
        this.loginStatus = loginStatus;
        this.isFristLogin = isFristLogin;
    }

    @Generated(hash = 1808839215)
    public AppDBInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginStatus() {
        return this.loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }

    public boolean getIsFristLogin() {
        return this.isFristLogin;
    }

    public void setIsFristLogin(boolean isFristLogin) {
        this.isFristLogin = isFristLogin;
    }



}
