package com.example.administrator.friendshape.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
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
import com.example.administrator.friendshape.model.bean.FriendNetBean;
import com.example.administrator.friendshape.ui.holder.MyViewHolder;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.utils.SystemUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：真理 Created by Administrator on 2018/11/12.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class FriendsSelectAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private String TAG = getClass().getSimpleName();

    Context context;
    private List<FriendNetBean.ResultBean.FriendBean> list;

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

    public FriendsSelectAdapter(Context context, List<FriendNetBean.ResultBean.FriendBean> list) {
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
                view = LayoutInflater.from(context).inflate(R.layout.item_friends_select, parent, false);
                break;
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer_view, parent, false);
                break;
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final AppCompatCheckBox check_select = holder.itemView.findViewById(R.id.check_select);
        ImageView friend_img = holder.itemView.findViewById(R.id.friend_img);
        TextView friend_content = holder.itemView.findViewById(R.id.friend_content);
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
            final FriendNetBean.ResultBean.FriendBean friendBean = list.get(position);
            Glide.with(context).load(SystemUtil.JudgeUrl(friendBean.getPhoto())).error(R.drawable.banner_off).into(friend_img);
            SystemUtil.textMagicTool(context, friend_content, friendBean.getSecondname(), new StringBuffer().append(friendBean.getSex()).append("      ").append(friendBean.getAge())
                    .append(context.getString(R.string.age)).toString(), R.dimen.dp14, R.dimen.dp12, R.color.black, R.color.gray_light_text, "\n");

            if (position == 0) {
                line.setVisibility(View.GONE);
            } else {
                line.setVisibility(View.VISIBLE);
            }

            if (friendBean.isSelect()) {
                check_select.setChecked(true);
            } else {
                check_select.setChecked(false);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckRefreshView(check_select, position);
                }
            });
        }
    }

    private void CheckRefreshView(AppCompatCheckBox checkSelect, int position) {
        if (checkSelect.isChecked()) {
            checkSelect.setChecked(false);
            checkItemListener.onCheckItemListener(position, false);
        } else {
            checkSelect.setChecked(true);
            checkItemListener.onCheckItemListener(position, true);
        }
    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size() + 1;
    }

    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }

    public interface CheckItemListener {
        void onCheckItemListener(int position, boolean status);
    }

    private CheckItemListener checkItemListener;

    public void setCheckItemListener(CheckItemListener checkItemListener) {
        this.checkItemListener = checkItemListener;
    }


}
