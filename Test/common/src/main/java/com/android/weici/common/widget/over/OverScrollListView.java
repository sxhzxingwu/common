package com.android.weici.common.widget.over;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by changyou on 2015/11/4.
 */
public class OverScrollListView extends ListView {
    protected AbsListViewOverScrollHelper helper;
    public OverScrollListView(Context context) {
        super(context);
        init();
    }

    public OverScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OverScrollListView(Context context, AttributeSet attrs,
                              int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    public OverScrollListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        helper = new AbsListViewOverScrollHelper(getContext(), this);
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

    @SuppressLint("ClickableViewAccessibility")
	@Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean eve = helper.onTouchEvent(ev);
        if (eve && ev.getAction() == MotionEvent.ACTION_MOVE) {
            return false;
        }
        return super.onTouchEvent(ev);
    }

    public void setOnOverScrollListener(OnOverScrollListener listener) {
        helper.setOnOverScrollListener(listener);
    }

}
