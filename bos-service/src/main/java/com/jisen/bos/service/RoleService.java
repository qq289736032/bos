package com.jisen.bos.service;

import java.util.List;

import com.jisen.bos.domain.Role;
import com.jisen.bos.utils.PageBean;

public interface RoleService {

	void save(Role model, String functionIds);

	void pageQuery(PageBean pageBean);

	List<Role> findAll();

}
