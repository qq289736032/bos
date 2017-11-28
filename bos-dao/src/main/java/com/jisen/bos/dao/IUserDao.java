package com.jisen.bos.dao;

import com.jisen.bos.dao.base.IBaseDao;
import com.jisen.bos.domain.User;

public interface IUserDao extends IBaseDao<User> {
	
	User findUserByUsernameAndPassword(String username, String password);

	User findUserByUsername(String username);

}
