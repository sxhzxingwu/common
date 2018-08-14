package com.android.weici.common.help;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by Mouse on 2018/7/10.
 */

public class UserHeadHelper {

    public static String saveUsrHeadBitmap(Context context, Bitmap imgBitmap, String fileName) {
        if(null==imgBitmap){
            return "";
        }
        String path = "/data/data/" + context.getPackageName() + "/temp";
        fileName = fileName + "_head.png";
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        File cacheFile = new File(path, fileName);
        if (cacheFile.exists() && cacheFile.isFile()) {
            cacheFile.delete();
        }

        if (save(imgBitmap, cacheFile.getPath())) {
            return cacheFile.getPath();
        }
        return "";
    }

    public static boolean save(Bitmap bitmap, String path) {
        if (bitmap == null || path == null)
            return false;
        OutputStream out = null;
        try {
            out = new FileOutputStream(path);
            Bitmap.CompressFormat format = null;
            String ext = getFileExt(path);
            if (ext != null) {
                if (ext.equals("jpg")) {
                    format = Bitmap.CompressFormat.JPEG;
                } else if (ext.equals("png")) {
                    format = Bitmap.CompressFormat.PNG;
                }
            }
            if (format == null) {
                format = Bitmap.CompressFormat.PNG;
            }
            return bitmap.compress(format, 100, out);
        } catch (FileNotFoundException e) {
        } catch (Throwable t) {

        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                }
            }
        }
        return false;
    }

    public static String getFileExt(String path) {
        if (path == null)
            return null;
        int index = path.lastIndexOf(".");
        if (index >= 0) {
            return path.substring(index + 1, path.length()).toLowerCase();
        }
        return null;
    }

}
