package com.jisen.bos.web.action;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jisen.bos.domain.Role;
import com.jisen.bos.service.RoleService;
import com.jisen.bos.web.action.base.BaseAction;

/**
 * 角色管理
 * @author Administrator
 * 角色管理字段
 *	private String id;
	private String name;
	private String code;
	private String description;
	private Set functions = new HashSet(0);//当前角色对应的多个权限
	private Set users = new HashSet(0);//当前角色对应的多个用户
 *
 */


@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {
	/*	code	1
		description	3
		functionIds	11,4028d5815d968f6a015d969383530000,116,114,113,115,112
		name	2
	*/
	@Autowired
	private RoleService roleService;
	
	/**
	 * 添加角色
	 */
	//属性驱动接收权限functionIds,是一个数组,
	private String functionIds;
	public void setFunctionIds(String functionIds) {
		this.functionIds = functionIds;
	}
	public String add() {
		roleService.save(model,functionIds);
		return "list";
	}
	
	/**
	 * 分页查询
	 */
	public String pageQuery() {
		roleService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{"functions","users"});
		return NONE;
	}
	
	/**
	 * 查询所有的role信息,返回json
	 */
	
	public String listajax(){
		List<Role> list = roleService.findAll();
		this.java2Json(list, new String[]{"functions","users"});
		return NONE;
	}
	
	
	
	
	
	
	
}
