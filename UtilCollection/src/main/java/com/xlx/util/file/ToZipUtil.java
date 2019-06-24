package com.xlx.util.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

public class ToZipUtil {

	private static Logger log = Logger.getLogger(ToZipUtil.class.getName()); 
	
	public static final int BUFFER_SIZE = 1024;
    /**s
     * 压缩文件
     * @param srcFilePath 压缩源路径
     * @param destFilePath 压缩目的路径
     */
    public static void compress(String srcFilePath, String destFilePath) {
        //
    	
        File src = new File(srcFilePath);
 
        if (!src.exists()) {
            throw new RuntimeException(srcFilePath + "不存在");
        }
        File zipFile = new File(destFilePath);
 
        try (
        		FileOutputStream fos = new FileOutputStream(zipFile);
        		){
            ZipOutputStream zos = new ZipOutputStream(fos);
            String baseDir = "";
            compressbyType(src, zos, baseDir);
            zos.close();
 
        } catch (Exception e) {
        	log.error("文件流读取失败", e);
        }
    }
    /**
     * 按照原路径的类型就行压缩。文件路径直接把文件压缩，
     * @param src
     * @param zos
     * @param baseDir
     */
     private static void compressbyType(File src, ZipOutputStream zos,String baseDir) {
 
            if (!src.exists())
                return;
            
            log.info("压缩路径" + baseDir + src.getName());
            //判断文件是否是文件，如果是文件调用compressFile方法,如果是路径，则调用compressDir方法；
            if (src.isFile()) {
                //src是文件，调用此方法
                compressFile(src, zos, baseDir);
                 
            } else if (src.isDirectory()) {
                //src是文件夹，调用此方法
                compressDir(src, zos, baseDir);
 
            }
 
        }
      
        /**
         * 压缩文件
        */
        private static void compressFile(File file, ZipOutputStream zos,String baseDir) {
            if (!file.exists())
                return;
            try (
            		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            		){
                ZipEntry entry = new ZipEntry(baseDir + file.getName());
                zos.putNextEntry(entry);
                int count;
                byte[] buf = new byte[BUFFER_SIZE];
                while ((count = bis.read(buf)) != -1) {
                    zos.write(buf, 0, count);
                }
                bis.close();
 
            } catch (Exception e) {
            	log.error("文件压缩失败", e);
            }
        }
         
        /**
         * 压缩文件夹
         */
        private static void compressDir(File dir, ZipOutputStream zos,String baseDir) {
            if (!dir.exists())
                return;
            File[] files = dir.listFiles();
            if(files.length == 0){
                try {
                    zos.putNextEntry(new ZipEntry(baseDir + dir.getName()+File.separator));
                } catch (IOException e) {
                    log.error("压缩文件夹失败", e);
                }
            }
            for (File file : files) {
                compressbyType(file, zos, baseDir + dir.getName() + File.separator);
            }
    }
        
        /**
         * @param args 主方法
         */
//        public static void main(String[] args) {
//            // TODO Auto-generated method stub
//            //第一个参数是需要压缩的源路径；第二个参数是压缩文件的目的路径，这边需要将压缩的文件名字加上去
//        	String realSrcFilePath = FilePathUtil.getRealFilePath("F:/456");
//        	String realDestFilePath = FilePathUtil.getRealFilePath("F:/456.zip");
//            compress(realSrcFilePath,realDestFilePath);
//        }
}
