package com.xlx.util.file;

import java.io.File;
import java.util.ArrayList;

/**
 * 
 * @ClassName: ReadFilesUtil 
 * @Description: 读取指定目录下的所有文件名
 * @author: xieleixin
 * @date: 2018年8月24日 下午2:50:11
 */
public class ReadFilesUtil {
	public static ArrayList<String> getFiles(String path) {
	    ArrayList<String> files = new ArrayList<String>();
	    File file = new File(path);
	    File[] tempList = file.listFiles();

	    for (int i = 0; i < tempList.length; i++) {
	        if (tempList[i].isFile()) {
	           //   System.out.println("文     件：" + tempList[i]);
	            files.add(tempList[i].toString());
	        }
	        if (tempList[i].isDirectory()) {
	         //     System.out.println("文件夹：" + tempList[i]);
	        }
	    }
	    return files;
	}
	
//	public static void main(String[] args) {
//		String path = "D:/";
//		getFiles(path);
//	}
}
