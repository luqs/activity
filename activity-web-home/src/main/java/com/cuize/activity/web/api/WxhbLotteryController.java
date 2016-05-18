package com.cuize.activity.web.api;

import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cuize.activity.service.dto.CommonInDto;
import com.cuize.activity.service.dto.GlobalConfig;
import com.cuize.activity.service.dto.common.CommonOutDto;
import com.cuize.activity.service.dto.wxlottery.WxhbLotteryAddInDto;
import com.cuize.activity.service.dto.wxlottery.WxhbLotteryDetailQueryOutDto;
import com.cuize.activity.service.dto.wxlottery.WxhbLotterySetPrizeBucketInDto;
import com.cuize.activity.service.dto.wxlottery.WxhbLotteryUpdateSwitchInDto;
import com.cuize.activity.service.impl.WxhbLotteryService;
import com.cuize.activity.service.impl.WxhbPreorderService;
import com.cuize.activity.web.helper.JosnRPCBizHelper;
import com.cuize.activity.web.util.WeiXinOAuthUtil;
import com.cuize.activity.web.util.oauth.Configuration;
import com.cuize.activity.web.util.oauth.OAuthTokenUtil;
import com.cuize.activity.web.util.oauth.pojo.OAuth2;
import com.cuize.activity.web.vo.lottery.WxhbLotteryAddReq;
import com.cuize.activity.web.vo.lottery.WxhbQueryBindTicketReq;
import com.cuize.activity.web.vo.lottery.WxhbQueryLotteryByPageReq;
import com.cuize.activity.web.vo.lottery.WxhbQueryLotteryListReq;
import com.cuize.activity.web.vo.lottery.WxhbSetLotterySwitchReq;
import com.cuize.activity.web.vo.lottery.WxhbSetPrizeBucketReq;
import com.cuize.commons.utils.WXPayUtil;


/**
 * 微信红包活动接口
 * @author JackieLan
 *
 */
@Controller
@RequestMapping(value = "/wxhb/lottery")
public class WxhbLotteryController {

	private static final Logger LOG = LoggerFactory.getLogger(WxhbLotteryController.class);
	
	@Autowired
	private WxhbLotteryService wxhbLotteryService;
	
	@Autowired
	private WxhbPreorderService wxhbPreorderService;
	
	@Autowired
	private GlobalConfig globalConfig;
	
	/**
	 * 微信红包活动错误页
	 */
	private static final String WXHB_LOTTERY_ERROR  =  "/wxhb/error";
	
	/**
	 * 微信红包活动页
	 */
	private static final String WXHB_LOTTERY_INDEX  =  "/wxhb/lottery";
	
