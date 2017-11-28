package com.jisen.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jisen.bos.dao.IDecidedzoneDao;
import com.jisen.bos.dao.ISubareaDao;
import com.jisen.bos.domain.Decidedzone;
import com.jisen.bos.domain.Subarea;
import com.jisen.bos.service.DecidedzoneService;
import com.jisen.bos.utils.PageBean;

@Service
@Transactional
public class DecidedzoneServiceImpl implements DecidedzoneService {
	@Autowired
	private IDecidedzoneDao decidedzoneDao;
	@Autowired
	private ISubareaDao subareaDao;
	public void save(Decidedzone model, String[] subareaid) {
		decidedzoneDao.save(model);
		for(String id : subareaid){
			Subarea subarea = subareaDao.findById(id);
			//model.getSubareas().add(subarea);一方(定区已经放弃维护外键权利,只能由多的一方(分区)负责维护)
			subarea.setDecidedzone(model);
		}
		
	}

	/**
	 * 定区分页查询
	 */
	public void pageQuery(PageBean pageBean) {
		decidedzoneDao.pageQuery(pageBean);
	}

}
