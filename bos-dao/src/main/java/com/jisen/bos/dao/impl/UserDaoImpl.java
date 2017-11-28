package com.jisen.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jisen.bos.dao.IUserDao;
import com.jisen.bos.dao.base.impl.BaseDaoImpl;
import com.jisen.bos.domain.User;

@SuppressWarnings("unchecked")
@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao {
	/**
	 * 根据用户名和密码查询用户
	 */
	public User findUserByUsernameAndPassword(String username, String password) {
		String hql = "from User u where u.username=? and u.password=?";
		List<User> list = (List<User>) this.getHibernateTemplate().find(hql, username,password);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据用户名查询用户
	 */
	public User findUserByUsername(String username) {
		String hql = "from User u where u.username=?";
		List<User> list = (List<User>) this.getHibernateTemplate().find(hql, username);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}


}
