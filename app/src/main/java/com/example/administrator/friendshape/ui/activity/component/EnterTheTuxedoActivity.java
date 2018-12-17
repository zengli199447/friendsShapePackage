package com.example.administrator.friendshape.ui.activity.component;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.BaseActivity;
import com.example.administrator.friendshape.base.BaseLifecycleObserver;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.BusObject;
import com.example.administrator.friendshape.model.bean.GroupAboutNetBean;
import com.example.administrator.friendshape.model.bean.SubmitATuxedoNetBean;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.rxtools.RxBus;
import com.example.administrator.friendshape.ui.controller.ControllerEnterTheTuxedo;
import com.example.administrator.friendshape.ui.dialog.ShowDialog;
import com.example.administrator.friendshape.ui.view.CustomPayPopupWindow;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.utils.SystemUtil;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/11/22.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class EnterTheTuxedoActivity extends BaseActivity implements CustomPayPopupWindow.OnItemClickListener, PopupWindow.OnDismissListener, ControllerEnterTheTuxedo.TuxedoNetWorkListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.tuxedo_status)
    TextView tuxedo_status;
    private ControllerEnterTheTuxedo controllerEnterTheTuxedo;
    private CustomPayPopupWindow customPayPopupWindow;
    private ShowDialog instance;
    private boolean status;
    private String groupid;
    private String money_avg;
    private String orderCode;
    private String freeBoyStatus;
    private String freeGirlStatus;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.TUXEDO_SUCCESSFUL_FINISH:
                finish();
                break;
            case EventCode.PAY_RETURN_STATUS:
                switch (commonevent.getTemp_value()) {
                    case 0:
                        instance.showHelpfulHintsDialog(this, getString(R.string.pay_status_no), EventCode.TUXEDO_SUCCESSFUL_FINISH);
                        break;
                    case 1:
                        instance.showHelpfulHintsDialog(this, getString(R.string.pay_status_off), EventCode.ONSTART);
                        break;
                    case 2:
                        instance.showHelpfulHintsDialog(this, getString(R.string.pay_status_ongoing), EventCode.TUXEDO_SUCCESSFUL_FINISH);
                        break;
                }
                break;
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_enter_the_tuxedo;
    }

    @Override
    protected void initClass() {
        controllerEnterTheTuxedo = new ControllerEnterTheTuxedo();
        customPayPopupWindow = new CustomPayPopupWindow(this);
        instance = ShowDialog.getInstance();
        if (!DataClass.USERID.isEmpty() && DataClass.PHONE.isEmpty()) {
            startActivity(new Intent(this, BindPhoneActivity.class).setFlags(0));
            finish();
            return;
        }
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerEnterTheTuxedo;
    }

    @Override
    protected void initData() {
        groupid = getIntent().getStringExtra("groupId");
        freeBoyStatus = getIntent().getStringExtra("freeBoyStatus");
        freeGirlStatus = getIntent().getStringExtra("freeGirlStatus");
        money_avg = getIntent().getStringExtra("MoneyAvg");

    }

    @Override
    protected void initView() {
        title_name.setText(R.string.tuxedo);
        if ("1".equals(freeGirlStatus) && DataClass.GENDER.equals(getString(R.string.gender_girl))) {
            money_avg = "";
            tuxedo_status.setText(getString(R.string.eligible_on));
        } else if ("1".equals(freeBoyStatus) && DataClass.GENDER.equals(getString(R.string.gender_boy))) {
            tuxedo_status.setText(getString(R.string.eligible_on));
            money_avg = "";
        } else {
            tuxedo_status.setText(getString(R.string.eligible_off));
        }

    }

    @Override
    protected void initListener() {
        customPayPopupWindow.setOnItemClickListener(this);
        customPayPopupWindow.setOnDismissListener(this);
        controllerEnterTheTuxedo.setTuxedoNetWorkListener(this);
    }

    @OnClick({R.id.img_btn_black, R.id.tuxedo})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.tuxedo:
                controllerEnterTheTuxedo.tuxedoNetWork(groupid, money_avg);
                break;
        }
    }

    @Override
    public void onDismiss() {
        SystemUtil.windowToLight(this);
    }

    //选择支付类型
    @Override
    public void setOnItemClick(View v, int selectType) {
        toastUtil.showToast("selectType : " + selectType);
        BusObject busObject = null;
        switch (selectType) {
            case 0:
                busObject = new BusObject(orderCode, getString(R.string.wechat_pay), null, -1);
                break;
            case 1:
                busObject = new BusObject(orderCode, getString(R.string.zfb_pay), null, -1);
                break;
        }
        RxBus.getDefault().post(new CommonEvent(EventCode.PAY_ACTION, busObject));
        customPayPopupWindow.dismiss();
    }


    @Override
    public void onTuxedoNetWorkListener(SubmitATuxedoNetBean submitATuxedoNetBean) {
        orderCode = submitATuxedoNetBean.getOrdercode();
        if ("1".equals(submitATuxedoNetBean.getNeedpay())) {
            customPayPopupWindow.showAtLocation(findViewById(R.id.tuxedo), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 100);
            SystemUtil.windowToDark(this);
            LogUtil.e(TAG, "支付");
        } else {
            instance.showHelpfulHintsDialog(this, getString(R.string.tuxedo_successful), EventCode.TUXEDO_SUCCESSFUL_FINISH);
            LogUtil.e(TAG, "无需支付");
        }
    }

}
