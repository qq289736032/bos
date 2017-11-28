package com.jisen.bos.dao.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.jisen.bos.dao.base.IBaseDao;
import com.jisen.bos.utils.PageBean;

/**
 * 持久层通用实现
 * 
 * @author Administrator
 *
 * @param <T>
 * 继承HibernateDaoSupport之后就可以利用模板对象了
 */

@SuppressWarnings("all")
public class BaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T> {

	@Resource // 根据类型注入spring工厂中的会话工厂对象sessionFactory
	public void setMySessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	// 代表的是某个实体的类型,只有在运行时才能确定
	private Class<T> entityClass;

	// 在的构造方法中动态获得entityClass,为什么在构造方法中能够获取?,
	//因为在继承BaseDaoImpl的具体Dao对象创建的时候就调用构造方法,
	public BaseDaoImpl() {
		//父类的类型
//		ParameterizedType superclass = (ParameterizedType) this.getClass().getGenericSuperclass();
//		// 获得父类声明的泛型数组
//		Type[] actualTypeArguments = superclass.getActualTypeArguments();
//		entityClass = (Class<T>) actualTypeArguments[0];
		entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public void save(T entity) {
		this.getHibernateTemplate().save(entity);

	}

	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);

	}

	public void update(T entity) {
		this.getHibernateTemplate().update(entity);

	}

	public T findById(Serializable id) {

		T t = this.getHibernateTemplate().get(entityClass, id);
		return t;
	}

	public List<T> findAll() {
		// 查询所有,要用hql
		String hql = "from " + entityClass.getSimpleName();
		List<T> list = (List<T>) this.getHibernateTemplate().find(hql);
		return list;
	}

	// 执行更新
	public void executeUpdate(String queryName, Object... objects) {
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.getNamedQuery(queryName);
		int i = 0;
		for (Object object : objects) {
			// 为hql语句中的问号?赋值
			query.setParameter(i++, object);
		}
		// 执行更新
		query.executeUpdate();

	}

	// 通用分页查询方法
	public void pageQuery(PageBean pageBean) {

		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();

		// 查询total--总数据量,封装查询总数
		detachedCriteria.setProjection(Projections.rowCount());

		// 查询总数
		List<Long> countList = (List<Long>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
		Long count = countList.get(0);
		pageBean.setTotal(count.intValue());
		System.out.println("分页查询总条数" + pageBean.getTotal());

		// 从pageBean中获取查询条件,当前页,每页条数,即离线查询
		Integer currentPage = pageBean.getCurrentPage();
		if (currentPage == null) {
			currentPage = 1;
		}
		System.out.println("分页查询当前页currentPage"+currentPage);
		Integer pageSize = pageBean.getPageSize();
		if (pageSize == null) {
			pageSize=pageBean.getTotal();
		}
		System.out.println("分页查询当前页显示条数"+pageSize);
		// 查询row--当前需要展示的数据集合,并在查询之前清空查询条件
		detachedCriteria.setProjection(null);
		// 指定hibernate封装方式,查询分区表的时候有个区域对象需要查询,而查询查得到的结果是两个表封装到了一个Object[2]里面,
		// 而我们需要的是将region封装到subarea里面
		detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
		
		Integer firstResult = (currentPage - 1) * pageSize;// 初始条
		System.out.println("当页第一条"+firstResult);
		Integer maxResults = pageSize;// 末尾条
		System.out.println("当页末尾条"+maxResults);
		List rows = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
		pageBean.setRows(rows);// 这一步可以代替返回值,至此整个pageBean封装完成,在action中,可以直接对完整的pageBean使用

	}

	// 保存或更新
	public void saveOrUpdate(T entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
	}

	public List<T> findByCriteria(DetachedCriteria detachedCriteria) {
		List<T> list = (List<T>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
		return list;
	}

}
