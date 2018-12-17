package com.example.administrator.friendshape.ui.controller;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.ControllerClassObserver;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.BusObject;
import com.example.administrator.friendshape.model.bean.GroupContentNetBean;
import com.example.administrator.friendshape.model.bean.HomePageNetBean;
import com.example.administrator.friendshape.model.bean.HotSearchResNetBean;
import com.example.administrator.friendshape.model.bean.MerchantsNetBean;
import com.example.administrator.friendshape.model.bean.WechatPayContentNetBean;
import com.example.administrator.friendshape.model.bean.ZfbPayContentNetBean;
import com.example.administrator.friendshape.model.db.entity.SearchDBInfo;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.rxtools.RxUtil;
import com.example.administrator.friendshape.ui.activity.HomeActivity;
import com.example.administrator.friendshape.ui.activity.component.MerchantsContentActivity;
import com.example.administrator.friendshape.ui.adapter.MerchantsRecyclerAdapter;
import com.example.administrator.friendshape.ui.dialog.ShowDialog;
import com.example.administrator.friendshape.ui.view.FlowLayout;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.widget.CommonSubscriber;
import com.example.administrator.friendshape.widget.OnLinePayBuilder;
import com.example.administrator.friendshape.widget.ViewBuilder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * 作者：真理 Created by Administrator on 2018/10/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerHome extends ControllerClassObserver implements View.OnClickListener, View.OnKeyListener, MerchantsRecyclerAdapter.ClassPersonalityClickListener {

    EditText search;
    FlowLayout searchHistoryLayout;
    FlowLayout searchHotLayout;
    RecyclerView recycler;
    private LayoutInflater mInflater;
    private List<HomePageNetBean.ShopsBean> searchList = new ArrayList<>();
    private MerchantsRecyclerAdapter merchantsRecyclerAdapter;
    private List<HotSearchResNetBean.ResultBean> hotSearchResult;
    private OnLinePayBuilder onLinePayBuilder;

    public ControllerHome(EditText search, FlowLayout searchHistoryLayout, FlowLayout searchHotLayout, RecyclerView recycler) {
        this.search = search;
        this.searchHistoryLayout = searchHistoryLayout;
        this.searchHotLayout = searchHotLayout;
        this.recycler = recycler;
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.SEARCH_CLEAR_ALL_COMMITE:
                ClearAllSearchHistoryLog();
                break;
            case EventCode.PAY_ACTION:
                BusObject busObject = commonevent.getBusObject();
                onPayContentNetWork(busObject.getValueF(), busObject.getValueL());
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
        mInflater = LayoutInflater.from(context);
        search.setOnKeyListener(this);
        onLinePayBuilder = new OnLinePayBuilder(context);
        initAdapter();
        HotSearchRes();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    public void initAdapter() {
        recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        merchantsRecyclerAdapter = new MerchantsRecyclerAdapter(context, searchList);
        recycler.setAdapter(merchantsRecyclerAdapter);
        merchantsRecyclerAdapter.setClassPersonalityClickListener(this);
    }

    private void RefreshView() {
        if (searchList.size() == 0) {
            ((HomeActivity) context).refeshEmptystatus(true);
        } else {
            ((HomeActivity) context).refeshEmptystatus(false);
        }
        merchantsRecyclerAdapter.notifyDataSetChanged();
    }

    //清空检索历史
    private void ClearAllSearchHistoryLog() {
        dataManager.deleteSearchDBInfo(DataClass.USERID);
        HistoryAndHotSearchView();
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
            ((InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            String inputContent = search.getText().toString();
            if (inputContent.isEmpty())
                return true;
            searchNetWork(inputContent);
            dataManager.insertSearchDBInfo(new SearchDBInfo(DataClass.USERID, inputContent));
            HistoryAndHotSearchView();
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
        if (v instanceof TextView) {
            String s = ((TextView) v).getText().toString();
            search.setText("");
            searchNetWork(s);
            ViewBuilder.closeKeybord(search);
            dataManager.insertSearchDBInfo(new SearchDBInfo(DataClass.USERID, s));
            HistoryAndHotSearchView();
        }
    }

    //检索内容
    @Override
    public void onClassPersonalityClickListener(int position) {
        Intent intent = new Intent(context, MerchantsContentActivity.class);
        intent.putExtra("MerchantsId", searchList.get(position).getShopid());
        context.startActivity(intent);
    }

    //热门、历史检索标签
    public void HistoryAndHotSearchView() {
        searchHistoryLayout.removeAllViews();
        searchHotLayout.removeAllViews();
        List<SearchDBInfo> searchDBInfos = dataManager.querySearchDBInfo(DataClass.USERID);
        for (HotSearchResNetBean.ResultBean hotSearch : hotSearchResult) {
            TextView searchHotContent = (TextView) mInflater.inflate(R.layout.item_search_label, searchHotLayout, false);
            searchHotContent.setText(hotSearch.getTitle());
            searchHotLayout.addView(searchHotContent);
            searchHotContent.setOnClickListener(this);
        }
        for (int i = 0; i < searchDBInfos.size(); i++) {
            if (i > context.getResources().getInteger(R.integer.search_history_log))
                return;
            TextView searchContent = (TextView) mInflater.inflate(R.layout.item_search_label, searchHistoryLayout, false);
            searchContent.setText(searchDBInfos.get(searchDBInfos.size() - 1 - i).getSearchContent());
            searchHistoryLayout.addView(searchContent);
            searchContent.setOnClickListener(this);
        }
    }

    /**
     * 检索返回
     * 注 ： 适配器采用主页方式（商户列表一致,复用模式 故：需要对数据进行包装同首页一致）
     *
     * @param content 关键字
     */
    public void searchNetWork(String content) {
        ((HomeActivity) context).refreshSearchView(false);
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.SHOP_LIST_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("keyword", content);
        linkedHashMap.put("servicecategoryid", "");
        linkedHashMap.put("shopgroupid", "");
        linkedHashMap.put("ordertype", "");
        linkedHashMap.put("pagenum", "");
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
                            searchList.clear();
                            List<MerchantsNetBean.ResultBean.ShopsBean> shops = merchantsNetBean.getResult().getShops();
                            for (MerchantsNetBean.ResultBean.ShopsBean shopsBean : shops)
                                searchList.add(new HomePageNetBean.ShopsBean(shopsBean.getAddress(), shopsBean.getDistance(), shopsBean.getMoney_avg(), shopsBean.getPhoto(), shopsBean.getRemark(), shopsBean.getScore(), shopsBean.getShopid(), shopsBean.getShopname()));
                            RefreshView();
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

    //热门词汇
    public void HotSearchRes() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.HOT_SEARCH_GET);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.GetHotSearchResNetBean(map)
                .compose(RxUtil.<HotSearchResNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<HotSearchResNetBean>(toastUtil) {
                    @Override
                    public void onNext(HotSearchResNetBean hotSearchResNetBean) {
                        if (hotSearchResNetBean.getStatus() == 1) {
                            hotSearchResult = hotSearchResNetBean.getResult();
                            HistoryAndHotSearchView();
                        } else {
                            toastUtil.showToast(hotSearchResNetBean.getMessage());
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
     * 获取支付信息
     *
     * @param orderCode 订单编号
     * @param payType   支付类型 ： 支付宝、微信
     */
    private void onPayContentNetWork(String orderCode, String payType) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.ORDER_PAY_SET);
        linkedHashMap.put("ordercode", orderCode);
        linkedHashMap.put("paytype", payType);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        if (context.getString(R.string.wechat_pay).equals(payType)) {
            addSubscribe(dataManager.GetWechatPayContentNetBean(map)
                    .compose(RxUtil.<WechatPayContentNetBean>rxSchedulerHelper())
                    .subscribeWith(new CommonSubscriber<WechatPayContentNetBean>(toastUtil) {
                        @Override
                        public void onNext(WechatPayContentNetBean wechatPayContentNetBean) {
                            if (wechatPayContentNetBean.getStatus() == 1) {
                                WechatPayContentNetBean.PrepayinfoBean prepayinfo = wechatPayContentNetBean.getPrepayinfo();
                                onLinePayBuilder.doWeiXinPay(prepayinfo.getAppid(),
                                        prepayinfo.getPartnerid(),
                                        prepayinfo.getPrepayid(),
                                        prepayinfo.getNoncestr(),
                                        prepayinfo.getTimestamp(),
                                        prepayinfo.getPackageX(),
                                        prepayinfo.getSign());
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, "Throwable : " + e.toString());
                            super.onError(e);
                        }
                    }));
        } else if (context.getString(R.string.zfb_pay).equals(payType)) {
            addSubscribe(dataManager.GetZfbPayContentNetBean(map)
                    .compose(RxUtil.<ZfbPayContentNetBean>rxSchedulerHelper())
                    .subscribeWith(new CommonSubscriber<ZfbPayContentNetBean>(toastUtil) {
                        @Override
                        public void onNext(ZfbPayContentNetBean zfbPayContentNetBean) {
                            if (zfbPayContentNetBean.getStatus() == 1) {
                                String content = new StringBuffer().append(context.getString(R.string.app_name)).append(context.getString(R.string.tuxedo_cost)).toString();
                                ZfbPayContentNetBean.PrepayinfoBean prepayinfo = zfbPayContentNetBean.getPrepayinfo();
                                onLinePayBuilder.doAlipay(content, content,
                                        prepayinfo.getOrderCode(),
                                        prepayinfo.getUrl_notify(),
                                        prepayinfo.getPartner(),
                                        prepayinfo.getSeller(),
                                        prepayinfo.getPrivate_key(),
                                        prepayinfo.getTotalmoney());
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


}
