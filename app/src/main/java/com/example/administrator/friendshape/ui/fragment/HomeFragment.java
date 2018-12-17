package com.example.administrator.friendshape.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.BaseFragment;
import com.example.administrator.friendshape.base.BaseLifecycleObserver;
import com.example.administrator.friendshape.model.bean.HomePageNetBean;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.ui.activity.component.GeneralActivity;
import com.example.administrator.friendshape.ui.controller.ControllerConcentrated;
import com.example.administrator.friendshape.ui.dialog.ProgressDialog;
import com.example.administrator.friendshape.ui.dialog.ShowDialog;
import com.example.administrator.friendshape.ui.view.ImageSlideshow;
import com.example.administrator.friendshape.ui.view.TextSwitcherView;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.utils.SystemUtil;
import com.example.administrator.friendshape.widget.ViewBuilder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/10/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class HomeFragment extends BaseFragment implements NestedScrollView.OnScrollChangeListener, ControllerConcentrated.ConcentretedRefreshListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.banner_layout)
    RelativeLayout banner_layout;
    @BindView(R.id.classification_recycler_view)
    RecyclerView classificationRecycler;
    @BindView(R.id.class_top_recycler_view)
    RecyclerView class_top_recycler_view;
    @BindView(R.id.personality_recycler_view)
    RecyclerView personality_recycler_view;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.progress_bar)
    RelativeLayout progress_bar;
    @BindView(R.id.footer_layout)
    RelativeLayout footer_layout;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipe_layout;
    @BindView(R.id.text_switcher_view)
    TextSwitcherView text_switcher_view;

    private ControllerConcentrated controllerConcentrated;
    private int scrollHeight;
    private int pageNumber = 1;
    private ProgressDialog progressDialog;
    private String txtInfoId = "";

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initClass() {
        controllerConcentrated = new ControllerConcentrated(banner_layout, classificationRecycler, class_top_recycler_view, personality_recycler_view, text_switcher_view);
        progressDialog = ShowDialog.getInstance().showProgressStatus(getActivity());
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerConcentrated;
    }

    @Override
    protected void initData() {
        progressDialog.show();
        controllerConcentrated.initHomePageNetWork(pageNumber);
    }

    @Override
    protected void initView() {
        footer_layout.setVisibility(View.GONE);
        ViewBuilder.ProgressStyleChange(swipe_layout);
    }

    @Override
    protected void initListener() {
        controllerConcentrated.setConcentretedRefreshListener(this);
        swipe_layout.setOnRefreshListener(this);
        scrollView.setOnScrollChangeListener(this);
    }

    @SuppressLint("WrongConstant")
    @OnClick(R.id.text_switcher_view)
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.text_switcher_view:
                toastUtil.showToast(getString(R.string.announcement_content));
                Intent intent = new Intent(getActivity(), GeneralActivity.class);
                intent.putExtra("textInfoId", txtInfoId);
                intent.setFlags(5);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
            pageNumber = pageNumber + 1;
            controllerConcentrated.initHomePageNetWork(pageNumber);
        }
    }

    @Override
    public void ConcentretedRefreshStatus(int status, HomePageNetBean.ResultBean.NewversionBean newversionBean) {
        progressDialog.dismiss();
        footer_layout.setVisibility(View.VISIBLE);
        scrollHeight = SystemUtil.dp2px(getActivity(), scrollView.getHeight());
        swipe_layout.setRefreshing(false);
        switch (status) {
            case 0:
                progress_bar.setVisibility(View.GONE);
                break;
            case 1:
                progress_bar.setVisibility(View.VISIBLE);
                break;
        }
        if (newversionBean != null)
            txtInfoId = newversionBean.getTxtinfoid();

    }

    @Override
    public void onRefresh() {
        pageNumber = 1;
        controllerConcentrated.initHomePageNetWork(pageNumber);
    }

}
