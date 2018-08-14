package com.android.weici.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;

import com.android.weici.common.R;
import com.android.weici.common.Tools;
import com.android.weici.common.widget.help.VUIAlphaViewHelper;

/**
 * Created by Mouse on 2017/10/16.
 */

public class VUIRoundAlphaButton extends VUIAlphaButton {



    public VUIRoundAlphaButton(Context context) {
        super(context);
    }

    public VUIRoundAlphaButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    private void init(AttributeSet attrs) {
        if (null == attrs)
            return;
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.VUIRoundAlphaButton);
        int colorRes = array.getColor(R.styleable.VUIRoundAlphaButton_bg_color, Color.WHITE);
        float radio = array.getDimension(R.styleable.VUIRoundAlphaButton_radio, 0);
        setBackgroundColor(colorRes,radio);
        array.recycle();
    }

    /**
     * @param color
     * @param radio dp 值
     */
    public void setBackgroundColor(@ColorInt int color, float radio) {
        int radiopx = Tools.dip2px(getContext(), radio);
        float[] outer = new float[]{radiopx, radiopx, radiopx, radiopx, radiopx, radiopx, radiopx, radiopx};
        RoundRectShape shape = new RoundRectShape(outer,null,null);
        ShapeDrawable drawable = new ShapeDrawable(shape);
        drawable.getPaint().setColor(color);
        drawable.getPaint().setStyle(Paint.Style.FILL);
        setBackgroundDrawable(drawable);
    }

    @Override
    public void setBackgroundColor(@ColorInt int color) {
        super.setBackgroundColor(color);
    }

}
