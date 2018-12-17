package com.example.administrator.friendshape.model.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者：真理 Created by Administrator on 2018/11/5.
 * 邮箱：229017464@qq.com
 * remark:
 */

@Entity
public class SearchDBInfo {
    //主键自增长
    @Id(autoincrement = true)
    private Long id;
    //对应用户
    private String userId;
    //搜索内容
    private String searchContent;

    public SearchDBInfo(String userId, String searchContent) {
        this.userId = userId;
        this.searchContent = searchContent;
    }

    @Generated(hash = 473920624)
    public SearchDBInfo(Long id, String userId, String searchContent) {
        this.id = id;
        this.userId = userId;
        this.searchContent = searchContent;
    }

    @Generated(hash = 1617490726)
    public SearchDBInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSearchContent() {
        return this.searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }






}
