package com.example.administrator.friendshape.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.BaseActivity;
import com.example.administrator.friendshape.base.BaseLifecycleObserver;
import com.example.administrator.friendshape.global.AppKeyConfig;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.db.entity.LoginUserInfo;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.rxtools.RxBus;
import com.example.administrator.friendshape.ui.activity.component.CityScreeningActivity;
import com.example.administrator.friendshape.ui.activity.component.ImMessageActivity;
import com.example.administrator.friendshape.ui.activity.component.PersonalActivity;
import com.example.administrator.friendshape.ui.activity.component.ReleaseNewDynamicActivity;
import com.example.administrator.friendshape.ui.controller.ControllerHome;
import com.example.administrator.friendshape.ui.dialog.ConfirmOrNoDialog;
import com.example.administrator.friendshape.ui.dialog.FloatingWindowDialog;
import com.example.administrator.friendshape.ui.dialog.ShowDialog;
import com.example.administrator.friendshape.ui.fragment.ChatFragment;
import com.example.administrator.friendshape.ui.fragment.HomeFragment;
import com.example.administrator.friendshape.ui.fragment.MineFragment;
import com.example.administrator.friendshape.ui.fragment.NearFragment;
import com.example.administrator.friendshape.ui.fragment.OrderFragment;
import com.example.administrator.friendshape.ui.view.FlowLayout;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.utils.SystemUtil;
import com.example.administrator.friendshape.widget.AliChatBuilder;
import com.example.administrator.friendshape.widget.Constants;
import com.example.administrator.friendshape.widget.ViewBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * 作者：真理 Created by Administrator on 2018/10/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class HomeActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.layout_title_bar)
    RelativeLayout layout_title_bar;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_about_text)
    TextView title_about_text;
    @BindView(R.id.title_about_img)
    ImageView title_about_img;

    @BindView(R.id.layout_search_bar)
    RelativeLayout layout_search_bar;
    @BindView(R.id.layout_fl)
    FrameLayout layout_fl;
    @BindView(R.id.group_view)
    RadioGroup group_view;
    @BindView(R.id.location)
    TextView location;

    @BindView(R.id.layout_search_content_bar)
    LinearLayout layout_search_content_bar;
    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.search_history_layout)
    FlowLayout search_history_layout;
    @BindView(R.id.search_hot_layout)
    FlowLayout search_hot_layout;

    @BindView(R.id.search_result)
    LinearLayout search_result;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.empty_layout)
    RelativeLayout empty_layout;

    private long exitTime = 0;
    private int showFragment = Constants.HOME;
    private int hideFragment = Constants.HOME;
    private HomeFragment homeFragment;
    private NearFragment nearFragment;
    private ChatFragment chatFragment;
    private OrderFragment orderFragment;
    private MineFragment mineFragment;
    private ControllerHome controllerHome;
    private boolean returnStatus;
    private ShowDialog showDialog;
    private boolean ShowFloatingWindowDialogStatus;
    private AliChatBuilder aliChatBuilder;
    private int oldSelect = R.id.home;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
            hideFragment = showFragment;
        }
    }

    private SupportFragment getTargetFragment(int item) {
        switch (item) {
            case Constants.HOME:
                return homeFragment;
            case Constants.NEAR:
                return nearFragment;
            case Constants.CHAT:
                return chatFragment;
            case Constants.ORDER:
                return orderFragment;
            case Constants.MINE:
                return mineFragment;
        }
        return homeFragment;
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.FLOATING_WINDOW_ACTION_STATUS:
                returnStatus = commonevent.isTemp_boolean();
                ShowFloatingWindowDialogStatus = commonevent.isTemp_boolean();
                break;
            case EventCode.OR_LOGIN_OUT:
                DataClass.ClearUserInfo(dataManager);
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case EventCode.REFRESH_IM_STATUS:
                refreshImStatus();
                break;
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_home;
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void initClass() {
        controllerHome = new ControllerHome(search, search_history_layout, search_hot_layout, recycler);
        showDialog = ShowDialog.getInstance();
        aliChatBuilder = new AliChatBuilder(this);
        if (!DataClass.USERID.isEmpty()) {
            refreshImStatus();
            if (DataClass.GENDER.isEmpty())
                startActivity(new Intent(this, PersonalActivity.class));
        }
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerHome;
    }

    @Override
    protected void initData() {
        SystemUtil.initWindowsManagerWidth(this);
    }

    @Override
    protected void initView() {
        homeFragment = new HomeFragment();
        nearFragment = new NearFragment();
        chatFragment = new ChatFragment();
        orderFragment = new OrderFragment();
        mineFragment = new MineFragment();
        loadMultipleRootFragment(R.id.layout_fl, 0, homeFragment, nearFragment, chatFragment, orderFragment, mineFragment);
        title_about_img.setImageDrawable(getResources().getDrawable(R.drawable.input_icon));
        title_about_text.setText(getString(R.string.login_out));
        layout_title_bar.setVisibility(View.GONE);
        img_btn_black.setVisibility(View.GONE);
        layout_search_bar.setVisibility(View.VISIBLE);
        View decorView = getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(ViewBuilder.getGlobalLayoutListener(decorView, findViewById(Window.ID_ANDROID_CONTENT)));
    }

    @Override
    protected void initListener() {
        group_view.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        location.setText(DataClass.CITY);
        if (!DataClass.USERID.isEmpty() && DataClass.GENDER.isEmpty())
            startActivity(new Intent(this, PersonalActivity.class).setFlags(0));
    }

    @OnClick({R.id.message, R.id.location, R.id.search_layout, R.id.cancel, R.id.clear_search, R.id.title_about_img, R.id.title_about_text, R.id.layout_search_content_bar})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.message:
                startActivity(new Intent(this, ImMessageActivity.class));
                break;
            case R.id.location:
                startActivity(new Intent(this, CityScreeningActivity.class));
                break;
            case R.id.search_layout:
                layout_search_content_bar.setVisibility(View.VISIBLE);
                ViewBuilder.showKeyboard(search);
                returnStatus = true;
                break;
            case R.id.cancel:
                if (recycler.getVisibility() == View.GONE) {
                    layout_search_content_bar.setVisibility(View.GONE);
                    returnStatus = false;
                    ViewBuilder.closeKeybord(search);
                } else {
                    refreshSearchView(true);
                }
                break;
            case R.id.clear_search:
                showDialog.showConfirmOrNoDialog(this, getString(R.string.or_empty_serach_history), EventCode.ONSTART, EventCode.SEARCH_CLEAR_ALL_COMMITE, EventCode.ONSTART);
                break;
            case R.id.title_about_img:
                startActivity(new Intent(this, ReleaseNewDynamicActivity.class));
                break;
            case R.id.title_about_text:
                showDialog.showConfirmOrNoDialog(this, getString(R.string.or_login_out), EventCode.ONSTART, EventCode.OR_LOGIN_OUT, EventCode.ONSTART);
                break;
            case R.id.layout_search_content_bar:
                break;
        }
    }

    //检索视图刷新
    public void refreshSearchView(boolean status) {
        if (status) {
            search_result.setVisibility(View.VISIBLE);
            recycler.setVisibility(View.GONE);
        } else {
            search_result.setVisibility(View.GONE);
            recycler.setVisibility(View.VISIBLE);
        }
    }

    //检索返回是否为空
    public void refeshEmptystatus(boolean status) {
        if (status) {
            empty_layout.setVisibility(View.VISIBLE);
        } else {
            empty_layout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (DataClass.USERID.isEmpty()) {
            if (checkedId != R.id.home && checkedId != R.id.near) {
                startActivity(new Intent(this, LoginActivity.class));
                group_view.check(oldSelect);
                return;
            }
        }
        switch (checkedId) {
            case R.id.home:
                showFragment = Constants.HOME;
                refreshView(0);
                break;
            case R.id.near:
                showFragment = Constants.NEAR;
                refreshView(1);
                break;
            case R.id.chat:
                showFragment = Constants.CHAT;
                refreshView(2);
                break;
            case R.id.order:
                showFragment = Constants.ORDER;
                refreshView(3);
                break;
            case R.id.mine:
                showFragment = Constants.MINE;
                refreshView(4);
                break;
        }
        group_view.check(checkedId);
        showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
        hideFragment = showFragment;
        oldSelect = checkedId;
    }

    private void refreshView(int status) {
        switch (status) {
            case 0:
                layout_title_bar.setVisibility(View.GONE);
                layout_search_bar.setVisibility(View.VISIBLE);
                break;
            case 1:
                title_name.setText(getString(R.string.near));
                title_about_img.setVisibility(View.VISIBLE);
                title_about_text.setVisibility(View.GONE);
                layout_title_bar.setVisibility(View.VISIBLE);
                layout_search_bar.setVisibility(View.GONE);
                break;
            case 2:
                title_name.setText(getString(R.string.chat));
                layout_title_bar.setVisibility(View.VISIBLE);
                layout_search_bar.setVisibility(View.GONE);
                title_about_img.setVisibility(View.GONE);
                title_about_text.setVisibility(View.GONE);
                break;
            case 3:
                title_name.setText(getString(R.string.order));
                layout_title_bar.setVisibility(View.VISIBLE);
                layout_search_bar.setVisibility(View.GONE);
                title_about_img.setVisibility(View.GONE);
                title_about_text.setVisibility(View.GONE);
                break;
            case 4:
                title_name.setText(getString(R.string.mine));
                title_about_img.setVisibility(View.GONE);
                title_about_text.setVisibility(View.VISIBLE);
                layout_title_bar.setVisibility(View.VISIBLE);
                layout_search_bar.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!returnStatus) {
                exit();
            } else {
                if (ShowFloatingWindowDialogStatus) {
                    RxBus.getDefault().post(new CommonEvent(EventCode.FLOATING_WINDOW_ACTION_CLOSE));
                    ShowFloatingWindowDialogStatus = false;
                    returnStatus = false;
                } else {
                    if (recycler.getVisibility() == View.GONE) {
                        layout_search_content_bar.setVisibility(View.GONE);
                        returnStatus = false;
                    } else {
                        refreshSearchView(true);
                    }
                }
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    //刷新IM 登录状态
    private void refreshImStatus() {
        aliChatBuilder.RefreshChatStatus();
        aliChatBuilder.LoginAliChat();
        aliChatBuilder.notifictionSetting();
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            toastUtil.showToast(getString(R.string.finish));
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

}
