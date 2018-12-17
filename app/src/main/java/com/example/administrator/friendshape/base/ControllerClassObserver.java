package com.example.administrator.friendshape.base;


import android.content.Context;


import com.example.administrator.friendshape.di.component.ControllerComponent;
import com.example.administrator.friendshape.di.component.DaggerControllerComponent;
import com.example.administrator.friendshape.di.module.ControllerModule;
import com.example.administrator.friendshape.global.MyApplication;
import com.example.administrator.friendshape.model.DataManager;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.rxtools.RxBus;
import com.example.administrator.friendshape.rxtools.RxUtil;
import com.example.administrator.friendshape.utils.ToastUtil;
import com.example.administrator.friendshape.widget.CommonSubscriber;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018/9/14.
 */

public abstract class ControllerClassObserver extends BaseLifecycleObserver {


    public String TAG = getClass().getSimpleName();

    @Inject
    public ToastUtil toastUtil;

    @Inject
    public DataManager dataManager;

    protected CompositeDisposable mCompositeDisposable;

    protected ControllerComponent getControllerComponent() {
        return DaggerControllerComponent.builder()
                .appComponent(MyApplication.getAppComponent())
                .controllerModule(new ControllerModule(this))
                .build();
    }

    protected abstract void registerEvent(CommonEvent commonevent);

    protected abstract void initInject();

    @Override
    protected void LifecycleObserver(Context context) {

    }

    protected void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    protected void addSubscribe(Disposable subscription) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }

    protected void initRegisterEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(CommonEvent.class)
                .compose(RxUtil.<CommonEvent>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<CommonEvent>(toastUtil, null) {

                    @Override
                    public void onNext(CommonEvent commonevent) {
                        registerEvent(commonevent);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                })
        );
    }



    @Override
    protected void onClassCreate() {
        initInject();
        initRegisterEvent();
    }

    @Override
    protected void onClassStart() {

    }

    @Override
    protected void onClassResume() {

    }

    @Override
    protected void onClassPause() {

    }

    @Override
    protected void onClassStop() {

    }

    @Override
    protected void onClassDestroy() {
        unSubscribe();
    }


}
