package com.android.weici.common.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.android.weici.common.R;
import com.android.weici.common.Tools;
import com.android.weici.common.thread.AsyncThread;
import com.android.weici.common.widget.InfoView;

import java.util.HashMap;

/**
 * Created by Mouse on 2017/11/3.
 */

public abstract class LoadingBaseActivity extends BaseActivity {

    private InfoView mLoadingLayout;
    private FrameLayout mContentLayout;

    private String mLoadingText = "正在加载...";
    private String mFailureText = "加载失败";
    protected Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vui_content_frame);
        mLoadingLayout = (InfoView) findViewById(R.id.vui_info);
        mLoadingLayout.setOnClickListener(onReplyClickListener);
        mContentLayout = (FrameLayout) findViewById(R.id.contentPanel);
        LayoutInflater.from(this).inflate(createContentRes(), mContentLayout);
        initView();
        handler = new Handler(Looper.myLooper());
        if (isNeedLoadData()) {
            load();
        }
    }

    View.OnClickListener onReplyClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            load();
        }
    };

    public void load() {
        switchLayout(true);
        mLoadingLayout.showLoading(mLoadingText);
        if (isNeedNetWorkLoad() && !Tools.isNetworkAvailable(this)) {
            mLoadingLayout.showFailureInfo("网络无效");
            return;
        }

        AsyncThread.AsyncRun(new Runnable() {
            @Override
            public void run() {
                final HashMap<String, Object> map = loadData();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onResult(map);
                    }
                });
            }
        });
    }

    protected void showFailure(String text) {
        switchLayout(true);
        mLoadingLayout.showFailureInfo(text);
    }

    protected void showSuccess() {
        switchLayout(false);
    }

    protected abstract void onResult(HashMap<String, Object> map);

    protected abstract HashMap<String, Object> loadData();


    protected boolean isNeedNetWorkLoad() {
        return true;
    }

    protected abstract void initView();

    protected boolean isNeedLoadData() {
        return true;
    }

    public void switchLayout(boolean isLoading) {
        if (isLoading) {
            mLoadingLayout.setVisibility(View.VISIBLE);
            getHiddenView().setVisibility(View.GONE);
        } else {
            mLoadingLayout.setVisibility(View.GONE);
            getHiddenView().setVisibility(View.VISIBLE);
        }
    }

    protected abstract int createContentRes();

    protected abstract View getHiddenView() ;

    protected String getLoadingText() {
        return mLoadingText;
    }

    protected String getFailureText() {
        return mFailureText;
    }

    protected void setLoadingText(String text) {
        this.mLoadingText = text;
    }

    protected void setFailureText(String text) {
        this.mFailureText = text;
    }
}
