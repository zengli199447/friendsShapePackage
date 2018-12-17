package com.example.administrator.friendshape.ui.activity.component;

import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.BaseActivity;
import com.example.administrator.friendshape.base.BaseLifecycleObserver;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.ui.controller.ControllerReleaseNewDynamic;
import com.example.administrator.friendshape.ui.dialog.ProgressDialog;
import com.example.administrator.friendshape.ui.dialog.ShowDialog;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.widget.MultipartBuilder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/10/31.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ReleaseNewDynamicActivity extends BaseActivity implements MultipartBuilder.UpLoadFileListener,ControllerReleaseNewDynamic.UpLoadNetWorkListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_about_text)
    TextView title_about_text;
    @BindView(R.id.input_content)
    EditText input_content;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    private ControllerReleaseNewDynamic controllerReleaseNewDynamic;
    private MultipartBuilder multipartBuilder;
    private ProgressDialog progressDialog;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.DYNAMIC_SAVE:
                DataClass.DYNAMICCONTENT = input_content.getText().toString();
                finish();
                break;
            case EventCode.DYNAMIC_CANCLE:
                DataClass.DYNAMICCONTENT = "";
                DataClass.AlbumFileList.clear();
                finish();
                break;
            case EventCode.RELEASE_SUCCESSFUL:
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
        return R.layout.activity_release_new_dynamic;
    }

    @Override
    protected void initClass() {
        controllerReleaseNewDynamic = new ControllerReleaseNewDynamic(recycler_view);
        multipartBuilder = new MultipartBuilder(this, 1);
        progressDialog = ShowDialog.getInstance().showProgressStatus(this);
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerReleaseNewDynamic;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.release_new_dynamic));
        title_about_text.setText(getString(R.string.release));
        title_about_text.setTextColor(getResources().getColor(R.color.blue_bar));
        if (!DataClass.DYNAMICCONTENT.isEmpty())
            input_content.setText(DataClass.DYNAMICCONTENT);
    }

    @Override
    protected void initListener() {
        multipartBuilder.setUpLoadFileListener(this);
        controllerReleaseNewDynamic.setUpLoadNetWorkListener(this);
    }

    @OnClick({R.id.title_about_text, R.id.img_btn_black})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.title_about_text:
                if (input_content.getText().toString().trim().isEmpty()) {
                    toastUtil.showToast(getString(R.string.empty_content));
                } else {
                    progressDialog.show();
                    toastUtil.showToast(getString(R.string.commite));
                    if (DataClass.AlbumFileList.size() > 0) {
                        multipartBuilder.arrangementUpLoad();
                    } else {
                        controllerReleaseNewDynamic.personalChangeNetWork("", input_content.getText().toString());
                    }
                }
                break;
            case R.id.img_btn_black:
                finishController();
                break;
        }
    }

    //是否保存当前编辑内容
    private void finishController() {
        if (input_content.getText().toString().isEmpty() && DataClass.AlbumFileList.size() == 0) {
            finish();
        } else {
            controllerReleaseNewDynamic.ShowOrSelect(EventCode.DYNAMIC_OR_SAVE);
        }
    }

    @Override
    public void onUpLoadFileListener(String url) {
        LogUtil.e(TAG, "fileList : " + url);
        controllerReleaseNewDynamic.personalChangeNetWork(url, input_content.getText().toString());
    }

    @Override
    public void onUpLoadNetWorkListener() {
        progressDialog.dismiss();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finishController();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


}
