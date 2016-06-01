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
import com.cuize.activity.service.dto.common.CommonOutDto;
import com.cuize.activity.service.dto.common.PageResult;
import com.cuize.activity.service.dto.lottery.LotteryAddInDto;
import com.cuize.activity.service.dto.lottery.LotteryQueryByPageInDto;
import com.cuize.activity.service.dto.wxpreorder.WxhbPreorderQueryByPageInDto;
import com.cuize.activity.service.impl.LotteryService;
import com.cuize.activity.web.vo.lottery.LotteryAddReq;
import com.cuize.activity.web.vo.lottery.LotteryQueryByPageReq;
import com.cuize.activity.web.vo.preorder.WxhbPreorderQueryByPageReq;
import com.cuize.commons.dao.activity.domain.Lottery;
import com.cuize.commons.dao.activity.domain.WxhbPreorder;

/**
 * 微信红包活动接口
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/lottery")
public class LotteryController {

	private static final Logger LOG = LoggerFactory.getLogger(LotteryController.class);
	
	@Autowired
	private LotteryService lotteryService;
	
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JSONObject createLottery(Object obj, Model model,
			HttpServletRequest request, HttpServletResponse response, LotteryAddReq req )
			throws Exception {
		LOG.info("########################### BEGIN INVOKE LotteryController.createLottery ###########################");
		LOG.info("****** req={}", JSON.toJSONString(req));
		CommonOutDto commonOutDto = new CommonOutDto();
		LotteryAddInDto inDto = new LotteryAddInDto();
		BeanUtils.copyProperties(req, inDto);
		/*if (inDto.getHbType().equals("NORMAL")) {
			inDto.setAmtType("");
		} else if (inDto.getHbType().equals("GROUP")){
			inDto.setAmtType("ALL_RAND");
		}*/
		inDto.setHbType("NORMAL"); // 普通红包
		inDto.setAmtType("ALL_RAND"); // 普通红包无效
		commonOutDto = lotteryService.createLottery(inDto);
		LOG.info("****** ResponseBody={}", JSON.toJSONString(commonOutDto));
		LOG.info("########################### END INVOKE LotteryController.createLottery ###########################\n\n");
		return JSON.parseObject(JSON.toJSONString(commonOutDto));
	}
	
	@ResponseBody
	@RequestMapping(value = "/query-by-page", method = RequestMethod.GET)
	public JSONObject lotteryQueryByPage(Object obj, Model model,
			HttpServletRequest request, HttpServletResponse response, LotteryQueryByPageReq req)
			throws Exception {
		LOG.info("########################### BEGIN INVOKE LotteryController.lotteryQueryByPage ###########################");
		LOG.info("****** req={}", JSON.toJSONString(req));
		int start = (req.getPage() - 1 ) * req.getRows();
		LotteryQueryByPageInDto inDto = new LotteryQueryByPageInDto();
		
		inDto.setProcessStatus(req.getProcessStatus());
		inDto.setStart(start);
		inDto.setLimit(req.getRows());
		PageResult<Lottery> result = lotteryService.queryLotteryByPage(inDto);
		
		LOG.info("****** ResponseBody={}", JSON.toJSONString(result));
		LOG.info("########################### END INVOKE LotteryController.lotteryQueryByPage ###########################\n\n");
		return JSON.parseObject(JSON.toJSONString(result));
	}
	
}
