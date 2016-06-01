package com.cuize.activity.service.dto.lottery;

public class LotteryQueryByPageInDto {

	private int start;
	
	private int limit; 
	
	private int processStatus;

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

	public int getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(int processStatus) {
		this.processStatus = processStatus;
	}

	
	
}
