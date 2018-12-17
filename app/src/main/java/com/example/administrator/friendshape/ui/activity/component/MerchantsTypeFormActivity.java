package com.example.administrator.friendshape.ui.activity.component;

import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.BaseActivity;
import com.example.administrator.friendshape.base.BaseLifecycleObserver;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.ui.controller.ControllerMerchantsTypeForm;
import com.example.administrator.friendshape.widget.ViewBuilder;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/11/5.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class MerchantsTypeFormActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, NestedScrollView.OnScrollChangeListener, SwipeRefreshLayout.OnRefreshListener, ControllerMerchantsTypeForm.MerchntsTypeFormListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.type_select)
    TextView type_select;
    @BindView(R.id.group_view)
    RadioGroup group_view;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.select_content_layout)
    RelativeLayout select_content_layout;
    @BindView(R.id.recycler_view_select)
    RecyclerView recycler_view_select;

    @BindView(R.id.scroll_view)
    NestedScrollView scroll_view;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipe_layout;
    @BindView(R.id.footer_layout)
    RelativeLayout footer_layout;
    @BindView(R.id.progress_bar)
    RelativeLayout progress_bar;

    @BindView(R.id.title_bar)
    LinearLayout title_bar;
    @BindView(R.id.line)
    View line;

    boolean typeSelectOpenStatus;
    private String merchantsId;
    private String merchantsName;
    private String merchantsGroup;
    private ControllerMerchantsTypeForm controllerMerchantsTypeForm;

    int pageNumber = 1;
    String reason = "";


    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_merchants_type_form;
    }

    @Override
    protected void initClass() {
        merchantsId = getIntent().getStringExtra("merchantsId");
        merchantsName = getIntent().getStringExtra("merchantsName");
        merchantsGroup = getIntent().getStringExtra("merchantsGroup");
        controllerMerchantsTypeForm = new ControllerMerchantsTypeForm(recycler_view, recycler_view_select, merchantsId, merchantsGroup);
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerMerchantsTypeForm;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        title_name.setText(merchantsName);
        footer_layout.setVisibility(View.GONE);
        ViewBuilder.ProgressStyleChange(swipe_layout);
        if (merchantsGroup != null) {
            title_bar.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        }
        if (getString(R.string.about).equals(merchantsName)) {
            type_select.setText(getString(R.string.all));
        } else {
            type_select.setText(merchantsName);
        }

    }

    @Override
    protected void initListener() {
        group_view.setOnCheckedChangeListener(this);
        scroll_view.setOnScrollChangeListener(this);
        swipe_layout.setOnRefreshListener(this);
        controllerMerchantsTypeForm.setMerchntsTypeFormListener(this);
    }

    @OnClick({R.id.img_btn_black, R.id.type_select, R.id.select_content_layout})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.type_select:
                if (typeSelectOpenStatus) {
                    refreshSelectView(0);
                } else {
                    refreshSelectView(1);
                }
                break;
            case R.id.select_content_layout:
                refreshSelectView(0);
                break;
        }
    }

    //类型栏状态刷新
    public void refreshSelectView(int status) {
        switch (status) {
            case 0:
                ViewBuilder.textDrawable(type_select, this, R.drawable.down_icon, 2);
                select_content_layout.setVisibility(View.GONE);
                break;
            case 1:
                ViewBuilder.textDrawable(type_select, this, R.drawable.up_icon, 2);
                select_content_layout.setVisibility(View.VISIBLE);
                break;
        }
        typeSelectOpenStatus = !typeSelectOpenStatus;
    }

    //属性数据
    public void RefreshNetWorkData() {
        controllerMerchantsTypeForm.MerchantsNetWork(pageNumber, reason);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        pageNumber = 1;
        switch (checkedId) {
            case R.id.score:
                reason = "1";
                break;
            case R.id.per_capita_concise:
                reason = "2";
                break;
            case R.id.distance:
                reason = "3";
                break;
        }
        RefreshNetWorkData();
    }


    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
            pageNumber = pageNumber + 1;
            RefreshNetWorkData();
        }
    }

    @Override
    public void onRefresh() {
        pageNumber = 1;
        RefreshNetWorkData();
    }


    @Override
    public void onMerchntsRecyclerClickListener(String title) {
        type_select.setText(title);
        title_name.setText(title);
        refreshSelectView(0);
        RefreshNetWorkData();
    }

    @Override
    public void onMerchntsNetWorkRefreshClickListener(int status) {
        footer_layout.setVisibility(View.VISIBLE);
        switch (status) {
            case 0:
                progress_bar.setVisibility(View.GONE);
                swipe_layout.setRefreshing(false);
                break;
            case 1:
                progress_bar.setVisibility(View.VISIBLE);
                swipe_layout.setRefreshing(false);
                break;
            case 2:
                swipe_layout.setRefreshing(true);
                break;
            case 3:
                progress_bar.setVisibility(View.GONE);
                swipe_layout.setRefreshing(false);
                break;
        }
    }


}
