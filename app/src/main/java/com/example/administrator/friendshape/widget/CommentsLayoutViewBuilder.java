package com.example.administrator.friendshape.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.administrator.friendshape.ui.adapter.DynamicAdapter;
import com.example.administrator.friendshape.ui.adapter.DynamicCommentsAdapter;
import com.example.administrator.friendshape.utils.LogUtil;

import java.util.ArrayList;

import static android.view.KeyEvent.ACTION_DOWN;
import static android.view.KeyEvent.ACTION_UP;
import static android.view.MotionEvent.ACTION_MOVE;

/**
 * 作者：真理 Created by Administrator on 2018/11/13.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class CommentsLayoutViewBuilder {

    String TAG = getClass().getSimpleName();

    Context context;
    RecyclerView recyclerView;
    RelativeLayout layout_dynamic_comments;

    public CommentsLayoutViewBuilder(RecyclerView recyclerView, RelativeLayout layout_dynamic_comments, Context context) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.layout_dynamic_comments = layout_dynamic_comments;
    }

    @SuppressLint("ClickableViewAccessibility")
    public void ScrollListener() {
        recyclerView.addOnScrollListener(scrollListener);
        int top = recyclerView.getTop();
        LogUtil.e(TAG, "top :　" + top);

        layout_dynamic_comments.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        LogUtil.e(TAG, "parent _ MotionEvent.ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int top = recyclerView.getTop();
                        LogUtil.e(TAG, "current_top :　" + top);
                        LogUtil.e(TAG, "MotionEvent.ACTION_MOVE");
                        break;
                    case MotionEvent.ACTION_UP:
                        LogUtil.e(TAG, "MotionEvent.ACTION_UP");
                        break;

                }
                return false;
            }
        });

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        LogUtil.e(TAG, "MotionEvent.ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int top = recyclerView.getTop();
                        LogUtil.e(TAG, "current_top :　" + top);
                        LogUtil.e(TAG, "MotionEvent.ACTION_MOVE");
                        break;
                    case MotionEvent.ACTION_UP:
                        LogUtil.e(TAG, "MotionEvent.ACTION_UP");
                        break;

                }
                return false;
            }
        });

    }

    private RecyclerView.OnScrollListener scrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadMore() {
            LogUtil.e(TAG, "底部");
        }

        @Override
        public void onScrolled(RecyclerView recyclerView) {

        }
    };


}
