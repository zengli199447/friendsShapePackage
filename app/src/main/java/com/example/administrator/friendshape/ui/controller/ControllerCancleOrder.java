package com.example.administrator.friendshape.ui.controller;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.ControllerClassObserver;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.CancleGroupNetBean;
import com.example.administrator.friendshape.model.bean.OredeContentNetBean;
import com.example.administrator.friendshape.model.bean.UpLoadStatusNetBean;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.rxtools.RxBus;
import com.example.administrator.friendshape.rxtools.RxUtil;
import com.example.administrator.friendshape.ui.adapter.ClassificationAdapter;
import com.example.administrator.friendshape.ui.adapter.SelectUserAdapter;
import com.example.administrator.friendshape.ui.dialog.ShowDialog;
import com.example.administrator.friendshape.ui.holder.MyViewHolder;
import com.example.administrator.friendshape.widget.CommonSubscriber;
import com.example.administrator.friendshape.widget.FullyGridLayoutManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/12.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerCancleOrder extends ControllerClassObserver implements SelectUserAdapter.CheckClickListener {

    RecyclerView recycler_view;
    TextView commite;
    String orderId;
    List<CancleGroupNetBean.ResultBean.UsersBean> list = new ArrayList<>();
    private SelectUserAdapter selectUserAdapter;
    private String theNewDirector = "";
    private ShowDialog instance;
    private String userId;

    public ControllerCancleOrder(RecyclerView recycler_view, TextView commite, String orderId) {
        this.recycler_view = recycler_view;
        this.commite = commite;
        this.orderId = orderId;
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.OR_CANCLE_OLDER:
                CancleOrderNetWork();
                toastUtil.showToast("提交");
                break;
        }
    }

    @Override
    protected void initInject() {
        getControllerComponent().inject(this);
    }

    @Override
    protected void onClassCreate() {
        super.onClassCreate();
        instance = ShowDialog.getInstance();
        initAdapter();
        StreamlineOrderNetWork();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    private void initAdapter() {
        FullyGridLayoutManager fullyGridLayoutManager = new FullyGridLayoutManager(context, 4);
        fullyGridLayoutManager.setScrollEnable(false);
        recycler_view.setLayoutManager(fullyGridLayoutManager);
        selectUserAdapter = new SelectUserAdapter(context, list);
        recycler_view.setAdapter(selectUserAdapter);
        selectUserAdapter.setCheckClickListener(this);
    }

    private void refreshView() {
        selectUserAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCheckClickListener(int position) {
        commite.setBackground(context.getResources().getDrawable(R.color.blue_bar));
        for (int i = 0; i < list.size(); i++) {
            if (i == position) {
                list.get(i).setStatus(true);
            } else {
                list.get(i).setStatus(false);
            }
        }
        theNewDirector = list.get(position).getOrderid();
        refreshView();
    }

    //过滤提交条件
    public void FilterConditions() {
        if (list.size() > 0 && theNewDirector.isEmpty()) {
            toastUtil.showToast(context.getString(R.string.empty_select));
        } else {
            instance.showConfirmOrNoDialog(context, context.getString(R.string.or_cancle_older), EventCode.ONSTART, EventCode.OR_CANCLE_OLDER, EventCode.ONSTART);
        }
    }

    //获取订单数据
    private void StreamlineOrderNetWork() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.ORDER_CANCEL_INIT_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("orderid", orderId);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.GetCancleGroupNetBean(map)
                .compose(RxUtil.<CancleGroupNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<CancleGroupNetBean>(toastUtil) {
                    @Override
                    public void onNext(CancleGroupNetBean cancleGroupNetBean) {
                        if (cancleGroupNetBean.getStatus() == 1) {
                            CancleGroupNetBean.ResultBean result = cancleGroupNetBean.getResult();
                            userId = result.getDetail().getUserid();
                            list.addAll(result.getUsers());
                            if (orderCancleNetWorkListener != null)
                                orderCancleNetWorkListener.onOrderCancleNetWorkListener(result);
                            refreshView();
                        } else {
                            toastUtil.showToast(cancleGroupNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    //提交取消
    private void CancleOrderNetWork() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.ORDER_CANCEL_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("orderid", orderId);
        linkedHashMap.put("userid_create", theNewDirector);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.UpLoadStatusNetBean(map)
                .compose(RxUtil.<UpLoadStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UpLoadStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(UpLoadStatusNetBean upLoadStatusNetBean) {
                        if (upLoadStatusNetBean.getStatus() == 1) {
                            RxBus.getDefault().post(new CommonEvent(EventCode.REFRESH_ALL_ORDER));
                            instance.showHelpfulHintsDialog(context, context.getString(R.string.order_cancle_successful), EventCode.ORDER_CANCLE_SUCCESSFUL);
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


    public interface OrderCancleNetWorkListener {
        void onOrderCancleNetWorkListener(CancleGroupNetBean.ResultBean result);
    }

    private OrderCancleNetWorkListener orderCancleNetWorkListener;

    public void setOrderCancleNetWorkListener(OrderCancleNetWorkListener orderCancleNetWorkListener) {
        this.orderCancleNetWorkListener = orderCancleNetWorkListener;
    }

}
