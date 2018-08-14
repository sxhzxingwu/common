package com.android.weici.common.media;

import android.text.TextUtils;

import java.util.List;

public class MediaListLogicImpl implements IMediaListLogic, OnMediaPlayerListener {
	private OnMediaListListener listener;

	private List<MediaListItem> mMediaList;
	private int position = 0;
	private boolean isPlaying = false;
	private boolean isUsa = true;

	public void error() {
		playNext();
	}

	@Override
	public void stop() {
		isPlaying = false;
        MediaPlayerLogicImpl.getInstance().stop();
	}

	public void onPause(){
        if(listener != null) listener.onStop();
    }

	public int getSize(){
	    if(mMediaList == null) return 0;
	    return mMediaList.size();
    }

	@Override
	public void destroy() {
        MediaPlayerLogicImpl.getInstance().detroy();
	}

	private int delayTime = 0;
	public void setDelayTime(int delayTime){
		this.delayTime = delayTime;
	}
	private void playNext() {
		position = position + 1 >= mMediaList.size() ? 0 : position + 1;
		// int delay = position%2==0?2000:1000;
		int delay = 1000 * delayTime;
		try {
			Thread.sleep(delay);
		} catch (InterruptedException ignored) {
		}

		if (isPlaying()) {
			startPlay();
		}
	}

	private void startPlay() {
        MediaListItem mediaListItem  = mMediaList.get(position);
        String name = isUsa ? mediaListItem.usaMp3 : mediaListItem.englishMp3;
        if (null != listener) {
            listener.onProgress(position, mediaListItem.position);
        }
        if (TextUtils.isEmpty(name)) {
            position = position + 1 >= mMediaList.size() ? 0 : position + 1;
            startPlay();
            return;
        }
		play();
	}

	@Override
	public void setMediaList(List<MediaListItem> list) {
		this.mMediaList = list;
		stop();
		position = 0;
	}

	@Override
	public boolean isPlaying() {
		return isPlaying;
	}

	@Override
	public void start() {
        MediaPlayerLogicImpl.getInstance().setOnMediaPlayerListener(this);
		if (null == mMediaList || mMediaList.size() == 0) {
		    if(listener != null)
			listener.onError("还没有单词");
			return;
		}
		if(isPlaying){
            MediaPlayerLogicImpl.getInstance().stop();
		    return;
        }
		isPlaying = true;
		play();
	}

	private void play(){
        MediaListItem mediaListItem  = mMediaList.get(position);
        String name = isUsa ? mediaListItem.usaMp3 : mediaListItem.englishMp3;
        if (null != listener) {
            listener.onProgress(position, mediaListItem.position);
        }
        MediaPlayerLogicImpl.getInstance().play(name);
    }

	@Override
	public void restart() {
		position = 0;
		stop();
		start();
	}

	@Override
	public void setListener(OnMediaListListener listener) {
		this.listener = listener;
	}

	@Override
	public boolean isUsa() {
		return isUsa;
	}

	@Override
	public void setUsa(boolean isUsa) {
		this.isUsa = isUsa;
		restart();
	}

	@Override
	public int getPosition() {
		return position;
	}

	@Override
	public void setPosition(int position) {
		this.position = position;
	}

    @Override
    public void onCompletionListener() {
        playNext();
    }

    @Override
    public void onError() {
        playNext();
    }

    @Override
    public void onPlayerPause() {

    }

    public static class MediaListItem{
	    public String usaMp3;
	    public String englishMp3;
	    public int position;
    }
}