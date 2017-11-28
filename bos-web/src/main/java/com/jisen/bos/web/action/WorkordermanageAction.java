package com.jisen.bos.web.action;

import java.io.IOException;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jisen.bos.domain.Workordermanage;
import com.jisen.bos.service.WorkordermanageService;
import com.jisen.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")//workordermanageAction
public class WorkordermanageAction extends BaseAction<Workordermanage> {
	/**
	 * 
	 */
	@Autowired
	private WorkordermanageService workordermanageService;

	public String add() throws IOException {
		String f = "1";
		try {
			workordermanageService.save(model);
		} catch (Exception e) {
			e.printStackTrace();
			f = "0";
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(f);
		return NONE;
	}
}
