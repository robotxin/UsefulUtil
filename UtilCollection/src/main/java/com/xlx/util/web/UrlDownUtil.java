package com.xlx.util.web;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;
/**

     * TODO 下载文件到本地
     * @author nadim  
     * @date Sep 11, 2015 11:45:31 AM
     * @param fileUrl 远程地址
     * @param fileLocal 本地路径
     * @throws Exception 
     */
public class UrlDownUtil {
	private static Logger log = Logger.getLogger(UrlDownUtil.class.getName()); 
	public static boolean downloadFile(String fileUrl, String fileLocal)
			throws Exception {
		boolean flag = false;
		URL url = new URL(fileUrl);
		HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
		urlCon.setConnectTimeout(6000);
		urlCon.setReadTimeout(6000);
		int code = urlCon.getResponseCode();
		if (code != HttpURLConnection.HTTP_OK) {
			throw new Exception("文件读取失败");
		}
		// 读文件流
		DataInputStream in = new DataInputStream(urlCon.getInputStream());
		try(DataOutputStream out = new DataOutputStream(new FileOutputStream(
				fileLocal))){
			byte[] buffer = new byte[2048];
			int count = 0;
			while ((count = in.read(buffer)) > 0) {
				out.write(buffer, 0, count);
			}
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}

			} catch (Exception e) {
				log.error("文件为空", e);
				return flag;
			}
		}catch (Exception e1) {
        	log.error("try buffer reader error", e1);
        	return flag;
        }
		
		
		flag = true;
		return flag;
	}

//	public static void main(String[] args) {
//		String fileUrl = "http://cachefly.cachefly.net/100mb.test";
//		String fileLocal = "E:\\test";
//
//		try {
//			downloadFile(fileUrl, fileLocal);
//		} catch (Exception e) {
//			log.error("下载文件失败", e);
//		}
//	}

}
