/**
 * @(#)CabController.java	V0.0.1 2015-7-22, 上午9:47:14
 *
 * Copyright 2015 www.ifood517.com. All rights reserved.
 * www.ifood517.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.joken.common.model;

import java.util.Map;

import com.joken.common.properties.SystemGlobal;
import com.joken.common.utils.JSONUtils;

/**
 * 数据响应模型对象
 * 
 * @version V1.0.0
 * @author 欧阳增高
 * 
 */
public class ResponseModel extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4384010186893198911L;

	/**
	 * 操作成功失败状态
	 * 
	 * @author 欧阳增高
	 * 
	 */
	public enum SuccessStatus {
		/**
		 * 成功
		 */
		SUCCESS(true),
		/**
		 * 失败
		 */
		FAIL(false);

		/**
		 * 枚举值
		 */
		private boolean value = true;

		private SuccessStatus(boolean value) {
			this.value = value;
		}

		public boolean getValue() {
			return this.value;
		}
	}

	/**
	 * 默认失败操作实例
	 */
	public static ResponseModel FAIL = new ResponseModel();
	static {
		FAIL.setStatus(1000);
		FAIL.setResult(SystemGlobal.get("MSG_FAILURE"));
		FAIL.setSuccess(SuccessStatus.FAIL);
	}

	/**
	 * 默认成功操作实例
	 */
	public static ResponseModel SUCC = new ResponseModel();
	static {
		SUCC.setResult(SystemGlobal.get("MSG_SUCCESS"));
	}

	/**
	 * 执行成功状态
	 */
	private SuccessStatus success = SuccessStatus.SUCCESS;

	/**
	 * 返回执行状态码,如成功编号
	 */
	private Integer status = 1001;

	/**
	 * 返回字符串信息
	 */
	private String result;

	/**
	 * 返回JSON对象数据
	 */
	private Object data;

	/**
	 * 构造
	 */
	public ResponseModel() {
	}

	/**
	 * 构造
	 * 
	 * @param msg
	 */
	public ResponseModel(String msg) {
		this.result = msg;
		// this.error = msg;
	}

	/**
	 * 构造
	 * 
	 * @param msg
	 * @param data
	 */
	public ResponseModel(String msg, Object data) {
		this.result = msg;
		// this.error = msg;
		this.data = data;
	}

	/**
	 * 构造
	 * 
	 * @param success
	 *            成功状态枚举对象
	 * @param code
	 *            状态码
	 * @param msg
	 *            错误信息内容
	 * @param data
	 *            响应的业务数据
	 */
	public ResponseModel(SuccessStatus success, Integer code, String msg,
			Object data) {
		this.success = success;
		this.status = code;
		this.result = msg;
		// this.error = msg;
		this.data = data;
	}

	public ResponseModel(boolean success,String msg,Object data){
		this.isSuccess(); 
		this.result = msg;
		// this.error = msg;
		this.data = data;
	}
	
	
	/**
	 * 获取操作成功状态
	 * 
	 * @return boolean
	 */
	public boolean isSuccess() {
		return success.getValue();
	}

	/**
	 * @param status
	 *            the success to set
	 */
	public void setSuccess(SuccessStatus status) {
		this.success = status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setSuccess(boolean status) {
		this.success = status ? SuccessStatus.SUCCESS : SuccessStatus.FAIL;
	}

	/**
	 * @return 状态码
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the msg
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param msg
	 *            the msg to set
	 */
	public void setResult(String msg) {
		this.result = msg;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * 获取响应数据中指定key的值
	 * 
	 * @param key
	 *            需要获取的属性名
	 * @return String
	 */
	@SuppressWarnings("rawtypes")
	public Object getDataByKey(String key) {
		if (data != null && data instanceof Map) {
			return ((Map) data).get(key);
		}
		return null;
	}

	/**
	 * 获取实体对象JOSN数据
	 */
	@Override
	public String toString() {
		return JSONUtils.toJSONString(this);
	}
}
