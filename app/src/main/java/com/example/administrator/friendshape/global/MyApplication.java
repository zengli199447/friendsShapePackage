package com.example.administrator.friendshape.global;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.wxlib.util.SysUtil;
import com.example.administrator.friendshape.di.component.AppComponent;
import com.example.administrator.friendshape.di.component.DaggerAppComponent;
import com.example.administrator.friendshape.di.module.AppModule;
import com.example.administrator.friendshape.di.module.HttpModule;
import com.example.administrator.friendshape.server.InitializeService;
import com.example.administrator.friendshape.ui.activity.HomeActivity;
import com.example.administrator.friendshape.ui.activity.LoginActivity;
import com.example.administrator.friendshape.ui.activity.MainActivity;
import com.example.administrator.friendshape.ui.activity.WelcomeActivity;
import com.example.administrator.friendshape.ui.activity.component.BindPhoneActivity;
import com.example.administrator.friendshape.ui.activity.component.CityScreeningActivity;
import com.example.administrator.friendshape.ui.activity.component.GeneralActivity;
import com.example.administrator.friendshape.ui.activity.component.MerchantsContentActivity;
import com.example.administrator.friendshape.ui.activity.component.MerchantsGroupsActivity;
import com.example.administrator.friendshape.ui.activity.component.MerchantsTypeFormActivity;
import com.example.administrator.friendshape.ui.activity.component.TheDetailsInformationActivity;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.widget.AliChatBuilder;
import com.luoxudong.app.threadpool.ThreadPoolHelp;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;


/**
 * Created by Administrator on 2017/10/27.
 */

public class MyApplication extends Application {

    public static AppComponent appComponent;
    public static MyApplication instance;
    public static Set<Activity> allActivities;
    public static ExecutorService executorService;

    public static synchronized MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        LogUtil.isDebug = true;
        initThreadTools();
        InitializeService.start(this);
        initUm();
        //百川云旺初始化
        AliChatBuilder.initAliChat(this);
    }

    //友盟初始化
    private void initUm() {
        UMConfigure.setLogEnabled(true);

        UMConfigure.init(this, AppKeyConfig.UMID, "umeng", UMConfigure.DEVICE_TYPE_PHONE, AppKeyConfig.UMPUSHID);

        PlatformConfig.setWeixin(AppKeyConfig.WXID, AppKeyConfig.WXID_ABOUT);

        PlatformConfig.setQQZone(AppKeyConfig.QQID, AppKeyConfig.QQID_ABOUT);
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    private void initThreadTools() {
        executorService = ThreadPoolHelp.Builder.fixed(6)
                .name("globalTask")
                .builder();
    }


    public static AppComponent getAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(instance))
                .httpModule(new HttpModule())
                .build();
        return appComponent;
    }

    public void addActivity(Activity act) {
        if (act instanceof LoginActivity || act instanceof MainActivity || act instanceof HomeActivity ||
                act instanceof WelcomeActivity || act instanceof BindPhoneActivity || act instanceof CityScreeningActivity||
                act instanceof MerchantsContentActivity|| act instanceof MerchantsGroupsActivity|| act instanceof MerchantsTypeFormActivity||
                act instanceof GeneralActivity|| act instanceof TheDetailsInformationActivity) {
            if (allActivities == null) {
                allActivities = new HashSet<>();
            }
            allActivities.add(act);
        } else {
            if (DataClass.USERID.isEmpty()) {
                act.finish();
                startActivity(new Intent(this, LoginActivity.class));
            }else {
                if (allActivities == null) {
                    allActivities = new HashSet<>();
                }
                allActivities.add(act);
            }
        }
    }

    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    //自杀
    public static void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

}
