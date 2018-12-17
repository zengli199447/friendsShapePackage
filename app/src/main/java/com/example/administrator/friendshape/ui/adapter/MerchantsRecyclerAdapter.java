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
import com.example.administrator.friendshape.model.bean.HomePageNetBean;
import com.example.administrator.friendshape.ui.holder.MyViewHolder;
import com.example.administrator.friendshape.ui.view.ShinyView;
import com.example.administrator.friendshape.utils.SystemUtil;

import java.security.InvalidParameterException;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/2.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class MerchantsRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<HomePageNetBean.ShopsBean> shops;

    public MerchantsRecyclerAdapter(Context context, List<HomePageNetBean.ShopsBean> shops) {
        this.context = context;
        this.shops = shops;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_class_personality, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        HomePageNetBean.ShopsBean shopsBean = shops.get(position);
        ImageView img = holder.itemView.findViewById(R.id.img);
        TextView title_name = holder.itemView.findViewById(R.id.title_name);
        ShinyView shiny_view = holder.itemView.findViewById(R.id.shiny_view);
        TextView consumption = holder.itemView.findViewById(R.id.consumption);
        TextView content = holder.itemView.findViewById(R.id.content);
        TextView location = holder.itemView.findViewById(R.id.location);
        TextView distance = holder.itemView.findViewById(R.id.distance);
        View line = holder.itemView.findViewById(R.id.line);

        Glide.with(context).load(SystemUtil.JudgeUrl(shopsBean.getPhoto())).error(R.drawable.banner_off).into(img);
        title_name.setText(shopsBean.getShopname());
        shiny_view.setStarMark(Float.valueOf(shopsBean.getScore()));
        if (!"0".equals(shopsBean.getMoney_avg()))
            consumption.setText(new StringBuffer().append(context.getString(R.string.per_capita)).append("￥").append(shopsBean.getMoney_avg()).toString());
        content.setText(new StringBuffer().append(context.getString(R.string.service_project)).append(shopsBean.getRemark()));
        location.setText(shopsBean.getAddress());
        String distanceValue = shopsBean.getDistance();
        distance.setText(SystemUtil.JudgeFormatDistance(distanceValue));

        if (position == shops.size() - 1) {
            line.setVisibility(View.GONE);
        } else {
            line.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (classPersonalityClickListener != null)
                    classPersonalityClickListener.onClassPersonalityClickListener(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return shops.size() == 0 ? 0 : shops.size();
    }

    public interface ClassPersonalityClickListener {
        void onClassPersonalityClickListener(int position);
    }

    private ClassPersonalityClickListener classPersonalityClickListener;

    public void setClassPersonalityClickListener(ClassPersonalityClickListener classPersonalityClickListener) {
        this.classPersonalityClickListener = classPersonalityClickListener;
    }

}
