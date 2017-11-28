package com.jisen.bos.web.action;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jisen.bos.domain.User;
import com.jisen.bos.service.UserService;
import com.jisen.bos.utils.BOSUtils;
import com.jisen.bos.utils.MD5Utils;
import com.jisen.bos.web.action.base.BaseAction;
import com.opensymphony.xwork2.ActionContext;

	/**
	 * private String id;
	private String username;
	private String password;
	private Double salary;
	private Date birthday;
	private String gender;
	private String station;
	private String telephone;
	private String remark;
	private Set noticebills = new HashSet(0);
	private Set roles = new HashSet(0);
	 */


@Controller//struts配置中的类由spring注解提供,因此全类名填写userAction
@Scope("prototype")
public class UserAction extends BaseAction<User> {
	/**
	 * 用户登录
	 */
	//属性驱动,接收页面输入的验证码
	private String checkcode;
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	//模型驱动model
	private User userModel=model;
	
	//service实现类
	@Autowired
	private UserService userService;
	
	/**
	 * 用户登录,使用shiro框架提供的方式进行认证操作
	 * @return
	 */
	
	public String login(){
		//从session中获取生成的验证码,为什么要从session中获取?
		String validatecode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		//校验是否输入正确
		if(StringUtils.isNoneBlank(checkcode)&&checkcode.equals(validatecode)){
			//使用shiro框架提供的方式进行认证操作
			Subject subject = SecurityUtils.getSubject();//获得当前用户对象,看成user即可,状态为未认证
			//创建用户名密码令牌对象
			AuthenticationToken token = new UsernamePasswordToken(model.getUsername(),MD5Utils.md5(model.getPassword()));
			try {
				subject.login(token);
			} catch (Exception e) {
				e.printStackTrace();
				return LOGIN;
			}
			User user = (User) subject.getPrincipal();
			ServletActionContext.getRequest().getSession().setAttribute("user", user);
			return "home";
		}else{
			//输入的验证错误,设置提示信息,然后跳转到提示信息
			this.addActionError("输入的验证码错误");
			return LOGIN;
		}
	}
	
	/*****************************************************************************************************/
	public String login_backup(){
		//从session中获取生成的验证码,为什么要从session中获取?
		String validatecode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		//校验是否输入正确
		if(StringUtils.isNoneBlank(checkcode)&&checkcode.equals(validatecode)){
			//输入的验证码正确
			User eUser = userService.login(userModel);
			if(eUser != null){
				//说明登录成功,将user对象放入session,并跳转到首页
				ServletActionContext.getRequest().getSession().setAttribute("user", eUser);
				return "home";
			}else{
				//用户名或密码错误,登录失败跳转到登录页面,并提示信息
				this.addActionError("用户名或者密码错误");
				return LOGIN;
			}
		}else{
			//输入的验证错误,设置提示信息,然后跳转到提示信息
			this.addActionError("输入的验证码错误");
			return LOGIN;
		}
	}
	
	
	
	/**
	 * 用户注销
	 */
	public String logout(){
		//退出登录,清理session缓存
		ServletActionContext.getRequest().getSession().invalidate();
		return LOGIN;
	}
	/**
	 *修改当前用户密码 
	 * @throws IOException 
	 */
	public String editPassword() throws IOException{
		String f = "1";
		//获取当前登录用户
		User user = BOSUtils.getLoginUser();
		try {
			userService.editPassword(user.getId(),userModel.getPassword());
		} catch (Exception e) {
			f="0";
			e.printStackTrace();
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=utf8");
		ServletActionContext.getResponse().getWriter().write(f);
		return NONE;
	}
	
	/**
		username:11
		password:11
		salary:11
		birthday:2017-07-17
		gender:男
		station:基地运转中心
		telephone:1111
		roleIds:4028d5815d97ddd4015d97de48da0000
	*/
	
	//属性驱动,接收多个roleIds
	
	private String[] roleIds;
	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}
	
	public String add() {
		userService.save(model,roleIds);
		return "list";
	}
	
	/**
	 * 用户数据的分页查询	
	 */
	public String pageQuery(){
		userService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{"noticebills","roles"});
		return NONE;
	} 
	
}
