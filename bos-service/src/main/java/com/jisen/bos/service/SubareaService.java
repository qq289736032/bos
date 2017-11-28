package com.jisen.bos.service;

import java.util.List;

import com.jisen.bos.domain.Subarea;
import com.jisen.bos.utils.PageBean;

public interface SubareaService {

	void save(Subarea model);

	void pageQuery(PageBean pageBean);

	List<Subarea> findAll();

	List<Subarea> findListNotAssociation();

	List<Subarea> findListByDecidedzoneId(String decidedzoneId);

}
