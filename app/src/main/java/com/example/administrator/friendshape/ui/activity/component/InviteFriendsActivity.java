package com.example.administrator.friendshape.ui.activity.component;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.BaseActivity;
import com.example.administrator.friendshape.base.BaseLifecycleObserver;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.ui.controller.ControllerInviteFriends;
import com.example.administrator.friendshape.ui.dialog.ShowDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/11/12.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class InviteFriendsActivity extends BaseActivity {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_about_text)
    TextView title_about_text;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.empty_layout)
    TextView empty_layout;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipe_layout;
    private ControllerInviteFriends controllerInviteFriends;


    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.SUBMITTED_SUCCESSFULLY:
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
        return R.layout.activity_invite_friends;
    }

    @Override
    protected void initClass() {
        String groupId = getIntent().getStringExtra("groupId");
        controllerInviteFriends = new ControllerInviteFriends(recycler_view, empty_layout,swipe_layout, groupId);
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerInviteFriends;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.invite_friends));
        title_about_text.setText(getString(R.string.complete));
        title_about_text.setTextColor(getResources().getColor(R.color.blue_bar));
    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.img_btn_black, R.id.title_about_text})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.title_about_text:
                controllerInviteFriends.getSelectReturn();
                break;
        }
    }


}
