package com.android.weici.common.widget.diaolog;


import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.android.weici.common.R;


public class BaseDialog extends Dialog{

	private boolean isBackEnable = true;
	
	public BaseDialog(Context context) {
		super(context, R.style.customDialog);
	}

	public BaseDialog(Context context, int theme) {
		super(context, theme);
	}

	public boolean isBackEnable() {
		return isBackEnable;
	}

	public void setBackEnable(boolean isBackEnable) {
		this.isBackEnable = isBackEnable;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	
		if(isBackEnable){
			return super.onKeyDown(keyCode, event);
		}else{
			return true;
		}
	}

	protected void setSize(double widthScale) {
		WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		int windowWidth = outMetrics.widthPixels;

		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.width = (int) (windowWidth * widthScale); // 宽度设置为屏幕的一定比例大小
		//params.height = (int) (outMetrics.heightPixels * widthScale);
		getWindow().setAttributes(params);
	}
}
