package com.android.weici.common.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.weici.common.R;

/**
 * Created by Mouse on 2017/11/3.
 */

public class InfoView extends FrameLayout implements View.OnClickListener {

    private View mLoadingView;
    private TextView mTextView;
    private LinearLayout mLoadingLayout;
    private LinearLayout mFailureLayout;

    private TextView mTextFailure;
    private WButton mBtnReplay;
    private OnClickListener onClickListener;

    public InfoView(@NonNull Context context) {
        super(context);
        initView();
    }

    public InfoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public void initView(){
        LayoutInflater.from(getContext()).inflate(R.layout.vui_info_view, this);
        mLoadingView = findViewById(R.id.loading);
        mTextView = findViewById(R.id.text);
        mLoadingLayout = findViewById(R.id.loadinglayout);
        mFailureLayout = findViewById(R.id.failure_info);

        mTextFailure = findViewById(R.id.failure_text);
        mBtnReplay = findViewById(R.id.reply);
        mBtnReplay.setOnClickListener(this);

    }

    public void showLoading(){
        switchLayout(true);
        RotateAnimation rotate  = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        LinearInterpolator lin = new LinearInterpolator();
        rotate.setInterpolator(lin);
        rotate.setDuration(2000);//设置动画持续周期
        rotate.setRepeatCount(-1);//设置重复次数
        rotate.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        rotate.setStartOffset(10);//执行前的等待时间
        mLoadingView.startAnimation(rotate);
    }

    public void showLoading(String text){
        showLoading();
        setLoadingText(text);
    }

    public void setLoadingText(String text){
        mTextView.setText(text);
    }

    private void switchLayout(boolean isLoading){
        if(isLoading){
            mLoadingLayout.setVisibility(View.VISIBLE);
            mFailureLayout.setVisibility(View.GONE);
        }else{
            mFailureLayout.setVisibility(View.VISIBLE);
            mLoadingLayout.setVisibility(View.GONE);
        }
    }

    public void showFailureInfo(){
        switchLayout(false);
    }

    public void showFailureInfo(String text){
        showFailureInfo();
        mTextFailure.setText(text);
    }

    @Override
    public void onClick(View view) {
        if(view==mBtnReplay&&null!=onClickListener){
            onClickListener.onClick(view);
        }
    }

    public void setOnReplyClickListener(OnClickListener listener){
        this.onClickListener = listener;
    }

}
