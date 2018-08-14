package com.android.weici.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.weici.common.R;
import com.android.weici.common.widget.help.WViewHelper;

public class WLoaddingButton extends RelativeLayout{
    private View mTextView;
    private View mProgressBar;
    Animation animation;

    public WViewHelper mWViewHelper;
    public WLoaddingButton(Context context) {
        super(context);
        init(context, null);
    }

    public WLoaddingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public WLoaddingButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        mWViewHelper = new WViewHelper();
        mWViewHelper.init(context, attrs, this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        init();
    }

    private void init(){
        int count = getChildCount();
        if(count<2){
            return;
        }
        mTextView = getChildAt(0);
        mProgressBar = getChildAt(1);
        mProgressBar.setVisibility(GONE);
        mProgressBar.setBackgroundResource(R.drawable.bga_refresh_loading01);
        animation = AnimationUtils
                .loadAnimation(getContext(), R.anim.anim_loading_style1);
    }

    private void loadding(){
        if(mTextView == null || mProgressBar == null) return;
        mTextView.setVisibility(GONE);
        mProgressBar.setVisibility(VISIBLE);

        mProgressBar.startAnimation(animation);
    }

    private void finishLoadding(String text){
        if(mTextView == null || mProgressBar == null) return;
        mTextView.setVisibility(VISIBLE);
        mProgressBar.clearAnimation();
        mProgressBar.setVisibility(GONE);
        ((TextView)mTextView).setText(text);
    }

    public void setState(int state){
        setClickable(state == -1);
        if(state == -1){
            finishLoadding("接受");
        }else if(state == 1){
            loadding();
        }else if( state == 0){
            finishLoadding("已接受");
        }
    }

    @Override
    public void setClickable(boolean clickable) {
        super.setClickable(clickable);
        if(mWViewHelper == null || mTextView == null) return;
        if(clickable){
            mWViewHelper.setBackgroundColor(0xff31af00);
            ((TextView)mTextView).setTextColor(0xffffffff);
        }else{
            mWViewHelper.setBackgroundColor(0xfff4f4f4);
            ((TextView)mTextView).setTextColor(0xff555555);
        }
    }
}
