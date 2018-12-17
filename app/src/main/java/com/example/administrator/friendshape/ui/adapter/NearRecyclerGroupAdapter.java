package com.example.administrator.friendshape.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.model.bean.GroupAboutNetBean;
import com.example.administrator.friendshape.ui.holder.MyViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：真理 Created by Administrator on 2018/10/30.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class NearRecyclerGroupAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<GroupAboutNetBean.ResultBean.ServicecategoryBean> strings;
    private final Map<Integer, CheckBox> views = new HashMap<>();

    public NearRecyclerGroupAdapter(Context context, List<GroupAboutNetBean.ResultBean.ServicecategoryBean> strings) {
        this.context = context;
        this.strings = strings;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_screening_group, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        CheckBox group_select = holder.itemView.findViewById(R.id.group_select);
        final GroupAboutNetBean.ResultBean.ServicecategoryBean servicecategoryBean = strings.get(position);
        group_select.setText(servicecategoryBean.getTitle());
        views.put(position, group_select);
        group_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshView(position);
                if (nearGroupRecyclerClickListener != null)
                    nearGroupRecyclerClickListener.nearGroupRecyclerClickListener(position, servicecategoryBean.getTitle());
            }
        });
    }

    private void refreshView(int index) {
        for (int i = 0; i < views.size(); i++) {
            if (i != index) {
                views.get(i).setChecked(false);
            }
        }
    }


    @Override
    public int getItemCount() {
        return strings.size() == 0 ? 0 : strings.size();
    }

    public interface NearGroupRecyclerClickListener {
        void nearGroupRecyclerClickListener(int position, String s);
    }

    private NearGroupRecyclerClickListener nearGroupRecyclerClickListener;

    public void setNearGroupRecyclerClickListener(NearGroupRecyclerClickListener nearGroupRecyclerClickListener) {
        this.nearGroupRecyclerClickListener = nearGroupRecyclerClickListener;
    }


}
