package com.jisen.bos.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jisen.bos.domain.Function;
import com.jisen.bos.service.FunctionService;
import com.jisen.bos.web.action.base.BaseAction;

/**
 * 权限管理
 * @author Administrator
 */
@Controller
@Scope("prototype")
public class FunctionAction extends BaseAction<Function> {
	@Autowired
	private FunctionService functionService;
	
	/**
	 * 查询所有权限,返回json数据
	 */
	public String listajax(){
		List<Function> list = functionService.findAll();
		this.java2Json(list, new String[]{"parentFunction","roles"});
		return NONE;
	}
	
	
	/**
	 * 保存添加权限
	 */
	public String add(){
		functionService.save(model);
		return "list";
		
	}
	
	/**
	 * 权限管理分页
	 */
	public String pageQuery(){
		String currentPage = model.getPage();
		pageBean.setCurrentPage(Integer.parseInt(currentPage));
		functionService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{"parentFunction","children","roles"});
		return NONE;
	}
	
	/**
	 * 根据当前登录人查询对应菜单数据,返回json
	 */
	public String findMenu() {
		List<Function> list = functionService.findMenu();
		this.java2Json(list, new String[]{"parentFunction","roles","children"});
		return NONE;
		
	}
	
	
	
	
	
}
