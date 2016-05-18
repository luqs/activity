/*
 * 微信公众平台(JAVA) SDK
 *
 * Copyright (c) 2014, Ansitech Network Technology Co.,Ltd All rights reserved.
 * 
 * http://www.wechat.org/sdk/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cuize.activity.web.util.oauth;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessControlException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 微信平台调用基础配置
 *
 * <p>
 * 如果存在wechat.properties,则加载属性文件中配置的参数</p>
 *
 * @author wechat<wechat@ansitech.com>
 */
public class Configuration {
	
	private static final Logger logger  =  LoggerFactory.getLogger(Configuration.class);
	
	// 状态码 成功 失败
	public final static String RETURN_CODE_SUCCESS = "SUCCESS";
	public final static String RETURN_CODE_FAIL = "FAIL";
	public final static String RETURN_MSG_OK = "/error";
	public final static String RETURN_CODE_STR = "return_code";
	public final static String RETURN_MSG_STR = "return_msg";
	
	// 交易类型
	public final static String TRADE_TYPE_JSAPI = "JSAPI";

    private static Properties defaultProperty;

    static {
        init();
    }

    static void init() {
        //初始化默认配置
        defaultProperty = new Properties();
        defaultProperty.setProperty("wechat.debug", "true");
        defaultProperty.setProperty("wechat.token", "wechatCourse");
        defaultProperty.setProperty("wechat.http.connectionTimeout", "1000");
        defaultProperty.setProperty("wechat.http.readTimeout", "1000");
        defaultProperty.setProperty("wechat.http.retryCount", "3");
        //读取自定义配置
        String t4jProps = "wechat.properties";
        boolean loaded = loadProperties(defaultProperty, "." + File.separatorChar + t4jProps)
                || loadProperties(defaultProperty, Configuration.class.getResourceAsStream("/WEB-INF/" + t4jProps))
                || loadProperties(defaultProperty, Configuration.class.getClassLoader().getResourceAsStream(t4jProps));
        if (!loaded) {
            logger.info("wechat:没有加载到wechat.properties属性文件!");
        }
    }

    /**
     * 加载属性文件
     *
     * @param props 属性文件实例
     * @param path 属性文件路径
     * @return 是否加载成功
     */
    private static boolean loadProperties(Properties props, String path) {
        try {
            File file = new File(path);
            if (file.exists() && file.isFile()) {
                props.load(new FileInputStream(file));
                return true;
            }
        } catch (IOException ignore) {
            //异常忽略
            ignore.printStackTrace();
        }
        return false;
    }

    /**
     * 加载属性文件
     *
     * @param props 属性文件实例
     * @param is 属性文件流
     * @return 是否加载成功
     */
    private static boolean loadProperties(Properties props, InputStream is) {
        try {
            if (is != null) {
                props.load(is);
                return true;
            }
        } catch (IOException ignore) {
            //异常忽略
            ignore.printStackTrace();
        }
        return false;
    }

    /**
     * 获取开发者第三方用户唯一凭证
     *
     * @return 第三方用户唯一凭证
     */
    public static String getOAuthAppId() {
        return getProperty("wechat.oauth.appid");
    }

    /**
     * 获取开发者第三方用户唯一凭证
     *
     * @param appid 默认第三方用户唯一凭证
     * @return 第三方用户唯一凭证
     */
    public static String getOAuthAppId(String appid) {
        return getProperty("wechat.oauth.appid", appid);
    }

    /**
     * 获取开发者第三方用户唯一凭证密钥
     *
     * @return 第三方用户唯一凭证密钥
     */
    public static String getOAuthSecret() {
        return getProperty("wechat.oauth.secret");
    }

    /**
     * 获取开发者第三方用户唯一凭证密钥
     *
     * @param secret 默认第三方用户唯一凭证密钥
     * @return 第三方用户唯一凭证密钥
     */
    public static String getOAuthSecret(String secret) {
        return getProperty("wechat.oauth.secret", secret);
    }
    
    /**
     * 获取 连接超时时间
     *
     * @return 连接超时时间
     */
    public static int getConnectionTimeout() {
        return getIntProperty("wechat.http.connectionTimeout");
    }

