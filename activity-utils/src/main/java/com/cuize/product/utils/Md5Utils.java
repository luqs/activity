package com.cuize.product.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.security.MessageDigest;

import org.apache.commons.io.IOUtils;

/**
 * MD5工具类
 * @author zhg8260
 *
 */
public class Md5Utils {

	/**
	 * 获取文件的MD5摘要
	 * @param sourceFile
	 * @param md5Key
	 * @return
	 * @throws Exception
	 */
	public static final String getFileMD5String(File sourceFile, String md5Key) throws Exception{
		  FileInputStream in = null;
		  BufferedReader bufread = null;
	      try {
	    	  MessageDigest messageDigest = MessageDigest.getInstance("MD5");
	    	  in = new FileInputStream(sourceFile);
	 		  bufread = new BufferedReader(new InputStreamReader(in, "GBK"));
    		  byte[] csvFileNameBytes  = IOUtils.toByteArray(bufread, "GBK");
	          messageDigest.update(md5Key.getBytes("GBK"));
	          messageDigest.update(csvFileNameBytes);
	         return StringUtil.bytesToHexString(messageDigest.digest());
	      } catch (Exception e) {
	         throw e;
	      } finally {
	    	  try{
        		if(bufread != null){
        			bufread.close();
        		}
        		if(in != null){
        			in.close();
        		}    		
	        	}catch(Exception e){
	        		throw e;
	        	}
	      }
	}
	
	/**
	 * 验证MD5摘要
	 * @param sourceFile
	 * @param digestFile
	 * @param md5Key
	 * @return
	 */
	public static final boolean validateDigest(File sourceFile, File digestFile, String md5Key) throws Exception{
		 BufferedReader reader = null;
		try {
			 reader = new BufferedReader(new FileReader(digestFile));
	    	// 读取一行
            String str = null;
            StringBuffer strBuffer = new StringBuffer();
            while ((str = reader.readLine()) != null){
                strBuffer.append(str);
            } 
	        return Md5Utils.getFileMD5String(sourceFile, md5Key).equalsIgnoreCase(strBuffer.toString());
	      } catch (Exception e) {
	    	  throw e;
	      } finally {
	    	  try{
	    		  if(reader != null){
	    			  reader.close();
	    		  }    		
	          }catch(Exception e){
	        	  throw e;
	          }
	      }
	}
}
