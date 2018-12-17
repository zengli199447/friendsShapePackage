package com.example.administrator.friendshape.widget;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.friendshape.R;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.AlbumLoader;

/**
 * 作者：真理 Created by Administrator on 2018/10/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class GlideAlbumLoader implements AlbumLoader {

    @Override
    public void load(ImageView imageView, AlbumFile albumFile) {
        load(imageView, albumFile.getPath());
    }

    @Override
    public void load(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .error(R.drawable.banner_off)
                .placeholder(R.drawable.placeholder)
                .crossFade()
                .into(imageView);
    }


}
