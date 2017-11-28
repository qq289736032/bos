package com.jisen.bos.web.interceptor;


import com.jisen.bos.domain.User;
import com.jisen.bos.utils.BOSUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class BOSLoginIntercepter extends MethodFilterInterceptor {
	
	//拦截方法
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		//从session中获取用户对象
		User eUser = BOSUtils.getLoginUser();
		if(eUser==null){
			return "login";
		}
		//方行
		return invocation.invoke() ;
	}

}
