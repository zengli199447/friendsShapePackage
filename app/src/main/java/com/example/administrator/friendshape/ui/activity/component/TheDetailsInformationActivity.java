package com.example.administrator.friendshape.ui.activity.component;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.BaseActivity;
import com.example.administrator.friendshape.base.BaseLifecycleObserver;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.UserDetailsNetBean;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.rxtools.RxBus;
import com.example.administrator.friendshape.ui.activity.LoginActivity;
import com.example.administrator.friendshape.ui.adapter.TabPageIndicatorAdapter;
import com.example.administrator.friendshape.ui.controller.ControllerTheDetailsInformation;
import com.example.administrator.friendshape.ui.dialog.FloatingWindowDialog;
import com.example.administrator.friendshape.ui.fragment.near.DynamicFragment;
import com.example.administrator.friendshape.ui.fragment.near.GroupFragment;
import com.example.administrator.friendshape.ui.fragment.near.PeopleNearbyFragment;
import com.example.administrator.friendshape.ui.fragment.personal.PersonalDynamicFragment;
import com.example.administrator.friendshape.ui.fragment.personal.PersonalGroupFragment;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.utils.SystemUtil;
import com.example.administrator.friendshape.widget.AlbumBuilder;
import com.example.administrator.friendshape.widget.AliChatBuilder;
import com.example.administrator.friendshape.widget.ViewBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/11/15.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class TheDetailsInformationActivity extends BaseActivity implements TabLayout.OnTabSelectedListener, ControllerTheDetailsInformation.UserDetailsNetWorkListener, AppBarLayout.OnOffsetChangedListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.user_photo)
    ImageView user_photo;
    @BindView(R.id.user_neck_name)
    TextView user_neck_name;
    @BindView(R.id.user_content)
    TextView user_content;
    @BindView(R.id.the_signature)
    TextView the_signature;
    @BindView(R.id.tab_layout)
    TabLayout tab_layout;
    @BindView(R.id.view_page)
    ViewPager view_page;
    @BindView(R.id.bar_layout)
    AppBarLayout bar_layout;
    @BindView(R.id.title_about_round_img)
    ImageView title_about_round_img;
    @BindView(R.id.controller_layout)
    RelativeLayout controller_layout;
    @BindView(R.id.add_friend)
    TextView add_friend;
    @BindView(R.id.say_hello)
    TextView say_hello;

    private String[] title;
    private List<Fragment> mFragments = new ArrayList<>();
    private TabPageIndicatorAdapter tabPageIndicatorAdapter;
    private ControllerTheDetailsInformation controllerTheDetailsInformation;
    int[] location = new int[2];
    private String nickName;
    private int interval;
    private String userId;
    private PersonalDynamicFragment personalDynamicFragment;
    private PersonalGroupFragment personalGroupFragment;
    private boolean returnStatus;
    private RelativeLayout.LayoutParams controllerLayoutParams;
    private int controllerHeight;
    private String userPhoto;
    private AlbumBuilder albumBuilder;
    private int flags;
    private AliChatBuilder aliChatBuilder;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.FLOATING_WINDOW_ACTION_STATUS:
                returnStatus = commonevent.isTemp_boolean();
                break;
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_the_details_information;
    }

    @Override
    protected void initClass() {
        userId = getIntent().getStringExtra("userId");
        controllerTheDetailsInformation = new ControllerTheDetailsInformation(userId);
        personalDynamicFragment = new PersonalDynamicFragment();
        personalGroupFragment = new PersonalGroupFragment();
        mFragments.add(personalDynamicFragment);
        mFragments.add(personalGroupFragment);
        albumBuilder = new AlbumBuilder(this);
        aliChatBuilder = new AliChatBuilder(this);
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerTheDetailsInformation;
    }

    @Override
    protected void initData() {
        title = new String[]{getString(R.string.dynamic), getString(R.string.group)};
        interval = SystemUtil.dp2px(this, 70);
        controllerHeight = SystemUtil.dp2px(this, 50);
        flags = getIntent().getFlags();
    }

    @Override
    protected void initView() {
        controllerLayoutParams = (RelativeLayout.LayoutParams) controller_layout.getLayoutParams();
        View decorView = getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(ViewBuilder.getGlobalLayoutListener(decorView, findViewById(Window.ID_ANDROID_CONTENT)));
        title_name.setText(getString(R.string.detailed_information_on));
        tabPageIndicatorAdapter = new TabPageIndicatorAdapter(getSupportFragmentManager(), title, (ArrayList<Fragment>) mFragments);
        view_page.setAdapter(tabPageIndicatorAdapter);
        tab_layout.setupWithViewPager(view_page);
        tab_layout.post(new Runnable() {
            @Override
            public void run() {
                ViewBuilder.setIndicator(tab_layout, getResources().getInteger(R.integer.details_bar_margin), getResources().getInteger(R.integer.details_bar_margin));
            }
        });
        if (DataClass.USERID.equals(userId)) {
            controller_layout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initListener() {
        tab_layout.setOnTabSelectedListener(this);
        controllerTheDetailsInformation.setUserDetailsNetWorkListener(this);
        bar_layout.addOnOffsetChangedListener(this);
    }

    @SuppressLint("WrongConstant")
    @OnClick({R.id.img_btn_black, R.id.add_friend, R.id.say_hello, R.id.user_photo})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.add_friend:
                toastUtil.showToast(getString(R.string.add_friend));
                Intent frientControllerIntent = new Intent(this, FriendsControllerActivity.class);
                frientControllerIntent.putExtra("friendId", userId);
                frientControllerIntent.putExtra("nickName", nickName);
                frientControllerIntent.putExtra("userPhoto", userPhoto);
                frientControllerIntent.setFlags(1);
                startActivity(frientControllerIntent);
                break;
            case R.id.say_hello:
                if (DataClass.USERID.isEmpty()) {
                    startActivity(new Intent(this, LoginActivity.class));
                    return;
                }
                if (flags == 1) {
                    finish();
                } else {
                    aliChatBuilder.OpenSingleConversation(userId);
                    toastUtil.showToast(getString(R.string.say_hello));
                }
                break;
            case R.id.user_photo:
                ArrayList<String> strings = new ArrayList<>();
                strings.add(userPhoto);
                albumBuilder.ImageTheExhibition(strings, false, 0);
                break;
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        view_page.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onUserDetailsNetWorkListener(UserDetailsNetBean.ResultBean result) {
        nickName = result.getSecondname();
        user_neck_name.setText(nickName);
        user_content.setText(new StringBuffer().append(result.getSex()).append("   ").append(result.getAge()).append(getString(R.string.at_the_age)).append("          ").append(getString(R.string.id)).append(":   ").append(result.getUserid()).toString());
        Glide.with(this).load(SystemUtil.JudgeUrl(result.getPhoto())).error(R.drawable.banner_off).into(user_photo);
        Glide.with(this).load(SystemUtil.JudgeUrl(result.getPhoto())).error(R.drawable.banner_off).into(title_about_round_img);
        userPhoto = SystemUtil.JudgeUrl(result.getPhoto());
        if (!result.getRemark().isEmpty()) {
            the_signature.setText(new StringBuffer().append(getString(R.string.the_signature)).append(" : ").append(result.getRemark()));
            the_signature.setVisibility(View.VISIBLE);
        }
        if ("1".equals(result.getIffriend())) {
            add_friend.setVisibility(View.GONE);
            say_hello.setText(getString(R.string.in_chat));
        } else {
            add_friend.setVisibility(View.VISIBLE);
            say_hello.setText(getString(R.string.say_hello));
        }

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        float top = tab_layout.getTop();
        float abs = Math.abs(verticalOffset);
        float proportion = abs / top;
        title_about_round_img.setAlpha(1 * proportion);
        tab_layout.getLocationOnScreen(location);
        if (location[1] - interval != 0) {
            title_name.setText(getString(R.string.detailed_information_on));
        } else {
            title_name.setText(new StringBuffer().append(nickName).append(getString(R.string.personal_space)).toString());
        }
        float topHight = proportion * ((float) controllerHeight) * (-1);
        controllerLayoutParams.setMargins(0, 0, 0, (int) topHight);
        controller_layout.setLayoutParams(controllerLayoutParams);

        personalDynamicFragment.swipeRefreshStatus(verticalOffset == 0 ? true : false);
        personalGroupFragment.swipeRefreshStatus(verticalOffset == 0 ? true : false);

    }

    public String GetUserId() {
        return userId;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!returnStatus) {
                finish();
            } else {
                RxBus.getDefault().post(new CommonEvent(EventCode.FLOATING_WINDOW_ACTION_CLOSE));
                returnStatus = false;
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}
