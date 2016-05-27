package com.cuize.activity.service.dto.wxpreorder;

import java.util.List;

import com.cuize.commons.dao.activity.domain.WxhbPreorder;

/**
 * 分页查询返回
 * @author JackieLan
 *
 */
public class WxhbPreorderQueryByPageOutDto {
	
	private int total; //总数量
	
	private List<WxhbPreorder> rows;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<WxhbPreorder> getRows() {
		return rows;
	}

	public void setRows(List<WxhbPreorder> rows) {
		this.rows = rows;
	}

	
}
