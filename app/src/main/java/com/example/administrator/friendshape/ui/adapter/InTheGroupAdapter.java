package com.example.administrator.friendshape.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.MerchantsContentsNetWorkBean;
import com.example.administrator.friendshape.ui.holder.MyViewHolder;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.utils.SystemUtil;
import com.example.administrator.friendshape.widget.CalendarBuilder;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 作者：真理 Created by Administrator on 2018/11/7.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class InTheGroupAdapter extends RecyclerView.Adapter<MyViewHolder> {

    String TAG = getClass().getSimpleName();

    Context context;
    List<MerchantsContentsNetWorkBean.ResultBean.GroupBean> groupList;
    boolean status;

    public InTheGroupAdapter(Context context, List<MerchantsContentsNetWorkBean.ResultBean.GroupBean> groupList, boolean status) {
        this.context = context;
        this.groupList = groupList;
        this.status = status;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_in_the_group, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        MerchantsContentsNetWorkBean.ResultBean.GroupBean groupBean = groupList.get(position);

        ImageView group_user_img = holder.itemView.findViewById(R.id.group_user_img);
        TextView the_initiator = holder.itemView.findViewById(R.id.the_initiator);
        TextView details = holder.itemView.findViewById(R.id.details);
        TextView current_personnel_status = holder.itemView.findViewById(R.id.current_personnel_status);
        final TextView time_limit = holder.itemView.findViewById(R.id.time_limit);
        View line = holder.itemView.findViewById(R.id.line);

//        final String timeDifference = CalendarBuilder.getTimeDifference(new Date().getTime(), CalendarBuilder.getFormatLongMinDate(groupBean.getEndtime()));

        final String timeDifferenceDance = CalendarBuilder.getTimeDifferenceDance(new Date().getTime(), CalendarBuilder.getFormatLongMinDate(groupBean.getEndtime()));

        time_limit.setText(new StringBuffer().append(context.getString(R.string.for_the_rest_of)).append("  ").append(timeDifferenceDance).toString());

        Glide.with(context).load(SystemUtil.JudgeUrl(groupBean.getPhoto())).error(R.drawable.banner_off).into(group_user_img);
        the_initiator.setText(groupBean.getSecondname());

        if (position == 0) {
            line.setVisibility(View.GONE);
        } else {
            line.setVisibility(View.VISIBLE);
        }

        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkClickListener != null)
                    checkClickListener.onCheckClickListener(position);
            }
        });

        int boyIndex = Integer.valueOf(groupBean.getNeed_boy());
        int girlIndex = Integer.valueOf(groupBean.getNeed_girl());
        String boy = new StringBuffer().append("<font color='#6aacff'>").append(boyIndex).append("</font>").append(context.getString(R.string.gender_boy)).toString();
        String girl = new StringBuffer().append("<font color='#6aacff'>").append(girlIndex).append("</font>").append(context.getString(R.string.gender_girl)).toString();
        if (boyIndex == 0 && girlIndex == 0) {
            current_personnel_status.setText(context.getString(R.string.researchers_have_full));
            return;
        } else if (boyIndex > 0 && girlIndex == 0) {
            girl = "";
        } else if (boyIndex == 0 && girlIndex > 0) {
            boy = "";
        }
        String s = new StringBuffer().append(context.getString(R.string.gap)).append(boy).append(girl).append(context.getString(R.string.composition)).toString();
        current_personnel_status.setText(Html.fromHtml(s));

    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (status) {
            if (groupList.size() == 2 | groupList.size() > 2) {
                size = 2;
            } else if (0 < groupList.size() && groupList.size() < 2) {
                size = groupList.size();
            }
        } else {
            size = groupList.size();
        }
        return size;
    }

    public interface CheckClickListener {
        void onCheckClickListener(int position);
    }

    private CheckClickListener checkClickListener;

    public void setCheckClickListener(CheckClickListener checkClickListener) {
        this.checkClickListener = checkClickListener;
    }

}
