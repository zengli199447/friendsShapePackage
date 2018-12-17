package com.example.administrator.friendshape.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.BaseActivity;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.global.MyApplication;
import com.example.administrator.friendshape.model.db.entity.AppDBInfo;
import com.example.administrator.friendshape.model.db.entity.LoginUserInfo;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.utils.LocationUtils;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.utils.SystemUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：真理 Created by Administrator on 2018/10/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.view_pager)
    ViewPager view_pager;
    @BindView(R.id.splash_img)
    ImageView splash_img;
    private DataClass dataClass;
    private ArrayList<Integer> bannerList;
    private ArrayList<ImageView> views = new ArrayList<>();
    private MyPagerAdapter myPagerAdapter;


    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case 0:
                    if (DataClass.USERID.isEmpty()) {
                        startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                    } else {
                        startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
                    }
                    finish();
                    break;
                case 1: {
                    startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
                    finish();
                }
            }
        }
    };

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initClass() {
        MyApplication.executorService.submit(new Runnable() {
            @Override
            public void run() {
                LocationUtils.getCNBylocation(WelcomeActivity.this);
            }
        });
        Glide.with(this).load(R.drawable.splash).error(R.drawable.banner_off).into(splash_img);
        dataClass = new DataClass(dataManager);
        if (dataManager.queryLoginUserInfo(DataClass.STANDARD_USER) != null) {
            LoginUserInfo admin = dataManager.queryLoginUserInfo(DataClass.STANDARD_USER);
            DataClass.USERID = admin.getUserid();
            DataClass.GENDER = admin.getUserGender();
            DataClass.UNAME = admin.getUserNiceName();
            DataClass.PHONE = admin.getUserPhoneNumber();
            DataClass.PASSWORD = admin.getUserPassWord();
            LogUtil.e(TAG, "DataClass.USERID : " + DataClass.USERID);
            LogUtil.e(TAG, "DataClass.PASSWORD : " + DataClass.PASSWORD);
        }
        if (dataManager.loadAppDBInfoDao().size() > 0) {
            splash_img.setVisibility(View.VISIBLE);
            view_pager.setVisibility(View.GONE);
            handler.sendEmptyMessageDelayed(1, getResources().getInteger(R.integer.send_message_action_ss));
        } else {
            view_pager.setVisibility(View.VISIBLE);
            splash_img.setVisibility(View.GONE);
            dataManager.insertAppDBInfoDao(new AppDBInfo("", true));
        }
    }

    @Override
    protected void initData() {
        bannerList = dataClass.getWelcomeBannerList();
        for (int i = 0; i < bannerList.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            views.add(imageView);
            Glide.with(WelcomeActivity.this).load(bannerList.get(i)).asBitmap().placeholder(R.drawable.white_).into(views.get(i));
        }
        myPagerAdapter = new MyPagerAdapter(views);
    }

    @Override
    protected void initView() {
        SystemUtil.hideBottomUIMenu(this);
        view_pager.setAdapter(myPagerAdapter);
    }

    @Override
    protected void initListener() {
        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                views.get(position).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (position == bannerList.size() - 1)
                            handler.sendEmptyMessage(0);
                    }
                });
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onClickAble(View view) {

    }

    public class MyPagerAdapter extends PagerAdapter {
        private List<ImageView> views;

        public MyPagerAdapter(List<ImageView> views) {
            this.views = views;
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = views.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "标题" + position;
        }
    }


}
