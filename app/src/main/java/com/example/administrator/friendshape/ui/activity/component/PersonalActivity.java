package com.example.administrator.friendshape.ui.activity.component;

import android.content.Intent;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.BaseActivity;
import com.example.administrator.friendshape.base.BaseLifecycleObserver;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.PersonalContentNetBean;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.ui.controller.ControllerPersonal;
import com.example.administrator.friendshape.ui.dialog.ProgressDialog;
import com.example.administrator.friendshape.ui.dialog.ShowDialog;
import com.example.administrator.friendshape.ui.view.CustomSingleChoicePopupWindow;
import com.example.administrator.friendshape.ui.view.CustomTimeChoicePopupWindow;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.utils.SystemUtil;
import com.example.administrator.friendshape.widget.AlbumBuilder;
import com.example.administrator.friendshape.widget.CalendarBuilder;
import com.example.administrator.friendshape.widget.MultipartBuilder;
import com.google.gson.Gson;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/10/31.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class PersonalActivity extends BaseActivity implements CustomSingleChoicePopupWindow.OnItemClickListener, CustomSingleChoicePopupWindow.OnDismissListener, ControllerPersonal.PersonalContentNetWorkListener, MultipartBuilder.UpLoadFileListener, CustomTimeChoicePopupWindow.OnItemClickListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_about_text)
    TextView title_about_text;
    @BindView(R.id.user_photo)
    ImageView user_photo;
    @BindView(R.id.edit_nick_name)
    EditText edit_nick_name;
    @BindView(R.id.edit_gender)
    TextView edit_gender;
    @BindView(R.id.edit_age)
    TextView edit_age;
    @BindView(R.id.user_id)
    TextView user_id;
    @BindView(R.id.the_signature)
    EditText the_signature;
    @BindView(R.id.gender_not_change)
    TextView gender_not_change;

    boolean theEditorStatus;
    private AlbumBuilder albumBuilder;
    private CustomSingleChoicePopupWindow customSingleChoicePopupWindow;
    private ControllerPersonal controllerPersonal;
    private MultipartBuilder multipartBuilder;
    private CustomTimeChoicePopupWindow customTimeChoicePopupWindow;
    private ProgressDialog progressDialog;
    private boolean changeGenderStatus;
    private ShowDialog instance;


    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.PICTURE:
                refreshView();
                break;
            case EventCode.PERSONAL_CHANGE_REFRESH:
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
        return R.layout.activity_personal;
    }

    @Override
    protected void initClass() {
        albumBuilder = new AlbumBuilder(this);
        customSingleChoicePopupWindow = new CustomSingleChoicePopupWindow(this);
        customTimeChoicePopupWindow = new CustomTimeChoicePopupWindow(this, new CalendarBuilder(Calendar.getInstance()), false);
        controllerPersonal = new ControllerPersonal();
        multipartBuilder = new MultipartBuilder(this, 0);
        instance = ShowDialog.getInstance();
        progressDialog = instance.showProgressStatus(this);
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerPersonal;
    }

    @Override
    protected void initData() {
        if (DataClass.GENDER.isEmpty()) {
            changeGenderStatus = true;
            instance.showHelpfulHintsDialog(this, getString(R.string.not_change_gender), EventCode.ONSTART);
        }
    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.personal));
        title_about_text.setText(getString(R.string.the_editor));
        CheckPermissions();
    }

    @Override
    protected void initListener() {
        customSingleChoicePopupWindow.setOnItemClickListener(this);
        customSingleChoicePopupWindow.setOnDismissListener(this);
        controllerPersonal.setPersonalContentNetWorkListener(this);
        customTimeChoicePopupWindow.setOnItemClickListener(this);
        customTimeChoicePopupWindow.setOnDismissListener(this);
        multipartBuilder.setUpLoadFileListener(this);
    }

    @OnClick({R.id.user_photo, R.id.img_btn_black, R.id.title_about_text, R.id.edit_gender, R.id.edit_age})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.user_photo:
                albumBuilder.ImageSingleSelection();
                break;
            case R.id.img_btn_black:
                if (DataClass.GENDER.isEmpty()) {
                    toastUtil.showToast(getString(R.string.select_gender));
                } else {
                    finish();
                }
                break;
            case R.id.title_about_text:
                if (theEditorStatus) {
                    progressDialog.show();
                    if (DataClass.USERPHOTO.contains("storage")) {
                        multipartBuilder.commitFile(DataClass.USERPHOTO);
                    } else {
                        onUpLoadFileListener("");
                    }
                }
                theEditorStatus = !theEditorStatus;
                CheckPermissions();
                break;
            case R.id.edit_gender:
                customSingleChoicePopupWindow.selectType(1, edit_gender.getText().toString());
                customSingleChoicePopupWindow.showAtLocation(findViewById(R.id.user_id), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                SystemUtil.windowToDark(this);
                break;
            case R.id.edit_age:
                customTimeChoicePopupWindow.refreshTitle(getString(R.string.choose_birthday), 0);
                customTimeChoicePopupWindow.showAtLocation(findViewById(R.id.user_id), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                SystemUtil.windowToDark(this);
                break;
        }
    }

    private void refreshView() {
        Glide.with(this).load(SystemUtil.JudgeUrl(DataClass.USERPHOTO)).error(R.drawable.banner_off).into(user_photo);
    }

    //输入权限
    private void CheckPermissions() {
        if (!theEditorStatus) {
            edit_nick_name.setFocusable(false);
            the_signature.setFocusable(false);
            edit_gender.setEnabled(false);
            edit_age.setEnabled(false);
            user_photo.setEnabled(false);
            title_about_text.setText(getString(R.string.the_editor));
            title_about_text.setTextColor(getResources().getColor(R.color.black_overlay));
        } else {
            edit_nick_name.setFocusableInTouchMode(true);
            the_signature.setFocusableInTouchMode(true);
            if (changeGenderStatus)
                edit_gender.setEnabled(true);
            edit_age.setEnabled(true);
            user_photo.setEnabled(true);
            title_about_text.setText(getString(R.string.save));
            title_about_text.setTextColor(getResources().getColor(R.color.blue_bar));
        }
    }

    @Override
    public void setOnItemClick(int currentSelectType, String selectContent, int currentIndex) {
        switch (currentSelectType) {
            case 0:
                edit_age.setText(selectContent);
                break;
            case 1:
                edit_gender.setText(selectContent);
                break;
        }
        customSingleChoicePopupWindow.dismiss();
    }

    @Override
    public void setOnItemClick(View v, String year, String month, String day, String hour, String minute, int selectType) {
        String birthday = new StringBuffer().append(year).append("-").append(month).append("-").append(day).toString();
        edit_age.setText(birthday);
        customTimeChoicePopupWindow.dismiss();
    }

    @Override
    public void onDismiss() {
        SystemUtil.windowToLight(this);
    }

    @Override
    public void onPersonalContentNetWorkListener(PersonalContentNetBean.ResultBean result) {
        edit_nick_name.setText(result.getSecondname());
        edit_gender.setText(result.getSex());
        edit_age.setText(result.getBrithday());
        user_id.setText(result.getUserid());
        the_signature.setText(result.getRemark());
        refreshView();
    }

    @Override
    public void onPersonalUpLoadStatusNetWorkListener() {
        progressDialog.dismiss();
    }

    @Override
    public void onUpLoadFileListener(String url) {
        if (edit_nick_name.getText().toString().isEmpty()) {
            toastUtil.showToast(getString(R.string.nice_name_empty));
        } else {
            if (url != null) {
                controllerPersonal.personalChangeNetWork(url, edit_nick_name.getText().toString(),
                        edit_gender.getText().toString(), edit_age.getText().toString(), the_signature.getText().toString());
            } else {
                toastUtil.showToast(getString(R.string.up_load_file_error));
                progressDialog.dismiss();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (DataClass.GENDER.isEmpty()) {
                toastUtil.showToast(getString(R.string.select_gender));
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
