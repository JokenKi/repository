/**
 * 
 */
package com.joken.quartz.context;

/**
 * 上下文服务接口
 * 
 * @version V1.0.0, 2016-3-15
 * @author 欧阳增高
 * @since V1.0.0
 */
public interface SocketContext {

	/**
	 * 获取指定名称服务
	 * 
	 * @param name
	 *            需要获取的服务名
	 * @return 获取到的服务对象
	 * @author 欧阳增高
	 * @date 2016-3-15 上午10:31:14
	 */
	Object getBean(String name);
}
