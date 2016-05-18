package com.cuize.activity.web.vo.lottery;

/**
 * 添加微信红包活动请求对象
 * @author JackieLan
 *
 */
public class WxhbLotteryAddReq {

	/**
	 * 是否使用模板（目前设置为2）
		1使用，2不使用
	 */
	private int useTemplate = 2;
	
	/**
	 * 活动名称（最长6个汉字，12个英文字母）
	 */
	private String title;
	
	/**
	 * 活动描述（最长7个汉字，14个英文字母）
	 */
	private String desc;
	
	/**
	 * 初始开关：
		0关闭
		1开启（默认）
	 */
	private int onoff = 1;
	
	/**
	 * 活动开始时间：格式为yyyyMMddmmhhss
	 */
	private String beginTime;
	
	/**
	 * 活动结束时间（最长91天）：格式为yyyyMMddmmhhss
	 */
	private String expireTime;
	
	/**
	 * 红包总数（红包总数>=预下单红包ticket总数）
	 */
	private int total;

	public int getUseTemplate() {
		return useTemplate;
	}

	public void setUseTemplate(int useTemplate) {
		this.useTemplate = useTemplate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getOnoff() {
		return onoff;
	}

	public void setOnoff(int onoff) {
		this.onoff = onoff;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
	
}
