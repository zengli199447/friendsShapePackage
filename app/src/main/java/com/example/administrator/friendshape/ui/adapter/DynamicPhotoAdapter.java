package com.example.administrator.friendshape.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.ui.holder.MyViewHolder;
import com.example.administrator.friendshape.utils.LogUtil;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

/**
 * 作者：真理 Created by Administrator on 2018/10/31.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class DynamicPhotoAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private String TAG = getClass().getSimpleName();
    Context context;
    ArrayList<AlbumFile> list;
    ArrayList<ImageView> viewList = new ArrayList<>();
    boolean deletStatus;

    public DynamicPhotoAdapter(Context context, ArrayList<AlbumFile> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dynamic_photo, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ImageView photo_img = holder.itemView.findViewById(R.id.photo_img);
        ImageView delet_img = holder.itemView.findViewById(R.id.delet_img);
        if (position == list.size() || list.size() == 0) {
            photo_img.setImageDrawable(context.getResources().getDrawable(R.drawable.add_icon));
            ClickListener(photo_img, position, 0);
            delet_img.setVisibility(View.GONE);
        } else {
            Glide.with(context).load(list.get(position).getPath()).error(R.drawable.banner_off).into(photo_img);
            ClickListener(photo_img, position, 1);
            ClickListener(delet_img, position, 2);
            if (deletStatus) {
                delet_img.setVisibility(View.VISIBLE);
            } else {
                delet_img.setVisibility(View.GONE);
            }
        }
    }

    private void ClickListener(final View view, final int position, final int type) {
        if (photoClickListener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (type) {
                        case 0:
                            photoClickListener.AddClickListener();
                            break;
                        case 1:
                            photoClickListener.PhotoClickListener(position);
                            break;
                        case 2:
                            photoClickListener.DeletPhotoClickListener(position);
                            break;
                    }
                }
            });
        }
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                deletStatus = true;
                notifyDataSetChanged();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size() < 9 ? list.size() + 1 : list.size();
    }


    public interface PhotoClickListener {
        void AddClickListener();

        void PhotoClickListener(int positon);

        void DeletPhotoClickListener(int positon);
    }

    private PhotoClickListener photoClickListener;

    public void setPhotoClickListener(PhotoClickListener photoClickListener) {
        this.photoClickListener = photoClickListener;
    }


}
