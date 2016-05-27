package com.cuize.activity.service.impl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.util.CollectionUtils;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cuize.activity.service.dto.GlobalConfig;
import com.cuize.activity.service.dto.common.CommonOutDto;
import com.cuize.activity.service.dto.common.PageResult;
import com.cuize.activity.service.dto.wxlottery.WxhbBindNoticeInDto;
import com.cuize.activity.service.dto.wxlottery.WxhbLotteryAddInDto;
import com.cuize.activity.service.dto.wxlottery.WxhbLotteryBindTicketInDto;
import com.cuize.activity.service.dto.wxlottery.WxhbLotteryDetailQueryOutDto;
import com.cuize.activity.service.dto.wxlottery.WxhbLotteryQueryByPageInDto;
import com.cuize.activity.service.dto.wxlottery.WxhbLotterySetPrizeBucketInDto;
import com.cuize.activity.service.dto.wxlottery.WxhbLotterySetPrizeBucketOutDto;
import com.cuize.activity.service.dto.wxlottery.WxhbLotteryUnBindTicketInDto;
import com.cuize.activity.service.dto.wxlottery.WxhbLotteryUpdateSwitchInDto;
import com.cuize.activity.service.util.CommonOutDtoUtil;
import com.cuize.activity.service.util.CommonWeixinCode;
import com.cuize.activity.service.util.WxOAuthAccessTokenUtil;
import com.cuize.activity.service.weixin.WxhbAddLotteryResultBean;
import com.cuize.activity.service.weixin.WxhbSetLotterySwitchResultBean;
import com.cuize.activity.service.weixin.WxhbSetPrizeBucketResultBean;
import com.cuize.activity.service.weixin.WxhbTicket;
import com.cuize.commons.dao.activity.domain.WxhbLottery;
import com.cuize.commons.dao.activity.domain.WxhbLotteryTicket;
import com.cuize.commons.dao.activity.domain.WxhbPreorder;
import com.cuize.commons.dao.activity.mapper.WxhbLotteryMapper;
import com.cuize.commons.dao.activity.mapper.WxhbPreorderMapper;
import com.cuize.commons.dao.activity.queryvo.common.Page;
import com.cuize.commons.dao.activity.queryvo.lottery.LotteryQueryVO;
import com.cuize.commons.dao.activity.queryvo.preorder.PreorderQueryVO;
import com.cuize.commons.dao.activity.resultvo.WxhbLotteryBindTicketVO;
import com.cuize.commons.dao.activity.resultvo.WxhbLotteryUnbindTicketVO;
import com.cuize.commons.utils.WXPayUtil;

/**
 * 微信红包活动服务
 * @author JackieLan
 *
 */
@Service
@Transactional(value="activityTransactionManager",rollbackFor=Exception.class)
public class WxhbLotteryService {

	private static final Logger LOG = LoggerFactory.getLogger(WxhbLotteryService.class);
	
	@Autowired
	private WxhbLotteryMapper wxhbLotteryMapper;
	
	@Autowired
	private WxhbPreorderMapper wxhbPreorderMapper;
	
