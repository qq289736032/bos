package com.jisen.bos.web.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jisen.bos.domain.Noticebill;
import com.jisen.bos.service.NoticebillService;
import com.jisen.bos.web.action.base.BaseAction;
import com.jisen.crm.Customer;
import com.jisen.crm.ICustomerService;

/**
 * 业务通知单管理
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class NoticebillAction extends BaseAction<Noticebill> {
	//注入crm客户端代理对象
	@Autowired
	private ICustomerService customerService;
	
	/**noticebillAction_findCustomerByTelephone
	 * 远程调用crm服务,根据手机号查询客户信息
	 */
	public String findCustomerByTelephone(){
		String telephone = model.getTelephone();
		Customer customer = customerService.findCustomerByTelephone(telephone);
		this.java2Json(customer, new String[]{});
		return NONE;
	}
	
	/**
	 * 保存一个业务通知单
	 * @return 
	 */
	@Autowired
	private NoticebillService noticebillService;
	public String add(){
		noticebillService.save(model);
		
		return "noticebill_add";
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
