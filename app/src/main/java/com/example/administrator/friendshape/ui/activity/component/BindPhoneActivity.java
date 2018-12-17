package com.example.administrator.friendshape.ui.activity.component;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.BaseActivity;
import com.example.administrator.friendshape.base.BaseLifecycleObserver;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.LoginInfoNetBean;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.ui.activity.LoginActivity;
import com.example.administrator.friendshape.ui.controller.ControllerLogin;
import com.example.administrator.friendshape.ui.dialog.ShowDialog;
import com.example.administrator.friendshape.widget.ViewBuilder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/7.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class BindPhoneActivity extends BaseActivity implements ControllerLogin.LoginAndRegistereNetWorkListener {

    @BindView(R.id.title_name)
    TextView title_name;

    @BindView(R.id.edit_you_phone)
    EditText edit_you_phone;
    @BindView(R.id.to_obtain_code)
    TextView to_obtain_code;
    @BindView(R.id.edit_verification_code)
    EditText edit_verification_code;

    @BindView(R.id.password_controller)
    LinearLayout password_controller;
    @BindView(R.id.edit_first_pass_word)
    EditText edit_first_pass_word;
    @BindView(R.id.first_password_check)
    AppCompatCheckBox first_password_check;
    @BindView(R.id.edit_commite_pass_word)
    EditText edit_commite_pass_word;
    @BindView(R.id.commite_password_check)
    AppCompatCheckBox commite_password_check;

    @BindView(R.id.commite_bind)
    TextView commite_bind;

    private int flags;
    private ControllerLogin controllerLogin;
    private ShowDialog instance;
    private String verificationCode;
    int time;


    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.MODIFY_SUCCESSFULLY:
                DataClass.ClearUserInfo(dataManager);
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case EventCode.BIND_SUCCESSFULLY:
                finish();
                break;
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_bind_phone;
    }

    @Override
    protected void initClass() {
        controllerLogin = new ControllerLogin();
        instance = ShowDialog.getInstance();
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerLogin;
    }

    @Override
    protected void initData() {
        flags = getIntent().getFlags();
        time = getResources().getInteger(R.integer.validation_interval);
    }

    @Override
    protected void initView() {
        switch (flags) {
            case 0:
                title_name.setText(getString(R.string.bind_phone));
                break;
            case 1:
                title_name.setText(getString(R.string.modify_the_password));
                password_controller.setVisibility(View.VISIBLE);
                commite_bind.setText(getString(R.string.commite_modify));
                break;
        }
    }

    @Override
    protected void initListener() {
        controllerLogin.setLoginAndRegistereNetWorkListener(this);

    }

    @OnClick({R.id.commite_bind, R.id.to_obtain_code, R.id.first_password_check, R.id.commite_password_check, R.id.img_btn_black})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.commite_bind:
                int bindType = -1;
                switch (flags) {
                    case 0:
                        switch (DataClass.LOGINTYPE) {
                            case 2:
                                bindType = 1;
                                break;
                            case 3:
                                bindType = 2;
                                break;
                        }
                        if (edit_you_phone.getText().toString().trim().isEmpty()) {
                            toastUtil.showToast(getString(R.string.empty_phone_number));
                        } else if (edit_verification_code.getText().toString().trim().isEmpty()) {
                            toastUtil.showToast(getString(R.string.empty_verification_code));
                        } else if (!verificationCode.equals(edit_verification_code.getText().toString().trim())) {
                            toastUtil.showToast(getString(R.string.error_verification_code));
                        }
                        controllerLogin.BindPhoneNetWork(edit_you_phone.getText().toString().trim(), bindType);
                        break;
                    case 1:
                        if (edit_you_phone.getText().toString().trim().isEmpty()) {
                            toastUtil.showToast(getString(R.string.empty_phone_number));
                        } else if (edit_verification_code.getText().toString().trim().isEmpty()) {
                            toastUtil.showToast(getString(R.string.empty_verification_code));
                        } else if (!verificationCode.equals(edit_verification_code.getText().toString().trim())) {
                            toastUtil.showToast(getString(R.string.error_verification_code));
                        } else if (edit_first_pass_word.getText().toString().trim().isEmpty() || edit_commite_pass_word.getText().toString().trim().isEmpty()) {
                            toastUtil.showToast(getString(R.string.empty_pass_word));
                        } else if (!edit_first_pass_word.getText().toString().trim().equals(edit_commite_pass_word.getText().toString().trim())) {
                            toastUtil.showToast(getString(R.string.error_pass_word));
                        } else {
                            controllerLogin.NetRegisteredLogin(edit_you_phone.getText().toString().trim(), edit_first_pass_word.getText().toString().trim(), 1);
                        }
                        break;
                }
                break;
            case R.id.to_obtain_code:
                if (edit_you_phone.getText().toString().trim().isEmpty()) {
                    toastUtil.showToast(getString(R.string.empty_phone_number));
                } else {
                    int controllerType = -1;
                    if (getString(R.string.bind_phone).equals(title_name.getText().toString())) {
                        switch (DataClass.LOGINTYPE) {
                            case 2:
                                controllerType = 4;
                                break;
                            case 3:
                                controllerType = 5;
                                break;
                        }
                    } else if (getString(R.string.modify_the_password).equals(title_name.getText().toString())) {
                        controllerType = 2;
                    }
                    controllerLogin.NetVerificationCode(edit_you_phone.getText().toString().trim(), controllerType);
                }
                break;
            case R.id.first_password_check:
                ViewBuilder.seeChecklListener(first_password_check, edit_first_pass_word);
                break;
            case R.id.commite_password_check:
                ViewBuilder.seeChecklListener(commite_password_check, edit_commite_pass_word);
                break;
            case R.id.img_btn_black:
                finish();
                break;
        }
    }

    @Override
    public void onLoginNetWorkListener(LoginInfoNetBean.ResultBean result) {

    }

    @Override
    public void onRegistereNetWorkListener(int type) {
        clearEdit();
        instance.showHelpfulHintsDialog(this, getString(R.string.modify_successfully), EventCode.MODIFY_SUCCESSFULLY);
    }

    @Override
    public void onVerificationCodeNetWorkListener(String verificationCode) {
        this.verificationCode = verificationCode;
        to_obtain_code.setEnabled(false);
        to_obtain_code.setTextColor(getResources().getColor(R.color.gray_light_text));
        refreshVerificationCodeView();
    }

    @Override
    public void onBindPhoneNetWorkListener() {
        clearEdit();
        instance.showHelpfulHintsDialog(this, getString(R.string.bind_successfully), EventCode.BIND_SUCCESSFULLY);
    }

    private void clearEdit() {
        edit_you_phone.setText("");
        edit_verification_code.setText("");
        edit_first_pass_word.setText("");
        edit_commite_pass_word.setText("");
    }

    //验证码状态刷新
    private void refreshVerificationCodeView() {
        if (time > 0) {
            time = time - 1;
            to_obtain_code.setText(new StringBuffer().append(time).append(getString(R.string.seconds_about)));
            handler.sendEmptyMessageDelayed(0, 999);
        } else {
            to_obtain_code.setEnabled(true);
            to_obtain_code.setTextColor(getResources().getColor(R.color.blue_bar));
            to_obtain_code.setText(getString(R.string.to_obtain_code));
            time = getResources().getInteger(R.integer.validation_interval);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case 0:
                    if (to_obtain_code != null)
                        refreshVerificationCodeView();
                    break;
            }
        }
    };

}
