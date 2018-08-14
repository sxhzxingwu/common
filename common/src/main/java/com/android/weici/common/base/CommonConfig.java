package com.android.weici.common.base;

import android.app.Application;

import com.android.weici.common.base.ui.ConfigData;

public class CommonConfig {
    public static Application app;
    public static ConfigData data;
    public static ApiData apiData;
    public static void init(Application context, ConfigData configData){
        app = context;
        data = configData;
    }

    public static void init(Application context){
        app = context;
    }

    public static void init(Application context,ApiData apiData){
        app = context;
        CommonConfig.apiData = apiData;
    }
}
