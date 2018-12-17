package com.example.administrator.friendshape.ui.activity.component;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.BaseActivity;
import com.example.administrator.friendshape.base.BaseLifecycleObserver;
import com.example.administrator.friendshape.model.bean.ImUserContentNetBean;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.ui.controller.ControllerFriendOperation;
import com.example.administrator.friendshape.ui.dialog.ShowDialog;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.utils.SystemUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/11/24.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class FriendsControllerActivity extends BaseActivity implements ControllerFriendOperation.NetWorkListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_about_text)
    TextView title_about_text;

    @BindView(R.id.layout_friends_confirm)
    LinearLayout layout_friends_confirm;
    @BindView(R.id.user_img)
    ImageView user_img;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.contorller_layout)
    LinearLayout contorller_layout;

    @BindView(R.id.layout_friends_to_apply_for)
    LinearLayout layout_friends_to_apply_for;
    @BindView(R.id.merchant_img)
    ImageView merchant_img;
    @BindView(R.id.user_name)
    TextView user_name;
    @BindView(R.id.action_feeling)
    EditText action_feeling;

    private ControllerFriendOperation controllerFriendOperation;
    private int flags;
    private String friendId;
    private String nickName;
    private String userPhoto;
    private ShowDialog instance;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.ADD_SUCCESSFULLY:
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
        return R.layout.activity_friends_controller;
    }

    @Override
    protected void initClass() {
        controllerFriendOperation = new ControllerFriendOperation(getIntent().getStringExtra("friendId"), getIntent().getFlags());
        instance = ShowDialog.getInstance();
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerFriendOperation;
    }

    @Override
    protected void initData() {
        flags = getIntent().getFlags();
        friendId = getIntent().getStringExtra("friendId");
        nickName = getIntent().getStringExtra("nickName");
        userPhoto = getIntent().getStringExtra("userPhoto");
    }

    @Override
    protected void initView() {
        switch (flags) {
            case 0:
                title_name.setText(getString(R.string.friend_requests));
                layout_friends_confirm.setVisibility(View.VISIBLE);
                break;
            case 1:
                title_name.setText(getString(R.string.to_apply_for_a_friend));
                layout_friends_to_apply_for.setVisibility(View.VISIBLE);
                title_about_text.setText(getString(R.string.send));
                title_about_text.setTextColor(getResources().getColor(R.color.blue_bar));
                user_name.setText(nickName);
                Glide.with(this).load(userPhoto).error(R.drawable.banner_off).into(merchant_img);
                break;
        }
    }

    @Override
    protected void initListener() {
        controllerFriendOperation.setNetWorkListener(this);
    }

    @OnClick({R.id.img_btn_black, R.id.btn_cancle, R.id.btn_commit, R.id.title_about_text, R.id.user_img, R.id.merchant_img})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.btn_cancle:
                controllerFriendOperation.FriendRequests(3);
                break;
            case R.id.btn_commit:
                controllerFriendOperation.FriendRequests(2);
                break;
            case R.id.title_about_text:
                controllerFriendOperation.ToApplyForAFriend(action_feeling.getText().toString());
                break;
            case R.id.user_img:
                Intent theDetailsInformationIntent = new Intent(this, TheDetailsInformationActivity.class);
                theDetailsInformationIntent.putExtra("userId", friendId);
                startActivity(theDetailsInformationIntent);
                break;
            case R.id.merchant_img:

                break;
        }
    }

    @Override
    public void onUserContentNetWorkListener(ImUserContentNetBean.ResultBean result) {
        switch (flags) {
            case 0:
                Glide.with(this).load(SystemUtil.JudgeUrl(result.getPhoto())).error(R.drawable.banner_off).into(user_img);
                String thePrefix = null;
                switch (result.getIffriend()) {
                    case 1:
                        contorller_layout.setVisibility(View.VISIBLE);
                        thePrefix = getString(R.string.add_friend_about);
                        break;
                    case 2:
                        contorller_layout.setVisibility(View.GONE);
                        thePrefix = getString(R.string.already_you_friends);
                        break;
                    case 3:
                        contorller_layout.setVisibility(View.GONE);
                        thePrefix = getString(R.string.already_refused_to_you);
                        break;
                }
                SystemUtil.textMagicToolTypeFace(this, content, result.getSecondname(), thePrefix,
                        R.dimen.dp14, R.dimen.album_sp_14, R.color.blue_bar, R.color.black_overlay, "  ");
                break;
            case 1:

                break;
        }
    }

    @Override
    public void onConfirmTheOperationNetWorkListener(String s) {
        contorller_layout.setVisibility(View.GONE);
        content.setText(s);
        instance.showHelpfulHintsDialog(this, getString(R.string.add_successfully), EventCode.ADD_SUCCESSFULLY);
    }

    @Override
    public void onConfirmToApplyForNetWorkListener() {
        instance.showHelpfulHintsDialog(this, getString(R.string.commite_successfully), EventCode.ADD_SUCCESSFULLY);
    }

}
