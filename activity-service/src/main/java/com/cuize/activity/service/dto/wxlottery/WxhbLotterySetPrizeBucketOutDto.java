package com.cuize.activity.service.dto.wxlottery;

import java.util.List;

import com.cuize.activity.service.weixin.WxhbTicket;

public class WxhbLotterySetPrizeBucketOutDto {

	private int successNum; // 成功录入的红包数量
	
	private List<WxhbTicket> repeatTicketList; // 重复使用的ticket列表，如为空，将不返回
	
	private List<WxhbTicket> expireTicketList; // 过期的ticket列表，如为空，将不返回
	
	private List<WxhbTicket> invalidAmountTicketList; // 金额不在大于1元，小于1000元的ticket列表，如为空，将不返回
	
	private List<WxhbTicket> wrongAuthmchidTicketList; // 原因：生成红包的时候，授权商户号auth_mchid和auth_appid没有写摇周边的商户号
	
	private List<WxhbTicket> invalidTicketList; // ticket解析失败，可能有错别字符或不完整 

	public int getSuccessNum() {
		return successNum;
	}

	public void setSuccessNum(int successNum) {
		this.successNum = successNum;
	}

	public List<WxhbTicket> getRepeatTicketList() {
		return repeatTicketList;
	}

	public void setRepeatTicketList(List<WxhbTicket> repeatTicketList) {
		this.repeatTicketList = repeatTicketList;
	}

	public List<WxhbTicket> getExpireTicketList() {
		return expireTicketList;
	}

	public void setExpireTicketList(List<WxhbTicket> expireTicketList) {
		this.expireTicketList = expireTicketList;
	}

	public List<WxhbTicket> getInvalidAmountTicketList() {
		return invalidAmountTicketList;
	}

	public void setInvalidAmountTicketList(List<WxhbTicket> invalidAmountTicketList) {
		this.invalidAmountTicketList = invalidAmountTicketList;
	}

	public List<WxhbTicket> getWrongAuthmchidTicketList() {
		return wrongAuthmchidTicketList;
	}

	public void setWrongAuthmchidTicketList(
			List<WxhbTicket> wrongAuthmchidTicketList) {
		this.wrongAuthmchidTicketList = wrongAuthmchidTicketList;
	}

	public List<WxhbTicket> getInvalidTicketList() {
		return invalidTicketList;
	}

	public void setInvalidTicketList(List<WxhbTicket> invalidTicketList) {
		this.invalidTicketList = invalidTicketList;
	}
}
