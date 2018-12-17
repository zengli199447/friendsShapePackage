package com.example.administrator.friendshape.ui.fragment.near;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.BaseFragment;
import com.example.administrator.friendshape.base.BaseLifecycleObserver;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.ui.controller.ControllerDynamic;
import com.example.administrator.friendshape.widget.CommentsLayoutViewBuilder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/10/30.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class DynamicFragment extends BaseFragment implements ControllerDynamic.NetWorkAndClickListener {

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipe_layout;
    @BindView(R.id.empty_layout)
    TextView empty_layout;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    private ControllerDynamic controllerDynamic;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dynamic;
    }

    @Override
    protected void initClass() {
        controllerDynamic = new ControllerDynamic(recycler_view, empty_layout, swipe_layout,1,"");
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerDynamic;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        controllerDynamic.setNetWorkAndClickListener(this);
    }

    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {

        }
    }

    @Override
    public void onNetWorkAndClickListener() {

    }

}
