package com.example.administrator.friendshape.ui.controller;

import android.util.Log;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.ControllerClassObserver;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.PersonalContentNetBean;
import com.example.administrator.friendshape.model.bean.UpLoadStatusNetBean;
import com.example.administrator.friendshape.model.db.entity.LoginUserInfo;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.rxtools.RxBus;
import com.example.administrator.friendshape.rxtools.RxUtil;
import com.example.administrator.friendshape.ui.dialog.ShowDialog;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.widget.CommonSubscriber;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;


/**
 * 作者：真理 Created by Administrator on 2018/11/20.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerPersonal extends ControllerClassObserver {

    private ShowDialog instance;

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getControllerComponent().inject(this);
    }

    @Override
    protected void onClassCreate() {
        super.onClassCreate();
        instance = ShowDialog.getInstance();
        personalContentNetWork();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    //获取用户信息
    public void personalContentNetWork() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.USER_INFO_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.GetPersonalContentNetBean(map)
                .compose(RxUtil.<PersonalContentNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<PersonalContentNetBean>(toastUtil) {
                    @Override
                    public void onNext(PersonalContentNetBean personalContentNetBean) {
                        if (personalContentNetBean.getStatus() == 1) {
                            PersonalContentNetBean.ResultBean result = personalContentNetBean.getResult();
                            LoginUserInfo loginUserInfo = dataManager.queryLoginUserInfo(DataClass.STANDARD_USER);
                            loginUserInfo.setUserNiceName(result.getSecondname());
                            loginUserInfo.setUserGender(result.getSex());
                            dataManager.UpDataLoginUserInfo(loginUserInfo);
                            if (personalContentNetWorkListener != null)
                                personalContentNetWorkListener.onPersonalContentNetWorkListener(personalContentNetBean.getResult());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    //提交个人信息修改
    public void personalChangeNetWork(String photo, String secondName, final String gender, String brithday, String about) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.USER_INFO_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("photo", photo);
        linkedHashMap.put("secondname", secondName);
        linkedHashMap.put("sex", gender);
        linkedHashMap.put("brithday", brithday);
        linkedHashMap.put("remark", about);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.UpLoadStatusNetBean(map)
                .compose(RxUtil.<UpLoadStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UpLoadStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(UpLoadStatusNetBean upLoadStatusNetBean) {
                        if (upLoadStatusNetBean.getStatus() == 1) {
                            LogUtil.e(TAG, "修改成功");
                            RxBus.getDefault().post(new CommonEvent(EventCode.MINE_INFO_REFRESH));
                            LoginUserInfo loginUserInfo = dataManager.queryLoginUserInfo(DataClass.STANDARD_USER);
                            loginUserInfo.setUserGender(gender);
                            DataClass.GENDER = gender;
                            dataManager.UpDataLoginUserInfo(loginUserInfo);
                            if (personalContentNetWorkListener != null)
                                personalContentNetWorkListener.onPersonalUpLoadStatusNetWorkListener();
                            instance.showHelpfulHintsDialog(context, context.getString(R.string.change_personal_successful), EventCode.PERSONAL_CHANGE_REFRESH);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }


    public interface PersonalContentNetWorkListener {
        void onPersonalContentNetWorkListener(PersonalContentNetBean.ResultBean result);

        void onPersonalUpLoadStatusNetWorkListener();
    }

    private PersonalContentNetWorkListener personalContentNetWorkListener;

    public void setPersonalContentNetWorkListener(PersonalContentNetWorkListener personalContentNetWorkListener) {
        this.personalContentNetWorkListener = personalContentNetWorkListener;
    }

}