	@Autowired
	private GlobalConfig globalConfig;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd mm:hh:ss");
	
	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 创建微信红包活动
	 * @param inDto
	 */
	public CommonOutDto createWxhbLottery(WxhbLotteryAddInDto inDto){
		LOG.info("WxhbLotteryService.createWxhbLottery入参{}", JSON.toJSONString(inDto));
		
		CommonOutDto commonOutDto = new CommonOutDto();
		// 存储微信红包活动记录
		WxhbLottery lotteryDb = new WxhbLottery();
		lotteryDb.setUseTemplate(inDto.getUseTemplate());
		lotteryDb.setLogoUrl(null);
		lotteryDb.setTitle(inDto.getTitle());
		lotteryDb.setDescription(inDto.getDesc());
		lotteryDb.setStatus(inDto.getOnoff());
		lotteryDb.setBeginTime(this.formatDate(inDto.getBeginTime()));
		lotteryDb.setExpireTime(this.formatDate(inDto.getExpireTime()));
		lotteryDb.setTotal(inDto.getTotal());
		wxhbLotteryMapper.insertWxhbLottery(lotteryDb);
		
		if (lotteryDb.getId() == 0) {
			LOG.error("WxhbLotteryService.createWxhbLottery添加红包活动失败");
			CommonOutDtoUtil.setResult(commonOutDto, false, CommonWeixinCode.PARAM_ERROR.getCode(), CommonWeixinCode.PARAM_ERROR.getDesc());
			return commonOutDto;
		}
		
		// 添加微信红包活动
		JSONObject wxParams = new JSONObject();
		wxParams.put("title", lotteryDb.getTitle());
		wxParams.put("desc", lotteryDb.getDescription());
		wxParams.put("onoff", lotteryDb.getStatus());
		wxParams.put("begin_time", this.getUnixTimestamp(lotteryDb.getBeginTime()));
		wxParams.put("expire_time", this.getUnixTimestamp(lotteryDb.getExpireTime()));
		wxParams.put("sponsor_appid", globalConfig.getAppid());
		wxParams.put("total", lotteryDb.getTotal());
		wxParams.put("key", globalConfig.getApikey());//TODO 替换为其他key
		
		LOG.info("WxhbLotteryService.createWxhbLottery添加红包活动request：{}", JSON.toJSONString(wxParams));
		
		String resJsonStr = null;
		try {
			String url = globalConfig.getAddlotteryinfoUrl() + "?access_token=" + inDto.getAccessToken()
					+ "&use_template=" + lotteryDb.getUseTemplate() + "&logo_url=";
			resJsonStr = Request
					.Post(url)
					.bodyString(JSON.toJSONString(wxParams), ContentType.parse("application/json; charset=UTF-8"))
					.execute().returnContent()
					.asString(Charset.forName("utf-8"));
		} catch (UnsupportedCharsetException e) {
			LOG.error("WxhbLotteryService.createWxhbLottery添加红包活动异常", e);
		} catch (ClientProtocolException e) {
			LOG.error("WxhbLotteryService.createWxhbLottery添加红包活动异常", e);
		} catch (ParseException e) {
			LOG.error("WxhbLotteryService.createWxhbLottery添加红包活动异常", e);
		} catch (IOException e) {
			LOG.error("WxhbLotteryService.createWxhbLottery添加红包活动异常", e);
		}
		LOG.info("WxhbLotteryService.createWxhbLottery添加红包活动response:{}", resJsonStr);
		
		WxhbAddLotteryResultBean resultBean = JSON.parseObject(resJsonStr, WxhbAddLotteryResultBean.class);
		
		if (resultBean != null && resultBean.getErrcode() == 0) {
			// 添加红包活动成功
			lotteryDb.setLotteryId(resultBean.getLottery_id());
			lotteryDb.setPageId(resultBean.getPage_id());
			lotteryDb.setResult("success");
		} else {
			// 添加红包活动失败
			lotteryDb.setResult("fail");
			if (resultBean != null) {
				CommonOutDtoUtil.setResult(commonOutDto, false, String.valueOf(resultBean.getErrcode()), resultBean.getErrmsg());
			} else {
				CommonOutDtoUtil.setResult(commonOutDto, false, CommonWeixinCode.BUSINESS_ERROR.getCode(), "微信添加红包活动失败，返回为空");
			}
		}
		int updateResult = wxhbLotteryMapper.updateWxhbLottery(lotteryDb);
		LOG.info("WxhbLotteryService.createWxhbLottery添加红包活动update:{}", updateResult > 0 ? "成功":"失败");
		return commonOutDto;
	}
	
