package com.cuize.activity.service.dto.wxlottery;

/**
 * 设置红包活动开关DTO
 * @author JackieLan
 *
 */
public class WxhbLotteryUpdateSwitchInDto {

	/**
	 * 活动ID
	 */
	private int hbLotteryId;
	
	/**
	 * 活动抽奖开关：
		1开启
		0关闭
	 */
	private int onoff;
	
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

	public int getOnoff() {
		return onoff;
	}

	public void setOnoff(int onoff) {
		this.onoff = onoff;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	
}
