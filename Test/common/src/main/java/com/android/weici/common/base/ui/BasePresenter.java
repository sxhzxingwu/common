package com.android.weici.common.base.ui;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.android.weici.common.base.api.ApiResult;
import com.android.weici.common.thread.AsyncThread;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mouse on 2017/11/6.
 */

public abstract class BasePresenter {

    protected Handler handler;
    protected Context context;
    private List<Runnable> list;

    public BasePresenter(Context context) {
        handler = new Handler(Looper.myLooper());
        list = new ArrayList<>();
        this.context = context;
    }


    public void executeOnNotUiThread(Runnable runnable) {
        AsyncThread.AsyncRun(runnable);
    }

    public void destroy() {
        for (Runnable run : list) {
            if(null!=handler){
                handler.removeCallbacks(run);
            }
        }
        handler = null;
        context = null;
    }

    public void executeOnUiThread(int code, String msg, ApiResult result, Object object) {
        WcRunnable run = new WcRunnable(code, msg, result, object);
        list.add(run);
        if(handler != null)
        handler.post(run);
    }

    public void executeOnUiThread(int code,ApiResult result, Object object) {
        executeOnUiThread(code, null, result, object);
    }

    public void executeOnUiThread(int code,String text, Object object) {
        executeOnUiThread(code, text, null, object);
    }

    public void executeOnUiThread(int code, Object object) {
        executeOnUiThread(code, null, null, object);
    }

    public void executeOnUiThread(int code, ApiResult result) {
        executeOnUiThread(code, null, result, null);
    }

    public void executeOnUiThread(int code, String msg, ApiResult result) {
        executeOnUiThread(code, msg, result, null);
    }

    public void executeOnUiThread(int code, String msg) {
        executeOnUiThread(code, msg, null);
    }

    public void executeOnUiThread(int code) {
        executeOnUiThread(code, "");
    }

    public class WcRunnable implements Runnable {

        private int code;
        private String msg;
        private ApiResult result;
        private Object object;

        public WcRunnable(int code, String msg, ApiResult result, Object object) {
            this.code = code;
            this.msg = msg;
            this.result = result;
            this.object = object;
        }
        @Override
        public void run() {
            onMainThreadResult(code, msg, result, object);
        }
    }

    public void onMainThreadResult(int code, String msg, ApiResult result, Object object) {
    }

    public void prepareData(){}
}
