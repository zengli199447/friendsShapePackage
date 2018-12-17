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
import com.example.administrator.friendshape.model.bean.HomePageNetBean;
import com.example.administrator.friendshape.ui.holder.MyViewHolder;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/2.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ClassificationAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<HomePageNetBean.ResultBean.CategoryBean> category;

    public ClassificationAdapter(Context context, List<HomePageNetBean.ResultBean.CategoryBean> category) {
        this.context = context;
        this.category = category;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_class_button, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        ImageView class_button = holder.itemView.findViewById(R.id.class_button);
        TextView class_title = holder.itemView.findViewById(R.id.class_title);
        if (position == category.size()) {
            Glide.with(context).load(R.drawable.about_icon).error(R.drawable.banner_off).into(class_button);
            class_title.setText(context.getResources().getString(R.string.about));
        } else {
            HomePageNetBean.ResultBean.CategoryBean categoryBean = category.get(position);
            Glide.with(context).load(new StringBuffer().append(DataClass.URL).append(categoryBean.getImg()).toString()).error(R.drawable.banner_off).into(class_button);
            class_title.setText(categoryBean.getTitle());
        }

        if (classIficationClickListener != null)
            class_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    classIficationClickListener.onClassIficationClickListener(position);
                }
            });

    }

    @Override
    public int getItemCount() {
        return category.size() == 0 ? 0 : category.size() + 1;
    }

    public interface ClassIficationClickListener {
        void onClassIficationClickListener(int positon);
    }

    private ClassIficationClickListener classIficationClickListener;

    public void setClassIficationClickListener(ClassIficationClickListener classIficationClickListener) {
        this.classIficationClickListener = classIficationClickListener;

    }

}
