package com.example.administrator.friendshape.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.GroupAboutNetBean;
import com.example.administrator.friendshape.ui.holder.MyViewHolder;
import com.example.administrator.friendshape.utils.SystemUtil;
import com.example.administrator.friendshape.widget.CalendarBuilder;

import java.util.Date;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/15.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class GroupAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    private List<GroupAboutNetBean.ResultBean.GroupBean> list;

    // 普通布局
    private final int TYPE_ITEM = 1;
    // 脚布局
    private final int TYPE_FOOTER = 2;
    // 当前加载状态，默认为加载完成
    private int loadState = 2;
    // 加载完成
    public final int LOADING_COMPLETE = 2;
    // 加载到底
    public final int LOADING_END = 3;

    // 正在加载
    public final int LOADING = 1;

    public GroupAdapter(Context context, List<GroupAboutNetBean.ResultBean.GroupBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        int Type = 0;
        if (position == list.size()) {
            Type = TYPE_FOOTER;
        } else {
            Type = TYPE_ITEM;
        }
        return Type;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_about, parent, false);
                break;
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer_view, parent, false);
                break;
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ImageView user_img = holder.itemView.findViewById(R.id.user_img);
        TextView user_content = holder.itemView.findViewById(R.id.user_content);
        TextView no_single_boy = holder.itemView.findViewById(R.id.no_single_boy);
        TextView no_single_girl = holder.itemView.findViewById(R.id.no_single_girl);
        TextView merchants_content = holder.itemView.findViewById(R.id.merchants_content);
        ProgressBar progress = holder.itemView.findViewById(R.id.progress);
        TextView tuxedo = holder.itemView.findViewById(R.id.tuxedo);
        TextView money_date = holder.itemView.findViewById(R.id.money_date);
        TextView chat = holder.itemView.findViewById(R.id.chat);

        View line = holder.itemView.findViewById(R.id.line);

        RelativeLayout progress_bar = holder.itemView.findViewById(R.id.progress_bar);
        if (position == list.size()) {
            switch (loadState) {
                case LOADING: // 正在加载
                    progress_bar.setVisibility(View.VISIBLE);
                    break;
                case LOADING_COMPLETE:
                    // 加载完成
                    progress_bar.setVisibility(View.INVISIBLE);
                    break;
                case LOADING_END:
                    // 加载到底
                    progress_bar.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        } else {
            GroupAboutNetBean.ResultBean.GroupBean groupBean = list.get(position);

            Glide.with(context).load(SystemUtil.JudgeUrl(groupBean.getPhoto())).error(R.drawable.banner_off).into(user_img);
            SystemUtil.textMagicTool(context, user_content, groupBean.getSecondname(),
                    groupBean.getCreatedate(), R.dimen.dp14, R.dimen.dp12, R.color.black, R.color.black_overlay, "\n\n");

            merchants_content.setText(groupBean.getTitle());

            progress.setMax(Integer.valueOf(groupBean.getMoney()));
            progress.setProgress(Integer.valueOf(groupBean.getMoney_total()));

            if ("1".equals(groupBean.getIffree_boy())) {
                no_single_boy.setVisibility(View.VISIBLE);
                no_single_girl.setVisibility(View.GONE);
            } else if ("1".equals(groupBean.getIffree_girl())) {
                no_single_girl.setVisibility(View.VISIBLE);
                no_single_boy.setVisibility(View.GONE);
            } else {
                no_single_boy.setVisibility(View.GONE);
                no_single_girl.setVisibility(View.GONE);
            }

            String money = new StringBuffer().append(context.getString(R.string.money)).append(": ").append(groupBean.getMoney_total()).append("/").append(groupBean.getMoney()).toString();
            String timeDifference;
            if ("1".equals(groupBean.getIfcanjoin())) {
                tuxedo.setBackground(context.getResources().getDrawable(R.drawable.corners_the_gradient_bg));
                timeDifference = CalendarBuilder.getTimeDifference(new Date().getTime(), CalendarBuilder.getFormatLongMinDate(groupBean.getEndtime()));
            } else {
                timeDifference = " ";
                tuxedo.setBackground(context.getResources().getDrawable(R.drawable.corners_solid_text_gray));
            }
            SystemUtil.textMagicTool(context, money_date, money, timeDifference, R.dimen.dp13, R.dimen.dp13, R.color.black_overlay, R.color.black_overlay, "\n");

            if (position == 0) {
                line.setVisibility(View.GONE);
            } else {
                line.setVisibility(View.VISIBLE);
            }

            CheckClickListener(holder.itemView, position);
            CheckClickListener(tuxedo, position);
            CheckClickListener(chat, position);
        }
    }

    private void CheckClickListener(final View view, final int postion) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkClickListener != null)
                    checkClickListener.onCheckClickListener(view, postion);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size() + 1;
    }

    /**
     * 设置上拉加载状态 * * @param loadState 0.正在加载 1.加载完成 2.加载到底
     */
    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }

    public interface CheckClickListener {
        void onCheckClickListener(View view, int position);
    }

    private CheckClickListener checkClickListener;

    public void setCheckClickListener(CheckClickListener checkClickListener) {
        this.checkClickListener = checkClickListener;
    }

}
