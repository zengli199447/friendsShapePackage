package com.example.administrator.friendshape.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.CityEntity;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.rxtools.RxBus;

import me.yokeyword.indexablerv.IndexableAdapter;

/**
 * 作者：真理 Created by Administrator on 2018/10/29.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class CityScreeningAdapter extends IndexableAdapter<CityEntity> {

    Context context;
    private final LayoutInflater mInflater;

    public CityScreeningAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateTitleViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_index_contact, parent, false);
        return new IndexVH(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_contact, parent, false);
        return new CityScreeningHolder(view);
    }

    @Override
    public void onBindTitleViewHolder(RecyclerView.ViewHolder holder, String indexTitle) {
        IndexVH vh = (IndexVH) holder;
        vh.tv.setText(indexTitle);
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder holder, final CityEntity entity) {
        CityScreeningHolder cityScreeningHolder = (CityScreeningHolder) holder;
        cityScreeningHolder.tvName.setText(entity.getNick());
        cityScreeningHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataClass.CITY = entity.getNick();
                ((Activity) context).finish();
            }
        });

    }

    private class IndexVH extends RecyclerView.ViewHolder {
        TextView tv;

        public IndexVH(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_index);
        }
    }

    private class CityScreeningHolder extends RecyclerView.ViewHolder {
        TextView tvName;

        public CityScreeningHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }

}
