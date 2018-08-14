package com.android.weici.common.help;

import android.content.Context;
import android.view.ViewGroup;

import jp.wasabeef.blurry.Blurry;

/**
 * Created by Mouse on 2018/5/8.
 */

public class BlurryHelper {

    public static void blurryViewGoup(Context context, ViewGroup viewGroup) {
        blurryViewGoup(context, viewGroup, 25, 2);
    }

    public static void blurryViewGoup(Context context, ViewGroup viewGroup, int radius, int sampling) {
        Blurry.with(context).radius(radius).sampling(sampling).onto(viewGroup);
    }

    public static void blurryViewGoup(Context context, ViewGroup viewGroup, int radius) {
        Blurry.with(context).radius(radius).onto(viewGroup);
    }


    //异步
    public static void blurryViewGoup(Context context, ViewGroup viewGroup, int radius, int sampling, int color) {
        Blurry.with(context).radius(10).sampling(8).color(color).async().animate(500).onto(viewGroup);

    }

}
