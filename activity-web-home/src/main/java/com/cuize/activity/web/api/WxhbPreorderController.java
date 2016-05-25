package com.cuize.activity.web.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cuize.activity.service.dto.CommonInDto;
import com.cuize.activity.service.dto.common.CommonOutDto;
import com.cuize.activity.service.dto.wxpreorder.WxhbPreorderAddInDto;
import com.cuize.activity.service.impl.WxhbLotteryService;
import com.cuize.activity.service.impl.WxhbPreorderService;
import com.cuize.activity.web.vo.preorder.WxhbPreorderAddReq;
import com.cuize.activity.web.vo.preorder.WxhbPreorderQueryByPageReq;
import com.cuize.commons.meta.JosnRPCBizHelper;

/**
 * 微信红包预下单接口
 * @author JackieLan
 *
 */
@Controller
@RequestMapping(value = "/wxhb/preorder")
public class WxhbPreorderController {

	private static final Logger LOG = LoggerFactory.getLogger(WxhbPreorderController.class);
	
	@Autowired
	private WxhbLotteryService wxhbLotteryService;
	
	@Autowired
	private WxhbPreorderService wxhbPreorderService;
	
	/**
	 * 微信红包预下单
	 * @param obj
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JSONObject wxhbPreorder(Object obj, Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LOG.info("########################### BEGIN INVOKE WxhbPreorderController.wxhbPreorder ###########################");
		CommonInDto<WxhbPreorderAddReq> preorderAddReq = JSON.parseObject(
				JosnRPCBizHelper.getForwardData(request).toJSONString(),
				new TypeReference<CommonInDto<WxhbPreorderAddReq>>(){});
		WxhbPreorderAddInDto inDto = new WxhbPreorderAddInDto();
		BeanUtils.copyProperties(preorderAddReq.getParams(), inDto);
		CommonOutDto commonOutDto = wxhbPreorderService.preorder(inDto);
		
		LOG.info("****** ResponseBody={}", JSON.toJSONString(commonOutDto));
		LOG.info("########################### END INVOKE WxhbPreorderController.wxhbPreorder ###########################\n\n");
		return JSON.parseObject(JSON.toJSONString(commonOutDto));
	}
	
	/**
	 * 分页查询预下单
	 * @param obj
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/query-by-page", method = RequestMethod.POST)
	public JSONObject wxhbPreorderQueryByPage(Object obj, Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LOG.info("########################### BEGIN INVOKE WxhbPreorderController.wxhbPreorderQueryByPage ###########################");
		CommonInDto<WxhbPreorderQueryByPageReq> preorderQueryByPageReq = JSON.parseObject(
				JosnRPCBizHelper.getForwardData(request).toJSONString(),
				new TypeReference<CommonInDto<WxhbPreorderQueryByPageReq>>(){});
		// TODO
		
		LOG.info("****** ResponseBody=");
		LOG.info("########################### END INVOKE WxhbPreorderController.wxhbPreorderQueryByPage ###########################\n\n");
		return null;
	}
}
