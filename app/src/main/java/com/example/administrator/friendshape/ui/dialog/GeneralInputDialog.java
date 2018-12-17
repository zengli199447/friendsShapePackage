package com.example.administrator.friendshape.ui.dialog;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.BaseDialog;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.rxtools.RxBus;
import com.example.administrator.friendshape.utils.SystemUtil;
import com.example.administrator.friendshape.utils.ToastUtil;

import butterknife.BindView;

/**
 * 作者：真理 Created by Administrator on 2018/11/13.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class GeneralInputDialog extends BaseDialog implements View.OnClickListener {

    @BindView(R.id.btn_commit)
    TextView btn_commit;
    @BindView(R.id.content)
    EditText content_text;

    private Context context;
    private String content;
    private int EventCode;

    protected GeneralInputDialog(Context context, int themeResId, String content, int EventCode) {
        super(context, themeResId);
        this.context = context;
        this.content = content;
        this.EventCode = EventCode;
    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_general_input;
    }

    @Override
    protected void initClass() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        content_text.setHint(content);
    }

    @Override
    protected void initListener() {
        btn_commit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_commit:
                if (content_text.getText().toString().isEmpty()) {
                    new ToastUtil(context).showToast(context.getString(R.string.empty_content));
                } else {
                    RxBus.getDefault().post(new CommonEvent(EventCode, content_text.getText().toString()));
                    dismiss();
                }
                break;
        }
    }

    @Override
    protected void onDismissListener() {
        super.onDismissListener();
        SystemUtil.windowToLight(context);
    }

    public void showKeyboard() {
        if (content_text != null) {
            //设置可获得焦点
            content_text.setFocusable(true);
            content_text.setFocusableInTouchMode(true);
            //请求获得焦点
            content_text.requestFocus();
            //调用系统输入法
            InputMethodManager inputManager = (InputMethodManager) content_text.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(content_text, 0);
        }
    }

}
