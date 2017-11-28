package com.jisen.bos.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.jisen.bos.dao.IRegionDao;
import com.jisen.bos.dao.base.impl.BaseDaoImpl;
import com.jisen.bos.domain.Region;

@SuppressWarnings("unchecked")
@Repository
public class RegionDaoImpl extends BaseDaoImpl<Region> implements IRegionDao {
	
	/**
	 * 根据q参数进行模糊查询
	 */
	public List<Region> findListByQ(String q) {
		String hql = "FROM Region r WHERE r.shortcode LIKE ? " + "	OR r.citycode LIKE ? OR r.province LIKE ? "
				+ "OR r.city LIKE ? OR r.district LIKE ?";
		List<Region> list = (List<Region>) this.getHibernateTemplate().find(hql, "%" + q + "%", "%" + q + "%",
				"%" + q + "%", "%" + q + "%", "%" + q + "%");
		return list;
	}

}
