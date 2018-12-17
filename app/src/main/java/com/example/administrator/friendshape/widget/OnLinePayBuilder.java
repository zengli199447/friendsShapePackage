package com.example.administrator.friendshape.widget;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.example.administrator.friendshape.global.MyApplication;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.rxtools.RxBus;
import com.example.administrator.friendshape.widget.pay.PayResult;
import com.example.administrator.friendshape.widget.pay.PayUitl;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 作者：真理 Created by Administrator on 2018/11/22.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class OnLinePayBuilder {

    private String TAG = getClass().getSimpleName();

    private Context context;

    public OnLinePayBuilder(Context context) {
        this.context = context;
    }

    /**
     *  支付宝支付
     * @param subject
     * @param body
     * @param out_trade_no
     * @param notify_url
     * @param partner
     * @param seller
     * @param private_key
     * @param totalmoney
     */
    public void doAlipay(String subject, String body, String out_trade_no, String notify_url, String partner, String seller, String private_key, String totalmoney) {
        String orderInfo = PayUitl.getOrderInfo(subject, body, out_trade_no, notify_url, totalmoney, partner, seller);  // 生成订单
        String sign = PayUitl.sign(orderInfo, private_key); // 对订单做RSA 签名
        try {
            sign = URLEncoder.encode(sign, "UTF-8"); // 仅需对sign 做URL编码
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + PayUitl.getSignType();// 完整的符合支付宝参数规范的订单信息

        MyApplication.executorService.submit(new Runnable() {
            @Override
            public void run() {
                int status = 0;
                PayTask alipay = new PayTask((Activity) context); // 构造PayTask 对象
                final String result = alipay.pay(payInfo, true);   // 调用支付接口，获取支付结果
                PayResult payResult = new PayResult(result);
                String resultStatus = payResult.getResultStatus();
                String memo = payResult.getMemo();
                if (memo.contains("取消")) {
                    return;
                }
                if (TextUtils.equals(resultStatus, "9000")) {
                    status = 0;
                } else if (TextUtils.equals(resultStatus, "8000")) {
                    status = 2;
                } else {
                    status = 1;
                }
                RxBus.getDefault().post(new CommonEvent(EventCode.PAY_RETURN_STATUS, status));
            }
        });
    }

    /**
     * 微信支付
     * @param appId
     * @param partnerId
     * @param prepayId
     * @param nonceStr
     * @param timeStamp
     * @param packageValue
     * @param sign
     */
    public void doWeiXinPay(String appId, String partnerId, String prepayId, String nonceStr, String timeStamp, String packageValue, String sign) {
        IWXAPI api = WXAPIFactory.createWXAPI(context, null);
        api.registerApp(appId);
        PayReq req = new PayReq();
        req.appId = appId;
        req.partnerId = partnerId;
        req.prepayId = prepayId;
        req.nonceStr = nonceStr;
        req.timeStamp = timeStamp;
        req.packageValue = packageValue;
        req.sign = sign;
        boolean sendReq = api.sendReq(req);
    }

}