	/**
	 * 添加微信红包活动
	 * @param obj
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JSONObject wxhbAddLottery(Object obj, Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LOG.info("########################### BEGIN INVOKE WxhbLotteryController.wxhbAddLottery ###########################");
		CommonInDto<WxhbLotteryAddReq> lotteryAddReq = JSON.parseObject(
				JosnRPCBizHelper.getForwardData(request).toJSONString(),
				new TypeReference<CommonInDto<WxhbLotteryAddReq>>(){});
		WxhbLotteryAddInDto inDto = new WxhbLotteryAddInDto();
		BeanUtils.copyProperties(lotteryAddReq.getParams(), inDto);
		inDto.setAccessToken(OAuthTokenUtil.access_token);
		CommonOutDto commonOutDto = wxhbLotteryService.createWxhbLottery(inDto);
		
		LOG.info("****** ResponseBody={}", JSON.toJSONString(commonOutDto));
		LOG.info("########################### END INVOKE WxhbLotteryController.wxhbAddLottery ###########################\n\n");
		return JSON.parseObject(JSON.toJSONString(commonOutDto));
	}
	
	/**
	 * 为红包活动录入红包信息
	 * @param obj
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/set-prize-bucket", method = RequestMethod.POST)
	public JSONObject wxhbSetPrizeBucket(Object obj, Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LOG.info("########################### BEGIN INVOKE WxhbLotteryController.wxhbSetPrizeBucket ###########################");
		CommonInDto<WxhbSetPrizeBucketReq> setPrizeBucketReq = JSON.parseObject(
				JosnRPCBizHelper.getForwardData(request).toJSONString(),
				new TypeReference<CommonInDto<WxhbSetPrizeBucketReq>>(){});
		WxhbLotterySetPrizeBucketInDto inDto = new WxhbLotterySetPrizeBucketInDto();
		BeanUtils.copyProperties(setPrizeBucketReq.getParams(), inDto);
		inDto.setAccessToken(OAuthTokenUtil.access_token);
		CommonOutDto commonOutDto = wxhbLotteryService.setWxhbLotteryPrizeBucket(inDto);
		
		LOG.info("****** ResponseBody={}", JSON.toJSONString(commonOutDto));
		LOG.info("########################### END INVOKE WxhbLotteryController.wxhbSetPrizeBucket ###########################\n\n");
		return JSON.parseObject(JSON.toJSONString(commonOutDto));
	}
	
	/**
	 * 红包活动开关
	 * @param obj
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/set-lottery-switch", method = RequestMethod.POST)
	public JSONObject wxhbSetLotterySwitch(Object obj, Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LOG.info("########################### BEGIN INVOKE WxhbLotteryController.wxhbSetLotterySwitch ###########################");
		CommonInDto<WxhbSetLotterySwitchReq> setLotterySwitchReq = JSON.parseObject(
				JosnRPCBizHelper.getForwardData(request).toJSONString(),
				new TypeReference<CommonInDto<WxhbSetLotterySwitchReq>>(){});
		WxhbLotteryUpdateSwitchInDto inDto = new WxhbLotteryUpdateSwitchInDto();
		BeanUtils.copyProperties(setLotterySwitchReq.getParams(), inDto);
		inDto.setAccessToken(OAuthTokenUtil.access_token);
		CommonOutDto commonOutDto = wxhbLotteryService.updateWxhbLotterySwitch(inDto);
		
		LOG.info("****** ResponseBody={}", JSON.toJSONString(commonOutDto));
		LOG.info("########################### END INVOKE WxhbLotteryController.wxhbSetLotterySwitch ###########################\n\n");
		return JSON.parseObject(JSON.toJSONString(commonOutDto));
	}

	/**
	 * 查询红包活动已录入的ticket
	 * @param obj
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/query-bind-ticket", method = RequestMethod.POST)
	public JSONObject wxhbQueryBindTicket(Object obj, Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LOG.info("########################### BEGIN INVOKE WxhbLotteryController.wxhbQueryBindTicket ###########################");
		WxhbQueryBindTicketReq queryBindTicketReq = JSON.parseObject(
				JosnRPCBizHelper.getForwardData(request).toJSONString(),
				new TypeReference<WxhbQueryBindTicketReq>(){});
		// TODO
		LOG.info("****** ResponseBody=");
		LOG.info("########################### END INVOKE WxhbLotteryController.wxhbQueryBindTicket ###########################\n\n");
		return null;
	}
	
	/**
	 * 分页查询红包活动
	 * @param obj
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/query-by-page", method = RequestMethod.POST)
	public JSONObject wxhbQueryLotteryByPage(Object obj, Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LOG.info("########################### BEGIN INVOKE WxhbLotteryController.wxhbQueryLotteryByPage ###########################");
		WxhbQueryLotteryByPageReq queryLotteryByPageReq = JSON.parseObject(
				JosnRPCBizHelper.getForwardData(request).toJSONString(),
				new TypeReference<WxhbQueryLotteryByPageReq>(){});
		// TODO
		LOG.info("****** ResponseBody=");
		LOG.info("########################### END INVOKE WxhbLotteryController.wxhbQueryLotteryByPage ###########################\n\n");
		return null;
	}
	
	/**
	 * 非分页列表查询红包活动
	 * 
	 * @param obj
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/query-list", method = RequestMethod.POST)
	public JSONObject wxhbQueryLotteryList(Object obj, Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LOG.info("########################### BEGIN INVOKE WxhbLotteryController.wxhbQueryLotteryList ###########################");
		WxhbQueryLotteryListReq queryLotteryListReq = JSON.parseObject(
				JosnRPCBizHelper.getForwardData(request).toJSONString(),
				new TypeReference<WxhbQueryLotteryListReq>(){});
		// TODO
		LOG.info("****** ResponseBody=");
		LOG.info("########################### END INVOKE WxhbLotteryController.wxhbQueryLotteryList ###########################\n\n");
		return null;
	}

	/**
	 * 微信红包活动页
	 * @param lotteryId
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping("/page/{lotteryId}")
	public String lotteryDetail(@PathVariable(value="lotteryId") Long lotteryId,  ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response){
		/*response.setContentType("text/html;utf-8");
		//response.setContentType("application/json; charset=UTF-8");
		response.setCharacterEncoding("utf-8"); */
		/*if (true) {
			//returnView.setViewName(WXHB_LOTTERY_ERROR);
			//modelMap.addAttribute("lotteryId", "aaaaa");
			modelMap.put("lotteryId", "bbbb");
			return "/index";
		}*/
		try{
			if(!StringUtils.isEmpty(request.getParameter("code"))){
				LOG.info("微信红包活动页：带code访问");
				// 查询微信红包活动
				WxhbLotteryDetailQueryOutDto lottery = wxhbLotteryService.queryWxhbLotteryDetail(lotteryId.intValue());
				if (lottery != null) {
					if (lottery.getStatus() == 1) {
						// 活动已打开
						if (isLettoryExpired(lottery.getExpireTime())) {
							// 活动已过期
							modelMap.put("errorMsg", "该活动已过期");
						} else {
							// 活动有效
							String openId = getWeChatUserOpenId(request);
							if(StringUtils.isEmpty(openId)){
								modelMap.put("errorMsg", "获取微信用户失败");
								return WXHB_LOTTERY_ERROR;
							}
							
							String noncestr = WXPayUtil.createNoncestr();
							SortedMap<String, String> wxParams = new TreeMap<String, String>();
							wxParams.put("lottery_id", lottery.getLotteryId());
							wxParams.put("openid", openId);
							wxParams.put("noncestr", noncestr);
							String sign = WXPayUtil.createSign("UTF-8", globalConfig.getApikey(), wxParams);
							
							modelMap.put("lotteryId", lottery.getLotteryId());
							modelMap.put("openid", openId);
							modelMap.put("noncestr", noncestr);
							modelMap.put("sign", sign);
							
							LOG.info("微信红包活动页：返回页面参数{}", JSON.toJSONString(wxParams));
							return WXHB_LOTTERY_INDEX;
						}
					} else {
						// 活动已关闭
						modelMap.put("errorMsg", "该活动已关闭");
					}
				} else {
					// 不存在该活动
					modelMap.put("errorMsg", "不存在该活动");
				}
			}else{
				LOG.info("微信红包活动页：不带code访问，进行OAuth授权");
				String oauthUrl = WeiXinOAuthUtil.getWeChatOAuthUrl(globalConfig.getAppid(), globalConfig.getOauthRedirectUri(), request);
				LOG.info("微信红包活动页跳转OAuthUrl={}", oauthUrl);
				return "redirect:"+oauthUrl;
			}
		}catch(Exception e){
			LOG.error("微信红包活动页错误", e);
		}
		return WXHB_LOTTERY_ERROR;
	}
	
	/**
	 * 测试接口页面跳转
	 * @param model
	 * @param reqeustPage
	 * @param pageSize
	 * @param usertype
	 * @return
	 */
	@RequestMapping(value = "/test-page", method = RequestMethod.GET)
    public ModelAndView getregister(ModelAndView model, Integer reqeustPage, Integer pageSize, Integer usertype)
    {
		model.setViewName("forward:/pages/wxhb/lottery.jsp");
		return model;
    }
	
	/**
	 * 测试rest接口
	 * @param obj
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/test-json", method = RequestMethod.GET)
	public JSONObject mytest(Object obj, Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LOG.info("########################### BEGIN INVOKE WxhbLotteryController.mytest ###########################");
		
		LOG.info("****** ResponseBody=");
		LOG.info("########################### END INVOKE WxhbLotteryController.mytest ###########################\n\n");
		JSONObject ss= new JSONObject();
		ss.put("dd", "d");
		return ss;
	}
	
	/**
	 * 摇一摇红包活动是否过期
	 * @param expireDate
	 * @return
	 */
	private boolean isLettoryExpired(Date expireDate){
		if (new Date().after(expireDate)) {
			return true;
		} else {
			return false;
		}
	}
	
	private static byte[] lock = new byte[0]; 
	
	/**
   	 * 网页授权并获取微信用户信息
   	 * @param request
   	 * @return
   	 * @throws WeixinException 
   	 */
   	public String getWeChatUserOpenId(HttpServletRequest request){
   		synchronized (lock) {
	   		try{
	   			if(Configuration.isWeChatBrowser(request)){
	   				String code  =  request.getParameter("code");
	   				String openId  =  (String)request.getSession().getAttribute("openId");
	   				if(!StringUtils.isEmpty(openId)){
	   					return openId;
	   				}
	   				if(!StringUtils.isEmpty(code)){
	   					// 调用OAuth授权的oauth2/access_token接口，获取token及openId
	   					openId = new OAuth2().login(globalConfig.getAppid(), globalConfig.getAppsecret(), request.getParameter("code")).getOpenid();
	   				}
	   				// 手动塞入该用户的openId，防止重复
	   				request.getSession(true).setAttribute("openId", openId);
	   				return openId;
	   			}
	   		}catch(Exception e){
	   			LOG.error("getWeChatUserOpenId异常", e);
	   		}
	   		return null;
   		}
   	}
}