	/**
	 * 录入红包信息
	 * @param inDto
	 */
	public CommonOutDto setWxhbLotteryPrizeBucket(WxhbLotterySetPrizeBucketInDto inDto){
		LOG.info("WxhbLotteryService.setWxhbLotteryPrizeBucket入参{}", JSON.toJSONString(inDto));
		
		CommonOutDto commonOutDto = new CommonOutDto();
		WxhbLotterySetPrizeBucketOutDto dataDto = new WxhbLotterySetPrizeBucketOutDto();
		
		// 查询微信红包活动ID
		WxhbLottery lottery = wxhbLotteryMapper.queryWxhbLotteryById(inDto.getHbLotteryId());
		if (lottery == null) {
			LOG.error("WxhbLotteryService.setWxhbLotteryPrizeBucket录入红包信息：不存在活动{}", inDto.getHbLotteryId());
			CommonOutDtoUtil.setResult(commonOutDto, false, CommonWeixinCode.BUSINESS_ERROR.getCode(), "不存在该活动");
			return commonOutDto;
		} else if (StringUtils.isEmpty(lottery.getLotteryId()) || !lottery.getResult().equals("success")){
			LOG.error("WxhbLotteryService.setWxhbLotteryPrizeBucket录入红包信息：活动状态异常id={},lotteryId={},result={}", 
					new Object[]{lottery.getId(), lottery.getLotteryId(), lottery.getResult()});
			CommonOutDtoUtil.setResult(commonOutDto, false, CommonWeixinCode.BUSINESS_ERROR.getCode(), "该活动状态异常");
			return commonOutDto;
		}
		
		// 录入微信红包信息
		JSONArray prizeInfoList = new JSONArray();
		for (String str : inDto.getTicket()) {
			JSONObject obj = new JSONObject();
			obj.put("ticket", str);
			prizeInfoList.add(obj);
		}
		JSONObject wxParams = new JSONObject();
		wxParams.put("lottery_id", lottery.getLotteryId());
		wxParams.put("mchid", globalConfig.getMchid());
		wxParams.put("sponsor_appid", globalConfig.getAppid());
		wxParams.put("prize_info_list", prizeInfoList);
		LOG.info("WxhbLotteryService.setWxhbLotteryPrizeBucket录入红包信息request：{}", JSON.toJSONString(wxParams));
		
		String resJsonStr = null;
		try {
			String url = globalConfig.getSetprizebucketUrl() + "?access_token=" + inDto.getAccessToken();
			resJsonStr = Request
					.Post(url)
					.bodyString(JSON.toJSONString(wxParams), ContentType.parse("application/json; charset=UTF-8"))
					.execute().returnContent()
					.asString(Charset.forName("utf-8"));
		} catch (UnsupportedCharsetException e) {
			LOG.error("WxhbLotteryService.setWxhbLotteryPrizeBucket录入红包信息异常", e);
		} catch (ClientProtocolException e) {
			LOG.error("WxhbLotteryService.setWxhbLotteryPrizeBucket录入红包信息异常", e);
		} catch (ParseException e) {
			LOG.error("WxhbLotteryService.setWxhbLotteryPrizeBucket录入红包信息异常", e);
		} catch (IOException e) {
			LOG.error("WxhbLotteryService.createWxhbLottery录入红包信息异常", e);
		}
		LOG.info("WxhbLotteryService.setWxhbLotteryPrizeBucket录入红包信息response:{}", resJsonStr);
		
		// 存储关联关系
		/*WxhbSetPrizeBucketResultBean resultBean = JSON.parseObject(resJsonStr, WxhbSetPrizeBucketResultBean.class);*/
		JSONObject obj = JSON.parseObject(resJsonStr);
		/*WxhbSetPrizeBucketResultBean resultBean = new WxhbSetPrizeBucketResultBean();
		resultBean.setErrcode(errcode);*/
		if (obj != null && obj.getIntValue("errcode") == 0) {
			// 录入红包信息成功
		/*	private List<WxhbTicket> repeat_ticket_list; // 重复使用的ticket列表，如为空，将不返回
			private List<WxhbTicket> expire_ticket_list; // 过期的ticket列表，如为空，将不返回
			private List<WxhbTicket> invalid_amount_ticket_list; // 金额不在大于1元，小于1000元的ticket列表，如为空，将不返回
			private List<WxhbTicket> wrong_authmchid_ticket_list; // 原因：生成红包的时候，授权商户号auth_mchid和auth_appid没有写摇周边的商户号
			private List<WxhbTicket> invalid_ticket_list; // ticket解析失败，可能有错别字符或不完整 
*/			
			List<WxhbTicket> allFailTicket = new ArrayList<WxhbTicket>();
			List<WxhbTicket> repeat_ticket_list = new ArrayList<WxhbTicket>();
			List<WxhbTicket> expire_ticket_list = new ArrayList<WxhbTicket>();
			List<WxhbTicket> invalid_amount_ticket_list = new ArrayList<WxhbTicket>();
			List<WxhbTicket> wrong_authmchid_ticket_list = new ArrayList<WxhbTicket>();
			List<WxhbTicket> invalid_ticket_list = new ArrayList<WxhbTicket>();
			
			if (obj.containsKey("repeat_ticket_list") && obj.getJSONObject("repeat_ticket_list") != null) {
				repeat_ticket_list = this.extractTicket(obj.getJSONObject("repeat_ticket_list"));
				allFailTicket.addAll(repeat_ticket_list);
			}
			if (obj.containsKey("expire_ticket_list") && obj.getJSONObject("expire_ticket_list") != null) {
				expire_ticket_list = this.extractTicket(obj.getJSONObject("expire_ticket_list"));
				allFailTicket.addAll(expire_ticket_list);
			}
			if (obj.containsKey("invalid_amount_ticket_list") && obj.getJSONObject("invalid_amount_ticket_list") != null) {
				invalid_amount_ticket_list = this.extractTicket(obj.getJSONObject("invalid_amount_ticket_list"));
				allFailTicket.addAll(invalid_amount_ticket_list);
			}
			if (obj.containsKey("wrong_authmchid_ticket_list") && obj.getJSONObject("wrong_authmchid_ticket_list") != null) {
				wrong_authmchid_ticket_list = this.extractTicket(obj.getJSONObject("wrong_authmchid_ticket_list"));
				allFailTicket.addAll(wrong_authmchid_ticket_list);
			}
			if (obj.containsKey("invalid_ticket_list") && obj.getJSONObject("invalid_ticket_list") != null) {
				invalid_ticket_list = this.extractTicket(obj.getJSONObject("invalid_ticket_list"));
				allFailTicket.addAll(invalid_ticket_list);
			}
		
			List<String> successTicketList = this.getInsertSuccessTicket(inDto.getTicket(), allFailTicket);
			if (!CollectionUtils.isEmpty(successTicketList)) {
				List<WxhbLotteryTicket> lotteryTicketList = new ArrayList<WxhbLotteryTicket>();
				for (String s : successTicketList) {
					WxhbLotteryTicket wlt = new WxhbLotteryTicket();
					wlt.setHbLotteryId(lottery.getId());
					wlt.setLotteryId(lottery.getLotteryId());
					wlt.setTicket(s);
					lotteryTicketList.add(wlt);
				}
				int batchInsertResult = wxhbLotteryMapper.batchInsertWxhbLotteryTicket(lotteryTicketList);
				LOG.info("WxhbLotteryService.setWxhbLotteryPrizeBucket录入红包信息添加关联关系:{}", batchInsertResult == lotteryTicketList.size() ? "成功":"失败");
				
				// 更新wxhb_preorder_ticket绑定状态
				int batchUpdateTicketResult = wxhbPreorderMapper.updateWxhbPreorderTicketStatusByTicket(successTicketList);
				LOG.info("WxhbLotteryService.setWxhbLotteryPrizeBucket录入红包信息更新ticket状态:{}", batchUpdateTicketResult == successTicketList.size() ? "成功":"失败");
			} else {
				LOG.error("WxhbLotteryService.setWxhbLotteryPrizeBucket录入红包信息成功时ticket为空");
				CommonOutDtoUtil.setResult(commonOutDto, false, CommonWeixinCode.BUSINESS_ERROR.getCode(), "微信录入红包信息成功ticket为空");
			}
			
			dataDto.setSuccessNum(obj.getIntValue("success_num"));
			dataDto.setExpireTicketList(expire_ticket_list);
			dataDto.setInvalidAmountTicketList(invalid_amount_ticket_list);
			dataDto.setInvalidTicketList(invalid_ticket_list);
			dataDto.setRepeatTicketList(repeat_ticket_list);
			dataDto.setWrongAuthmchidTicketList(wrong_authmchid_ticket_list);
			commonOutDto.setData(dataDto);
		} else {
			// 录入红包信息失败
			LOG.error("WxhbLotteryService.setWxhbLotteryPrizeBucket录入红包信息失败");
			if (obj != null) {
				CommonOutDtoUtil.setResult(commonOutDto, false, String.valueOf(obj.getIntValue("errcode")), obj.getString("errmsg"));
			} else {
				CommonOutDtoUtil.setResult(commonOutDto, false, CommonWeixinCode.BUSINESS_ERROR.getCode(), "微信录入红包信息失败，返回为空");
			}
		}
		
		return commonOutDto;
	}
	
