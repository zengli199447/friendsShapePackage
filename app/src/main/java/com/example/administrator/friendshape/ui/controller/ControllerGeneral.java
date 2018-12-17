package com.example.administrator.friendshape.ui.controller;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.ControllerClassObserver;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.CallPhoneNetBean;
import com.example.administrator.friendshape.model.bean.OredeContentNetBean;
import com.example.administrator.friendshape.model.bean.OtherContentNetBean;
import com.example.administrator.friendshape.model.bean.RefundNetBean;
import com.example.administrator.friendshape.model.bean.UpLoadStatusNetBean;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.rxtools.RxUtil;
import com.example.administrator.friendshape.ui.adapter.ServicePhoneAdapter;
import com.example.administrator.friendshape.ui.dialog.ShowDialog;
import com.example.administrator.friendshape.widget.CommonSubscriber;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/10/31.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerGeneral extends ControllerClassObserver implements ServicePhoneAdapter.onCallClickListener {

    RecyclerView service_recycler_view;
    int Flag;
    String orderId;
    String textInfoId;
    private ServicePhoneAdapter servicePhoneAdapter;
    private List<CallPhoneNetBean.ResultBean> ServicePhoneList = new ArrayList<>();
    private ShowDialog instance;

    public ControllerGeneral(RecyclerView service_recycler_view, int Flag, String orderId,String textInfoId) {
        this.service_recycler_view = service_recycler_view;
        this.Flag = Flag;
        this.orderId = orderId;
        this.textInfoId = textInfoId;
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
        instance = ShowDialog.getInstance();
        initServicePhoneAdapter();
        switch (Flag) {
            case 0:
                otherNetWork(4);
                break;
            case 1:
                break;
            case 2:
                initServiceNetWork();
                break;
            case 3:
                break;
            case 4:
                initServiceNetWork();
                RefundNetWork();
                break;
            case 5:
            case 6:
                otherContentNetWork(textInfoId);
                break;
        }
    }

    public void initServicePhoneAdapter() {
        service_recycler_view.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        servicePhoneAdapter = new ServicePhoneAdapter(context, ServicePhoneList);
        service_recycler_view.setAdapter(servicePhoneAdapter);
        servicePhoneAdapter.setOnCallClickListener(this);
    }

    @Override
    public void onCallClick(int positon) {
        instance.showConfirmOrNoDialog(context, ServicePhoneList.get(positon).getTitle(), EventCode.CALL_PHONE, EventCode.ONSTART, EventCode.ONSTART);
    }

    private void refreshView() {
        servicePhoneAdapter.notifyDataSetChanged();
    }

    /**
     * 网络请求(客服电话)
     */
    public void initServiceNetWork() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.SERVICEPHONE_GET);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.GetCallPhoneNetBean(map)
                .compose(RxUtil.<CallPhoneNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<CallPhoneNetBean>(toastUtil) {
                    @Override
                    public void onNext(CallPhoneNetBean callPhoneNetBean) {
                        if (callPhoneNetBean.getStatus() == 1) {
                            List<CallPhoneNetBean.ResultBean> callPhoneNetBeanResult = callPhoneNetBean.getResult();
                            ServicePhoneList.addAll(callPhoneNetBeanResult);
                            refreshView();
                        } else {
                            toastUtil.showToast(callPhoneNetBean.getMessage());
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
     * 说明性内容  4.帮助 7.注册协议
     */
    public void otherNetWork(int type) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.TEXTINFO_GET);
        linkedHashMap.put("infotype", type);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.GetOtherContentNetBean(map)
                .compose(RxUtil.<OtherContentNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<OtherContentNetBean>(toastUtil) {
                    @Override
                    public void onNext(OtherContentNetBean otherContentNetBean) {
                        OtherContentNetBean.ResultBean result = otherContentNetBean.getResult();
                        if (otherContentNetBean.getStatus() == 1) {
                            if (generalNetWorkBeanListener != null)
                                generalNetWorkBeanListener.onGeneralNetWorkBeanListener(result);
                        } else {
                            toastUtil.showToast(otherContentNetBean.getMessage());
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
     * 文本内容 公告、推荐
     */
    public void otherContentNetWork(String textInfoId) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.TEXTINFO_DETAIL_GET);
        linkedHashMap.put("textInfoid", textInfoId);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.GetOtherContentNetBean(map)
                .compose(RxUtil.<OtherContentNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<OtherContentNetBean>(toastUtil) {
                    @Override
                    public void onNext(OtherContentNetBean otherContentNetBean) {
                        OtherContentNetBean.ResultBean result = otherContentNetBean.getResult();
                        if (otherContentNetBean.getStatus() == 1) {
                            if (generalNetWorkBeanListener != null)
                                generalNetWorkBeanListener.onGeneralNetWorkBeanListener(result);
                        } else {
                            toastUtil.showToast(otherContentNetBean.getMessage());
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
     * 评价提交
     *
     * @param evaluationMark    评分
     * @param evaluationContent 附加内容
     */
    public void evaluationNetWork(float evaluationMark, String evaluationContent) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.ORDER_COMMENTS_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("orderid", orderId);
        linkedHashMap.put("remark", evaluationContent);
        linkedHashMap.put("score", evaluationMark);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.UpLoadStatusNetBean(map)
                .compose(RxUtil.<UpLoadStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UpLoadStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(UpLoadStatusNetBean upLoadStatusNetBean) {
                        if (upLoadStatusNetBean.getStatus() == 1) {
                            instance.showHelpfulHintsDialog(context, context.getString(R.string.evaluation_successful), EventCode.EVALUATION_SUCCESSFUL);
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

    /**
     * 退款详情
     */
    public void RefundNetWork() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.ORDER_CANCEL_DETAIL_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("orderid", orderId);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.GetRefundNetBean(map)
                .compose(RxUtil.<RefundNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<RefundNetBean>(toastUtil) {
                    @Override
                    public void onNext(RefundNetBean refundNetBean) {
                        if (refundNetBean.getStatus() == 1) {
                            RefundNetBean.ResultBean result = refundNetBean.getResult();
                            if (generalNetWorkBeanListener != null)
                                generalNetWorkBeanListener.onGeneralNetWorkBeanListener(result);
                        } else {
                            toastUtil.showToast(refundNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    public interface GeneralNetWorkBeanListener {
        void onGeneralNetWorkBeanListener(Object o);


    }

    private GeneralNetWorkBeanListener generalNetWorkBeanListener;

    public void setGeneralNetWorkBeanListener(GeneralNetWorkBeanListener generalNetWorkBeanListener) {
        this.generalNetWorkBeanListener = generalNetWorkBeanListener;
    }


}
