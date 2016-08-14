/**
 * 
 */
package com.joken.common.exception;

/**
 * 基础异常封装类
 * 
 * @version V1.0.0
 * @author 欧阳增高
 */
public class BaseException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2449710174006468334L;

	/**
	 * 异常抛出操作参数
	 */
	private Object param;

	/**
	 * 构造
	 */
	public BaseException() {
		super();
	}

	/**
	 * 构造
	 * 
	 * @param message
	 *            异常信息
	 */
	public BaseException(String message) {
		super(message);
	}

	/**
	 * 构造
	 * 
	 * @param cause
	 *            引起异常原因对象
	 */
	public BaseException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 * @param message
	 *            异常信息
	 * @param param
	 *            需要抛出异常数据
	 */
	public BaseException(String message, Object param) {
		super(message);
		this.setParam(param);
	}

	/**
	 * 
	 * @param cause
	 *            引起异常原因对象
	 * @param param
	 *            需要抛出异常数据
	 */
	public BaseException(Throwable cause, Object param) {
		super(cause);
		this.setParam(param);
	}

	/**
	 * 获取异常抛出数据对象
	 * 
	 * @return 异常抛出数据对象
	 * @author 欧阳增高
	 * @date 2016-3-1 下午2:26:46
	 */
	public Object getParam() {
		return param;
	}

	/**
	 * 设置异常数据对象
	 * 
	 * @param param
	 *            需要抛出数据对象
	 * @author 欧阳增高
	 * @date 2016-3-1 下午2:27:00
	 */
	public void setParam(Object param) {
		this.param = param;
	}
}
