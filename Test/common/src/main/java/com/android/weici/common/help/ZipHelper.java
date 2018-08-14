package com.android.weici.common.help;

import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;


public class ZipHelper {

//	public static boolean unrar(File src, String unrarPath) {
//
//		try {
//			Archive rarFile = new Archive(src);
//			FileHeader fh = null;
//			int total = rarFile.getFileHeaders().size();
//			Log.d("test_string", "total:" + total);
//			for (int i = 0; i < total; i++) {
//				fh = rarFile.getFileHeaders().get(i);
//				String entryPath = null;
//				if (fh.isUnicode()) {
//					entryPath = fh.getFileNameW().trim();
//				} else {
//					entryPath = fh.getFileNameString().trim();
//				}
//				entryPath = entryPath.replace("\\", "/");
//				String path = unrarPath + "/" + entryPath;
//				Log.d("test_path", path);
//				File file = new File(path);
//				if (fh.isDirectory()) {
//					file.mkdirs();
//				} else {
//					File parent = file.getParentFile();
//					if (parent != null && !parent.exists()) {
//						parent.mkdirs();
//					}
//					FileOutputStream fileout = new FileOutputStream(file);
//					rarFile.extractFile(fh, fileout);
//					fileout.close();
//				}
//			}
//			rarFile.close();
//			return true;
//		} catch (RarException e) {
//			Log.d("test_path", "error:" + e.getMessage());
//			e.printStackTrace();
//		} catch (IOException e) {
//			Log.d("test_path", "error:" + e.getMessage());
//			e.printStackTrace();
//		}
//		return false;
//	}

    public void UnZipFolder(final String srcpath, final String outPathString, final UnZipCallback callback) throws IOException {

        File srcfile = new File(srcpath);
        ZipInputStream inZip = new ZipInputStream(new FileInputStream(srcfile));
        ZipEntry zipEntry;
        String szName = "";
        long t = System.currentTimeMillis();
        while ((zipEntry = inZip.getNextEntry()) != null) {
            szName = zipEntry.getName();
            if (null != callback && System.currentTimeMillis() - t >= 100) {
                t = System.currentTimeMillis();
                callback.onUnZipFileCallback(szName);
            }

            if (zipEntry.isDirectory()) {
                szName = szName.substring(0, szName.length() - 1);
                File folder = new File(outPathString + File.separator + szName);
                folder.mkdirs();
            } else {
                String[] split = szName.split("/");
                szName = split[split.length - 1];
                String path = outPathString + File.separator + szName;
                File file = new File(path);
                //file.createNewFile();
                FileOutputStream out = new FileOutputStream(file);
                int len;
                byte[] buffer = new byte[1024];

                while ((len = inZip.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                    out.flush();
                }
                out.close();
            }
        }
        inZip.close();
        if (callback != null) {
            callback.onUnZipFinish();
        }
    }

    private float lastProgress = 0;

    private void updateProgress(float progress, UnZipCallback listener) {
        /* 因为会频繁的刷新,这里我只是进度>1%的时候才去显示 */
        if (progress - lastProgress > 1) {
            lastProgress = progress;
            listener.onUnZipFileCallback((int) progress + "%");
        }
    }

    /**
     * 获取压缩包解压后的内存大小
     *
     * @param filePath 文件路径
     * @return 返回内存long类型的值
     */
    private long getZipTrueSize(String filePath) {
        long size = 0;
        ZipFile f;
        try {
            f = new ZipFile(filePath);
            Enumeration<? extends ZipEntry> en = f.entries();
            while (en.hasMoreElements()) {
                size += en.nextElement().getSize();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }

    public interface UnZipCallback {
        void onUnZipFileCallback(String name);

        void onUnZipFinish();
    }


    public void UnZipFolderWithFile(final String srcpath, final String outPathString, final UnZipCallback callback) throws IOException {

        File srcfile = new File(srcpath);
        ZipInputStream inZip = new ZipInputStream(new FileInputStream(srcfile));
        ZipEntry zipEntry;
        String szName = "";
        long t = System.currentTimeMillis();
        while ((zipEntry = inZip.getNextEntry()) != null) {
            szName = zipEntry.getName();
            if (null != callback && System.currentTimeMillis() - t >= 100) {
                t = System.currentTimeMillis();
                callback.onUnZipFileCallback(szName);
            }

            if (zipEntry.isDirectory()) {

                int i = szName.indexOf("/");
                if (i >= 0 && i + 1 < szName.length()){
                    szName = szName.substring(i + 1);
                    File folder = new File(outPathString + File.separator + szName);
                    folder.mkdirs();
                }
//                szName = szName.substring(0, szName.length() - 1);
            } else {
                int i = szName.indexOf("/");
                if (i >= 0 && i + 1 < szName.length()) {
                    szName = szName.substring(i + 1);
                    String path = outPathString + File.separator + szName;


                    File file = new File(path);
                    file.createNewFile();
                    FileOutputStream out = new FileOutputStream(file);
                    int len;
                    byte[] buffer = new byte[1024];

                    while ((len = inZip.read(buffer)) != -1) {
                        out.write(buffer, 0, len);
                        out.flush();
                    }
                    out.close();
                }
            }
        }
        inZip.close();
        if (callback != null) {
            callback.onUnZipFinish();
        }
    }

    public static void Unzip4j(String zipFile,String destPath) throws ZipException {
        long startTime = System.currentTimeMillis();
        //第一时间设置编码格式
        net.lingala.zip4j.core.ZipFile zip = new net.lingala.zip4j.core.ZipFile(zipFile);
        zip.setFileNameCharset("GBK");
        //用自带的方法检测一下zip文件是否合法，包括文件是否存在、是否为zip文件、是否被损坏等
        if (!zip.isValidZipFile()) {
            throw new ZipException("文件不合法或不存在");
        }
        // 跟java自带相比，这里文件路径会自动生成，不用判断
        zip.extractAll(destPath);
        System.out.println("解压成功！");
        long endTime = System.currentTimeMillis();
        System.out.println("耗时：" + (endTime - startTime) + "ms");
    }


}
