package com.cuize.activity.web.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cuize.activity.service.dto.CommonInDto;
import com.cuize.activity.service.dto.ValidateDrawInDto;
import com.cuize.activity.service.dto.ValidateDrawOutDto;
import com.cuize.activity.service.impl.ValidateDrawService;
import com.cuize.commons.meta.JosnRPCBizHelper;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;

import net.minidev.json.JSONObject;

/**
 * 产品库存入库接口
 * 
 * @author luqingsong
 *
 */
@Controller
public class ValidateDrawController {

	private static final Logger _LOG = LoggerFactory.getLogger(ValidateDrawController.class);

	@Autowired
	private ValidateDrawService service;
	
	@ResponseBody
	@RequestMapping(value = "/validateDraw", method = RequestMethod.POST)
	public JSONObject draw(Object obj, Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		_LOG.info("########################### BEGIN INVOKE ValidateDrawController ###########################");
		CommonInDto<ValidateDrawInDto> inDto = JSON.parseObject(
				JosnRPCBizHelper.getForwardData(request).toJSONString(),
				new TypeReference<CommonInDto<ValidateDrawInDto>>(){});
		JSONRPC2Response jsonresp = new JSONRPC2Response(inDto.getId());
		ValidateDrawOutDto responseDto= service.validateDraw(inDto.getParams());
		jsonresp.setResult(responseDto);
		_LOG.info("****** ResponseBody="+jsonresp.toJSONString());
		_LOG.info("########################### END INVOKE ValidateDrawController ###########################\n\n");
		return jsonresp.toJSONObject();

	}
	
}
