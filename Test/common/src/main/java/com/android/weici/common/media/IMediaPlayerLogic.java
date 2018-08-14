package com.android.weici.common.media;


import android.widget.TextView;

public interface IMediaPlayerLogic {

	void play(String name);
	void setOnMediaPlayerListener(OnMediaPlayerListener listener);
	void executePlay(String name, OnMediaPlayerListener listener);
	void stop();
	void detroy();
}
