package com.jisen.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jisen.bos.domain.Staff;
import com.jisen.bos.service.StaffService;
import com.jisen.bos.utils.PageBean;
import com.jisen.bos.web.action.base.BaseAction;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 取派员管理
 * @author Administrator
 *
 */
@Controller//struts配置中的类由spring注解提供,因此全类名填写userAction
@Scope("prototype")
public class StaffAction extends BaseAction<Staff> {
	@Autowired
	private StaffService staffService;
	/**
	 * 添加取派员
	 */
	//模型值
	private Staff staffModel = model;
	public String add(){
		staffService.save(staffModel);
		return "list";
	}
	
	/**
	 * 分页查询方法
	 */
	//属性驱动接收 currentpage,和rows
	public String pageQuery() throws IOException{
		staffService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[] { "currentPage", "detachedCriteria", "decidedzones"});
		return NONE;
	}
	
	/**
	 * 批量删除
	 */
	//属性驱动接收ids参数
	private String ids;
	public void setIds(String ids) {
		this.ids = ids;
	}
	@RequiresPermissions("staff-delete")//执行这个方法需要当前用户具有staff这个权限
	public String deleteBatch(){
		staffService.deleteBatch(ids);
		return "list";
	}
	
	/**
	 * 修改取派员信息
	 */
	//@RequiresPermissions("staff-delete")
	public String edit() {
		/*
		 * 	haspda	1
		   	id	4028d5815d71c5d0015d71c78f890000
		 	name	王大锤
			standard	标准一
			station	运输公司
			telephone	18370995005
		*/
		//根据根据id查询原始数据库
		Staff staff = staffService.findById(staffModel.getId());
		//使用页面提交的数据进行覆盖
		staff.setHaspda(staffModel.getHaspda());
		staff.setName(staffModel.getName());
		staff.setStandard(staffModel.getStandard());
		staff.setStation(staffModel.getStation());
		staff.setTelephone(staffModel.getTelephone());
		staffService.update(staff);
		return "list";
	}
	
	
	/**
	 * 查询所有未删除的取派员,返回json
	 */
	public String listajax() {
		List<Staff> list = staffService.findListNotDelete();
		this.java2Json(list, new String[]{"decidedzones"});
		return NONE;
		
	}
}
