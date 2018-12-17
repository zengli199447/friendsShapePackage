package com.example.administrator.friendshape.ui.controller;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.ControllerClassObserver;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.DynamicNetBean;
import com.example.administrator.friendshape.model.bean.PeopleNearbyNetBean;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.rxtools.RxUtil;
import com.example.administrator.friendshape.ui.activity.component.TheDetailsInformationActivity;
import com.example.administrator.friendshape.ui.adapter.DynamicAdapter;
import com.example.administrator.friendshape.ui.adapter.PeopleNearbyAdapter;
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
public class ControllerPeopleNearby extends ControllerClassObserver implements SwipeRefreshLayout.OnRefreshListener, PeopleNearbyAdapter.CheckClickListener {

    RecyclerView recycler_view;
    TextView empty_layout;
    SwipeRefreshLayout swipe_layout;

    private List<PeopleNearbyNetBean.ResultBean.UserBean> list = new ArrayList<>();
    int pageNumber = 1;
    private int newPeopleNearbySize;
    private String genderType = "";
    private PeopleNearbyAdapter peopleNearbyAdapter;

    public ControllerPeopleNearby(RecyclerView recycler_view, TextView empty_layout, SwipeRefreshLayout swipe_layout) {
        this.recycler_view = recycler_view;
        this.empty_layout = empty_layout;
        this.swipe_layout = swipe_layout;
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.SEARCH_ACTION_PEOPLE_NEAR:
                if (context.getString(R.string.people_nearby).equals(commonevent.getTemp_str())) {
                    genderType = "";
                } else {
                    genderType = commonevent.getTemp_str();
                }
                pageNumber = 1;
                PeopleNearbyNetWork();
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
        initAdapter();
        swipe_layout.setOnRefreshListener(this);
        ViewBuilder.ProgressStyleChange(swipe_layout);
        swipe_layout.setRefreshing(true);
        PeopleNearbyNetWork();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    private void initAdapter() {
        recycler_view.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        peopleNearbyAdapter = new PeopleNearbyAdapter(context, list);
        recycler_view.setAdapter(peopleNearbyAdapter);
        recycler_view.addOnScrollListener(scrollListener);
        peopleNearbyAdapter.setCheckClickListener(this);
    }

    private RecyclerView.OnScrollListener scrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadMore() {
            if (peopleNearbyAdapter != null) {
                peopleNearbyAdapter.setLoadState(peopleNearbyAdapter.LOADING);
                if (list.size() > DataClass.DefaultInformationFlow) {
                    pageNumber = pageNumber + 1;
                    PeopleNearbyNetWork();
                } else {
                    // 显示加载到底的提示
                    peopleNearbyAdapter.setLoadState(peopleNearbyAdapter.LOADING_END);
                }
                if (newPeopleNearbySize == 0 || newPeopleNearbySize < DataClass.DefaultInformationFlow + 1) {
                    peopleNearbyAdapter.setLoadState(peopleNearbyAdapter.LOADING_END);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView) {

        }
    };

    private void refreshView() {
        if (pageNumber != 1) {
            peopleNearbyAdapter.notifyItemRangeInserted(list.size() - newPeopleNearbySize, newPeopleNearbySize);
        } else {
            peopleNearbyAdapter.notifyDataSetChanged();
        }
        swipe_layout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        pageNumber = 1;
        PeopleNearbyNetWork();
    }

    @Override
    public void onCheckClickListener(int position) {
        Intent intent = new Intent(context, TheDetailsInformationActivity.class);
        PeopleNearbyNetBean.ResultBean.UserBean userBean = list.get(position);
        intent.putExtra("userId", userBean.getUserid());
        intent.putExtra("userAge", userBean.getAge());
        intent.putExtra("userGender", userBean.getSex());
        intent.putExtra("userNiceName", userBean.getSecondname());
        intent.putExtra("userPhoto", userBean.getPhoto());
        context.startActivity(intent);
    }

    //获取附近人(男、女)
    private void PeopleNearbyNetWork() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.USER_LIST_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("pagenum", pageNumber);
        linkedHashMap.put("longitude", DataClass.LONGITUDE);
        linkedHashMap.put("latitude", DataClass.LATITUDE);
        linkedHashMap.put("datatype", genderType);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.GetPeopleNearbyNetBean(map)
                .compose(RxUtil.<PeopleNearbyNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<PeopleNearbyNetBean>(toastUtil) {
                    @Override
                    public void onNext(PeopleNearbyNetBean peopleNearbyNetBean) {
                        if (peopleNearbyNetBean.getStatus() == 1) {
                            List<PeopleNearbyNetBean.ResultBean.UserBean> user = peopleNearbyNetBean.getResult().getUser();
                            newPeopleNearbySize = user.size();
                            if (pageNumber == 1) {
                                list.clear();
                                if (newPeopleNearbySize == 0) {
                                    empty_layout.setVisibility(View.VISIBLE);
                                } else {
                                    empty_layout.setVisibility(View.GONE);
                                }
                            }
                            list.addAll(user);
                            refreshView();
                        } else {
                            toastUtil.showToast(peopleNearbyNetBean.getMessage());
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
