package com.cuize.activity.service.dto.common;

import java.util.List;

import com.cuize.commons.dao.activity.domain.WxhbLottery;

public class PageResult<T> {

	private int total; //总数量
	
	private List<T> rows;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
	
}
