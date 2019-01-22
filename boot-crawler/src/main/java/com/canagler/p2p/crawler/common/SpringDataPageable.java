package com.canagler.p2p.crawler.common;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * 分页
 * Created by scq on 2018-04-10 18:36:30
 */
public class SpringDataPageable implements Serializable, Pageable {

	private static final long serialVersionUID = 1L;
	// 当前页
	private Integer pagenumber = 1;
	// 当前页面条数
	private Integer pagesize = 10;
	//排序条件
	private Sort sort;

	public Integer getPagenumber() {
		return pagenumber;
	}

	public void setPagenumber(Integer pagenumber) {
		this.pagenumber = pagenumber;
	}

	public Integer getPagesize() {
		return pagesize;
	}

	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}

	@Override
	public int getPageNumber() {
		return getPagenumber();
	}

	@Override
	public int getPageSize() {
		return getPagesize();
	}

	@Override
	public long getOffset() {
		return (getPagenumber() - 1) * getPagesize();
	}

	@Override
	public Sort getSort() {
		return sort;
	}

	@Override
	public Pageable next() {
		return null;
	}

	@Override
	public Pageable previousOrFirst() {
		return null;
	}

	@Override
	public Pageable first() {
		return null;
	}

	@Override
	public boolean hasPrevious() {
		return false;
	}
}
