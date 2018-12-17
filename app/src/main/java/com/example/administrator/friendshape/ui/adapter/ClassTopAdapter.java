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
import com.example.administrator.friendshape.model.bean.HomePageNetBean;
import com.example.administrator.friendshape.ui.holder.MyViewHolder;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/2.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ClassTopAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<HomePageNetBean.ResultBean.ShopTopBean> shopTop;

    public ClassTopAdapter(Context context, List<HomePageNetBean.ResultBean.ShopTopBean> shopTop) {
        this.context = context;
        this.shopTop = shopTop;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_class_top, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        ImageView img_top = holder.itemView.findViewById(R.id.img_top);
        Glide.with(context).load(new StringBuffer().append(DataClass.URL).append(shopTop.get(position).getImg()).toString()).error(R.drawable.banner_off).into(img_top);
        img_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (classTopClickListener != null)
                    classTopClickListener.onClassTopClickListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shopTop.size() == 0 ? 0 : shopTop.size();
    }

    public interface ClassTopClickListener {
        void onClassTopClickListener(int position);
    }

    private ClassTopClickListener classTopClickListener;

    public void setClassTopClickListener(ClassTopClickListener classTopClickListener) {
        this.classTopClickListener = classTopClickListener;
    }


}
