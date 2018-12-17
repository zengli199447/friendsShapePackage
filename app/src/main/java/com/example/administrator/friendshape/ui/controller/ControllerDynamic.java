package com.example.administrator.friendshape.ui.controller;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.ControllerClassObserver;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.BusObject;
import com.example.administrator.friendshape.model.bean.CancleGroupNetBean;
import com.example.administrator.friendshape.model.bean.DynamicNetBean;
import com.example.administrator.friendshape.model.bean.OrderaAllTypeNetBean;
import com.example.administrator.friendshape.model.bean.PackingNetBeanPhoto;
import com.example.administrator.friendshape.model.bean.PeopleNearbyNetBean;
import com.example.administrator.friendshape.model.bean.PraiseStatusNetBean;
import com.example.administrator.friendshape.model.bean.UpLoadStatusNetBean;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.rxtools.RxBus;
import com.example.administrator.friendshape.rxtools.RxUtil;
import com.example.administrator.friendshape.ui.activity.LoginActivity;
import com.example.administrator.friendshape.ui.activity.component.OrderContentActivity;
import com.example.administrator.friendshape.ui.activity.component.TheDetailsInformationActivity;
import com.example.administrator.friendshape.ui.adapter.DynamicAdapter;
import com.example.administrator.friendshape.ui.adapter.DynamicCommentsAdapter;
import com.example.administrator.friendshape.ui.adapter.OrderTypeAdapter;
import com.example.administrator.friendshape.ui.dialog.FloatingWindowDialog;
import com.example.administrator.friendshape.ui.dialog.ShowDialog;
import com.example.administrator.friendshape.ui.holder.MyViewHolder;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.widget.AlbumBuilder;
import com.example.administrator.friendshape.widget.CommentsLayoutViewBuilder;
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
 * 作者：真理 Created by Administrator on 2018/11/13.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerDynamic extends ControllerClassObserver implements SwipeRefreshLayout.OnRefreshListener, DynamicAdapter.DynamicParentClickListener, FloatingWindowDialog.OnAppBarLayoutOffsetChangedListener, FloatingWindowDialog.OnDismissListener {

    SwipeRefreshLayout swipe_layout;
    TextView empty_layout;
    RecyclerView recycler_view;
    private DynamicAdapter dynamicAdapter;
    private List<DynamicNetBean.ResultBean.NewsBean> list = new ArrayList<>();
    private ArrayList<String> photoList = new ArrayList<>();
    private ArrayList<Object> commentsList = new ArrayList<>();
    int pageNumber = 1;
    String lookUserId = "";
    int newDynamicSize;
    int dynamicType;
    private AlbumBuilder albumBuilder;
    private ShowDialog instance;
    private String selectDynamicId;
    private String userId;
    private int selectCommentsIndex = -1;

    private FloatingWindowDialog floatingWindowDialog;

    /**
     * @param recycler_view
     * @param empty_layout
     * @param swipe_layout
     * @param dynamicType   1 最新动态 2 好友 动态 3 我的动态 4 某一用户动态 （需要 lookUserId）
     * @param lookUserId    查看用户的动态（注 ： 个人详情界面使用）
     */
    public ControllerDynamic(RecyclerView recycler_view, TextView empty_layout, SwipeRefreshLayout swipe_layout, int dynamicType, String lookUserId) {
        this.swipe_layout = swipe_layout;
        this.empty_layout = empty_layout;
        this.recycler_view = recycler_view;
        this.dynamicType = dynamicType;
        this.lookUserId = lookUserId;
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.SEARCH_ACTION_DYNAMIC:
                if (context.getString(R.string.dynamic).equals(commonevent.getTemp_str())) {
                    dynamicType = 1;
                } else if (context.getString(R.string.friends).equals(commonevent.getTemp_str())) {
                    dynamicType = 2;
                }
                pageNumber = 1;
                DynamicNetWork();
                break;
            case EventCode.PROSECUTION:
                codeViolationNetWork(commonevent.getTemp_str());
                break;
            case EventCode.FLOATING_WINDOW_ACTION_SEND_COMMENTS:
                if (dynamicType == commonevent.getBusObject().getType()) {
                    BusObject busObject = commonevent.getBusObject();
                    String valueF = busObject.getValueF();
                    String valueL = busObject.getValueL();
                    if (userId.equals(valueF)) {
                        sendCommentsNetWork("", valueL);
                        LogUtil.e(TAG, "sendCommentsNetWork -- no");
                    } else {
                        String[] split = valueL.split(":");
                        sendCommentsNetWork(valueF, split[1].toString().trim());
                        LogUtil.e(TAG, "sendCommentsNetWork -- off");
                    }
                }
                break;
            case EventCode.FLOATING_WINDOW_ACTION_CLOSE:
                if (floatingWindowDialog != null)
                    floatingWindowDialog.dismiss();
                break;
            case EventCode.OR_THE_DELET_DYNAMIC:
                deletControllerNetWork();
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
        albumBuilder = new AlbumBuilder(context);
        swipe_layout.setOnRefreshListener(this);
        ViewBuilder.ProgressStyleChange(swipe_layout);
        swipe_layout.setRefreshing(true);
        instance = ShowDialog.getInstance();
        initAdapter();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
        DynamicNetWork();
    }

    @Override
    public void onRefresh() {
        pageNumber = 1;
        DynamicNetWork();
    }

    private void initAdapter() {
        recycler_view.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        dynamicAdapter = new DynamicAdapter(context, list, dynamicType);
        recycler_view.setAdapter(dynamicAdapter);
        recycler_view.addOnScrollListener(scrollListener);
        dynamicAdapter.setDynamicParentClickListener(this);
    }

    private RecyclerView.OnScrollListener scrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadMore() {
            if (dynamicAdapter != null) {
                dynamicAdapter.setLoadState(dynamicAdapter.LOADING);
                if (list.size() > DataClass.DefaultInformationFlow) {
                    pageNumber = pageNumber + 1;
                    DynamicNetWork();
                } else {
                    // 显示加载到底的提示
                    dynamicAdapter.setLoadState(dynamicAdapter.LOADING_END);
                }
                if (newDynamicSize == 0 || newDynamicSize < DataClass.DefaultInformationFlow + 1) {
                    dynamicAdapter.setLoadState(dynamicAdapter.LOADING_END);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView) {

        }
    };

    //适配器刷新
    private void refreshView() {
        if (pageNumber != 1) {
            dynamicAdapter.notifyItemRangeInserted(list.size() - newDynamicSize, newDynamicSize);
        } else {
            dynamicAdapter.notifyDataSetChanged();
        }
        swipe_layout.setRefreshing(false);
        RefreshComments();
    }

    //刷新评论栏
    private void RefreshComments() {
        if (selectCommentsIndex != -1 && selectCommentsIndex < list.size()) {
            commentsList.clear();
            commentsList.addAll(list.get(selectCommentsIndex).getReply());
            if (floatingWindowDialog != null)
                floatingWindowDialog.refreshView(commentsList);
        }
    }

    //图片浏览
    @Override
    public void onChildClickListener(int position, PackingNetBeanPhoto packingNetBeanPhoto) {
        DynamicNetBean.ResultBean.NewsBean newsBean = list.get(packingNetBeanPhoto.getPosition());
        photoList.clear();
        if (newsBean.getImgarr().size() == 0) {
            photoList.add(new StringBuffer().append(DataClass.URL).append(newsBean.getImgs()).toString());
        } else {
            for (int i = 0; i < newsBean.getImgarr().size(); i++) {
                photoList.add(new StringBuffer().append(DataClass.URL).append(newsBean.getImgarr().get(i)).toString());
            }
        }
        albumBuilder.ImageTheExhibition(photoList, false, position);
    }

    //check选择
    @Override
    public void onCheckClickListener(int position, int id) {
        if (id != R.id.user_img && DataClass.USERID.isEmpty()) {
            context.startActivity(new Intent(context, LoginActivity.class));
            return;
        }
        DynamicNetBean.ResultBean.NewsBean newsBean = list.get(position);
        selectDynamicId = newsBean.getNewsid();
        userId = newsBean.getUserid();
        selectCommentsIndex = position;
        switch (id) {
            case -1:
                if (!"0".equals(newsBean.getGroupid())) {
                    Intent orderContentIntent = new Intent(context, OrderContentActivity.class);
                    orderContentIntent.putExtra("groupId", newsBean.getGroupid());
                    context.startActivity(orderContentIntent);
                }
                break;
            case R.id.user_img:
                Intent intent = new Intent(context, TheDetailsInformationActivity.class);
                intent.putExtra("userId", userId);
                context.startActivity(intent);
                break;
            case R.id.support_check:
                orSupportNetWork(position, newsBean, R.id.support_check);
                break;
            case R.id.comments:
                commentsList.clear();
                commentsList.addAll(newsBean.getReply());
                initFloatingWindowView(commentsList, newsBean.getSecondname(), newsBean.getUserid());
                break;
            case R.id.code_violation:
                instance.showInputDialog(context, context.getString(R.string.prosecution), EventCode.PROSECUTION);
                break;
        }
    }

    //删除动态
    @Override
    public void onClearCheckClickListener(int position) {
        instance.showConfirmOrNoDialog(context, context.getString(R.string.or_the_delet_dynamic), EventCode.ONSTART, EventCode.OR_THE_DELET_DYNAMIC, EventCode.ONSTART);
        DynamicNetBean.ResultBean.NewsBean newsBean = list.get(position);
        selectDynamicId = newsBean.getNewsid();
    }

    //适配器选择性属性
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void refreshItem(int position, DynamicNetBean.ResultBean.NewsBean newsBean, int id) {
        RecyclerView.ViewHolder viewHolder = recycler_view.findViewHolderForAdapterPosition(position);
        if (viewHolder != null && viewHolder instanceof MyViewHolder) {
            MyViewHolder itemHolder = (MyViewHolder) viewHolder;
            dynamicAdapter.refreshCheckView(itemHolder, newsBean, id);
        }
    }

    @Override
    public void onAppBarLayoutOffsetChanged(RecyclerView recyclerView, AppBarLayout appBarLayout, int i) {
        if (i == 0) {
            //展开状态
            LogUtil.e(TAG, "折叠");
            floatingWindowDialog.dismiss();
        } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
            //折叠
            LogUtil.e(TAG, "折叠");
        } else {
            //中间状态
            LogUtil.e(TAG, "中间状态");
        }
    }

    @Override
    public void onDismiss(Object o) {
        LogUtil.e(TAG, "关闭状态");
        RxBus.getDefault().post(new CommonEvent(EventCode.FLOATING_WINDOW_ACTION_STATUS, false));
    }

    //浮动评论窗口
    private void initFloatingWindowView(List<Object> list, String dynamicUserName, String dynamicUserId) {
        floatingWindowDialog = new FloatingWindowDialog(context, list, dynamicUserName, dynamicUserId, dynamicType);
        floatingWindowDialog.setOnAppBarLayoutOffsetChangedListener(this);
        floatingWindowDialog.setOnDismissListener(this);
        floatingWindowDialog.show();
        RxBus.getDefault().post(new CommonEvent(EventCode.FLOATING_WINDOW_ACTION_STATUS, true));
    }

    //获取动态(周边、个人)
    private void DynamicNetWork() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.NEWS_LIST_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("pagenum", pageNumber);
        linkedHashMap.put("longitude", DataClass.LONGITUDE);
        linkedHashMap.put("latitude", DataClass.LATITUDE);
        linkedHashMap.put("datatype", dynamicType);
        linkedHashMap.put("userid_view", lookUserId);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.GetDynamicNetBean(map)
                .compose(RxUtil.<DynamicNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<DynamicNetBean>(toastUtil) {
                    @Override
                    public void onNext(DynamicNetBean dynamicNetBean) {
                        if (dynamicNetBean.getStatus() == 1) {
                            DynamicNetBean.ResultBean result = dynamicNetBean.getResult();
                            List<DynamicNetBean.ResultBean.NewsBean> news = result.getNews();
                            newDynamicSize = news.size();
                            if (pageNumber == 1) {
                                list.clear();
                                if (newDynamicSize == 0) {
                                    empty_layout.setVisibility(View.VISIBLE);
                                } else {
                                    empty_layout.setVisibility(View.GONE);
                                }
                            }
                            list.addAll(news);
                            refreshView();
                        } else {
                            toastUtil.showToast(dynamicNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    //点赞、取消点赞
    private void orSupportNetWork(final int position, final DynamicNetBean.ResultBean.NewsBean newsBean, final int id) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.NEWS_ZAN_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("newsid", selectDynamicId);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.GetPraiseStatusNetBean(map)
                .compose(RxUtil.<PraiseStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<PraiseStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(PraiseStatusNetBean praiseStatusNetBean) {
                        if (praiseStatusNetBean.getStatus() == 1) {
                            newsBean.setIfzan_cleck(praiseStatusNetBean.getIfzan_cleck());
                            int total;
                            if (praiseStatusNetBean.getIfzan_cleck() == 1) {
                                total = Integer.valueOf(newsBean.getZan_total()) + 1;
                            } else {
                                total = Integer.valueOf(newsBean.getZan_total()) - 1;
                            }
                            newsBean.setZan_total(String.valueOf(total));
                            refreshItem(position, newsBean, id);
                        } else {
                            refreshItem(position, newsBean, id);
                            toastUtil.showToast(praiseStatusNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        refreshItem(position, newsBean, id);
                        super.onError(e);
                    }
                }));
    }

    //举报违规动态
    private void codeViolationNetWork(String content) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.NEWS_REPORT_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("newsid", selectDynamicId);
        linkedHashMap.put("content", content);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.UpLoadStatusNetBean(map)
                .compose(RxUtil.<UpLoadStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UpLoadStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(UpLoadStatusNetBean upLoadStatusNetBean) {
                        if (upLoadStatusNetBean.getStatus() == 1) {
                            toastUtil.showToast(context.getString(R.string.to_report_success));
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

    //发送回复
    private void sendCommentsNetWork(String selectCommentsId, String content) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.REPLY_ADD_SET);
        linkedHashMap.put("newsid", selectDynamicId);
        linkedHashMap.put("content", content);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("userid_to", selectCommentsId);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.UpLoadStatusNetBean(map)
                .compose(RxUtil.<UpLoadStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UpLoadStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(UpLoadStatusNetBean upLoadStatusNetBean) {
                        if (upLoadStatusNetBean.getStatus() == 1) {
                            toastUtil.showToast("评论成功");
                            LogUtil.e(TAG, "评论成功");
                            DynamicNetWork();
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

    //删除动态
    private void deletControllerNetWork() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.NEWS_DEL_SET);
        linkedHashMap.put("newsid", selectDynamicId);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.UpLoadStatusNetBean(map)
                .compose(RxUtil.<UpLoadStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UpLoadStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(UpLoadStatusNetBean upLoadStatusNetBean) {
                        if (upLoadStatusNetBean.getStatus() == 1) {
                            DynamicNetWork();
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

    public interface NetWorkAndClickListener {
        void onNetWorkAndClickListener();
    }

    private NetWorkAndClickListener netWorkAndClickListener;

    public void setNetWorkAndClickListener(NetWorkAndClickListener netWorkAndClickListener) {
        this.netWorkAndClickListener = netWorkAndClickListener;
    }


}
