package com.joken.api;

/**
 * 监控远程调用接口
 * 
 * @version V1.0.0, 2016-4-7
 * @author Hanzibin
 * @since V1.0.0
 */
public interface LogRemoteService {

	/**
	 * 记录事件
	 * 
	 * @Auther Hanzibin
	 * @date 11:43:10 AM,Mar 29, 2016
	 * @param type
	 *            类型
	 * @param name
	 *            名称
	 * @param nameValuePairs
	 *            信息
	 */
	Object logEvent(String type, String name, String nameValuePairs);
}
