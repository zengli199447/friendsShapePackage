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
import com.example.administrator.friendshape.model.bean.OrderaAllTypeNetBean;
import com.example.administrator.friendshape.ui.holder.MyViewHolder;
import com.example.administrator.friendshape.utils.SystemUtil;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/8.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class OrderTypeAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    private List<OrderaAllTypeNetBean.ResultBean.OrderBean> orderList;
    private int type;

    // 普通布局
    private final int TYPE_ITEM = 1;
    // 脚布局
    private final int TYPE_FOOTER = 2;
    // 当前加载状态，默认为加载完成
    private int loadState = 2;
    // 加载完成
    public final int LOADING_COMPLETE = 2;
    // 加载到底
    public final int LOADING_END = 3;
    // 正在加载
    public final int LOADING = 1;

    public OrderTypeAdapter(Context context, List<OrderaAllTypeNetBean.ResultBean.OrderBean> orderList) {
        this.orderList = orderList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        int Type = 0;
        if (position == orderList.size()) {
            Type = TYPE_FOOTER;
        } else {
            Type = TYPE_ITEM;
        }
        return Type;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_layout, parent, false);
                break;
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer_view, parent, false);
                break;
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        ImageView merchants_img = holder.itemView.findViewById(R.id.merchants_img);
        TextView merchants_name = holder.itemView.findViewById(R.id.merchants_name);
        TextView order_status = holder.itemView.findViewById(R.id.order_status);
        TextView creat_time = holder.itemView.findViewById(R.id.creat_time);
        TextView money = holder.itemView.findViewById(R.id.money);
        final TextView setting_order_status = holder.itemView.findViewById(R.id.setting_order_status);
        View line = holder.itemView.findViewById(R.id.line);
        RelativeLayout progress_bar = holder.itemView.findViewById(R.id.progress_bar);
        RelativeLayout contorller_layout = holder.itemView.findViewById(R.id.contorller_layout);

        if (position == orderList.size()) {
            switch (loadState) {
                case LOADING:
                    progress_bar.setVisibility(View.VISIBLE);
                    break;
                case LOADING_COMPLETE:
                    progress_bar.setVisibility(View.INVISIBLE);
                    break;
                case LOADING_END:
                    progress_bar.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        } else {
            OrderaAllTypeNetBean.ResultBean.OrderBean orderBean = orderList.get(position);
            Glide.with(context).load(SystemUtil.JudgeUrl(orderBean.getPhoto())).error(R.drawable.banner_off).into(merchants_img);
            merchants_name.setText(orderBean.getShopname());
            order_status.setText(orderBean.getState_group());
            creat_time.setText(orderBean.getDate_show());
            money.setText(orderBean.getMoneypay_show());

            if (orderBean.getState_group().equals(context.getString(R.string.to_be_paid))) {
                setting_order_status.setText(context.getString(R.string.pay));
                order_status.setTextColor(context.getResources().getColor(R.color.blue_bar));
            } else if (orderBean.getState_group().equals(context.getString(R.string.stay_a_team))) {
                setting_order_status.setText(context.getString(R.string.cancle_order));
                order_status.setTextColor(context.getResources().getColor(R.color.red_text));
            } else if (orderBean.getState_group().equals(context.getString(R.string.stay_consumption))) {
                setting_order_status.setText(context.getString(R.string.cancle_order));
                order_status.setTextColor(context.getResources().getColor(R.color.blue_bar));
            } else if (orderBean.getState_group().equals(context.getString(R.string.already_consumption))) {
                setting_order_status.setText(context.getString(R.string.evaluation_order));
                order_status.setTextColor(context.getResources().getColor(R.color.black));
            } else if (orderBean.getState_group().equals(context.getString(R.string.cancelled))) {
                setting_order_status.setText(orderBean.getState_pay());
                order_status.setTextColor(context.getResources().getColor(R.color.black));
            }

            if (orderBean.getState_group().equals(context.getString(R.string.cancelled)) || orderBean.getState_group().equals(context.getString(R.string.stay_consumption))) {
                contorller_layout.setVisibility(View.GONE);
                if (orderBean.getState_pay().equals(context.getString(R.string.for_a_refund))) {
                    setting_order_status.setText(orderBean.getState_pay());
                    order_status.setTextColor(context.getResources().getColor(R.color.black));
                    contorller_layout.setVisibility(View.VISIBLE);
                }
            } else {
                if (orderBean.getState_pay().equals(context.getString(R.string.to_be_paid)) || orderBean.getState().equals(context.getString(R.string.already_consumption)) || orderBean.getState().equals(context.getString(R.string.for_a_refund)) || orderBean.getState_group().equals(context.getString(R.string.to_be_paid))) {
                    contorller_layout.setVisibility(View.VISIBLE);
                } else {
                    contorller_layout.setVisibility(View.GONE);
                }
            }

            if (position == orderList.size() - 1) {
                line.setVisibility(View.GONE);
            } else {
                line.setVisibility(View.VISIBLE);
            }

            if (orderClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        orderClickListener.OrderClickListener(position);
                    }
                });
                setting_order_status.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        orderClickListener.ControllerCheckListener(position, setting_order_status.getText().toString());
                    }
                });
            }
        }

    }

    @Override
    public int getItemCount() {
        return orderList.size() == 0 ? 0 : orderList.size() + 1;
    }

    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }

    public interface OrderClickListener {
        void ControllerCheckListener(int position, String content);

        void OrderClickListener(int position);
    }

    private OrderClickListener orderClickListener;

    public void setOrderClickListener(OrderClickListener orderClickListener) {
        this.orderClickListener = orderClickListener;
    }

}
