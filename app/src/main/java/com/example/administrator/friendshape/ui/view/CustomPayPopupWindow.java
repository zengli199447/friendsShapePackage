package com.example.administrator.friendshape.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.utils.SystemUtil;
import com.example.administrator.friendshape.widget.CalendarBuilder;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/10/17.
 */

public class CustomPayPopupWindow extends PopupWindow implements View.OnClickListener {
    private String TAG = getClass().getSimpleName();
    private TextView confirm;
    private View mPopView;
    private OnItemClickListener mListener;
    private Context context;
    private int selectType = 0;
    private AppCompatCheckBox wechat_pay;
    private AppCompatCheckBox zhifubao_pay;
    private final Map<Integer, CheckBox> views = new HashMap<>();

    public CustomPayPopupWindow(Context context) {
        super(context);
        this.context = context;
        init();
        setPopupWindow();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        mPopView = inflater.inflate(R.layout.custom_pay_popup_window, null);

        confirm = mPopView.findViewById(R.id.confirm);
        wechat_pay = mPopView.findViewById(R.id.wechat_pay);
        zhifubao_pay = mPopView.findViewById(R.id.zhifubao_pay);
        views.put(0, wechat_pay);
        views.put(1, zhifubao_pay);

        confirm.setOnClickListener(this);

        CheckListener(wechat_pay, 0);
        CheckListener(zhifubao_pay, 1);
    }

    private void CheckListener(View view, final int type) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectType = type;
                for (int i = 0; i < views.size(); i++) {
                    if (i != type) {
                        views.get(i).setChecked(false);
                    }
                }
            }
        });
    }

    /**
     * 设置窗口的相关属性
     */
    @SuppressLint("InlinedApi")
    private void setPopupWindow() {
        this.setContentView(mPopView);// 设置View
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的高
        this.setFocusable(true);// 设置弹出窗口可
        this.setAnimationStyle(R.style.popwindow_anim_style);// 设置动画
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));// 设置背景透明
        mPopView.setOnTouchListener(new View.OnTouchListener() {// 如果触摸位置在窗口外面则销毁
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = mPopView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        SystemUtil.windowToLight(context);
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    public interface OnItemClickListener {
        void setOnItemClick(View v, int selectType);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            SystemUtil.windowToLight(context);
            mListener.setOnItemClick(v, selectType);
        }
    }


}
