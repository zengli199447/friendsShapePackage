package com.example.administrator.friendshape.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;


/**
 * Created by Administrator on 2018/3/5 0005.
 */

public abstract class SimpleFragment extends SupportFragment implements View.OnClickListener{

    protected String TAG = getClass().getSimpleName();

    private Activity mActivity;
    private Context mContext;
    private View mView;
    private Unbinder mUnBinder;
    private FragmentActivity activity;

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), null);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
        activity = getActivity();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initClass();
        LifecycleObserverBind();
        initData();
        initView();
        initListener();

    }

    protected abstract int getLayoutId();

    protected abstract void initClass();

    protected abstract void initData();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void onClickAble(View view);

    protected abstract BaseLifecycleObserver initLifecycleObserver();

    protected void LifecycleObserverBind() {
        BaseLifecycleObserver baseLifecycleObserver = initLifecycleObserver();
        if (baseLifecycleObserver != null) {
            baseLifecycleObserver.initContext(activity);
            getLifecycle().addObserver(baseLifecycleObserver);
        }
    }

    protected void onTheCustom() {

    }

    protected void onUnSubscribe() {

    }

    @Override
    public void onClick(View view) {
        onClickAble(view);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
        onUnSubscribe();
        onTheCustom();
    }
}
