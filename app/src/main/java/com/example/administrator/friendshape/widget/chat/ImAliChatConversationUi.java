package com.example.administrator.friendshape.widget.chat;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.aop.Pointcut;
import com.alibaba.mobileim.aop.custom.IMConversationListUI;
import com.alibaba.mobileim.channel.util.AccountUtils;
import com.alibaba.mobileim.contact.IYWContact;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.conversation.YWConversationType;
import com.alibaba.mobileim.conversation.YWCustomConversationBody;
import com.alibaba.mobileim.conversation.YWP2PConversationBody;
import com.alibaba.mobileim.conversation.YWTribeConversationBody;
import com.alibaba.mobileim.kit.contact.YWContactHeadLoadHelper;
import com.alibaba.mobileim.ui.IYWConversationFragment;
import com.alibaba.mobileim.utility.IMUtil;
import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.global.AppKeyConfig;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.ui.view.DrawableCenterTextView;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.utils.SystemUtil;
import com.example.administrator.friendshape.widget.AliChatBuilder;
import com.example.administrator.friendshape.widget.CalendarBuilder;
import com.example.administrator.friendshape.widget.ViewBuilder;

import java.lang.ref.WeakReference;

/**
 * 作者：真理 Created by Administrator on 2018/11/23.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ImAliChatConversationUi extends IMConversationListUI {

    private String TAG = getClass().getSimpleName();

    public ImAliChatConversationUi(Pointcut pointcut) {
        super(pointcut);
    }

    //不显示标题栏
    @Override
    public boolean needHideTitleView(Fragment fragment) {
        return true;
    }

    @Override
    public View getCustomConversationTitleView(Fragment fragment, YWConversation conversation, View convertView, TextView defaultView) {

        return super.getCustomConversationTitleView(fragment, conversation, convertView, defaultView);
    }

    /**
     * 是否隐藏无网络提醒View
     *
     * @param fragment
     * @return true: 隐藏无网络提醒，false：不隐藏无网络提醒
     */
    @Override
    public boolean needHideNullNetWarn(Fragment fragment) {
        return false;
    }

    public String getCustomTopConversationColor() {
        return "#ffffffff";
    }

    /**
     * 是否支持下拉刷新
     *
     * @return
     */
    @Override
    public boolean getPullToRefreshEnabled() {
        return false;
    }

    /**
     * 该方法可以构造一个会话列表为空时的展示View
     *
     * @return empty view
     */
    @Override
    public View getCustomEmptyViewInConversationUI(Context context) {
        /** 以下为示例代码，开发者可以按需返回任何view*/
        View view = LayoutInflater.from(context).inflate(R.layout.layout_chat_conveision_empty, null);
        return view;
    }

    @Override
    public int getCustomBackgroundResId() {
        return R.color.gray_;
    }

    private final int[] viewTypeArray = {0};

    @Override
    public int getCustomItemViewTypeCount() {
        return viewTypeArray.length;
    }

    public int getCustomItemViewType(YWConversation conversation) {
        int id = 0;
        switch (0) {
            case 0:
                id = viewTypeArray[0];
                break;
            case 1:
                id = super.getCustomItemViewType(conversation);
                break;
        }
        return id;
    }

    @Override
    public void onResume(Fragment fragment) {
        super.onResume(fragment);
    }

    /**
     * 会话列表初始化完成回调
     *
     * @param fragment 会话列表Fragment
     */
    @Override
    public void onInitFinished(IYWConversationFragment fragment) {
        //TODO 为了防止内存泄露这里请使用弱引用方式
        WeakReference<IYWConversationFragment> reference = new WeakReference<IYWConversationFragment>(fragment);
        //获取IYWConversationFragment实例，开发者可以通过该实例主动调用该接口内的方法
        IYWConversationFragment iywConversationFragment = reference.get();
        //TODO 由于是弱引用，所以conversationFragment可能为null，因此使用时一定要判空
        if (iywConversationFragment != null) {        //刷新adapter
            iywConversationFragment.refreshAdapter();
        }
    }

    /**
     * 会话列表onDestroy事件
     *
     * @param fragment
     */
    @Override
    public void onDestroy(Fragment fragment) {
        super.onDestroy(fragment);
    }

    /**
     * 根据viewType自定义item的view
     *
     * @param fragment
     * @param conversation   当前item对应的会话
     * @param convertView    convertView
     * @param viewType       当前itemView的viewType
     * @param headLoadHelper 加载头像管理器，用户可以使用该管理器设置头像
     * @param parent         getView中的ViewGroup参数
     * @return
     */
    @Override
    public View getCustomItemView(final Fragment fragment, YWConversation conversation, View convertView, int viewType, YWContactHeadLoadHelper headLoadHelper, ViewGroup parent) {
        String finalConversationId = "";
        if (viewType == viewTypeArray[0]) {
            ViewHolder holder = null;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(fragment.getActivity());
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_chat_message, parent, false);
                holder.user_img = convertView.findViewById(R.id.user_img);
                holder.user_name = convertView.findViewById(R.id.user_name);
                holder.content_message = convertView.findViewById(R.id.content_message);
                holder.creat_time = convertView.findViewById(R.id.creat_time);
                holder.message_count = convertView.findViewById(R.id.message_count);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            headLoadHelper.setDefaultHeadView(holder.user_img);
            headLoadHelper.setHeadView(holder.user_img, conversation);

            holder.content_message.setText(conversation.getLatestContent());
            holder.creat_time.setText(CalendarBuilder.formatHMSText(conversation.getLatestTime(),2));

            holder.creat_time.setText(IMUtil.getFormatTime(conversation.getLatestTimeInMillisecond(), DataClass.mIMKit.getIMCore().getServerTime()));
            String title = "";
            if (conversation.getConversationType() == YWConversationType.P2P) {
                final YWP2PConversationBody conversationBody = (YWP2PConversationBody) conversation.getConversationBody();

                if (!TextUtils.isEmpty(conversationBody.getContact().getShowName())) {
                    title = conversationBody.getContact().getShowName();
                } else {
                    YWIMKit imKit = YWAPI.getIMKitInstance(DataClass.USERID, AppKeyConfig.IM_ALI_CHAT);
                    IYWContact contact = imKit.getContactService().getContactProfileInfo(conversationBody.getContact().getUserId(), conversationBody.getContact().getAppKey());
                    if (contact != null && !TextUtils.isEmpty(contact.getShowName()))
                        title = contact.getShowName();
                }
                //如果标题为空,那么直接使用Id
                if (TextUtils.isEmpty(title))
                    title = conversationBody.getContact().getUserId();

//                finalConversationId = conversationBody.getContact().getUserId();

            } else {
                if (conversation.getConversationBody() instanceof YWTribeConversationBody) {
                    title = ((YWTribeConversationBody) conversation.getConversationBody()).getTribe().getTribeName();
//                    finalConversationId = String.valueOf(((YWTribeConversationBody) conversation.getConversationBody()).getTribe().getTribeId());
                    if (TextUtils.isEmpty(title))
                        title = fragment.getActivity().getString(R.string.group_chat);
                } else {
                    if (conversation.getConversationType() == YWConversationType.SHOP)
                        title = AccountUtils.getShortUserID(conversation.getConversationId());
//                    finalConversationId = AccountUtils.getShortUserID(conversation.getConversationId());
                }
            }

            if (conversation.getConversationId().equals("igmwqgs4kefu")) {
                holder.user_name.setText(fragment.getActivity().getString(R.string.system_service));
            } else {
                holder.user_name.setText(title);
            }

            int unreadCount = conversation.getUnreadCount();
            if (unreadCount > 0) {
                holder.message_count.setVisibility(View.VISIBLE);
                if (unreadCount > 99) {
                    holder.message_count.setText("99+");
                } else {
                    holder.message_count.setText(String.valueOf(unreadCount));
                }
            }

//            final String finalConversationId1 = finalConversationId;
//            convertView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    LogUtil.e(TAG, "会话点击");
//                    new AliChatBuilder(fragment.getActivity()).OpenSingleConversation(finalConversationId1);
//                }
//            });

            return convertView;
        }
        return super.getCustomItemView(fragment, conversation, convertView, viewType, headLoadHelper, parent);
    }


    public class ViewHolder {
        ImageView user_img;
        TextView user_name;
        TextView content_message;
        TextView creat_time;
        TextView message_count;
    }

}