	private List<WxhbTicket> extractTicket(JSONObject obj){
		List<WxhbTicket> tickets = new ArrayList<WxhbTicket>();
		Set<String> keys = obj.keySet();
		for (String s : keys) {
			WxhbTicket t  = new WxhbTicket();
			t.setTicket(obj.getString(s));
			tickets.add(t);
		}
		return tickets;
	}
	
	/**
	 * 设置微信红包活动开关
	 * @param inDto
	 */
	public CommonOutDto updateWxhbLotterySwitch(WxhbLotteryUpdateSwitchInDto inDto){
		LOG.info("WxhbLotteryService.updateWxhbLotterySwitch入参{}", JSON.toJSONString(inDto));
		
		CommonOutDto commonOutDto = new CommonOutDto();
		// 查询微信红包活动ID
		WxhbLottery lottery = wxhbLotteryMapper.queryWxhbLotteryById(inDto.getHbLotteryId());
		if (lottery == null) {
			LOG.error("WxhbLotteryService.updateWxhbLotterySwitch设置微信红包活动开关：不存在活动{}", inDto.getHbLotteryId());
			CommonOutDtoUtil.setResult(commonOutDto, false, CommonWeixinCode.BUSINESS_ERROR.getCode(), "不存在该活动");
			return commonOutDto;
		} else if (StringUtils.isEmpty(lottery.getLotteryId()) || !lottery.getResult().equals("success")){
			LOG.error("WxhbLotteryService.updateWxhbLotterySwitch设置微信红包活动开关：活动状态异常id={},lotteryId={},result={}", 
					new Object[]{lottery.getId(), lottery.getLotteryId(), lottery.getResult()});
			CommonOutDtoUtil.setResult(commonOutDto, false, CommonWeixinCode.BUSINESS_ERROR.getCode(), "该活动状态异常");
			return commonOutDto;
		}
		
		// 设置微信红包活动开关
		String resJsonStr = null;
		try {
			String url = globalConfig.getSetlotteryswitchUrl() + "?access_token=" + inDto.getAccessToken()
					+ "&lottery_id=" + lottery.getLotteryId() + "&onoff=" + inDto.getOnoff();
			LOG.info("WxhbLotteryService.updateWxhbLotterySwitch设置微信红包活动开关request:{}", url);
			resJsonStr = Request
					.Get(url)
					.execute().returnContent()
					.asString(Charset.forName("utf-8"));
		} catch (UnsupportedCharsetException e) {
			LOG.error("WxhbLotteryService.updateWxhbLotterySwitch设置微信红包活动开关异常", e);
		} catch (ClientProtocolException e) {
			LOG.error("WxhbLotteryService.updateWxhbLotterySwitch设置微信红包活动开关异常", e);
		} catch (ParseException e) {
			LOG.error("WxhbLotteryService.updateWxhbLotterySwitch设置微信红包活动开关异常", e);
		} catch (IOException e) {
			LOG.error("WxhbLotteryService.updateWxhbLotterySwitch设置微信红包活动开关异常", e);
		}
		LOG.info("WxhbLotteryService.updateWxhbLotterySwitch设置微信红包活动开关response:{}", resJsonStr);
		
		
		WxhbSetLotterySwitchResultBean resultBean = JSON.parseObject(resJsonStr, WxhbSetLotterySwitchResultBean.class);
		if (resultBean !=null && resultBean.getErrcode() == 0) {
			// 设置微信红包活动开关成功
			// 更新数据库红包活动状态
			int updateResult = wxhbLotteryMapper.updateWxhbLotteryStatus(inDto.getOnoff(), lottery.getId());
			LOG.info("WxhbLotteryService.updateWxhbLotterySwitch设置微信红包活动开关update:{}", updateResult > 0 ? "成功":"失败");
		} else {
			// 设置微信红包活动开关失败
			if (resultBean != null) {
				CommonOutDtoUtil.setResult(commonOutDto, false, String.valueOf(resultBean.getErrcode()), resultBean.getErrmsg());
			} else {
				CommonOutDtoUtil.setResult(commonOutDto, false, CommonWeixinCode.BUSINESS_ERROR.getCode(), "设置微信红包活动开关失败，返回为空");
			}
		}
		return commonOutDto;
	}
	
