package com.example.administrator.friendshape.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * 作者：真理 Created by Administrator on 2018/9/28.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class TabPageIndicatorAdapter extends FragmentStatePagerAdapter {

    private String[] strings;
    private ArrayList<Fragment> FragmentList;

    public TabPageIndicatorAdapter(FragmentManager supportFragmentManager, String[] strings, ArrayList<Fragment> FragmentList) {
        super(supportFragmentManager);
        this.strings = strings;
        this.FragmentList = FragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = FragmentList.get(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return strings.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return strings[position % strings.length];

    }

}
