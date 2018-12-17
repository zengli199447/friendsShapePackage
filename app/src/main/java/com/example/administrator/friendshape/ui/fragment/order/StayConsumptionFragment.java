package com.example.administrator.friendshape.ui.fragment.order;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.BaseFragment;
import com.example.administrator.friendshape.base.BaseLifecycleObserver;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.ui.controller.ControllerAllTypeOrder;

import butterknife.BindView;

/**
 * 作者：真理 Created by Administrator on 2018/10/31.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class StayConsumptionFragment extends BaseFragment{

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipe_layout;
    @BindView(R.id.empty_layout)
    TextView empty_layout;
    private ControllerAllTypeOrder controllerAllTypeOrder;

    @Override
    protected void initInject() {
         getFragmentComponent().inject(this);
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_stay_consumption;
    }

    @Override
    protected void initClass() {
        controllerAllTypeOrder = new ControllerAllTypeOrder(recyclerView,swipe_layout,empty_layout,getActivity().getString(R.string.stay_consumption));
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerAllTypeOrder;
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
