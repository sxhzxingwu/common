package com.android.weici.common.widget.over;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

public class OverScrollGridView extends GridView {
    private AbsListViewOverScrollHelper helper;

    public OverScrollGridView(Context context) {
        super(context);
        init();
    }

    public OverScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OverScrollGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setScrollbarFadingEnabled(true);
        helper = new AbsListViewOverScrollHelper(getContext(), this);
    }

    @Override
    public void computeScroll() {
        helper.computeScroll();
        super.computeScroll();
    }

    public void setOverScrollEnable(boolean enable) {
        helper.setOverScrollEnable(enable);
    }

    public void setTopOverScrollEnable(boolean enable) {
        helper.setTopOverScrollEnable(enable);
    }

    public void setBottomOverScrollEnable(boolean enable) {
        helper.setBottomOverScrollEnable(enable);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean eve = helper.onTouchEvent(ev);
        if (eve) {
            return false;
        }
        return super.onTouchEvent(ev);
    }

    public void setOnOverScrollListener(OnOverScrollListener listener) {
        helper.setOnOverScrollListener(listener);
    }

}
