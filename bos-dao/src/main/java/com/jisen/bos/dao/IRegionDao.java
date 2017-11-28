package com.jisen.bos.dao;


import java.util.List;

import com.jisen.bos.dao.base.IBaseDao;
import com.jisen.bos.domain.Region;

public interface IRegionDao extends IBaseDao<Region> {

	List<Region> findListByQ(String q);

}
