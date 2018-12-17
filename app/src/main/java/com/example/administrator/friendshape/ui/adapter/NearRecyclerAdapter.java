package com.example.administrator.friendshape.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.ui.holder.MyViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：真理 Created by Administrator on 2018/10/30.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class NearRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    private final List<String> dynamic;
    private final List<String> people_nearby;
    int type = 0;
    private final Map<Integer, CheckBox> views = new HashMap<>();

    public NearRecyclerAdapter(Context context, int type) {
        this.context = context;
        this.type = type;
        dynamic = Arrays.asList(context.getResources().getStringArray(R.array.dynamic));
        people_nearby = Arrays.asList(context.getResources().getStringArray(R.array.people_nearby));
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_screening, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CheckBox dynamicView = holder.itemView.findViewById(R.id.dynamic);
        CheckBox peopleNearbyView = holder.itemView.findViewById(R.id.people_nearby);
        View dynamic_line = holder.itemView.findViewById(R.id.dynamic_line);
        View people_nearby_line = holder.itemView.findViewById(R.id.people_nearby_line);

        switch (type) {
            case 0:
                dynamicView.setText(dynamic.get(position));
                ClickListener(dynamicView, dynamic.get(position), position);
                dynamic_line.setVisibility(View.VISIBLE);
                break;
            case 1:
                peopleNearbyView.setText(people_nearby.get(position));
                ClickListener(peopleNearbyView, people_nearby.get(position), position);
                people_nearby_line.setVisibility(View.VISIBLE);
                break;
            case 2:
                peopleNearbyView.setText(people_nearby.get(position));
                people_nearby_line.setVisibility(View.VISIBLE);
                views.put(position, peopleNearbyView);
                ClickListener(peopleNearbyView, people_nearby.get(position), position);
                break;
        }
    }

    private void refreshView(int index) {
        for (int i = 0; i < views.size(); i++) {
            if (i != index) {
                views.get(i).setChecked(false);
            }
        }
    }

    private void ClickListener(View view, final String content, final int positon) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 2) {
                    refreshView(positon);
                    if (nearRecyclerClickListener != null)
                        nearRecyclerClickListener.nearRecyclerCombinationListener(content);
                } else {
                    if (nearRecyclerClickListener != null)
                        nearRecyclerClickListener.nearRecyclerClickListener(content);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        int size = 0;
        switch (type) {
            case 0:
                size = dynamic.size();
                break;
            case 1:
                size = people_nearby.size();
                break;
            case 2:
                size = people_nearby.size();
                break;
        }
        return size;
    }

    public interface NearRecyclerClickListener {
        void nearRecyclerClickListener(String s);

        void nearRecyclerCombinationListener(String s);
    }

    private NearRecyclerClickListener nearRecyclerClickListener;

    public void setNearRecyclerClickListener(NearRecyclerClickListener nearRecyclerClickListener) {
        this.nearRecyclerClickListener = nearRecyclerClickListener;
    }

}
