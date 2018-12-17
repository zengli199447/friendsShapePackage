package com.example.administrator.friendshape.di.component;


import com.example.administrator.friendshape.base.ControllerClassObserver;
import com.example.administrator.friendshape.di.module.ControllerModule;
import com.example.administrator.friendshape.di.scope.ControllerScope;
import com.example.administrator.friendshape.ui.controller.ControllerATeam;
import com.example.administrator.friendshape.ui.controller.ControllerAllTypeOrder;
import com.example.administrator.friendshape.ui.controller.ControllerBindAndModify;
import com.example.administrator.friendshape.ui.controller.ControllerCancleOrder;
import com.example.administrator.friendshape.ui.controller.ControllerChatFriends;
import com.example.administrator.friendshape.ui.controller.ControllerConcentrated;
import com.example.administrator.friendshape.ui.controller.ControllerDynamic;
import com.example.administrator.friendshape.ui.controller.ControllerEnterTheTuxedo;
import com.example.administrator.friendshape.ui.controller.ControllerFriendOperation;
import com.example.administrator.friendshape.ui.controller.ControllerGeneral;
import com.example.administrator.friendshape.ui.controller.ControllerGroup;
import com.example.administrator.friendshape.ui.controller.ControllerGroupsAndComments;
import com.example.administrator.friendshape.ui.controller.ControllerHome;
import com.example.administrator.friendshape.ui.controller.ControllerInviteFriends;
import com.example.administrator.friendshape.ui.controller.ControllerLogin;
import com.example.administrator.friendshape.ui.controller.ControllerMerchants;
import com.example.administrator.friendshape.ui.controller.ControllerMerchantsTypeForm;
import com.example.administrator.friendshape.ui.controller.ControllerMessage;
import com.example.administrator.friendshape.ui.controller.ControllerNear;
import com.example.administrator.friendshape.ui.controller.ControllerOrderContent;
import com.example.administrator.friendshape.ui.controller.ControllerPeopleNearby;
import com.example.administrator.friendshape.ui.controller.ControllerPersonal;
import com.example.administrator.friendshape.ui.controller.ControllerReleaseNewDynamic;
import com.example.administrator.friendshape.ui.controller.ControllerTheDetailsInformation;

import dagger.Component;


/**
 * Created by Administrator on 2017/10/27.
 */
@ControllerScope
@Component(modules = ControllerModule.class, dependencies = AppComponent.class)
public interface ControllerComponent {
    ControllerClassObserver getController();

    void inject(ControllerLogin controllerLogin);

    void inject(ControllerHome controllerHome);

    void inject(ControllerNear controllerNear);

    void inject(ControllerGeneral controllerGeneral);

    void inject(ControllerReleaseNewDynamic controllerReleaseNewDynamic);

    void inject(ControllerConcentrated controllerConcentrated);

    void inject(ControllerMerchantsTypeForm controllerMerchantsTypeForm);

    void inject(ControllerMerchants controllerMerchants);

    void inject(ControllerATeam controllerATeam);

    void inject(ControllerAllTypeOrder controllerAllTypeOrder);

    void inject(ControllerOrderContent controllerOrderContent);

    void inject(ControllerInviteFriends controllerInviteFriends);

    void inject(ControllerCancleOrder controllerCancleOrder);

    void inject(ControllerDynamic controllerDynamic);

    void inject(ControllerPeopleNearby controllerPeopleNearby);

    void inject(ControllerTheDetailsInformation controllerTheDetailsInformation);

    void inject(ControllerGroup controllerGroup);

    void inject(ControllerPersonal controllerPersonal);

    void inject(ControllerGroupsAndComments controllerGroupsAndComments);

    void inject(ControllerEnterTheTuxedo controllerEnterTheTuxedo);

    void inject(ControllerChatFriends controllerChatFriends);

    void inject(ControllerMessage controllerMessage);

    void inject(ControllerFriendOperation controllerFriendOperation);

    void inject(ControllerBindAndModify controllerBindAndModify);

}
