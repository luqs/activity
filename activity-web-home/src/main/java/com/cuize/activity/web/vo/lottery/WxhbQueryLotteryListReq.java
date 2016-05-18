package com.cuize.activity.web.vo.lottery;

/**
 * 非分页列表查询红包活动
 * @author JackieLan
 *
 */
public class WxhbQueryLotteryListReq {

	/**
	 * 活动状态：0关闭，1开启，2全部
	 */
	private int status = 1;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
