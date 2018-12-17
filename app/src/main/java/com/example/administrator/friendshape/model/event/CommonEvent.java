package com.example.administrator.friendshape.model.event;


import com.example.administrator.friendshape.model.bean.BusObject;

/**
 * Created by Administrator on 2018/1/5.
 */

public class CommonEvent {

    private int code;
    private String temp_str;
    private int temp_value;
    private boolean temp_boolean;

    private BusObject busObject;

    public CommonEvent(int code) {
        this.code = code;
    }

    public CommonEvent(int code, String temp_str) {
        this.code = code;
        this.temp_str = temp_str;
    }

    public CommonEvent(int code, int temp_value) {
        this.code = code;
        this.temp_value = temp_value;
    }

    public CommonEvent(int code, boolean temp_boolean) {
        this.code = code;
        this.temp_boolean = temp_boolean;
    }

    public CommonEvent(int code, String temp_str, int temp_value) {
        this.code = code;
        this.temp_str = temp_str;
        this.temp_value = temp_value;
    }

    public CommonEvent(int code, String temp_str, int temp_value, boolean temp_boolean) {
        this.code = code;
        this.temp_str = temp_str;
        this.temp_value = temp_value;
        this.temp_boolean = temp_boolean;
    }

    public CommonEvent(int code, BusObject busObject) {
        this.code = code;
        this.busObject = busObject;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTemp_str() {
        return temp_str;
    }

    public void setTemp_str(String temp_str) {
        this.temp_str = temp_str;
    }

    public int getTemp_value() {
        return temp_value;
    }

    public void setTemp_value(int temp_value) {
        this.temp_value = temp_value;
    }

    public boolean isTemp_boolean() {
        return temp_boolean;
    }

    public void setTemp_boolean(boolean temp_boolean) {
        this.temp_boolean = temp_boolean;
    }

    public BusObject getBusObject() {
        return busObject;
    }

    public void setBusObject(BusObject busObject) {
        this.busObject = busObject;
    }

}
