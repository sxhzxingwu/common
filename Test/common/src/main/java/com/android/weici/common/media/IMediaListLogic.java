package com.android.weici.common.media;

import java.util.List;


public interface IMediaListLogic {

	void stop();
	void destroy();
	void setMediaList(List<MediaListLogicImpl.MediaListItem> list);
	boolean isPlaying();
	void start();
	void restart();
	
	void setListener(OnMediaListListener listener);
	
	boolean isUsa();
	void setUsa(boolean isUsa);
	
	int getPosition();
	void setPosition(int position);
}
