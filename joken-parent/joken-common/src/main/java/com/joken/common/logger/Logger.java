package com.joken.common.logger;

/**
 * 
 * 日志接口
 * 
 * @version V1.0.0, 2016-3-15
 * @author 欧阳增高
 * @since V1.0.0
 */
public interface Logger {

	/**
	 * 默认日志名称
	 */
	static final String ROOT_LOGGER_NAME = "root";

	/**
	 * 获取日志分类名称
	 * 
	 * @return 名称
	 */
	String getName();

	/**
	 * 是否开启堆栈日志
	 * 
	 * @return boolean
	 */
	boolean isTraceEnabled();

	/**
	 * 记录堆栈日志
	 * 
	 * @param msg
	 *            需要记录的信息
	 */
	void trace(String msg);

	/**
	 * 记录堆栈日志
	 * 
	 * @param msg
	 *            需要记录的信息
	 * @param obj
	 *            需要处理的业务数据
	 */
	void trace(String msg, Object obj);

	/**
	 * 记录堆栈日志
	 * 
	 * @param msg
	 *            需要记录的信息
	 * @param throwable
	 *            异常对象
	 */
	void trace(String msg, Throwable throwable);

	/**
	 * 是否启用debug日志
	 * 
	 * @return boolean
	 */
	boolean isDebugEnabled();

	/**
	 * 记录dubug级别日志
	 * 
	 * @param msg
	 *            需要记录的信息
	 */
	void debug(String msg);

	/**
	 * 记录dubug级别日志
	 * 
	 * @param msg
	 *            需要记录的信息
	 * @param obj
	 *            需要处理的业务数据
	 */
	void debug(String msg, Object obj);

	/**
	 * 记录dubug级别日志
	 * 
	 * @param msg
	 *            需要记录的信息
	 * @param throwable
	 *            异常对象
	 */
	void debug(String msg, Throwable throwable);

	/**
	 * 是否开启Info级别日志
	 * 
	 * @return boolean
	 */
	boolean isInfoEnabled();

	/**
	 * 记录Info级别日志
	 * 
	 * @param msg
	 *            需要记录的信息
	 */
	void info(String msg);

	/**
	 * 记录Info级别日志
	 * 
	 * @param msg
	 *            需要记录的信息
	 * @param obj
	 *            需要处理的业务数据
	 */
	void info(String msg, Object obj);

	/**
	 * 记录Info级别日志
	 * 
	 * @param msg
	 *            需要记录的信息
	 * @param throwable
	 *            异常对象
	 */
	void info(String msg, Throwable throwable);

	/**
	 * 是否开启warn级别日志
	 * 
	 * @return boolean
	 */
	boolean isWarnEnabled();

	/**
	 * 记录warn级别日志
	 * 
	 * @param msg
	 *            需要记录的信息
	 */
	void warn(String msg);

	/**
	 * 记录warn级别日志
	 * 
	 * @param msg
	 *            需要记录的信息
	 * @param obj
	 *            异常业务数据
	 */
	void warn(String msg, Object obj);

	/**
	 * 记录warn级别日志
	 * 
	 * @param msg
	 *            需要记录的信息
	 * @param throwable
	 *            异常对象
	 */
	void warn(String msg, Throwable throwable);

	/**
	 * 是否开启error级别日志
	 * 
	 * @return boolean
	 */
	boolean isErrorEnabled();

	/**
	 * 记录error级别日志
	 * 
	 * @param msg
	 *            需要记录的信息
	 */
	void error(String msg);

	/**
	 * 记录error级别日志
	 * 
	 * @param msg
	 *            需要记录的信息
	 * @param obj
	 *            异常业务数据
	 */
	void error(String msg, Object obj);

	/**
	 * 记录error级别日志
	 * 
	 * @param msg
	 *            需要记录的信息
	 * @param throwable
	 *            异常对象
	 */
	void error(String msg, Throwable throwable);
}