	/**
	 * 微信红包绑定用户通知
	 * @param inDto
	 */
	public void wxhbLotteryTicketBindNotice(WxhbBindNoticeInDto inDto){
		LOG.info("WxhbLotteryService.wxhbLotteryTicketBindNotice入参{}", JSON.toJSONString(inDto));
		
		// 查询微信红包活动
		WxhbLottery lottery = wxhbLotteryMapper.queryWxhbLotteryByLotteryId(inDto.getLotteryId());
		if (lottery == null) {
			LOG.error("WxhbLotteryService.wxhbLotteryTicketBindNotice微信红包绑定用户通知：不存在活动{}", inDto.getLotteryId());
			return;
		} else if (StringUtils.isEmpty(lottery.getLotteryId())){
			LOG.error("WxhbLotteryService.wxhbLotteryTicketBindNotice微信红包绑定用户通知：活动状态异常id={},lotteryId={}", 
					new Object[]{lottery.getId(), lottery.getLotteryId()});
			return;
		}
		
		// 更新管理关系
		WxhbLotteryTicket lotteryTicket = new WxhbLotteryTicket();
		lotteryTicket.setBindTime(inDto.getBindTime());
		lotteryTicket.setLotteryId(inDto.getLotteryId());
		lotteryTicket.setOpenId(inDto.getOpenId());
		lotteryTicket.setTicket(inDto.getTicket());
		lotteryTicket.setMoney(inDto.getMoney());
		int updateResult = wxhbLotteryMapper.updateWxhbLotteryTicket(lotteryTicket);
		LOG.info("WxhbLotteryService.wxhbLotteryTicketBindNotice微信红包绑定用户通知更新ticket:{}", updateResult > 0 ? "成功":"失败");
	}
	
