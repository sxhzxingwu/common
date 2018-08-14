package com.android.weici.common.help;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by Mouse on 2018/4/2.
 */

public class PermissionHelper {

    public static boolean hasRecordPermission(Context context) {
//        if (Tools.getCurrentSdk() < Tools.ANDROID_6_0) {
//            return true;
//        }
        return ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
    }

}
