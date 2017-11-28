package com.jisen.bos.dao;

import com.jisen.bos.dao.base.IBaseDao;
import com.jisen.bos.domain.Noticebill;


public interface INoticebillDao extends IBaseDao<Noticebill> {

	void save(Noticebill model);

}
