package com.cuize.activity.web.vo.lottery;

/**
 * 红包活动开关请求对象
 * @author JackieLan
 *
 */
public class WxhbSetLotterySwitchReq {

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
}
