package com.example.administrator.friendshape.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.administrator.friendshape.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 作者：真理 Created by Administrator on 2018/11/5.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class TextSwitcherView extends TextSwitcher implements ViewSwitcher.ViewFactory {
    private static final int UPDATA_TEXTSWITCHER = 1;
    private Context mContext;
    private int index = 0;
    private ArrayList<String> mReArrayList = new ArrayList<>();
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            Message message = new Message();
            message.what = UPDATA_TEXTSWITCHER;
            handler.sendMessage(message);
        }
    };
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATA_TEXTSWITCHER:
                    updateTextSwitcher();
                    break;
            }
        }
    };

    public void setResource(ArrayList<String> reArrayList) {
        this.mReArrayList = reArrayList;
    }

    private void updateTextSwitcher() {
        if (mReArrayList != null && mReArrayList.size() > 0) {
            this.setText(mReArrayList.get(index++));
            //实现归零
            if (index > mReArrayList.size() - 1) {
                index = 0;
            }
        }
    }

    public TextSwitcherView(Context context) {
        super(context, null);
    }

    public TextSwitcherView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    private void init() {
        this.setFactory(this);
        this.setInAnimation(getContext(), R.anim.rolling_bottom_in);
        this.setOutAnimation(getContext(), R.anim.rolling_buttom_out);
        Timer timer = new Timer();
        timer.schedule(timerTask, 1, 6000);

    }

    public void RefreshView(String content) {
        mReArrayList.add(content);
    }

    @Override
    public View makeView() {
        //如果想实现跑马灯 可以这么做 不然直接用TextView也是没有关系的
        //设置自己的TextView样式 继承TextView 重写isFoused方法为true
        WaterTextView tv = new WaterTextView(getContext());
//        Android:ellipsize = "end"　　  省略号在结尾
//        android:ellipsize = "start" 　　省略号在开头
//        android:ellipsize = "middle"     省略号在中间
//        android:ellipsize = "marquee"  跑马灯
//        最好加一个约束android:singleline = "true"
        tv.setSingleLine(true);
        tv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        //设置重复次数 -1则无限循环
        tv.setMarqueeRepeatLimit(-1);
        tv.setTextColor(getResources().getColor(R.color.black_overlay));
        tv.setTextSize(13);
        return tv;
    }

    public class WaterTextView extends TextView {
        public WaterTextView(Context context) {
            super(context);
        }

        public WaterTextView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }


        public WaterTextView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        public boolean isFocused() {
            return true;
        }
    }

}
