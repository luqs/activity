package com.cuize.activity.service.constant;

/**
 * 微信接口枚举类
 * @author Administrator
 *
 */
public enum WeChatApiConstant {

	SHAKEHAND_PREORDER("PREORDER","摇一摇红包预下单"),
	SHAKEHAND_ADD_LOTTERY("ADD_LOTTERY","摇一摇创建红包活动"),
	SHAKEHAND_SET_LOTTERY_SWITCH("SET_LOTTERY_SWITCH","摇一摇设置红包活动开关"),
	SHAKEHAND_SET_PRIZE_BUCKET("SET_PRIZE_BUCKET","摇一摇录入红包信息");

	private String apiName;
	 
	private String apiDesc;
	
	private WeChatApiConstant(String apiName, String apiDesc){
		this.apiName = apiName;
		this.apiDesc = apiDesc;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public String getApiDesc() {
		return apiDesc;
	}

	public void setApiDesc(String apiDesc) {
		this.apiDesc = apiDesc;
	}
	
	
}
