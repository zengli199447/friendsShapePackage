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
import com.example.administrator.friendshape.model.bean.FriendNetBean;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.rxtools.RxUtil;
import com.example.administrator.friendshape.ui.activity.component.TheDetailsInformationActivity;
import com.example.administrator.friendshape.ui.adapter.ChatFriendAdapter;
import com.example.administrator.friendshape.ui.adapter.FriendsSelectAdapter;
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
 * 作者：真理 Created by Administrator on 2018/11/22.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerChatFriends extends ControllerClassObserver implements SwipeRefreshLayout.OnRefreshListener, ChatFriendAdapter.CheckClickListener {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipe_layout;
    TextView empty_layout;

    private List<FriendNetBean.ResultBean.FriendBean> list = new ArrayList<>();
    private int newFriendsSize;
    int pageNumber = 1;
    private ChatFriendAdapter chatFriendAdapter;
    private AliChatBuilder aliChatBuilder;

    public ControllerChatFriends(RecyclerView recyclerView, SwipeRefreshLayout swipe_layout, TextView empty_layout) {
        this.recyclerView = recyclerView;
        this.swipe_layout = swipe_layout;
        this.empty_layout = empty_layout;
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
        ViewBuilder.ProgressStyleChange(swipe_layout);
        aliChatBuilder = new AliChatBuilder(context);
        swipe_layout.setOnRefreshListener(this);
        swipe_layout.setRefreshing(true);
        initAdapter();
        FriendsNetWork();
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        chatFriendAdapter = new ChatFriendAdapter(context, list);
        recyclerView.setAdapter(chatFriendAdapter);
        recyclerView.addOnScrollListener(scrollListener);
        chatFriendAdapter.setCheckClickListener(this);
    }

    private RecyclerView.OnScrollListener scrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadMore() {
            if (chatFriendAdapter != null) {
                chatFriendAdapter.setLoadState(chatFriendAdapter.LOADING);
                if (list.size() > DataClass.DefaultInformationFlow) {
                    pageNumber = pageNumber + 1;
                    FriendsNetWork();
                } else {
                    // 显示加载到底的提示
                    chatFriendAdapter.setLoadState(chatFriendAdapter.LOADING_END);
                }
                if (newFriendsSize == 0 || newFriendsSize < DataClass.DefaultInformationFlow + 1) {
                    chatFriendAdapter.setLoadState(chatFriendAdapter.LOADING_END);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView) {
        }
    };

    private void refreshView() {
        if (pageNumber != 1) {
            chatFriendAdapter.notifyItemRangeInserted(list.size() - newFriendsSize, newFriendsSize);
        } else {
            chatFriendAdapter.notifyDataSetChanged();
        }
        swipe_layout.setRefreshing(false);
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    @Override
    public void onRefresh() {
        pageNumber = 1;
        FriendsNetWork();
    }

    @Override
    public void onCheckClickListener(View view, int position) {
        FriendNetBean.ResultBean.FriendBean friendBean = list.get(position);
        switch (view.getId()) {
            case -1:
                toastUtil.showToast(friendBean.getSecondname());
                aliChatBuilder.OpenSingleConversation(friendBean.getUserid_friend());
                break;
            case R.id.user_img:
                Intent theDetailsInformationIntent = new Intent(context, TheDetailsInformationActivity.class);
                theDetailsInformationIntent.putExtra("userId", friendBean.getUserid_friend());
                context.startActivity(theDetailsInformationIntent);
                toastUtil.showToast(friendBean.getUserid());
                break;
        }
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
                            newFriendsSize = friend.size();
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


}
