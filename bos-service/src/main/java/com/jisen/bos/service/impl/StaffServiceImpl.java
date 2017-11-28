package com.jisen.bos.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jisen.bos.dao.IStaffDao;
import com.jisen.bos.domain.Staff;
import com.jisen.bos.service.StaffService;
import com.jisen.bos.utils.PageBean;
@Service
@Transactional
public class StaffServiceImpl implements StaffService {
	@Autowired
	private IStaffDao staffDao;
	
	
	public void save(Staff staffModel) {
		staffDao.save(staffModel);
		
	}
	/**
	 * 分页查询
	 */
	public void pageQuery(PageBean pageBean) {
		staffDao.pageQuery(pageBean);
	}
	
	/**
	 * 取派员的批量删除
	 * 将deltag改为1
	 */
	public void deleteBatch(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] staffIds = ids.split(",");
			for(String id : staffIds){
				staffDao.executeUpdate("staff_delete", id);
			}
		}
	}
	
	/**
	 * 查询数据库返回原始对象
	 */
	public Staff findById(String id) {
		return staffDao.findById(id);
	}
	
	/**
	 * 根据id修改取派员
	 */
	public void update(Staff staff) {
		staffDao.update(staff);
	}

	/**
	 * 查询所有未删除的取派员
	 */
	public List<Staff> findListNotDelete() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Staff.class);
		//添加过滤条件,deltag等于0
		detachedCriteria.add(Restrictions.eq("deltag", "0"));
//		detachedCriteria.add(Restrictions.ne("deltag", "0"));
		List<Staff> list = staffDao.findByCriteria(detachedCriteria );
		return list;
	}

}
