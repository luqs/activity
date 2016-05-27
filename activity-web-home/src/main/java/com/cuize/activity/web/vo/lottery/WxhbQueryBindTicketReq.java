package com.cuize.activity.web.vo.lottery;

import com.cuize.activity.web.vo.common.PageInfo;

/**
 * 查询红包活动已录入的ticket请求对象
 * @author JackieLan
 *
 */
public class WxhbQueryBindTicketReq extends PageInfo{

	/**
	 * 活动ID
	 */
	private int hbLotteryId;

	public int getHbLotteryId() {
		return hbLotteryId;
	}

	public void setHbLotteryId(int hbLotteryId) {
		this.hbLotteryId = hbLotteryId;
	}
	
	
}
