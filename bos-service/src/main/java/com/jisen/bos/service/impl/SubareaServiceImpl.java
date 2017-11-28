package com.jisen.bos.service.impl;

import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jisen.bos.dao.IDecidedzoneDao;
import com.jisen.bos.dao.ISubareaDao;
import com.jisen.bos.domain.Decidedzone;
import com.jisen.bos.domain.Subarea;
import com.jisen.bos.service.SubareaService;
import com.jisen.bos.utils.PageBean;
@Service
@Transactional
public class SubareaServiceImpl implements SubareaService {
	
	@Autowired
	private ISubareaDao subareaDao;
	
	public void save(Subarea model) {
		subareaDao.save(model);
	}

	public void pageQuery(PageBean pageBean) {
		subareaDao.pageQuery(pageBean);
	}

	public List<Subarea> findAll() {
		List<Subarea> list = subareaDao.findAll();
		return list;
	}

	/**
	 * 查询所有未关联到定区的分区
	 */
	public List<Subarea> findListNotAssociation() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
		detachedCriteria.add(Restrictions.isNull("decidedzone"));
		List<Subarea> list = subareaDao.findByCriteria(detachedCriteria);
		return list;
	}

	/**
	 * 根据id查询关联分区
	 */
//	@Autowired
//	private IDecidedzoneDao decidedzoneDao;
	public List<Subarea> findListByDecidedzoneId(String decidedzoneId) {
//		Decidedzone decidedzone = decidedzoneDao.findById(decidedzoneId);
//		Set subareas = decidedzone.getSubareas();
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
		//添加过滤条件
		detachedCriteria.add(Restrictions.eq("decidedzone.id", decidedzoneId));
		List<Subarea> findByCriteria = subareaDao.findByCriteria(detachedCriteria);
		return findByCriteria;
	}
}
