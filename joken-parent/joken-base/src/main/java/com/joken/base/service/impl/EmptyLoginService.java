/**
 * 
 */
package com.joken.base.service.impl;

import org.springframework.stereotype.Service;

import com.joken.base.service.LoginService;
import com.joken.common.model.ResponseModel;
import com.joken.common.properties.MsgProperties;
import com.joken.common.utils.JSONUtils;

/**
 * @author 欧阳增高
 * 
 */
@Service("LoginService")
public class EmptyLoginService implements LoginService {

	@Override
	public ResponseModel userLogin(String loginName, String pwd) {
		if ("admin".equals(loginName) && "123456".equals(pwd)) {
			return MsgProperties.getSuccess(JSONUtils
					.toJSON("{\"user\":\"admin\"}"));
		}
		return null;
	}

}
