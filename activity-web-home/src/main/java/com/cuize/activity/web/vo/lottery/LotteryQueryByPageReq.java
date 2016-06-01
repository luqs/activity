package com.cuize.activity.web.vo.lottery;

import com.cuize.activity.web.vo.common.PageInfo;

public class LotteryQueryByPageReq  extends PageInfo {

	private int processStatus = 1;

	public int getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(int processStatus) {
		this.processStatus = processStatus;
	}
	
	
}
