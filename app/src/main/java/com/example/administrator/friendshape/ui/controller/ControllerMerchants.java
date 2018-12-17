package com.example.administrator.friendshape.ui.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.ControllerClassObserver;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.HotSearchResNetBean;
import com.example.administrator.friendshape.model.bean.MerchantsContentsNetWorkBean;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.rxtools.RxUtil;
import com.example.administrator.friendshape.ui.activity.component.OrderContentActivity;
import com.example.administrator.friendshape.ui.activity.component.TheDetailsInformationActivity;
import com.example.administrator.friendshape.ui.adapter.CommentsAdapter;
import com.example.administrator.friendshape.ui.adapter.InTheGroupAdapter;
import com.example.administrator.friendshape.ui.view.ImageSlideshow;
import com.example.administrator.friendshape.widget.CommonSubscriber;
import com.example.administrator.friendshape.widget.FullyLinearLayoutManager;
import com.example.administrator.friendshape.widget.ViewBuilder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 作者：真理 Created by Administrator on 2018/11/6.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerMerchants extends ControllerClassObserver implements ImageSlideshow.OnItemClickListener, InTheGroupAdapter.CheckClickListener, CommentsAdapter.CommentsClickListener {

    RecyclerView recycler_view_group_ing;
    RecyclerView recycler_view_evaluation;
    ImageSlideshow banner_layout;
    String MerchantsId = "";
    boolean refreshStatus;

    List<MerchantsContentsNetWorkBean.ResultBean.GroupBean> groupList = new ArrayList<>();
    List<MerchantsContentsNetWorkBean.ResultBean.CommentBean> commentsList = new ArrayList<>();
    private InTheGroupAdapter inTheGroupAdapter;
    private CommentsAdapter commentsAdapter;
    private List<String> bannerList;
    private Timer timer;

    public ControllerMerchants(String merchantsId, RecyclerView recycler_view_group_ing, RecyclerView recycler_view_evaluation, ImageSlideshow banner_layout) {
        this.MerchantsId = merchantsId;
        this.recycler_view_group_ing = recycler_view_group_ing;
        this.recycler_view_evaluation = recycler_view_evaluation;
        this.banner_layout = banner_layout;
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
        banner_layout.setOnItemClickListener(this);
        banner_layout.setDotSpace(24);
        banner_layout.setDotSize(12);
        banner_layout.setDelay(3000);
        timer = new Timer(true);
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
        NetWorkContent(1);
    }

    private void initAdapter() {
        recycler_view_group_ing.setLayoutManager(ViewBuilder.getFullyLinearLayoutManager(context,false));
        inTheGroupAdapter = new InTheGroupAdapter(context, groupList, true);
        recycler_view_group_ing.setAdapter(inTheGroupAdapter);
        recycler_view_evaluation.setLayoutManager(ViewBuilder.getFullyLinearLayoutManager(context,false));
        commentsAdapter = new CommentsAdapter(context, commentsList);
        recycler_view_evaluation.setAdapter(commentsAdapter);

        commentsAdapter.setCommentsClickListener(this);
        inTheGroupAdapter.setCheckClickListener(this);
    }

    private void refreshView() {
        timingIntervalAdapter();
        commentsAdapter.notifyDataSetChanged();
        if (bannerList != null && bannerList.size() > 0 && !refreshStatus) {
            for (String bannerUrl : bannerList) {
                banner_layout.addImageUrl(new StringBuffer().append(DataClass.URL).append(bannerUrl).toString());
            }
            banner_layout.commit();
            refreshStatus = !refreshStatus;
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
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onCheckClickListener(int position) {
        MerchantsContentsNetWorkBean.ResultBean.GroupBean groupBean = groupList.get(position);
        Intent intent = new Intent(context, OrderContentActivity.class);
        intent.putExtra("groupId", groupBean.getGroupid());
        context.startActivity(intent);
    }

    @Override
    public void onCommentsClickListener(int position) {
        MerchantsContentsNetWorkBean.ResultBean.CommentBean commentBean = commentsList.get(position);
        Intent intent = new Intent(context, TheDetailsInformationActivity.class);
        intent.putExtra("userId", commentBean.getUserid());
        context.startActivity(intent);
    }

    /**
     * 商户详情
     *
     * @param pagenum
     */
    private void NetWorkContent(int pagenum) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.SHOP_DETAIL_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("shopid", MerchantsId);
        linkedHashMap.put("pagenum", pagenum);
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
                            commentsList.clear();
                            groupList.clear();
                            commentsList.addAll(merchantsContent.getComment());
                            groupList.addAll(merchantsContent.getGroup());
                            if (merchantsContent.getShop() != null && !refreshStatus)
                                bannerList = merchantsContent.getShop().getImgarr();
                            if (merchantsNetWorksListener != null)
                                merchantsNetWorksListener.onMerchantsNetWorksListener(merchantsContent);
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

    public interface MerchantsNetWorksListener {
        void onMerchantsNetWorksListener(MerchantsContentsNetWorkBean.ResultBean merchantsContent);
    }

    private MerchantsNetWorksListener merchantsNetWorksListener;

    public void setMerchantsNetWorksListener(MerchantsNetWorksListener merchantsNetWorksListener) {
        this.merchantsNetWorksListener = merchantsNetWorksListener;
    }


}
