package com.example.administrator.friendshape.ui.fragment.personal;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.BaseFragment;
import com.example.administrator.friendshape.base.BaseLifecycleObserver;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.ui.activity.component.TheDetailsInformationActivity;
import com.example.administrator.friendshape.ui.controller.ControllerDynamic;

import butterknife.BindView;

/**
 * 作者：真理 Created by Administrator on 2018/11/15.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class PersonalDynamicFragment extends BaseFragment implements ControllerDynamic.NetWorkAndClickListener{

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipe_layout;
    @BindView(R.id.empty_layout)
    TextView empty_layout;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    private ControllerDynamic controllerDynamic;
    private TheDetailsInformationActivity activity;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal_dynamic;
    }

    @Override
    protected void initClass() {
        activity = (TheDetailsInformationActivity) getActivity();
        controllerDynamic = new ControllerDynamic(recycler_view, empty_layout, swipe_layout, 4, activity.GetUserId());

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

    }

    public void swipeRefreshStatus(boolean status){
        swipe_layout.setEnabled(status);
    }

    @Override
    public void onNetWorkAndClickListener() {

    }


}
