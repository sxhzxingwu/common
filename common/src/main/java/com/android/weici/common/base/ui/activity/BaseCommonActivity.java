package com.android.weici.common.base.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.android.weici.common.R;
import com.android.weici.common.base.BaseActivity;
import com.android.weici.common.base.ui.BasePresenter;
import com.android.weici.common.base.ui.IBaseView;
import com.android.weici.common.base.ui.PageStateView;
import com.android.weici.common.widget.VUITitleBar;

/**
 * Created by weici on 2017/12/7.
 */
public abstract class BaseCommonActivity extends BaseActivity implements IBaseView, PageStateView.OnPageStateListener {

    private PageStateView mPage;
    protected BasePresenter mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        initPageManager();
        initContentView();
    }

    protected abstract int getContentView();

    protected abstract void initContentView();

    protected BasePresenter getPresenter(){
        return null;
    }

    protected VUITitleBar setTitle(String title) {
        VUITitleBar titleBar = findViewById(R.id.title_bar);

        titleBar.setTitle(title);
        return titleBar;
    }

    private void initPageManager() {
        mPage = new PageStateView(this, getContainer(), this);
        if(mPresenter == null)
            mPresenter = getPresenter();
    }

    protected ViewGroup getContainer() {
        ViewGroup vg = findViewById(R.id.root_layout);
        if (vg != null)
            return vg;
        return this.findViewById(android.R.id.content);
    }

    @Override
    public void showMessage(String s) {
        showToast(s);
    }

    @Override
    public void showFailureState(String text, int code) {
        mPage.showError(text, code);
    }

    @Override
    public void showFailureState(String text) {
        mPage.showError(text, -1);
    }

    @Override
    public void showLoadingState() {
        mPage.showLoading();
    }

    @Override
    public void showSuccessState() {
        mPage.showSuccess();
    }

    @Override
    public void stateAction(int n) {
        if (mPresenter != null && n == -2) mPresenter.prepareData();
    }
}
