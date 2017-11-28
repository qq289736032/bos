package com.jisen.bos.service;

import com.jisen.bos.domain.User;
import com.jisen.bos.utils.PageBean;

public interface UserService {
	//登录
	User login(User user);
	//修改密码
	void editPassword(String id, String password);
	//添加用户
	void save(User model, String[] roleIds);
	//分页查询
	void pageQuery(PageBean pageBean);

}
