package com.example.administrator.friendshape.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMCore;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.aop.AdviceBinder;
import com.alibaba.mobileim.aop.PointCutEnum;
import com.alibaba.mobileim.aop.custom.IMConversationListOperation;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.conversation.EServiceContact;
import com.alibaba.mobileim.conversation.IYWConversationService;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.wxlib.util.SysUtil;
import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.global.AppKeyConfig;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.global.MyApplication;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.utils.SystemUtil;
import com.example.administrator.friendshape.utils.ToastUtil;
import com.example.administrator.friendshape.widget.chat.ImAliChatConversationUi;
import com.example.administrator.friendshape.widget.chat.ImAliChatMessageItemUi;
import com.example.administrator.friendshape.widget.chat.ImAliChatTheMessageBoardUi;
import com.example.administrator.friendshape.widget.chat.ImAliChatTitleUi;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/23.
 * 邮箱：229017464@qq.com
 * remark: 百川通讯
 */
public class AliChatBuilder {

    String TAG = getClass().getSimpleName();

    Context context;

    public AliChatBuilder(Context context) {
        this.context = context;
    }

    //加载API
    public static void initAliChat(MyApplication context) {

        SysUtil.setApplication(context);

        if (SysUtil.isTCMSServiceProcess(context))
            return;

        if (SysUtil.isMainProcess())
            YWAPI.init(context, AppKeyConfig.IM_ALI_CHAT);

        AdviceBinder.bindAdvice(PointCutEnum.CHATTING_FRAGMENT_UI_POINTCUT, ImAliChatTitleUi.class);//自定义聊天界面
        AdviceBinder.bindAdvice(PointCutEnum.CONVERSATION_FRAGMENT_UI_POINTCUT, ImAliChatConversationUi.class);
        AdviceBinder.bindAdvice(PointCutEnum.CHATTING_FRAGMENT_OPERATION_POINTCUT, ImAliChatMessageItemUi.class);

    }

    //刷新状态
    public void RefreshChatStatus() {
        DataClass.mIMKit = YWAPI.getIMKitInstance(DataClass.USERID, AppKeyConfig.IM_ALI_CHAT);

        DataClass.imCore = YWAPI.createIMCore(DataClass.USERID, AppKeyConfig.IM_ALI_CHAT);
    }

    //登陆
    public void LoginAliChat() {
        IYWLoginService loginService = DataClass.mIMKit.getLoginService();

        String passWord = "";
        switch (DataClass.LOGINTYPE) {
            case 1:
                passWord = SystemUtil.string2MD5(DataClass.PASSWORD);
                break;
            case 2:
            case 3:
                passWord = DataClass.PASSWORD;
                break;
        }

        YWLoginParam loginParam = YWLoginParam.createLoginParam(DataClass.USERID, passWord);

        loginService.login(loginParam, new IWxCallback() {

            @Override
            public void onSuccess(Object... objects) {
                LogUtil.e(TAG, "阿里百川云旺 - 登陆成功");
                GetConversationNetContent();
            }

            @Override
            public void onError(int i, String s) {
                LogUtil.e(TAG, "阿里百川云旺 - 登陆异常 ：" + s);
            }

            @Override
            public void onProgress(int i) {
                LogUtil.e(TAG, "阿里百川云旺 - 登陆中 ... ");
            }
        });

    }

    //通知栏
    public void notifictionSetting(){
        DataClass.mIMKit.setEnableNotification(true);
        DataClass.mIMKit.setAppName(new StringBuffer().append(context.getString(R.string.app_name)).append(" ").append(context.getString(R.string.new_message)).toString());//通知栏显示的名称
        DataClass.mIMKit.setResId(R.drawable.logo_icon);
        Intent intent = DataClass.mIMKit.getConversationActivityIntent(); //开发者可以使用openIM提供的intent也可以使用自定义的intent
        DataClass.mIMKit.setNotificationIntent(intent);

    }

    //会话中心Actvity
    public void OpenConversation() {
        Intent intent = DataClass.mIMKit.getConversationActivityIntent();
        context.startActivity(intent);
    }

    //单聊
    public void OpenSingleConversation(String target) {
        if (DataClass.mIMKit == null){
            new ToastUtil(context).showToast(context.getString(R.string.temporary_not_start));
        }else {
            Intent intent = DataClass.mIMKit.getChattingActivityIntent(target);
            context.startActivity(intent);
        }
    }

    //客服
    public void OpenServiceContact(String target, int groupChatId) {
        EServiceContact contact = new EServiceContact(target, groupChatId);
        Intent intent = DataClass.mIMKit.getChattingActivityIntent(contact);
        context.startActivity(intent);
    }

    //获取会话列表
    public void GetConversationNetContent() {

        Class<IMConversationListOperation> imConversationListOperationClass = IMConversationListOperation.class;

        Annotation[] annotations = imConversationListOperationClass.getAnnotations();

        LogUtil.e(TAG, "annotations : " + annotations.length);

    }


}
