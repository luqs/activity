package com.cuize.activity.service.weixin;

import java.util.List;

/**
 * 录入红包信息返回Bean
 * @author JackieLan
 *
 */
public class WxhbSetPrizeBucketResultBean {

	private int errcode;
	private String errmsg;
	private int success_num; // 成功录入的红包数量
	
	private List<WxhbTicket> repeat_ticket_list; // 重复使用的ticket列表，如为空，将不返回
	private List<WxhbTicket> expire_ticket_list; // 过期的ticket列表，如为空，将不返回
	private List<WxhbTicket> invalid_amount_ticket_list; // 金额不在大于1元，小于1000元的ticket列表，如为空，将不返回
	private List<WxhbTicket> wrong_authmchid_ticket_list; // 原因：生成红包的时候，授权商户号auth_mchid和auth_appid没有写摇周边的商户号
	private List<WxhbTicket> invalid_ticket_list; // ticket解析失败，可能有错别字符或不完整 
	public int getErrcode() {
		return errcode;
	}
	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public int getSuccess_num() {
		return success_num;
	}
	public void setSuccess_num(int success_num) {
		this.success_num = success_num;
	}
	public List<WxhbTicket> getRepeat_ticket_list() {
		return repeat_ticket_list;
	}
	public void setRepeat_ticket_list(List<WxhbTicket> repeat_ticket_list) {
		this.repeat_ticket_list = repeat_ticket_list;
	}
	public List<WxhbTicket> getExpire_ticket_list() {
		return expire_ticket_list;
	}
	public void setExpire_ticket_list(List<WxhbTicket> expire_ticket_list) {
		this.expire_ticket_list = expire_ticket_list;
	}
	public List<WxhbTicket> getInvalid_amount_ticket_list() {
		return invalid_amount_ticket_list;
	}
	public void setInvalid_amount_ticket_list(
			List<WxhbTicket> invalid_amount_ticket_list) {
		this.invalid_amount_ticket_list = invalid_amount_ticket_list;
	}
	public List<WxhbTicket> getWrong_authmchid_ticket_list() {
		return wrong_authmchid_ticket_list;
	}
	public void setWrong_authmchid_ticket_list(
			List<WxhbTicket> wrong_authmchid_ticket_list) {
		this.wrong_authmchid_ticket_list = wrong_authmchid_ticket_list;
	}
	public List<WxhbTicket> getInvalid_ticket_list() {
		return invalid_ticket_list;
	}
	public void setInvalid_ticket_list(List<WxhbTicket> invalid_ticket_list) {
		this.invalid_ticket_list = invalid_ticket_list;
	}
	
	
	
}
