package com.android.weici.common;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.android.weici.common.exception.CustomException;
import com.android.weici.common.manager.ToastManager;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {

    public static final int ANDROID_1_5 = 3;
    public static final int ANDROID_1_6 = 4;
    public static final int ANDROID_2_0 = 5;
    public static final int ANDROID_2_0_1 = 6;
    public static final int ANDROID_2_1 = 7;
    public static final int ANDROID_2_2 = 8;
    public static final int ANDROID_2_3 = 9;
    public static final int ANDROID_2_3_3 = 10;
    public static final int ANDROID_3_0 = 11;
    public static final int ANDROID_4_0 = 14;
    public static final int ANDROID_4_1 = 16;
    public static final int ANDROID_4_2 = 17;
    public static final int ANDROID_4_4 = 19;
    public static final int ANDROID_5_0 = 20;
    public static final int ANDROID_6_0 = 23;
    /**
     * dp转px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率 px(像素) 转 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取屏幕分辨
     *
     * @param context
     * @return
     */
    public static int[] getScreenSize(Context context) {
        DisplayMetrics metrics = getDisplayMetrics(context);
        return new int[]{metrics.widthPixels, metrics.heightPixels};
    }

    /**
     * 获取屏幕高
     *
     * @param context
     * @return
     */
    public static int getDisplayDensityDpi(Context context) {
        return getDisplayMetrics(context).densityDpi;
    }

    /**
     * 返回原尺寸的DisplayMetrics�?4.0默认会减掉�?�知栏部分，故要作处�?
     *
     * @param context
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metric);
        Display display = wm.getDefaultDisplay();

        //4.0之前的SDK直接返回当前metric
        if (Build.VERSION.SDK_INT < 14) {
            return metric;
        }

        int rawWidth = metric.widthPixels;
        int rawHeight = metric.heightPixels;
        try {
            Method mGetRawH = Display.class.getMethod("getRawHeight");
            Method mGetRawW = Display.class.getMethod("getRawWidth");
            rawWidth = (Integer) mGetRawW.invoke(display);
            rawHeight = (Integer) mGetRawH.invoke(display);
        } catch (SecurityException e) {
        } catch (NoSuchMethodException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }
        metric.widthPixels = rawWidth;
        metric.heightPixels = rawHeight;

        return metric;
    }

    public static String getMetaData(Context context, String key) {
        ApplicationInfo appInfo;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            return appInfo.metaData.getString(key);
        } catch (PackageManager.NameNotFoundException e) {
        }
        return null;
    }

    /**
     * 获取文字的高度
     *
     * @param paint
     * @return
     */
    public static int getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.top) + 2;
    }

    /**
     * @return 返回指定笔离文字顶部的基准距离
     */
    public static float getFontLeading(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.leading - fm.ascent;
    }

    /**
     * 获取文字的宽度
     *
     * @param str
     * @param paint
     * @return
     */
    public static int getStringWidth(String str, Paint paint) {
        return (int) paint.measureText(str);
    }


    public static boolean isEnglishChar(String s) throws CustomException {
        if(TextUtils.isEmpty(s)){
            throw new CustomException("string is not null");
        }

        if(s.length()!=1){
            throw new CustomException("length of string is not bigger than one");
        }
        char c = s.charAt(0);
        return isEnglishChar(c);
    }

    /**
     * ASCII码
     * A-Z 65-90 91 [ 92 \ 93 ] 94^ 95_96` a-z 97-122
     * 33-47 标点符号 0-9 48-64
     * @param s
     * @return
     */
    public static boolean isEnglishChar(char s){
        if((s>=65&&s<=90)||(s>=97&&s<=122)){
            return true;
        }
        return false;
    }

    public static String getVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException nnfe) {
            return "null";
        } catch (Exception e) {
            return "null";
        }
    }

    public static int getCurrentSdk(){
        return Build.VERSION.SDK_INT;
    }

    public static boolean isLetterOrChinese(String str) {
        String regex = "^[a-zA-Z\u4e00-\u9fa5]{2,10}";
        return str.matches(regex);
    }

    public static boolean isLetterDigitOrChinese(String str) {
        String regex = "^[a-z0-9A-Z\u4e00-\u9fa5]{4,40}";
        return str.matches(regex);
    }



    public static void setClickEffection(View view) {

        view.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setAlpha(0.6f);
                        break;
                    case MotionEvent.ACTION_OUTSIDE:
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_SCROLL:
                        v.setAlpha(1.0f);
                        break;
                    default:
                        break;
                }

                return false;
            }
        });
    }

    public static String uuid(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 判断当前网络是否是wifi
     */
    public static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public static int getVersionCode(Context context) {
        PackageInfo pi = null;
        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            return pi.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getVersionName(Context context) {
        PackageInfo pi = null;
        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            return pi.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {// 逐一查找状�?�为已连接的网络
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isAvalidEmail(String email) {
        String str = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean isAvalidPsw(String psw){
        String reg = "[a-zA-Z0-9]{8,16}$";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(psw);
        return m.matches();
    }

    public static int stringToInt(String num){
        try {
            return Integer.valueOf(num);
        }catch (Exception e){
            return 0;
        }
    }

    public static float stringToFloat(String num){
        try {
            return Float.valueOf(num);
        }catch (Exception e){
            return 0;
        }
    }

    public static void startMarket(Context context) {
        try {
            Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
            Intent it = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(it);
        } catch (Exception e) {
            ToastManager.getInstance().showToast(context, "没有找到应用市场");
        }
    }
    /**
     * 是否是字母（大小写）
     * @param c
     * @return
     */
    public static boolean isLetter(char c){
        return ((c>=65&&c<=90)||(c>=97&&c<=122));
    }

    public static boolean isListNull(List list){
        if(null==list||list.size()==0){
            return true;
        }
        return false;
    }

    public static long getSystemLeftSpace(){
        File root = Environment.getDataDirectory();
        StatFs sf = new StatFs(root.getPath());
        long blockSize = sf.getBlockSize();
        return sf.getAvailableBlocks()*blockSize;
    }

    public static boolean isLetterDigitChinese(String str) {
        String regex = "^[a-z0-9A-Z\u4e00-\u9fa5]+";
        return str.matches(regex);
    }

    public static String isEnableInPut(String text){
        if(TextUtils.isEmpty(text) || text.equals("")) return "";
        text = text.trim();
        text = text.replaceAll("\\?+$", "");
        if(isLetterDigitChinese(text)) return text;
        return "";
    }
    public static String stringListToStr(List<String> list){
        if(list == null || list.size() == 0) return "";
        StringBuilder ids = new StringBuilder();
        for(int i=0; i<list.size(); i++){
            ids.append(list.get(i));
            if(i<list.size()-1) ids.append(",");
        }
        return ids.toString();
    }

    public static <T> void copyList(List<T> from, List<T> to){
        if(from == null || from.size() == 0 || to == null) return;
        to.clear();
        for(int i=0; i<from.size(); i++){
            to.add(from.get(i));
        }
    }
}
