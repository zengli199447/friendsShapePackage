package com.example.administrator.friendshape.ui.fragment.near;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.BaseFragment;
import com.example.administrator.friendshape.base.BaseLifecycleObserver;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.ui.controller.ControllerPeopleNearby;

import butterknife.BindView;

/**
 * 作者：真理 Created by Administrator on 2018/10/30.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class PeopleNearbyFragment extends BaseFragment {

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipe_layout;
    @BindView(R.id.empty_layout)
    TextView empty_layout;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    private ControllerPeopleNearby controllerPeopleNearby;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_people_nearby;
    }

    @Override
    protected void initClass() {
        controllerPeopleNearby = new ControllerPeopleNearby(recycler_view, empty_layout, swipe_layout);
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerPeopleNearby;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onClickAble(View view) {

    }

}
