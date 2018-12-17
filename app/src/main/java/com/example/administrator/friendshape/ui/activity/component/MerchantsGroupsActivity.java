package com.example.administrator.friendshape.ui.activity.component;

import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.BaseActivity;
import com.example.administrator.friendshape.base.BaseLifecycleObserver;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.ui.controller.ControllerGroupsAndComments;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.utils.SystemUtil;
import com.example.administrator.friendshape.widget.ViewBuilder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/11/22.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class MerchantsGroupsActivity extends BaseActivity implements NestedScrollView.OnScrollChangeListener, SwipeRefreshLayout.OnRefreshListener, ControllerGroupsAndComments.MerchantsNetWorkRefreshListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipe_layout;
    @BindView(R.id.empty_layout)
    TextView empty_layout;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.progress_bar)
    RelativeLayout progress_bar;
    @BindView(R.id.footer_layout)
    RelativeLayout footer_layout;

    private int pageNumber = 1;

    private ControllerGroupsAndComments controllerGroupsAndComments;
    private int flags;

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_merchants;
    }

    @Override
    protected void initClass() {
        String merchantsId = getIntent().getStringExtra("merchantsId");
        flags = getIntent().getFlags();
        controllerGroupsAndComments = new ControllerGroupsAndComments(recyclerView, merchantsId, flags);
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerGroupsAndComments;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        footer_layout.setVisibility(View.GONE);
        ViewBuilder.ProgressStyleChange(swipe_layout);
        switch (flags) {
            case 0:
                title_name.setText(getString(R.string.merchants_group));
                break;
            case 1:
                title_name.setText(getString(R.string.merchants_comments));
                break;
        }
    }

    @Override
    protected void initListener() {
        scrollView.setOnScrollChangeListener(this);
        controllerGroupsAndComments.setMerchantsNetWorkRefreshListener(this);
        swipe_layout.setOnRefreshListener(this);
    }

    @OnClick({R.id.img_btn_black})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
        }
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
            pageNumber = pageNumber + 1;
            controllerGroupsAndComments.NetWorkContent(pageNumber);
        }
    }

    @Override
    public void onRefresh() {
        pageNumber = 1;
        controllerGroupsAndComments.NetWorkContent(pageNumber);
    }

    @Override
    public void onMerchantsNetWorkRefreshListener(boolean status, boolean viewStatus) {
        footer_layout.setVisibility(View.VISIBLE);
        swipe_layout.setRefreshing(false);
        if (status) {
            progress_bar.setVisibility(View.VISIBLE);
        } else {
            progress_bar.setVisibility(View.GONE);
        }
        if (viewStatus) {
            empty_layout.setVisibility(View.VISIBLE);
            footer_layout.setVisibility(View.GONE);
        } else {
            empty_layout.setVisibility(View.GONE);
        }
    }


}
