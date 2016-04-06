package com.cuize.activity.service.dto;

import com.cuize.activity.meta.RequireField;

public class ValidateDrawInDto {
	@RequireField
	private String openid;
	private String activityCode;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

}
