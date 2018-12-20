package com.example.administrator.friendshape.ui.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.ControllerClassObserver;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.HomePageNetBean;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.rxtools.RxUtil;
import com.example.administrator.friendshape.ui.activity.component.GeneralActivity;
import com.example.administrator.friendshape.ui.activity.component.MerchantsContentActivity;
import com.example.administrator.friendshape.ui.activity.component.MerchantsTypeFormActivity;
import com.example.administrator.friendshape.ui.adapter.MerchantsRecyclerAdapter;
import com.example.administrator.friendshape.ui.adapter.ClassTopAdapter;
import com.example.administrator.friendshape.ui.adapter.ClassificationAdapter;
import com.example.administrator.friendshape.ui.view.ImageSlideshow;
import com.example.administrator.friendshape.ui.view.TextSwitcherView;
import com.example.administrator.friendshape.utils.SystemUtil;
import com.example.administrator.friendshape.widget.CommonSubscriber;
import com.example.administrator.friendshape.widget.FullyGridLayoutManager;
import com.example.administrator.friendshape.widget.FullyLinearLayoutManager;
import com.example.administrator.friendshape.widget.ViewBuilder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/1.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerConcentrated extends ControllerClassObserver implements ImageSlideshow.OnItemClickListener, ClassificationAdapter.ClassIficationClickListener, ClassTopAdapter.ClassTopClickListener, MerchantsRecyclerAdapter.ClassPersonalityClickListener {

    ImageSlideshow easyBanner;
    RelativeLayout banner_layout;
    RecyclerView classificationRecycler;
    RecyclerView class_top_recycler_view;
    RecyclerView personality_recycler_view;
    TextSwitcherView text_switcher_view;
    private List<HomePageNetBean.ResultBean.BannerBean> banner;
    private HomePageNetBean.ResultBean.NewversionBean newversion;
    private List<HomePageNetBean.ResultBean.NoticeBean> notice;
    private List<HomePageNetBean.ResultBean.CategoryBean> category = new ArrayList<>();
    private ClassificationAdapter classificationAdapter;
    private List<HomePageNetBean.ResultBean.ShopTopBean> shopTop = new ArrayList<>();
    private ClassTopAdapter classTopAdapter;
    private List<HomePageNetBean.ShopsBean> shops = new ArrayList<>();
    private MerchantsRecyclerAdapter merchantsRecyclerAdapter;
    private boolean OnlyRefreshstatus;

    public ControllerConcentrated(RelativeLayout banner_layout, RecyclerView classificationRecycler, RecyclerView class_top_recycler_view, RecyclerView personality_recycler_view, TextSwitcherView text_switcher_view) {
        this.banner_layout = banner_layout;
        this.classificationRecycler = classificationRecycler;
        this.class_top_recycler_view = class_top_recycler_view;
        this.personality_recycler_view = personality_recycler_view;
        this.text_switcher_view = text_switcher_view;
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
        initImageSlideshow();
        initAdapter();
    }

    //轮播图
    private void initImageSlideshow() {
        banner_layout.removeAllViews();
        easyBanner = new ImageSlideshow(context);
        easyBanner.setOnItemClickListener(this);
        easyBanner.setDotSpace(24);
        easyBanner.setDotSize(12);
        easyBanner.setDelay(3000);
        banner_layout.addView(easyBanner);
    }

    private void initAdapter() {
        classificationRecycler.setLayoutManager(ViewBuilder.getFullyGridLayoutManager(context, false, 3));
        classificationAdapter = new ClassificationAdapter(context, category);
        classificationRecycler.setAdapter(classificationAdapter);

        class_top_recycler_view.setLayoutManager(ViewBuilder.getFullyGridLayoutManager(context, false, 2));
        classTopAdapter = new ClassTopAdapter(context, shopTop);
        class_top_recycler_view.setAdapter(classTopAdapter);

        personality_recycler_view.setLayoutManager(ViewBuilder.getFullyLinearLayoutManager(context, false));
        merchantsRecyclerAdapter = new MerchantsRecyclerAdapter(context, shops);
        personality_recycler_view.setAdapter(merchantsRecyclerAdapter);

        classificationAdapter.setClassIficationClickListener(this);
        classTopAdapter.setClassTopClickListener(this);
        merchantsRecyclerAdapter.setClassPersonalityClickListener(this);

    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    private void refreshView() {
        if (!OnlyRefreshstatus) {
            for (HomePageNetBean.ResultBean.NoticeBean noticeBean : notice) {
                text_switcher_view.RefreshView(noticeBean.getTitle());
            }
            text_switcher_view.makeView();
        }
        if (banner.size() > 0) {
            initImageSlideshow();
            easyBanner.ClearList();
            for (HomePageNetBean.ResultBean.BannerBean bannerBean : banner) {
                easyBanner.addImageUrl(new StringBuffer().append(DataClass.URL).append(bannerBean.getPhoto()).toString());
            }
            easyBanner.commit();
            OnlyRefreshstatus = !OnlyRefreshstatus;
        }
        classificationAdapter.notifyDataSetChanged();
        classTopAdapter.notifyDataSetChanged();
        merchantsRecyclerAdapter.notifyDataSetChanged();
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onItemClick(View view, int position) {
        if (banner != null && banner.size() > position) {
            HomePageNetBean.ResultBean.BannerBean bannerBean = banner.get(position);
            String intamount = bannerBean.getIntamount();
            if ("1".equals(intamount)) {
                toastUtil.showToast("跳转到新闻");
                Intent intent = new Intent(context, GeneralActivity.class);
                intent.putExtra("textInfoId", bannerBean.getTextInfoid());
                intent.setFlags(6);
                context.startActivity(intent);
            } else if ("2".equals(intamount)) {
                Intent intent = new Intent(context, MerchantsContentActivity.class);
                intent.putExtra("MerchantsId", bannerBean.getIntamount2());
                context.startActivity(intent);
                toastUtil.showToast("跳转到商铺 : " + bannerBean.getIntamount2());
            }
        }
    }

    //分类选择
    @Override
    public void onClassIficationClickListener(int positon) {
        Intent intent = new Intent(context, MerchantsTypeFormActivity.class);
        if (positon == category.size()) {
            intent.putExtra("merchantsId", "");
            intent.putExtra("merchantsName", context.getResources().getString(R.string.about));
            context.startActivity(intent);
        } else {
            intent.putExtra("merchantsId", category.get(positon).getServicecategoryid());
            intent.putExtra("merchantsName", category.get(positon).getTitle());
            context.startActivity(intent);
        }
    }

    //热门选择
    @Override
    public void onClassTopClickListener(int position) {
        Intent intent = new Intent(context, MerchantsTypeFormActivity.class);
        intent.putExtra("merchantsGroup", shopTop.get(position).getShopgroupid());
        intent.putExtra("merchantsName", context.getResources().getString(R.string.hot_merchants));
        context.startActivity(intent);
    }

    //推荐选择
    @Override
    public void onClassPersonalityClickListener(int position) {
        Intent intent = new Intent(context, MerchantsContentActivity.class);
        intent.putExtra("MerchantsId", shops.get(position).getShopid());
        context.startActivity(intent);
    }

    @Override
    protected void onClassStop() {
        super.onClassStop();
    }

    //首页数据的获取
    public void initHomePageNetWork(final int page) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.HOME_PAGE);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("versionvalue", SystemUtil.getAppVersionName(context, true));
        linkedHashMap.put("systemtype", 1);
        linkedHashMap.put("pagenum", page);
        linkedHashMap.put("longitude", DataClass.LONGITUDE);
        linkedHashMap.put("latitude", DataClass.LATITUDE);
        linkedHashMap.put("city", DataClass.CITY);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.GetHomePageNetBean(map)
                .compose(RxUtil.<HomePageNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<HomePageNetBean>(toastUtil) {
                    @Override
                    public void onNext(HomePageNetBean homePageNetBean) {
                        if (homePageNetBean.getStatus() == 1) {
                            HomePageNetBean.ResultBean result = homePageNetBean.getResult();
                            DataClass.URL = result.getRootPath();
                            banner = result.getBanner();
                            newversion = result.getNewversion();
                            notice = result.getNotice();
                            if (page == 1) {
                                category.clear();
                                shopTop.clear();
                                shops.clear();
                            }
                            category.addAll(result.getCategory());
                            shopTop.addAll(result.getShop_top());
                            shops.addAll(result.getShops());
                            refreshView();
                            if (result.getShops().size() == 0) {
                                if (concentretedRefreshListener != null)
                                    concentretedRefreshListener.ConcentretedRefreshStatus(0, newversion);
                            } else {
                                if (concentretedRefreshListener != null)
                                    concentretedRefreshListener.ConcentretedRefreshStatus(1, newversion);
                            }
                        } else {
                            toastUtil.showToast(homePageNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));

    }

    public interface ConcentretedRefreshListener {
        void ConcentretedRefreshStatus(int status, HomePageNetBean.ResultBean.NewversionBean newversionBean);
    }

    private ConcentretedRefreshListener concentretedRefreshListener;

    public void setConcentretedRefreshListener(ConcentretedRefreshListener concentretedRefreshListener) {
        this.concentretedRefreshListener = concentretedRefreshListener;
    }


}
