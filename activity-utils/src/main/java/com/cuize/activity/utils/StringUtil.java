package com.cuize.activity.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class StringUtil {
	
	
	public static final byte[] readBytes(InputStream is, int contentLen) throws IOException{
		if (contentLen > 0) {
			int readLen = 0;
			int readLengthThisTime = 0;
			byte[] message = new byte[contentLen];
			try {
				while (readLen != contentLen) {
					readLengthThisTime = is.read(message, readLen, contentLen
							- readLen);
					if (readLengthThisTime == -1) {
						// Should not happen.
						break;
					}
					readLen += readLengthThisTime;
				}
				return message;
			} catch (IOException e) {
				LoggerUtil.error(e);
				throw e;
			}
		}

		return new byte[] {};
	}
	
	public static String getSubString(String s, int n) {
		StringBuffer buffer = new StringBuffer();
        try{
        	if (s != null && "".equals(s)) {
                s = new String(s.getBytes(), "UTF-8");
            }
            
            if (n != 0 && n < s.getBytes("UTF-8").length) {
                for (int i = 0; n > 0; i++) {
                    char c = s.charAt(i);
                    if (isChineseChar(c)) {
                        n = n-3;
                    }else {
                    	n = n-1;
                    }
                    if(n >= 0){
                    	buffer.append(c);
                    }
                }
            }
        } catch(Exception ex){
        	LoggerUtil.error(ex);
        }
        
        return buffer.toString();
    }
 
    private static boolean isChineseChar(char c) throws UnsupportedEncodingException {
    	 return String.valueOf(c).getBytes("UTF-8").length > 1;
    }
    /**
	 * 判断字符串是否为空.
	 * 
	 * @param src
	 * @return
	 */
	public static boolean isEmpty(final String src) {
		if (null == src || "".equals(src)||"".equals(src.trim())) {
			return true;
		}
		return false;
	}
	
	/**  
	 * 这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。  
	 * @param src byte[] data  
	 * @return hex string  
	 */       
	public static String bytesToHexString(byte[] src) throws Exception{   
		StringBuilder stringBuilder = new StringBuilder("");   
		if (src == null || src.length <= 0) {   
			return "";   
		}   
		for (int i = 0; i < src.length; i++) {   
			int v = src[i] & 0xFF;   
			String hv = Integer.toHexString(v);   
			if (hv.length() < 2) {   
			stringBuilder.append(0);   
			}   
			stringBuilder.append(hv);   
		}   
		return stringBuilder.toString();   
	}
	
	/**  
	 * 16进制字符串转byte数组
	 * @param hexString the hex string  
	 * @return byte[]  
	 */  
	public static byte[] hexStringToBytes(String hexString) throws Exception{   
		if (hexString == null || hexString.equals("")) {   
			return null;   
		}
		if (hexString.length() %2 != 0) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] resultBytes = new byte[length];   
		for (int i = 0; i < length; i++) {   
			int pos = i * 2;   
			resultBytes[i] = (byte) (charToBytePos(hexChars[pos]) << 4 | charToBytePos(hexChars[pos + 1]));   
		}
		return resultBytes;   
	}
	
	/**
	 * 16进制字符对应的10进制字节
	 * @param c
	 * @return
	 */
	private static byte charToBytePos(char c) {   
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
}
