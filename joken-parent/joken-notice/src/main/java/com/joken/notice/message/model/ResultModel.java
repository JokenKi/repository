package com.joken.notice.message.model;

import com.joken.notice.message.constants.ErrorCode;

/**
 * 返回值
 * <p>
 * 。
 * 
 * @version V0.0.1, Aug 7, 2015
 * @author Hanzibin
 * @since V0.0.1
 */
public class ResultModel {

	/**
	 * 数据
	 */
	private Object data;

	/**
	 * 状态码
	 */
	private int code = 0;

	/**
	 * 返回信息
	 */
	private String msg = "success";

	public ResultModel() {

	}

	/**
	 * 正常数据返回的view
	 * 
	 * @param object
	 */
	public ResultModel(Object object) {
		this.data = object;
	}

	/**
	 * 抛出异常时返回的view
	 * 
	 * @param errorCode
	 *            错误码
	 */
	public ResultModel(ErrorCode errorCode) {
		this.code = errorCode.getCode();
		this.msg = errorCode.getDescription();
	}

	/**
	 * @author Hanzibin
	 * @time 6:59:25 PM ,Aug 7, 2015
	 * @param code
	 *            错误号
	 * @param msg
	 *            错误信息
	 */
	public ResultModel(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
