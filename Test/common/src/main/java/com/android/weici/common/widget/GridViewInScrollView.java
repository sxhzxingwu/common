package com.android.weici.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * GridView嵌套在滚动页面（ScrollView/ListView....)
 * Created by weici on 2017/11/1.
 */

public class GridViewInScrollView extends GridView {
    private boolean canScroll = false;

    public GridViewInScrollView(Context context) {
        super(context);
    }

    public GridViewInScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridViewInScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setCanScroll(boolean can){
        canScroll = can;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(canScroll){
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
