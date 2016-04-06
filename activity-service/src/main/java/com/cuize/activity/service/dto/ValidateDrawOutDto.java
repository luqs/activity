package com.cuize.activity.service.dto;

import java.util.List;

import com.cuize.activity.dao.domain.Award;


public class ValidateDrawOutDto{
	private Integer status;//0可以抽奖  1已经中奖 2没有课参加的活动
	private List<Award> awardList;
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public List<Award> getAwardList() {
		return awardList;
	}
	public void setAwardList(List<Award> awardList) {
		this.awardList = awardList;
	}
}
