package com.example.administrator.friendshape.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.model.bean.CallPhoneNetBean;
import com.example.administrator.friendshape.ui.holder.MyViewHolder;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/10/31.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ServicePhoneAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<CallPhoneNetBean.ResultBean> list;

    public ServicePhoneAdapter(Context context, List<CallPhoneNetBean.ResultBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_phone, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        TextView phone_number = holder.itemView.findViewById(R.id.phone_number);
        TextView call = holder.itemView.findViewById(R.id.call);
        phone_number.setText(list.get(position).getTitle());
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCallClickListener != null)
                    onCallClickListener.onCallClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size();
    }

    public interface onCallClickListener {
        void onCallClick(int positon);
    }

    private onCallClickListener onCallClickListener;

    public void setOnCallClickListener(onCallClickListener onCallClickListener) {
        this.onCallClickListener = onCallClickListener;
    }


}
