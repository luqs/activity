package com.cuize.product.service.dto;

import java.util.Date;

public class QueryTicketListOutDto extends CommonOutDto {

	private Integer id;
	private Integer productId;
	private String parkName;
	private String productName;
	private String mainPic;
	private Integer shopId;
	private Integer poductStatus;
	private Integer sortNum;
	private Integer dayTicketType;
	private Double parkPrice;
	private Double accountPrice;
	private Double salesPrice;
	private Date useStartDate;
	private Date useEndDate;
	private Integer seasonTicketType;
	private Integer personTicketType;
	private String ticketId;
	private String parkAbstract;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getMainPic() {
		return mainPic;
	}
	public void setMainPic(String mainPic) {
		this.mainPic = mainPic;
	}
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public Integer getPoductStatus() {
		return poductStatus;
	}
	public void setPoductStatus(Integer poductStatus) {
		this.poductStatus = poductStatus;
	}
	public Integer getSortNum() {
		return sortNum;
	}
	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}
	public Integer getDayTicketType() {
		return dayTicketType;
	}
	public void setDayTicketType(Integer dayTicketType) {
		this.dayTicketType = dayTicketType;
	}
	public Double getParkPrice() {
		return parkPrice;
	}
	public void setParkPrice(Double parkPrice) {
		this.parkPrice = parkPrice;
	}
	public Double getAccountPrice() {
		return accountPrice;
	}
	public void setAccountPrice(Double accountPrice) {
		this.accountPrice = accountPrice;
	}
	public Double getSalesPrice() {
		return salesPrice;
	}
	public void setSalesPrice(Double salesPrice) {
		this.salesPrice = salesPrice;
	}
	public Date getUseStartDate() {
		return useStartDate;
	}
	public void setUseStartDate(Date useStartDate) {
		this.useStartDate = useStartDate;
	}
	public Date getUseEndDate() {
		return useEndDate;
	}
	public void setUseEndDate(Date useEndDate) {
		this.useEndDate = useEndDate;
	}
	public Integer getSeasonTicketType() {
		return seasonTicketType;
	}
	public void setSeasonTicketType(Integer seasonTicketType) {
		this.seasonTicketType = seasonTicketType;
	}
	public Integer getPersonTicketType() {
		return personTicketType;
	}
	public void setPersonTicketType(Integer personTicketType) {
		this.personTicketType = personTicketType;
	}
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	public String getParkAbstract() {
		return parkAbstract;
	}
	public void setParkAbstract(String parkAbstract) {
		this.parkAbstract = parkAbstract;
	}
	public String getParkName() {
		return parkName;
	}
	public void setParkName(String parkName) {
		this.parkName = parkName;
	}
}
