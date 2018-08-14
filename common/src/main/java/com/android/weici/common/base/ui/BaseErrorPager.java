package com.android.weici.common.base.ui;

import android.app.Activity;
import android.widget.TextView;

public interface BaseErrorPager {
    void showText(TextView textView, int code);

    void doAction(Activity context, int code);
}
