package com.jisen.bos.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jisen.bos.dao.IRoleDao;
import com.jisen.bos.domain.Function;
import com.jisen.bos.domain.Role;
import com.jisen.bos.service.RoleService;
import com.jisen.bos.utils.PageBean;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	@Autowired
	private IRoleDao roleDao;

	/**
	 * 保存一个角色,同时还需要关联权限
	 */
	public void save(Role model, String functionIds) {
		roleDao.save(model);
		if (StringUtils.isNotBlank(functionIds)) {
			String[] fIds = functionIds.split(",");
			for (String functionId : fIds) {
				//手动构造一个权限对象,设置id,对象状态为托管状态
				Function function = new Function(functionId);
				// 角色关联权限
				model.getFunctions().add(function);
			}
		}
	}


	/**
	 * 分页查询
	 */
	public void pageQuery(PageBean pageBean) {
		roleDao.pageQuery(pageBean);
	}


	/**
	 * 查询所有角色
	 */
	public List<Role> findAll() {
		List<Role> list = roleDao.findAll();
		return list;
	}
}
