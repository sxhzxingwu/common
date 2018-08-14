package com.android.weici.common.base.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.weici.common.Logger;
import com.android.weici.common.R;
import com.android.weici.common.base.ui.BasePresenter;
import com.android.weici.common.base.ui.IBaseView;
import com.android.weici.common.base.ui.PageStateView;
import com.android.weici.common.manager.ToastManager;
import com.android.weici.common.widget.diaolog.LoadingDialog;

public abstract class BaseCommonFragment extends BaseFragment implements IBaseView, PageStateView.OnPageStateListener {
    private PageStateView mPage;
    protected View mRoot;
    protected LoadingDialog mLoadingDialog;
    protected BasePresenter mPresenter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getRootView(inflater, container);
        try {
            mPage = new PageStateView(getActivity(), getContainer(), this);
            mPresenter = getPresenter();
            initView();
        }catch(Exception e) {
            Logger.d("e:"+e.getMessage());
        }
        registMyReceiver();
        return mRoot;
    }

    protected BasePresenter getPresenter(){return null;}
    protected ViewGroup getContainer() {
        return mRoot.findViewById(R.id.root_layout);
    }

    private void getRootView(LayoutInflater inflater, ViewGroup container){
        if(getRootLayout() == -1){
            mRoot = inflater.inflate(R.layout.fragment_root_layout, container, false);
            LinearLayout root = mRoot.findViewById(R.id.root_layout);
            if(onCreateContent() != 0) {
                View contentView = View.inflate(getContext(), onCreateContent(), null);
                root.addView(contentView);
            }
        }else {
            mRoot = inflater.inflate(getRootLayout(), container, false);
        }
    }

    protected int getRootLayout(){
        return -1;
    }

    @Override
    public void showLoadingDialog() {
        if (null != mLoadingDialog && mLoadingDialog.isShowing()) {
            return;
        }
        getLoadingDialog().show();
    }

    @Override
    public void dismissLoadingDialog() {
        if (null != mLoadingDialog && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void dismissLoadingDialog(boolean success, String text) {
        if (null != mLoadingDialog && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss(text, success);
        }
    }

    public void dismissLoadingDialog(boolean success, String text, LoadingDialog.OnDialogDismissListener listener) {
        if (null != mLoadingDialog && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss(text, success, listener);
        }
    }

    protected LoadingDialog getLoadingDialog() {
        if (null == mLoadingDialog) {
            mLoadingDialog = new LoadingDialog(getContext());
        }
        return mLoadingDialog;
    }

    @Override
    public void showMessage(String s) {
        ToastManager.getInstance().showToast(getContext(), s);
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

    public void stateAction(int code){
        if(!doAction()) if(mPresenter != null) mPresenter.prepareData();
    }

    protected boolean doAction(){
        return false;
    }
}
