package com.android.weici.common.base.api;

import android.os.AsyncTask;

import com.android.weici.common.Logger;
import com.android.weici.common.Tools;
import com.android.weici.common.base.CommonConfig;
import com.android.weici.common.net.OkHttpUtil;
import com.android.weici.common.thread.AsyncThread;

public class ApiRequest {

    public static void execute(final ApiBody apiBody, final OnCallBackListener listener) {
        final ApiResult result = new ApiResult(-1);
        if (!Tools.isNetworkAvailable(CommonConfig.app)) {
            result.code = -2;//无网络错误码
            if (listener != null) listener.callBack(result);
            return;
        }
        AsyncThread.AsyncRun(new Runnable() {
            @Override
            public void run() {
                doApiBody(apiBody, result);
                if (listener != null) listener.callBack(result);
            }
        });
    }

    public static void executeWithOutPresenter(final ApiBody apiBody, final OnCallBackListener listener) {
        final ApiResult result = new ApiResult(-1);
        if (!Tools.isNetworkAvailable(CommonConfig.app)) {
            result.code = -2;//无网络错误码
            if (listener != null) listener.callBack(result);
            return;
        }
        new TaskWithOutPresenter(apiBody, result, listener).execute();
    }

    private static class TaskWithOutPresenter extends AsyncTask<Void, Void, ApiResult> {
        ApiBody apiBody;
        ApiResult result;
        OnCallBackListener listener;

        TaskWithOutPresenter(final ApiBody apiBody, final ApiResult result, final OnCallBackListener listener) {
            this.apiBody = apiBody;
            this.result = result;
            this.listener = listener;
        }

        @Override
        protected ApiResult doInBackground(Void... voids) {
            doApiBody(apiBody, result);
            return result;
        }

        @Override
        protected void onPostExecute(ApiResult result) {
            if (listener != null) listener.callBack(result);
        }
    }

    public static void execute(final OnCallBackListListener listener, final ApiBody... apiBody) {
        ApiResult result = new ApiResult(-1);
        if (!Tools.isNetworkAvailable(CommonConfig.app)) {
            result.code = -2;//无网络错误码
            if (listener != null) listener.callBack(result);
            return;
        }
        AsyncThread.AsyncRun(new Runnable() {
            @Override
            public void run() {
                int size = apiBody.length;
                ApiResult[] results = new ApiResult[size];
                for (int i = 0; i < size; i++) {
                    results[i] = new ApiResult(-1);
                    doApiBody(apiBody[i], results[i]);
                }

                if (listener != null) listener.callBack(results);
            }
        });
    }

    public static ApiResult execute(final ApiBody apiBody) {
        ApiResult result = new ApiResult(-1);
        if (!Tools.isNetworkAvailable(CommonConfig.app)) {
            result.code = -2;//无网络错误码
            return result;
        }
        doApiBody(apiBody, result);
        return result;
    }

    private static void doApiBody(ApiBody apiBody, ApiResult result) {
        String url = apiBody.URL;
        String s = null;
        if (apiBody.type == OkHttpUtil.TYPE.GET) {
            s = OkHttpUtil.getStrByGet(url, apiBody.params);
        } else if (apiBody.type == OkHttpUtil.TYPE.POST) {
            s = OkHttpUtil.getStrByPost(url, apiBody.params, apiBody.Encoder);
        }else if(apiBody.type == OkHttpUtil.TYPE.IMG){
            result.object = OkHttpUtil.getBitmapByGet(url, apiBody.params);
        }else if(apiBody.type == OkHttpUtil.TYPE.UPDATE_FILE){
            s = OkHttpUtil.uploadFileByPost(url, apiBody.file, apiBody.params);
        }
        Logger.i(s);
        apiBody.parse(s, result);
    }

    public static ApiResult executeOnGet(ApiBody apiBody) {
        ApiResult result = new ApiResult(-1);
        String url = apiBody.URL;
        String s = null;
        if (apiBody.type == OkHttpUtil.TYPE.GET) {
            s = OkHttpUtil.getStrByGet(url, apiBody.params);
        } else if (apiBody.type == OkHttpUtil.TYPE.POST) {
            s = OkHttpUtil.getStrByPost(url, apiBody.params);
        }
        apiBody.parse(s, result);
        return result;
    }

    public static ApiResult executeOnPost(ApiBody apiBody) {
        ApiResult result = new ApiResult(-1);
        String url = apiBody.URL;
        String s = OkHttpUtil.getStrByPost(url, apiBody.params);
        Logger.i("result:" + s);
        apiBody.parse(s, result);
        return result;
    }

    public interface OnCallBackListener {
        void callBack(ApiResult result);
    }

    public interface OnCallBackListListener {
        void callBack(ApiResult... result);
    }
}
