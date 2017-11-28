package com.jisen.bos.utils;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

/**
 * 封装分页属性
 * @author Administrator
 */
public class PageBean {
	//
	private Integer currentPage;//当前页
	private Integer pageSize;//每页显示的记录数
	private DetachedCriteria detachedCriteria;//离线查询条件
	private Integer total;//总记录数
	private List rows;//当前页需要展示的数据集合
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public DetachedCriteria getDetachedCriteria() {
		return detachedCriteria;
	}
	public void setDetachedCriteria(DetachedCriteria detachedCriteria) {
		this.detachedCriteria = detachedCriteria;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	
}
