package com.example.administrator.friendshape.widget.chat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.aop.Pointcut;
import com.alibaba.mobileim.aop.custom.IMChattingPageUI;
import com.alibaba.mobileim.channel.constant.YWProfileSettingsConstants;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.conversation.YWConversationType;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.fundamental.widget.WxAlertDialog;
import com.alibaba.mobileim.gingko.model.tribe.YWTribe;
import com.alibaba.mobileim.utility.YWIMImageUtils;
import com.example.administrator.friendshape.R;

/**
 * 作者：真理 Created by Administrator on 2018/11/26.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ImAliChatTheMessageBoardUi extends IMChattingPageUI {

    public ImAliChatTheMessageBoardUi(Pointcut pointcut) {
        super(pointcut);
    }

    /**
     * 设置消息气泡背景图，需要.9图
     *
     * @param conversation 当前消息所在会话
     * @param message      需要设置背景的消息
     * @param self         是否是自己发送的消息，true：自己发送的消息， false：别人发送的消息
     * @return 0: 默认背景 －1:透明背景（无背景） >0:使用用户设置的背景图
     */
    @Override
    public int getMsgBackgroundResId(YWConversation conversation, YWMessage message, boolean self) {
        if (true)
            return super.getMsgBackgroundResId(conversation, message, self);
        int msgType = message.getSubType();
        if (msgType == YWMessage.SUB_MSG_TYPE.IM_TEXT || msgType == YWMessage.SUB_MSG_TYPE.IM_AUDIO) {
            if (self) {
                return R.drawable.select_talk_pop_r_bg;
            } else {
                return R.drawable.select_talk_pop_l_bg;
            }
        } else if (msgType == YWMessage.SUB_MSG_TYPE.IM_IMAGE) {
            if (self) {
                return R.drawable.select_talk_pic_pop_r_bg;
            } else {
                return R.drawable.select_talk_pic_pop_l_bg;
            }
        } else if (msgType == YWMessage.SUB_MSG_TYPE.IM_VIDEO) {
            if (self) {
                return R.drawable.select_talk_pic_pop_r_bg;
            } else {
                return R.drawable.select_talk_pic_pop_l_bg;
            }
        } else if (msgType == YWMessage.SUB_MSG_TYPE.IM_GEO) {
            if (self) {
                return R.drawable.aliwx_comment_r_bg;
            } else {
                return R.drawable.aliwx_comment_l_bg;
            }
        } else if (msgType == YWMessage.SUB_MSG_TYPE.IM_P2P_CUS || msgType == YWMessage.SUB_MSG_TYPE.IM_TRIBE_CUS) {
            if (self) {
                return -1;
            } else {
                return -1;
            }
        }
        return super.getMsgBackgroundResId(conversation, message, self);
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
        return 0;
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
        return 0;
    }

    @Override
    public boolean needHideTitleView(Fragment fragment, YWConversation conversation) {
        return true;
    }

}
