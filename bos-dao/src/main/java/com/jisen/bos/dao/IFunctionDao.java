package com.jisen.bos.dao;

import java.util.List;

import com.jisen.bos.dao.base.IBaseDao;
import com.jisen.bos.domain.Function;

public interface IFunctionDao extends IBaseDao<Function> {

	List<Function> findFunctionListByUserId(String id);

	List<Function> findAllMenu();

	List<Function> findMenuByUserId(String id);

}
