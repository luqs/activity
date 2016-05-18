package com.cuize.activity.web.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.cuize.activity.web.util.oauth.pojo.OAuth2;

/**
 * 微信OAuth授权工具类
 * @author JackieLan
 *
 */
public class WeiXinOAuthUtil {

	private static final Logger LOG = LoggerFactory.getLogger(WeiXinOAuthUtil.class);
	
   	/**
   	 * 获取微信网页授权地址
   	 * @param userInfo
   	 * @return
   	 */
   	public static String getWeChatOAuthUrl(String appId, String oauthRedirectUri,HttpServletRequest request){
   		
		//String basePath = request.getScheme()+"://"+request.getServerName()+(80==request.getServerPort()?"":":"+request.getServerPort());
   		String basePath = oauthRedirectUri;
		LOG.info("WeiXinOAuthUtil.getWeChatOAuthUrl:basePath={}", basePath);
		
		String redirectUrl = "";
		String queryString = ""; 
		if ("GET".equalsIgnoreCase(request.getMethod())) {
			queryString  =  request.getQueryString();
			redirectUrl = queryString != null ? request.getRequestURI() + "?" + queryString : request.getRequestURI();
		}else{
		 Map<String, String[]> params = request.getParameterMap();  
	        for (String key : params.keySet()) {  
	            String[] values = params.get(key);  
	            for (int i = 0; i < values.length; i++) {  
	                String value = values[i];  
	                queryString += key + "=" + value + "&";  
	            }  
	        } 
	        if(!StringUtils.isEmpty(queryString)){
	        	// 去掉最后一个空格  
	        	queryString = queryString.substring(0, queryString.length() - 1);
	        	redirectUrl = request.getRequestURI() + "?" + queryString;
	        }else{
	        	redirectUrl = request.getRequestURI();
	        } 
		}
		LOG.info("WeiXinOAuthUtil.getWeChatOAuthUrl:redirectUrl={}", redirectUrl);
		return new OAuth2().getOAuth2CodeUrl(appId, basePath+redirectUrl, "snsapi_base", "shakewechat");
   	}
}
