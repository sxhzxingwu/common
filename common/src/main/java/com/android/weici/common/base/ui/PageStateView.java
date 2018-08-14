package com.android.weici.common.base.ui;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.weici.common.R;
import com.android.weici.common.base.CommonConfig;

public class PageStateView {
    private Activity mContext;
    private View mView;
    private TextView mErrorText;
    private ImageView mErrorImg;
    private ProgressBar mProgressBar;
    public PageStateView(Activity context, ViewGroup rootView, OnPageStateListener listener){
        mListener = listener;
        mContext = context;
        mView = View.inflate(mContext, R.layout.view_state_layout, null);
        mView.setVisibility(View.GONE);
        mErrorText = mView.findViewById(R.id.view_state_text);
        mErrorImg = mView.findViewById(R.id.view_state_img);
        mProgressBar = mView.findViewById(R.id.view_state_progress);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mView.setLayoutParams(lp);
        if(rootView instanceof LinearLayout) rootView.addView(mView, 0);
        else rootView.addView(mView);
    }

    public void showLoading(){
        mView.setVisibility(View.VISIBLE);
        mErrorImg.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mErrorText.setText("数据加载中...");
    }

    public void showError(String error, final int code){
        mView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mErrorImg.setVisibility(View.VISIBLE);
        mErrorText.setText(error);
        if(!(CommonConfig.data == null || CommonConfig.data.getErrorPager() == null))
            CommonConfig.data.getErrorPager().showText(mErrorText, code);
        mErrorText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null) mListener.stateAction(code);
                if(!(CommonConfig.data == null || CommonConfig.data.getErrorPager() == null))
                CommonConfig.data.getErrorPager().doAction(mContext, code);
            }
        });
    }

    public void showSuccess(){
        mView.setVisibility(View.GONE);
    }

    private OnPageStateListener mListener;
    public interface OnPageStateListener{
        void stateAction(int n);
    }
}
