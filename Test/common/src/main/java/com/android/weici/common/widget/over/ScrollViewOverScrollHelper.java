package com.android.weici.common.widget.over;

import android.content.Context;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by changyou on 2016/3/1.
 */
public class ScrollViewOverScrollHelper extends OverScrollHelper {

    private ScrollView scrollView;

    public ScrollViewOverScrollHelper(Context context, ScrollView view) {
        super(context);
        this.scrollView = view;
        scrollView.setFadingEdgeLength(0);
        scrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    @Override
    protected View getView() {
        return scrollView.getChildAt(0);
    }

    @Override
    protected boolean isTopOverScrolled(int delY) {
        View view = scrollView.getChildAt(0);
        return delY < 0 && (view.getTop() - view.getPaddingTop() >= 0) && scrollView.getScrollY() == 0;
    }

    @Override
    protected boolean isBottomOverScrolled(int delY) {
        View view = scrollView.getChildAt(0);
        return scrollView.getBottom() >= view.getBottom() + view.getPaddingBottom() - scrollView.getScrollY() && delY > 0;
    }

    @Override
    protected int getMaxOverScroll() {
        return scrollView.getMeasuredHeight() / 4 * 5;
    }
}
