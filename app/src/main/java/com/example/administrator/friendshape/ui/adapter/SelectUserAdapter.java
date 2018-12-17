package com.example.administrator.friendshape.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.CancleGroupNetBean;
import com.example.administrator.friendshape.ui.holder.MyViewHolder;
import com.example.administrator.friendshape.utils.SystemUtil;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/12.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class SelectUserAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<CancleGroupNetBean.ResultBean.UsersBean> users;


    public SelectUserAdapter(Context context, List<CancleGroupNetBean.ResultBean.UsersBean> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_group_user, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        CancleGroupNetBean.ResultBean.UsersBean usersBean = users.get(position);
        ImageView user_img = holder.itemView.findViewById(R.id.user_img);
        TextView user_name = holder.itemView.findViewById(R.id.user_name);
        CheckStatus(holder,usersBean);
        Glide.with(context).load(SystemUtil.JudgeUrl(usersBean.getPhoto())).error(R.drawable.banner_off).into(user_img);
        user_name.setText(usersBean.getSecondname());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkClickListener != null)
                    checkClickListener.onCheckClickListener(position);
            }
        });

    }

    public void CheckStatus(MyViewHolder holder, CancleGroupNetBean.ResultBean.UsersBean usersBean) {
        View check_select = holder.itemView.findViewById(R.id.check_select);
        if (usersBean.isStatus()) {
            check_select.setVisibility(View.VISIBLE);
        } else {
            check_select.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return users.size() == 0 ? 0 : users.size();
    }

    public interface CheckClickListener {
        void onCheckClickListener(int position);
    }

    private CheckClickListener checkClickListener;

    public void setCheckClickListener(CheckClickListener checkClickListener) {
        this.checkClickListener = checkClickListener;
    }


}
