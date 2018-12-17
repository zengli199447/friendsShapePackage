package com.example.administrator.friendshape.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 作者：真理 Created by Administrator on 2018/10/29.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class IsometricGridView extends GridView {

    public IsometricGridView(Context context) {
        super(context);
    }

    public IsometricGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IsometricGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);

    }

}
