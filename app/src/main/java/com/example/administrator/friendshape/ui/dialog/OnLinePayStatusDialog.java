package com.example.administrator.friendshape.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StyleRes;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.friendshape.R;


/**
 * Created by Administrator on 2018/8/11.
 * 支付状态
 */

public class OnLinePayStatusDialog extends AlertDialog {

    private Context context;
    private int status;
    private ImageView iv_loading_status;
    private TextView tv_loading_status;

    protected OnLinePayStatusDialog(Context context, @StyleRes int themeResId, int status) {
        super(context, themeResId);
        this.context = context;
        this.status = status;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pay_status);
        iv_loading_status = (ImageView) findViewById(R.id.iv_loading_status);
        tv_loading_status = (TextView) findViewById(R.id.tv_loading_status);
        switch (status) {
            case 0:
                iv_loading_status.setBackground(context.getResources().getDrawable(R.drawable.pay_successful));
                tv_loading_status.setText(context.getResources().getString(R.string.pay_status_no));
                break;
            case 1:
                iv_loading_status.setBackground(context.getResources().getDrawable(R.drawable.pay_failure));
                tv_loading_status.setText(context.getResources().getString(R.string.pay_status_off));
                break;
            case 2:
                iv_loading_status.setBackground(context.getResources().getDrawable(R.drawable.pay_ongoing));
                tv_loading_status.setText(context.getResources().getString(R.string.pay_status_ongoing));
                break;
        }
    }

}
