package com.cuize.activity.web.api;

import java.text.SimpleDateFormat;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cuize.activity.service.dto.CommonInDto;
import com.cuize.activity.service.dto.GlobalConfig;
import com.cuize.activity.service.dto.common.CommonOutDto;
import com.cuize.activity.service.dto.common.PageResult;
import com.cuize.activity.service.dto.wxlottery.WxhbLotteryAddInDto;
import com.cuize.activity.service.dto.wxlottery.WxhbLotteryBindTicketInDto;
import com.cuize.activity.service.dto.wxlottery.WxhbLotteryDetailQueryOutDto;
import com.cuize.activity.service.dto.wxlottery.WxhbLotteryQueryByPageInDto;
import com.cuize.activity.service.dto.wxlottery.WxhbLotterySetPrizeBucketInDto;
import com.cuize.activity.service.dto.wxlottery.WxhbLotteryUnBindTicketInDto;
import com.cuize.activity.service.dto.wxlottery.WxhbLotteryUpdateSwitchInDto;
import com.cuize.activity.service.impl.WxhbLotteryService;
import com.cuize.activity.service.impl.WxhbPreorderService;
import com.cuize.activity.web.util.HttpRequestJsonPrameterUtil;
import com.cuize.activity.web.util.WeiXinOAuthUtil;
import com.cuize.activity.web.util.oauth.Configuration;
import com.cuize.activity.web.util.oauth.OAuthTokenUtil;
import com.cuize.activity.web.util.oauth.WeixinException;
import com.cuize.activity.web.util.oauth.pojo.OAuth2;
import com.cuize.activity.web.vo.lottery.WxhbLotteryAddReq;
import com.cuize.activity.web.vo.lottery.WxhbQueryBindTicketReq;
import com.cuize.activity.web.vo.lottery.WxhbQueryLotteryByPageReq;
import com.cuize.activity.web.vo.lottery.WxhbQueryLotteryListReq;
import com.cuize.activity.web.vo.lottery.WxhbSetLotterySwitchReq;
import com.cuize.activity.web.vo.lottery.WxhbSetPrizeBucketReq;
import com.cuize.commons.meta.JosnRPCBizHelper;
import com.cuize.commons.dao.activity.domain.WxhbLottery;
import com.cuize.commons.dao.activity.resultvo.WxhbLotteryBindTicketVO;
import com.cuize.commons.dao.activity.resultvo.WxhbLotteryUnbindTicketVO;
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
			HttpServletRequest request, HttpServletResponse response, WxhbLotteryAddReq req)
			throws Exception {
		LOG.info("########################### BEGIN INVOKE WxhbLotteryController.wxhbAddLottery ###########################");
		LOG.info("****** req={}", JSON.toJSONString(req));
		CommonOutDto commonOutDto = new CommonOutDto();
		WxhbLotteryAddInDto inDto = new WxhbLotteryAddInDto();
		BeanUtils.copyProperties(req, inDto);
		inDto.setAccessToken(OAuthTokenUtil.access_token);
		commonOutDto = wxhbLotteryService.createWxhbLottery(inDto);
		
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
		final String jsonData = HttpRequestJsonPrameterUtil.getRestData(request);
		LOG.info("****** req={}", jsonData);
		CommonOutDto commonOutDto = new CommonOutDto();
		if (jsonData != null) {
			WxhbSetPrizeBucketReq req = JSON.parseObject(jsonData, WxhbSetPrizeBucketReq.class);
			WxhbLotterySetPrizeBucketInDto inDto = new WxhbLotterySetPrizeBucketInDto();
			BeanUtils.copyProperties(req, inDto);
			inDto.setAccessToken(OAuthTokenUtil.access_token);
		    commonOutDto = wxhbLotteryService.setWxhbLotteryPrizeBucket(inDto);
		}
		
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
		final String jsonData = HttpRequestJsonPrameterUtil.getRestData(request);
		LOG.info("****** req={}", jsonData);
		CommonOutDto commonOutDto = new CommonOutDto();
		if (jsonData != null) {
			WxhbSetLotterySwitchReq req = JSON.parseObject(jsonData, WxhbSetLotterySwitchReq.class);
			WxhbLotteryUpdateSwitchInDto inDto = new WxhbLotteryUpdateSwitchInDto();
			BeanUtils.copyProperties(req, inDto);
			inDto.setAccessToken(OAuthTokenUtil.access_token);
			commonOutDto = wxhbLotteryService.updateWxhbLotterySwitch(inDto);
		}
		
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
	@ResponseBody
	@RequestMapping(value = "/query-bind-ticket", method = RequestMethod.GET)
	public JSONObject queryWxhbLotteryBindTicketByPage(Object obj, Model model,
			HttpServletRequest request, HttpServletResponse response, WxhbQueryBindTicketReq req )
			throws Exception {
		LOG.info("########################### BEGIN INVOKE WxhbLotteryController.queryWxhbLotteryBindTicketByPage ###########################");
		
		int start = (req.getPage() - 1 ) * req.getRows();
		WxhbLotteryBindTicketInDto inDto = new WxhbLotteryBindTicketInDto();
		inDto.setStart(start);;
		inDto.setLimit(req.getRows());
		inDto.setHbLotteryId(req.getHbLotteryId());
		PageResult<WxhbLotteryBindTicketVO> result = wxhbLotteryService.queryWxhbLotteryBindTicketByPage(inDto);
		LOG.info("****** ResponseBody={}", JSON.toJSONString(result));
		LOG.info("########################### END INVOKE WxhbLotteryController.queryWxhbLotteryBindTicketByPage ###########################\n\n");
		return JSON.parseObject(JSON.toJSONString(result));
	}
	
	/**
	 * 查询未被使用的有效ticket
	 * @param obj
	 * @param model
	 * @param request
	 * @param response
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/query-unbind-ticket", method = RequestMethod.GET)
	public JSONObject queryUnBindTicketByPage(Object obj, Model model,
			HttpServletRequest request, HttpServletResponse response, WxhbQueryBindTicketReq req )
			throws Exception {
		LOG.info("########################### BEGIN INVOKE WxhbLotteryController.queryUnBindTicketByPage ###########################");
		
		int start = (req.getPage() - 1 ) * req.getRows();
		WxhbLotteryUnBindTicketInDto inDto = new WxhbLotteryUnBindTicketInDto();
		inDto.setStart(start);;
		inDto.setLimit(req.getRows());
		//inDto.setHbLotteryId(req.getHbLotteryId());
		PageResult<WxhbLotteryUnbindTicketVO> result = wxhbLotteryService.queryWxhbUnBindTicketByPage(inDto);
		LOG.info("****** ResponseBody={}", JSON.toJSONString(result));
		LOG.info("########################### END INVOKE WxhbLotteryController.queryUnBindTicketByPage ###########################\n\n");
		return JSON.parseObject(JSON.toJSONString(result));
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
	@ResponseBody
	@RequestMapping(value = "/query-by-page", method = RequestMethod.GET)
	public JSONObject wxhbQueryLotteryByPage(Object obj, Model model,
			HttpServletRequest request, HttpServletResponse response, WxhbQueryLotteryByPageReq req)
			throws Exception {
		LOG.info("########################### BEGIN INVOKE WxhbLotteryController.wxhbQueryLotteryByPage ###########################");
		
		int start = (req.getPage() - 1 ) * req.getRows();
		WxhbLotteryQueryByPageInDto inDto = new WxhbLotteryQueryByPageInDto();
		inDto.setTitle(req.getTitle());
		inDto.setStatus(req.getStatus());
		
		inDto.setStart(start);
		inDto.setLimit(req.getRows());
		PageResult<WxhbLottery> result = wxhbLotteryService.queryWxhbLotteryByPage(inDto);
		LOG.info("****** ResponseBody={}", JSON.toJSONString(result));
		LOG.info("########################### END INVOKE WxhbLotteryController.wxhbQueryLotteryByPage ###########################\n\n");
		return JSON.parseObject(JSON.toJSONString(result));
	}
	
	/**
	 * 查询红包活动详情
	 * @param obj
	 * @param model
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public JSONObject queryWxhbQueryLotteryById(Object obj, Model model,
			HttpServletRequest request, HttpServletResponse response, @PathVariable(value="id") Integer id)
			throws Exception {
		LOG.info("########################### BEGIN INVOKE WxhbLotteryController.queryWxhbQueryLotteryById ###########################");
		
		JSONObject result = new JSONObject();
		WxhbLottery lottery = wxhbLotteryService.queryWxhbLotteryDetail(id);
		if (lottery != null) {
			result = JSON.parseObject(JSON.toJSONString(lottery));
			
			if (lottery.getCreateTime() != null) {
				String ct = sdf.format(lottery.getCreateTime());
				result.put("createTime", ct);
			}
			if (lottery.getUpdateTime() != null) {
				String ct = sdf.format(lottery.getUpdateTime());
				result.put("updateTime", ct);
			}
			if (lottery.getExpireTime() != null) {
				String ct = sdf.format(lottery.getExpireTime());
				result.put("expireTime", ct);
			}
			if (lottery.getBeginTime() != null) {
				String ct = sdf.format(lottery.getBeginTime());
				result.put("beginTime", ct);
			}
			if (lottery.getStatus() == 0) {
				result.put("statusStr", "关闭");
			} else if(lottery.getStatus() == 1) {
				result.put("statusStr", "开启");
			} 
		} 
		LOG.info("****** ResponseBody={}", result);
		LOG.info("########################### END INVOKE WxhbLotteryController.queryWxhbQueryLotteryById ###########################\n\n");
		return result;
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
   	
   	private static final SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
}
