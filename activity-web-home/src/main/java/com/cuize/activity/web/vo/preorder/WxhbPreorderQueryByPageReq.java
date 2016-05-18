package com.cuize.activity.web.vo.preorder;

import com.cuize.activity.web.vo.common.PageInfo;

/**
 * 分页查询红包预下单
 * @author JackieLan
 *
 */
public class WxhbPreorderQueryByPageReq extends PageInfo{

	/**
	 * 状态：
		0全部
		1有效
		2失效
	      后台过滤时间字段
	 */
	private int status;
	
	/**
	 * 红包类型
		NORMAL普通红包
		GROUP裂变红包
	 */
	private String hbType;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getHbType() {
		return hbType;
	}

	public void setHbType(String hbType) {
		this.hbType = hbType;
	}
}
