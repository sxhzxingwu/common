package com.android.weici.common.help;

import com.android.weici.common.Logger;

/**
 * Created by Mouse on 2018/7/23.
 */

public class CapabilityHelper {

    private static final String TAG = "test_capablity";
    private static long initTime;
    public static void start(){
        Logger.d(TAG,"-----------------start---------------------");
        initTime = System.currentTimeMillis();
    }

    public static void end(){
        Logger.d(TAG,"用时:"+(System.currentTimeMillis()-initTime));
        Logger.d(TAG,"-----------------end---------------------");
    }

    public static void end(String string){
        Logger.d(TAG,string+(System.currentTimeMillis()-initTime));
        Logger.d(TAG,"-----------------end---------------------");
    }

    public static void progress(String text){
        Logger.d(TAG,text);
    }

}