    /**
     * 获取 连接超时时间
     *
     * @param connectionTimeout 默认连接超时时间
     * @return 连接超时时间
     */
    public static int getConnectionTimeout(int connectionTimeout) {
        return getIntProperty("wechat.http.connectionTimeout", connectionTimeout);
    }

    /**
     * 获取 请求超时时间
     *
     * @return 请求超时时间
     */
    public static int getReadTimeout() {
        return getIntProperty("wechat.http.readTimeout");
    }

    /**
     * 获取 请求超时时间
     *
     * @param readTimeout 默认请求超时时间
     * @return 请求超时时间
     */
    public static int getReadTimeout(int readTimeout) {
        return getIntProperty("wechat.http.readTimeout", readTimeout);
    }

    /**
     * 获取 是否为调试模式
     *
     * @return 是否为调试模式
     */
    public static boolean isDebug() {
        return getBoolean("wechat.debug");
    }

    public static boolean getBoolean(String name) {
        String value = getProperty(name);
        return Boolean.valueOf(value);
    }

    public static int getIntProperty(String name) {
        String value = getProperty(name);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException nfe) {
            return -1;
        }
    }

    public static int getIntProperty(String name, int fallbackValue) {
        String value = getProperty(name, String.valueOf(fallbackValue));
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException nfe) {
            return -1;
        }
    }

    /**
     * 获取属性值
     *
     * @param name 属性名称
     * @return 属性值
     */
    public static String getProperty(String name) {
        return getProperty(name, null);
    }

    /**
     * 获取属性值
     *
     * @param name 属性名
     * @param fallbackValue 默认返回值
     * @return 属性值
     */
    public static String getProperty(String name, String fallbackValue) {
        String value;
        try {
            //从全局系统获取
            value = System.getProperty(name, null);
            if (null == value) {
                //先从系统配置文件获取
                value = defaultProperty.getProperty(name, fallbackValue);
            }
        } catch (AccessControlException ace) {
            // Unsigned applet cannot access System properties
            value = fallbackValue;
        }
        return value;
    }
    
    /**
     * 获取微信商户号
     * @return
     */
    public static String getPayPattnerId(){
    	return Configuration.getProperty("wechat.pay.partner.id");
    }
    
    /**
     * 获取微信商户密钥
     * @return
     */
    public static String getPayPartnerKey(){
    	return Configuration.getProperty("wechat.pay.partner.key");
    }
    
    /**
     * 获取拍卖订单微信支付后台通知地址
     * @return
     */
    public static String getPaiPayNotifyUrl(){
    	return Configuration.getProperty("wechat.pai.pay.notify_url");
    }

    /**
     * 获取戎店订单微信支付后台通知地址
     * @return
     */
    public static String getPayNotifyUrl(){
    	return Configuration.getProperty("wechat.pay.notify_url");
    }
    
    /**
	 * 判断时是否是微信浏览器
	 * @param request
	 * @return
	 */
	public static boolean isWeChatBrowser(HttpServletRequest request){
		try {
			String agent = request.getHeader("User-Agent"); 
			Pattern pat = Pattern.compile("MicroMessenger",Pattern.CASE_INSENSITIVE);
			Matcher mat = pat.matcher(agent);  
			boolean rs = mat.find();  
			if(rs){
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	} 
	
	/**
	 * 获取微信服务器地址
	 * @return
	 */
	public static String getServiceUrl(){
		return Configuration.getProperty("wechat.service.url");
	}
	
	/**
	 * 获取微信服务令牌
	 * @return
	 */
	public static String getServiceToken(){
		return Configuration.getProperty("wechat.token");
	}

	/**
	 * 获取微信平台图片上传地址
	 * @return
	 */
	public static String getPicUploadPath(){
		return Configuration.getProperty("wechat.upload.pic.path");
	}
	
	/**
	 * 获取微信平台部署服务器操作系统
	 * @return
	 */
	public static String getSysEnv(){
		return Configuration.getProperty("wechat.system.env");
	}
	
	/**
	 * 获取微信平台部署服务器操作系统
	 * @return
	 */
	public static String getSysDomain(){
		return Configuration.getProperty("wechat.system.domain");
	}
	
}
