package com.cuize.activity.service.weixin;

/**
 * 添加微信红包活动返回对象
 * @author JackieLan
 *
 */
public class WxhbAddLotteryResultBean {

	private int errcode;
	private String errmsg;
	private String lottery_id;
	private int page_id;
	public int getErrcode() {
		return errcode;
	}
	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public String getLottery_id() {
		return lottery_id;
	}
	public void setLottery_id(String lottery_id) {
		this.lottery_id = lottery_id;
	}
	public int getPage_id() {
		return page_id;
	}
	public void setPage_id(int page_id) {
		this.page_id = page_id;
	}
	
	
}
