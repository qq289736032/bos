package com.jisen.bos.web.action.base;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import com.jisen.bos.domain.Staff;
import com.jisen.bos.utils.PageBean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 表现层通用实现
 * 
 * @author Administrator
 *
 * @param <T>
 */

@SuppressWarnings("all")
public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
	// ******************分页查询代码抽取**************************
	// 属性驱动接收当前页码和当前页显示条数
	protected PageBean pageBean = new PageBean();

	public void setPage(Integer page) {
		pageBean.setCurrentPage(page);
	}

	public void setRows(Integer rows) {
		pageBean.setPageSize(rows);
	}

	// 创建离线提交查询对象
	DetachedCriteria detachedCriteria = null;

	/**
	 * 将指定Java对象转为json,并响应到客户端页面
	 * @param o
	 */
	public void java2Json(Object o, String[] excludes) {
		// 将不需要转换为json的属性排除
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		// 将封装完整的pageBean对象转换为json
		String json = JSONObject.fromObject(o,jsonConfig).toString();
		// 将筛选后的json写入到响应页面
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		try {
			ServletActionContext.getResponse().getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将指定List转为json,并响应到客户端页面
	 * @param o
	 */
	public void java2Json(List o, String[] excludes) {
		// 将不需要转换为json的属性排除
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		// 将封装完整的pageBean对象转换为json
		String json = JSONArray.fromObject(o,jsonConfig).toString();
		// 将筛选后的json写入到响应页面
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		try {
			ServletActionContext.getResponse().getWriter().print(json);
			System.out.println(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ******************模型驱动构建**************************
	// 模型驱动
	protected T model;

	public T getModel() {
		return model;
	}

	// 在构造方法中动态获取动态获取实体类型,通过反射常见model对象
	public BaseAction() {
		ParameterizedType superclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		Type[] actualTypeArguments = superclass.getActualTypeArguments();
		Class<T> entityClass = (Class<T>) actualTypeArguments[0];

		detachedCriteria = DetachedCriteria.forClass(entityClass);
		pageBean.setDetachedCriteria(detachedCriteria);
		
		// 通过反射创建对象
		try {
			model = entityClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
