package com.example.administrator.friendshape.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.mobileim.contact.IYWContact;
import com.alibaba.mobileim.contact.IYWContactService;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.conversation.YWConversationBody;
import com.alibaba.mobileim.conversation.YWMessageLoader;
import com.alibaba.mobileim.conversation.YWP2PConversationBody;
import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.ui.holder.MyViewHolder;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.utils.SystemUtil;
import com.example.administrator.friendshape.widget.CalendarBuilder;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/23.
 * 邮箱：229017464@qq.com
 * remark: 注意  百川通讯未提供用户详细信息，需要根据ID 来查询本地服务的相关信息
 */
public class ChatMessageAdapter extends RecyclerView.Adapter<MyViewHolder> {

    String TAG = getClass().getSimpleName();

    Context context;
    List<YWConversation> list;

    public ChatMessageAdapter(Context context, List<YWConversation> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chat_message, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        YWConversation ywConversation = list.get(position);
        ImageView user_img = holder.itemView.findViewById(R.id.user_img);
        TextView user_name = holder.itemView.findViewById(R.id.user_name);
        TextView content_message = holder.itemView.findViewById(R.id.content_message);
        TextView creat_time = holder.itemView.findViewById(R.id.creat_time);
        TextView message_count = holder.itemView.findViewById(R.id.message_count);
        View line = holder.itemView.findViewById(R.id.line);

        content_message.setText(ywConversation.getLatestContent());
        creat_time.setText(CalendarBuilder.formatHMSText(ywConversation.getLatestTime(),2));

        if (ywConversation.getUnreadCount() > 0) {
            message_count.setText(String.valueOf(ywConversation.getUnreadCount()));
            message_count.setVisibility(View.VISIBLE);
        } else {
            message_count.setVisibility(View.GONE);
        }

        if (position == list.size() - 1) {
            line.setVisibility(View.GONE);
        } else {
            line.setVisibility(View.VISIBLE);
        }

        YWConversationBody conversationBody = ywConversation.getConversationBody();

        if (conversationBody instanceof YWP2PConversationBody) {
            YWP2PConversationBody conversationBodyAbout = (YWP2PConversationBody) conversationBody;
            IYWContact contact = conversationBodyAbout.getContact();

            LogUtil.e(TAG, "contact.getShowName() : " + contact.getShowName());

            LogUtil.e(TAG, "contact.getAvatarPath() : " + contact.getAvatarPath());

            LogUtil.e(TAG, "contact.getUserId() : " + contact.getUserId());

            LogUtil.e(TAG, "contact.getAppKey() : " + contact.getAppKey());

        }

    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size();
    }

    public interface ClickListener {
        void onClickListener();

    }

    private ClickListener clickListener;

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }


}
