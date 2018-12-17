package com.example.administrator.friendshape.ui.controller;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.ControllerClassObserver;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.FriendNetBean;
import com.example.administrator.friendshape.model.bean.OredeContentNetBean;
import com.example.administrator.friendshape.model.bean.UpLoadStatusNetBean;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.rxtools.RxUtil;
import com.example.administrator.friendshape.ui.adapter.FriendsSelectAdapter;
import com.example.administrator.friendshape.ui.adapter.GroupUserAdapter;
import com.example.administrator.friendshape.ui.dialog.ShowDialog;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.widget.CommonSubscriber;
import com.example.administrator.friendshape.widget.EndlessRecyclerOnScrollListener;
import com.example.administrator.friendshape.widget.ViewBuilder;
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
public class ControllerInviteFriends extends ControllerClassObserver implements FriendsSelectAdapter.CheckItemListener , SwipeRefreshLayout.OnRefreshListener{

    RecyclerView recycler_view;
    TextView empty_layout;
    SwipeRefreshLayout swipe_layout;
    private ShowDialog instance;
    private List<FriendNetBean.ResultBean.FriendBean> list = new ArrayList<>();
    private FriendsSelectAdapter friendsSelectAdapter;
    private int newOrderSize;
    int pageNumber = 1;
    String groupId;
    String idChain = "";

    public ControllerInviteFriends(RecyclerView recycler_view, TextView empty_layout,SwipeRefreshLayout swipe_layout, String groupId) {
        this.recycler_view = recycler_view;
        this.empty_layout = empty_layout;
        this.swipe_layout = swipe_layout;
        this.groupId = groupId;
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.OR_THE_INVITATION:
                SendOutInvitationsNetWork(idChain);
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
        instance = ShowDialog.getInstance();
        initAdapter();
        FriendsNetWork();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    private void initAdapter() {
        recycler_view.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        friendsSelectAdapter = new FriendsSelectAdapter(context, list);
        recycler_view.setAdapter(friendsSelectAdapter);
        recycler_view.addOnScrollListener(scrollListener);
        friendsSelectAdapter.setCheckItemListener(this);
    }

    private void refreshView() {
        friendsSelectAdapter.notifyDataSetChanged();
        swipe_layout.setRefreshing(false);
    }

    private RecyclerView.OnScrollListener scrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadMore() {
            if (friendsSelectAdapter != null) {
                friendsSelectAdapter.setLoadState(friendsSelectAdapter.LOADING);
                if (list.size() > DataClass.DefaultInformationFlow) {
                    pageNumber = pageNumber + 1;
                    FriendsNetWork();
                } else {
                    // 显示加载到底的提示
                    friendsSelectAdapter.setLoadState(friendsSelectAdapter.LOADING_END);
                }
                if (newOrderSize == 0) {
                    friendsSelectAdapter.setLoadState(friendsSelectAdapter.LOADING_END);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView) {
        }
    };

    @Override
    public void onCheckItemListener(int position, boolean status) {
        FriendNetBean.ResultBean.FriendBean friendBean = list.get(position);
        friendBean.setSelect(status);
    }

    //所选好友
    public void getSelectReturn() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isSelect()) {
                LogUtil.e(TAG, list.get(i).getSecondname());
                idChain = new StringBuffer().append(idChain).append(list.get(i).getFriendid()).append(",").toString();
            }
        }
        LogUtil.e(TAG, "idChain : " + idChain);
        if (idChain.isEmpty()) {
            toastUtil.showToast(context.getString(R.string.empty_select));
        } else {
            instance.showConfirmOrNoDialog(context, context.getString(R.string.or_the_invitation), EventCode.ONSTART, EventCode.OR_THE_INVITATION, EventCode.ONSTART);
        }
    }

    @Override
    public void onRefresh() {
        pageNumber = 1;
        FriendsNetWork();
    }

    //好友列表
    private void FriendsNetWork() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.FRIEND_LIST_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("pagenum", pageNumber);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.GetFriendNetBean(map)
                .compose(RxUtil.<FriendNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<FriendNetBean>(toastUtil) {
                    @Override
                    public void onNext(FriendNetBean friendNetBean) {
                        if (friendNetBean.getStatus() == 1) {
                            List<FriendNetBean.ResultBean.FriendBean> friend = friendNetBean.getResult().getFriend();
                            newOrderSize = friend.size();
                            if (pageNumber == 1) {
                                list.clear();
                                if (friend.size() == 0) {
                                    empty_layout.setVisibility(View.VISIBLE);
                                } else {
                                    empty_layout.setVisibility(View.GONE);
                                }
                            }
                            list.addAll(friend);
                            refreshView();
                        } else {
                            toastUtil.showToast(friendNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    //提交邀请
    private void SendOutInvitationsNetWork(String ids) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.ORDER_INVITE_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("groupid", groupId);
        linkedHashMap.put("userids_invite", ids);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.UpLoadStatusNetBean(map)
                .compose(RxUtil.<UpLoadStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UpLoadStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(UpLoadStatusNetBean upLoadStatusNetBean) {
                        if (upLoadStatusNetBean.getStatus() == 1) {
                            instance.showHelpfulHintsDialog(context, context.getString(R.string.submitted_successfully), EventCode.SUBMITTED_SUCCESSFULLY);
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



}
