package com.example.administrator.friendshape.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.model.bean.MerchantsTypeFormNetBean;
import com.example.administrator.friendshape.ui.holder.MyViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：真理 Created by Administrator on 2018/11/5.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class MerchantsTypeRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<MerchantsTypeFormNetBean.ResultBean> list;

    public MerchantsTypeRecyclerAdapter(Context context, List<MerchantsTypeFormNetBean.ResultBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_merchants_type, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        TextView merchant_class = holder.itemView.findViewById(R.id.merchant_class);
        merchant_class.setText(list.get(position).getTitle());
        checkClickListener(holder.itemView,position);
    }

    private void checkClickListener(View view , final int position){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (merchantsRecyclerClickListener != null)
                    merchantsRecyclerClickListener.MerchantsRecyclerClickListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size();
    }

    public interface MerchantsRecyclerClickListener {
        void MerchantsRecyclerClickListener(int position);
    }

    private MerchantsRecyclerClickListener merchantsRecyclerClickListener;

    public void setMerchantsRecyclerClickListener(MerchantsRecyclerClickListener merchantsRecyclerClickListener) {
        this.merchantsRecyclerClickListener = merchantsRecyclerClickListener;
    }

}
