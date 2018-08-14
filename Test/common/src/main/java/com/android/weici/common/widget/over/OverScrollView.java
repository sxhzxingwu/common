package com.android.weici.common.widget.over;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by changyou on 2016/3/1.
 */
public class OverScrollView extends ScrollView{

    private OverScrollHelper helper;
    public OverScrollView(Context context) {
        super(context);
        init();
    }

    public OverScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OverScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public OverScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        helper = new ScrollViewOverScrollHelper(getContext(), this);
    }

    @Override
    public void computeScroll() {
        helper.computeScroll();
        super.computeScroll();
    }

    public void setOverScrollEnable(boolean enable){
        helper.setOverScrollEnable(enable);
    }

    public void setTopOverScrollEnable(boolean enable){
        helper.setTopOverScrollEnable(enable);
    }

    public void setBottomOverScrollEnable(boolean enable){
        helper.setBottomOverScrollEnable(enable);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        helper.onTouchEvent(ev);
        return super.onTouchEvent(ev);
    }

    public void setOnOverScrollListener(OnOverScrollListener listener) {
        helper.setOnOverScrollListener(listener);
    }

}
