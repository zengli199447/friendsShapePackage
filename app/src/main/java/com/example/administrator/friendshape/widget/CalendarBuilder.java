package com.example.administrator.friendshape.widget;

import android.content.Context;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.utils.LogUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 作者：真理 Created by Administrator on 2018/10/31.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class CalendarBuilder {

    private String TAG = getClass().getSimpleName();
    private Calendar calendar;
    private String year;
    private String month;
    private String day;
    private int hour;
    private int minute;

    public CalendarBuilder(Calendar calendar) {
        this.calendar = calendar;
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
    }

    public String queryYear() {
        year = String.valueOf(calendar.get(Calendar.YEAR));
        return year;
    }

    public String queryMonth() {
        month = String.valueOf((calendar.get(Calendar.MONTH)) + 1);
        return month;
    }

    public String queryDay() {
        day = String.valueOf(calendar.get(Calendar.DATE));
        return day;
    }

    public int queryHour() {
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        return hour;
    }

    public int queryMinute() {
        minute = calendar.get(Calendar.MINUTE);
        return minute;
    }

    public static String getCurrentTime(boolean status) {
        SimpleDateFormat df = null;
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (status)
            df = new SimpleDateFormat("yyyy-MM-dd");
        Date day = new Date();
        return df.format(day);
    }

    public static String formatHMSText(long date , int type) {
        SimpleDateFormat df = null;
        switch (type){
            case 0:
                df = new SimpleDateFormat("HH:mm");
                break;
            case 1:
                df = new SimpleDateFormat("mm:ss");
                break;
            case 2:
                df = new SimpleDateFormat("yyyy.MM.dd HH:mm");
                break;
        }
        return df.format(new Date(date));
    }

    public static long getFormatLongDate(String time) {
        long longTime = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date utilDate = sdf.parse(time);
            longTime = utilDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return longTime;
    }

    public static long getFormatLongMinDate(String time) {
        long longTime = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date utilDate = sdf.parse(time);
            longTime = utilDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return longTime;
    }

    public static String getTimeDifference(long beginTime, long endTime) {
        Long between = (beginTime - endTime) / 1000;// 除以1000是为了转换成秒
        String day = ((Long) (between / (24 * 3600))).toString();
        String hour = ((Long) (between % (24 * 3600) / 3600)).toString();
        String minute = ((Long) (between % 3600 / 60)).toString();
        String second = ((Long) (between % 60 / 60)).toString();

        if (hour.length() == 1) {
            hour = "0" + hour.toString();
        }
        if (minute.length() == 1) {
            minute = "0" + minute.toString();
        }
        if (second.length() == 1) {
            second = "0" + second.toString();
        }

        if (day.equals("0")) {
            return new StringBuffer().append(Math.abs(Integer.valueOf(hour))).append("小时").append(Math.abs(Integer.valueOf(minute))).append("分钟").toString();
        } else {
            return new StringBuffer().append(Math.abs(Integer.valueOf(day))).append("天  ").append(Math.abs(Integer.valueOf(hour))).append("小时").append(Math.abs(Integer.valueOf(minute))).append("分钟").toString();
        }
    }

    public static String getTimeDifferenceDance(long beginTime, long endTime) {
        String TimeDifference = "";
        try {
            Long diff = endTime - beginTime; //两时间差，精确到毫秒
            //以天数为单位取整
            Long day = diff / (1000 * 60 * 60 * 24);
            //以小时为单位取整
            Long hour = (diff / (60 * 60 * 1000) - day * 24);
            //以分钟为单位取整
            Long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            //以秒为单位
            Long second = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            String dayString = new StringBuffer().append(day).append("天  ").toString();
            String hourString = new StringBuffer().append(hour).append("小时").toString();
            String minString = new StringBuffer().append(min).append("分钟").toString();
            String secondString = new StringBuffer().append(second).append("秒").toString();
            if (day == 0) {
                dayString = "";
            } else if (hour == 0) {
                hourString = "";
            } else if (min == 0) {
                minString = "";
            }
            TimeDifference = new StringBuffer().append(dayString).append(hourString).append(minString).append(secondString).toString();
        } catch (Exception e) {
            //建议抛出总异常
            e.printStackTrace();
        }
        return TimeDifference;
    }

    public static int getAgeByBirthday(String birthday) throws ParseException {
        // 格式化传入的时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = format.parse(birthday);
        int age = 0;
        try {
            Calendar now = Calendar.getInstance();
            now.setTime(new Date()); // 当前时间

            Calendar birth = Calendar.getInstance();
            birth.setTime(parse); // 传入的时间

            //如果传入的时间，在当前时间的后面，返回0岁
            if (birth.after(now)) {
                age = 0;
            } else {
                age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
                if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
                    age += 1;
                }
            }
            return age;
        } catch (Exception e) {
            return 0;
        }
    }

}
