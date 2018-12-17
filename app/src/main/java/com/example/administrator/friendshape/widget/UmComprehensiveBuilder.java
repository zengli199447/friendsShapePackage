package com.example.administrator.friendshape.widget;

import android.app.Activity;

import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.utils.ToastUtil;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * 作者：真理 Created by Administrator on 2018/11/21.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class UmComprehensiveBuilder {

    private String TAG = getClass().getSimpleName();
    private Activity activity;
    private ToastUtil toastUtil;

    public UmComprehensiveBuilder(Activity activity, ToastUtil toastUtil) {
        this.activity = activity;
        this.toastUtil = toastUtil;
    }

    //友盟登录
    public void initUmLogin(int status) {
        UMShareAPI.get(activity).setShareConfig(new UMShareConfig().isNeedAuthOnGetUserInfo(true));
        switch (status) {
            case 0:
                UMShareAPI.get(activity).getPlatformInfo(activity, SHARE_MEDIA.QQ, authListener);
                break;
            case 1:
                UMShareAPI.get(activity).getPlatformInfo(activity, SHARE_MEDIA.WEIXIN, authListener);
                break;
        }
    }

    //清除登录状态
    public void deleteOauth(int status) {
        switch (status) {
            case 1:
                UMShareAPI.get(activity).deleteOauth(activity, SHARE_MEDIA.QQ, authListener);
                break;
            case 2:
                UMShareAPI.get(activity).deleteOauth(activity, SHARE_MEDIA.WEIXIN, authListener);
                break;
        }
    }

    //登陆监听
    private UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            LogUtil.e(TAG, "授权成功的回调  action : " + action);
            if (onCompleteListener != null)
                onCompleteListener.comlete(data);
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            LogUtil.e(TAG, "授权失败的回调 : " + t.getMessage());
            if (onCompleteListener != null)
                onCompleteListener.notReach();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            LogUtil.e(TAG, "授权取消的回调");
            if (onCompleteListener != null)
                onCompleteListener.notReach();
        }
    };

    public interface onCompleteListener {
        void comlete(Map<String, String> data);

        void notReach();
    }

    private onCompleteListener onCompleteListener;

    public void setOnCompleteListener(onCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
    }

}
