package com.android.weici.common.widget.tab;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;

public class TabView extends TextView{

	private boolean isSelected = false;
	private int position;

	int colorSelected = Color.BLUE;
	int colorDefault = Color.GRAY;

	public TabView(Context context) {
		super(context);
	}
	
	public TabView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	

	public void select(boolean isSelected){
		setTextColor(isSelected?colorSelected:colorDefault);
	}

	public void setPosition(int position){
		this.position = position;
	}
	
	public int getPosition(){
		return position;
	}

	public void setColorSelected(int color){
		this.colorSelected = color;
	}

	public void setColorDefault(int colorDefault) {
		this.colorDefault = colorDefault;
	}
}
