package com.android.weici.common.img;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.android.weici.common.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.concurrent.ExecutionException;

public class GlideImageUtil {
    /**
     * 从 url中加载 圆形 图片
     *
     * @param activity
     * @param imgView
     * @param url
     * @param defRes
     */
    public static void setCircleImageUrl(Context activity, ImageView imgView, String url, int defRes){
        Glide.with(activity).load(url).placeholder(defRes).transform(new GlideCircleTransform(activity)).into(imgView);
    }

    public static void setCircleImageUrl(Context activity, SimpleTarget<GlideDrawable> target, String url, int defRes) {
        Glide.with(activity).load(url).placeholder(defRes).transform(new GlideCircleTransform(activity)).into(target);
    }

    public static void setCircleHeadImageUrl(Context activity,ImageView imgView, String url, int defRes) {
        Glide.with(activity).load(url).placeholder(defRes).transform(new GlideCircleTransform(activity)).override(400,400).into(imgView);
    }

    public static void setCircleImageUrl(Context activity, SimpleTarget<GlideDrawable> target, Bitmap bitmap, int defRes) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes=baos.toByteArray();
        Glide.with(activity).load(bytes).placeholder(defRes).transform(new GlideCircleTransform(activity)).into(target);
    }

    public static void setImageBitmap(ImageView imgView, Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes=baos.toByteArray();
        Glide.with(imgView.getContext()).load(bytes).into(imgView);
    }

    /**
     * 从 url中加载 图片
     * @param imgView
     * @param url
     * @param defRes
     */
    public static void setImageUrl(ImageView imgView, String url, int defRes) {
        Glide.with(imgView.getContext()).load(url).placeholder(defRes).into(imgView);
    }

    public static void setImageRes(ImageView imgView, int imgRes, int defRes) {
        Glide.with(imgView.getContext()).load(imgRes).centerCrop().placeholder(defRes).into(imgView);
    }

    public static void setImageRes(ImageView imgView, int imgRes) {
        Glide.with(imgView.getContext()).load(imgRes).dontAnimate().into(imgView);
    }

    public static void setImageResId(ImageView imgView, int imgRes) {
        Glide.with(imgView.getContext()).load(imgRes).dontAnimate().into(imgView);
    }

    public static void setImageFile(ImageView imgView, String filePath, int defRes) {
        Glide.with(imgView.getContext()).load(filePath).dontAnimate().placeholder(defRes).into(imgView);
    }

    public static void setImageFile(ImageView imgView, String filePath) {
        Glide.with(imgView.getContext()).load(filePath).dontAnimate().into(imgView);
    }

    public static void setImageResNoAnim(ImageView imgView, int imgRes, int defRes) {
        Glide.with(imgView.getContext()).load(imgRes).dontAnimate().centerCrop().placeholder(defRes).into(imgView);
    }

    public static void setImageUrl1(ImageView imgView, String url, int defRes) {
        if (defRes > 0) {
            Glide.with(imgView.getContext()).load(url).placeholder(defRes).dontAnimate().into(imgView);
        } else {
            Glide.with(imgView.getContext()).load(url).dontAnimate().into(imgView);
        }
    }

    public static void setImageUrl(ImageView imgView, String url, int defRes, boolean isAnim) {
        if (isAnim) {
            Glide.with(imgView.getContext()).load(url).placeholder(defRes).into(imgView);
        } else {
            Glide.with(imgView.getContext()).load(url).placeholder(defRes).dontAnimate().into(imgView);
        }
    }

    /**
     * 从 url中加载 图片
     *
     * @param activity
     * @param imgView
     * @param url
     */
    public static void setImageUrl(Activity activity, ImageView imgView, String url) {
        Glide.with(activity).load(url).centerCrop().into(imgView);
    }

    /**
     * 设置圆角图片
     *
     * @param activity
     * @param imgView
     * @param res
     */
    public static void setRoundImageUrl(Activity activity, ImageView imgView, int res) {
        Glide.with(activity).load(res).transform(new GlideRoundTransform(activity, 5)).animate(R.anim.alpha_0_3_to_1).into(imgView);
    }

    public static void setRoundImageUrl(Context activity, ImageView imgView, String url, int defRes) {
        Glide.with(activity).load(url).transform(new CenterCrop(activity), new GlideRoundTransform(activity, 5)).animate(R.anim.alpha_0_3_to_1).placeholder(defRes).into(imgView);
    }

    public static void setRoundImageUrl(Context activity, ImageView imgView, int res, int rote, boolean isAnim) {
        if (isAnim) {
            Glide.with(activity).load(res).transform(new CenterCrop(activity), new GlideRoundTransform(activity, rote)).into(imgView);
        } else {
            Glide.with(activity).load(res).transform(new CenterCrop(activity), new GlideRoundTransform(activity, rote)).dontAnimate().into(imgView);
        }
    }

    /**
     * 从 文件路径中加载 圆形 图片
     *
     * @param activity
     * @param imgView
     * @param path
     * @param defRes
     */
    public static void setCircleImageFileNoCache(Activity activity, ImageView imgView, String path, int defRes) {
        Glide.with(activity).load(Uri.fromFile(new File(path))).placeholder(defRes)
                .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(new GlideCircleTransform(activity)).into(imgView);
    }

    public static void loadGif(Activity activity, int res, ImageView imgView) {
        Glide.with(activity).load(res).asGif().into(imgView);
    }

    public static Bitmap getBitmap(String url, Context context) throws ExecutionException, InterruptedException {
        return Glide.with(context.getApplicationContext()).load(url).asBitmap().into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
    }

    public static Bitmap getBitmap(String url, Context context, int width, int height) throws ExecutionException, InterruptedException {
        return Glide.with(context.getApplicationContext()).load(url).asBitmap().into(width, height).get();
    }

    public static void setImageUrl(Context activity, final ImageView imgView, String file) {
        Glide.with(activity)
                .load(file).into(new SimpleTarget<GlideDrawable>() { // 加上这段代码 可以解决
                    @Override
                    public void onResourceReady(GlideDrawable resource,
                                                GlideAnimation<? super GlideDrawable> glideAnimation) {
                        imgView.setImageDrawable(resource); //显示图片
                    }

                });
    }


}
