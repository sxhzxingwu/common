package com.android.weici.common.widget.help;

import android.support.annotation.NonNull;
import android.view.View;

import com.android.weici.common.R;

/**
 * Created by Mouse on 2017/9/28.
 */

public class VUIAlphaViewHelper {

    private View mTarget;

    /**
     * 设置是否要在 press 时改变透明度
     */
    private boolean mChangeAlphaWhenPress = true;

    /**
     * 设置是否要在 disabled 时改变透明度
     */
    private boolean mChangeAlphaWhenDisable = true;

    private float mNormalAlpha = 1f;
    private float mPressedAlpha = .5f;
    private float mDisabledAlpha = .5f;

    public VUIAlphaViewHelper(@NonNull View target) {
        mTarget = target;
        mPressedAlpha = VUIResHelper.getAttrFloatValue(target.getContext(), R.attr.vui_alpha_pressed);
        mDisabledAlpha = VUIResHelper.getAttrFloatValue(target.getContext(), R.attr.vui_alpha_disabled);
    }

    public void onPressedChanged(View target, boolean pressed) {
        if (mTarget.isEnabled()) {
            mTarget.setAlpha(mChangeAlphaWhenPress && pressed && target.isClickable()? mPressedAlpha : mNormalAlpha);
        } else {
            if (mChangeAlphaWhenDisable) {
                target.setAlpha(mDisabledAlpha);
            }
        }
    }

    public void onEnabledChanged(View target, boolean enabled) {
        float alphaForIsEnable;
        if (mChangeAlphaWhenDisable) {
            alphaForIsEnable = enabled ? mNormalAlpha : mDisabledAlpha;
        } else {
            alphaForIsEnable = mNormalAlpha;
        }
        target.setAlpha(alphaForIsEnable);
    }

    /**
     * 设置是否要在 press 时改变透明度
     *
     * @param changeAlphaWhenPress 是否要在 press 时改变透明度
     */
    public void setChangeAlphaWhenPress(boolean changeAlphaWhenPress) {
        mChangeAlphaWhenPress = changeAlphaWhenPress;
    }

    /**
     * 设置是否要在 disabled 时改变透明度
     *
     * @param changeAlphaWhenDisable 是否要在 disabled 时改变透明度
     */
    public void setChangeAlphaWhenDisable(boolean changeAlphaWhenDisable) {
        mChangeAlphaWhenDisable = changeAlphaWhenDisable;
        onEnabledChanged(mTarget, mTarget.isEnabled());
    }
}
