package com.example.administrator.friendshape.widget.chat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.aop.Pointcut;
import com.alibaba.mobileim.aop.custom.IMChattingPageUI;
import com.alibaba.mobileim.channel.util.AccountUtils;
import com.alibaba.mobileim.contact.IYWContact;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.conversation.YWConversationBody;
import com.alibaba.mobileim.conversation.YWConversationDraft;
import com.alibaba.mobileim.conversation.YWConversationType;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.conversation.YWMessageBody;
import com.alibaba.mobileim.conversation.YWP2PConversationBody;
import com.alibaba.mobileim.conversation.YWTribeConversationBody;
import com.alibaba.mobileim.utility.YWIMImageUtils;
import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.global.AppKeyConfig;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.ui.activity.component.TheDetailsInformationActivity;
import com.example.administrator.friendshape.utils.LogUtil;

/**
 * 作者：真理 Created by Administrator on 2018/11/23.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ImAliChatTitleUi extends IMChattingPageUI {

    private String TAG = getClass().getSimpleName();

    public ImAliChatTitleUi(Pointcut pointcut) {
        super(pointcut);
    }

    @Override
    public View getCustomTitleView(final Fragment fragment, final Context context, LayoutInflater inflater, YWConversation conversation) {
        View view = inflater.inflate(R.layout.layout_ali_title, new RelativeLayout(context), false);
        ImageButton img_btn_black = view.findViewById(R.id.img_btn_black);
        TextView title_name = view.findViewById(R.id.title_name);
        ImageView title_about_img = view.findViewById(R.id.title_about_img);

        String title = null;
        if (conversation.getConversationType() == YWConversationType.P2P) {
            final YWP2PConversationBody conversationBody = (YWP2PConversationBody) conversation.getConversationBody();
            ClickListener(fragment, title_about_img, conversationBody);
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
            if (context.getString(R.string.system_message).equals(title))
                title_about_img.setVisibility(View.GONE);
        } else {
            title_about_img.setVisibility(View.GONE);
            if (conversation.getConversationBody() instanceof YWTribeConversationBody) {
                title = ((YWTribeConversationBody) conversation.getConversationBody()).getTribe().getTribeName();
                if (TextUtils.isEmpty(title))
                    title = context.getString(R.string.group_chat);
            } else {
                if (conversation.getConversationType() == YWConversationType.SHOP)
                    title = AccountUtils.getShortUserID(conversation.getConversationId());
            }
        }

        if (conversation.getConversationId().equals("igmwqgs4kefu")) {
            title_name.setText(context.getString(R.string.system_service));
        } else {
            title_name.setText(title);
        }

        ClickListener(fragment, img_btn_black, null);

        return view;
    }

    private void ClickListener(final Fragment fragment, final View view, final YWP2PConversationBody conversationBody) {
        view.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.title_about_img:
                        Intent theDetailsInformationIntent = new Intent(fragment.getActivity(), TheDetailsInformationActivity.class);
                        theDetailsInformationIntent.putExtra("userId", conversationBody.getContact().getUserId());
                        theDetailsInformationIntent.setFlags(1);
                        fragment.getActivity().startActivity(theDetailsInformationIntent);
                        break;
                    case R.id.img_btn_black:
                        fragment.getActivity().finish();
                        break;
                }
            }
        });
    }

    /**
     * 设置消息气泡背景图，需要.9图
     *
     * @param conversation 当前消息所在会话
     * @param message      需要设置背景的消息
     * @param self         是否是自己发送的消息，true：自己发送的消息， false：别人发送的消息
     * @return 0: 默认背景 1:电视机 2:小猪佩琪
     */
    @Override
    public int getMsgBackgroundResId(YWConversation conversation, YWMessage message, boolean self) {
        int id = 0;
        switch (1) {
            case 0:
                if (self) {
                    id = R.drawable.aliwx_comment_r_bg;
                } else {
                    id = R.drawable.aliwx_comment_l_bg;
                }
                break;
            case 1:
                if (self) {
                    id = R.drawable.select_talk_pic_pop_r_bg;
                } else {
                    id = R.drawable.select_talk_pic_pop_l_bg;
                }
                break;
            case 2:
                if (self) {
                    id = R.drawable.select_talk_pop_r_bg;
                } else {
                    id = R.drawable.select_talk_pop_l_bg;
                }
                break;
        }
        return id;
    }

    /**
     * 建议使用{@link #processBitmapOfLeftImageMsg｝和{@link #processBitmapOfRightImageMsg｝灵活修改Bitmap，达到对图像进行［圆角处理］,［裁减］等目的,这里建议return false
     * 设置是否需要将聊天界面的图片设置为圆角
     *
     * @return false: 不做圆角处理
     * <br>
     * true：做圆角处理（重要：返回true时不会做{@link #processBitmapOfLeftImageMsg｝和{@link #processBitmapOfRightImageMsg｝二次图片处理，两者互斥！）
     */

    @Override
    public boolean needRoundChattingImage() {
        return false;
    }

    /**
     * 设置聊天界面图片圆角的边角半径的长度(单位：dp)
     *
     * @return
     */
    @Override
    public float getRoundRadiusDps() {
        return 12.6f;
    }

    /**
     * 设置聊天窗口背景
     *
     * @return 聊天窗口背景，默认不显示
     */
    @Override
    public int getChattingBackgroundResId() {
        //聊天窗口背景，默认不显示
        return R.color.gray_;
        // return R.drawable.demo_chatting_bg;
    }

    /**
     * 用于更灵活地加工［左边图片消息］的Bitmap用于显示，SDK内部会缓存之，后续直接使用缓存的Bitmap显示。例如：对图像进行［裁减］，［圆角处理］等等
     * 重要：使用该方法时：
     * 1.请将 {@link #needRoundChattingImage}设为return false(不裁剪圆角)，两者是互斥关系
     * 2.建议将{@link #getLeftImageMsgBackgroundResId}设为return－1（背景透明）
     *
     * @param input 网络获取的聊天图片
     * @return 供显示的Bitmap
     */
    public Bitmap processBitmapOfLeftImageMsg(Bitmap input) {
        Bitmap output = Bitmap.createBitmap(input.getWidth(),
                input.getHeight(), Bitmap.Config.ARGB_8888);
        //为提高性能，对取得的resource图片做缓存
        Bitmap distBitmap = YWIMImageUtils.getFromCacheOrDecodeResource(R.drawable.left_bubble);
        NinePatch np = new NinePatch(distBitmap, distBitmap.getNinePatchChunk(), null);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        final Rect rectSrc = new Rect(0, 0, input.getWidth(), input.getHeight());
        final RectF rectDist = new RectF(0, 0, input.getWidth(), input.getHeight());
        np.draw(canvas, rectDist);
        canvas.drawARGB(0, 0, 0, 0);
        //设置Xfermode
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(input, rectSrc, rectSrc, paint);
        return output;
    }

    /**
     * 用于更灵活地加工［右边图片消息］的Bitmap用于显示，SDK内部会缓存之，后续直接使用缓存的Bitmap显示。例如：对图像进行［裁减］，［圆角处理］等等
     * 重要：使用该方法时：
     * 1.请将 {@link #needRoundChattingImage}设为return false(不裁剪圆角)，两者是互斥关系
     * 2.建议将{@link #getRightImageMsgBackgroundResId}设为return－1（背景透明）
     *
     * @param input 网络获取的聊天图片
     * @return 供显示的Bitmap
     */
    public Bitmap processBitmapOfRightImageMsg(Bitmap input) {
        Bitmap output = Bitmap.createBitmap(input.getWidth(),
                input.getHeight(), Bitmap.Config.ARGB_8888);
        //为提高性能，对取得的resource图片做缓存
        Bitmap distBitmap = YWIMImageUtils.getFromCacheOrDecodeResource(R.drawable.right_bubble);
        NinePatch np = new NinePatch(distBitmap, distBitmap.getNinePatchChunk(), null);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        final Rect rectSrc = new Rect(0, 0, input.getWidth(), input.getHeight());
        final RectF rectDist = new RectF(0, 0, input.getWidth(), input.getHeight());
        np.draw(canvas, rectDist);
        canvas.drawARGB(0, 0, 0, 0);
        //设置Xfermode
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(input, rectSrc, rectSrc, paint);
        return output;
    }

    /**
     * 返回单聊默认头像资源Id
     *
     * @return 0:使用SDK默认提供的
     */
    @Override
    public int getDefaultHeadImageResId() {
        return 0;
    }

    /**
     * 是否需要圆角矩形的头像
     *
     * @return true:需要圆角矩形
     * <br>
     * false:不需要圆角矩形，默认为圆形
     * <br>
     * 注：如果返回true，则需要使用{@link #getRoundRectRadius()}给出圆角的设置半径，否则无圆角效果
     */
    @Override
    public boolean isNeedRoundRectHead() {
        return false;
    }

    /**
     * 返回设置圆角矩形的圆角半径大小
     *
     * @return 0:如果{@link #isNeedRoundRectHead()}返回true，此处返回0则表示头像显示为直角正方形
     */
    @Override
    public int getRoundRectRadius() {
        return 10;
    }

    /**
     * 返回自定义发送消息的文字颜资源Id
     *
     * @return 颜色资源Id
     */
    @Override
    public int getCustomRightTextColorId() {
        return R.color.black_overlay;
    }


}
