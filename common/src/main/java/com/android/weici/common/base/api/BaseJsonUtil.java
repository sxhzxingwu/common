package com.android.weici.common.base.api;

import android.text.TextUtils;

import org.json.JSONObject;

/**
 * Created by weici on 2018/1/16.
 */

public abstract class BaseJsonUtil {

    protected static void parseResult(ApiResult result, Object object){
        result.object = object;
        if(result.code == 200 && result.object == null){
            result.code = 201;
        }
    }

    public static JSONObject parseOnlyCode(String text, ApiResult result) {
        if (!TextUtils.isEmpty(text)) {
            try {
                JSONObject json = new JSONObject(text);
                result.code = json.optInt("result_code");
                onErrorCode(result.code);
                return json;
            } catch (Exception e) {
            }
        }
        return null;
    }

    protected static void onErrorCode(int code) {

    }
}
