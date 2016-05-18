package com.cuize.activity.web.vo.lottery;

import java.util.List;

/**
 * 红包活动录入Ticket请求对象
 * @author JackieLan
 *
 */
public class WxhbSetPrizeBucketReq {

	/**
	 * 活动ID
	 */
	private int hbLotteryId; 
	
	/**
	 * ticket列表
	 */
	private List<String> ticket;

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
}
