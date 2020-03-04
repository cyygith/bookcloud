package com.elling.book.flowable.common.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Page<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static final int DEFAULT_PAGE_SIZE = 10;
	
	/**
	 * 每页数量
	 */
	private Integer limit = 10;
	/**
	 * 当前页
	 */
	private Integer page = 1;
	/**
	 * 分页开始点
	 */
	private Integer start = 0;
	/**
	 * 总数量
	 */
	private Integer total;
	/**
	 * 数据列表
	 */
	private List<T> data = new ArrayList<T>();
	/**
	 * 是否分页查询  0-否 ，1-是
	 */
	private String flag = "1";
	
	public Page() {}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
		if(this.page !=null && this.limit!= null) {
			start = (page-1) * limit;
		}
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
		if(this.page !=null && this.limit!= null) {
			start = (page-1) * limit;
		}
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static int getDefaultPageSize() {
		return DEFAULT_PAGE_SIZE;
	}
	
	
	
}
