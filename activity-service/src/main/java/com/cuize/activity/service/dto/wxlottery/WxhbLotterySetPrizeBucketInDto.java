package com.cuize.activity.service.dto.wxlottery;

import java.util.List;

/**
 * 录入红包信息DTO
 * @author JackieLan
 *
 */
public class WxhbLotterySetPrizeBucketInDto {

	/**
	 * 活动ID
	 */
	private int hbLotteryId; 
	
	/**
	 * ticket列表
	 */
	private List<String> ticket;
	
	/**
	 * 微信令牌
	 */
	private String accessToken;

	public int getHbLotteryId() {
		return hbLotteryId;
	}

	public void setHbLotteryId(int hbLotteryId) {
		this.hbLotteryId = hbLotteryId;
	}

	public List<String> getTicket() {
		return ticket;
	}

	public void setTicket(List<String> ticket) {
		this.ticket = ticket;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	
}
