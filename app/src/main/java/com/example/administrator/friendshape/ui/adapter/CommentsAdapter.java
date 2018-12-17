package com.example.administrator.friendshape.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.MerchantsContentsNetWorkBean;
import com.example.administrator.friendshape.ui.holder.MyViewHolder;
import com.example.administrator.friendshape.ui.view.ShinyView;
import com.example.administrator.friendshape.utils.SystemUtil;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/7.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class CommentsAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<MerchantsContentsNetWorkBean.ResultBean.CommentBean> commentsList;

    public CommentsAdapter(Context context, List<MerchantsContentsNetWorkBean.ResultBean.CommentBean> commentsList) {
        this.context = context;
        this.commentsList = commentsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comments, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        MerchantsContentsNetWorkBean.ResultBean.CommentBean commentBean = commentsList.get(position);
        ImageView user_img = holder.itemView.findViewById(R.id.user_img);
        TextView user_name = holder.itemView.findViewById(R.id.user_name);
        ShinyView shiny_view = holder.itemView.findViewById(R.id.shiny_view);
        TextView creat_time = holder.itemView.findViewById(R.id.creat_time);
        TextView conmments = holder.itemView.findViewById(R.id.conmments);
        View line = holder.itemView.findViewById(R.id.line);

        Glide.with(context).load(SystemUtil.JudgeUrl(commentBean.getPhoto())).error(R.drawable.banner_off).into(user_img);
        user_name.setText(commentBean.getSecondname());
        shiny_view.setStarMark(Float.valueOf(commentBean.getScore()));
        creat_time.setText(commentBean.getCreatedate());
        conmments.setText(commentBean.getRemark());

        user_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commentsClickListener != null)
                    commentsClickListener.onCommentsClickListener(position);
            }
        });

        if (commentsList.size() - 1 == position) {
            line.setVisibility(View.GONE);
        } else {
            line.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return commentsList.size() == 0 ? 0 : commentsList.size();
    }

    public interface CommentsClickListener {
        void onCommentsClickListener(int position);
    }

    private CommentsClickListener commentsClickListener;

    public void setCommentsClickListener(CommentsClickListener commentsClickListener) {
        this.commentsClickListener = commentsClickListener;
    }


}
