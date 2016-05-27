package com.cuize.activity.service.dto.wxpreorder;

/**
 * 分页查询入参
 * @author JackieLan
 *
 */
public class WxhbPreorderQueryByPageInDto {

	private int start;
	
	private int limit;
	
	private String hbType;
	
	private int status;

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getHbType() {
		return hbType;
	}

	public void setHbType(String hbType) {
		this.hbType = hbType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
