package com.example.administrator.friendshape.base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;

import com.example.administrator.friendshape.utils.LogUtil;


/**
 * Created by Administrator on 2018/9/14.
 */

public abstract class BaseLifecycleObserver implements LifecycleObserver {

    protected String TAG = getClass().getSimpleName();
    protected Context context;

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void ON_CREATE() {
        onClassCreate();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void ON_START() {
        onClassStart();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void ON_RESUME() {
        onClassResume();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void ON_PAUSE() {
        onClassPause();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void ON_STOP() {
        onClassStop();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void ON_DESTROY() {
        onClassDestroy();
    }

    protected abstract void onClassCreate();

    protected abstract void onClassStart();

    protected abstract void onClassResume();

    protected abstract void onClassPause();

    protected abstract void onClassStop();

    protected abstract void onClassDestroy();

    public void initContext(Context context){
        this.context = context;
        LogUtil.e(TAG,"context -  : " + context);
    }

    protected abstract void LifecycleObserver(Context context);

}
