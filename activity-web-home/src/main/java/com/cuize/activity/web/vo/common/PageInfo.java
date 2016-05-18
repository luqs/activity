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
	private int pageIndex;
	
	/**
	 * 每页数量，默认10
	 */
	private int pageSize;

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
