package com.cuize.activity.service.dto;

import com.cuize.activity.meta.RequireField;


public class ValidateDrawInDto {
		@RequireField
		private String openid;
		private Integer shopId;

		public String getOpenid() {
			return openid;
		}

		public void setOpenid(String openid) {
			this.openid = openid;
		}

		public Integer getShopId() {
			return shopId;
		}

		public void setShopId(Integer shopId) {
			this.shopId = shopId;
		}

}
