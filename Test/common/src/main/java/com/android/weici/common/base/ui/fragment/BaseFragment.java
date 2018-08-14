package com.android.weici.common.base.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.weici.common.Logger;

/**
 * Created by Mouse on 2017/10/10.
 */

public abstract class BaseFragment extends Fragment {

    protected View mRoot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(onCreateContent(), container, false);
        //try {
            initView();
        //}catch(Exception e) {
        //}
        registMyReceiver();
        return mRoot;
    }

    public void registMyReceiver(){

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    protected void initView() {
        Logger.i(this.getClass().getName());
    }

    protected abstract int onCreateContent();

    /**
     * 设置fragment name
     */
    protected String getPageName(){
        return null;
    }

}
