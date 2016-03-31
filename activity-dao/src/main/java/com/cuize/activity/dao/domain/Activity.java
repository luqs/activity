package com.cuize.activity.dao.domain;

import java.util.Date;

public class Activity {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column activity.id
	 * @mbggenerated  Thu Mar 31 11:25:42 CST 2016
	 */
	private Integer id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column activity.shop_id
	 * @mbggenerated  Thu Mar 31 11:25:42 CST 2016
	 */
	private Integer shopId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column activity.activity_name
	 * @mbggenerated  Thu Mar 31 11:25:42 CST 2016
	 */
	private String activityName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column activity.begin_date
	 * @mbggenerated  Thu Mar 31 11:25:42 CST 2016
	 */
	private Date beginDate;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column activity.end_date
	 * @mbggenerated  Thu Mar 31 11:25:42 CST 2016
	 */
	private Date endDate;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column activity.total_day
	 * @mbggenerated  Thu Mar 31 11:25:42 CST 2016
	 */
	private Integer totalDay;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column activity.id
	 * @return  the value of activity.id
	 * @mbggenerated  Thu Mar 31 11:25:42 CST 2016
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column activity.id
	 * @param id  the value for activity.id
	 * @mbggenerated  Thu Mar 31 11:25:42 CST 2016
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column activity.shop_id
	 * @return  the value of activity.shop_id
	 * @mbggenerated  Thu Mar 31 11:25:42 CST 2016
	 */
	public Integer getShopId() {
		return shopId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column activity.shop_id
	 * @param shopId  the value for activity.shop_id
	 * @mbggenerated  Thu Mar 31 11:25:42 CST 2016
	 */
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column activity.activity_name
	 * @return  the value of activity.activity_name
	 * @mbggenerated  Thu Mar 31 11:25:42 CST 2016
	 */
	public String getActivityName() {
		return activityName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column activity.activity_name
	 * @param activityName  the value for activity.activity_name
	 * @mbggenerated  Thu Mar 31 11:25:42 CST 2016
	 */
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column activity.begin_date
	 * @return  the value of activity.begin_date
	 * @mbggenerated  Thu Mar 31 11:25:42 CST 2016
	 */
	public Date getBeginDate() {
		return beginDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column activity.begin_date
	 * @param beginDate  the value for activity.begin_date
	 * @mbggenerated  Thu Mar 31 11:25:42 CST 2016
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column activity.end_date
	 * @return  the value of activity.end_date
	 * @mbggenerated  Thu Mar 31 11:25:42 CST 2016
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column activity.end_date
	 * @param endDate  the value for activity.end_date
	 * @mbggenerated  Thu Mar 31 11:25:42 CST 2016
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column activity.total_day
	 * @return  the value of activity.total_day
	 * @mbggenerated  Thu Mar 31 11:25:42 CST 2016
	 */
	public Integer getTotalDay() {
		return totalDay;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column activity.total_day
	 * @param totalDay  the value for activity.total_day
	 * @mbggenerated  Thu Mar 31 11:25:42 CST 2016
	 */
	public void setTotalDay(Integer totalDay) {
		this.totalDay = totalDay;
	}
}