package com.xlx.util.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.log4j.Logger;
/**
 * 
 * @ClassName: ZipUtil 
 * @Description: 解压ZIP文件
 * @author: xieleixin
 * @date: 2018年10月30日 下午1:45:07
 * throws Exception
 */
public class ZipUtil {
	private static Logger log = Logger.getLogger(ZipUtil.class.getName()); 
	
	public static final int BUFFER_SIZE = 1024;
    /**
     * 解压zip格式的压缩文件到指定位置
     * 
     * @param zipFileName 压缩文件
     * @param extPlace 解压目录
     * @param saveFilePlace 解压缩后文件保存的位置
     * @throws Exception
     */
    public static void unZipFiles(String zipFileName, String extPlace,String saveFilePlace)
             {
            File f = new File(extPlace + zipFileName);
            if ((!f.exists()) && (f.length() <= 0)) {
            	log.info("要解压的文件不存在!");
            }else{
                try(
                		//
                		ZipFile zipFile = new ZipFile(extPlace + zipFileName,Charset.forName("gbk")); // 注意一定要传入绝对路径否则解压不了
                ){
                	
                    String strPath, gbkPath, strtemp;
                    log.info(saveFilePlace);
                    File tempFile = new File(saveFilePlace);
                    log.info("tempFile is Exist 新建解压文件" + tempFile);
                    strPath = tempFile.getAbsolutePath();
                    Enumeration<?> e = zipFile.entries();
                    while (e.hasMoreElements()) {
                        ZipEntry zipEnt = (ZipEntry) e.nextElement();
                        ZipEntry zipEntry=new ZipEntry(saveFilePlace);
                        
                    	byte[] titleName = zipEnt.getName().getBytes("utf-8");
                    	gbkPath = new String(titleName, "utf-8");
//                        gbkPath = zipEnt.getName();
                        if (zipEnt.isDirectory()) {
                            strtemp = strPath + File.separator + gbkPath;
                            File dir = new File(strtemp);
                            dir.mkdirs();
                            continue;
                        } else {
                            // 读写文件
                            InputStream is = zipFile.getInputStream(zipEnt);
                            BufferedInputStream bis = new BufferedInputStream(is);
//                            gbkPath = zipEnt.getName();
                            strtemp = strPath + File.separator + gbkPath;
                            // 建目录
//                            String strsubdir = gbkPath;
//                            for (int i = 0; i < strsubdir.length(); i++) {
//                                if (strsubdir.substring(i, i + 1)
//                                        .equalsIgnoreCase("/")) {
//                                    String temp = strPath + File.separator
//                                            + strsubdir.substring(0, i);
//                                    File subdir = new File(temp);
//                                    if (!subdir.exists())
//                                        subdir.mkdir();
//                                }
//                            }
                            OutputStream os = null;
                            try {
                                os = new BufferedOutputStream(new FileOutputStream(new File(strtemp)), BUFFER_SIZE);
                                IOUtils.copy(is, os);
                            } finally {
                                IOUtils.closeQuietly(os);
                            }
 
                        }
                    }
                    log.info("文件解压成功");
                }
                catch (Exception e) {
        			log.error("解压文件失败", e);
        		}
            }
    }
//    public static void main(String[] args) {
//    	String zipFileName="444.zip";
//    	String extPlace="F:/ali/";
//    	String dateTime="20180910";
//    	String saveFilePlace="F:/ali/"+dateTime+"/";
//    	try {
//			unZipFiles(zipFileName,extPlace,saveFilePlace);
//		} 
//    	catch (Exception e) {
//			log.error("系统找不到指定的文件 !", e);
//		}
//	}
    

}
