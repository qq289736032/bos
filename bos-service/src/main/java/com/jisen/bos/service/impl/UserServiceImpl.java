package com.jisen.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jisen.bos.dao.IUserDao;
import com.jisen.bos.domain.Role;
import com.jisen.bos.domain.User;
import com.jisen.bos.service.UserService;
import com.jisen.bos.utils.MD5Utils;
import com.jisen.bos.utils.PageBean;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private IUserDao userDao;
	//用户登录
	public User login(User user) {
		//调用Dao方法,查询登录信息
		//使用MD5加密
		String password = MD5Utils.md5(user.getPassword());
		User eUser = userDao.findUserByUsernameAndPassword(user.getUsername(),password);
		return eUser;
	}
	/**
	 * 根据用户id修改密码
	 */
	public void editPassword(String id, String password) {
		//使用MD5加密
		String md5 = MD5Utils.md5(password);
		userDao.executeUpdate("user.editpassword", md5, id);
		
	}
	
	/**
	 * 添加一个用户,同时关联角色
	 */
	public void save(User model, String[] roleIds) {
		String password = MD5Utils.md5(model.getPassword());
		model.setPassword(password);
		userDao.save(model);
		if(roleIds != null && roleIds.length>0){
			for (String roleId : roleIds) {
				//手动构造一个托管对象
				Role role = new Role(roleId);
				//用户对象关联角色对象
				model.getRoles().add(role);
			}
		}
	}

	/**
	 * 分页查询
	 */
	public void pageQuery(PageBean pageBean) {
		userDao.pageQuery(pageBean);
	}

}
