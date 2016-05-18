package com.cuize.activity.web.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cuize.activity.web.util.oauth.OAuthTokenRefresh;
import com.cuize.activity.web.util.oauth.OAuthTokenUtil;

/**
 * 微信相关接口
 * @author JackieLan
 *
 */
@Controller
@RequestMapping(value = "/wechat")
public class WeixinCommonController {

	private static final Logger LOG = LoggerFactory.getLogger(WeixinCommonController.class);
	
	/**
	 * 查询token
	 * @param obj
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/token/query", method = RequestMethod.GET)
	public JSONObject queryOAuthToken(Object obj, Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LOG.info("########################### BEGIN INVOKE WeixinCommonController.queryOAuthToken ###########################");
		
		String accessToken = OAuthTokenUtil.access_token;
		JSONObject result = new JSONObject();
		result.put("access_token", accessToken);
		LOG.info("****** ResponseBody={}", JSON.toJSONString(result));
		LOG.info("########################### END INVOKE WeixinCommonController.queryOAuthToken ###########################\n\n");
		return result;
	}
	
	/**
	 * 刷新token（每天接口限制次数，故谨慎执行）
	 * @param obj
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/token/refresh", method = RequestMethod.GET)
	public JSONObject refreshOAuthToken(Object obj, Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LOG.info("########################### BEGIN INVOKE WeixinCommonController.refreshOAuthToken ###########################");
		
		OAuthTokenRefresh.refreshOAuthToken();
		String accessToken = OAuthTokenUtil.access_token;
		JSONObject result = new JSONObject();
		result.put("access_token", accessToken);
		
		LOG.info("****** ResponseBody={}", JSON.toJSONString(result));
		LOG.info("########################### END INVOKE WeixinCommonController.refreshOAuthToken ###########################\n\n");
		return result;
	}
	
	/**
	 * 手动设置access_token
	 * @param obj
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/token/set", method = RequestMethod.GET)
	public JSONObject setOAuthToken(Object obj, Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LOG.info("########################### BEGIN INVOKE WeixinCommonController.setOAuthToken ###########################");
		
		String token = request.getParameter("access_token");
		
		JSONObject result = new JSONObject();
		if (token == null || token.isEmpty()) {
			result.put("result", false);
		} else {
			result.put("result", true);
			OAuthTokenUtil.access_token = token;
		}
		
		LOG.info("########################### END INVOKE WeixinCommonController.setOAuthToken ###########################\n\n");
		return result;
	}
}
