package com.example.administrator.friendshape.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.global.MyApplication;
import com.example.administrator.friendshape.ui.dialog.ProgressDialog;
import com.example.administrator.friendshape.ui.dialog.ShowDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ShowDialog showDialog = ShowDialog.getInstance();
        final ProgressDialog progressDialog = showDialog.showProgressStatus(this);
        progressDialog.show();
        progressDialog.ShowDiaLog("发生错误，正在关闭应用 ...");
        MyApplication.executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2500);
                    progressDialog.dismiss();
                    MyApplication.exitApp();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
