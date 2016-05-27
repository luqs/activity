package com.cuize.activity.service.dto.wxpreorder;

import com.cuize.commons.dao.activity.domain.WxhbPreorder;

public class WxhbPreorderQueryDetailOutDto extends WxhbPreorder{

	private String ticket;

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	
	
}
