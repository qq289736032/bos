package com.jisen.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jisen.bos.dao.IWorkordermanageDao;
import com.jisen.bos.domain.Workordermanage;
import com.jisen.bos.service.WorkordermanageService;

@Service
@Transactional
public class WorkordermanageServiceImpl implements WorkordermanageService {
	
	
	@Autowired
	private IWorkordermanageDao workordermanageDao;
	public void save(Workordermanage model) {
		workordermanageDao.save(model);
		
	}

}
