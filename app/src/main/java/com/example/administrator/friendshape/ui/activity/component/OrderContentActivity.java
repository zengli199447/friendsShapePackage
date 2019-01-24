package com.example.administrator.friendshape.ui.activity.component;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.BaseActivity;
import com.example.administrator.friendshape.base.BaseLifecycleObserver;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.OredeContentNetBean;
import com.example.administrator.friendshape.model.bean.SubmitATuxedoNetBean;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.ui.controller.ControllerOrderContent;
import com.example.administrator.friendshape.ui.dialog.ShowDialog;
import com.example.administrator.friendshape.utils.SystemUtil;
import com.example.administrator.friendshape.widget.AliChatBuilder;
import com.example.administrator.friendshape.widget.CalendarBuilder;
import com.example.administrator.friendshape.widget.ViewBuilder;

import java.text.ParseException;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/11/8.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class OrderContentActivity extends BaseActivity implements ControllerOrderContent.OrderNetWorkListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.order_img_status)
    ImageView order_img_status;
    @BindView(R.id.order_status)
    TextView order_status;
    @BindView(R.id.title_about_text)
    TextView title_about_text;

    @BindView(R.id.sponsor_img)
    ImageView sponsor_img;
    @BindView(R.id.sponsor_content)
    TextView sponsor_content;
    @BindView(R.id.activity_content)
    TextView activity_content;
    @BindView(R.id.invite_friends)
    TextView invite_friends;

    @BindView(R.id.members_of_the_group)
    TextView members_of_the_group;
    @BindView(R.id.group_recycler_view)
    RecyclerView group_recycler_view;
    @BindView(R.id.gap_boy)
    TextView gap_boy;
    @BindView(R.id.gap_girl)
    TextView gap_girl;
    @BindView(R.id.layout_gap)
    RelativeLayout layout_gap;

    @BindView(R.id.group_funds)
    TextView group_funds;
    @BindView(R.id.no_single)
    TextView no_single;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.difference)
    TextView difference;

    @BindView(R.id.in_group_chat)
    TextView in_group_chat;
    @BindView(R.id.tuxedo_immediately)
    TextView tuxedo_immediately;

    @BindView(R.id.activity_needs)
    TextView activity_needs;
    @BindView(R.id.the_contact)
    TextView the_contact;
    @BindView(R.id.the_contact_phone)
    TextView the_contact_phone;
    @BindView(R.id.merchants_content)
    TextView merchants_content;
    @BindView(R.id.merchants_address)
    TextView merchants_address;

    @BindView(R.id.the_order_no)
    TextView the_order_no;
    @BindView(R.id.a_group_time)
    TextView a_group_time;
    @BindView(R.id.a_group_successful_time)
    TextView a_group_successful_time;

    @BindView(R.id.operation_layout)
    RelativeLayout operation_layout;
    @BindView(R.id.about_operation_order)
    TextView about_operation_order;

    @BindView(R.id.layout_order)
    LinearLayout layout_order;

    private ControllerOrderContent controllerOrderContent;
    private ShowDialog showDialog;
    private String groupId;
    private String orderId;
    private int ifCanJoin;
    private AliChatBuilder aliChatBuilder;
    private OredeContentNetBean.ResultBean.GroupBean group;
    private OredeContentNetBean.ResultBean.OrderBean order;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.TUXEDO_SUCCESSFUL_FINISH:
                finish();
                break;
            case EventCode.REFRESH_ALL_ORDER:
                finish();
                break;
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_order_content;
    }

    @Override
    protected void initClass() {
        orderId = getIntent().getStringExtra("orderId");
        groupId = getIntent().getStringExtra("groupId");
        controllerOrderContent = new ControllerOrderContent(group_recycler_view, orderId, groupId);
        showDialog = ShowDialog.getInstance();
        aliChatBuilder = new AliChatBuilder(this);
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerOrderContent;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.order_content));
    }

    @Override
    protected void initListener() {
        controllerOrderContent.setOrderNetWorkListener(this);
    }

    @SuppressLint("WrongConstant")
    @OnClick({R.id.img_btn_black, R.id.phone_img, R.id.invite_friends, R.id.in_group_chat, R.id.tuxedo_immediately, R.id.about_operation_order, R.id.sponsor_img, R.id.title_about_text})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.phone_img:
                showDialog.showConfirmOrNoDialog(this, group.getShopphone(), EventCode.CALL_PHONE, EventCode.ONSTART, EventCode.ONSTART);
                break;
            case R.id.invite_friends:
                Intent intent = new Intent(this, InviteFriendsActivity.class);
                intent.putExtra("groupId", groupId);
                startActivity(intent);
                break;
            case R.id.in_group_chat:
                if (!getString(R.string.cancelled).equals(group.getState())) {
                    toastUtil.showToast("进入群聊 : " + group.getTribe_id());
                    aliChatBuilder.OpenSingleConversation(group.getTribe_id());
                }
                break;
            case R.id.tuxedo_immediately:
                if (ifCanJoin == 1) {
                    toastUtil.showToast("立即参团");
                    Intent enterTheTuxedoIntent = new Intent(this, EnterTheTuxedoActivity.class);
                    enterTheTuxedoIntent.putExtra("groupId", groupId);
                    enterTheTuxedoIntent.putExtra("freeBoyStatus", group.getIffree_boy());
                    enterTheTuxedoIntent.putExtra("freeGirlStatus", group.getIffree_girl());
                    enterTheTuxedoIntent.putExtra("MoneyAvg", group.getMoney_avg());
                    startActivity(enterTheTuxedoIntent);
                }
                break;
            case R.id.about_operation_order:
                if (about_operation_order.getText().toString().equals(getString(R.string.cancle_order))) {
                    toastUtil.showToast(getString(R.string.cancle_order));
                    Intent orderIntent = new Intent(this, CancleOrderActivity.class);
                    orderIntent.putExtra("orderId", orderId);
                    startActivity(orderIntent);
                } else if (about_operation_order.getText().toString().equals(getString(R.string.evaluation_order))) {
                    toastUtil.showToast(getString(R.string.evaluation_order));
                    Intent evaluationIntent = new Intent(this, GeneralActivity.class);
                    evaluationIntent.putExtra("orderId", orderId);
                    evaluationIntent.putExtra("merchantsPhoto", group.getShopphoto());
                    evaluationIntent.putExtra("merchantsShopname", group.getShopname());
                    evaluationIntent.setFlags(3);
                    startActivity(evaluationIntent);
                } else if (about_operation_order.getText().toString().equals(getString(R.string.for_a_refund))) {
                    toastUtil.showToast(getString(R.string.for_a_refund));
                    Intent refundIntent = new Intent(this, GeneralActivity.class);
                    refundIntent.putExtra("orderId", orderId);
                    refundIntent.setFlags(4);
                    startActivity(refundIntent);
                }
                break;
            case R.id.sponsor_img:
                Intent detailsIntent = new Intent(this, TheDetailsInformationActivity.class);
                detailsIntent.putExtra("userId", group.getUserid());
                startActivity(detailsIntent);
                break;
            case R.id.title_about_text:
                Intent aTeamIntent = new Intent(this, ATeamActivity.class);
                aTeamIntent.setFlags(1);
                aTeamIntent.putExtra("groupId", group.getGroupid());
                aTeamIntent.putExtra("merchantsId", group.getShopid());
                aTeamIntent.putExtra("merchantsName", group.getShopname());
                aTeamIntent.putExtra("merchantsImg", group.getShopphoto());
                startActivity(aTeamIntent);
                break;
        }
    }

    @Override
    public void onOrderNetWorkListener(OredeContentNetBean.ResultBean result) {
        group = result.getGroup();
        order = result.getOrder();

        Glide.with(this).load(SystemUtil.JudgeUrl(group.getPhoto())).error(R.drawable.banner_off).into(sponsor_img);

        ifCanJoin = result.getIfcanjoin();

        if (getString(R.string.cancelled).equals(group.getState())) {
            if (getString(R.string.for_a_refund).equals(order.getState_pay())) { //取消 + 订单状态为待退款
                refreshView(3);
            }
            difference.setVisibility(View.GONE);
            layout_gap.setVisibility(View.GONE);
            in_group_chat.setTextColor(getResources().getColor(R.color.gray_light_text));
            ViewBuilder.textDrawable(in_group_chat, this, R.drawable.group_chat_off_icon, 0);
            tuxedo_immediately.setText(getString(R.string.tuxedo_immediately_again));

            order_img_status.setBackground(getResources().getDrawable(R.drawable.orange_bg));
            SystemUtil.textMagicTool(this, order_status, group.getState(),
                    new StringBuffer().append(getString(R.string.cancelled_date)).append(": ").append(order.getTime_cancel())
                            .toString(), R.dimen.dp14, R.dimen.dp12, R.color.white, R.color.white, "\n\n");

        } else if (getString(R.string.stay_consumption).equals(group.getState())) {
            refreshView(0);
            order_img_status.setBackground(getResources().getDrawable(R.drawable.yellow_bg));
            order_status.setText(group.getState());
            order_status.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            order_status.setTextSize(16);
        } else if (getString(R.string.already_consumption).equals(group.getState())) {
            refreshView(1);
            order_img_status.setBackground(getResources().getDrawable(R.drawable.green_bg));
            SystemUtil.textMagicTool(this, order_status, group.getState(),
                    new StringBuffer().append(getString(R.string.consumption_date)).append(": ").append(group.getTime_used())
                            .toString(), R.dimen.dp14, R.dimen.dp12, R.color.white, R.color.white, "\n\n");
        } else {
            refreshView(0);

            order_img_status.setBackground(getResources().getDrawable(R.drawable.blue_bg));

            timingIntervalAdapter();

            if ((Integer.valueOf(group.getBoy_need()) > 0 | Integer.valueOf(group.getGirl_need()) > 0)) {
                invite_friends.setVisibility(View.VISIBLE);
            } else {
                invite_friends.setVisibility(View.GONE);
            }
        }
        try {
            SystemUtil.textMagicTool(this, sponsor_content, group.getSecondname(),
                    new StringBuffer().append(group.getSex()).append("  ").append(CalendarBuilder.getAgeByBirthday(group.getBrithday())).
                            append(getString(R.string.at_the_age)).toString(), R.dimen.dp14, R.dimen.dp12, R.color.black, R.color.gray_light_text, "\n");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        activity_content.setText(new StringBuffer().append(getString(R.string.the)).append("【").append(group.getShopname()).append("】").
                append(getString(R.string.action_content)).append(group.getActiontime()).toString());

        members_of_the_group.setText(new StringBuffer().append(group.getPeople_total_need()).append(getString(R.string.people)).toString());

        gap_boy.setText(new StringBuffer().append(group.getBoy_need()).append(getString(R.string.people)).toString());
        gap_girl.setText(new StringBuffer().append(group.getGirl_need()).append(getString(R.string.people)).toString());
        group_funds.setText(new StringBuffer().append(group.getMoney_total()).append(getString(R.string.the_yuan)).toString());

        if ("0".equals(group.getIffree_girl()) && "0".equals(group.getIffree_boy())) {
            no_single.setVisibility(View.GONE);
        } else if ("1".equals(group.getIffree_girl())) {
            ViewBuilder.textDrawable(no_single, this, R.drawable.girl_icon, 0);
        } else if ("1".equals(group.getIffree_boy())) {
            ViewBuilder.textDrawable(no_single, this, R.drawable.boy_icon, 0);
        }

        progress.setMax(Integer.valueOf(group.getMoney()));
        if (group.getMoney_need() != null && !group.getMoney_need().isEmpty()) {
            progress.setProgress(Integer.valueOf(group.getMoney()) - Integer.valueOf(group.getMoney_need()));
            String differenceContent = new StringBuffer().append("<font color='#000000'>").append(group.getMoney_need()).append(getString(R.string.the_yuan)).append("</font>").toString();
            difference.setText(Html.fromHtml(new StringBuffer().append(getString(R.string.difference)).append(differenceContent).append(getString(R.string.group_complete)).toString()));
        } else {
            if (getString(R.string.to_be_paid).equals(group.getState())) {
                progress.setProgress(0);
                String differenceContent = new StringBuffer().append("<font color='#000000'>").append(group.getMoney()).append(getString(R.string.the_yuan)).append("</font>").toString();
                difference.setText(Html.fromHtml(new StringBuffer().append(getString(R.string.difference)).append(differenceContent).append(getString(R.string.group_complete)).toString()));
            } else {
                progress.setProgress(Integer.valueOf(group.getMoney()));
                String differenceContent = new StringBuffer().append("<font color='#000000'>").append(0).append(getString(R.string.the_yuan)).append("</font>").toString();
                difference.setText(Html.fromHtml(new StringBuffer().append(getString(R.string.difference)).append(differenceContent).append(getString(R.string.group_complete)).toString()));
            }
        }


        if (ifCanJoin == 0) {
            tuxedo_immediately.setTextColor(getResources().getColor(R.color.gray_light_text));
            ViewBuilder.textDrawable(tuxedo_immediately, this, R.drawable.to_participate_in_off_icon, 0);
        } else {
            tuxedo_immediately.setTextColor(getResources().getColor(R.color.blue_bar));
            ViewBuilder.textDrawable(tuxedo_immediately, this, R.drawable.to_participate_in_on_icon, 0);
        }

        if (orderId == null || getString(R.string.cancelled).equals(order.getState())) {
            title_name.setText(getString(R.string.group_activities));
            operation_layout.setVisibility(View.GONE);
        } else {
            if (getString(R.string.stay_consumption).equals(group.getState()) || getString(R.string.to_be_paid).equals(group.getState()) || getString(R.string.stay_a_team).equals(group.getState())) {
                if (DataClass.USERID.equals(group.getUserid()))
                    if (!getString(R.string.stay_consumption).equals(group.getState()))
                        title_about_text.setText(getString(R.string.modify));
            }
            if (getString(R.string.stay_consumption).equals(group.getState())) {
                operation_layout.setVisibility(View.GONE);
            } else {
                operation_layout.setVisibility(View.VISIBLE);
            }
        }

        if (getString(R.string.stay_a_team).equals(group.getState()))
            layout_order.setVisibility(View.GONE);

        activity_needs.setText(group.getRemark());
        the_contact.setText(group.getLinkman());
        the_contact_phone.setText(group.getLinkphone());
        merchants_content.setText(group.getShopname());
        merchants_address.setText(group.getAddress());

        the_order_no.setText(new StringBuffer().append(getString(R.string.the_order_no)).append(" :   ").append(order.getOrdercode()));
        a_group_time.setText(new StringBuffer().append(getString(R.string.a_group_time)).append(" :   ").append(group.getCreatedate()));
        a_group_successful_time.setText(new StringBuffer().append(getString(R.string.a_group_successful_time)).append(" :   ").append(group.getTime_success()));

    }

    //订单操作
    private void refreshView(int status) {
        operation_layout.setVisibility(View.VISIBLE);
        switch (status) {
            case 0:
                about_operation_order.setText(getString(R.string.cancle_order));
                break;
            case 1:
                about_operation_order.setText(getString(R.string.evaluation_order));
                break;
            case 3:
                about_operation_order.setText(getString(R.string.for_a_refund));
                break;
        }
    }

    @Override
    public void onTuxedoNetWorkListener(SubmitATuxedoNetBean submitATuxedoNetBean) {

    }

    //剩余时间计时
    private void timingIntervalAdapter() {
        if (order_status == null)
            return;
        SystemUtil.textMagicTool(this, order_status, group.getState(),
                new StringBuffer().append(getString(R.string.for_the_rest_of)).append(": ").append(CalendarBuilder.getTimeDifferenceDance(new Date().getTime(), CalendarBuilder.getFormatLongMinDate(group.getEndtime())))
                        .toString(), R.dimen.dp14, R.dimen.dp12, R.color.white, R.color.white, "\n\n");
        handler.sendEmptyMessageDelayed(0, getResources().getInteger(R.integer.timing_interval));
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            timingIntervalAdapter();
        }
    };


}