	/**
	 * 查询微信红包活动详情
	 * @param id
	 * @return
	 */
	public WxhbLotteryDetailQueryOutDto queryWxhbLotteryDetail(int id){
		WxhbLotteryDetailQueryOutDto resultDto = new WxhbLotteryDetailQueryOutDto();
		WxhbLottery lottery = wxhbLotteryMapper.queryWxhbLotteryById(id);
		if (lottery != null) {
			BeanUtils.copyProperties(lottery, resultDto);
			return resultDto;
		} else {
			return null;
		}
		
	}
	
	/**
	 * 获取录入成功的ticket
	 * @param allTicket
	 * @param resultBean
	 * @return
	 */
	private List<String> getInsertSuccessTicket(List<String> allTicket, List<WxhbTicket> allFailTicket){
		List<String> failTicketList = new ArrayList<String>();
		for (WxhbTicket t : allFailTicket) {
			failTicketList.add(t.getTicket());
		}
		
		allTicket.removeAll(failTicketList);
		return allTicket;
	}
	
	/**
	 * 获取UNIX时间戳
	 * @param date
	 * @return
	 */
	private long getUnixTimestamp(Date date){
		return date.getTime() / 1000;
	}
	/**
	 * 格式化日期
	 * @param str
	 * @return
	 */
	private Date formatDate(String str){
		Date result = null;
		try {
			result = sdf.parse(str);
		} catch (java.text.ParseException e) {
			LOG.error("String转换Date异常", e);
		}
		return result;
	}
	
