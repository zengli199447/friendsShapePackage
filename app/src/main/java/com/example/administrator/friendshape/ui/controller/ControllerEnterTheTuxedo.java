package com.example.administrator.friendshape.ui.controller;

import android.util.Log;
import android.view.Gravity;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.ControllerClassObserver;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.SubmitATuxedoNetBean;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.rxtools.RxUtil;
import com.example.administrator.friendshape.ui.view.CustomPayPopupWindow;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.utils.SystemUtil;
import com.example.administrator.friendshape.widget.CommonSubscriber;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 作者：真理 Created by Administrator on 2018/11/22.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerEnterTheTuxedo extends ControllerClassObserver {


    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getControllerComponent().inject(this);
    }

    //参团
    public void tuxedoNetWork(String groupId, String OrNoSing) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.GROUP_JOIN_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("groupid", groupId);
        linkedHashMap.put("moneypay_online", OrNoSing);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.GetSubmitATuxedoNetBean(map)
                .compose(RxUtil.<SubmitATuxedoNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<SubmitATuxedoNetBean>(toastUtil) {
                    @Override
                    public void onNext(SubmitATuxedoNetBean submitATuxedoNetBean) {
                        if (submitATuxedoNetBean.getStatus() == 1) {
                            if (tuxedoNetWorkListener != null)
                                tuxedoNetWorkListener.onTuxedoNetWorkListener(submitATuxedoNetBean);
                        } else {
                            toastUtil.showToast(submitATuxedoNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    public interface TuxedoNetWorkListener {
        void onTuxedoNetWorkListener(SubmitATuxedoNetBean submitATuxedoNetBean);
    }

    private TuxedoNetWorkListener tuxedoNetWorkListener;

    public void setTuxedoNetWorkListener(TuxedoNetWorkListener tuxedoNetWorkListener) {
        this.tuxedoNetWorkListener = tuxedoNetWorkListener;
    }

}
