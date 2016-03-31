package com.cuize.product.service.dto;

import com.cuize.product.dao.domain.ShopProduct;


public class QueryTicketDetailOutDto extends CommonOutDto {

	private ShopProduct data;

	public ShopProduct getData() {
		return data;
	}

	public void setData(ShopProduct data) {
		this.data = data;
	}

}
