package com.cuize.activity.web.task;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cuize.activity.service.dto.common.CommonOutDto;
import com.cuize.activity.service.dto.wxlottery.WxhbLotteryAddInDto;
import com.cuize.activity.service.dto.wxlottery.WxhbLotterySetPrizeBucketInDto;
import com.cuize.activity.service.dto.wxpreorder.WxhbPreorderAddInDto;
import com.cuize.activity.service.impl.LotteryService;
import com.cuize.activity.service.impl.WxhbLotteryService;
import com.cuize.activity.service.impl.WxhbPreorderService;
import com.cuize.activity.web.util.oauth.OAuthTokenUtil;
import com.cuize.commons.dao.activity.domain.Lottery;
import com.cuize.commons.dao.activity.domain.WxhbLottery;

/**
 * 嗨摇红包活动处理定时器
 * @author Administrator
 *
 */
@Component
public class LotteryAutoProcessTask {


	private static final Logger LOG = LoggerFactory.getLogger(AccessTokenTask.class);
	
	@Autowired
	private LotteryService lotteryService;
	
	@Autowired
	private WxhbLotteryService wxhbLotteryService;
	
	@Autowired
	private WxhbPreorderService wxhbPreorderService;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
     * 定时处理
     */
	@Scheduled(cron = "*/60 * * * * ?")
	@PostConstruct
    public void process(){
		LOG.info("LotteryAutoCreateTask.process:start");
		Lottery target = lotteryService.queryLatestLottery();
		if (target!=null) {
			/*if (true) {
				return;
			}*/
			boolean result = false;
			int updateRes = 0;
			// 创建微信红包活动
			result = this.createWxhbLottery(target);
			
			// 微信红包预下单
			if (!result) {
				updateRes = lotteryService.updateLottery(target.getId(), 3);
				LOG.error("LotteryAutoCreateTask.process:创建微信红包活动异常，变更状态{}", updateRes > 0 ? "成功":"失败");
				return;
			}
			LOG.info("LotteryAutoCreateTask.process:创建微信红包活动success");
			result = this.createWxhbPreorder(target);
			
			// 关联微信红包活动和预下单ticket
			if (!result) {
				updateRes = lotteryService.updateLottery(target.getId(), 3);
				LOG.error("LotteryAutoCreateTask.process:微信红包预下单异常，变更状态{}", updateRes > 0 ? "成功":"失败");
				return;
			}
			LOG.info("LotteryAutoCreateTask.process:微信红包预下单success");
			result = this.setPrizeBucket(target);
			
			if (!result) {
				updateRes = lotteryService.updateLottery(target.getId(), 3);
				LOG.error("LotteryAutoCreateTask.process:关联微信红包活动和预下单ticket异常，变更状态{}", updateRes > 0 ? "成功":"失败");
				return;
			} else {
				updateRes = lotteryService.updateLottery(target.getId(), 2);
				LOG.info("LotteryAutoCreateTask.process:关联微信红包活动和预下单ticket成功，变更状态{}", updateRes > 0 ? "成功":"失败");
			}
		} else {
			LOG.info("LotteryAutoCreateTask.process:当前无待处理活动");
		}
	}
	
