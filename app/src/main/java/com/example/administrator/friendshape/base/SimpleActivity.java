package com.example.administrator.friendshape.base;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.example.administrator.friendshape.global.MyApplication;
import com.example.administrator.friendshape.utils.LogUtil;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.umeng.socialize.UMShareAPI;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by Administrator on 2018/3/1.
 */

public abstract class SimpleActivity extends SupportActivity implements View.OnClickListener {

    protected String TAG = getClass().getSimpleName();

    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TitleSysStyle();
        setContentView(getLayout());
        requestPermissions();
        mUnBinder = ButterKnife.bind(this);
        MyApplication.getInstance().addActivity(this);
        onViewCreated();
        initClass();
        LifecycleObserverBind();
        initData();
        initView();
        initListener();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    protected void onViewCreated() {

    }

    protected void onUnSubscribe() {

    }

    protected void onTheCustom() {

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    protected abstract int getLayout();

    protected abstract void initClass();

    protected abstract void initData();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void onClickAble(View view);

    protected abstract BaseLifecycleObserver initLifecycleObserver();

    protected void LifecycleObserverBind() {
        BaseLifecycleObserver baseLifecycleObserver = initLifecycleObserver();
        if (baseLifecycleObserver != null) {
            baseLifecycleObserver.initContext(this);
            getLifecycle().addObserver(baseLifecycleObserver);
        }
    }

    private void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions(SimpleActivity.this);
        rxPermission
                .requestEach(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_CALENDAR,
                        Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_SMS,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.SYSTEM_ALERT_WINDOW,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.CHANGE_WIFI_STATE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                        Manifest.permission.CHANGE_NETWORK_STATE,
                        Manifest.permission.GET_TASKS,
                        Manifest.permission.RECEIVE_BOOT_COMPLETED,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.SYSTEM_ALERT_WINDOW,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                        Manifest.permission.WRITE_SETTINGS)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            Log.i(TAG, permission.name + " is granted.");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            Log.i(TAG, permission.name + " is denied. More info should be provided.");
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            Log.e(TAG, permission.name + " is denied.");
                        }
                    }
                });
    }

    private void TitleSysStyle() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
                Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
                field.setAccessible(true);
                field.setInt(getWindow().getDecorView(), Color.TRANSPARENT);  //改为透明

                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            } catch (Exception e) {
                LogUtil.e(TAG, " style 异常 ");
            }
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        SystemUtil.hideBottomAndUIMenu(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        onClickAble(view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().removeActivity(this);
        mUnBinder.unbind();
        onUnSubscribe();
        onTheCustom();
    }

}
