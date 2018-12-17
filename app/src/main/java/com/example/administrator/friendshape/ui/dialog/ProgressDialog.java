package com.example.administrator.friendshape.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.friendshape.R;

/**
 * 作者：真理 Created by Administrator on 2018/11/26.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ProgressDialog extends AlertDialog {

    private Context context;
    private ImageView imageView;
    private AnimationDrawable animationDrawable;
    private String content;


    protected ProgressDialog(Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress);
        imageView = (ImageView) findViewById(R.id.iv_loading);
        try {
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(R.drawable.progress_white_animlist);
            animationDrawable = (AnimationDrawable) imageView.getDrawable();
            animationDrawable.start();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    public void ShowDiaLog(String content) {
        show();
    }


}