	/**
	 * 创建微信红包活动
	 * @param lottery
	 * @return
	 */
	private boolean createWxhbLottery(Lottery lottery){
		WxhbLotteryAddInDto inDto = new WxhbLotteryAddInDto();
		inDto.setTitle(lottery.getTitle());
		inDto.setDesc(lottery.getDescription());
		inDto.setOnoff(lottery.getStatus());
		
		String btStr = sdf.format(lottery.getBeginTime());
		String etStr = sdf.format(lottery.getExpireTime());
		inDto.setBeginTime(btStr);
		inDto.setExpireTime(etStr);
		inDto.setTotal(lottery.getTotal());
		inDto.setAccessToken(OAuthTokenUtil.access_token);
		inDto.setCzhbLotteryId(lottery.getId());
		
		CommonOutDto result = wxhbLotteryService.createWxhbLottery(inDto);
		if (result.isSuccess()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 微信红包预下单
	 * @param lottery
	 * @return
	 */
	private boolean createWxhbPreorder(Lottery lottery){
		boolean result = false;
		
		// 创建totalTicket份红包数量
		for (int i = 0; i < lottery.getTotalTicket(); i ++) {
			WxhbPreorderAddInDto inDto = new WxhbPreorderAddInDto();
			inDto.setHbType("NORMAL");
			
			// TODO 计算
			if (lottery.getAmountType().equals("FIX")) {
				// 固定金额
				inDto.setTotalAmount(lottery.getPerMinAmount());
			} else if (lottery.getAmountType().equals("RANDOM")){
				// 随机金额
				inDto.setTotalAmount(this.getRandomAmount(lottery.getPerMinAmount(), lottery.getPerMaxAmount()));
			} else {
				LOG.error("LotteryAutoCreateTask.process:红包预下单失败，跳出循环终止预下单，原因红包金额类型非法{}",lottery.getId());
				break;
			}
			
			inDto.setTotalNum(1);
			inDto.setAmtType("ALL_RAND");
			inDto.setWishing(lottery.getWishing());
			inDto.setActName(lottery.getTitle());
			inDto.setRemark(lottery.getTitle());
			inDto.setCzhbLotteryId(lottery.getId());
			
			CommonOutDto preorderRes = wxhbPreorderService.preorder(inDto);
			// 一个成功则成功
			// TODO 后续完善异常类型，对于某些异常可以终止循环
			if (preorderRes.getResultCode().equals("NOTENOUGH")) {
				// 帐号余额不足，请到商户平台充值后再重试
				LOG.error("LotteryAutoCreateTask.process:红包预下单失败，跳出循环终止预下单，原因{}", preorderRes.getResultCode());
				break;
			} else {
				result = result || preorderRes.isSuccess();
			}
		}
		
		return result;
	}
	
	private int getRandomAmount(int min,int max){
		Random rand = new Random();  
		return rand.nextInt(max - min + 1) + min;
	}
	
	/**
	 * 往红包活动东录入ticket
	 * @param lottery
	 * @return
	 */
	private boolean setPrizeBucket(Lottery lottery){
		boolean result = false;
		WxhbLottery wxhbLottery = wxhbLotteryService.queryWxhbLotteryByCzhbLotteryId(lottery.getId());
		List<String> ticketList= wxhbPreorderService.queryWxhbPreorderTicketByCzhbLotteryId(lottery.getId());
		if (wxhbLottery !=null && ticketList != null && !ticketList.isEmpty()) {
			// 每次最多100个ticket
			int allCount = ticketList.size()/100;
			int mode = ticketList.size() % 100;
			int count = 0;
			if (allCount == 0){
				count = 1; 
			} else {
				count = mode > 0 ? allCount+1:allCount;
			}
			LOG.info("LotteryAutoCreateTask.process：录入红包次数{}", count);
			for (int i =0 ; i < count; i++) {
				WxhbLotterySetPrizeBucketInDto inDto = new WxhbLotterySetPrizeBucketInDto();
				inDto.setHbLotteryId(wxhbLottery.getId());
				inDto.setAccessToken(OAuthTokenUtil.access_token);
				inDto.setTicket(this.splitTicket(ticketList, i));
				
				CommonOutDto setPrizeBucketRes = wxhbLotteryService.setWxhbLotteryPrizeBucket(inDto);
				result = result || setPrizeBucketRes.isSuccess();
			}
		} else {
			if (wxhbLottery == null) {
				LOG.error("LotteryAutoCreateTask.process：无法查找到微信红包活动");
			}
			if (ticketList == null || ticketList.isEmpty()) {
				LOG.error("LotteryAutoCreateTask.process：无法查找到微信红包预下单数据");
			}
		}
		return result;
	}
	
	/**
	 * 获取子ticket列表
	 * @param ticketList
	 * @param count
	 * @return
	 */
	private List<String> splitTicket(List<String> ticketList, int index){ 
		int len = ticketList.size();
		int startIndex = index * 100;
		int endIndex = (index + 1)*100;
		if (endIndex > len) {
			endIndex = len;
		}
		LOG.info("LotteryAutoCreateTask.process：startIndex={},endIndex={}", startIndex, endIndex);
		List<String> subTicketList = ticketList.subList(startIndex, endIndex);
		return subTicketList;
	}
}
