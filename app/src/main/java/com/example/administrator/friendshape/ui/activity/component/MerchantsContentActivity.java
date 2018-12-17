package com.example.administrator.friendshape.ui.activity.component;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.BaseActivity;
import com.example.administrator.friendshape.base.BaseLifecycleObserver;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.MerchantsContentsNetWorkBean;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.ui.controller.ControllerMerchants;
import com.example.administrator.friendshape.ui.dialog.ShowDialog;
import com.example.administrator.friendshape.ui.view.ImageSlideshow;
import com.example.administrator.friendshape.ui.view.ShinyView;
import com.example.administrator.friendshape.utils.SystemUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/11/6.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class MerchantsContentActivity extends BaseActivity implements ControllerMerchants.MerchantsNetWorksListener, NestedScrollView.OnScrollChangeListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.merchants_img)
    ImageView merchants_img;
    @BindView(R.id.merchants_name)
    TextView merchants_name;
    @BindView(R.id.shiny_view)
    ShinyView shiny_view;
    @BindView(R.id.consumption)
    TextView consumption;
    @BindView(R.id.merchants_location)
    TextView merchants_location;
    @BindView(R.id.merchants_business_hours)
    TextView merchants_business_hours;

    @BindView(R.id.recycler_view_group_ing)
    RecyclerView recycler_view_group_ing;

    @BindView(R.id.service_content)
    TextView service_content;

    @BindView(R.id.recycler_view_evaluation)
    RecyclerView recycler_view_evaluation;

    @BindView(R.id.scrollView)
    NestedScrollView scrollView;

    @BindView(R.id.banner_layout)
    ImageSlideshow banner_layout;

    @BindView(R.id.group_line)
    View group_line;
    @BindView(R.id.service_line)
    View service_line;
    @BindView(R.id.comments_line)
    View comments_line;

    private ControllerMerchants controllerMerchants;
    private ShowDialog instance;
    private MerchantsContentsNetWorkBean.ResultBean.ShopBean shop;
    private String merchantsId;

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_merchants_content;
    }

    @Override
    protected void initClass() {
        merchantsId = getIntent().getStringExtra("MerchantsId");
        controllerMerchants = new ControllerMerchants(merchantsId, recycler_view_group_ing, recycler_view_evaluation, banner_layout);
        instance = ShowDialog.getInstance();
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerMerchants;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.merchants_content));
    }

    @Override
    protected void initListener() {
        controllerMerchants.setMerchantsNetWorksListener(this);
        scrollView.setOnScrollChangeListener(this);
    }

    @SuppressLint("WrongConstant")
    @OnClick({R.id.img_btn_black, R.id.to_view_more, R.id.phone_img, R.id.initiate_group, R.id.to_view_more_comments})
    @Override
    protected void onClickAble(View view) {
        Intent merchantsGroupsIntent = new Intent(this, MerchantsGroupsActivity.class);
        merchantsGroupsIntent.putExtra("merchantsId", merchantsId);
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.to_view_more:
                merchantsGroupsIntent.setFlags(0);
                startActivity(merchantsGroupsIntent);
                break;
            case R.id.to_view_more_comments:
                merchantsGroupsIntent.setFlags(1);
                startActivity(merchantsGroupsIntent);
                break;
            case R.id.phone_img:
                instance.showConfirmOrNoDialog(this, shop.getPhone(), EventCode.CALL_PHONE, EventCode.ONSTART, EventCode.ONSTART);
                break;
            case R.id.initiate_group:
                if (shop != null) {
                    Intent intent = new Intent(this, ATeamActivity.class);
                    intent.putExtra("groupId", "");
                    intent.putExtra("merchantsId", shop.getShopid());
                    intent.putExtra("merchantsName", shop.getShopname());
                    intent.putExtra("merchantsImg", shop.getPhoto());
                    intent.setFlags(0);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void onMerchantsNetWorksListener(MerchantsContentsNetWorkBean.ResultBean merchantsContent) {
        shop = merchantsContent.getShop();
        Glide.with(this).load(new StringBuffer().append(DataClass.URL).append(shop.getImg()).toString()).error(R.drawable.banner_off).into(merchants_img);
        merchants_name.setText(shop.getShopname());
        shiny_view.setStarMark(Float.valueOf(shop.getScore()));
        consumption.setText(new StringBuffer().append(getString(R.string.per_capita)).append("￥").append(shop.getMoney_avg()).toString());

        merchants_location.setText(shop.getAddress());
        String distanceValue = shop.getDistance();
        SystemUtil.textMagicTool(this, merchants_location, shop.getAddress(), new StringBuffer().append(getString(R.string.distance_you)).append(SystemUtil.JudgeFormatDistance(distanceValue)).toString(),
                R.dimen.dp14, R.dimen.dp12, R.color.black, R.color.black_overlay, "\n");

        service_content.setText(new StringBuffer().append(getString(R.string.service_project)).append(shop.getRemark()));
        merchants_business_hours.setText(shop.getOpeningtime());

        if (merchantsContent.getGroup().size() == 0)
            group_line.setVisibility(View.GONE);
        if (merchantsContent.getComment().size() == 0)
            comments_line.setVisibility(View.GONE);
        if (merchantsContent.getShop().getRemark().isEmpty()) {
            service_line.setVisibility(View.GONE);
            service_content.setVisibility(View.GONE);
        }

    }


    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

    }


}
