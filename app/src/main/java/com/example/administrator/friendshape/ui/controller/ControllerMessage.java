package com.example.administrator.friendshape.ui.controller;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.alibaba.mobileim.conversation.IYWConversationListener;
import com.alibaba.mobileim.conversation.IYWConversationService;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.conversation.YWConversationManager;
import com.example.administrator.friendshape.base.ControllerClassObserver;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.ui.adapter.ChatMessageAdapter;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.widget.ViewBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/23.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerMessage extends ControllerClassObserver implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipe_layout;
    TextView empty_layout;
    private IYWConversationService conversationService;
    List<YWConversation> conversationAllList = new ArrayList<>();
    private ChatMessageAdapter chatMessageAdapter;

    public ControllerMessage(RecyclerView recyclerView, SwipeRefreshLayout swipe_layout, TextView empty_layout) {
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
        conversationService = DataClass.imCore.getConversationService();
        conversationService.addConversationListener(mConversationListener);//添加会话列表变更监听，收到该监听回调时更新adapter就可以刷新页面了
        ViewBuilder.ProgressStyleChange(swipe_layout);
        swipe_layout.setOnRefreshListener(this);
        initAdapter();
        GetConversationList();
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        chatMessageAdapter = new ChatMessageAdapter(context, conversationAllList);
        recyclerView.setAdapter(chatMessageAdapter);
    }

    private void refreshView() {
        chatMessageAdapter.notifyDataSetChanged();
        swipe_layout.setRefreshing(false);
        LogUtil.e(TAG, "conversationAllList.size() : " + conversationAllList.size());
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }


    @Override
    public void onRefresh() {
        GetConversationList();
    }

    private void SynchronousMessage() {

    }

    //初始化最近联系人列表
    public void GetConversationList() {
        conversationAllList.clear();
        conversationAllList.addAll(conversationService.getConversationList());
        refreshView();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case 0:

                    break;
                case 1:

                    break;
            }
        }
    };

    IYWConversationListener mConversationListener = new IYWConversationListener() {
        @Override
        public void onItemUpdated() {
            handler.sendEmptyMessage(0);
        }
    };

    @Override
    protected void onClassDestroy() {
        super.onClassDestroy();
        if (conversationService != null)
            conversationService.removeConversationListener(mConversationListener);
    }
}
