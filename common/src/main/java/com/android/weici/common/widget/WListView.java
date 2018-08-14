package com.android.weici.common.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.weici.common.widget.help.WViewHelper;


/***
 * 设置圆角
 * @author wxw
 */
public class WListView extends ListView {

	public WViewHelper WViewHelper;
	public WListView(Context context) {
		super(context);
		init(context, null);
	}

	public WListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs){
		WViewHelper = new WViewHelper();
		WViewHelper.init(context, attrs, this);
	}
}
