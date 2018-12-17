package com.example.administrator.friendshape.ui.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.Window;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.utils.SystemUtil;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Administrator on 2018/3/19.
 */

public class ShowDialog {

    private static ShowDialog instance = new ShowDialog();

    private ShowDialog() {
    }

    public static ShowDialog getInstance() {
        return instance;
    }


    public ConfirmOrNoDialog showConfirmOrNoDialog(final Context context, String content, int type, int commit, int cancle) {
        final ConfirmOrNoDialog confirmOrNoDialog = new ConfirmOrNoDialog(context, R.style.dialog, content, type,commit,cancle);
        confirmOrNoDialog.show();
        SystemUtil.windowToDark(context);
        return confirmOrNoDialog;
    }

    public void showHelpfulHintsDialog(final Context context, String content, int eventCode) {
        final HelpfulHintsDialog helpfulHintsDialog = new HelpfulHintsDialog(context, R.style.dialog, content, eventCode);
        helpfulHintsDialog.show();
        SystemUtil.windowToDark(context);
    }

    public void showInputDialog(final Context context, String content, int eventCode) {
        final GeneralInputDialog generalInputDialog = new GeneralInputDialog(context, R.style.dialog, content, eventCode);
        generalInputDialog.show();
        SystemUtil.windowToDark(context);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                generalInputDialog.showKeyboard();
            }
        }, 200);
    }

    public void showOnLinePayStatusDialog(final Context context, int status) {
        OnLinePayStatusDialog onLinePayStatusDialog = new OnLinePayStatusDialog(context, R.style.dialog, status);
        onLinePayStatusDialog.setCanceledOnTouchOutside(true);
        Window window = onLinePayStatusDialog.getWindow();
        if (window != null) {
            window.setGravity(Gravity.CENTER);//此处可以设置dialog显示的位置
        }
        onLinePayStatusDialog.show();
    }

    public ProgressDialog showProgressStatus(final Context context) {
        final ProgressDialog progressDialog = new ProgressDialog(context, R.style.dialog);
        progressDialog.setCanceledOnTouchOutside(true);
        Window window = progressDialog.getWindow();
        if (window != null) {
            window.setGravity(Gravity.CENTER);//此处可以设置dialog显示的位置
        }
        return progressDialog;
    }

}
