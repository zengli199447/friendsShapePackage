package com.example.administrator.friendshape.utils;

import android.content.Context;
import android.widget.Toast;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/10/30.
 */

public class ToastUtil {

    private Context context;

    public static boolean status = true;

    @Inject
    public ToastUtil(Context context) {
        this.context = context;
    }

    public void showToast(String message) {
        if (status)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
