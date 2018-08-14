package com.android.weici.common.help;

import android.text.TextUtils;

import org.json.JSONObject;

/**
 * Created by Mouse on 2018/6/22.
 */

public class HanderDataHelper {

    public static String getJsonString(String string){

        if(TextUtils.isEmpty(string)){
            return string;
        }

        if(string.equals("null")){
            return "";
        }

        if(string.equals("NULL")){
            return "";
        }

        if(string.equals("Null")){
            return "";
        }

        return string;
    }

    public static String jsonOptString(JSONObject object,String key){
        String s = object.optString(key);
        return getJsonString(s);
    }



}
