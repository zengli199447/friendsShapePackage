package com.example.administrator.friendshape.widget;

import android.view.Gravity;

import com.example.administrator.friendshape.R;

/**
 * 作者：真理 Created by Administrator on 2018/11/14.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class PickerViewAnimateBuilder {
    private static final int INVALID = -1;
    /**
     * Get default animation resource when not defined by the user
     *
     * @param gravity       the gravity of the dialog
     * @param isInAnimation determine if is in or out animation. true when is is
     * @return the id of the animation resource
     */
    public static int getAnimationResource(int gravity, boolean isInAnimation) {
        switch (gravity) {
            case Gravity.BOTTOM:
                return isInAnimation ? R.anim.slide_in_bottom : R.anim.slide_out_bottom;
        }
        return INVALID;
    }

}
