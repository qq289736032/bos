package com.jisen.bos.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jisen.bos.dao.IDecidedzoneDao;
import com.jisen.bos.dao.INoticebillDao;
import com.jisen.bos.dao.IWorkbillDao;
import com.jisen.bos.domain.Decidedzone;
import com.jisen.bos.domain.Noticebill;
import com.jisen.bos.domain.Staff;
import com.jisen.bos.domain.User;
import com.jisen.bos.domain.Workbill;
import com.jisen.bos.service.NoticebillService;
import com.jisen.bos.utils.BOSUtils;
import com.jisen.crm.ICustomerService;

@Service
@Transactional
public class NoticebillServiceImpl implements NoticebillService {
	@Autowired
	private INoticebillDao noticebillDao;
	
	@Autowired
	private ICustomerService customerService;
	@Autowired
	private IDecidedzoneDao decidedzoneDao;
	@Autowired
	private IWorkbillDao workbillDao;
	
	/**
	 * 保存业务通知单,还要尝试自动分单
	 */
	public void save(Noticebill model) {
		User user = BOSUtils.getLoginUser();
		model.setUser(user);//设置当前登录用户
		noticebillDao.save(model);
		
		//获取客户提供的取件地址,
		String pickaddress = model.getPickaddress();
		//远程调用crm服,根据取件地址查询定区id
		String decidedzoneId = customerService.findDecidedzoneIdByAddress(pickaddress);
		if(decidedzoneId != null){
			//查询到了定区id不为空,可以根据定区id查询到定区对象,通过定区对象可以知道取派员,可以完成自动分单
			Decidedzone decidedzone = decidedzoneDao.findById(decidedzoneId);
			Staff staff = decidedzone.getStaff();
			model.setStaff(staff);//让业务通知单关联取派员对象
			//设置分单类型为自动分单
			model.setOrdertype(Noticebill.ORDERTYPE_AUTO);
			//为取派员产生一个工单
			Workbill workbill = new Workbill();
			workbill.setAttachbilltimes(0);
			workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));//创建的事件包括时分秒
			workbill.setNoticebill(model);//工单关联页面通知单
			workbill.setPickstate(Workbill.PICKSTATE_NO);//取件状态
			workbill.setRemark(model.getRemark());//备注信息
			workbill.setStaff(staff);//工单关联取派员
			workbill.setType(Workbill.TYPE_1);//工单类型
			
			workbillDao.save(workbill);
			
			//调用短信平台
			
			
		}else{
			model.setOrdertype(Noticebill.ORDERTYPE_MAN);
			
		}
	}
}
