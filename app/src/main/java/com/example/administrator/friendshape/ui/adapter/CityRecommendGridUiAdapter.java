package com.example.administrator.friendshape.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.friendshape.R;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/10/29.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class CityRecommendGridUiAdapter extends BaseAdapter {

    private String[] list;
    private Context context;
    private LayoutInflater inflater;

    public CityRecommendGridUiAdapter(Context context, String[] list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.length;
    }

    @Override
    public Object getItem(int position) {
        return list[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_city_recommend_grid_ui, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.item_gridview_cyb_change_city_tv.setText(list[position]);
        return convertView;
    }

    class ViewHolder {
        private TextView item_gridview_cyb_change_city_tv;

        public ViewHolder(View view) {
            item_gridview_cyb_change_city_tv = (TextView) view.findViewById(R.id.item_gridview_cyb_change_city_tv);
        }
    }

}
