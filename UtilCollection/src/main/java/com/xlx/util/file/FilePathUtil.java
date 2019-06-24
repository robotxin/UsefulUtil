package com.xlx.util.file;

/**
 * 
 * @ClassName: FilePathUtil 
 * @Description: 适配linux和windows路径
 * @author: xieleixin
 * @date: 2019年6月24日 下午2:11:50
 */
public class FilePathUtil {
	public static final String FILE_SEPARATOR = System.getProperty("file.separator");
	//public static final String FILE_SEPARATOR = File.separator;
 
	public static String getRealFilePath(String path) {
		return path.replace("/", FILE_SEPARATOR).replace("\\", FILE_SEPARATOR);
	}
 
	public static String getHttpURLPath(String path) {
		return path.replace("\\", "/");
	}
}
