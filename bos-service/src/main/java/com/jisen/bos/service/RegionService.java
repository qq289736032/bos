package com.jisen.bos.service;

import java.util.List;

import com.jisen.bos.domain.Region;
import com.jisen.bos.utils.PageBean;

public interface RegionService {

	void saveBatch(List<Region> regionList);

	void pageQuery(PageBean pageBean);

	List<Region> findAll();

	List<Region> findListByQ(String q);

}
