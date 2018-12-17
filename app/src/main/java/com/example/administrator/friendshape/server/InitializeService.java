package com.example.administrator.friendshape.server;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.administrator.friendshape.global.CrashHandler;
import com.example.administrator.friendshape.utils.SystemUtil;
import com.example.administrator.friendshape.widget.GlideAlbumLoader;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;

import java.util.Locale;


/**
 * Created by Administrator on 2018/4/2.
 */

public class InitializeService extends IntentService {

    protected String TAG = getClass().getSimpleName();

    private static final String ACTION_INIT = "initApplication";

    public InitializeService() {
        super("InitializeService");
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, InitializeService.class);
        intent.setAction(ACTION_INIT);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT.equals(action)) {
                initAppComponent();
            }
        }
    }

    private void initAppComponent() {
        //媒体配置
        initAlbum();
        //异常捕获
//        initCrashHandler();
        //初始化网络变化监听服务
        initNetworkListenService();

    }

    private void initNetworkListenService() {
        Intent intentNetworkListenService = new Intent();
        intentNetworkListenService.setAction(ListenNetworkStateService.class.getCanonicalName());
        intentNetworkListenService.setPackage(getPackageName());
        if (SystemUtil.isServiceWork(InitializeService.this, ListenNetworkStateService.class.getSimpleName())) {
            this.stopService(intentNetworkListenService);
        }
        this.startService(intentNetworkListenService);
    }

    //监视应用异常
    private void initCrashHandler() {
        try {
            CrashHandler crashHandler = CrashHandler.getInstance();
            crashHandler.init(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initAlbum() {
        Album.initialize(
                AlbumConfig.newBuilder(this)
                        .setAlbumLoader(new GlideAlbumLoader())
                        .setLocale(Locale.CHINA)
                        .build()
        );
    }

}
