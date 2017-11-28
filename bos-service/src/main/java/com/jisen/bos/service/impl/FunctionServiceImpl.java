package com.jisen.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jisen.bos.dao.IFunctionDao;
import com.jisen.bos.domain.Function;
import com.jisen.bos.domain.User;
import com.jisen.bos.service.FunctionService;
import com.jisen.bos.utils.BOSUtils;
import com.jisen.bos.utils.PageBean;

@Service
@Transactional
public class FunctionServiceImpl implements FunctionService {
	@Autowired
	private IFunctionDao functionDao;
	
	//查询所有权限
	public List<Function> findAll() {
		List<Function> list = functionDao.findAll();
		return list;
	}
	
	//添加权限
	public void save(Function model) {
		Function parentFunction = model.getParentFunction();
		if(parentFunction != null && parentFunction.getId().equals("")){
			model.setParentFunction(null);
		}
		functionDao.save(model);
	}

	/**
	 * 权限管理分页
	 */
	public void pageQuery(PageBean pageBean) {
		functionDao.pageQuery(pageBean);
		
	}

	/**
	 * 查询菜单
	 */
	public List<Function> findMenu() {
		List<Function> list = null;
		User user = BOSUtils.getLoginUser();
		if(user.getUsername().equals("tom")){
			//如果是超级管理员内置用户,查询所有菜单
			list = functionDao.findAllMenu();
		}else{
			//其他用户需要根据用户id查询菜单
			list = functionDao.findMenuByUserId(user.getId());
		}
		return list;
	}

}
