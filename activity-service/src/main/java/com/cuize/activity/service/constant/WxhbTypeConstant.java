package com.cuize.activity.service.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信红包类型定义
 * @author JackieLan
 *
 */
public enum WxhbTypeConstant {

	NORMAL("NORMAL","普通红包"),
	GROUP("GROUP","裂变红包");
	
	private String typeName;
	 
	private String typeDesc;

	private WxhbTypeConstant(String typeName, String typeDesc){
		this.typeName = typeName;
		this.typeDesc = typeDesc;
	}
	
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}
	 
	public static Map<String, WxhbTypeConstant> map = new HashMap<String, WxhbTypeConstant>();
	
	static {
		WxhbTypeConstant[] types = WxhbTypeConstant.values();
		for (int i =0 ; i < types.length ; i++) {
			map.put(types[i].getTypeName(), types[i]);
		}
	}
	
	/**
	 * 校验类型
	 * @param typeName
	 * @return
	 */
	public static WxhbTypeConstant getWxhbType(String typeName){
		return map.get(typeName);
	}
	
}
