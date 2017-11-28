package com.jisen.bos.realm;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import com.jisen.bos.dao.IFunctionDao;
import com.jisen.bos.dao.IUserDao;
import com.jisen.bos.domain.Function;
import com.jisen.bos.domain.User;

/**
 * 
 * @author Administrator
 *
 */
public class BOSRealm extends AuthorizingRealm {
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IFunctionDao functionDao;
	
	//认证方法
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("自定义的realm中的认证方法执行了....");
		
		UsernamePasswordToken passwordToken = (UsernamePasswordToken) token;
		String username = passwordToken.getUsername();
		
		//根据用户名查询查询密码
		User user = userDao.findUserByUsername(username);
		
		if(user == null){
			//页面输入的用户不存在
			return null;
		}
		//框架负责比对数据库中的密码和页面输入的密码是否一致
		//简单认证信息对象
		AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
		return info;
	}

	//授权方法
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//获取当前登录用户对象
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		//根据当前登录用户查询数据库,获取实际对应权限,对于tom登录用户特殊处理
		List<Function> list = null;
		if(user.getUsername().equals("tom")){
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Function.class);
			//超级管理员内置用户,查询所有数据
			list = functionDao.findByCriteria(detachedCriteria );
		}else{
			list = functionDao.findFunctionListByUserId(user.getId());
		}
		for (Function function : list) {
			info.addStringPermission(function.getCode());
		}
		return info;
	}


}
