package com.cuize.activity.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.cuize.activity.service.dto.common.CommonOutDto;
import com.cuize.activity.service.dto.common.PageResult;
import com.cuize.activity.service.dto.lottery.LotteryAddInDto;
import com.cuize.activity.service.dto.lottery.LotteryQueryByPageInDto;
import com.cuize.activity.service.dto.wxpreorder.WxhbPreorderQueryByPageInDto;
import com.cuize.activity.service.util.CommonOutDtoUtil;
import com.cuize.activity.service.util.CommonWeixinCode;
import com.cuize.commons.dao.activity.domain.Lottery;
import com.cuize.commons.dao.activity.domain.WxhbPreorder;
import com.cuize.commons.dao.activity.mapper.LotteryMapper;
import com.cuize.commons.dao.activity.queryvo.common.Page;
import com.cuize.commons.dao.activity.queryvo.lottery.LotteryQueryVo;
import com.cuize.commons.dao.activity.queryvo.preorder.WxPreorderQueryVO;

/**
 * 红包活动服务
 * @author Administrator
 *
 */
@Service
@Transactional(value="activityTransactionManager",rollbackFor=Exception.class)
public class LotteryService {

	private static final Logger LOG = LoggerFactory.getLogger(LotteryService.class);
	
	@Autowired
	private LotteryMapper lotteryMapper;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 创建微信红包活动
	 * @param inDto
	 * @return
	 */
	public CommonOutDto createLottery(LotteryAddInDto inDto){
		LOG.info("LotteryService.createLottery入参{}", JSON.toJSONString(inDto));
		
		CommonOutDto commonOutDto = new CommonOutDto();
		
		Lottery lottery = new Lottery();
		lottery.setTitle(inDto.getTitle());
		lottery.setDescription(inDto.getDescription());
		lottery.setStatus(inDto.getStatus());
		lottery.setBeginTime(this.formatDate(inDto.getBeginTime()));
		lottery.setExpireTime(this.formatDate(inDto.getExpireTime()));
		lottery.setHbType(inDto.getHbType());
		lottery.setTotalAmount(inDto.getTotalAmount());
		lottery.setTotal(inDto.getTotal());
		lottery.setTotalTicket(inDto.getTotalTicket());
		lottery.setAmtType(inDto.getAmtType());
		lottery.setWishing(inDto.getWishing());
		lottery.setAmountType(inDto.getAmountType());
		lottery.setPerMinAmount(inDto.getPerMinAmount());
		lottery.setPerMaxAmount(inDto.getPerMaxAmount());
		
		int result = lotteryMapper.insertLottery(lottery);
		if (result == 0) {
			CommonOutDtoUtil.setResult(commonOutDto, false, CommonWeixinCode.BUSINESS_ERROR.getCode(), "微信红包活动创建失败");
		}
		return commonOutDto;
	}	
	
	/**
	 * 查询最新的一个待处理活动
	 * @return
	 */
	public Lottery queryLatestLottery(){
		Lottery result = null;
		result = lotteryMapper.queryLatestLottery();
		if (result!=null) {
			int updateResult = lotteryMapper.updateLottery(result.getId(), 1);
			if (updateResult == 0) {
				result = null;
			}
		}
		return result;
	}
	
	/**
	 * 变更活动处理状态
	 * @param id
	 * @param processStatus
	 * @return
	 */
	public int updateLottery(int id, int processStatus){
		return lotteryMapper.updateLottery(id, processStatus);
	}
	
	
	/**
	 * 分页查询Lottery
	 * @param inDto
	 * @return
	 */
	public PageResult<Lottery> queryLotteryByPage(LotteryQueryByPageInDto inDto){
		PageResult<Lottery> result = new PageResult<Lottery>();
		
		Page page = new Page();
		page.setStart(inDto.getStart());
		page.setLimit(inDto.getLimit());
		
		LotteryQueryVo query = new LotteryQueryVo();
		query.setProcessStatus(inDto.getProcessStatus());
		
		int total = lotteryMapper.countLotteryByPage(query);
		List<Lottery> list = new ArrayList<Lottery>();
		if (total > 0) {
			list = lotteryMapper.queryLotteryByPage(page, query);
		}
		
		result.setTotal(total);
		result.setRows(list);
		return result;
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
}
