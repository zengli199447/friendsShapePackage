package com.example.administrator.friendshape.ui.activity.component;

import android.content.Intent;
import android.net.Network;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.BaseActivity;
import com.example.administrator.friendshape.base.BaseLifecycleObserver;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.BusObject;
import com.example.administrator.friendshape.model.bean.GroupContentNetBean;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.rxtools.RxBus;
import com.example.administrator.friendshape.ui.controller.ControllerATeam;
import com.example.administrator.friendshape.ui.dialog.ShowDialog;
import com.example.administrator.friendshape.ui.view.CustomPayPopupWindow;
import com.example.administrator.friendshape.ui.view.CustomSingleChoicePopupWindow;
import com.example.administrator.friendshape.ui.view.CustomTimeChoicePopupWindow;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.utils.SystemUtil;
import com.example.administrator.friendshape.widget.CalendarBuilder;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/11/7.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ATeamActivity extends BaseActivity implements CustomSingleChoicePopupWindow.OnItemClickListener, PopupWindow.OnDismissListener, CustomTimeChoicePopupWindow.OnItemClickListener, ControllerATeam.NetWorkRefreshListener, CustomPayPopupWindow.OnItemClickListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.the_contact)
    EditText the_contact;
    @BindView(R.id.the_contact_phone)
    EditText the_contact_phone;
    @BindView(R.id.merchants_img)
    ImageView merchants_img;
    @BindView(R.id.merchants_name)
    TextView merchants_name;
    @BindView(R.id.title_about_text)
    TextView title_about_text;

    @BindView(R.id.the_activity_time)
    TextView the_activity_time;
    @BindView(R.id.the_activity_time_select)
    ImageView the_activity_time_select;
    @BindView(R.id.the_number_of_groups)
    TextView the_number_of_groups;
    @BindView(R.id.boy_groups)
    TextView boy_groups;
    @BindView(R.id.boy_groups_no_single)
    AppCompatCheckBox boy_groups_no_single;
    @BindView(R.id.gril_groups)
    TextView gril_groups;
    @BindView(R.id.gril_groups_no_single)
    AppCompatCheckBox gril_groups_no_single;

    @BindView(R.id.group_amount)
    EditText group_amount;
    @BindView(R.id.as_of_the_date)
    TextView as_of_the_date;
    @BindView(R.id.as_of_the_date_select)
    ImageView as_of_the_date_select;
    @BindView(R.id.activity_needs)
    EditText activity_needs;
    @BindView(R.id.release_the_dynamic)
    AppCompatCheckBox release_the_dynamic;

    @BindView(R.id.pay_number)
    TextView pay_number;
    @BindView(R.id.pay_layout)
    RelativeLayout pay_layout;
    @BindView(R.id.scrollView)
    NestedScrollView nestedScrollView;

    private ControllerATeam controllerATeam;
    private String merchantsId;
    private String merchantsName;
    private String merchantsImg;
    private CustomSingleChoicePopupWindow customSingleChoicePopupWindow;

    private int boyNumber = 0;
    private int girlNumber = 0;
    private int payPeopleNumber = 0;
    private CustomTimeChoicePopupWindow customTimeChoicePopupWindow;
    private String TheActivityTime;
    private String AsOfTheDate;
    private CustomPayPopupWindow customPayPopupWindow;
    private Double perCapita;
    private int perCapitaGender = 2;
    private ShowDialog instance;
    private int flags;
    String orderCode;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.INITIATE_GROUP:
                finish();
                break;
            case EventCode.PAY_RETURN_STATUS:
                switch (commonevent.getTemp_value()) {
                    case 0:
                        instance.showHelpfulHintsDialog(this, getString(R.string.pay_status_no), EventCode.INITIATE_GROUP);
                        break;
                    case 1:
                        instance.showHelpfulHintsDialog(this, getString(R.string.pay_status_off), EventCode.ONSTART);
                        break;
                    case 2:
                        instance.showHelpfulHintsDialog(this, getString(R.string.pay_status_ongoing), EventCode.INITIATE_GROUP);
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
        return R.layout.activity_a_team;
    }

    @Override
    protected void initClass() {
        controllerATeam = new ControllerATeam(getIntent().getStringExtra("groupId"), getIntent().getFlags());
        customSingleChoicePopupWindow = new CustomSingleChoicePopupWindow(this);
        customTimeChoicePopupWindow = new CustomTimeChoicePopupWindow(this, new CalendarBuilder(Calendar.getInstance()), true);
        customPayPopupWindow = new CustomPayPopupWindow(this);
        instance = ShowDialog.getInstance();

    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerATeam;
    }

    @Override
    protected void initData() {
        flags = getIntent().getFlags();
        merchantsId = getIntent().getStringExtra("merchantsId");
        merchantsName = getIntent().getStringExtra("merchantsName");
        merchantsImg = getIntent().getStringExtra("merchantsImg");
    }

    @Override
    protected void initView() {
        switch (flags) {
            case 0:
                title_name.setText(getString(R.string.a_group));
                the_contact_phone.setText(DataClass.PHONE);
                break;
            case 1:
                title_name.setText(getString(R.string.change_group));
                pay_layout.setVisibility(View.GONE);
                title_about_text.setText(getString(R.string.commite));
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) nestedScrollView.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, 0);
                nestedScrollView.setLayoutParams(layoutParams);
                break;
        }
        the_contact.setText(DataClass.UNAME);
        Glide.with(this).load(new StringBuffer().append(DataClass.URL).append(merchantsImg).toString()).error(R.drawable.banner_off).into(merchants_img);
        merchants_name.setText(merchantsName);
    }

    @Override
    protected void initListener() {
        customSingleChoicePopupWindow.setOnItemClickListener(this);
        customSingleChoicePopupWindow.setOnDismissListener(this);
        customTimeChoicePopupWindow.setOnItemClickListener(this);
        customTimeChoicePopupWindow.setOnDismissListener(this);
        customPayPopupWindow.setOnItemClickListener(this);
        customPayPopupWindow.setOnDismissListener(this);
        group_amount.addTextChangedListener(mTextChangedListener);
        controllerATeam.setNetWorkRefreshListener(this);
    }

    @OnClick({R.id.img_btn_black, R.id.immediately_pay, R.id.boy_groups, R.id.gril_groups, R.id.the_activity_time_select,
            R.id.as_of_the_date_select, R.id.boy_groups_no_single, R.id.gril_groups_no_single, R.id.title_about_text})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.boy_groups_no_single:
                gril_groups_no_single.setChecked(false);
                if (girlNumber == 0) {
                    toastUtil.showToast(getString(R.string.bill_allocation_error));
                    gril_groups_no_single.setChecked(false);
                } else {
                    RefreshPayNumber();
                }
                break;
            case R.id.gril_groups_no_single:
                boy_groups_no_single.setChecked(false);
                if (boyNumber == 0) {
                    toastUtil.showToast(getString(R.string.bill_allocation_error));
                    gril_groups_no_single.setChecked(false);
                } else {
                    RefreshPayNumber();
                }
                break;
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.immediately_pay:
                if (DataClass.PHONE.isEmpty()) {
                    startActivity(new Intent(this, BindPhoneActivity.class).setFlags(0));
                    return;
                }
                EmptyFilter();
                break;
            case R.id.gril_groups:
                customSingleChoicePopupWindow.selectType(3, new StringBuffer().append(girlNumber).append(getString(R.string.people)).toString());
                customSingleChoicePopupWindow.showAtLocation(findViewById(R.id.immediately_pay), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                SystemUtil.windowToDark(this);
                break;
            case R.id.boy_groups:
                customSingleChoicePopupWindow.selectType(2, new StringBuffer().append(boyNumber).append(getString(R.string.people)).toString());
                customSingleChoicePopupWindow.showAtLocation(findViewById(R.id.immediately_pay), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                SystemUtil.windowToDark(this);
                break;
            case R.id.the_activity_time_select:
                customTimeChoicePopupWindow.refreshTitle(getString(R.string.the_activity_start_date), 0);
                customTimeChoicePopupWindow.showAtLocation(findViewById(R.id.immediately_pay), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                SystemUtil.windowToDark(this);
                break;
            case R.id.as_of_the_date_select:
                if (the_activity_time.getText().toString().isEmpty()) {
                    toastUtil.showToast(getString(R.string.activity_time_is_empty));
                } else {
                    customTimeChoicePopupWindow.refreshTitle(getString(R.string.select_as_of_the_date), 1);
                    customTimeChoicePopupWindow.showAtLocation(findViewById(R.id.immediately_pay), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    SystemUtil.windowToDark(this);
                }
                break;
            case R.id.title_about_text:
                EmptyFilter();
                break;
        }
    }

    @Override
    public void onDismiss() {
        SystemUtil.windowToLight(this);
    }

    //支付人员
    private void RefreshPayNumber() {
        if (boy_groups_no_single.isChecked() && !gril_groups_no_single.isChecked()) {
            payPeopleNumber = girlNumber;
            perCapitaGender = 1;
        } else if (!boy_groups_no_single.isChecked() && gril_groups_no_single.isChecked()) {
            payPeopleNumber = boyNumber;
            perCapitaGender = 0;
        } else if (!boy_groups_no_single.isChecked() && !gril_groups_no_single.isChecked()) {
            payPeopleNumber = boyNumber + girlNumber;
            perCapitaGender = 2;
        }
        PerCapitaRefresh();
    }

    //人员配置选择
    @Override
    public void setOnItemClick(int currentSelectType, String selectContent, int currentIndex) {
        switch (currentSelectType) {
            case 2:
                boyNumber = currentIndex;
                SystemUtil.textMagicTool(this, boy_groups, getString(R.string.gender_boy), new StringBuffer().append(" ").append(boyNumber).append(getString(R.string.people)).toString(), R.dimen.dp14, R.dimen.dp13, R.color.black, R.color.black_overlay, "");
                break;
            case 3:
                girlNumber = currentIndex;
                SystemUtil.textMagicTool(this, gril_groups, getString(R.string.gender_girl), new StringBuffer().append(" ").append(girlNumber).append(getString(R.string.people)).toString(), R.dimen.dp14, R.dimen.dp13, R.color.black, R.color.black_overlay, "");
                break;
        }
        the_number_of_groups.setText(new StringBuffer().append(boyNumber + girlNumber).append(getString(R.string.people)).toString());
        customSingleChoicePopupWindow.dismiss();
        RefreshPayNumber();
    }

    //人均支付
    private void PerCapitaRefresh() {
        if (payPeopleNumber > 0 && !group_amount.getText().toString().isEmpty() && SystemUtil.WhetherTheDivisible(Integer.valueOf(group_amount.getText().toString()), payPeopleNumber)) {
            Double aDouble = Double.valueOf(group_amount.getText().toString());
            perCapita = aDouble / (payPeopleNumber);
            SystemUtil.textMagicTool(ATeamActivity.this, pay_number, getString(R.string.pay_number), new StringBuffer().append("  ").append(perCapita).toString(), R.dimen.dp14, R.dimen.dp12, R.color.black, R.color.gray_light_text, "");
        } else {
            SystemUtil.textMagicTool(ATeamActivity.this, pay_number, getString(R.string.pay_number), new StringBuffer().append("  ").append("0.0").toString(), R.dimen.dp14, R.dimen.dp12, R.color.black, R.color.gray_light_text, "");
        }
        switch (perCapitaGender) {
            case 0:
                if (DataClass.GENDER.equals(getString(R.string.gender_girl))) {
                    SystemUtil.textMagicTool(ATeamActivity.this, pay_number, getString(R.string.pay_number), new StringBuffer().append("  ").append("0.0").toString(), R.dimen.dp14, R.dimen.dp12, R.color.black, R.color.gray_light_text, "");
                    perCapita = 0.0;
                }
                break;
            case 1:
                if (DataClass.GENDER.equals(getString(R.string.gender_boy))) {
                    SystemUtil.textMagicTool(ATeamActivity.this, pay_number, getString(R.string.pay_number), new StringBuffer().append("  ").append("0.0").toString(), R.dimen.dp14, R.dimen.dp12, R.color.black, R.color.gray_light_text, "");
                    perCapita = 0.0;
                }
                break;
        }
    }

    //相关时间选择
    @Override
    public void setOnItemClick(View v, String year, String month, String day, String hour, String minute, int selectType) {
        switch (selectType) {
            case 0:
                TheActivityTime = new StringBuffer().append(year).append("-").append(month).append("-").append(day).append("  ").append(hour).append(":").append(minute).toString();
                if (new Date().getTime() < CalendarBuilder.getFormatLongDate(TheActivityTime)) {
                    the_activity_time.setText(TheActivityTime);
                    as_of_the_date.setText("");
                } else {
                    toastUtil.showToast(getString(R.string.the_activity_time_min));
                }
                break;
            case 1:
                AsOfTheDate = new StringBuffer().append(year).append("-").append(month).append("-").append(day).append("  ").append(hour).append(":").append(minute).toString();
                if (new Date().getTime() > CalendarBuilder.getFormatLongDate(AsOfTheDate)) {
                    toastUtil.showToast(getString(R.string.activity_time_is_min_min));
                } else if (CalendarBuilder.getFormatLongDate(AsOfTheDate) < CalendarBuilder.getFormatLongDate(TheActivityTime)) {
                    as_of_the_date.setText(AsOfTheDate);
                } else {
                    toastUtil.showToast(getString(R.string.activity_time_is_min));
                }
                break;
        }
        customTimeChoicePopupWindow.dismiss();
    }

    //支付类型选择
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

    //发起成功
    @Override
    public void onNetWorkRefreshListener(String needPayStatus, String orderId, String orderCode) {
        LogUtil.e(TAG, "needPayStatus : " + needPayStatus);
        this.orderCode = orderCode;
        if ("1".equals(needPayStatus)) {
            LogUtil.e(TAG, "支付");
            customPayPopupWindow.showAtLocation(findViewById(R.id.immediately_pay), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            SystemUtil.windowToDark(this);
        } else if ("0".equals(needPayStatus)) {
            instance.showHelpfulHintsDialog(this, getString(R.string.initiate_group_successful), EventCode.INITIATE_GROUP);
            LogUtil.e(TAG, "无需支付");
        } else {
            instance.showHelpfulHintsDialog(this, getString(R.string.change_group_successful), EventCode.INITIATE_GROUP);
            LogUtil.e(TAG, "修改成功");
        }
    }

    @Override
    public void onGroupContentListener(GroupContentNetBean.ResultBean.GroupBean groupBean) {
        the_contact_phone.setText(groupBean.getLinkphone());
        the_contact.setText(groupBean.getLinkman());
        the_activity_time.setText(groupBean.getActiontime());
        the_number_of_groups.setText(groupBean.getPeople_total());
        boy_groups.setText(groupBean.getPeople_boy());
        gril_groups.setText(groupBean.getPeople_girl());

        boyNumber = Integer.valueOf(groupBean.getPeople_boy());
        girlNumber = Integer.valueOf(groupBean.getPeople_girl());
        SystemUtil.textMagicTool(this, boy_groups, getString(R.string.gender_boy), new StringBuffer().append(" ").append(boyNumber).append(getString(R.string.people)).toString(), R.dimen.dp14, R.dimen.dp13, R.color.black, R.color.black_overlay, "");
        SystemUtil.textMagicTool(this, gril_groups, getString(R.string.gender_girl), new StringBuffer().append(" ").append(girlNumber).append(getString(R.string.people)).toString(), R.dimen.dp14, R.dimen.dp13, R.color.black, R.color.black_overlay, "");
        the_number_of_groups.setText(new StringBuffer().append(boyNumber + girlNumber).append(getString(R.string.people)).toString());
        RefreshPayNumber();

        group_amount.setText(groupBean.getMoney());
        as_of_the_date.setText(groupBean.getEndtime());
        activity_needs.setText(groupBean.getRemark());

        refreshCheckView(boy_groups_no_single, groupBean.getIffree_boy());
        refreshCheckView(gril_groups_no_single, groupBean.getIffree_girl());
        refreshCheckView(release_the_dynamic, groupBean.getIftoaction());
    }

    private TextWatcher mTextChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            PerCapitaRefresh();
        }
    };

    //过滤提交
    private void EmptyFilter() {
        if (boyNumber + girlNumber == 0) {
            toastUtil.showToast(getString(R.string.people_is_empty));
        } else if (the_activity_time.getText().toString().isEmpty()) {
            toastUtil.showToast(getString(R.string.activity_time_is_empty));
        } else if (as_of_the_date.getText().toString().isEmpty()) {
            toastUtil.showToast(getString(R.string.select_as_of_the_date));
        } else if (the_contact.getText().toString().isEmpty()) {
            toastUtil.showToast(getString(R.string.the_contact_empty));
        } else if (the_contact_phone.getText().toString().isEmpty() | !SystemUtil.isPhotoNumberLegal(the_contact_phone.getText().toString())) {
            toastUtil.showToast(getString(R.string.the_contact_phone_empty));
        } else if (group_amount.getText().toString().isEmpty()) {
            toastUtil.showToast(getString(R.string.group_amount_empty));
        } else if (Integer.valueOf(group_amount.getText().toString()) < getResources().getInteger(R.integer.the_minimum_amount)) {
            toastUtil.showToast(getString(R.string.group_amount_min));
        } else if (!SystemUtil.WhetherTheDivisible(Integer.valueOf(group_amount.getText().toString()), payPeopleNumber)) {
            toastUtil.showToast(getString(R.string.multiple_error));
        } else {
            controllerATeam.ATeamNetWorkRefresh(merchantsId, the_contact.getText().toString(), the_contact_phone.getText().toString(),
                    the_activity_time.getText().toString(), boyNumber, isCheckStatus(boy_groups_no_single), girlNumber, isCheckStatus(gril_groups_no_single),
                    group_amount.getText().toString(), as_of_the_date.getText().toString(), activity_needs.getText().toString(), isCheckStatus(release_the_dynamic), perCapita);
        }
    }

    private int isCheckStatus(AppCompatCheckBox appCompatCheckBox) {
        int checked;
        if (appCompatCheckBox.isChecked()) {
            checked = 1;
        } else {
            checked = 0;
        }
        return checked;
    }

    private void refreshCheckView(AppCompatCheckBox view, String status) {
        if ("1".equals(status)) {
            view.setChecked(true);
        } else {
            view.setChecked(false);
        }
    }

}
