package com.cuize.activity.service.dto.wxpreorder;

/**
 * 微信红包预下单DTO
 * @author JackieLan
 *
 */
public class WxhbPreorderAddInDto {

	/**
	 * 红包类型:
		NORMAL普通红包
		GROUP裂变红包
	 */
	private String hbType; 
	
	/**
	 * 总金额，单位分
	 */
	private int totalAmount;
	
	/**
	 * 红包发放总人数，普通红包1，裂变红包大于1
	 */
	private int totalNum;
	
	/**
	 * 红包金额设置方式，裂变红包必传：
		ALL_RANDOM随机
	 */
	private String amtType;
	
	/**
	 * 红包祝福语，展示在红包页面。
	 */
	private String wishing;
	
	/**
	 * 活动名称：在不支持原生红包的微信版本中展示在红包消息
	 */
	private String actName;
	
	/**
	 * 备注：在不支持原生红包的微信版本中展示在红包消息
	 */
	private String remark;

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

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
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

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
