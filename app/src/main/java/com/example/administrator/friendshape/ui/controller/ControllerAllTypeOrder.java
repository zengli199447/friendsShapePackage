package com.example.administrator.friendshape.ui.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.ControllerClassObserver;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.BusObject;
import com.example.administrator.friendshape.model.bean.MerchantsTypeFormNetBean;
import com.example.administrator.friendshape.model.bean.OrderaAllTypeNetBean;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.rxtools.RxBus;
import com.example.administrator.friendshape.rxtools.RxUtil;
import com.example.administrator.friendshape.ui.activity.component.CancleOrderActivity;
import com.example.administrator.friendshape.ui.activity.component.GeneralActivity;
import com.example.administrator.friendshape.ui.activity.component.OrderContentActivity;
import com.example.administrator.friendshape.ui.adapter.OrderTypeAdapter;
import com.example.administrator.friendshape.ui.dialog.ShowDialog;
import com.example.administrator.friendshape.ui.view.CustomPayPopupWindow;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.utils.SystemUtil;
import com.example.administrator.friendshape.widget.CommonSubscriber;
import com.example.administrator.friendshape.widget.EndlessRecyclerOnScrollListener;
import com.example.administrator.friendshape.widget.FullyLinearLayoutManager;
import com.example.administrator.friendshape.widget.ViewBuilder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/8.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerAllTypeOrder extends ControllerClassObserver implements OrderTypeAdapter.OrderClickListener, SwipeRefreshLayout.OnRefreshListener, CustomPayPopupWindow.OnItemClickListener, PopupWindow.OnDismissListener {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipe_layout;
    TextView empty_layout;
    private List<OrderaAllTypeNetBean.ResultBean.OrderBean> orderList = new ArrayList<>();
    private OrderTypeAdapter orderTypeAdapter;
    private int newOrderSize;
    private int pageNumber = 1;
    String type;
    private CustomPayPopupWindow customPayPopupWindow;
    private String moneyPayOnline;
    private String orderCode;
    private ShowDialog instance;
    boolean refreshStatus;

    public ControllerAllTypeOrder(RecyclerView recyclerView, SwipeRefreshLayout swipe_layout, TextView empty_layout, String type) {
        this.recyclerView = recyclerView;
        this.swipe_layout = swipe_layout;
        this.empty_layout = empty_layout;
        this.type = type;
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.REFRESH_ALL_ORDER:
                AllTypeOredeNetWork();
                break;
            case EventCode.PAY_RETURN_STATUS:
                if (refreshStatus) {
                    instance.showOnLinePayStatusDialog(context, commonevent.getTemp_value());
                    refreshStatus = false;
                    onRefresh();
                }
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
        ViewBuilder.ProgressStyleChange(swipe_layout);
        swipe_layout.setOnRefreshListener(this);
        swipe_layout.setRefreshing(true);
        customPayPopupWindow = new CustomPayPopupWindow(context);
        instance = ShowDialog.getInstance();
        initAdapter();
        AllTypeOredeNetWork();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        orderTypeAdapter = new OrderTypeAdapter(context, orderList);
        recyclerView.setAdapter(orderTypeAdapter);
        recyclerView.addOnScrollListener(scrollListener);
        orderTypeAdapter.setOrderClickListener(this);
        customPayPopupWindow.setOnItemClickListener(this);
        customPayPopupWindow.setOnDismissListener(this);
    }

    private void refreshView() {
        if (pageNumber != 1) {
            orderTypeAdapter.notifyItemRangeInserted(orderList.size() - newOrderSize, newOrderSize);
        } else {
            orderTypeAdapter.notifyDataSetChanged();
        }
        swipe_layout.setRefreshing(false);
    }

    private RecyclerView.OnScrollListener scrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadMore() {
            if (orderTypeAdapter != null) {
                orderTypeAdapter.setLoadState(orderTypeAdapter.LOADING);
                if (orderList.size() > DataClass.DefaultInformationFlow) {
                    pageNumber = pageNumber + 1;
                    AllTypeOredeNetWork();
                } else {
                    // 显示加载到底的提示
                    orderTypeAdapter.setLoadState(orderTypeAdapter.LOADING_END);
                }
                if (newOrderSize == 0 || newOrderSize < DataClass.DefaultInformationFlow + 1) {
                    orderTypeAdapter.setLoadState(orderTypeAdapter.LOADING_END);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView) {

        }
    };

    @Override
    public void onRefresh() {
        pageNumber = 1;
        AllTypeOredeNetWork();
    }

    //按钮点击响应
    @SuppressLint("WrongConstant")
    @Override
    public void ControllerCheckListener(int position, String content) {
        OrderaAllTypeNetBean.ResultBean.OrderBean orderBean = orderList.get(position);
        orderBean.getState();
        moneyPayOnline = orderBean.getMoneypay_online();
        orderCode = orderBean.getOrdercode();
        if (context.getString(R.string.cancle_order).equals(content)) {
            Intent orderIntent = new Intent(context, CancleOrderActivity.class);
            orderIntent.putExtra("orderId", orderBean.getOrderid());
            context.startActivity(orderIntent);
        } else if (context.getString(R.string.pay).equals(content)) {
            if (orderBean.getState_group().equals(context.getString(R.string.cancelled))) {
                toastUtil.showToast(context.getString(R.string.order_cancelled));
            } else {
                customPayPopupWindow.showAtLocation(empty_layout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                SystemUtil.windowToDark(context);
            }
        } else if (context.getString(R.string.for_a_refund).equals(content)) {
            Intent refundIntent = new Intent(context, GeneralActivity.class);
            refundIntent.putExtra("orderId", orderBean.getOrderid());
            refundIntent.setFlags(4);
            context.startActivity(refundIntent);
        } else if (context.getString(R.string.evaluation_order).equals(content)) {
            Intent evaluationIntent = new Intent(context, GeneralActivity.class);
            evaluationIntent.putExtra("orderId", orderBean.getOrderid());
            evaluationIntent.putExtra("merchantsPhoto", orderBean.getPhoto());
            evaluationIntent.putExtra("merchantsShopname", orderBean.getShopname());
            evaluationIntent.setFlags(3);
            context.startActivity(evaluationIntent);
        }
    }

    //整体响应
    @Override
    public void OrderClickListener(int position) {
        Intent intent = new Intent(context, OrderContentActivity.class);
        OrderaAllTypeNetBean.ResultBean.OrderBean orderBean = orderList.get(position);
        intent.putExtra("orderId", orderBean.getOrderid());
        intent.putExtra("groupId", orderBean.getGroupid());
        context.startActivity(intent);
    }

    @Override
    public void onDismiss() {
        SystemUtil.windowToLight(context);
    }

    //选择支付类型
    @Override
    public void setOnItemClick(View v, int selectType) {
        toastUtil.showToast("selectType : " + selectType);
        BusObject busObject = null;
        switch (selectType) {
            case 0:
                busObject = new BusObject(orderCode, context.getString(R.string.wechat_pay), null, -1);
                break;
            case 1:
                busObject = new BusObject(orderCode, context.getString(R.string.zfb_pay), null, -1);
                break;
        }
        refreshStatus = true;
        RxBus.getDefault().post(new CommonEvent(EventCode.PAY_ACTION, busObject));
        customPayPopupWindow.dismiss();
    }

    //订单列表
    public void AllTypeOredeNetWork() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.ORDER_LIST_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("state", type);
        linkedHashMap.put("pagenum", pageNumber);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.GetOrderaAllTypeNetBean(map)
                .compose(RxUtil.<OrderaAllTypeNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<OrderaAllTypeNetBean>(toastUtil) {
                    @Override
                    public void onNext(OrderaAllTypeNetBean orderaAllTypeNetBean) {
                        if (orderaAllTypeNetBean.getStatus() == 1) {
                            OrderaAllTypeNetBean.ResultBean result = orderaAllTypeNetBean.getResult();
                            List<OrderaAllTypeNetBean.ResultBean.OrderBean> order = result.getOrder();
                            newOrderSize = order.size();
                            if (pageNumber == 1) {
                                orderList.clear();
                                if (order.size() == 0) {
                                    empty_layout.setVisibility(View.VISIBLE);
                                } else {
                                    empty_layout.setVisibility(View.GONE);
                                }
                            }
                            orderList.addAll(order);
                            refreshView();
                        } else {
                            toastUtil.showToast(orderaAllTypeNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));

    }


}
