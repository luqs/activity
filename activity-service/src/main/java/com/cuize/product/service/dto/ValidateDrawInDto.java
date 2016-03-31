package com.cuize.product.service.dto;

import com.cuize.product.meta.RequireField;


public class ValidateDrawInDto {
		@RequireField
		private String openid;

		public String getOpenid() {
			return openid;
		}

		public void setOpenid(String openid) {
			this.openid = openid;
		}

}
