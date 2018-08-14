package com.android.weici.common;

import android.text.TextUtils;
import android.util.Log;


public class Logger {

    public static boolean isDebug = true;
    private static final String TAG = "weiciLog";

    public static void i(String msg) {
        println(Log.INFO, TAG, msg);
    }

    public static void e(String msg) {
        println(Log.ERROR, TAG, msg);
    }

    public static void e(String tag, String msg) {
        println(Log.ERROR, tag, msg);
    }

    public static void e(String tag, String msg, Throwable e) {
        println(tag, msg, e);
    }

    public static void w(String msg) {
        println(Log.WARN, TAG, msg);
    }

    public static void w(String tag, String msg) {
        println(Log.WARN, tag, msg);
    }

    public static void i(String tag, String msg) {
        println(Log.INFO, tag, msg);
    }

    public static void v(String tag, String s) {
        println(Log.VERBOSE, tag, s);
    }

    public static void d(String tag, String s) {
        println(Log.DEBUG, tag, s);
    }

    public static void d(String s) {
        println(Log.DEBUG, TAG, s);
    }

    private static void println(int priority, String tag, String msg) {
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(msg) || !isDebug) return;

        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        String info = "";
        if(trace != null && trace.length >=4){
            StackTraceElement element = trace[4];
            info = "(" + element.getFileName() + ":" + element.getLineNumber() + ")";
        }
        Log.println(priority, tag, info + msg);
    }

    private static void println(String tag, String msg, Throwable e) {
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(msg) || !isDebug) return;

        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        String info = "";
        if(trace != null && trace.length >=4){
            StackTraceElement element = trace[4];
            info = "(" + element.getFileName() + ":" + element.getLineNumber() + ")";
        }
        Log.e(tag, info + msg, e);
    }
}
