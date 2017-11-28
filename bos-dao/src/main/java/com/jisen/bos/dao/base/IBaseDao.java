package com.jisen.bos.dao.base;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.jisen.bos.utils.PageBean;

/**
 * 持久层通用接口
 * @author Administrator
 *
 */
public interface IBaseDao<T> {
	//持久层通用接口
    public void save(T entity);
    public void delete(T entity);
	public void update(T entity);
	public void saveOrUpdate(T entity);
	public T findById(Serializable id);
	public List<T> findAll();
	public List<T> findByCriteria(DetachedCriteria detachedCriteria);
	public void executeUpdate(String queryName, Object...object);
	public void pageQuery(PageBean pageBean);
}
