/**
 * 
 */
package com.joken.web.controllers;

import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import com.joken.base.service.BaseService;
import com.joken.common.model.ResponseModel;
import com.joken.common.properties.SystemGlobal;

/**
 * 错误处理Controller
 * 
 * @author 欧阳增高
 * 
 */
@Path("Error")
public class ErrorController extends BaseController {

	/**
	 * 404错误提示信息
	 * 
	 * @return 错误信息
	 */
	@Get("404")
	public String _404() {
		return this.getJsonWrite(getMsg("404"));
	}

	/**
	 * 500错误提示信息
	 * 
	 * @return 错误信息
	 */
	@Get("500")
	public String _500() {
		return this.getJsonWrite(getMsg("500"));
	}

	/**
	 * 获取错误信息
	 * 
	 * @param code
	 *            错误码
	 * @return 错误信息
	 */
	private String getMsg(String code) {
		ResponseModel model = new ResponseModel(SystemGlobal.get("MSG_FAILUER_"
				+ code));
		model.setStatus(Integer.valueOf(code));
		model.setSuccess(ResponseModel.SuccessStatus.FAIL);
		return model.toString();
	}

	@Override
	public BaseService<?> getService() {
		return null;
	}
}
