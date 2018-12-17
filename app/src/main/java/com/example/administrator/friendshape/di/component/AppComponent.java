package com.example.administrator.friendshape.di.component;

import android.content.Context;


import com.example.administrator.friendshape.di.module.AppModule;
import com.example.administrator.friendshape.di.module.HttpModule;
import com.example.administrator.friendshape.model.DataManager;
import com.example.administrator.friendshape.model.db.GreenDaoHelper;
import com.example.administrator.friendshape.model.http.RetrofitHelper;
import com.example.administrator.friendshape.utils.ToastUtil;

import javax.inject.Singleton;

import dagger.Component;


/**
 * Created by Administrator on 2017/10/27.
 */
@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {

    Context getContext();

    ToastUtil getToastUtil();

    DataManager getDataManager(); //数据中心

    RetrofitHelper getRetrofitHelper();  //提供http的帮助类

    GreenDaoHelper greenDaoHelper();    //提供数据库帮助类

}
