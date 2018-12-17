package com.example.administrator.friendshape.ui.activity.component;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.BaseActivity;
import com.example.administrator.friendshape.model.bean.CityEntity;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.ui.adapter.CityRecommendedAdapter;
import com.example.administrator.friendshape.ui.adapter.CityScreeningAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.indexablerv.IndexableLayout;

/**
 * 作者：真理 Created by Administrator on 2018/10/29.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class CityScreeningActivity extends BaseActivity {

    @BindView(R.id.city_screening)
    IndexableLayout city_screening;
    @BindView(R.id.title_name)
    TextView title_name;
    private CityScreeningAdapter cityScreeningAdapter;
    private List<CityEntity> cityList = new ArrayList<>();
    private List<String> placeholderList = new ArrayList<>();
    private CityRecommendedAdapter cityRecommendedAdapter;

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_city_screening;
    }

    @Override
    protected void initClass() {
        city_screening.setLayoutManager(new LinearLayoutManager(this));
        cityScreeningAdapter = new CityScreeningAdapter(this);
        city_screening.setAdapter(cityScreeningAdapter);

    }

    @Override
    protected void initData() {
        List<String> contactStrings = Arrays.asList(getResources().getStringArray(R.array.provinces));
        List<String> mobileStrings = Arrays.asList(getResources().getStringArray(R.array.provinces));
        for (int i = 0; i < contactStrings.size(); i++) {
            CityEntity cityEntity = new CityEntity(contactStrings.get(i), mobileStrings.get(i));
            cityList.add(cityEntity);
        }
        placeholderList.add("");

    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.city));
        city_screening.setOverlayStyle_Center();
        cityScreeningAdapter.setDatas(cityList);
        // 全字母排序、排序规则设置为：每个字母都会进行比较排序;
        city_screening.setCompareMode(IndexableLayout.MODE_FAST);
        cityRecommendedAdapter = new CityRecommendedAdapter(this, "↑", null, placeholderList, null);
        city_screening.addHeaderAdapter(cityRecommendedAdapter);

    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.img_btn_black})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
        }
    }
}
