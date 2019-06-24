package com.xlx.util.file;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CreateFile {

	private final Logger LOGGER = LoggerFactory.getLogger(CreateFile.class);
	/**
	 * 
	 * @Title: createStoreFile 
	 * @Description: 创建文件
	 * @param filePath
	 * @return
	 * @return: File
	 */
	  protected File createStoreFile(String filePath) {
		    //create file
		    File file = new File(filePath);
		    if(!file.exists()) {
		      try {
		        boolean createNewFile = file.createNewFile();
		        if(createNewFile) {
		        	LOGGER.info("创建文件成功");
		        }else {
		        	LOGGER.info("创建文件失败");
		        }
		      } catch (IOException e) {
		        LOGGER.error("创建本地文件失败", e);
		        return null;
		      }
		    }
		    return file;
		  }
}
