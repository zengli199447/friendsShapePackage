package com.example.administrator.friendshape.ui.controller;

import android.util.Log;
import android.view.Gravity;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.ControllerClassObserver;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.GroupContentNetBean;
import com.example.administrator.friendshape.model.bean.GroupOrderNetBean;
import com.example.administrator.friendshape.model.bean.MerchantsTypeFormNetBean;
import com.example.administrator.friendshape.model.bean.SubmitATuxedoNetBean;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.rxtools.RxUtil;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.utils.SystemUtil;
import com.example.administrator.friendshape.widget.CommonSubscriber;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 作者：真理 Created by Administrator on 2018/11/7.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerATeam extends ControllerClassObserver {

    String groupId;
    int flags;

    public ControllerATeam(String groupId, int flags) {
        this.groupId = groupId;
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

                break;
            case 1:
                groupContentNetWork();
                break;
        }
    }


    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    //发起组团
    public void ATeamNetWorkRefresh(String merchantsId, String theContact, String theContactPhone, String actiontime
            , int boyNumber, int boyGroupsNoSingle, int gril, int grilGroupsNoSingle, String groupAmount, String endtime, String about, int iftoaction, Double perCapita) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.GROUP_SET);
        linkedHashMap.put("groupid", groupId);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("shopid", merchantsId);
        linkedHashMap.put("linkman", theContact);
        linkedHashMap.put("linkphone", theContactPhone);
        linkedHashMap.put("actiontime", actiontime);
        linkedHashMap.put("people_boy", boyNumber);
        linkedHashMap.put("iffree_boy", boyGroupsNoSingle);
        linkedHashMap.put("people_girl", gril);
        linkedHashMap.put("iffree_girl", grilGroupsNoSingle);
        linkedHashMap.put("money", groupAmount);
        linkedHashMap.put("endtime", endtime);
        linkedHashMap.put("remark", about);
        linkedHashMap.put("iftoaction", iftoaction);
        linkedHashMap.put("moneypay_online", perCapita);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.GetGroupOrderNetBean(map)
                .compose(RxUtil.<GroupOrderNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<GroupOrderNetBean>(toastUtil) {
                    @Override
                    public void onNext(GroupOrderNetBean groupOrderNetBean) {
                        if (groupOrderNetBean.getStatus() == 1) {
                            if (netWorkRefreshListener != null)
                                netWorkRefreshListener.onNetWorkRefreshListener(groupOrderNetBean.getNeedpay(), groupOrderNetBean.getOrderid(), groupOrderNetBean.getOrdercode());
                        } else {
                            toastUtil.showToast(groupOrderNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));

    }

    //获取组团信息
    private void groupContentNetWork() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.SHOP_GROUP_INIT_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("groupid", groupId);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.GetGroupContentNetBean(map)
                .compose(RxUtil.<GroupContentNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<GroupContentNetBean>(toastUtil) {
                    @Override
                    public void onNext(GroupContentNetBean groupContentNetBean) {
                        if (groupContentNetBean.getStatus() == 1) {
                            if (netWorkRefreshListener != null)
                                netWorkRefreshListener.onGroupContentListener(groupContentNetBean.getResult().getGroup());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    public interface NetWorkRefreshListener {
        void onNetWorkRefreshListener(String needPayStatus, String orderId, String orderCode);

        void onGroupContentListener(GroupContentNetBean.ResultBean.GroupBean groupBean);

    }

    private NetWorkRefreshListener netWorkRefreshListener;

    public void setNetWorkRefreshListener(NetWorkRefreshListener netWorkRefreshListener) {
        this.netWorkRefreshListener = netWorkRefreshListener;
    }


}
