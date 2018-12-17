package com.example.administrator.friendshape.ui.controller;

import android.util.Log;

import com.example.administrator.friendshape.base.ControllerClassObserver;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.LoginInfoNetBean;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.rxtools.RxUtil;
import com.example.administrator.friendshape.widget.CommonSubscriber;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 作者：真理 Created by Administrator on 2018/12/8.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerBindAndModify extends ControllerClassObserver {

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getControllerComponent().inject(this);
    }

    @Override
    protected void onClassCreate() {
        super.onClassCreate();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    //修改密码
    public void ModifyPassWord(String phoneNumber, String Password) {
        HashMap registeredMap = new HashMap<>();
        LinkedHashMap registeredLinkedHashMap = new LinkedHashMap();
        registeredLinkedHashMap.put("action", DataClass.USER_REGISTER_SET);
        registeredLinkedHashMap.put("phone", phoneNumber);
        registeredLinkedHashMap.put("pwd", Password);
        registeredMap.put("version", "v1");
        registeredMap.put("vars", new Gson().toJson(registeredLinkedHashMap));
        addSubscribe(dataManager.fetchLogin(registeredMap)
                .compose(RxUtil.<LoginInfoNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<LoginInfoNetBean>(toastUtil) {
                    @Override
                    public void onNext(LoginInfoNetBean loginInfoNetBean) {
                        if (loginInfoNetBean.getStatus() == 1) {
                            LoginInfoNetBean.ResultBean result = loginInfoNetBean.getResult();
                            if (modifyPassWordListener != null) {
                                modifyPassWordListener.onModifyPassWordListener();
                            }
                        } else {
                            toastUtil.showToast(loginInfoNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));

    }

    public interface ModifyPassWordListener {
        void onModifyPassWordListener();
    }

    private ModifyPassWordListener modifyPassWordListener;

    public void setModifyPassWordListener(ModifyPassWordListener modifyPassWordListener) {
        this.modifyPassWordListener = modifyPassWordListener;
    }


}
