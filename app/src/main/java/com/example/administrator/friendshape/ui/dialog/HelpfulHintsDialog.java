package com.example.administrator.friendshape.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.BaseDialog;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.rxtools.RxBus;
import com.example.administrator.friendshape.utils.SystemUtil;

import butterknife.BindView;

/**
 * 作者：真理 Created by Administrator on 2018/11/9.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class HelpfulHintsDialog extends BaseDialog implements View.OnClickListener{

    @BindView(R.id.btn_commit)
    TextView btn_commit;
    @BindView(R.id.content)
    TextView content_text;

    private Context context;
    private String content;
    private int EventCode;

    protected HelpfulHintsDialog(Context context, int themeResId, String content,int EventCode) {
        super(context, themeResId);
        this.context = context;
        this.content = content;
        this.EventCode = EventCode;
    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_helpful_hints;
    }

    @Override
    protected void initClass() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        content_text.setText(content);
    }

    @Override
    protected void initListener() {
        btn_commit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_commit:
                RxBus.getDefault().post(new CommonEvent(EventCode));
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
