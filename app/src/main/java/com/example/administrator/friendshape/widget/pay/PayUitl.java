package com.example.administrator.friendshape.widget.pay;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * Created by Administrator on 2018/8/7.
 */

public class PayUitl {

    private static final String ALGORITHM = "RSA";

    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * create the order info. 创建订单信息
     */
    public static String getOrderInfo(String subject,String body,String out_trade_no, String notify_url, String price,String partner,String seller) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + partner + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + seller + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + out_trade_no + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + notify_url + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }


    /**
     * get the sign type we use. 获取签名方式
     */
    public static String getSignType() {
        return "sign_type=\"RSA\"";
    }

    public static final int SDK_PAY_FLAG = 1;
    public static final int SDK_CHECK_FLAG = 2;

    /**
     * sign the order info. 对订单信息进行签名
     */
    public static String sign(String content, String privateKey) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance(ALGORITHM, "BC");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update(content.getBytes(DEFAULT_CHARSET));

            byte[] signed = signature.sign();

            return Base64.encode(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
