package com.cuize.activity.service.impl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.cuize.activity.service.constant.WxConstant;
import com.cuize.activity.service.constant.WxhbTypeConstant;
import com.cuize.activity.service.dto.GlobalConfig;
import com.cuize.activity.service.dto.common.CommonOutDto;
import com.cuize.activity.service.dto.common.PageResult;
import com.cuize.activity.service.dto.wxpreorder.WxhbPreorderAddInDto;
import com.cuize.activity.service.dto.wxpreorder.WxhbPreorderAddOutDto;
import com.cuize.activity.service.dto.wxpreorder.WxhbPreorderQueryByPageInDto;
import com.cuize.activity.service.dto.wxpreorder.WxhbPreorderQueryByPageOutDto;
import com.cuize.activity.service.dto.wxpreorder.WxhbPreorderQueryDetailOutDto;
import com.cuize.activity.service.util.CommonOutDtoUtil;
import com.cuize.activity.service.util.CommonWeixinCode;
import com.cuize.activity.service.util.Map2BeanUtil;
import com.cuize.activity.service.weixin.WecatCaSecurityHttpUtil;
import com.cuize.activity.service.weixin.PreorderResultBean;
import com.cuize.commons.dao.activity.domain.WxhbPreorder;
import com.cuize.commons.dao.activity.domain.WxhbPreorderTicket;
import com.cuize.commons.dao.activity.mapper.WxhbPreorderMapper;
import com.cuize.commons.dao.activity.queryvo.common.Page;
import com.cuize.commons.dao.activity.queryvo.preorder.PreorderQueryVO;
import com.cuize.commons.utils.BeanInitialUtils;
import com.cuize.commons.utils.WXPayUtil;

/**
 * 微信红包预下单服务
 * @author JackieLan
 *
 */
@Service
@Transactional(value="activityTransactionManager",rollbackFor=Exception.class)
public class WxhbPreorderService {

	private static final Logger LOG = LoggerFactory.getLogger(WxhbPreorderService.class);

	@Autowired
	private WxhbPreorderMapper wxhbPreorderMapper;
	
	@Autowired
	private GlobalConfig globalConfig;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddmmhhss");
	
