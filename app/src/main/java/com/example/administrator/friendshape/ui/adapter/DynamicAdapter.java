package com.example.administrator.friendshape.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.GridLayoutManager;
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
import com.example.administrator.friendshape.model.bean.DynamicNetBean;
import com.example.administrator.friendshape.model.bean.PackingNetBeanPhoto;
import com.example.administrator.friendshape.ui.holder.MyViewHolder;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.utils.SystemUtil;
import com.example.administrator.friendshape.widget.FullyGridLayoutManager;
import com.example.administrator.friendshape.widget.ViewBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/13.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class DynamicAdapter extends RecyclerView.Adapter<MyViewHolder> implements RecyclerChildAdapter.ChildClickListener {

    Context context;
    private List<DynamicNetBean.ResultBean.NewsBean> list;
    private int dynamicType;

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

    public DynamicAdapter(Context context, List<DynamicNetBean.ResultBean.NewsBean> list, int dynamicType) {
        this.context = context;
        this.list = list;
        this.dynamicType = dynamicType;
    }

    @Override
    public int getItemViewType(int position) {
        int Type = 0;
        if (position == list.size()) {
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
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dynamic_the_parent, parent, false);
                break;
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer_view, parent, false);
                break;
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        ImageView user_img = holder.itemView.findViewById(R.id.user_img);
        TextView user_name = holder.itemView.findViewById(R.id.user_name);
        TextView distance = holder.itemView.findViewById(R.id.distance);

        TextView dynamic_content = holder.itemView.findViewById(R.id.dynamic_content);
        RecyclerView recycler_view = holder.itemView.findViewById(R.id.recycler_view);
        RelativeLayout recycler_view_layout = holder.itemView.findViewById(R.id.recycler_view_layout);

        TextView creat_time = holder.itemView.findViewById(R.id.creat_time);
        AppCompatCheckBox support_check = holder.itemView.findViewById(R.id.support_check);
        TextView comments = holder.itemView.findViewById(R.id.comments);
        TextView code_violation = holder.itemView.findViewById(R.id.code_violation);
        View line = holder.itemView.findViewById(R.id.line);

        RelativeLayout progress_bar = holder.itemView.findViewById(R.id.progress_bar);
        if (position == list.size()) {
            switch (loadState) {
                case LOADING: // 正在加载
                    progress_bar.setVisibility(View.VISIBLE);
                    break;
                case LOADING_COMPLETE:
                    // 加载完成
                    progress_bar.setVisibility(View.INVISIBLE);
                    break;
                case LOADING_END:
                    // 加载到底
                    progress_bar.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        } else {
            DynamicNetBean.ResultBean.NewsBean newsBean = list.get(position);

            user_name.setText(newsBean.getSecondname());
            dynamic_content.setText(newsBean.getContent());
            creat_time.setText(newsBean.getCreatedate());

            List<String> imgarr = newsBean.getImgarr();
            List<DynamicNetBean.ResultBean.NewsBean.ReplyBean> reply = newsBean.getReply();
            ArrayList<PackingNetBeanPhoto> packingNetBeanPhotoArrayList = new ArrayList<>();

            Glide.with(context).load(SystemUtil.JudgeUrl(newsBean.getPhoto())).error(R.drawable.banner_off).into(user_img);

            comments.setText(SystemUtil.JudgeCount(reply.size()));

            if (imgarr.size() > 0) {
                for (String photo : imgarr) {
                    packingNetBeanPhotoArrayList.add(new PackingNetBeanPhoto(photo, position));
                }
                recycler_view_layout.setVisibility(View.VISIBLE);
                initRecyclerView(recycler_view, packingNetBeanPhotoArrayList);
            } else if (imgarr.size() == 0 && newsBean.getImgs().isEmpty()) {
                recycler_view_layout.setVisibility(View.GONE);
            } else {
                packingNetBeanPhotoArrayList.add(new PackingNetBeanPhoto(newsBean.getImgs(), position));
                recycler_view_layout.setVisibility(View.VISIBLE);
                initRecyclerView(recycler_view, packingNetBeanPhotoArrayList);
            }

            if (newsBean.getContent().isEmpty()) {
                dynamic_content.setVisibility(View.GONE);
            } else {
                dynamic_content.setVisibility(View.VISIBLE);
            }

            if (dynamicType == 3) {
                ViewBuilder.textDrawable(distance, context, R.drawable.clear_icon, 0);
                distance.setText("");
                distance.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dynamicParentClickListener != null)
                            dynamicParentClickListener.onClearCheckClickListener(position);
                    }
                });
            } else {
                distance.setText(SystemUtil.JudgeFormatDistance(newsBean.getDistance()));
            }

            if (position == list.size() - 1) {
                line.setVisibility(View.GONE);
            } else {
                line.setVisibility(View.VISIBLE);
            }

            refreshCheckView(holder, newsBean, R.id.support_check);

            initCheckClickListener(user_img, position);
            initCheckClickListener(support_check, position);
            initCheckClickListener(comments, position);
            initCheckClickListener(code_violation, position);
            initCheckClickListener(holder.itemView, position);
        }

    }

    //view刷新
    public void refreshCheckView(MyViewHolder holder, DynamicNetBean.ResultBean.NewsBean newsBean, int id) {
        switch (id) {
            case R.id.support_check:
                AppCompatCheckBox support_check = holder.itemView.findViewById(id);
                support_check.setText(new StringBuffer().append(" ").append(newsBean.getZan_total()));
                if (newsBean.getIfzan_cleck() == 1) {
                    support_check.setChecked(true);
                } else {
                    support_check.setChecked(false);
                }
                break;
        }
    }

    //check集
    private void initCheckClickListener(View view, final int position) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dynamicParentClickListener != null)
                    dynamicParentClickListener.onCheckClickListener(position, v.getId());
            }
        });
    }

    //类图模式(单张、两张、更多)
    private void initRecyclerView(RecyclerView recycler_view, List<PackingNetBeanPhoto> imgList) {
        int spanCount = 0;
        if (imgList.size() == 1) {
            spanCount = 1;
        } else if (imgList.size() == 2) {
            spanCount = 2;
        } else {
            spanCount = 3;
        }
        recycler_view.setLayoutManager(ViewBuilder.getFullyGridLayoutManager(context, false, spanCount));
        RecyclerChildAdapter recyclerChildAdapter = new RecyclerChildAdapter(context, imgList);
        recycler_view.setAdapter(recyclerChildAdapter);
        recyclerChildAdapter.notifyDataSetChanged();
        recyclerChildAdapter.setChildClickListener(this);
    }


    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size() + 1;
    }

    /**
     * 设置上拉加载状态 * * @param loadState 0.正在加载 1.加载完成 2.加载到底
     */
    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }

    @Override
    public void onChildClickListener(int position, PackingNetBeanPhoto packingNetBeanPhoto) {
        if (dynamicParentClickListener != null)
            dynamicParentClickListener.onChildClickListener(position, packingNetBeanPhoto);
    }

    public interface DynamicParentClickListener {
        void onChildClickListener(int position, PackingNetBeanPhoto packingNetBeanPhoto);

        void onCheckClickListener(int position, int id);

        void onClearCheckClickListener(int position);

    }

    private DynamicParentClickListener dynamicParentClickListener;

    public void setDynamicParentClickListener(DynamicParentClickListener dynamicParentClickListener) {
        this.dynamicParentClickListener = dynamicParentClickListener;
    }


}
