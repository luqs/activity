package com.cuize.activity.service.dto.wxlottery;

import java.math.BigDecimal;

/**
 * 微信红包绑定用户通知DTO
 * @author JackieLan
 *
 */
public class WxhbBindNoticeInDto {

	private String lotteryId;//活动ID
	
	private String openId;//微信用户openid
	
	private int bindTime;//红包绑定时间
	
	private BigDecimal money; //红包金额
	
	private String ticket;//红包

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public int getBindTime() {
		return bindTime;
	}

	public void setBindTime(int bindTime) {
		this.bindTime = bindTime;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	
	
}
