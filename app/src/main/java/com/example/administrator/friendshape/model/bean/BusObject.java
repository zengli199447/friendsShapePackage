package com.example.administrator.friendshape.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/8/22.
 * BUS 信息载体
 */

public class BusObject {

    int type;
    String ValueF;
    String ValueL;
    List<Object> list;

    public BusObject(String valueF, String valueL, List<Object> list, int type) {
        ValueF = valueF;
        ValueL = valueL;
        this.list = list;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValueF() {
        return ValueF;
    }

    public void setValueF(String valueF) {
        ValueF = valueF;
    }

    public String getValueL() {
        return ValueL;
    }

    public void setValueL(String valueL) {
        ValueL = valueL;
    }

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }
}