	/**
	 * 分页查询红包活动
	 * @param inDto
	 * @return
	 */
	public PageResult<WxhbLottery> queryWxhbLotteryByPage(WxhbLotteryQueryByPageInDto inDto){
		PageResult<WxhbLottery> result = new PageResult<WxhbLottery>();
		LotteryQueryVO query = new LotteryQueryVO();
		query.setTitle(inDto.getTitle());
		query.setStatus(inDto.getStatus());
		
		Page page = new Page();
		page.setStart(inDto.getStart());
		page.setLimit(inDto.getLimit());
		
		int total = wxhbLotteryMapper.countWxhbLotteryPage(query);
		List<WxhbLottery> list = new ArrayList<WxhbLottery>();
		if (total > 0) {
			list = wxhbLotteryMapper.queryWxhbLotteryByPage(page, query);
		}
		
		result.setTotal(total);
		result.setRows(list);
		return result;
	}
	
	/**
	 * 分页查询红包活动已绑定的ticket
	 */
	public PageResult<WxhbLotteryBindTicketVO> queryWxhbLotteryBindTicketByPage(WxhbLotteryBindTicketInDto inDto){
		PageResult<WxhbLotteryBindTicketVO> result = new PageResult<WxhbLotteryBindTicketVO>();
		
		Page page = new Page();
		page.setStart(inDto.getStart());
		page.setLimit(inDto.getLimit());
		
		int total = wxhbLotteryMapper.countWxhbLotteryBindTicketByPage(inDto.getHbLotteryId());
		List<WxhbLotteryBindTicketVO> list = new ArrayList<WxhbLotteryBindTicketVO>();
		if (total > 0) {
			list = wxhbLotteryMapper.queryWxhbLotteryBindTicketByPage(page, inDto.getHbLotteryId());
		}
		result.setTotal(total);
		result.setRows(list);
		return result;
	}
	
	/**
	 * 分页查询未使用的有效红包
	 * @param inDto
	 * @return
	 */
	public PageResult<WxhbLotteryUnbindTicketVO> queryWxhbUnBindTicketByPage(WxhbLotteryUnBindTicketInDto inDto){
		PageResult<WxhbLotteryUnbindTicketVO> result = new PageResult<WxhbLotteryUnbindTicketVO>();
		
		Page page = new Page();
		page.setStart(inDto.getStart());
		page.setLimit(inDto.getLimit());
		
		int total = wxhbLotteryMapper.countUnBindTicketByPage(page);
		List<WxhbLotteryUnbindTicketVO> list = new ArrayList<WxhbLotteryUnbindTicketVO>();
		if (total > 0) {
			list = wxhbLotteryMapper.queryUnBindTicketByPage(page);
		}
		result.setTotal(total);
		result.setRows(list);
		
		return result;
	}
}
