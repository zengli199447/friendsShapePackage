package com.example.administrator.friendshape.ui.dialog;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.model.bean.BusObject;
import com.example.administrator.friendshape.model.bean.DynamicNetBean;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.rxtools.RxBus;
import com.example.administrator.friendshape.ui.adapter.DynamicCommentsAdapter;
import com.example.administrator.friendshape.ui.view.FloatingWindowBarLayout;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.utils.ToastUtil;
import com.example.administrator.friendshape.widget.FullyLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/14.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class FloatingWindowDialog extends FloatingWindowBarLayout implements View.OnClickListener, DynamicCommentsAdapter.ReplyClickListener {

    private String TAG = getClass().getSimpleName();

    Context context;
    private View inflate;
    private RecyclerView recycler_view;
    private DynamicCommentsAdapter dynamicCommentsAdapter;
    private List<Object> list = new ArrayList<>();
    private EditText input_reply;
    private TextView send;
    private TextView empty_layout;
    private String relpyId = "";
    private String relpyBasisId = "";
    private String relpyName = "";
    private String relpyBasisName = "";
    private int type;
    //目标项是否在最后一个可见项之后
    private boolean mShouldScroll;
    //记录目标项位置
    private int mToPosition;

    public FloatingWindowDialog(Context context, List<Object> listContent, String dynamicUserName, String dynamicUserId, int type) {
        super(context);
        this.context = context;
        this.relpyId = dynamicUserId;
        this.relpyBasisId = dynamicUserId;
        this.relpyName = dynamicUserName;
        this.relpyBasisName = dynamicUserName;
        this.type = type;
        list.clear();
        list.addAll(listContent);
        initView();
    }

    private void initView() {
        inflate = LayoutInflater.from(context).inflate(R.layout.layout_floating_windows_comments, contentContainer);
        AppBarLayout appBarLayout = inflate.findViewById(R.id.app_bar);
        recycler_view = inflate.findViewById(R.id.recycler_view);
        input_reply = inflate.findViewById(R.id.input_reply);
        send = inflate.findViewById(R.id.send);
        empty_layout = inflate.findViewById(R.id.empty_layout);
        View app_bar = inflate.findViewById(R.id.app_bar);

        ViewGroup.LayoutParams layoutParams = appBarLayout.getLayoutParams();
        layoutParams.height = ((AppCompatActivity) context).getWindowManager().getDefaultDisplay().getHeight() / 2;
        appBarLayout.setLayoutParams(layoutParams);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if (onAppBarLayoutOffsetChangedListener != null) {
                    onAppBarLayoutOffsetChangedListener.onAppBarLayoutOffsetChanged(recycler_view, appBarLayout, i);
                }
            }
        });

        setCancelable(false);
        initAdapter();
        send.setOnClickListener(this);
        app_bar.setOnClickListener(this);
        input_reply.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    send.setTextColor(context.getResources().getColor(R.color.gray_light_text));
                    relpyId = relpyBasisId;
                    relpyName = relpyBasisName;
                } else {
                    send.setTextColor(context.getResources().getColor(R.color.blue_bar));
                }
                if (!relpyId.equals(relpyBasisId)) {
                    if (!s.toString().contains(new StringBuffer().append("@").append(relpyName).append(" : ").toString())) {
                        input_reply.setText("");
                    }
                }
                LogUtil.e(TAG, "relpyId : " + relpyId);
                LogUtil.e(TAG, "relpyName : " + relpyName);
            }
        });
    }

    private void initAdapter() {
        FullyLinearLayoutManager fullyLinearLayoutManager = new FullyLinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recycler_view.setLayoutManager(fullyLinearLayoutManager);
        dynamicCommentsAdapter = new DynamicCommentsAdapter(context, list);
        recycler_view.setAdapter(dynamicCommentsAdapter);
        dynamicCommentsAdapter.setReplyClickListener(this);
        refreshViewStatus();
        dynamicCommentsAdapter.notifyDataSetChanged();

        recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mShouldScroll && RecyclerView.SCROLL_STATE_IDLE == newState) {
                    mShouldScroll = false;
                    smoothMoveToPosition(recycler_view, mToPosition);
                }
            }
        });
    }

    //刷新@项
    private void refreshHint(String relpyName) {
        input_reply.setText(new StringBuffer().append("@").append(relpyName).append(" : ").toString());
    }

    //评论栏刷新
    public void refreshView(List<Object> newCommentsList) {
        list.clear();
        list.addAll(newCommentsList);
        refreshViewStatus();
        dynamicCommentsAdapter.notifyDataSetChanged();
        smoothMoveToPosition(recycler_view, newCommentsList.size() - 1);
    }

    //提示
    private void refreshViewStatus() {
        if (list.size() == 0) {
            empty_layout.setVisibility(View.VISIBLE);
        } else {
            empty_layout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send:
                if (input_reply.getText().toString().isEmpty()) {
                    new ToastUtil(context).showToast(context.getString(R.string.empty_content));
                } else {
                    BusObject busObject = new BusObject(relpyId, input_reply.getText().toString(), null, type);
                    RxBus.getDefault().post(new CommonEvent(EventCode.FLOATING_WINDOW_ACTION_SEND_COMMENTS, busObject));
                    input_reply.setText("");
                }
                break;
            case R.id.app_bar:
                dismiss();
                break;
        }
    }

    //@项选择
    @Override
    public void onReplyClickListener(int position, int type) {
        DynamicNetBean.ResultBean.NewsBean.ReplyBean replyBean = (DynamicNetBean.ResultBean.NewsBean.ReplyBean) list.get(position);
        switch (type) {
            case 0:
                relpyId = replyBean.getFromUser().getId();
                relpyName = replyBean.getFromUser().getName();
                break;
            case 1:
                relpyId = replyBean.getToUser().getId();
                relpyName = replyBean.getToUser().getName();
                break;
        }
        if (!relpyId.equals(relpyBasisId)) {
            refreshHint(relpyName);
        }
    }

    /**
     * 滑动到指定位置
     */
    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        try {
            int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
            int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
            if (position < firstItem) {
                mRecyclerView.smoothScrollToPosition(position);
            } else if (position <= lastItem) {
                int movePosition = position - firstItem;
                if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                    int top = mRecyclerView.getChildAt(movePosition).getTop();
                    mRecyclerView.smoothScrollBy(0, top);
                }
            } else {
                mRecyclerView.smoothScrollToPosition(position);
                mToPosition = position;
                mShouldScroll = true;
            }
        } catch (Exception e) {
            LogUtil.e(TAG, "Exception : " + e.toString());
        }
    }

    public interface OnAppBarLayoutOffsetChangedListener {
        void onAppBarLayoutOffsetChanged(RecyclerView recyclerView, AppBarLayout appBarLayout, int i);
    }

    private OnAppBarLayoutOffsetChangedListener onAppBarLayoutOffsetChangedListener;

    public void setOnAppBarLayoutOffsetChangedListener(OnAppBarLayoutOffsetChangedListener onAppBarLayoutOffsetChangedListener) {
        this.onAppBarLayoutOffsetChangedListener = onAppBarLayoutOffsetChangedListener;
    }

}
