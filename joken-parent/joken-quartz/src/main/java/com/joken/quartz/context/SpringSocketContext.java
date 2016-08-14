/**
 * 
 */
package com.joken.quartz.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * spring上下文实现类
 * 
 * @version V1.0.0, 2016-3-15
 * @author 欧阳增高
 * @since V1.0.0
 */
public class SpringSocketContext extends Thread implements SocketContext {

	/**
	 * spring上下文
	 */
	private static ApplicationContext applicationContext;
	/**
	 * socket上下文
	 */
	private static SocketContext socketContext;

	/**
	 * 初始化块
	 */
	static {
		socketContext = new SpringSocketContext();
		applicationContext = new ClassPathXmlApplicationContext(
				"spring/applicationContext-quartz.xml");
	}

	/**
	 * 私有构造
	 */
	private SpringSocketContext() {
	}

	/**
	 * 获取socket上下文实例
	 * 
	 * @return SocketContext
	 * @author 欧阳增高
	 * @date 2016-3-15 上午10:33:46
	 */
	public static SocketContext getInstance() {
		return socketContext;
	}

	/**
	 * 获取类的实例
	 */
	@Override
	public Object getBean(String name) {
		return applicationContext.getBean(name);
	}
}
