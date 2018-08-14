package com.android.weici.common.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;

import com.android.weici.common.help.StatusBarUtil;
import com.android.weici.common.manager.ToastManager;
import com.android.weici.common.widget.diaolog.LoadingDialog;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {

    public static List<Activity> activityStack = new ArrayList<>();

    protected LoadingDialog mLoadingDialog;
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        activityStack.add(this);
        registEvenBus();
        Log.i("weiciLog", "onCreate:" + this.getClass().getName());
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        initStatuBar();
    }


    private void initStatuBar(){
        if (isNeedStatusBarLightMode() && StatusBarUtil.setStatusbarTextColor(this) != 0) {
            if(isNeedTranslateMode()){
                onChangeTitlebarColor();
            }else{
                StatusBarUtil.setStatusBarColor(this, Color.WHITE);
            }
        }
    }

    private void registEvenBus() {
        if (configEvenBus()) {
            EventBus.getDefault().register(this);
        }
    }

    protected void onChangeTitlebarColor(){

    }

    protected boolean isNeedStatusBarLightMode() {
        return true;
    }

    protected boolean isNeedTranslateMode() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityStack.remove(this);
        unregistEvenBus();
    }

    private void unregistEvenBus() {
        if (configEvenBus()) {
            EventBus.getDefault().unregister(this);
        }
    }

    protected boolean configEvenBus() {
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackKeyDown();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void onBackKeyDown() {
        if (null != mLoadingDialog && mLoadingDialog.isShowing()) {
            return;
        }
        onBackPressed();
    }

    protected LoadingDialog getLoadingDialog() {
        if (null == mLoadingDialog) {
            mLoadingDialog = new LoadingDialog(this);
        }
        return mLoadingDialog;
    }

    public void dismissLoadingDialog() {
        if (null != mLoadingDialog && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

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

    public void showLoadingDialog() {
        if (null != mLoadingDialog && mLoadingDialog.isShowing()) {
            return;
        }
        getLoadingDialog().show();
    }

    public void showToast(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        ToastManager.getInstance().showToast(this, msg);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public static void finishAll(){
        for(Activity activity:activityStack){
            activity.finish();
        }
    }
}
