package com.android.weici.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.android.weici.common.R;

/**
 * Created by weici on 2018/4/24.
 */

public class ExpandTextView extends WLinearLayout{
    private TextView mTextView;
    private ExpandIconView mIcon;
    public ExpandTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        View view = inflate(getContext(), R.layout.view_expand_text_item, this);
        mTextView = view.findViewById(R.id.text);

        TypedArray array = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.ExpandIconView, 0, 0);
        String text = array.getString(R.styleable.ExpandIconView_eiv_text);
        if(!TextUtils.isEmpty(text)) mTextView.setText(text);
        float textSize = array.getDimension(R.styleable.ExpandIconView_eiv_text_size, 0);
        if(textSize != 0) mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

        array.recycle();
        mIcon = view.findViewById(R.id.left_arrow);
        mIcon.setAttrs(attrs);
    }

    public void setText(String text){
        mTextView.setText(text);
    }

    public void switchState(){
        mIcon.switchState();
    }
}