	/**
	 * 微信红包预下单
	 * @param inDto
	 */
	public CommonOutDto preorder(WxhbPreorderAddInDto inDto){
		LOG.info("WxhbPreorderService.preorder入参{}", JSON.toJSONString(inDto));
		
		CommonOutDto commonOutDto = new CommonOutDto();
		boolean paramCheckResult = false;
		try {
			paramCheckResult = BeanInitialUtils.checkRequire(inDto);
		} catch (Exception e) {
			LOG.error("WxhbPreorderService.preorder必填校验异常", e);
		}
		
		if (paramCheckResult) {
			paramCheckResult = this.checkParams(inDto);
		}
		
		if (!paramCheckResult) {
			LOG.error("WxhbPreorderService.preorder参数校验失败");
			CommonOutDtoUtil.setResult(commonOutDto, false, CommonWeixinCode.PARAM_ERROR.getCode(), CommonWeixinCode.PARAM_ERROR.getDesc());
			return commonOutDto;
		}
		
		// 存储预下单数据
		WxhbPreorder preorderDb = new WxhbPreorder();
		// preorderDb.setMchId("1326533901"); //商户号
		preorderDb.setMchId(globalConfig.getMchid()); //商户号
		preorderDb.setMchBillNo(this.createMchBillNo(preorderDb.getMchId())); // 商户订单号
		preorderDb.setMchName(globalConfig.getMchname()); //商户名称
		preorderDb.setHbType(inDto.getHbType()); //红包类型
		preorderDb.setTotalAmount(inDto.getTotalAmount()); //红包总金额
		preorderDb.setTotalNum(inDto.getTotalNum());//总人数
		preorderDb.setAmtType(inDto.getAmtType()); //红包金额设置方式
		preorderDb.setWishing(inDto.getWishing()); //红包祝福语
		preorderDb.setActName(inDto.getActName()); //活动名称
		preorderDb.setRemark(inDto.getRemark()); //备注信息
		wxhbPreorderMapper.insertWxhbPreorder(preorderDb);
		
		if (preorderDb.getId() == 0) {
			LOG.error("WxhbPreorderService.preorder插入预下单记录失败");
			CommonOutDtoUtil.setResult(commonOutDto, false, CommonWeixinCode.BUSINESS_ERROR.getCode(), "添加预下单记录失败");
			return commonOutDto;
		}
		
		// 微信红包预下单
		// 参数（按参数名的ASCII码的升序排列）
		SortedMap<String, String> wxParams = new TreeMap<String, String>();
		wxParams.put("nonce_str", WXPayUtil.createNoncestr());
		wxParams.put("mch_billno", preorderDb.getMchBillNo());
		wxParams.put("mch_id", preorderDb.getMchId());
		wxParams.put("wxappid", globalConfig.getAppid()); // 微信公众号appid
		wxParams.put("send_name", preorderDb.getMchName());
		wxParams.put("hb_type", preorderDb.getHbType());
		wxParams.put("total_amount", String.valueOf(preorderDb.getTotalAmount()));
		wxParams.put("total_num", String.valueOf(preorderDb.getTotalNum()));
		wxParams.put("amt_type", preorderDb.getAmtType());
		wxParams.put("wishing", preorderDb.getWishing());
		wxParams.put("act_name", preorderDb.getActName());
		wxParams.put("remark", preorderDb.getRemark());
		wxParams.put("auth_mchid", WxConstant.WEIXIN_AUTH_MCHID);
		wxParams.put("auth_appid", WxConstant.WEIXIN_AUTH_APPID);
		wxParams.put("risk_cntl", WxConstant.WEIXIN_RISK_CONTROL);
		
		String sign = WXPayUtil.createSign("UTF-8", globalConfig.getApikey(), wxParams);
		wxParams.put("sign", sign);
		String requestXML = WXPayUtil.getPrepayXml(wxParams);
		LOG.info("WxhbPreorderService.preorder微信预下单request："+requestXML);
		
		String resXml = null;
/*		try {
			String url = globalConfig.getHbpreorderUrl();
			resXml = Request
					.Post(url)
					.bodyString(requestXML,ContentType.parse("application/xml; charset=UTF-8"))
					.execute().returnContent()
					.asString(Charset.forName("utf-8"));
		} catch (UnsupportedCharsetException e) {
			LOG.error("WxhbPreorderService.preorder微信预下单异常", e);
		} catch (ClientProtocolException e) {
			LOG.error("WxhbPreorderService.preorder微信预下单异常", e);
		} catch (ParseException e) {
			LOG.error("WxhbPreorderService.preorder微信预下单异常", e);
		} catch (IOException e) {
			LOG.error("WxhbPreorderService.preorder微信预下单异常", e);
		}*/
		
		try {
			String url = globalConfig.getHbpreorderUrl();
			String cerFilePath = globalConfig.getCerFilePath();
			resXml = WecatCaSecurityHttpUtil.post(requestXML, preorderDb.getMchId(), cerFilePath, url);
		} catch (Exception e1) {
			LOG.error("WxhbPreorderService.preorder微信预下单异常", e1);
		}
/*		if (true) {
			return null;
		}*/
		LOG.info("WxhbPreorderService.preorder微信预下单response:"+resXml);
		
		Map<String,String> resultMap = null;
		try {
			resultMap = WXPayUtil.xml2map(resXml);
		} catch (JDOMException e) {
			LOG.error("WxhbPreorderService.preorder微信预下单结果解析异常", e);
		} catch (IOException e) {
			LOG.error("WxhbPreorderService.preorder微信预下单结果解析异常", e);
		}

		if (resultMap != null) {
			PreorderResultBean resBean = new PreorderResultBean();
			Map2BeanUtil.transMap2Bean(resultMap, resBean);
			LOG.info("WxhbPreorderService.preorder微信预下单返回Bean:{}", JSON.toJSONString(resBean));
			
			if (!StringUtils.isEmpty(resBean.getReturn_code()) 
					&& resBean.getReturn_code().equals("SUCCESS")) {
				// 通讯成功
				if (resBean.getResult_code().equals("SUCCESS")) {
					// 预下单成功
					String sendTime = resBean.getSend_time();
					
					preorderDb.setResult("success");
					preorderDb.setSendTime(sendTime);
					preorderDb.setDetailId(resBean.getDetail_id());
					preorderDb.setExpireTime(this.getExpireTime(sendTime)); //计算失效时间
					
					// 添加关联关系
					WxhbPreorderTicket preorderTicket = new WxhbPreorderTicket();
					preorderTicket.setHbPreorderId(preorderDb.getId());
					preorderTicket.setTicket(resBean.getSp_ticket());
					preorderTicket.setStatus(0);
					wxhbPreorderMapper.insertWxhbPreorderTicket(preorderTicket);
				} else {
					// 预下单失败
					preorderDb.setResult("fail");
					CommonOutDtoUtil.setResult(commonOutDto, false, CommonWeixinCode.getWeixinCode(resBean.getErr_code()).getCode(), 
							CommonWeixinCode.getWeixinCode(resBean.getErr_code()).getDesc());
				}
			} else {
				// 通讯失败
				preorderDb.setResult("fail");
				CommonOutDtoUtil.setResult(commonOutDto, false, CommonWeixinCode.BUSINESS_ERROR.getCode(), "通讯失败");
			}
			
			// 更新结果
			int result = wxhbPreorderMapper.updateWxhbPreorder(preorderDb);
			LOG.info("WxhbPreorderService.preorder微信预下单update:{}", result > 0 ? "成功":"失败");
		} else {
			CommonOutDtoUtil.setResult(commonOutDto, false, CommonWeixinCode.BUSINESS_ERROR.getCode(), "微信预下单失败，返回为空");
		}
		
		return commonOutDto;
	}
	
