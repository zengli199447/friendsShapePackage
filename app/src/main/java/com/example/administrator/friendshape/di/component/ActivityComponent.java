package com.example.administrator.friendshape.di.component;

import android.app.Activity;


import com.example.administrator.friendshape.di.module.ActivityModule;
import com.example.administrator.friendshape.di.scope.ActivityScope;
import com.example.administrator.friendshape.ui.activity.HomeActivity;
import com.example.administrator.friendshape.ui.activity.LoginActivity;
import com.example.administrator.friendshape.ui.activity.WelcomeActivity;
import com.example.administrator.friendshape.ui.activity.component.ATeamActivity;
import com.example.administrator.friendshape.ui.activity.component.BindPhoneActivity;
import com.example.administrator.friendshape.ui.activity.component.CancleOrderActivity;
import com.example.administrator.friendshape.ui.activity.component.CityScreeningActivity;
import com.example.administrator.friendshape.ui.activity.component.EnterTheTuxedoActivity;
import com.example.administrator.friendshape.ui.activity.component.FriendsControllerActivity;
import com.example.administrator.friendshape.ui.activity.component.GeneralActivity;
import com.example.administrator.friendshape.ui.activity.component.ImMessageActivity;
import com.example.administrator.friendshape.ui.activity.component.InviteFriendsActivity;
import com.example.administrator.friendshape.ui.activity.component.MerchantsContentActivity;
import com.example.administrator.friendshape.ui.activity.component.MerchantsGroupsActivity;
import com.example.administrator.friendshape.ui.activity.component.MerchantsTypeFormActivity;
import com.example.administrator.friendshape.ui.activity.component.MyDynamicActivity;
import com.example.administrator.friendshape.ui.activity.component.OrderContentActivity;
import com.example.administrator.friendshape.ui.activity.component.PersonalActivity;
import com.example.administrator.friendshape.ui.activity.component.ReleaseNewDynamicActivity;
import com.example.administrator.friendshape.ui.activity.component.TheDetailsInformationActivity;

import dagger.Component;


/**
 * Created by Administrator on 2017/10/27.
 */
@ActivityScope
@Component(modules = ActivityModule.class, dependencies = AppComponent.class)
public interface ActivityComponent {
    Activity getActivity();

    void inject(WelcomeActivity welcomeActivity);

    void inject(HomeActivity homeActivity);

    void inject(LoginActivity loginActivity);

    void inject(CityScreeningActivity cityScreeningActivity);

    void inject(PersonalActivity personalActivity);

    void inject(MyDynamicActivity myDynamicActivity);

    void inject(GeneralActivity generalActivity);

    void inject(ReleaseNewDynamicActivity releaseNewDynamicActivity);

    void inject(MerchantsTypeFormActivity merchantsTypeFormActivity);

    void inject(MerchantsContentActivity merchantsContentActivity);

    void inject(ATeamActivity aTeamActivity);

    void inject(OrderContentActivity orderContentActivity);

    void inject(InviteFriendsActivity inviteFriendsActivity);

    void inject(CancleOrderActivity cancleOrderActivity);

    void inject(TheDetailsInformationActivity theDetailsInformationActivity);

    void inject(MerchantsGroupsActivity merchantsGroupsActivity);

    void inject(EnterTheTuxedoActivity enterTheTuxedoActivity);

    void inject(FriendsControllerActivity friendsControllerActivity);

    void inject(BindPhoneActivity bindPhoneActivity);

    void inject(ImMessageActivity imMessageActivity);

}
