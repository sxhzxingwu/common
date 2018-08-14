package com.android.weici.common.widget.tab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.android.weici.common.R;

public class TabViewGroup extends LinearLayout {

	private OnTabButtonClickListener onTabButtonClickListener;

	private int selectColor = Color.BLUE;
	private int defaultColor= Color.GRAY;

	public TabViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}

	private void init(AttributeSet attrs) {
		if(attrs==null){
			return;
		}
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.vui_tab);
		selectColor = a.getColor(R.styleable.vui_tab_text_color_select, Color.BLUE);
		defaultColor = a.getColor(R.styleable.vui_tab_text_color_default,Color.GRAY);
		a.recycle();
	}

	public void setData(String[] array) {
		setOrientation(LinearLayout.HORIZONTAL);
		setWeightSum(array.length);
		for (int i = 0; i < array.length; i++) {
			TabView tab = new TabView(getContext());
			tab.setColorDefault(defaultColor);
			tab.setColorSelected(selectColor);
			tab.setText(array[i]);
			tab.setTextSize(16);
			tab.setGravity(Gravity.CENTER);
			tab.setPosition(i);

			tab.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					TabView tab = (TabView) v;
					int position = tab.getPosition();
					TabViewGroup.this.select(position);
					if (null != onTabButtonClickListener) {
						onTabButtonClickListener.onTabButtonClickListener(position);
					}
				}
			});
			LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			lp.weight = 1.0f;
			this.addView(tab, lp);
		}
	}

	public void select(int position) {

		for (int i = 0; i < getChildCount(); i++) {
			TabView tab = (TabView) getChildAt(i);
			tab.select(i == position);
		}
	}

	public void setTitle(int position, String title) {
		for (int i = 0; i < getChildCount(); i++) {
			TabView tab = (TabView) getChildAt(i);
			if(i == position){
				tab.setText(title);
			}
		}
	}

	public interface OnTabButtonClickListener {

		public void onTabButtonClickListener(int i);

	}

	public OnTabButtonClickListener getOnTabButtonClickListener() {
		return onTabButtonClickListener;
	}

	public void setOnTabButtonClickListener(OnTabButtonClickListener onTabButtonClickListener) {
		this.onTabButtonClickListener = onTabButtonClickListener;
	}

}
