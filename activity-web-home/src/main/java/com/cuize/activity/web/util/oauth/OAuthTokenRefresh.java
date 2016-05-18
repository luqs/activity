package com.cuize.activity.web.util.oauth;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 刷新Token
 * @author JackieLan
 *
 */
public class OAuthTokenRefresh {
	//获得凭证、菜单
	public static void refreshOAuthToken(){
		OAuthTokenUtil.getToken(new IcallBackBase() {
			@Override
			public void callback(Object... params) {
				String data = (String) params[0];		
				JSONObject jsonObject = new JSONObject();
				jsonObject = JSON.parseObject(data);
				OAuthTokenUtil.access_token = jsonObject.getString("access_token");
				System.out.println("access_token:"+OAuthTokenUtil.access_token);
			}
		});
	}
}
