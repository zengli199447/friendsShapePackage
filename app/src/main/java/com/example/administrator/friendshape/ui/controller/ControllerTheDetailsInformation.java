package com.example.administrator.friendshape.ui.controller;

import android.util.Log;

import com.example.administrator.friendshape.base.ControllerClassObserver;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.DynamicNetBean;
import com.example.administrator.friendshape.model.bean.UpLoadStatusNetBean;
import com.example.administrator.friendshape.model.bean.UserDetailsNetBean;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.rxtools.RxUtil;
import com.example.administrator.friendshape.widget.CommonSubscriber;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 作者：真理 Created by Administrator on 2018/11/15.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerTheDetailsInformation extends ControllerClassObserver {

    String lookUserId;

    public ControllerTheDetailsInformation(String id) {
        lookUserId = id;
    }

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
        UserDetailsNetWork();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    //获取主页详情
    private void UserDetailsNetWork() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.OTHER_INFO_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("userid_view", lookUserId);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.GetUserDetailsNetBean(map)
                .compose(RxUtil.<UserDetailsNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UserDetailsNetBean>(toastUtil) {
                    @Override
                    public void onNext(UserDetailsNetBean userDetailsNetBean) {
                        if (userDetailsNetBean.getStatus() == 1) {
                            UserDetailsNetBean.ResultBean result = userDetailsNetBean.getResult();
                            if (userDetailsNetWorkListener != null)
                                userDetailsNetWorkListener.onUserDetailsNetWorkListener(result);
                        } else {
                            toastUtil.showToast(userDetailsNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    public interface UserDetailsNetWorkListener {
        void onUserDetailsNetWorkListener(UserDetailsNetBean.ResultBean result);
    }

    private UserDetailsNetWorkListener userDetailsNetWorkListener;

    public void setUserDetailsNetWorkListener(UserDetailsNetWorkListener userDetailsNetWorkListener) {
        this.userDetailsNetWorkListener = userDetailsNetWorkListener;
    }

}
