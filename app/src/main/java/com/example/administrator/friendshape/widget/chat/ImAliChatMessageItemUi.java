package com.example.administrator.friendshape.widget.chat;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.alibaba.mobileim.aop.Pointcut;
import com.alibaba.mobileim.aop.custom.IMChattingPageOperateion;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.conversation.YWMessageBody;
import com.example.administrator.friendshape.ui.activity.component.FriendsControllerActivity;
import com.example.administrator.friendshape.ui.activity.component.OrderContentActivity;
import com.example.administrator.friendshape.utils.LogUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 作者：真理 Created by Administrator on 2018/11/24.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ImAliChatMessageItemUi extends IMChattingPageOperateion {

    String TAG = getClass().getSimpleName();

    public ImAliChatMessageItemUi(Pointcut pointcut) {
        super(pointcut);
    }

    @Override
    public boolean onMessageClick(Fragment fragment, YWMessage message) {
        LogUtil.e(TAG, "消息内容点击");
        YWMessageBody body = message.getMessageBody();
        String content = body.getContent();
        String data = (String) body.getExtraData();
        String summary = body.getSummary();

        LogUtil.e(TAG, "消息内容点击 content : " + content);
        LogUtil.e(TAG, "消息内容点击 data : " + data);
        LogUtil.e(TAG, "消息内容点击 summary : " + summary);

        int type = -1;
        String id = null;
        String orderid = null;

        try {
            JSONObject jsonObject = new JSONObject(content);
            type = jsonObject.getInt("type");
            id = jsonObject.getString("id");
            orderid = jsonObject.getString("orderid");
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            switch (type) {
                case 1:
                case 3:
                    Intent intent = new Intent(fragment.getActivity(), OrderContentActivity.class);
                    intent.putExtra("groupId", id);
                    intent.putExtra("orderId", orderid);
                    fragment.getActivity().startActivity(intent);
                    break;
                case 2:
                    Intent frientControllerIntent = new Intent(fragment.getActivity(), FriendsControllerActivity.class);
                    frientControllerIntent.putExtra("friendId", id);
                    frientControllerIntent.setFlags(0);
                    fragment.getActivity().startActivity(frientControllerIntent);
                    break;
            }
        }
        return super.onMessageClick(fragment, message);
    }
}
