package com.example.administrator.friendshape.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.PeopleNearbyNetBean;
import com.example.administrator.friendshape.ui.holder.MyViewHolder;
import com.example.administrator.friendshape.utils.SystemUtil;
import com.example.administrator.friendshape.widget.CalendarBuilder;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/15.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class PeopleNearbyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<PeopleNearbyNetBean.ResultBean.UserBean> list;

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

    public PeopleNearbyAdapter(Context context, List<PeopleNearbyNetBean.ResultBean.UserBean> list) {
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
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_people_nearby, parent, false);
                break;
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer_view, parent, false);
                break;
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        ImageView user_img = holder.itemView.findViewById(R.id.user_img);
        TextView user_content = holder.itemView.findViewById(R.id.user_content);
        TextView distance = holder.itemView.findViewById(R.id.distance);
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
            PeopleNearbyNetBean.ResultBean.UserBean userBean = list.get(position);

            Glide.with(context).load(SystemUtil.JudgeUrl(userBean.getPhoto())).error(R.drawable.banner_off).into(user_img);

            String age = null;
            if (userBean.getAge().isEmpty() || "0".equals(userBean.getAge())) {
                age = "";
            } else {
                age = new StringBuffer().append(userBean.getAge()).append(context.getString(R.string.at_the_age)).toString();
            }
            SystemUtil.textMagicTool(context, user_content, userBean.getSecondname(),
                    new StringBuffer().append(userBean.getSex()).append("  ").append(age).toString(), R.dimen.dp14, R.dimen.dp12, R.color.black, R.color.gray_light_text, "\n");

            distance.setText(SystemUtil.JudgeFormatDistance(userBean.getDistance()));

            if (position == list.size() - 1) {
                line.setVisibility(View.GONE);
            } else {
                line.setVisibility(View.VISIBLE);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkClickListener != null)
                        checkClickListener.onCheckClickListener(position);
                }
            });
        }

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
        void onCheckClickListener(int position);
    }

    private CheckClickListener checkClickListener;

    public void setCheckClickListener(CheckClickListener checkClickListener) {
        this.checkClickListener = checkClickListener;
    }


}
