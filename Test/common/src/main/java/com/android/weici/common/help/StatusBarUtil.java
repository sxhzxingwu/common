package com.android.weici.common.help;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.android.weici.common.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Mouse on 2017/10/27.
 */

public class StatusBarUtil {

    private static final int SYSTEM_UI_FLAG_LIGHT_STATUS_BAR = 1 << 13;

    public static int setStatusbarTextColor(Activity var0) {
        byte var1 = 0;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isMiUI(var0, true)) {
                var1 = 1;
            } else if (isMeizu(var0.getWindow(), true)) {
                var1 = 2;
            } else {
                if (Build.VERSION.SDK_INT >= 23) {
                    var0.getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    var1 = 3;
                }
            }
        }

        return var1;
    }


    public static void setStatusBarColor(Activity var0, @ColorInt int var1) {
        if (Build.VERSION.SDK_INT >= 21) {
            if (Build.VERSION.SDK_INT >= 23&&isMiUI(var0, true)) {
                return;
            }
            var0.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            var0.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            var0.getWindow().setStatusBarColor(calculateStatusColor(var1, 0));
        } else if (Build.VERSION.SDK_INT >= 19) {
            var0.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup var2 = (ViewGroup) var0.getWindow().getDecorView();
            View var3 = var2.findViewById(R.id.statusbarutil_translucent_view);
            if (var3 != null) {
                if (var3.getVisibility() == View.GONE) {
                    var3.setVisibility(View.VISIBLE);
                }
                var3.setBackgroundColor(calculateStatusColor(var1, 0));
            } else {
                var2.addView(createStatusBarView(var0, var1));
            }
            setRootView(var0);
        }
    }


    private static int calculateStatusColor(@ColorInt int color, int alpha) {
        if (alpha == 0) {
            return color;
        }
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }

    private static void setRootView(Activity var0) {
        ViewGroup var1 = (ViewGroup) var0.findViewById(android.R.id.content);
        int var2 = 0;
        for (int var3 = var1.getChildCount(); var2 < var3; ++var2) {
            View childView = var1.getChildAt(var2);
            if (childView instanceof ViewGroup) {
                childView.setFitsSystemWindows(true);
                ((ViewGroup) childView).setClipToPadding(true);
            }
        }

    }

    private static View createStatusBarView(Activity activity, @ColorInt int var1) {
        View statusBarView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(calculateStatusColor(var1, 0));
        statusBarView.setId(R.id.statusbarutil_fake_status_bar_view);
        return statusBarView;
    }

    private static int getStatusBarHeight(Context var0) {
        int var1 = var0.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return var0.getResources().getDimensionPixelSize(var1);
    }

    private static boolean isMiUI(Activity activity, boolean var1) {
        boolean var2 = false;
        Window var3 = activity.getWindow();
        if (var3 != null) {
            Class var4 = var3.getClass();

            try {
                boolean var5 = false;
                Class var6 = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field var7 = var6.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                int var10 = var7.getInt(var6);
                Method var8 = var4.getMethod("setExtraFlags", new Class[]{Integer.TYPE, Integer.TYPE});
                if (var1) {
                    var8.invoke(var3, new Object[]{Integer.valueOf(var10), Integer.valueOf(var10)});
                } else {
                    var8.invoke(var3, new Object[]{Integer.valueOf(0), Integer.valueOf(var10)});
                }

                var2 = true;
                Window window = activity.getWindow();
                if (Build.VERSION.SDK_INT >= 23) {
                    if(var1){
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                        window.setStatusBarColor(Color.WHITE);
                    }else{
                        int flag = activity.getWindow().getDecorView().getSystemUiVisibility()
                                & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                        window.getDecorView().setSystemUiVisibility(flag);
                    }
                }
            } catch (Exception var9) {

            }
        }

        return var2;
    }

    private static boolean isMeizu(Window var0, boolean var1) {
        boolean var2 = false;
        if (var0 != null) {
            try {
                android.view.WindowManager.LayoutParams var3 = var0.getAttributes();
                Field var4 = android.view.WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field var5 = android.view.WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                var4.setAccessible(true);
                var5.setAccessible(true);
                int var6 = var4.getInt((Object) null);
                int var7 = var5.getInt(var3);
                if (var1) {
                    var7 |= var6;
                } else {
                    var7 &= ~var6;
                }

                var5.setInt(var3, var7);
                var0.setAttributes(var3);
                var2 = true;
            } catch (Exception var8) {
                ;
            }
        }

        return var2;
    }

    @TargetApi(23)
    private static int changeStatusBarModeRetainFlag(Window window, int out) {
        out = retainSystemUiFlag(window, out, View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        out = retainSystemUiFlag(window, out, View.SYSTEM_UI_FLAG_FULLSCREEN);
        out = retainSystemUiFlag(window, out, View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        out = retainSystemUiFlag(window, out, View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        out = retainSystemUiFlag(window, out, View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        out = retainSystemUiFlag(window, out, View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        return out;
    }

    public static int retainSystemUiFlag(Window window, int out, int type) {
        int now = window.getDecorView().getSystemUiVisibility();
        if ((now & type) == type) {
            out |= type;
        }
        return out;
    }

}
