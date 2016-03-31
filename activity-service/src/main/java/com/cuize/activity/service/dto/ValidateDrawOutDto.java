package com.cuize.activity.service.dto;


public class ValidateDrawOutDto{
	private Integer status;//0可以抽奖  1已经中奖 2没有课参加的活动
	private Integer activityId;
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
}
