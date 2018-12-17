package com.example.administrator.friendshape.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.rxtools.RxBus;
import com.example.administrator.friendshape.utils.LogUtil;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

/**
 * 作者：真理 Created by Administrator on 2018/11/22.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
    private IWXAPI api;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {
                Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
                LogUtil.e(TAG, "支付成功");
                RxBus.getDefault().post(new CommonEvent(EventCode.PAY_RETURN_STATUS, 0));
            } else if (resp.errCode == -2) {
                Toast.makeText(this, "支付已取消", Toast.LENGTH_SHORT).show();
                LogUtil.e(TAG, "支付已取消");
            } else {
                Toast.makeText(this, "支付失败，请重试", Toast.LENGTH_SHORT).show();
                LogUtil.e(TAG, "支付失败，请重试");
                RxBus.getDefault().post(new CommonEvent(EventCode.PAY_RETURN_STATUS, 1));
            }
        } else {
            if (resp.errCode == -2) {
                Toast.makeText(this, "支付已取消", Toast.LENGTH_SHORT).show();
                LogUtil.e(TAG, "支付已取消");
            } else {
                Toast.makeText(this, "支付失败，请重试", Toast.LENGTH_SHORT).show();
                LogUtil.e(TAG, "支付失败，请重试");
                RxBus.getDefault().post(new CommonEvent(EventCode.PAY_RETURN_STATUS, 1));
            }
        }
        finish();
    }

}
