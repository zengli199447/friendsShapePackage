package com.example.administrator.friendshape.ui.controller;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.ControllerClassObserver;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.GroupAboutNetBean;
import com.example.administrator.friendshape.model.bean.UpLoadStatusNetBean;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.rxtools.RxBus;
import com.example.administrator.friendshape.rxtools.RxUtil;
import com.example.administrator.friendshape.ui.adapter.NearRecyclerAdapter;
import com.example.administrator.friendshape.ui.adapter.NearRecyclerGroupAdapter;
import com.example.administrator.friendshape.ui.fragment.NearFragment;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.utils.SystemUtil;
import com.example.administrator.friendshape.widget.CommonSubscriber;
import com.example.administrator.friendshape.widget.ViewBuilder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/10/30.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerNear extends ControllerClassObserver implements NearRecyclerAdapter.NearRecyclerClickListener, NearRecyclerGroupAdapter.NearGroupRecyclerClickListener {

    RecyclerView recycler_view;
    RecyclerView recycler_view_group;
    List<GroupAboutNetBean.ResultBean.ServicecategoryBean> servicecategoryBeans = new ArrayList<>();
    private int size;
    private String combinationConditionsFirst = "";
    private String combinationConditionsLast = "";
    private String singleCombinationConditions = "";
    private int Type = 0;
    private NearRecyclerGroupAdapter nearRecyclerGroupAdapter;

    public ControllerNear(RecyclerView recycler_view, RecyclerView recycler_view_group) {
        this.recycler_view = recycler_view;
        this.recycler_view_group = recycler_view_group;
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
        initNetWork();
    }


    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    /**
     * 筛选适配器
     *
     * @param type 筛选条件
     */
    public void initScreeningAdapter(int type) {
        this.Type = type;
        switch (type) {
            case 0:
                size = Arrays.asList(context.getResources().getStringArray(R.array.dynamic)).size();
                break;
            case 1:
                size = Arrays.asList(context.getResources().getStringArray(R.array.people_nearby)).size();
                break;
            case 2:
                size = servicecategoryBeans.size();
                break;
        }
        ViewBuilder.HeightCalculation(recycler_view, size, 5);
        ViewBuilder.HeightCalculation(recycler_view_group, size, 5);
        NearRecyclerAdapter nearRecyclerAdapter = new NearRecyclerAdapter(context, type);
        nearRecyclerAdapter.setNearRecyclerClickListener(this);
        recycler_view.setAdapter(nearRecyclerAdapter);
        if (type == 2) {
            nearRecyclerGroupAdapter = new NearRecyclerGroupAdapter(context, servicecategoryBeans);
            nearRecyclerGroupAdapter.setNearGroupRecyclerClickListener(this);
            recycler_view_group.setAdapter(nearRecyclerGroupAdapter);
            recycler_view_group.setVisibility(View.VISIBLE);
        } else {
            recycler_view_group.setVisibility(View.GONE);
        }
    }

    //单条件
    @Override
    public void nearRecyclerClickListener(String s) {
        singleCombinationConditions = s;
        if (screeningListener != null) {
            initScreeningNetWork(s);
            screeningListener.onScreeningListener(s, Type);
        }
    }

    //组合1
    @Override
    public void nearRecyclerCombinationListener(String s) {
        combinationConditionsFirst = s;
        if (!combinationConditionsLast.isEmpty()) {
            String search = new StringBuffer().append(combinationConditionsFirst).append("-").append(combinationConditionsLast).toString();
            initScreeningNetWork(search);
            screeningListener.onScreeningListener(singleCombinationConditions, Type);
        }
    }

    //组合2
    @Override
    public void nearGroupRecyclerClickListener(int position, String s) {
        GroupAboutNetBean.ResultBean.ServicecategoryBean servicecategoryBean = servicecategoryBeans.get(position);
        combinationConditionsLast = servicecategoryBean.getServicecategoryid();
        singleCombinationConditions = s;
        if (!combinationConditionsFirst.isEmpty()) {
            String search = new StringBuffer().append(combinationConditionsFirst).append("-").append(combinationConditionsLast).toString();
            initScreeningNetWork(search);
            screeningListener.onScreeningListener(singleCombinationConditions, Type);
        }
    }

    /**
     * 请求筛选结果
     *
     * @param conditions 选择条件
     */
    private void initScreeningNetWork(String conditions) {
        switch (Type) {
            case 0:
                DataClass.INDEX_DYNAMIC = 0;
                RxBus.getDefault().post(new CommonEvent(EventCode.SEARCH_ACTION_DYNAMIC, conditions));
                break;
            case 1:
                DataClass.INDEX_PEOPLE_NEAR = 0;
                RxBus.getDefault().post(new CommonEvent(EventCode.SEARCH_ACTION_PEOPLE_NEAR, conditions));
                break;
            case 2:
                DataClass.INDEX_GROUP = 0;
                RxBus.getDefault().post(new CommonEvent(EventCode.SEARCH_ACTION_GROUP, conditions));
                break;
        }
    }

    /**
     * 清空组合条件
     */
    public void refreshcombinationConditions() {
        singleCombinationConditions = "";
        combinationConditionsFirst = "";
        combinationConditionsLast = "";
    }

    //请求筛选类型
    private void initNetWork() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.GROUP_LIST_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("pagenum", 1);
        linkedHashMap.put("longitude", DataClass.LONGITUDE);
        linkedHashMap.put("latitude", DataClass.LATITUDE);
        linkedHashMap.put("datatype", "");
        linkedHashMap.put("servicecategoryid", "");
        linkedHashMap.put("userid_view", "");
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.GetGroupAboutNetBean(map)
                .compose(RxUtil.<GroupAboutNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<GroupAboutNetBean>(toastUtil) {
                    @Override
                    public void onNext(GroupAboutNetBean groupAboutNetBean) {
                        if (groupAboutNetBean.getStatus() == 1) {
                            List<GroupAboutNetBean.ResultBean.ServicecategoryBean> servicecategory = groupAboutNetBean.getResult().getServicecategory();
                            servicecategoryBeans.clear();
                            servicecategoryBeans.add(new GroupAboutNetBean.ResultBean.ServicecategoryBean("-1", context.getString(R.string.all)));
                            servicecategoryBeans.addAll(servicecategory);
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

    public interface ScreeningListener {
        void onScreeningListener(String s, int type);
    }

    private ScreeningListener screeningListener;

    public void setScreeningListener(ScreeningListener screeningListener) {
        this.screeningListener = screeningListener;
    }

}
