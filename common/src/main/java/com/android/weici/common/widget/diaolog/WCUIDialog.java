package com.android.weici.common.widget.diaolog;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.android.weici.common.R;

/**
 * Created by weici on 2017/10/23.
 */

public class WCUIDialog extends Dialog {

    private boolean isBackEnable = true;

    public WCUIDialog(Context context) {
        super(context, R.style.customDialog);
        init();
    }

    public WCUIDialog(Context context, int theme) {
        super(context, theme);
        init();
    }

    private void init(){
        //setSize(0.8f);
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
