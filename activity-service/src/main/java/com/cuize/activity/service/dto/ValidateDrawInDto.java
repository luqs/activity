package com.cuize.activity.service.dto;

import com.cuize.activity.meta.RequireField;


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
