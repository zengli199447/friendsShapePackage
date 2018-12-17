package com.example.administrator.friendshape.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.utils.SystemUtil;

import java.lang.reflect.Field;

/**
 * 作者：真理 Created by Administrator on 2018/10/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ViewBuilder {

    public static void showKeyboard(EditText editText) {
        if (editText != null) {
            //设置可获得焦点
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            //请求获得焦点
            editText.requestFocus();
            //调用系统输入法
            InputMethodManager inputManager = (InputMethodManager) editText
                    .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(editText, 0);
        }
    }

    public static void closeKeybord(EditText editText) {
        InputMethodManager imm = (InputMethodManager) editText.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * tablayout标题长度修改
     */
    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    public static void textDrawable(TextView view, Context context, int id, int direction) {
        Drawable nav_up = context.getResources().getDrawable(id);
        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
        switch (direction) {
            case 0:
                view.setCompoundDrawables(nav_up, null, null, null);
                break;
            case 1:
                view.setCompoundDrawables(null, nav_up, null, null);
                break;
            case 2:
                view.setCompoundDrawables(null, null, nav_up, null);
                break;
            case 3:
                view.setCompoundDrawables(null, null, null, nav_up);
                break;
        }
    }

    public static void ProgressStyleChange(SwipeRefreshLayout swipe_layout) {
        swipe_layout.setProgressViewOffset(true, -20, 100);
        swipe_layout.setColorSchemeResources(R.color.blue_bar);
    }

    /**
     * 高度测量
     *
     * @param recyclerView 指定的view
     * @param size         数据长度
     * @param max          最大长度
     */
    public static void HeightCalculation(RecyclerView recyclerView, int size, int max) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) recyclerView.getLayoutParams();
        if (size > max) {
            layoutParams.height = SystemUtil.dp2px(recyclerView.getContext(), 40 * max);
        } else {
            layoutParams.height = SystemUtil.dp2px(recyclerView.getContext(), 40 * size);
        }
        recyclerView.setLayoutParams(layoutParams);
    }

    public static ViewTreeObserver.OnGlobalLayoutListener getGlobalLayoutListener(final View decorView, final View contentView) {
        return new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                decorView.getWindowVisibleDisplayFrame(r);

                int height = decorView.getContext().getResources().getDisplayMetrics().heightPixels;
                int diff = height - r.bottom;

                if (diff != 0) {
                    if (contentView.getPaddingBottom() != diff) {
                        contentView.setPadding(0, 0, 0, diff);
                    }
                } else {
                    if (contentView.getPaddingBottom() != 0) {
                        contentView.setPadding(0, 0, 0, 0);
                    }
                }
            }
        };
    }

    //输入内容可见状态
    public static void seeChecklListener(CheckBox checkBox, EditText editText) {
        int type = InputType.TYPE_CLASS_TEXT;
        if (!checkBox.isChecked()) {
            type = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
        }
        editText.setInputType(type);
    }

    //布局管理器
    public static FullyLinearLayoutManager getFullyLinearLayoutManager(Context context, boolean status) {
        FullyLinearLayoutManager fullyLinearLayoutManager = new FullyLinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        fullyLinearLayoutManager.setScrollEnable(status);
        return fullyLinearLayoutManager;
    }

    public static FullyGridLayoutManager getFullyGridLayoutManager(Context context, boolean status, int spanCount) {
        FullyGridLayoutManager fullyGridLayoutManager = new FullyGridLayoutManager(context, spanCount);
        fullyGridLayoutManager.setScrollEnable(false);
        return fullyGridLayoutManager;
    }


}
