package com.jisen.bos.service;

import java.util.List;

import com.jisen.bos.domain.Staff;
import com.jisen.bos.utils.PageBean;

public interface StaffService {

	void save(Staff staffModel);

	void pageQuery(PageBean pageBean);

	void deleteBatch(String ids);
	
	Staff findById(String id);

	void update(Staff staff);

	List<Staff> findListNotDelete();

}
