package com.android.weici.common.widget.viewpaper;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class VUIViewPager extends ViewPager {
	private boolean isScrollable = true;

	public VUIViewPager(Context context, AttributeSet attrs){
		super(context, attrs);
	}
	
	public VUIViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (!isScrollable) {
			return false;
		} else {
			return super.onTouchEvent(ev);
		}

	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (!isScrollable) {
			return false;
		} else {
			return super.onInterceptTouchEvent(ev);
		}
	}

	public void setScrollable(boolean scrollable) {
		isScrollable = scrollable;
	}
}
