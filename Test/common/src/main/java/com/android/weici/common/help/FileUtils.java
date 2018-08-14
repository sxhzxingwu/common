package com.android.weici.common.help;

import java.io.File;

public class FileUtils {

	public static boolean isExist(String path) {
		if (path == null || path.equals(""))
			return false;
		return new File(path).exists();
	}

	public static boolean renameToDest(String destPath,String srcPath ){
		
		File file = new File(srcPath);
		if(!file.exists()){
			return false;
		}
		File file2 = new File(destPath);
		String parent = file2.getParent();
		File dir = new File(parent);
		if(!dir.exists()){
			dir.mkdirs();
		}
		return file.renameTo(new File(destPath));
	}

	public static String getFilenameForUrl(String key) {  
	    int firstHalfLength = key.length() / 2;  
	    String localFilename = String.valueOf(key.substring(0, firstHalfLength).hashCode());  
	    localFilename += String.valueOf(key.substring(firstHalfLength).hashCode());  
	    return localFilename;  
	}

	public static String getFilename(String path) {
		int index = path.lastIndexOf("/");
		return path.substring(index + 1);
	}
	
}
