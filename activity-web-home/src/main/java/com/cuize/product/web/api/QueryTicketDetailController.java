package com.cuize.product.web.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.minidev.json.JSONObject;

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
import com.cuize.product.service.dto.CommonInDto;
import com.cuize.product.service.dto.QueryTicketDetailInDto;
import com.cuize.product.service.dto.QueryTicketDetailOutDto;
import com.cuize.product.service.impl.QueryTicketDetailService;
import com.cuize.product.web.helper.JosnRPCBizHelper;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;

/**
 * 产品库存入库接口
 * 
 * @author luqingsong
 *
 */
@Controller
public class QueryTicketDetailController {

	private static final Logger _LOG = LoggerFactory.getLogger(QueryTicketDetailController.class);

	@Autowired
	private QueryTicketDetailService service;
	
	@ResponseBody
	@RequestMapping(value = "/queryTicketDetail", method = RequestMethod.POST)
	public JSONObject queryTicketList(Object obj, Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		_LOG.info("########################### BEGIN INVOKE QueryTicketDetailController ###########################");
		CommonInDto<QueryTicketDetailInDto> inDto = JSON.parseObject(
				JosnRPCBizHelper.getForwardData(request).toJSONString(),
				new TypeReference<CommonInDto<QueryTicketDetailInDto>>(){});
		JSONRPC2Response jsonresp = new JSONRPC2Response(inDto.getId());
		QueryTicketDetailOutDto responseDto= service.queryTicketDetail(inDto.getParams());
		jsonresp.setResult(responseDto);
		_LOG.info("****** ResponseBody="+jsonresp.toJSONString());
		_LOG.info("########################### END INVOKE QueryTicketDetailController ###########################\n\n");
		return jsonresp.toJSONObject();

	}

}
