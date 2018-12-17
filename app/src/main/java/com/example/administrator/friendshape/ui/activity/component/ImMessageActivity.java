package com.example.administrator.friendshape.ui.activity.component;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.BaseActivity;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.event.CommonEvent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/8.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ImMessageActivity extends BaseActivity {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.frame_layout)
    FrameLayout frame_layout;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_message;
    }

    @Override
    protected void initClass() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.my_message_form));
        if (DataClass.mIMKit != null) {
            fragmentTransaction.replace(R.id.frame_layout, DataClass.mIMKit.getConversationFragment());
            fragmentTransaction.commit();
        }
    }

    @Override
    protected void initListener() {

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

}
