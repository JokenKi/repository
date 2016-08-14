package com.joken.web.controllers.required;

import java.lang.annotation.Annotation;

import net.paoding.rose.web.ControllerInterceptorAdapter;
import net.paoding.rose.web.Invocation;

import com.joken.common.properties.SystemProperties;
import com.joken.common.utils.StringUtils;

/**
 * 默认登录验证实现类
 * 
 * @author 欧阳增高
 * 
 */
public class LoginRequredInterceptor extends ControllerInterceptorAdapter {
	/**
	 * 默认构造
	 */
	public LoginRequredInterceptor() {
		setPriority(900);
	}

	/**
	 * 获取登录注解类
	 */
	@Override
	protected Class<? extends Annotation> getRequiredAnnotationClass() {
		return LoginRequired.class;
	}

	/**
	 * 获取免登录注解类
	 */
	@Override
	protected Class<? extends Annotation> getDenyAnnotationClass() {
		return LoginDenyRequired.class;
	}

	/**
	 * 登录验证检查
	 */
	@Override
	protected Object before(Invocation inv) throws Exception {
		Object loginUser = inv.getRequest().getSession()
				.getAttribute("loginUser");
		if (loginUser == null) {
			String requestUrl = inv.getRequest().getHeader("referer");
			inv.getRequest().getSession().setAttribute("preUrl", requestUrl);
			String url = StringUtils.getValue(
					SystemProperties.getDefaultLoginPath(), "/login.jsp");
			return "r:" + url;
		}
		return true;
	}
}