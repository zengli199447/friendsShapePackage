package com.example.administrator.friendshape.ui.controller;

import android.content.Intent;
import android.util.Log;

import com.example.administrator.friendshape.base.ControllerClassObserver;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.LoginInfoNetBean;
import com.example.administrator.friendshape.model.bean.UpLoadStatusNetBean;
import com.example.administrator.friendshape.model.bean.VerificationCodeNetWork;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.rxtools.RxBus;
import com.example.administrator.friendshape.rxtools.RxUtil;
import com.example.administrator.friendshape.ui.activity.LoginActivity;
import com.example.administrator.friendshape.widget.CalendarBuilder;
import com.example.administrator.friendshape.widget.CommonSubscriber;
import com.google.gson.Gson;

import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 作者：真理 Created by Administrator on 2018/10/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerLogin extends ControllerClassObserver {

    private String verificationCode;

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getControllerComponent().inject(this);
    }

    //登陆
    public void NetLogin(String userName, String Password) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.LOGIN);
        linkedHashMap.put("phone", userName);
        linkedHashMap.put("pwd", Password);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.fetchLogin(map)
                .compose(RxUtil.<LoginInfoNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<LoginInfoNetBean>(toastUtil) {
                    @Override
                    public void onNext(LoginInfoNetBean loginInfoNetBean) {
                        if (loginInfoNetBean.getStatus() == 1) {
                            LoginInfoNetBean.ResultBean result = loginInfoNetBean.getResult();
                            if (loginAndRegistereNetWorkListener != null)
                                loginAndRegistereNetWorkListener.onLoginNetWorkListener(result);
                        } else {
                            toastUtil.showToast(loginInfoNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    /**
     * 获取验证码
     *
     * @param phoneNumber
     * @param controllerType 1.注册 2.修改密码、忘记密码 4.qq登陆绑定手机 5.微信登陆绑定手机
     */
    public void NetVerificationCode(String phoneNumber, int controllerType) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.GET_CODE);
        linkedHashMap.put("phone", phoneNumber);
        linkedHashMap.put("type", controllerType);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.GetVerificationCodeNetWork(map)
                .compose(RxUtil.<VerificationCodeNetWork>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<VerificationCodeNetWork>(toastUtil) {
                    @Override
                    public void onNext(VerificationCodeNetWork verificationCodeNetWork) {
                        if (verificationCodeNetWork.getStatus() == 1) {
                            verificationCode = verificationCodeNetWork.getResult().getSmscode();
                            if (loginAndRegistereNetWorkListener != null)
                                loginAndRegistereNetWorkListener.onVerificationCodeNetWorkListener(verificationCode);
                        } else {
                            toastUtil.showToast(verificationCodeNetWork.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    //注册账号、忘记密码、修改密码
    public void NetRegisteredLogin(String phoneNumber, String Password, final int type) {
        switch (type) {
            case 0:
                HashMap registeredMap = new HashMap<>();
                LinkedHashMap registeredLinkedHashMap = new LinkedHashMap();
                registeredLinkedHashMap.put("action", DataClass.USER_REGISTER_SET);
                registeredLinkedHashMap.put("phone", phoneNumber);
                registeredLinkedHashMap.put("pwd", Password);
                registeredMap.put("version", "v1");
                registeredMap.put("vars", new Gson().toJson(registeredLinkedHashMap));
                addSubscribe(dataManager.fetchLogin(registeredMap)
                        .compose(RxUtil.<LoginInfoNetBean>rxSchedulerHelper())
                        .subscribeWith(new CommonSubscriber<LoginInfoNetBean>(toastUtil) {
                            @Override
                            public void onNext(LoginInfoNetBean loginInfoNetBean) {
                                if (loginInfoNetBean.getStatus() == 1) {
                                    LoginInfoNetBean.ResultBean result = loginInfoNetBean.getResult();
                                    if (!loginInfoNetBean.getMessage().isEmpty())
                                        toastUtil.showToast(loginInfoNetBean.getMessage());
                                    if (loginAndRegistereNetWorkListener != null) {
                                        loginAndRegistereNetWorkListener.onRegistereNetWorkListener(type);
                                    }
                                } else {
                                    toastUtil.showToast(loginInfoNetBean.getMessage());
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "Throwable : " + e.toString());
                                super.onError(e);
                            }
                        }));
                break;
            case 1:
                HashMap map = new HashMap<>();
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                linkedHashMap.put("action", DataClass.USER_PWD_SET);
                linkedHashMap.put("phone", phoneNumber);
                linkedHashMap.put("pwd", Password);
                map.put("version", "v1");
                map.put("vars", new Gson().toJson(linkedHashMap));
                addSubscribe(dataManager.UpLoadStatusNetBean(map)
                        .compose(RxUtil.<UpLoadStatusNetBean>rxSchedulerHelper())
                        .subscribeWith(new CommonSubscriber<UpLoadStatusNetBean>(toastUtil) {
                            @Override
                            public void onNext(UpLoadStatusNetBean upLoadStatusNetBean) {
                                if (upLoadStatusNetBean.getStatus() == 1) {
                                    if (loginAndRegistereNetWorkListener != null)
                                        loginAndRegistereNetWorkListener.onRegistereNetWorkListener(type);
                                } else {
                                    toastUtil.showToast(upLoadStatusNetBean.getMessage());
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "Throwable : " + e.toString());
                                super.onError(e);
                            }
                        }));
                break;
        }
    }

    //第三方登录
    public void PlatformLoginNetWork(String qq, String qqName, String wechatId, String wechatName, String photo) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.USER_THIRDLOGIN_SET);
        linkedHashMap.put("qq", qq);
        linkedHashMap.put("qqname", qqName);
        linkedHashMap.put("weixinid", wechatId);
        linkedHashMap.put("weixinname", wechatName);
        linkedHashMap.put("photo", photo);
        map.put("version", "v1");
        map.put("vars", new Gson().toJson(linkedHashMap));
        addSubscribe(dataManager.fetchLogin(map)
                .compose(RxUtil.<LoginInfoNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<LoginInfoNetBean>(toastUtil) {
                    @Override
                    public void onNext(LoginInfoNetBean loginInfoNetBean) {
                        if (loginInfoNetBean.getStatus() == 1) {
                            if (loginAndRegistereNetWorkListener != null)
                                loginAndRegistereNetWorkListener.onLoginNetWorkListener(loginInfoNetBean.getResult());
                        } else {
                            toastUtil.showToast(loginInfoNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    /**
     * 第三方登陆绑定
     *
     * @param phoneNumber
     * @param bindType    1.qq登录 2.wechat登录
     */
    public void BindPhoneNetWork(String phoneNumber, int bindType) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.PHONE_BIND_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("phone", phoneNumber);
        linkedHashMap.put("sex", "");
        linkedHashMap.put("thirdlogintype", bindType);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.UpLoadStatusNetBean(map)
                .compose(RxUtil.<UpLoadStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UpLoadStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(UpLoadStatusNetBean upLoadStatusNetBean) {
                        if (upLoadStatusNetBean.getStatus() == 1) {
                            if (loginAndRegistereNetWorkListener != null)
                                loginAndRegistereNetWorkListener.onBindPhoneNetWorkListener();
                        } else {
                            toastUtil.showToast(upLoadStatusNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    public interface LoginAndRegistereNetWorkListener {
        void onLoginNetWorkListener(LoginInfoNetBean.ResultBean result);

        void onRegistereNetWorkListener(int type);

        void onVerificationCodeNetWorkListener(String verificationCode);

        void onBindPhoneNetWorkListener();
    }

    private LoginAndRegistereNetWorkListener loginAndRegistereNetWorkListener;

    public void setLoginAndRegistereNetWorkListener(LoginAndRegistereNetWorkListener loginAndRegistereNetWorkListener) {
        this.loginAndRegistereNetWorkListener = loginAndRegistereNetWorkListener;
    }

}
