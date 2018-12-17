package com.example.administrator.friendshape.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.rxtools.RxBus;
import com.example.administrator.friendshape.ui.view.IsometricGridView;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.indexablerv.IndexableHeaderAdapter;

/**
 * 作者：真理 Created by Administrator on 2018/10/29.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class CityRecommendedAdapter extends IndexableHeaderAdapter {

    private String[] city;
    private static final int TYPE = 1;
    Context context;
    private CityRecommendGridUiAdapter cityRecommendGridUiAdapter;

    public CityRecommendedAdapter(Context context, String index, String indexTitle, List datas, String[] city) {
        super(index, indexTitle, datas);
        this.context = context;
        this.city = city;
    }

    @Override
    public int getItemViewType() {
        return TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_city_recommended, parent, false);
        return new HotViewHolder(view);
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder holder, Object entity) {
        final HotViewHolder hotViewHolder = (HotViewHolder) holder;
        hotViewHolder.current_location.setText(DataClass.CITY);
        cityRecommendGridUiAdapter = new CityRecommendGridUiAdapter(context, city);
        hotViewHolder.head_home_change_city_gridview.setAdapter(cityRecommendGridUiAdapter);
        hotViewHolder.head_home_change_city_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String select = city[position];
                DataClass.CITY = select;
                ((Activity)context).finish();
            }
        });
        hotViewHolder.current_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = hotViewHolder.current_location.getText().toString();
                DataClass.CITY = location;
                ((Activity)context).finish();
            }
        });

    }

    private class HotViewHolder extends RecyclerView.ViewHolder {
        GridView head_home_change_city_gridview;
        TextView current_location;
        LinearLayout hot_layout;
        View line;

        public HotViewHolder(View itemView) {
            super(itemView);
            head_home_change_city_gridview = (IsometricGridView) itemView.findViewById(R.id.item_header_city_gridview);
            current_location = itemView.findViewById(R.id.current_location);
            hot_layout = itemView.findViewById(R.id.hot_layout);
            line = itemView.findViewById(R.id.line);
        }
    }

}
