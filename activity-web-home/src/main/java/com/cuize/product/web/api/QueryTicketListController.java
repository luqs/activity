package com.cuize.product.web.api;

import java.util.List;

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
import com.cuize.product.service.dto.QueryTicketListInDto;
import com.cuize.product.service.dto.QueryTicketListOutDto;
import com.cuize.product.service.impl.QueryTicketListService;
import com.cuize.product.web.helper.JosnRPCBizHelper;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;

/**
 * 产品库存入库接口
 * 
 * @author luqingsong
 *
 */
@Controller
public class QueryTicketListController {

	private static final Logger _LOG = LoggerFactory.getLogger(QueryTicketListController.class);

	@Autowired
	private QueryTicketListService service;
	
	@ResponseBody
	@RequestMapping(value = "/queryTicketList", method = RequestMethod.POST)
	public JSONObject queryTicketList(Object obj, Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		_LOG.info("########################### BEGIN INVOKE QueryTicketListController ###########################");
		CommonInDto<QueryTicketListInDto> inDto = JSON.parseObject(
				JosnRPCBizHelper.getForwardData(request).toJSONString(),
				new TypeReference<CommonInDto<QueryTicketListInDto>>(){});
		JSONRPC2Response jsonresp = new JSONRPC2Response(inDto.getId());
		List<QueryTicketListOutDto> responseDto= service.queryTicketList(inDto.getParams());
		jsonresp.setResult(responseDto);
		_LOG.info("****** ResponseBody="+jsonresp.toJSONString());
		_LOG.info("########################### END INVOKE QueryTicketListController ###########################\n\n");
		return jsonresp.toJSONObject();

	}
	
}
