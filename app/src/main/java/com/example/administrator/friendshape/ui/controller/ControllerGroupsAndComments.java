package com.example.administrator.friendshape.ui.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.ControllerClassObserver;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.MerchantsContentsNetWorkBean;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.rxtools.RxUtil;
import com.example.administrator.friendshape.ui.activity.component.OrderContentActivity;
import com.example.administrator.friendshape.ui.activity.component.TheDetailsInformationActivity;
import com.example.administrator.friendshape.ui.adapter.CommentsAdapter;
import com.example.administrator.friendshape.ui.adapter.InTheGroupAdapter;
import com.example.administrator.friendshape.widget.CommonSubscriber;
import com.example.administrator.friendshape.widget.FullyLinearLayoutManager;
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
public class ControllerGroupsAndComments extends ControllerClassObserver implements InTheGroupAdapter.CheckClickListener, CommentsAdapter.CommentsClickListener {

    RecyclerView recyclerView;
    String merchantsId;
    int flags;

    List<MerchantsContentsNetWorkBean.ResultBean.GroupBean> groupList = new ArrayList<>();
    List<MerchantsContentsNetWorkBean.ResultBean.CommentBean> commentsList = new ArrayList<>();
    List<Object> list = new ArrayList<>();

    private InTheGroupAdapter inTheGroupAdapter;
    private CommentsAdapter commentsAdapter;
    private int commentSize;
    private int groupSize;
    private int pageNumber;

    public ControllerGroupsAndComments(RecyclerView recyclerView, String merchantsId, int flags) {
        this.recyclerView = recyclerView;
        this.merchantsId = merchantsId;
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
        initAdapter();
        NetWorkContent(1);
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(ViewBuilder.getFullyLinearLayoutManager(context,false));
        switch (flags) {
            case 0:
                inTheGroupAdapter = new InTheGroupAdapter(context, groupList, false);
                recyclerView.setAdapter(inTheGroupAdapter);
                inTheGroupAdapter.setCheckClickListener(this);
                break;
            case 1:
                commentsAdapter = new CommentsAdapter(context, commentsList);
                recyclerView.setAdapter(commentsAdapter);
                commentsAdapter.setCommentsClickListener(this);
                break;
        }
    }

    private void refreshView() {
        switch (flags) {
            case 0:
                timingIntervalAdapter();
                break;
            case 1:
                if (pageNumber != 1) {
                    commentsAdapter.notifyItemRangeInserted(list.size() - commentSize, commentSize);
                } else {
                    commentsAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    //剩余时间计时
    private void timingIntervalAdapter() {
        inTheGroupAdapter.notifyDataSetChanged();
        handler.sendEmptyMessageDelayed(0, context.getResources().getInteger(R.integer.timing_interval));
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            timingIntervalAdapter();
        }
    };

    @Override
    public void onCommentsClickListener(int position) {
        MerchantsContentsNetWorkBean.ResultBean.CommentBean commentBean = commentsList.get(position);
        Intent intent = new Intent(context, TheDetailsInformationActivity.class);
        intent.putExtra("userId", commentBean.getUserid());
        context.startActivity(intent);
    }

    @Override
    public void onCheckClickListener(int position) {
        MerchantsContentsNetWorkBean.ResultBean.GroupBean groupBean = groupList.get(position);
        Intent intent = new Intent(context, OrderContentActivity.class);
        intent.putExtra("groupId", groupBean.getGroupid());
        context.startActivity(intent);
    }


    /**
     * 商户详情
     */
    public void NetWorkContent(final int pageNumber) {
        this.pageNumber = pageNumber;
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.SHOP_DETAIL_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("shopid", merchantsId);
        linkedHashMap.put("pagenum", pageNumber);
        linkedHashMap.put("longitude", DataClass.LONGITUDE);
        linkedHashMap.put("latitude", DataClass.LATITUDE);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.GetMerchantsContentsNetWorkBean(map)
                .compose(RxUtil.<MerchantsContentsNetWorkBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<MerchantsContentsNetWorkBean>(toastUtil) {
                    @Override
                    public void onNext(MerchantsContentsNetWorkBean merchantsContentsNetWorkBean) {
                        if (merchantsContentsNetWorkBean.getStatus() == 1) {
                            MerchantsContentsNetWorkBean.ResultBean merchantsContent = merchantsContentsNetWorkBean.getResult();
                            list.clear();
                            if (pageNumber == 1) {
                                commentsList.clear();
                                groupList.clear();
                            }
                            commentSize = merchantsContent.getComment().size();
                            groupSize = merchantsContent.getGroup().size();
                            commentsList.addAll(merchantsContent.getComment());
                            groupList.addAll(merchantsContent.getGroup());
                            boolean viewStatus = false;
                            switch (flags) {
                                case 0:
                                    if (groupList.size() == 0)
                                        viewStatus = true;
                                    list.addAll(merchantsContent.getGroup());
                                    setNetWorkListener(list, viewStatus);
                                    break;
                                case 1:
                                    if (commentsList.size() == 0)
                                        viewStatus = true;
                                    list.addAll(merchantsContent.getComment());
                                    setNetWorkListener(list, viewStatus);
                                    break;
                            }
                            refreshView();
                        } else {
                            toastUtil.showToast(merchantsContentsNetWorkBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    private void setNetWorkListener(List<Object> list, boolean viewStatus) {
        boolean status = false;
        if (list.size() == 0)
            status = true;
        if (merchantsNetWorkRefreshListener != null)
            merchantsNetWorkRefreshListener.onMerchantsNetWorkRefreshListener(status, viewStatus);
    }

    public interface MerchantsNetWorkRefreshListener {
        void onMerchantsNetWorkRefreshListener(boolean status, boolean viewStatus);
    }

    private MerchantsNetWorkRefreshListener merchantsNetWorkRefreshListener;

    public void setMerchantsNetWorkRefreshListener(MerchantsNetWorkRefreshListener merchantsNetWorkRefreshListener) {
        this.merchantsNetWorkRefreshListener = merchantsNetWorkRefreshListener;
    }


}
