package com.example.administrator.friendshape.ui.controller;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RelativeLayout;

import com.example.administrator.friendshape.base.ControllerClassObserver;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.HomePageNetBean;
import com.example.administrator.friendshape.model.bean.HotSearchResNetBean;
import com.example.administrator.friendshape.model.bean.MerchantsNetBean;
import com.example.administrator.friendshape.model.bean.MerchantsTypeFormNetBean;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.rxtools.RxUtil;
import com.example.administrator.friendshape.ui.activity.HomeActivity;
import com.example.administrator.friendshape.ui.activity.component.MerchantsContentActivity;
import com.example.administrator.friendshape.ui.activity.component.MerchantsTypeFormActivity;
import com.example.administrator.friendshape.ui.adapter.MerchantsRecyclerAdapter;
import com.example.administrator.friendshape.ui.adapter.MerchantsTypeRecyclerAdapter;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.utils.SystemUtil;
import com.example.administrator.friendshape.widget.CommonSubscriber;
import com.example.administrator.friendshape.widget.FullyLinearLayoutManager;
import com.example.administrator.friendshape.widget.ViewBuilder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.Flowable;

/**
 * 作者：真理 Created by Administrator on 2018/11/5.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerMerchantsTypeForm extends ControllerClassObserver implements MerchantsTypeRecyclerAdapter.MerchantsRecyclerClickListener, MerchantsRecyclerAdapter.ClassPersonalityClickListener {

    RecyclerView recycler_view;
    RecyclerView recycler_view_select;
    private String merchantsId = "";
    private String merchantsGroup = "";
    private List<HomePageNetBean.ShopsBean> merchantsList = new ArrayList<>();
    private MerchantsRecyclerAdapter merchantsRecyclerAdapter;
    private List<MerchantsTypeFormNetBean.ResultBean> merchantsTypeList = new ArrayList<>();
    private MerchantsTypeRecyclerAdapter merchantsTypeRecyclerAdapter;
    private int pageNumber;
    private int shopsSize;

    public ControllerMerchantsTypeForm(RecyclerView recycler_view, RecyclerView recycler_view_select, String merchantsId, String merchantsGroup) {
        this.recycler_view = recycler_view;
        this.recycler_view_select = recycler_view_select;
        this.merchantsId = merchantsId;
        this.merchantsGroup = merchantsGroup;
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
        if (merchantsGroup == null)
            merchantsGroup = "";
        initAdapter();
        MerchantClassNetWork();
        MerchantsNetWork(1, "");
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();

    }

    // 注 ： 适配器采用主页方式（商户列表一致,复用模式(加载模式)）
    private void initAdapter() {
        recycler_view.setLayoutManager(ViewBuilder.getFullyLinearLayoutManager(context,false));
        recycler_view_select.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        merchantsRecyclerAdapter = new MerchantsRecyclerAdapter(context, merchantsList);
        recycler_view.setAdapter(merchantsRecyclerAdapter);
        merchantsTypeRecyclerAdapter = new MerchantsTypeRecyclerAdapter(context, merchantsTypeList);
        recycler_view_select.setAdapter(merchantsTypeRecyclerAdapter);

        merchantsTypeRecyclerAdapter.setMerchantsRecyclerClickListener(this);
        merchantsRecyclerAdapter.setClassPersonalityClickListener(this);

    }

    private void refreshView(int type) {
        switch (type) {
            case 0:
                ViewBuilder.HeightCalculation(recycler_view_select, merchantsTypeList.size(), 5);
                merchantsTypeRecyclerAdapter.notifyDataSetChanged();
                break;
            case 1:
                if (pageNumber != 1) {
                    merchantsRecyclerAdapter.notifyItemRangeInserted(merchantsList.size() - shopsSize, shopsSize);
                } else {
                    merchantsRecyclerAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    //类型选择
    @Override
    public void MerchantsRecyclerClickListener(int position) {
        merchantsId = merchantsTypeList.get(position).getServicecategoryid();
        if (merchntsTypeFormListener != null)
            merchntsTypeFormListener.onMerchntsRecyclerClickListener(merchantsTypeList.get(position).getTitle());
    }

    //商家选择
    @Override
    public void onClassPersonalityClickListener(int position) {
        Intent intent = new Intent(context, MerchantsContentActivity.class);
        intent.putExtra("MerchantsId", merchantsList.get(position).getShopid());
        context.startActivity(intent);
    }

    //商户类别
    private void MerchantClassNetWork() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.SERVICECATEGORY_LIST_GET);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.GetMerchantsTypeFormNetBean(map)
                .compose(RxUtil.<MerchantsTypeFormNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<MerchantsTypeFormNetBean>(toastUtil) {
                    @Override
                    public void onNext(MerchantsTypeFormNetBean merchantsTypeFormNetBean) {
                        if (merchantsTypeFormNetBean.getStatus() == 1) {
                            merchantsTypeList.clear();
                            merchantsTypeList.addAll(merchantsTypeFormNetBean.getResult());
                            refreshView(0);
                        } else {
                            toastUtil.showToast(merchantsTypeFormNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    /**
     * 商户列表、热门商户列表
     * 注 ： 适配器采用主页方式（商户列表一致,复用模式 故：需要对数据进行包装同首页一致）
     *
     * @param pageNumber 数据page
     * @param ordertype  商户条件评级
     */
    public void MerchantsNetWork(final int pageNumber, String ordertype) {
        this.pageNumber = pageNumber;
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.SHOP_LIST_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("keyword", "");
        linkedHashMap.put("servicecategoryid", merchantsId);
        linkedHashMap.put("shopgroupid", merchantsGroup);
        linkedHashMap.put("ordertype", ordertype);
        linkedHashMap.put("pagenum", pageNumber);
        linkedHashMap.put("longitude", DataClass.LONGITUDE);
        linkedHashMap.put("latitude", DataClass.LATITUDE);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.GetMerchantsNetBean(map)
                .compose(RxUtil.<MerchantsNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<MerchantsNetBean>(toastUtil) {
                    @Override
                    public void onNext(MerchantsNetBean merchantsNetBean) {
                        if (merchantsNetBean.getStatus() == 1) {
                            if (pageNumber == 1) {
                                merchantsList.clear();
                            }
                            List<MerchantsNetBean.ResultBean.ShopsBean> shops = merchantsNetBean.getResult().getShops();
                            shopsSize = shops.size();
                            for (MerchantsNetBean.ResultBean.ShopsBean shopsBean : shops) {
                                merchantsList.add(new HomePageNetBean.ShopsBean(shopsBean.getAddress(), shopsBean.getDistance(), shopsBean.getMoney_avg(), shopsBean.getPhoto(), shopsBean.getRemark(), shopsBean.getScore(), shopsBean.getShopid(), shopsBean.getShopname()));
                            }
                            if (merchntsTypeFormListener != null)
                                if (shops.size() == 0) {
                                    merchntsTypeFormListener.onMerchntsNetWorkRefreshClickListener(0);
                                } else if (shops.size() < 5) {
                                    merchntsTypeFormListener.onMerchntsNetWorkRefreshClickListener(3);
                                } else {
                                    merchntsTypeFormListener.onMerchntsNetWorkRefreshClickListener(1);
                                }
                            refreshView(1);
                        } else {
                            toastUtil.showToast(merchantsNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    public interface MerchntsTypeFormListener {
        void onMerchntsRecyclerClickListener(String selectTitle);

        void onMerchntsNetWorkRefreshClickListener(int status);
    }

    private MerchntsTypeFormListener merchntsTypeFormListener;

    public void setMerchntsTypeFormListener(MerchntsTypeFormListener merchntsTypeFormListener) {
        this.merchntsTypeFormListener = merchntsTypeFormListener;
    }

}
