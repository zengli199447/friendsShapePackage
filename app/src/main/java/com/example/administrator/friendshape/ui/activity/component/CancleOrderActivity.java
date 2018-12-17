package com.example.administrator.friendshape.ui.activity.component;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.BaseActivity;
import com.example.administrator.friendshape.base.BaseLifecycleObserver;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.CancleGroupNetBean;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.ui.controller.ControllerCancleOrder;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.utils.SystemUtil;
import com.example.administrator.friendshape.widget.CalendarBuilder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/11/12.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class CancleOrderActivity extends BaseActivity implements ControllerCancleOrder.OrderCancleNetWorkListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.sponsor_img)
    ImageView sponsor_img;
    @BindView(R.id.sponsor_content)
    TextView sponsor_content;
    @BindView(R.id.activity_content)
    TextView activity_content;
    @BindView(R.id.order_status)
    TextView order_status;
    @BindView(R.id.the_refund_amount)
    TextView the_refund_amount;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.commite)
    TextView commite;
    @BindView(R.id.user_layout)
    LinearLayout user_layout;

    private ControllerCancleOrder controllerCancleOrder;


    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.ORDER_CANCLE_SUCCESSFUL:
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
        return R.layout.activity_cancle_order;
    }

    @Override
    protected void initClass() {
        String orderId = getIntent().getStringExtra("orderId");
        controllerCancleOrder = new ControllerCancleOrder(recycler_view, commite, orderId);
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerCancleOrder;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.cancle_order));

    }

    @Override
    protected void initListener() {
        controllerCancleOrder.setOrderCancleNetWorkListener(this);
    }

    @OnClick({R.id.commite, R.id.img_btn_black})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.commite:
                controllerCancleOrder.FilterConditions();
                break;
            case R.id.img_btn_black:
                finish();
                break;
        }
    }

    @Override
    public void onOrderCancleNetWorkListener(CancleGroupNetBean.ResultBean result) {
        CancleGroupNetBean.ResultBean.DetailBean detail = result.getDetail();
        if (result.getUsers().size() > 0)
            user_layout.setVisibility(View.VISIBLE);
        Glide.with(this).load(SystemUtil.JudgeUrl(detail.getPhoto())).error(R.drawable.banner_off).into(sponsor_img);
        SystemUtil.textMagicTool(this, sponsor_content, detail.getSecondname(),
                new StringBuffer().append(detail.getSex()).append("  ").append(detail.getAge()).
                        append(getString(R.string.at_the_age)).toString(), R.dimen.dp14, R.dimen.dp12, R.color.black, R.color.gray_light_text, "\n");

        activity_content.setText(detail.getTitle());
        order_status.setText(detail.getState());

        if (result.getUsers().size() == 0) {
            commite.setBackground(getResources().getDrawable(R.color.blue_bar));
        }

        if ("1".equals(detail.getIffree()) | !getString(R.string.have_to_pay).equals(detail.getState())) {
            the_refund_amount.setText(new StringBuffer().append(0).append(getString(R.string.the_yuan)).toString());
        } else {
            the_refund_amount.setText(new StringBuffer().append(detail.getMoneypay_online()).append(getString(R.string.the_yuan)).toString());
        }

    }


}
