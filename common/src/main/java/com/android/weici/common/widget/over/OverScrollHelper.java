package com.android.weici.common.widget.over;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.OvershootInterpolator;
import android.widget.Scroller;

/**
 * Created by changyou on 2015/11/10.
 */
public abstract class OverScrollHelper {
    private boolean isOverScrolled;
    private Scroller mScroller;

    private boolean isMove;
    private float oldY;
    private float checkMoveY;
    private int dealy;
    private ViewConfiguration configuration;
    private OnOverScrollListener listener;
    private boolean overScrollEnable = true;
    private boolean topOverScrollEnable = true;
    private boolean bottomOverScrollEnable = true;
    private int maxOverScroll;

    public OverScrollHelper(Context context) {
        mScroller = new Scroller(context, new OvershootInterpolator(0.75f));
        configuration = ViewConfiguration.get(context);
    }

    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            getView().scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            getView().postInvalidate();
        } else {
            isOverScrolled = false;
        }
    }

    public void setOverScrollEnable(boolean enable) {
        overScrollEnable = enable;
    }

    public void setTopOverScrollEnable(boolean enable) {
        topOverScrollEnable = enable;
    }

    public void setBottomOverScrollEnable(boolean enable) {
        bottomOverScrollEnable = enable;
    }

    protected void mSmoothScrollTo(int fx, int fy) {
        // int dx = fx - mScroller.getFinalX();
        int dy = fy - mScroller.getFinalY();
        mSmoothScrollBy(0, dy);
    }

    protected void mSmoothScrollBy(int dx, int dy) {
        mScroller.startScroll(0, mScroller.getFinalY(), 0, dy);
        if (isOverScrolled) {
            getView().invalidate();
        }
    }

    protected abstract View getView();

    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_POINTER_DOWN:
                isMove = false;
                break;
            case MotionEvent.ACTION_DOWN:
                break;

            case MotionEvent.ACTION_MOVE:
                if (!isMove) {
                    oldY = ev.getY();
                    isMove = checkIsMove(ev);
                } else {
                    checkMoveY = 0;
                    dealy += (int) (oldY - ev.getY());
                    oldY = ev.getY();
                    isOverScrolled = checkOverScrollMode(dealy);
                    if (overScrollEnable) {
                        overScrolled(dealy);
                    }

                }

                break;

            case MotionEvent.ACTION_POINTER_UP:
                isMove = false;
                break;
            case MotionEvent.ACTION_CANCEL:

            case MotionEvent.ACTION_UP:
                isMove = false;
                dealy = 0;
                mSmoothScrollTo(0, 0);
                break;
        }
        return isOverScrolled;
    }

    private void initMaxOverScroll() {
        maxOverScroll = getMaxOverScroll();
    }

    protected abstract int getMaxOverScroll();

    private boolean checkIsMove(MotionEvent ev) {
        if (checkMoveY == 0) {
            checkMoveY = ev.getY();
            return false;
        }
        return Math.abs(checkMoveY - ev.getY()) >= configuration.getScaledTouchSlop();
    }

    private boolean checkOverScrollMode(int delY) {
        if (topOverScrollEnable && isTopOverScrolled(delY)) {
            if (listener != null) {
                listener.onTopOverScoll();
            }
            return true;
        }

        if (bottomOverScrollEnable && isBottomOverScrolled(delY)) {
            if (listener != null) {
                listener.onBottomOverScroll();
            }
            return true;
        }
        return false;
    }

    protected abstract boolean isTopOverScrolled(int delY);

    protected abstract boolean isBottomOverScrolled(int delY);

    private void overScrolled(int delY) {
        if (maxOverScroll == 0) {
            initMaxOverScroll();
        }
        if (Math.abs(delY) > maxOverScroll) {
            delY = delY < 0 ? -maxOverScroll : maxOverScroll;
        }
        int distance = delY / 2;
        mSmoothScrollTo(0, distance);
    }

    public void setOnOverScrollListener(OnOverScrollListener listener) {
        this.listener = listener;
    }

}
