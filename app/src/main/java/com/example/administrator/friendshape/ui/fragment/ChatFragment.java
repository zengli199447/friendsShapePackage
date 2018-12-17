package com.example.administrator.friendshape.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.BaseFragment;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.ui.adapter.TabPageIndicatorAdapter;
import com.example.administrator.friendshape.ui.fragment.chat.FriendsFragment;
import com.example.administrator.friendshape.widget.ViewBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：真理 Created by Administrator on 2018/10/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ChatFragment extends BaseFragment implements TabLayout.OnTabSelectedListener {

    @BindView(R.id.tab_layout)
    TabLayout tab_layout;
    @BindView(R.id.view_page)
    ViewPager view_page;

    private List<Fragment> mFragments = new ArrayList<>();
    private TabPageIndicatorAdapter tabPageIndicatorAdapter;
    String[] title;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat;
    }

    @Override
    protected void initClass() {
        mFragments.add(DataClass.mIMKit.getConversationFragment());
//        mFragments.add(new MessageFragment());
        mFragments.add(new FriendsFragment());
    }

    @Override
    protected void initData() {
        title = new String[]{getActivity().getString(R.string.message), getActivity().getString(R.string.friends)};
    }

    @Override
    protected void initView() {
        tabPageIndicatorAdapter = new TabPageIndicatorAdapter(getChildFragmentManager(), title, (ArrayList<Fragment>) mFragments);
        view_page.setAdapter(tabPageIndicatorAdapter);
        tab_layout.setupWithViewPager(view_page);
        tab_layout.post(new Runnable() {
            @Override
            public void run() {
                ViewBuilder.setIndicator(tab_layout, getResources().getInteger(R.integer.chat_bar_margin), getResources().getInteger(R.integer.chat_bar_margin));
            }
        });
    }

    @Override
    protected void initListener() {
        tab_layout.setOnTabSelectedListener(this);
    }

    @Override
    protected void onClickAble(View view) {

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
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {

        }
    }

}
