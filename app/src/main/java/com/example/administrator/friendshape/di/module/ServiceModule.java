package com.example.administrator.friendshape.di.module;

import android.app.Service;


import com.example.administrator.friendshape.di.scope.ServiceScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2018/3/2 0002.
 */
@Module
public class ServiceModule {
    private Service service;

    public ServiceModule(Service service) {
        this.service = service;
    }

    @Provides
    @ServiceScope
    public Service provideServer() {
        return service;
    }
}
