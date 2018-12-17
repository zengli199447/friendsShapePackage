package com.example.administrator.friendshape.ui.fragment;

import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.BaseFragment;
import com.example.administrator.friendshape.base.BaseLifecycleObserver;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.ui.adapter.TabPageIndicatorAdapter;
import com.example.administrator.friendshape.ui.controller.ControllerNear;
import com.example.administrator.friendshape.ui.fragment.near.DynamicFragment;
import com.example.administrator.friendshape.ui.fragment.near.GroupFragment;
import com.example.administrator.friendshape.ui.fragment.near.PeopleNearbyFragment;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.utils.SystemUtil;
import com.example.administrator.friendshape.widget.ViewBuilder;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/10/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class NearFragment extends BaseFragment implements TabLayout.OnTabSelectedListener, ControllerNear.ScreeningListener {

    @BindView(R.id.tab_layout)
    TabLayout tab_layout;
    @BindView(R.id.view_page)
    ViewPager view_page;
    @BindView(R.id.dynamic_select)
    TextView dynamic_select;
    @BindView(R.id.people_nearby_select)
    TextView people_nearby_select;
    @BindView(R.id.group_select)
    TextView group_select;

    @BindView(R.id.screening_layout)
    RelativeLayout screening_layout;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.recycler_view_group)
    RecyclerView recycler_view_group;

    private List<Fragment> mFragments = new ArrayList<>();
    private TabPageIndicatorAdapter tabPageIndicatorAdapter;
    String[] title;
    TextView[] selectGroupView;
    boolean checkStatus;
    int currentIndex = 0;
    private ControllerNear controllerNear;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_near;
    }

    @Override
    protected void initClass() {
        mFragments.add(new DynamicFragment());
        mFragments.add(new PeopleNearbyFragment());
        mFragments.add(new GroupFragment());
        controllerNear = new ControllerNear(recycler_view, recycler_view_group);
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerNear;
    }

    @Override
    protected void initData() {
        title = new String[]{getActivity().getString(R.string.dynamic), getActivity().getString(R.string.people_nearby), getActivity().getString(R.string.group)};
        selectGroupView = new TextView[]{dynamic_select, people_nearby_select, group_select};
    }

    @Override
    protected void initView() {
        tabPageIndicatorAdapter = new TabPageIndicatorAdapter(getChildFragmentManager(), title, (ArrayList<Fragment>) mFragments);
        view_page.setAdapter(tabPageIndicatorAdapter);
        tab_layout.setupWithViewPager(view_page);
        tab_layout.post(new Runnable() {
            @Override
            public void run() {
                ViewBuilder.setIndicator(tab_layout, getResources().getInteger(R.integer.title_bar_margin), getResources().getInteger(R.integer.title_bar_margin));
            }
        });
    }

    @Override
    protected void initListener() {
        tab_layout.setOnTabSelectedListener(this);
        controllerNear.setScreeningListener(this);
    }

    @OnClick({R.id.dynamic_select, R.id.people_nearby_select, R.id.group_select, R.id.screening_layout})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.dynamic_select:
                GroupScreeningTools(0);
                view_page.setCurrentItem(0);
                break;
            case R.id.people_nearby_select:
                GroupScreeningTools(1);
                view_page.setCurrentItem(1);
                break;
            case R.id.group_select:
                GroupScreeningTools(2);
                view_page.setCurrentItem(2);
                break;
            case R.id.screening_layout:
                closeScreeningLayout();
                break;
        }
    }

    /**
     * 更新对应筛选
     *
     * @param index 筛选类型
     */
    public void GroupScreeningTools(int index) {
        if (currentIndex == index) {
            if (screening_layout.getVisibility() == View.GONE) {
                screening_layout.setVisibility(View.VISIBLE);
                ViewBuilder.textDrawable(selectGroupView[index], getActivity(), R.drawable.up_icon, 2);
                switch (index) {
                    case 0:
                        controllerNear.initScreeningAdapter(index);
                        break;
                    case 1:
                        controllerNear.initScreeningAdapter(index);
                        break;
                    case 2:
                        controllerNear.initScreeningAdapter(index);
                        break;
                }
            } else {
                closeScreeningLayout();
            }
        } else {
            screening_layout.setVisibility(View.GONE);
            selectGroupRefreshView(index);
        }
    }

    /**
     * 更新按钮状态
     *
     * @param index 按钮下标
     */
    private void selectGroupRefreshView(int index) {
        currentIndex = index;
        for (int i = 0; i < selectGroupView.length; i++) {
            if (index == i) {
                ViewBuilder.textDrawable(selectGroupView[i], getActivity(), R.drawable.down_icon, 2);
                selectGroupView[i].setTextColor(getResources().getColor(R.color.blue_bar));
            } else {
                ViewBuilder.textDrawable(selectGroupView[i], getActivity(), R.drawable.down_white, 2);
                selectGroupView[i].setTextColor(getResources().getColor(R.color.black_overlay));
            }
        }

    }

    /**
     * 关闭筛选、清空搜索条件
     */
    public void closeScreeningLayout() {
        controllerNear.refreshcombinationConditions();
        ViewBuilder.textDrawable(selectGroupView[currentIndex], getActivity(), R.drawable.down_icon, 2);
        screening_layout.setVisibility(View.GONE);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        view_page.setCurrentItem(tab.getPosition());
        selectGroupRefreshView(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    //搜索回调
    @Override
    public void onScreeningListener(String s, int type) {
        switch (type) {
            case 0:
                dynamic_select.setText(s);
                break;
            case 1:
                people_nearby_select.setText(s);
                break;
            case 2:
                group_select.setText(s);
                break;
        }
        closeScreeningLayout();
    }

}
