package com.jisen.bos.service;

import java.util.List;

import com.jisen.bos.domain.Function;
import com.jisen.bos.utils.PageBean;

public interface FunctionService {

	List<Function> findAll();

	void save(Function model);

	void pageQuery(PageBean pageBean);
	//查菜单
	List<Function> findMenu();

}
