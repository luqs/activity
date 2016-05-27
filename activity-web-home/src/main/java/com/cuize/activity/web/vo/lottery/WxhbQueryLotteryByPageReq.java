package com.cuize.activity.web.vo.lottery;

import com.cuize.activity.web.vo.common.PageInfo;

/**
 * 分页查询红包活动请求对象
 * @author JackieLan
 *
 */
public class WxhbQueryLotteryByPageReq extends PageInfo {

	/**
	 * 活动状态：0关闭，1开启，2全部
	 */
	private int status = 2;
	
	/**
	 * 活动名称，模糊查询
	 */
	private String title;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
