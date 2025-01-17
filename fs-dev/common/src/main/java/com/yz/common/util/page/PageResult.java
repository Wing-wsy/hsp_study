package com.yz.common.util.page;

import java.util.List;

/**
 * 用来返回分页的数据格式
 */
public class PageResult<T> {

	private long page;			// 当前页数
	private long size;			// 每页记录数
	private long total;			// 总页数
	private long records;		// 总记录数
	private List<T> rows;		// 每行显示的内容

	public long getPage() {
		return page;
	}
	public void setPage(long page) {
		this.page = page;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public long getRecords() {
		return records;
	}
	public void setRecords(long records) {
		this.records = records;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}
