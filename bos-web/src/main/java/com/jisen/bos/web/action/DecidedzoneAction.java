package com.jisen.bos.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jisen.bos.domain.Decidedzone;
import com.jisen.bos.service.DecidedzoneService;
import com.jisen.bos.web.action.base.BaseAction;
import com.jisen.crm.Customer;
import com.jisen.crm.ICustomerService;

/**
 * 定区管理
 * @author Administrator
 */
@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone> {
	@Autowired
	DecidedzoneService decidedzoneService;
	
	/*	id	123
		name	定取
		staff.id	4028d5815d734efc015d7355dab10002
		subareaid	4028d5815d79a594015d79ac16640000*/

	// 属性驱动,接收多个分区id
	private String[] subareaid;
	public void setSubareaid(String[] subareaid) {
		this.subareaid = subareaid;
	}
	
	/**
	 * 添加定区
	 */
	public String add(){
		decidedzoneService.save(model,subareaid);
		return "list";
		
	}
	
	/**
	 * 定区分页查询
	 */
	public String pageQuery() {
		decidedzoneService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{"currentPage", "detachedCriteria", "pageSize", "subareas","decidedzones"});
		return NONE;
	}
	
	
	//注入crm代理对象
	@Autowired
	private ICustomerService proxy;
	/**
	 * 远程调用crm,获取未关联到定区的客户
	 * @return
	 */
	public String findListNotAssociation(){
		List<Customer> list = proxy.findListNotAssociation();
		this.java2Json(list, new String[]{});
		return NONE;
	}
	
	/**
	 * 远程调用crm,获取已经关联到指定定区的客户
	 * @return
	 */
	public String findListHasAssociation(){
		String id = model.getId();
		List<Customer> list = proxy.findListHasAssociation(id);
		this.java2Json(list, new String[]{});
		return NONE;
	}
	
	
	/**
	 * 远程调用crm,将客户关联到定区
	 */
	//属性驱动,接收页面提交多个客户id
	private List<Integer> customerIds;
	public void setCustomerIds(List<Integer> customerIds) {
		this.customerIds = customerIds;
	}
	
	public String assigncustomerstodecidedzone() {
		proxy.assigncustomerstodecidedzone(model.getId(), customerIds);
		return NONE;

	}
}
