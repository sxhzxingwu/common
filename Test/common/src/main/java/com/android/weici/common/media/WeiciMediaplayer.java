package com.android.weici.common.media;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;

import com.android.weici.common.Logger;
import com.android.weici.common.base.CommonConfig;

import java.io.File;
import java.lang.ref.WeakReference;

public class WeiciMediaplayer
		implements IMediaPlayerLogic, OnCompletionListener, OnErrorListener, OnPreparedListener {

	private static WeiciMediaplayer mInstance;
	private MediaPlayer mMediaPlayer;
	private MusicRadioHandlerThread mHandlerThread;
	private Handler mThreadHandler;
	private OnMediaPlayerListener listener;
	private boolean isAssets = false;

	private WeiciMediaplayer() {
		WeakReference<WeiciMediaplayer> logic = new WeakReference<>(this);
		mHandlerThread = new MusicRadioHandlerThread("musicradio", logic);
		mHandlerThread.start();
		mThreadHandler = new Handler(mHandlerThread.getLooper(), mHandlerThread);
	}

	public static WeiciMediaplayer getInstance() {
		if (null == mInstance) {
			mInstance = new WeiciMediaplayer();
		}
		return mInstance;
	}

	public void setAssets(boolean assets) {
		isAssets = assets;
	}

	private void playByName(String name) {
		try {

			MediaPlayer mediaPlayer = getMediaPlayer();
			if (isAssets) {
				AssetManager am = CommonConfig.app.getAssets();
				AssetFileDescriptor afd = am.openFd(name);
				mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
			} else {
				boolean exist = new File(name).exists();
				if(!exist){
					throw new Exception("file is not exist");
				}
				mediaPlayer.setDataSource(name);

			}
			mediaPlayer.prepareAsync();
		} catch (Exception e) {
			if (null != listener) {
				listener.onError();
			}
		}
	}

	@Override
	public void stop() {
		try {
			if (null != mMediaPlayer && mMediaPlayer.isPlaying()) {
				mMediaPlayer.stop();
				if(null!=listener){
					listener.onCompletionListener();
				}
			}
		} catch (Exception ignored) {
		}
	}

	@Override
	public void detroy() {
		if(listener != null) listener.onCompletionListener();
		if (null != mMediaPlayer) {
			stop();
			mMediaPlayer.release();
			mThreadHandler.removeMessages(0);
			mMediaPlayer = null;
			mHandlerThread = null;
			mThreadHandler = null;
			mInstance = null;
			listener = null;
		}
	}

	private MediaPlayer getMediaPlayer() {
		if (mMediaPlayer != null) {
		    mMediaPlayer.reset();
			return mMediaPlayer;
		}
		mMediaPlayer = new MediaPlayer();
		mMediaPlayer.setOnCompletionListener(this);
		mMediaPlayer.setOnErrorListener(this);
		mMediaPlayer.setOnPreparedListener(this);
		return mMediaPlayer;
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		Logger.d("test_media","onCompletion");
		if (null != listener) {
			listener.onCompletionListener();
		}
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		Logger.d("test_media","onError");
		if (null != listener) {
			listener.onError();
		}
		return false;
	}

	public static class MusicRadioHandlerThread extends HandlerThread implements Callback {

		WeakReference<WeiciMediaplayer> mLogic;

		MusicRadioHandlerThread(String name, WeakReference<WeiciMediaplayer> logic) {
			super(name);
			this.mLogic = logic;
		}

		@Override
		public boolean handleMessage(Message msg) {
			if (null == msg) {
				return false;
			}
			WeiciMediaplayer logic = mLogic.get();
			if (0 == msg.what) {
				logic.playByName((String) msg.obj);
			}

			return false;
		}
	}

    @Override
    public void setOnMediaPlayerListener(OnMediaPlayerListener listener) {
        this.listener = listener;
    }

    @Override
    public void play(String name) {
        if (TextUtils.isEmpty(name) || null == mThreadHandler) {
            if (null != listener) {
                listener.onCompletionListener();
            }
            return;
        }
        Message msg = new Message();
        msg.what = 0;
        msg.obj = name;
        mThreadHandler.sendMessage(msg);
    }

	@Override
	public void executePlay(String name, OnMediaPlayerListener listener) {
		this.listener = listener;
		if (TextUtils.isEmpty(name) || null == mThreadHandler) {
			if (null != listener) {
				listener.onCompletionListener();
			}
			return;
		}
		Message msg = new Message();
		msg.what = 0;
		msg.obj = name;
		mThreadHandler.sendMessage(msg);
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		if (mMediaPlayer != null) {
			mMediaPlayer.start();
		} else {
			if (null != listener) {
				listener.onCompletionListener();
			}
		}
	}
}
