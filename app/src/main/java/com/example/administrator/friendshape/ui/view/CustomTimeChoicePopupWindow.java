package com.example.administrator.friendshape.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.utils.SystemUtil;
import com.example.administrator.friendshape.widget.CalendarBuilder;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/10/17.
 */

public class CustomTimeChoicePopupWindow extends PopupWindow implements View.OnClickListener {
    private String TAG = getClass().getSimpleName();
    private TextView confirm;
    private View mPopView;
    private OnItemClickListener mListener;
    private Context context;
    private LoopView loop_view_year;
    private LoopView loop_view_month;
    private LoopView loop_view_day;
    private LoopView loop_view_hour;
    private LoopView loop_view_minute;
    private TextView title_name;
    private TextView time_hour_title;
    private TextView time_minute_title;
    private int currentYear;
    private List<String> yearList;
    private List<String> monthList;
    private List<String> dayList = new ArrayList<>();
    private List<String> hourList;
    private List<String> minuteList;
    private CalendarBuilder calendarBuilder;
    private int currentSelectYear = 0;
    private int currentSelectMonth = 0;
    private int currentSelectDay = 0;
    private int currentSelectHour = 0;
    private int currentSelectMinute = 0;
    private int selectType = 0;


    /**
     * 构造
     *
     * @param context
     * @param calendarBuilder
     * @param status          五项选择(活动时间)、三项选择(生日)
     */
    public CustomTimeChoicePopupWindow(Context context, CalendarBuilder calendarBuilder, boolean status) {
        super(context);
        this.context = context;
        this.calendarBuilder = calendarBuilder;
        currentYear = Integer.valueOf(calendarBuilder.queryYear());
        currentSelectMonth = Integer.valueOf(calendarBuilder.queryMonth()) - 1;
        currentSelectDay = Integer.valueOf(calendarBuilder.queryDay()) - 1;
        currentSelectHour = Integer.valueOf(calendarBuilder.queryHour());
        initData(status);
        init(status);
        setPopupWindow();
    }

    private void init(boolean status) {
        LayoutInflater inflater = LayoutInflater.from(context);
        mPopView = inflater.inflate(R.layout.custom_time_choice_popup_window, null);
        loop_view_year = mPopView.findViewById(R.id.loop_view_year);
        loop_view_month = mPopView.findViewById(R.id.loop_view_month);
        loop_view_day = mPopView.findViewById(R.id.loop_view_day);
        loop_view_hour = mPopView.findViewById(R.id.loop_view_hour);
        loop_view_minute = mPopView.findViewById(R.id.loop_view_minute);
        confirm = mPopView.findViewById(R.id.confirm);
        title_name = mPopView.findViewById(R.id.title_name);
        time_hour_title = mPopView.findViewById(R.id.time_hour_title);
        time_minute_title = mPopView.findViewById(R.id.time_minute_title);

        if (status) {
            time_hour_title.setVisibility(View.VISIBLE);
            time_minute_title.setVisibility(View.VISIBLE);
            loop_view_hour.setVisibility(View.VISIBLE);
            loop_view_minute.setVisibility(View.VISIBLE);
        }

        initDay(currentSelectDay);

        loop_view_month.setDividerColor(context.getResources().getColor(R.color.transparent));
        loop_view_month.setItems(monthList);
        loop_view_month.setCurrentPosition(currentSelectMonth);
        loop_view_month.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                currentSelectMonth = index;
                initDay(currentSelectMonth);
            }
        });

        loop_view_day.setDividerColor(context.getResources().getColor(R.color.transparent));
        loop_view_day.setCurrentPosition(currentSelectDay);
        loop_view_day.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                currentSelectDay = index;
            }
        });

        setLoopView(loop_view_year, yearList, currentSelectYear, 0);
        loop_view_year.setNotLoop();

        setLoopView(loop_view_hour, hourList, currentSelectHour, 1);

        setLoopView(loop_view_minute, minuteList, currentSelectMinute, 2);
        loop_view_minute.setNotLoop();

        confirm.setOnClickListener(this);
    }

    private void setLoopView(LoopView view, List<String> list, int currentPosition, final int currentIndex) {
        LogUtil.e(TAG, "currentPosition : " + currentPosition);
        LogUtil.e(TAG, "list.get(currentPosition) : " + list.get(currentPosition));
        view.setItems(list);
        view.setDividerColor(context.getResources().getColor(R.color.transparent));
        view.setCurrentPosition(currentPosition);
        view.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                switch (currentIndex) {
                    case 0:
                        currentSelectYear = index;
                        break;
                    case 1:
                        currentSelectHour = index;
                        break;
                    case 2:
                        currentSelectMinute = index;
                        break;
                }
            }
        });
    }

    public void refreshTitle(String content, int selectType) {
        title_name.setText(content);
        this.selectType = selectType;
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
        void setOnItemClick(View v, String year, String month, String day, String hour, String minute, int selectType);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            SystemUtil.windowToLight(context);
            mListener.setOnItemClick(v, yearList.get(currentSelectYear),
                    monthList.get(currentSelectMonth),
                    dayList.get(currentSelectDay),
                    hourList.get(currentSelectHour),
                    minuteList.get(currentSelectMinute),
                    selectType);
        }
    }

    private void initData(boolean status) {
        yearList = new ArrayList<>();
        monthList = new ArrayList<>();
        hourList = new ArrayList<>();
        minuteList = new ArrayList<>();
        int maxSelectYear;
        if (status) {
            maxSelectYear = currentYear + context.getResources().getInteger(R.integer.max_select_to_year);
            for (int i = currentYear; i < maxSelectYear + 1; i++) {
                yearList.add(String.valueOf(i));
            }
        } else {
            maxSelectYear = currentYear - context.getResources().getInteger(R.integer.x_max_select_to_year);
            currentSelectYear = context.getResources().getInteger(R.integer.x_max_select_to_year);
            for (int i = maxSelectYear; i < currentYear + 1; i++) {
                yearList.add(String.valueOf(i));
            }
        }

        for (int i = 1; i < 13; i++) {
            monthList.add(String.valueOf(i));
        }
        for (int i = 0; i < 24; i++) {
            hourList.add(String.valueOf(i));
        }
        minuteList.add("00");
        minuteList.add("30");

    }

    private void initDay(int currentMonth) {
        int month = currentMonth + 1;
        LogUtil.e(TAG, "month : " + month);
        dayList.clear();
        if (month == 2) {
            for (int i = 1; i < 29; i++) {
                dayList.add(String.valueOf(i));
            }
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            for (int i = 1; i < 31; i++) {
                dayList.add(String.valueOf(i));
            }
        } else {
            for (int i = 1; i < 32; i++) {
                dayList.add(String.valueOf(i));
            }
        }
        loop_view_day.setItems(dayList);
        loop_view_day.refreshDrawableState();
    }


}
