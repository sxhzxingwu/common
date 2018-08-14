package com.android.weici.common.help;

import android.text.TextUtils;

import java.util.HashMap;

/**
 * Created by Mouse on 2018/1/4.
 */

public class WeiciDict {

    private HashMap<String,String> map;

    public WeiciDict(){
        map = new HashMap<>();
    }

    public int getInt(String key){
        String s = map.get(key);
        if(TextUtils.isEmpty(s)){
            return -1;
        }
        return Integer.valueOf(s);
    }

    public String getString(String key){
        return map.get(key);
    }

}
