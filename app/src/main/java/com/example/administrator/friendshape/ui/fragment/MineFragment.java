package com.example.administrator.friendshape.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.BaseFragment;
import com.example.administrator.friendshape.base.BaseLifecycleObserver;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.PersonalContentNetBean;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.ui.activity.component.BindPhoneActivity;
import com.example.administrator.friendshape.ui.activity.component.GeneralActivity;
import com.example.administrator.friendshape.ui.activity.component.MyDynamicActivity;
import com.example.administrator.friendshape.ui.activity.component.PersonalActivity;
import com.example.administrator.friendshape.ui.activity.component.TheDetailsInformationActivity;
import com.example.administrator.friendshape.ui.controller.ControllerPersonal;
import com.example.administrator.friendshape.ui.dialog.ProgressDialog;
import com.example.administrator.friendshape.ui.dialog.ShowDialog;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.utils.SystemUtil;
import com.example.administrator.friendshape.widget.CalendarBuilder;

import java.text.ParseException;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/10/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class MineFragment extends BaseFragment implements ControllerPersonal.PersonalContentNetWorkListener {

    @BindView(R.id.user_photo)
    ImageView user_photo;
    @BindView(R.id.user_neck_name)
    TextView user_neck_name;
    @BindView(R.id.user_content)
    TextView user_content;
    @BindView(R.id.the_signature)
    TextView the_signature;
    private ControllerPersonal controllerPersonal;
    private CalendarBuilder calendarBuilder;
    private ProgressDialog progressDialog;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.MINE_INFO_REFRESH:
                if (controllerPersonal != null)
                    controllerPersonal.personalContentNetWork();
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initClass() {
        calendarBuilder = new CalendarBuilder(Calendar.getInstance());
        controllerPersonal = new ControllerPersonal();
        progressDialog = ShowDialog.getInstance().showProgressStatus(getActivity());
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerPersonal;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        controllerPersonal.setPersonalContentNetWorkListener(this);
    }

    @SuppressLint("WrongConstant")
    @OnClick({R.id.my_dynamic_layout, R.id.service_phone_layout, R.id.about_me_layout, R.id.help_layout,
            R.id.user_title_layout, R.id.user_photo, R.id.modify_passwaor_layout})
    @Override
    protected void onClickAble(View view) {
        Intent intent = new Intent(getActivity(), GeneralActivity.class);
        switch (view.getId()) {
            case R.id.my_dynamic_layout:
                startActivity(new Intent(getActivity(), MyDynamicActivity.class));
                break;
            case R.id.service_phone_layout:
                intent.setFlags(2);
                startActivity(intent);
                break;
            case R.id.about_me_layout:
                intent.setFlags(1);
                startActivity(intent);
                break;
            case R.id.help_layout:
                intent.setFlags(0);
                startActivity(intent);
                break;
            case R.id.user_title_layout:
                Intent theDetailsInformationIntent = new Intent(getActivity(), TheDetailsInformationActivity.class);
                theDetailsInformationIntent.putExtra("userId", DataClass.USERID);
                startActivity(theDetailsInformationIntent);
                break;
            case R.id.user_photo:
                startActivity(new Intent(getActivity(), PersonalActivity.class));
                break;
            case R.id.modify_passwaor_layout:
                startActivity(new Intent(getActivity(), BindPhoneActivity.class).setFlags(1));
                break;
        }
    }

    @Override
    public void onPersonalContentNetWorkListener(PersonalContentNetBean.ResultBean result) {
        progressDialog.dismiss();
        int ageByBirthday = -1;
        try {
            ageByBirthday = calendarBuilder.getAgeByBirthday(result.getBrithday());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DataClass.USERPHOTO = result.getPhoto();
        DataClass.GENDER = result.getSex();
        DataClass.UNAME = result.getSecondname();
        DataClass.PHONE = result.getPhone();
        DataClass.AGE = String.valueOf(ageByBirthday);
        user_neck_name.setText(result.getSecondname());
        user_content.setText(new StringBuffer().append(result.getSex()).append("   ").append(ageByBirthday).append(getString(R.string.at_the_age)).append("          ").append(getString(R.string.id)).append(":   ").append(result.getUserid()).toString());
        Glide.with(this).load(SystemUtil.JudgeUrl(result.getPhoto())).error(R.drawable.banner_off).into(user_photo);
        if (result.getRemark().isEmpty()) {
            the_signature.setVisibility(View.GONE);
        } else {
            the_signature.setText(new StringBuffer().append(getString(R.string.the_signature)).append(" : ").append(result.getRemark()));
            the_signature.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPersonalUpLoadStatusNetWorkListener() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            progressDialog.show();
            controllerPersonal.personalContentNetWork();
        }
    }

}
