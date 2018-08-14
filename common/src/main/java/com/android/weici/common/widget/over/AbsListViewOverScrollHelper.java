package com.android.weici.common.widget.over;

import android.content.Context;
import android.view.View;
import android.widget.AbsListView;

public class AbsListViewOverScrollHelper extends OverScrollHelper {

    private AbsListView view;

    public AbsListViewOverScrollHelper(Context context, AbsListView view) {
        super(context);
        this.view = view;
        view.setFadingEdgeLength(0);
        view.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    @Override
    protected View getView() {
        return view;
    }

    @Override
    protected boolean isTopOverScrolled(int delY) {
        if (view.getCount() == 0) {
            return true;
        }
        int fvp = view.getFirstVisiblePosition();
        View childTop = view.getChildAt(0);
        return delY < 0 && fvp == 0 && childTop.getTop() >= view.getPaddingTop();
    }

    @Override
    protected boolean isBottomOverScrolled(int delY) {
        if (view.getCount() == 0) {
            return true;
        }
        int fvp = view.getFirstVisiblePosition();
        int lvp = view.getLastVisiblePosition();
        View childBottom = view.getChildAt(lvp - fvp);
        return delY > 0 && lvp == view.getCount() - 1 && childBottom.getBottom() <= view.getHeight();

    }

    @Override
    protected int getMaxOverScroll() {
        return getView().getMeasuredHeight() / 4 * 5;
    }
}
