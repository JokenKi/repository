/**
 * 
 */
package com.joken.base.service;

import com.joken.common.model.ResponseModel;

/**
 * 用户登录接口
 * 
 * @author 欧阳增高
 * 
 */
public interface LoginService {

	/**
	 * 后台用户登录
	 * 
	 * @param loginName
	 *            登录名称
	 * @param pwd
	 *            登录密码
	 * @return 验证后的响应实体对象
	 */
	ResponseModel userLogin(String loginName, String pwd);

}
