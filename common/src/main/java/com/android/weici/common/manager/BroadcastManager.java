package com.android.weici.common.manager;

import android.content.Context;
import android.content.Intent;

/**
 * Created by weici on 2018/1/16.
 */

public class BroadcastManager {

    public static final String ACTION_LOGIN_FINISH = "action_login_finish";
    public static final String ACTION_USER_AVILID = "ACTION_USER_AVALID";
    public static void sendUserAvalidReceiver(Context context, int code) {
        Intent intent = new Intent();
        intent.setAction(ACTION_USER_AVILID);
        intent.putExtra("code", code);
        context.sendBroadcast(intent);
        sendLoginFinishReceiver(context);
    }

    public static void sendLoginFinishReceiver(Context context) {
        Intent intent = new Intent(ACTION_LOGIN_FINISH);
        context.sendBroadcast(intent);
    }

}