	/**
	 * 获取失效时间
	 * 72小时
	 * @param time
	 * @return
	 */
	private Date getExpireTime(String time){
		Date expireTime = null;
		try {
			Date date = sdf.parse(time);
			Calendar cl = Calendar.getInstance();
		    cl.setTime(date);
		    cl.add(Calendar.DATE, 3);
		    expireTime = cl.getTime();
		} catch (java.text.ParseException e) {
			LOG.error("String转换Date异常", e);
		}
		return expireTime;
	}
	
	/**
	 * 生成商户订单号
	 * @param mchId
	 * @return
	 */
	private String createMchBillNo(String mchId){
		String dateFmt = DateFormatUtils.format(new Date(), "yyyyMMdd");
		StringBuilder sb = new StringBuilder();
		sb.append(mchId).append(dateFmt).append(this.createRandomStr());
		return sb.toString();
	}
	
	/**
	 * 校验预下单参数合法性
	 * @param dto
	 * @return
	 */
	private boolean checkParams(WxhbPreorderAddInDto dto){
		
		// 红包类型
		if (WxhbTypeConstant.getWxhbType(dto.getHbType()) == null){
			return false;
		}
		
		if (!StringUtils.isEmpty(dto.getAmtType()) && !"ALL_RAND".equals(dto.getAmtType())) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 生成10位随机数字字符串
	 * @return
	 */
	private String createRandomStr() {
		/*String chars = "0123456789";
		String res = "";
		Random rd = new Random(9);
		for (int i = 0; i < 10; i++) {
			res += chars.charAt(rd.nextInt());
		}
		return res;*/
		return getRandomNumberString(10);
	}
	
	private String getRandomNumberString(int length){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int ran = random.nextInt(9); // 0~9
			sb.append(letterChars[ran]);
		}
		return sb.toString();
	}
	
	private static final char[] letterChars = new char[]{
			'0','1','2','3','4','5','6','7','8','9'};
	
	private static final Random random = new Random();
	
	/**
	 * 分页查询预下单记录
	 * @param inDto
	 * @return
	 */
	public PageResult<WxhbPreorder> queryWxhbPreorderByPage(WxhbPreorderQueryByPageInDto inDto){
		PageResult<WxhbPreorder> result = new PageResult<WxhbPreorder>();
		PreorderQueryVO query = new PreorderQueryVO();
		query.setHbType(inDto.getHbType());
		query.setStatus(inDto.getStatus());
		
		Page page = new Page();
		page.setStart(inDto.getStart());
		page.setLimit(inDto.getLimit());
		
		int total = wxhbPreorderMapper.countWxhbPreorderByPage(query);
		List<WxhbPreorder> list = new ArrayList<WxhbPreorder>();
		if (total > 0) {
			list = wxhbPreorderMapper.queryWxhbPreorderByPage(page, query);
		}
		
		result.setTotal(total);
		result.setRows(list);
		return result;
	}
	
	/**
	 * 查询预下单详情
	 * @param id
	 * @return
	 */
	public WxhbPreorderQueryDetailOutDto queryWxhbPreorderById(int id){
		WxhbPreorderQueryDetailOutDto result = new WxhbPreorderQueryDetailOutDto();
		WxhbPreorder preorder = wxhbPreorderMapper.queryWxhbPreorderById(id);
		if (preorder == null) {
			return null;
		} else {
			BeanUtils.copyProperties(preorder, result, new String[]{"ticket"});
			WxhbPreorderTicket ticket = wxhbPreorderMapper.queryWxhbPreorderTicketByPreorderId(id);
			if (ticket != null) {
				result.setTicket(ticket.getTicket());
			}
		}
		return result;
	}
}
