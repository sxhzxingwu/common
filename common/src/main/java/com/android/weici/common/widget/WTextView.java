package com.android.weici.common.widget;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;

import com.android.weici.common.R;
import com.android.weici.common.widget.help.WViewHelper;

/***
 * 设置圆角
 * @author wxw
 */
public class WTextView extends android.support.v7.widget.AppCompatTextView {

	public WViewHelper WViewHelper;
	public WTextView(Context context) {
		super(context);
		init(context, null);
	}

	public WTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public WTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs){
		WViewHelper = new WViewHelper();
		WViewHelper.init(context, attrs, this);
	}
}
