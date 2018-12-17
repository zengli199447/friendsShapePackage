package com.example.administrator.friendshape.ui.controller;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.ControllerClassObserver;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.OrderaAllTypeNetBean;
import com.example.administrator.friendshape.model.bean.OredeContentNetBean;
import com.example.administrator.friendshape.model.bean.SubmitATuxedoNetBean;
import com.example.administrator.friendshape.model.bean.UpLoadStatusNetBean;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.rxtools.RxUtil;
import com.example.administrator.friendshape.ui.activity.component.TheDetailsInformationActivity;
import com.example.administrator.friendshape.ui.adapter.GroupUserAdapter;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.widget.CommonSubscriber;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/9.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerOrderContent extends ControllerClassObserver implements GroupUserAdapter.OredeContentClickListener {

    RecyclerView group_recycler_view;
    String orderId;
    String groupId;
    private List<OredeContentNetBean.ResultBean.UserBean> list = new ArrayList<>();
    private GroupUserAdapter groupUserAdapter;

    public ControllerOrderContent(RecyclerView group_recycler_view, String orderId, String groupId) {
        this.group_recycler_view = group_recycler_view;
        this.orderId = orderId;
        this.groupId = groupId;
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
        initAdapter();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
        OrderContentNetWork();
    }

    private void initAdapter() {
        group_recycler_view.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        groupUserAdapter = new GroupUserAdapter(context, list);
        group_recycler_view.setAdapter(groupUserAdapter);
        groupUserAdapter.setOredeContentClickListener(this);
    }

    private void refreshView() {
        groupUserAdapter.notifyDataSetChanged();
    }

    @Override
    public void onOredeContentClickListener(int position) {
        toastUtil.showToast(list.get(position).getSecondname());
        Intent intent = new Intent(context, TheDetailsInformationActivity.class);
        intent.putExtra("userId", list.get(position).getUserid());
        context.startActivity(intent);
    }

    //组团订单信息
    private void OrderContentNetWork() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.ORDER_DETAIL_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("orderid", orderId);
        linkedHashMap.put("groupid", groupId);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.GetOredeContentNetBean(map)
                .compose(RxUtil.<OredeContentNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<OredeContentNetBean>(toastUtil) {
                    @Override
                    public void onNext(OredeContentNetBean oredeContentNetBean) {
                        if (oredeContentNetBean.getStatus() == 1) {
                            OredeContentNetBean.ResultBean oredeContentNetBeanResult = oredeContentNetBean.getResult();
                            list.clear();
                            list.addAll(oredeContentNetBeanResult.getUser());
                            if (orderNetWorkListener != null)
                                orderNetWorkListener.onOrderNetWorkListener(oredeContentNetBeanResult);
                            refreshView();
                        } else {
                            toastUtil.showToast(oredeContentNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    //确认消费
    public void confirmTheConsumptionNetWork() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.ORDER_FINISH_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("groupid", groupId);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.UpLoadStatusNetBean(map)
                .compose(RxUtil.<UpLoadStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UpLoadStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(UpLoadStatusNetBean upLoadStatusNetBean) {
                        if (upLoadStatusNetBean.getStatus() == 1) {

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    public interface OrderNetWorkListener {
        void onOrderNetWorkListener(OredeContentNetBean.ResultBean result);

        void onTuxedoNetWorkListener(SubmitATuxedoNetBean submitATuxedoNetBean);
    }

    private OrderNetWorkListener orderNetWorkListener;

    public void setOrderNetWorkListener(OrderNetWorkListener orderNetWorkListener) {
        this.orderNetWorkListener = orderNetWorkListener;
    }


}
