package com.android.weici.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.weici.common.R;

public class UserItemView extends RelativeLayout {
    public UserItemView(Context context) {
        super(context);
        init(null);
    }

    public UserItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.user_setting_item, this);
        View mIconImg = findViewById(R.id.icon);
        TextView mTextView = findViewById(R.id.text);
        View mDividerView = findViewById(R.id.user_divider);
        mTextView.setClickable(false);
        this.setBackgroundResource(R.drawable.ripple_item_selector);
        if (null != attrs) {
            TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.UserSettingItem);
            Drawable drawable = attributes.getDrawable(R.styleable.UserSettingItem_setting_icon);
            if (null != drawable) {
                mIconImg.setBackgroundDrawable(drawable);
                float iconWidth = attributes.getDimension(R.styleable.UserSettingItem_icon_width, 0);
                float iconHeight = attributes.getDimension(R.styleable.UserSettingItem_icon_height, 0);
                LayoutParams lp = (LayoutParams) mIconImg.getLayoutParams();
                lp.width = (int) iconWidth;
                lp.height = (int) iconHeight;
            }else{
                mIconImg.setVisibility(GONE);
            }
            String text = attributes.getString(R.styleable.UserSettingItem_setting_text);
            if (!TextUtils.isEmpty(text)) {
                mTextView.setText(text);
            }
            boolean isShowDivider = attributes.getBoolean(R.styleable.UserSettingItem_setting_divider, false);
            mDividerView.setVisibility(isShowDivider ? View.VISIBLE : View.GONE);

            attributes.recycle();
        }
    }
}
