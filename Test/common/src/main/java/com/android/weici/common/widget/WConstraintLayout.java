package com.android.weici.common.widget;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;

import com.android.weici.common.widget.help.WViewHelper;

public class WConstraintLayout extends ConstraintLayout {
    public com.android.weici.common.widget.help.WViewHelper WViewHelper;

    public WConstraintLayout(Context context) {
        super(context);
        init(context, null);
    }

    public WConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public WConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        WViewHelper = new WViewHelper();
        WViewHelper.init(context, attrs, this);
    }
}
