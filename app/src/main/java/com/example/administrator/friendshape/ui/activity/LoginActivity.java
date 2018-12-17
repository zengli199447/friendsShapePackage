package com.example.administrator.friendshape.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.BaseActivity;
import com.example.administrator.friendshape.base.BaseLifecycleObserver;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.LoginInfoNetBean;
import com.example.administrator.friendshape.model.db.entity.LoginUserInfo;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.rxtools.RxBus;
import com.example.administrator.friendshape.ui.activity.component.BindPhoneActivity;
import com.example.administrator.friendshape.ui.controller.ControllerLogin;
import com.example.administrator.friendshape.ui.dialog.ProgressDialog;
import com.example.administrator.friendshape.ui.dialog.ShowDialog;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.widget.CalendarBuilder;
import com.example.administrator.friendshape.widget.UmComprehensiveBuilder;
import com.example.administrator.friendshape.widget.ViewBuilder;

import java.text.ParseException;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/10/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class LoginActivity extends BaseActivity implements ControllerLogin.LoginAndRegistereNetWorkListener, UmComprehensiveBuilder.onCompleteListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.layout_login_content)
    RelativeLayout layout_login_content;
    @BindView(R.id.layout_registered_and_forgot)
    LinearLayout layout_registered_and_forgot;
    @BindView(R.id.password_type)
    TextView password_type;

    @BindView(R.id.edit_user_name)
    EditText edit_user_name;
    @BindView(R.id.edit_pass_word)
    EditText edit_pass_word;
    @BindView(R.id.password_check)
    CheckBox password_check;
    @BindView(R.id.save)
    CheckBox save;

    @BindView(R.id.edit_first_pass_word)
    EditText edit_first_pass_word;
    @BindView(R.id.first_password_check)
    CheckBox first_password_check;
    @BindView(R.id.edit_commite_pass_word)
    EditText edit_commite_pass_word;
    @BindView(R.id.commite_password_check)
    CheckBox commite_password_check;
    @BindView(R.id.to_obtain_code)
    TextView to_obtain_code;

    @BindView(R.id.edit_you_phone)
    EditText edit_you_phone;
    @BindView(R.id.edit_verification_code)
    EditText edit_verification_code;
    @BindView(R.id.login_about)
    TextView login_about;


    private boolean returnStatus;
    private ControllerLogin controllerLogin;
    private LoginUserInfo loginUserInfo;
    private String verificationCode;

    int time;
    private ShowDialog instance;
    private String passWord;
    private UmComprehensiveBuilder umComprehensiveBuilder;
    private ProgressDialog progressDialog;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.CHANGE_SUCCESSFUL:
                swichTexttitle(0);
                break;
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initClass() {
        controllerLogin = new ControllerLogin();
        instance = ShowDialog.getInstance();
        umComprehensiveBuilder = new UmComprehensiveBuilder(this, toastUtil);
        progressDialog = instance.showProgressStatus(this);
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerLogin;
    }

    @Override
    protected void initData() {
        swichTexttitle(0);
        loginUserInfo = dataManager.queryLoginUserInfo(DataClass.STANDARD_USER);
        time = getResources().getInteger(R.integer.validation_interval);
    }

    @Override
    protected void initView() {
        if (loginUserInfo != null) {
            edit_user_name.setText(loginUserInfo.getUserPhoneNumber());
            if (!loginUserInfo.getUserPhoneNumber().isEmpty()) {
                edit_pass_word.setText(loginUserInfo.getUserPassWord());
                save.setChecked(true);
            }
        }
    }

    @Override
    protected void initListener() {
        controllerLogin.setLoginAndRegistereNetWorkListener(this);
        umComprehensiveBuilder.setOnCompleteListener(this);
    }

    @OnClick({R.id.img_btn_black, R.id.registered, R.id.forgot_password, R.id.password_check,
            R.id.first_password_check, R.id.commite_password_check, R.id.save, R.id.login, R.id.login_about, R.id.to_obtain_code,
            R.id.wechat, R.id.qq})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                if (!returnStatus) {
                    finish();
                } else {
                    swichTexttitle(0);
                }
                break;
            case R.id.registered:
                swichTexttitle(1);
                break;
            case R.id.forgot_password:
                swichTexttitle(2);
                break;
            case R.id.password_check:
                ViewBuilder.seeChecklListener(password_check, edit_pass_word);
                break;
            case R.id.first_password_check:
                ViewBuilder.seeChecklListener(first_password_check, edit_first_pass_word);
                break;
            case R.id.commite_password_check:
                ViewBuilder.seeChecklListener(commite_password_check, edit_commite_pass_word);
                break;
            case R.id.save:

                break;
            case R.id.login:
                DataClass.LOGINTYPE = 1;
                if (!edit_user_name.getText().toString().isEmpty() && !edit_pass_word.getText().toString().isEmpty()) {
                    progressDialog.show();
                    passWord = edit_pass_word.getText().toString();
                    controllerLogin.NetLogin(edit_user_name.getText().toString(), edit_pass_word.getText().toString());
                } else {
                    toastUtil.showToast(getString(R.string.empty_error));
                }
                break;
            case R.id.wechat:
                DataClass.LOGINTYPE = 3;
                umComprehensiveBuilder.initUmLogin(1);
                break;
            case R.id.qq:
                DataClass.LOGINTYPE = 2;
                umComprehensiveBuilder.initUmLogin(0);
                break;
            case R.id.login_about:
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
                    int type = -1; // 0 注册 1 修改密码
                    if (getString(R.string.commite).equals(login_about.getText().toString())) {
                        type = 0;
                    } else if (getString(R.string.commite_modify).equals(login_about.getText().toString())) {
                        type = 1;
                    }
                    controllerLogin.NetRegisteredLogin(edit_you_phone.getText().toString().trim(), edit_commite_pass_word.getText().toString().trim(), type);
                }
                break;
            case R.id.to_obtain_code:
                if (edit_you_phone.getText().toString().trim().isEmpty()) {
                    toastUtil.showToast(getString(R.string.empty_phone_number));
                } else {
                    int controllerType = 0;
                    if (getString(R.string.commite).equals(login_about.getText().toString())) {
                        controllerType = 1;
                    } else if (getString(R.string.commite_modify).equals(login_about.getText().toString())) {
                        controllerType = 2;
                    }
                    controllerLogin.NetVerificationCode(edit_you_phone.getText().toString().trim(), controllerType);
                }
                break;
        }
    }

    //登录成功返回
    @Override
    public void onLoginNetWorkListener(LoginInfoNetBean.ResultBean result) {
        RxBus.getDefault().post(new CommonEvent(EventCode.REFRESH_IM_STATUS));
        progressDialog.dismiss();
        try {
            DataClass.UNAME = result.getSecondname();
            DataClass.USERID = result.getUserid();
            DataClass.USERPHOTO = result.getPhoto();
            DataClass.GENDER = result.getSex();
            DataClass.AGE = String.valueOf(CalendarBuilder.getAgeByBirthday(result.getBrithday()));
            DataClass.PHONE = result.getPhone();
            if (DataClass.LOGINTYPE == 1) {
                DataClass.PASSWORD = passWord;
            } else {
                DataClass.PASSWORD = result.getPwd();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (save.isChecked() || DataClass.LOGINTYPE != 1) {
            LogUtil.e(TAG, "保存账号至本地");
            LoginUserInfo loginUserInfo = dataManager.queryLoginUserInfo(DataClass.STANDARD_USER);
            if (loginUserInfo != null) {
                loginUserInfo.setUserGender(result.getSex());
                loginUserInfo.setUserid(result.getUserid());
                loginUserInfo.setUserNiceName(result.getSecondname());
                loginUserInfo.setUserPassWord(DataClass.PASSWORD);
                loginUserInfo.setUserPhoneNumber(result.getPhone());
                dataManager.UpDataLoginUserInfo(loginUserInfo);
            } else {
                dataManager.insertLoginUserInfo(new LoginUserInfo(DataClass.STANDARD_USER, result.getUserid(), result.getSecondname(), result.getPhone(), DataClass.PASSWORD, result.getSex()));
            }
        }
        if (DataClass.LOGINTYPE != 1 && result.getPhone().isEmpty()) {
            startActivity(new Intent(this, BindPhoneActivity.class).setFlags(0));
            finish();
        } else {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
    }

    //注册、修改密码返回
    @Override
    public void onRegistereNetWorkListener(int type) {
        String content = null;
        switch (type) {
            case 0:
                content = getString(R.string.registered_successful);
                break;
            case 1:
                content = getString(R.string.change_successful);
                break;
        }
        edit_you_phone.setText("");
        edit_verification_code.setText("");
        edit_first_pass_word.setText("");
        edit_commite_pass_word.setText("");
        instance.showHelpfulHintsDialog(this, content, EventCode.CHANGE_SUCCESSFUL);
    }

    //验证码返回
    @Override
    public void onVerificationCodeNetWorkListener(String verificationCode) {
        this.verificationCode = verificationCode;
        to_obtain_code.setEnabled(false);
        to_obtain_code.setTextColor(getResources().getColor(R.color.gray_light_text));
        refreshVerificationCodeView();
    }

    @Override
    public void onBindPhoneNetWorkListener() {

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

    /**
     * 业务状态更新
     *
     * @param type 0.登陆 1.注册 2.忘记、修改密码
     */
    private void swichTexttitle(int type) {
        switch (type) {
            case 0:
                title_name.setText(getString(R.string.login_));
                layout_login_content.setVisibility(View.VISIBLE);
                layout_registered_and_forgot.setVisibility(View.GONE);
                returnStatus = false;
                break;
            case 1:
                title_name.setText(getString(R.string.registered));
                password_type.setText(getString(R.string.password));
                layout_login_content.setVisibility(View.GONE);
                layout_registered_and_forgot.setVisibility(View.VISIBLE);
                returnStatus = true;
                login_about.setText(getString(R.string.commite));
                break;
            case 2:
                title_name.setText(getString(R.string.modify_the_password));
                password_type.setText(getString(R.string.new_password));
                layout_login_content.setVisibility(View.GONE);
                layout_registered_and_forgot.setVisibility(View.VISIBLE);
                login_about.setText(getString(R.string.commite_modify));
                returnStatus = true;
                break;
        }
    }

    //第三方登录返回
    @Override
    public void comlete(Map<String, String> data) {
        String qq = "";
        String qqName = "";
        String wechatId = "";
        String wechatName = "";
        String photo = "";
        switch (DataClass.LOGINTYPE) {
            case 2:
                qq = data.get("openid");
                qqName = data.get("screen_name");
                photo = data.get("profile_image_url");
                break;
            case 3:
                wechatId = data.get("unionid");
                wechatName = data.get("screen_name");
                photo = data.get("profile_image_url");
                break;
        }
        progressDialog.show();
        controllerLogin.PlatformLoginNetWork(qq, qqName, wechatId, wechatName, photo);
    }

    //第三方登录返回 获取权限失败
    @Override
    public void notReach() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (returnStatus) {
                swichTexttitle(0);
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
