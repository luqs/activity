package com.cuize.activity.web.api;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cuize.activity.service.dto.CommonInDto;
import com.cuize.activity.service.dto.common.CommonOutDto;
import com.cuize.activity.service.dto.common.PageResult;
import com.cuize.activity.service.dto.wxlottery.WxhbLotteryQueryByPageInDto;
import com.cuize.activity.service.dto.wxpreorder.WxhbPreorderAddInDto;
import com.cuize.activity.service.dto.wxpreorder.WxhbPreorderQueryByPageInDto;
import com.cuize.activity.service.dto.wxpreorder.WxhbPreorderQueryByPageOutDto;
import com.cuize.activity.service.dto.wxpreorder.WxhbPreorderQueryDetailOutDto;
import com.cuize.activity.service.impl.WxhbLotteryService;
import com.cuize.activity.service.impl.WxhbPreorderService;
import com.cuize.activity.web.util.HttpRequestJsonPrameterUtil;
import com.cuize.activity.web.vo.lottery.WxhbQueryLotteryByPageReq;
import com.cuize.activity.web.vo.lottery.WxhbSetPrizeBucketReq;
import com.cuize.activity.web.vo.preorder.WxhbPreorderAddReq;
import com.cuize.activity.web.vo.preorder.WxhbPreorderQueryByPageReq;
import com.cuize.commons.meta.JosnRPCBizHelper;
import com.cuize.commons.dao.activity.domain.WxhbLottery;
import com.cuize.commons.dao.activity.domain.WxhbPreorder;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;

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
			HttpServletRequest request, HttpServletResponse response, WxhbPreorderAddReq req )
			throws Exception {
		LOG.info("########################### BEGIN INVOKE WxhbPreorderController.wxhbPreorder ###########################");
		LOG.info("****** req={}", JSON.toJSONString(req));
		CommonOutDto commonOutDto = new CommonOutDto();
		WxhbPreorderAddInDto inDto = new WxhbPreorderAddInDto();
		BeanUtils.copyProperties(req, inDto);
		commonOutDto = wxhbPreorderService.preorder(inDto);
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
	@RequestMapping(value = "/query-by-page", method = RequestMethod.GET)
	public JSONObject wxhbPreorderQueryByPage(Object obj, Model model,
			HttpServletRequest request, HttpServletResponse response, WxhbPreorderQueryByPageReq req)
			throws Exception {
		LOG.info("########################### BEGIN INVOKE WxhbPreorderController.wxhbPreorderQueryByPage ###########################");
		/*WxhbPreorderQueryByPageReq preorderQueryByPageReq = JSON.parseObject(
				JosnRPCBizHelper.getForwardData(request).toJSONString(),
				new TypeReference<CommonInDto<WxhbPreorderQueryByPageReq>>(){});*/
		LOG.info("****** req={}", JSON.toJSONString(req));
		int start = (req.getPage() - 1 ) * req.getRows();
		WxhbPreorderQueryByPageInDto inDto = new WxhbPreorderQueryByPageInDto();
		if (req.getHbType() != null && !"ALL".equals(req.getHbType())) {
			inDto.setHbType(req.getHbType());
		}
		inDto.setStatus(req.getStatus());
		inDto.setStart(start);
		inDto.setLimit(req.getRows());
		PageResult<WxhbPreorder> result = wxhbPreorderService.queryWxhbPreorderByPage(inDto);
		
		LOG.info("****** ResponseBody={}", JSON.toJSONString(result));
		LOG.info("########################### END INVOKE WxhbPreorderController.wxhbPreorderQueryByPage ###########################\n\n");
		return JSON.parseObject(JSON.toJSONString(result));
	}
	
	/**
	 * 查询预下单详情
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
	public JSONObject wxhbPreorderQueryByDetail(Object obj, Model model,
			HttpServletRequest request, HttpServletResponse response, @PathVariable(value="id") Integer id)
			throws Exception {
		LOG.info("########################### BEGIN INVOKE WxhbPreorderController.wxhbPreorderQueryByDetail ###########################");
		// TODO
		WxhbPreorderQueryDetailOutDto result = wxhbPreorderService.queryWxhbPreorderById(id);
		
		JSONObject json = JSON.parseObject(JSON.toJSONString(result));
		if (result!= null) {
			if (result.getCreateTime() != null) {
				String ct = sdf.format(result.getCreateTime());
				json.put("createTime", ct);
			}
			if (result.getUpdateTime() != null) {
				String ct = sdf.format(result.getUpdateTime());
				json.put("updateTime", ct);
			}
			if (result.getExpireTime() != null) {
				String ct = sdf.format(result.getExpireTime());
				json.put("expireTime", ct);
			}
		}
		LOG.info("****** ResponseBody={}", json);
		LOG.info("########################### END INVOKE WxhbPreorderController.wxhbPreorderQueryByDetail ###########################\n\n");
		return json;
	}
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
}
