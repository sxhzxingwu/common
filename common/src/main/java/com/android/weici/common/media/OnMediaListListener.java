package com.android.weici.common.media;

public interface OnMediaListListener {

	void onProgress(int progress, int pos);
	void onError(String msg);
	void onStop();
}
