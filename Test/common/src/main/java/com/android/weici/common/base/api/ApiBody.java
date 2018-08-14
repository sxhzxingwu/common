package com.android.weici.common.base.api;

import android.support.annotation.NonNull;

import com.android.weici.common.base.CommonConfig;
import com.android.weici.common.help.json.GsonHelper;
import com.android.weici.common.help.json.JsonHelper;
import com.android.weici.common.net.OkHttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

/**
 * Created by weici on 2017/11/13.
 */

public class ApiBody {
    public HashMap<String, String> params;
    public String URL;
    public boolean Encoder = true;
    public OkHttpUtil.TYPE type = OkHttpUtil.TYPE.GET;
    public File file;

    public void parse(String text,ApiResult result){
        BaseJsonUtil.parseOnlyCode(text,result);
    }


    public static class ApiBodyBuilder{

        private ApiBody apiBody;

        public  ApiBodyBuilder create(){
            apiBody = new ApiBody();
            return this;
        }

        public  ApiBodyBuilder create(@NonNull final OnRequestFinishListener onRequestFinishListener){
            apiBody = new ApiBody(){
                @Override
                public void parse(String text, ApiResult result) {
                    onRequestFinishListener.parse(text,result);
                }
            };
            return this;
        }

        public ApiBodyBuilder createWithJson(){
            apiBody = new ApiBody(){
                @Override
                public void parse(String text, ApiResult result) {
                    super.parse(text, result);
                    result.object = text;
                }
            };
            return this;
        }

        public <T> ApiBodyBuilder createWithJsonList(final Class<T> c){
            apiBody = new ApiBody(){
                @Override
                public void parse(String text, ApiResult result) {
                    JSONObject jsonObject = BaseJsonUtil.parseOnlyCode(text, result);
                    if(result.code==200){
                        JSONArray data = JsonHelper.getJsonArray(jsonObject, "data");
                        result.object = GsonHelper.stringToList(data.toString(), c);
                    }
                }
            };
            return this;
        }

        public  ApiBodyBuilder buildBaseParams(){
            apiBody.params = CommonConfig.apiData.getBaseParams();
            return this;
        }

        public  ApiBodyBuilder buildUserBaseParams(){
            apiBody.params = CommonConfig.apiData.getUserBaseParams();
            return this;
        }

        public ApiBodyBuilder add(String key,String value){
            if(null==apiBody.params){
                apiBody.params = CommonConfig.apiData.getUserBaseParams();
            }
            apiBody.params.put(key,value);
            return this;
        }

        public ApiBodyBuilder url(String url){
            apiBody.URL = url;
            return this;
        }

        public ApiResult executeOnGet(){
            return ApiRequest.executeOnGet(apiBody);
        }

        public ApiResult executeOnPost(){
            return ApiRequest.executeOnPost(apiBody);
        }
    }


}
