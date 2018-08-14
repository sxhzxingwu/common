package com.android.weici.common.manager;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Mouse on 2017/9/19.
 */

public class ToastManager {

    private Toast mToast;
    private static ToastManager manager;

    public static ToastManager getInstance() {
        if (null == manager) {
            manager = new ToastManager();
        }
        return manager;
    }

    public Toast getToast(Context context) {
        if (null != mToast) {
            mToast.cancel();
        }
        mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        return mToast;
    }

    public void showToast(Context context, String str) {
        try {
            mToast = getToast(context);
            mToast.setText(str);
            mToast.show();
        }catch (Exception ignored){
        }catch (Error ignored){}
    }

    public void cancelToast() {
        if (null != mToast) {
            mToast.cancel();
        }
        mToast = null;
    }

}
