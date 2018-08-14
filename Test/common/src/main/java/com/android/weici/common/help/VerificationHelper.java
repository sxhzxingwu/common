package com.android.weici.common.help;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Mouse on 2017/11/6.
 */

public class VerificationHelper {

    public static boolean isAvalidPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        }
        String reg = "^1\\d{10}$";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public static boolean containEmoji(String string) {
        if(TextUtils.isEmpty(string)){
            return false;
        }
        Pattern p = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(string);
        return m.find();
    }

    public static boolean isAvalidIdCard(String idCard) {
        if (TextUtils.isEmpty(idCard)) {
            return false;
        }
        if (idCard.length() != 15 && idCard.length() != 18) {
            return false;
        }
        return true;
    }

    public static boolean isAvalidEmail(String email) {
        String str = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
