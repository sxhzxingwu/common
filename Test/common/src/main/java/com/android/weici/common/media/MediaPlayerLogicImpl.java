package com.android.weici.common.media;

import android.content.Context;
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

public class MediaPlayerLogicImpl
		implements IMediaPlayerLogic, OnCompletionListener, OnErrorListener, OnPreparedListener {

	private static MediaPlayerLogicImpl mInstance;
	//private Context mContext;
	private MediaPlayer mMediaPlayer;
	private MusicRadioHandlerThread mHandlerThread;
	private Handler mThreadHandler;
	private OnMediaPlayerListener listener;
	private int state;

	public static final int STATE_IDLE = 0;
	public static final int STATE_PLAYING = 1;
	public static final int STATE_PREPAREING = 2;

	private MediaPlayerLogicImpl() {
		//this.mContext = context.getApplicationContext();
		WeakReference<MediaPlayerLogicImpl> logic = new WeakReference<>(this);
		mHandlerThread = new MusicRadioHandlerThread("musicradio", logic);
		mHandlerThread.start();
		mThreadHandler = new Handler(mHandlerThread.getLooper(), mHandlerThread);
		state = STATE_IDLE;
		//Logger.d("state:"+state);
	}

	public static MediaPlayerLogicImpl getInstance() {
		if (null == mInstance) {
			mInstance = new MediaPlayerLogicImpl();
		}
		return mInstance;
	}

	private boolean isExist(String path) {
		return !(path == null || path.equals("")) && new File(path).exists();
	}

	private void playByName(String name) {
		try {
			String path = name;
			boolean exist = isExist(path);
			if(!exist){
				path = getDownloadAudioDir(CommonConfig.app) + "/" + name;
				exist = isExist(path);
			}

			MediaPlayer mediaPlayer = getMediaPlayer();
			boolean http = name.startsWith("http");
			if (exist) {
				mediaPlayer.setDataSource(path);
			}else if(http){
				mediaPlayer.setDataSource(name);
			} else {
				AssetManager am = CommonConfig.app.getAssets();
				AssetFileDescriptor afd = am.openFd("audio/" + name);
				mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
			}
			mediaPlayer.prepareAsync();
			state = STATE_PREPAREING;
			//Logger.d("state:"+state);
            //Log.i("studyLog", "播放:" + name);
		} catch (Exception e) {
			if (null != listener) {
                //ToastManager.getInstance().showToast(mContext, "播放失败，请重试");
				listener.onError();
			}
		}
	}

	@Override
	public void stop() {
		try {
			if (null != mMediaPlayer && mMediaPlayer.isPlaying()) {
				mMediaPlayer.stop();
				state = STATE_IDLE;
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
			state = STATE_IDLE;
			//Logger.d("state:"+state);
			//mContext = null;
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
		if (null != listener) {
			listener.onCompletionListener();
			state = STATE_IDLE;
			//Logger.d("state:"+state);
		}
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		if (null != listener) {
			listener.onError();
			state = STATE_IDLE;
			//Logger.d("state:"+state);
		}
		return false;
	}

	public static class MusicRadioHandlerThread extends HandlerThread implements Callback {

		WeakReference<MediaPlayerLogicImpl> mLogic;

		MusicRadioHandlerThread(String name, WeakReference<MediaPlayerLogicImpl> logic) {
			super(name);
			this.mLogic = logic;
		}

		@Override
		public boolean handleMessage(Message msg) {
			if (null == msg) {
				return false;
			}
			MediaPlayerLogicImpl logic = mLogic.get();
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
			state = STATE_PLAYING;
			//Logger.d("state:"+state);
		} else {
			if (null != listener) {
				listener.onCompletionListener();
			}
		}
	}

	private String getDownloadAudioDir(Context context){
		return "/data/data/"+context.getPackageName()+"/media";
	}

	public boolean isPlaying(){
		//Logger.d("state:"+state);
		return state!=STATE_IDLE;
	}
}
