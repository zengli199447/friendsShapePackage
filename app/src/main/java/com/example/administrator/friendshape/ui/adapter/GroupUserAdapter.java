package com.example.administrator.friendshape.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.OredeContentNetBean;
import com.example.administrator.friendshape.ui.holder.MyViewHolder;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.utils.SystemUtil;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/10.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class GroupUserAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<OredeContentNetBean.ResultBean.UserBean> list;
    private String TAG = getClass().getSimpleName();

    public GroupUserAdapter(Context context, List<OredeContentNetBean.ResultBean.UserBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_in_the_group_user, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        OredeContentNetBean.ResultBean.UserBean userBean = list.get(position);
        ImageView group_user_img = holder.itemView.findViewById(R.id.group_user_img);
        Glide.with(context).load(SystemUtil.JudgeUrl(userBean.getPhoto())).error(R.drawable.banner_off).into(group_user_img);
        group_user_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (oredeContentClickListener != null)
                    oredeContentClickListener.onOredeContentClickListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size();
    }

    public interface OredeContentClickListener {
        void onOredeContentClickListener(int position);
    }

    private OredeContentClickListener oredeContentClickListener;

    public void setOredeContentClickListener(OredeContentClickListener oredeContentClickListener) {
        this.oredeContentClickListener = oredeContentClickListener;
    }


}
