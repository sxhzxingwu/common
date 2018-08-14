package com.android.weici.common.media;

import android.widget.TextView;

/**
 *
 * Created by weici on 2018/1/23.
 */

public class MediaButtonLogicImpl implements IMediaButtonLogic{

    public String path;
    private TextView mBtnPlaySound;
    private static MediaButtonLogicImpl mLogic;

    public static MediaButtonLogicImpl getInstance(){
        if(mLogic == null) mLogic = new MediaButtonLogicImpl();
        return mLogic;
    }

    @Override
    public void setButton(TextView button) {
        this.mBtnPlaySound = button;
    }

    @Override
    public TextView getButton() {
        return mBtnPlaySound;
    }

    @Override
    public void play(String name) {
        path = name;
        MediaPlayerLogicImpl.getInstance().play(name);
    }

    @Override
    public void setOnMediaPlayerListener(OnMediaPlayerListener listener) {
        MediaPlayerLogicImpl.getInstance().setOnMediaPlayerListener(listener);
    }

    @Override
    public void executePlay(String name, OnMediaPlayerListener listener) {
        path = name;
        MediaPlayerLogicImpl.getInstance().executePlay(name, listener);
    }

    @Override
    public void stop() {
        MediaPlayerLogicImpl.getInstance().stop();
    }

    @Override
    public void detroy() {
        MediaPlayerLogicImpl.getInstance().detroy();
        mLogic = null;
        //mContext = null;
    }
}
