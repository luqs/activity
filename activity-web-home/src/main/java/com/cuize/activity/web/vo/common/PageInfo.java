package com.cuize.activity.web.vo.common;

/**
 * 分页对象
 * @author JackieLan
 *
 */
public class PageInfo {

	/**
	 * 页码，从1开始
	 */
	private int page = 1;
	
	/**
	 * 每页数量，默认10
	 */
	private int rows = 10;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	
}
