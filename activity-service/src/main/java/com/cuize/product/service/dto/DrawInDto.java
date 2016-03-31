package com.cuize.product.service.dto;

import com.cuize.product.meta.RequireField;


public class DrawInDto {
		@RequireField
		private Integer id;
		@RequireField
		private String openid;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getOpenid() {
			return openid;
		}

		public void setOpenid(String openid) {
			this.openid = openid;
		}

}
