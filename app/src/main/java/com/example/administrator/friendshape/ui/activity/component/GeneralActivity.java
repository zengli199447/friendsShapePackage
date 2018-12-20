package com.example.administrator.friendshape.ui.activity.component;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.BaseActivity;
import com.example.administrator.friendshape.base.BaseLifecycleObserver;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.OtherContentNetBean;
import com.example.administrator.friendshape.model.bean.RefundNetBean;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.ui.controller.ControllerGeneral;
import com.example.administrator.friendshape.ui.view.ShinyView;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.utils.SystemUtil;
import com.example.administrator.friendshape.widget.WebViewBuilder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/10/31.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class GeneralActivity extends BaseActivity implements ShinyView.OnStarChangeListener, ControllerGeneral.GeneralNetWorkBeanListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_about_text)
    TextView title_about_text;

    @BindView(R.id.general_help)
    LinearLayout general_help;

    @BindView(R.id.general_about_me)
    RelativeLayout general_about_me;
    @BindView(R.id.to_score_layout)
    RelativeLayout to_score_layout;
    @BindView(R.id.to_up_version_layout)
    RelativeLayout to_up_version_layout;
    @BindView(R.id.current_version)
    TextView current_version;

    @BindView(R.id.general_service)
    LinearLayout general_service;
    @BindView(R.id.service_recycler_view)
    RecyclerView service_recycler_view;

    @BindView(R.id.layout_submit_evaluation)
    LinearLayout layout_submit_evaluation;
    @BindView(R.id.merchant_img)
    ImageView merchant_img;
    @BindView(R.id.merchant_name)
    TextView merchant_name;
    @BindView(R.id.shiny_view)
    ShinyView shiny_view;
    @BindView(R.id.action_feeling)
    EditText action_feeling;

    @BindView(R.id.layout_general_refund)
    LinearLayout layout_general_refund;
    @BindView(R.id.order_status)
    TextView order_status;
    @BindView(R.id.cancel_order_time)
    TextView cancel_order_time;
    @BindView(R.id.refund_pay_number)
    TextView refund_pay_number;
    @BindView(R.id.contact_customer_service)
    TextView contact_customer_service;

    @BindView(R.id.web_view)
    WebView web_view;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    private int flags;
    private ControllerGeneral controllerGeneral;
    float evaluationMark;
    private WebViewBuilder webViewBuilder;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.EVALUATION_SUCCESSFUL:
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
        return R.layout.activity_general;
    }

    @Override
    protected void initClass() {
        flags = getIntent().getFlags();
        webViewBuilder = new WebViewBuilder(web_view, progressbar);
        controllerGeneral = new ControllerGeneral(service_recycler_view, flags, getIntent().getStringExtra("orderId"), getIntent().getStringExtra("textInfoId"));
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerGeneral;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        switch (flags) {
            case 0:
                title_name.setText(getText(R.string.help));
                general_help.setVisibility(View.VISIBLE);
                webViewBuilder.initWebView();
                break;
            case 1:
                title_name.setText(getText(R.string.about_me));
                general_about_me.setVisibility(View.VISIBLE);
                current_version.setText(new StringBuffer().append("v").append(SystemUtil.getAppVersionName(this,false)).toString());
                break;
            case 2:
                title_name.setText(getText(R.string.service_phone));
                general_service.setVisibility(View.VISIBLE);
                break;
            case 3:
                title_name.setText(getText(R.string.order_evaluation));
                layout_submit_evaluation.setVisibility(View.VISIBLE);
                title_about_text.setText(getString(R.string.commite));
                title_about_text.setTextColor(getResources().getColor(R.color.blue_bar));
                merchant_name.setText(getIntent().getStringExtra("merchantsShopname"));
                Glide.with(this).load(new StringBuffer().append(DataClass.URL).append(getIntent().getStringExtra("merchantsPhoto")).toString()).error(R.drawable.banner_off).into(merchant_img);
                break;
            case 4:
                title_name.setText(getText(R.string.refund_content));
                layout_general_refund.setVisibility(View.VISIBLE);
                break;
            case 5:
                title_name.setText(getText(R.string.content_of_the_announcement));
                general_help.setVisibility(View.VISIBLE);
                webViewBuilder.initWebView();
                break;
            case 6:
                title_name.setText(getText(R.string.recommended));
                general_help.setVisibility(View.VISIBLE);
                webViewBuilder.initWebView();
                break;
        }
    }

    @Override
    protected void initListener() {
        shiny_view.setOnStarChangeListener(this);
        controllerGeneral.setGeneralNetWorkBeanListener(this);
    }

    @OnClick({R.id.img_btn_black, R.id.title_about_text, R.id.contact_customer_service})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.title_about_text:
                switch (flags) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        controllerGeneral.evaluationNetWork(evaluationMark, action_feeling.getText().toString());
                        break;
                }

                break;
            case R.id.contact_customer_service:
                toastUtil.showToast("联系客服");
                controllerGeneral.onCallClick(0);
                break;

        }
    }

    @Override
    public void onStarChange(float mark) {
        this.evaluationMark = mark;
    }

    @Override
    public void onGeneralNetWorkBeanListener(Object netWorkBean) {
        switch (flags) {
            case 0:
                if (netWorkBean instanceof OtherContentNetBean.ResultBean) {
                    OtherContentNetBean.ResultBean otherContent = (OtherContentNetBean.ResultBean) netWorkBean;
                    webViewBuilder.loadWebView(otherContent.getContent(), false);
                }
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                if (netWorkBean instanceof RefundNetBean.ResultBean) {
                    RefundNetBean.ResultBean resultBean = (RefundNetBean.ResultBean) netWorkBean;
                    SystemUtil.textMagicTool(this, order_status, getString(R.string.waiting_to_be_processed),
                            getString(R.string.waiting_to_be_processed_about), R.dimen.dp14, R.dimen.dp12, R.color.white, R.color.white, "\n");
                    cancel_order_time.setText(new StringBuffer().append(getString(R.string.cancel_order_time)).append("   ").append(resultBean.getCancelinfo().getTime_cancel()).toString());
                    refund_pay_number.setText(new StringBuffer().append(getString(R.string.refund_pay_number)).append("   ").append(resultBean.getCancelinfo().getMoneypay_online()).append(getString(R.string.the_yuan)).toString());
                }
                break;
            case 5:
            case 6:
                if (netWorkBean instanceof OtherContentNetBean.ResultBean) {
                    OtherContentNetBean.ResultBean otherContent = (OtherContentNetBean.ResultBean) netWorkBean;
                    webViewBuilder.loadWebView(otherContent.getContent(), false);
                }
                break;
        }
    }


}
