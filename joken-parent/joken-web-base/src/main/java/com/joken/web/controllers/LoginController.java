/**
 * 
 */
package com.joken.web.controllers;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.springframework.beans.factory.annotation.Autowired;

import com.joken.base.service.BaseService;
import com.joken.base.service.LoginService;
import com.joken.common.model.BaseModel;
import com.joken.common.model.ResponseModel;
import com.joken.common.properties.MsgProperties;
import com.joken.common.properties.SystemProperties;
import com.joken.common.utils.StringUtils;
import com.joken.web.controllers.validator.NotBlank;

/**
 * 登录控制器类
 * 
 * @author 欧阳增高
 * 
 */
@Path("login")
public class LoginController extends BaseController {

	/**
	 * 注入Rose架构请求封装
	 */
	@Autowired
	private Invocation inv;

	/**
	 * 注入用户登录接口实现类
	 */
	@Autowired(required = false)
	protected LoginService loginService;

	/**
	 * @param loginService
	 *            the loginService to set
	 */
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	/**
	 * 用户登录
	 * 
	 * @param login
	 *            用户登录用户名
	 * @param pwd
	 *            用户登录密码
	 * @param code
	 *            登录验证码
	 * @return 登录信息
	 */
	@Post
	public String login(@NotBlank @Param("userName") String login,
			@NotBlank @Param("password") String pwd,
			@NotBlank @Param("vcode") String code) {
		String validateCode = (String) inv.getRequest().getSession()
				.getAttribute("validateCode");
		if (!code.equals(validateCode)) {
			return this.getJsonWrite(MsgProperties.getRequestVerifyNopass());
		}
		ResponseModel emp = loginService.userLogin(login, pwd);
		if (emp == null) {
			return this.getJsonWrite(MsgProperties.getRequestVerifyNopass());
		}
		// if ((emp instanceof ResponseModel)) {
		// ResponseModel model = (ResponseModel) emp;
		// if (!model.isSuccess()) {
		// return this.getJsonWrite(emp.toString());
		// }
		// emp = model.getData();
		// }
		inv.getRequest().getSession().setAttribute("loginUser", emp.getData());
		return this.getJsonWrite(emp.toString());
	}

	/**
	 * 打开登录页面
	 * 
	 * @return 登录路径
	 */
	@Get
	public String login() {
		return logout();
	}

	/**
	 * 退出登录
	 * 
	 * @return 登录路径
	 */
	@Get("logout")
	public String logout() {
		inv.getRequest().getSession().invalidate();
		String url = StringUtils.getValue(
				SystemProperties.getDefaultLoginPath(), "/login.jsp");
		return "r:" + url;
	}

	/**
	 * 验证是否已登录
	 * 
	 * @return 验证信息
	 * @author 欧阳增高
	 * @date 2016-3-15 上午10:57:11
	 */
	@Get("validate")
	public String validate() {
		Object session = inv.getRequest().getSession()
				.getAttribute("loginUser");
		if (session == null) {
			return this.getJsonWrite(MsgProperties.getRequestVerifyNopass());
		}
		return this.getJsonWrite(MsgProperties.getSuccess());
	}

	@Override
	public BaseService<? extends BaseModel> getService() {
		return null;
	}

}
