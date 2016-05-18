package com.cuize.activity.service.util;

import java.util.HashMap;
import java.util.Map;


public enum CommonWeixinCode {

	BUSINESS_ERROR("BUSINESS_ERROR","业务错误"),
	CA_ERROR("CA_ERROR","请求携带的证书出错"),
	SIGN_ERROR("SIGN_ERROR","商户签名错误"),
	NO_AUTH("NO_AUTH","没有权限"),
	FREQ_LIMIT("FREQ_LIMIT","受频率限制"),
	XML_ERROR("XML_ERROR","请求的xml格式错误"),
	PARAM_ERROR("PARAM_ERROR","参数错误"),
	OPENID_ERROR("OPENID_ERROR","Openid错误"),
	NOTENOUGH("NOTENOUGH","余额不足"),
	FATAL_ERROR("FATAL_ERROR","重复请求时，参数与原单不一致"),
	TIME_LIMITED("TIME_LIMITED","企业红包的发送时间受限"),
	SECOND_OVER_LIMITED("SECOND_OVER_LIMITED","企业红包的按分钟发放受限"),
	DAY_OVER_LIMITED("DAY_OVER_LIMITED","企业红包的按天日发放受限"),
	MONEY_LIMIT("MONEY_LIMIT","红包金额发放限制"),
	SEND_FAILED("SEND_FAILED","红包发放失败，请更换单号再重试。"),
	SYSTEMERROR("SYSTEMERROR","系统繁忙，请再试。");
	
	
	private String code;
	
	private String desc;

	private CommonWeixinCode(String code, String desc){
		this.code = code;
		this.desc = desc;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static Map<String, CommonWeixinCode> map = new HashMap<String, CommonWeixinCode>();
	
	static {
		CommonWeixinCode[] types = CommonWeixinCode.values();
		for (int i =0 ; i < types.length ; i++) {
			map.put(types[i].getCode(), types[i]);
		}
	}
	
	/**
	 * 校验类型
	 * @param typeName
	 * @return
	 */
	public static CommonWeixinCode getWeixinCode(String code){
		return map.get(code);
	}
}
