package com.cuize.activity.web.util;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 获取request中的json参数数据
 * @author JackieLan
 *
 */
public class HttpRequestJsonPrameterUtil {

	public static String getRestData(HttpServletRequest request){
		if (null == request) {
			return null;
		}

		String method = request.getMethod();
		String ret = null;
		if ((method.equalsIgnoreCase("GET")) || (method.equalsIgnoreCase("DELETE"))) {
			ret = request.getQueryString();
		} else
			try {
				ret = getBodyData(request);
			} catch (Exception e) {
			}

		if (null == ret) {
			return null;
		}

		
		return ret;
	}
	
	private static String getBodyData(HttpServletRequest request) throws Exception {
		StringBuffer data = new StringBuffer();
		String line = null;
		BufferedReader reader = null;
		try {
			reader =request.getReader();
			while (null != (line = reader.readLine()))
				data.append(line);
		} catch (IOException e) {
			e = e;

			throw new Exception(e.getMessage(), e.getCause());
		} finally {
		}
		return data.toString();
	}
	
}
