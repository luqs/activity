package com.cuize.activity.service.dto.lottery;

import java.util.Date;

/**
 * 微信红包活动DTO
 * @author Administrator
 *
 */
public class LotteryAddInDto {

	private String title; //红包活动名称
	
	private String description; //红包活动描述
	
	private int status; // 红包活动开关：0关闭1开启
	
	/**
	 * 活动开始时间：格式为yyyy-MM-dd HH:mm:ss
	 */
	private String beginTime;
	
	/**
	 * 活动结束时间（最长91天）：格式为yyyy-MM-dd HH:mm:ss
	 */
	private String expireTime;
	
	private String hbType; //红包类型：NORMAL普通红包，GROUP裂变红包
	
	private int totalAmount; //总付金额，单位分
	
	private int total; //红包总数
	
	private int totalTicket; //实际红包数量
	
	private String amtType; //红包金额设置方式，只对裂变红包有效。ALL_RAND全部随机
	
	private String wishing; //红包祝福语
	
	private int perMinAmount = 100; //每个红包最小金额（分）

	private int perMaxAmount = 100; // 每个红包最大金额（分）
	
	private String amountType; //红包金额类型：FIX固定金额红包，RANDOM随机金额红包

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	public String getHbType() {
		return hbType;
	}

	public void setHbType(String hbType) {
		this.hbType = hbType;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getTotalTicket() {
		return totalTicket;
	}

	public void setTotalTicket(int totalTicket) {
		this.totalTicket = totalTicket;
	}

	public String getAmtType() {
		return amtType;
	}

	public void setAmtType(String amtType) {
		this.amtType = amtType;
	}

	public String getWishing() {
		return wishing;
	}

	public void setWishing(String wishing) {
		this.wishing = wishing;
	}

	public int getPerMinAmount() {
		return perMinAmount;
	}

	public void setPerMinAmount(int perMinAmount) {
		this.perMinAmount = perMinAmount;
	}

	public int getPerMaxAmount() {
		return perMaxAmount;
	}

	public void setPerMaxAmount(int perMaxAmount) {
		this.perMaxAmount = perMaxAmount;
	}

	public String getAmountType() {
		return amountType;
	}

	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}
	
	
}
