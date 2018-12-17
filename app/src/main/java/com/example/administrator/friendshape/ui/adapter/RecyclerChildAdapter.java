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
import com.example.administrator.friendshape.model.bean.PackingNetBeanPhoto;
import com.example.administrator.friendshape.ui.holder.MyViewHolder;
import com.example.administrator.friendshape.utils.SystemUtil;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/13.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class RecyclerChildAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<PackingNetBeanPhoto> list;
    boolean viewStatus;

    public RecyclerChildAdapter(Context context, List<PackingNetBeanPhoto> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dynamic_photo, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final PackingNetBeanPhoto packingNetBeanPhoto = list.get(position);
        ImageView photo_img = holder.itemView.findViewById(R.id.photo_img);
        if (viewStatus) {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (list.size() > 2) {
                layoutParams.height = SystemUtil.dp2px(context, (DataClass.WINDOWS_WIDTH / 3) - 10);
            } else {
                layoutParams.height = SystemUtil.dp2px(context, DataClass.WINDOWS_WIDTH * 2 / 5);
            }
            holder.itemView.setLayoutParams(layoutParams);
        }

        Glide.with(context).load(SystemUtil.JudgeUrl(packingNetBeanPhoto.getPhoto())).error(R.drawable.banner_off).into(photo_img);
        photo_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (childClickListener != null)
                    childClickListener.onChildClickListener(position, packingNetBeanPhoto);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list.size() == 1 || list.size() > 2)
            viewStatus = true;
        return list.size() == 0 ? 0 : list.size();
    }

    public interface ChildClickListener {
        void onChildClickListener(int position, PackingNetBeanPhoto packingNetBeanPhoto);
    }

    private ChildClickListener childClickListener;

    public void setChildClickListener(ChildClickListener childClickListener) {
        this.childClickListener = childClickListener;
    }


}
