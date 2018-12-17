package com.example.administrator.friendshape.ui.activity.component;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.BaseActivity;
import com.example.administrator.friendshape.base.BaseLifecycleObserver;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.ui.controller.ControllerDynamic;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/10/31.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class MyDynamicActivity extends BaseActivity {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_about_img)
    ImageView title_about_img;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipe_layout;
    @BindView(R.id.empty_layout)
    TextView empty_layout;
    private ControllerDynamic controllerDynamic;

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_my_dynamic;
    }

    @Override
    protected void initClass() {
        controllerDynamic = new ControllerDynamic(recycler_view, empty_layout, swipe_layout, 3, DataClass.USERID);
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
        title_name.setText(getString(R.string.my_dynamic));
        title_about_img.setImageDrawable(getResources().getDrawable(R.drawable.input_icon));
    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.title_about_img, R.id.img_btn_black})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.title_about_img:
                startActivity(new Intent(this, ReleaseNewDynamicActivity.class));
                break;
            case R.id.img_btn_black:
                finish();
                break;
        }
    }

}
