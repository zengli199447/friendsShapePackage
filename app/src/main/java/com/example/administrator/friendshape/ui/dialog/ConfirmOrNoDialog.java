package com.example.administrator.friendshape.ui.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.BaseDialog;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.rxtools.RxBus;
import com.example.administrator.friendshape.utils.SystemUtil;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/8/15.
 */

public class ConfirmOrNoDialog extends BaseDialog implements View.OnClickListener {

    private Context context;
    private String content;
    private int type;
    int commit;
    int cancle;

    @BindView(R.id.btn_cancle)
    TextView btn_cancle;
    @BindView(R.id.btn_commit)
    TextView btn_commit;
    @BindView(R.id.content)
    TextView content_text;


    protected ConfirmOrNoDialog(Context context, @StyleRes int themeResId, String content, int type, int commit, int cancle) {
        super(context, themeResId);
        this.context = context;
        this.content = content;
        this.type = type;
        this.commit = commit;
        this.cancle = cancle;

    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_confirm;
    }

    @Override
    protected void initClass() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        switch (type) {
            case EventCode.CALL_PHONE:
                content_text.setText(new StringBuffer().append(context.getString(R.string.or_call)).append(content).toString());
                break;
            case EventCode.ONSTART:
                content_text.setText(content);
                break;
        }
    }

    @Override
    protected void initListener() {
        btn_cancle.setOnClickListener(this);
        btn_commit.setOnClickListener(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancle:
                switch (type) {
                    case EventCode.ONSTART:
                        RxBus.getDefault().post(new CommonEvent(cancle));
                        break;
                }
                dismiss();
                break;
            case R.id.btn_commit:
                switch (type) {
                    case EventCode.CALL_PHONE:
                        Intent Intent = new Intent(android.content.Intent.ACTION_CALL, Uri.parse("tel:" + content));//直接拨打电话
                        context.startActivity(Intent);
                        break;
                    case EventCode.ONSTART:
                        RxBus.getDefault().post(new CommonEvent(commit));
                        break;
                }
                dismiss();
                break;
        }
    }

    @Override
    protected void onDismissListener() {
        super.onDismissListener();
        SystemUtil.windowToLight(context);
    }

}
