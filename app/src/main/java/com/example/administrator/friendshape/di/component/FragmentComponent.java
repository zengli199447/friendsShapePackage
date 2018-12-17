package com.example.administrator.friendshape.di.component;

import android.app.Activity;

import com.example.administrator.friendshape.di.module.FragmentModule;
import com.example.administrator.friendshape.di.scope.FragmentScope;
import com.example.administrator.friendshape.ui.fragment.ChatFragment;
import com.example.administrator.friendshape.ui.fragment.HomeFragment;
import com.example.administrator.friendshape.ui.fragment.MineFragment;
import com.example.administrator.friendshape.ui.fragment.NearFragment;
import com.example.administrator.friendshape.ui.fragment.OrderFragment;
import com.example.administrator.friendshape.ui.fragment.chat.FriendsFragment;
import com.example.administrator.friendshape.ui.fragment.chat.MessageFragment;
import com.example.administrator.friendshape.ui.fragment.near.DynamicFragment;
import com.example.administrator.friendshape.ui.fragment.near.GroupFragment;
import com.example.administrator.friendshape.ui.fragment.near.PeopleNearbyFragment;
import com.example.administrator.friendshape.ui.fragment.order.AllFragment;
import com.example.administrator.friendshape.ui.fragment.order.AlreadyCancelFragment;
import com.example.administrator.friendshape.ui.fragment.order.AlreadyConsumptionFragment;
import com.example.administrator.friendshape.ui.fragment.order.StayATeamFragment;
import com.example.administrator.friendshape.ui.fragment.order.StayConsumptionFragment;
import com.example.administrator.friendshape.ui.fragment.personal.PersonalDynamicFragment;
import com.example.administrator.friendshape.ui.fragment.personal.PersonalGroupFragment;

import dagger.Component;


@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(HomeFragment homeFragment);

    void inject(NearFragment nearFragment);

    void inject(ChatFragment chatFragment);

    void inject(OrderFragment orderFragment);

    void inject(MineFragment mineFragment);

    void inject(DynamicFragment dynamicFragment);

    void inject(PeopleNearbyFragment peopleNearbyFragment);

    void inject(GroupFragment groupFragment);

    void inject(AllFragment allFragment);

    void inject(StayATeamFragment stayATeamFragment);

    void inject(StayConsumptionFragment stayConsumptionFragment);

    void inject(AlreadyConsumptionFragment alreadyConsumptionFragment);

    void inject(AlreadyCancelFragment alreadyCancelFragment);

    void inject(PersonalDynamicFragment personalDynamicFragment);

    void inject(PersonalGroupFragment personalGroupFragment);

    void inject(MessageFragment messageFragment);

    void inject(FriendsFragment friendsFragment);

}
