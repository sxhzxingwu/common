package com.android.weici.common.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 若想异步执行网络线程 调用此类，不要使用AsyncTask 如果感觉网络异步调用特别阻塞，可以使用这个类。
 * 
 * @author pp
 */
public class AsyncThread {
	private static ExecutorService mExecutorService = Executors
			.newFixedThreadPool(20);

	public static void AsyncRun(Runnable runable) {
		mExecutorService.execute(runable);
	}

}
