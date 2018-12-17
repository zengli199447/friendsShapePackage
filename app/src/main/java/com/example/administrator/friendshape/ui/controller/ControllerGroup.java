package com.example.administrator.friendshape.ui.controller;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.ControllerClassObserver;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.BusObject;
import com.example.administrator.friendshape.model.bean.GroupAboutNetBean;
import com.example.administrator.friendshape.model.bean.OrderaAllTypeNetBean;
import com.example.administrator.friendshape.model.bean.SubmitATuxedoNetBean;
import com.example.administrator.friendshape.model.bean.UpLoadStatusNetBean;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.rxtools.RxBus;
import com.example.administrator.friendshape.rxtools.RxUtil;
import com.example.administrator.friendshape.ui.activity.LoginActivity;
import com.example.administrator.friendshape.ui.activity.component.EnterTheTuxedoActivity;
import com.example.administrator.friendshape.ui.activity.component.OrderContentActivity;
import com.example.administrator.friendshape.ui.adapter.DynamicAdapter;
import com.example.administrator.friendshape.ui.adapter.GroupAdapter;
import com.example.administrator.friendshape.ui.dialog.ShowDialog;
import com.example.administrator.friendshape.ui.view.CustomPayPopupWindow;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.utils.SystemUtil;
import com.example.administrator.friendshape.widget.AliChatBuilder;
import com.example.administrator.friendshape.widget.CommonSubscriber;
import com.example.administrator.friendshape.widget.EndlessRecyclerOnScrollListener;
import com.example.administrator.friendshape.widget.ViewBuilder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/15.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerGroup extends ControllerClassObserver implements SwipeRefreshLayout.OnRefreshListener, GroupAdapter.CheckClickListener {

    SwipeRefreshLayout swipe_layout;
    TextView empty_layout;
    RecyclerView recycler_view;

    private List<GroupAboutNetBean.ResultBean.GroupBean> list = new ArrayList<>();
    private GroupAdapter groupAdapter;

    int pageNumber = 1;
    String lookUserId = "";
    String gender = "";
    String merchantsType = "";
    private int newGroupSize;
    private AliChatBuilder aliChatBuilder;

    public ControllerGroup(SwipeRefreshLayout swipe_layout, TextView empty_layout, RecyclerView recycler_view, String lookUserId) {
        this.swipe_layout = swipe_layout;
        this.empty_layout = empty_layout;
        this.recycler_view = recycler_view;
        this.lookUserId = lookUserId;
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.SEARCH_ACTION_GROUP:
                String[] split = commonevent.getTemp_str().split("-");
                gender = split[0];
                if (context.getString(R.string.there_is_no_limit).equals(split[0])) {
                    gender = "";
                }
                if ("-1".equals(split[1])) {
                    merchantsType = "";
                } else {
                    merchantsType = split[1];
                }
                pageNumber = 1;
                codeViolationNetWork();
                break;
            case EventCode.REFRESH_ALL_ORDER:
                codeViolationNetWork();
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
        swipe_layout.setOnRefreshListener(this);
        ViewBuilder.ProgressStyleChange(swipe_layout);
        swipe_layout.setRefreshing(true);
        aliChatBuilder = new AliChatBuilder(context);
        initAdapter();
        codeViolationNetWork();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
        LogUtil.e(TAG, "onClassResume - Fragment");
    }

    private void initAdapter() {
        recycler_view.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        groupAdapter = new GroupAdapter(context, list);
        recycler_view.setAdapter(groupAdapter);
        recycler_view.addOnScrollListener(scrollListener);
        groupAdapter.setCheckClickListener(this);
    }

    private RecyclerView.OnScrollListener scrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadMore() {
            if (groupAdapter != null) {
                groupAdapter.setLoadState(groupAdapter.LOADING);
                if (list.size() > DataClass.DefaultInformationFlow) {
                    pageNumber = pageNumber + 1;
                    codeViolationNetWork();
                } else {
                    // 显示加载到底的提示
                    groupAdapter.setLoadState(groupAdapter.LOADING_END);
                }
                if (newGroupSize == 0 || newGroupSize < DataClass.DefaultInformationFlow + 1) {
                    groupAdapter.setLoadState(groupAdapter.LOADING_END);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView) {

        }
    };

    private void refreshView() {
        if (pageNumber != 1) {
            groupAdapter.notifyItemRangeInserted(list.size() - newGroupSize, newGroupSize);
        } else {
            groupAdapter.notifyDataSetChanged();
        }
        swipe_layout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        pageNumber = 1;
        codeViolationNetWork();
    }

    @Override
    public void onCheckClickListener(View view, int position) {
        GroupAboutNetBean.ResultBean.GroupBean groupBean = list.get(position);
        switch (view.getId()) {
            case -1:
                Intent intent = new Intent(context, OrderContentActivity.class);
                intent.putExtra("groupId", groupBean.getGroupid());
                context.startActivity(intent);
                break;
            case R.id.tuxedo:
                if ("1".equals(groupBean.getIfcanjoin())) {
                    Intent enterTheTuxedoIntent = new Intent(context, EnterTheTuxedoActivity.class);
                    enterTheTuxedoIntent.putExtra("groupId", groupBean.getGroupid());
                    enterTheTuxedoIntent.putExtra("freeBoyStatus", groupBean.getIffree_boy());
                    enterTheTuxedoIntent.putExtra("freeGirlStatus", groupBean.getIffree_girl());
                    enterTheTuxedoIntent.putExtra("MoneyAvg", groupBean.getMoney_avg());
                    context.startActivity(enterTheTuxedoIntent);
                }
                break;
            case R.id.chat:
                toastUtil.showToast("进入群聊");
                if (DataClass.USERID.isEmpty()) {
                    context.startActivity(new Intent(context, LoginActivity.class));
                    return;
                }
                aliChatBuilder.OpenSingleConversation(groupBean.getTribe_id());
                break;
        }
    }

    //获取组团分类、组团列表
    private void codeViolationNetWork() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.GROUP_LIST_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("pagenum", pageNumber);
        linkedHashMap.put("longitude", DataClass.LONGITUDE);
        linkedHashMap.put("latitude", DataClass.LATITUDE);
        linkedHashMap.put("datatype", gender);
        linkedHashMap.put("servicecategoryid", merchantsType);
        linkedHashMap.put("userid_view", lookUserId);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.GetGroupAboutNetBean(map)
                .compose(RxUtil.<GroupAboutNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<GroupAboutNetBean>(toastUtil) {
                    @Override
                    public void onNext(GroupAboutNetBean groupAboutNetBean) {
                        if (groupAboutNetBean.getStatus() == 1) {
                            List<GroupAboutNetBean.ResultBean.GroupBean> servicecategory = groupAboutNetBean.getResult().getGroup();
                            newGroupSize = servicecategory.size();
                            if (pageNumber == 1) {
                                list.clear();
                                if (newGroupSize == 0) {
                                    empty_layout.setVisibility(View.VISIBLE);
                                } else {
                                    empty_layout.setVisibility(View.GONE);
                                }
                            }
                            list.addAll(servicecategory);
                            refreshView();
                        } else {
                            toastUtil.showToast(groupAboutNetBean.getMessage());
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
