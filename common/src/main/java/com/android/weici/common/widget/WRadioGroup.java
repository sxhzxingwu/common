package com.android.weici.common.widget;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RadioGroup;

import com.android.weici.common.widget.help.WViewHelper;


/***
 * 设置圆角
 * @author wxw
 */
public class WRadioGroup extends RadioGroup {

	public WViewHelper WViewHelper;
	public WRadioGroup(Context context) {
		super(context);
		init(context, null);
	}

	public WRadioGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs){
		WViewHelper = new WViewHelper();
		WViewHelper.init(context, attrs, this);
	}
}
