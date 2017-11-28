package com.jisen.bos.service;

import com.jisen.bos.domain.Decidedzone;
import com.jisen.bos.utils.PageBean;

public interface DecidedzoneService {

	void save(Decidedzone model, String[] subareaid);

	void pageQuery(PageBean pageBean);

}
