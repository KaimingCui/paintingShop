package com.kaiming.domain;

import java.util.List;

public class PageBean<T> {
	private Integer page; //当前页数
	private Integer totalPage; //总页数
	private Integer limit; //每页的记录数
	private Integer totalCount; //总记录数
	private List<T> list; //当前页的数据
	
	public PageBean() {
		
	}
	
	public PageBean(Integer page, Integer totalPage, Integer limit, Integer totalCount, List<T> list) {
		super();
		this.page = page;
		this.totalPage = totalPage;
		this.limit = limit;
		this.totalCount = totalCount;
		this.list = list;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "PageBean [page=" + page + ", totalPage=" + totalPage + ", limit=" + limit + ", totalCount=" + totalCount
				+ ", list=" + list + "]";
	}
	
	
	

}
