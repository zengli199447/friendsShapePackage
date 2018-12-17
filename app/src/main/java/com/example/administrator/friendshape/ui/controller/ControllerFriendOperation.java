package com.example.administrator.friendshape.ui.controller;

import android.util.Log;

import com.example.administrator.friendshape.base.ControllerClassObserver;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.ImUserContentNetBean;
import com.example.administrator.friendshape.model.bean.UpLoadStatusNetBean;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.rxtools.RxUtil;
import com.example.administrator.friendshape.widget.CommonSubscriber;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 作者：真理 Created by Administrator on 2018/11/24.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerFriendOperation extends ControllerClassObserver {

    String friendId;
    private int flags;

    public ControllerFriendOperation(String friendId, int flags) {
        this.friendId = friendId;
        this.flags = flags;
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
        switch (flags) {
            case 0:
                UserContentNetWork();
                break;
            case 1:

                break;
        }
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    //查询用户信息
    private void UserContentNetWork() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.USER_IMINFO_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("friendid", friendId);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.GetImUserContentNetBean(map)
                .compose(RxUtil.<ImUserContentNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<ImUserContentNetBean>(toastUtil) {
                    @Override
                    public void onNext(ImUserContentNetBean imUserContentNetBean) {
                        if (imUserContentNetBean.getStatus() == 1) {
                            if (netWorkListener != null)
                                netWorkListener.onUserContentNetWorkListener(imUserContentNetBean.getResult());
                        } else {
                            toastUtil.showToast(imUserContentNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    //好友申请操作
    public void FriendRequests(int state) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.FRIEND_CHECK_SET);
        linkedHashMap.put("friendid", friendId);
        linkedHashMap.put("state", state);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.UpLoadStatusNetBean(map)
                .compose(RxUtil.<UpLoadStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UpLoadStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(UpLoadStatusNetBean upLoadStatusNetBean) {
                        if (upLoadStatusNetBean.getStatus() == 1) {
                            if (netWorkListener != null)
                                netWorkListener.onConfirmTheOperationNetWorkListener(upLoadStatusNetBean.getMessage());
                        } else {
                            toastUtil.showToast(upLoadStatusNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    //申请好友
    public void ToApplyForAFriend(String about) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.FRIEND_ADD_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("userid_to", friendId);
        linkedHashMap.put("remark", about);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.UpLoadStatusNetBean(map)
                .compose(RxUtil.<UpLoadStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UpLoadStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(UpLoadStatusNetBean upLoadStatusNetBean) {
                        if (upLoadStatusNetBean.getStatus() == 1) {
                            if (netWorkListener != null)
                                netWorkListener.onConfirmToApplyForNetWorkListener();
                        } else {
                            toastUtil.showToast(upLoadStatusNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }


    public interface NetWorkListener {
        void onUserContentNetWorkListener(ImUserContentNetBean.ResultBean result);

        void onConfirmTheOperationNetWorkListener(String content);

        void onConfirmToApplyForNetWorkListener();
    }

    private NetWorkListener netWorkListener;

    public void setNetWorkListener(NetWorkListener netWorkListener) {
        this.netWorkListener = netWorkListener;
    }


}
