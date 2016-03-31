package com.cuize.activity.service.dto;

import com.cuize.activity.meta.RequireField;

public class ValidateDrawInDto {
	@RequireField
	private String openid;
	private Integer activityId;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

}
