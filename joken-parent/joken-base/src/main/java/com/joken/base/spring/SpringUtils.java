package com.joken.base.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.joken.common.utils.StringUtils;

/**
 * 
 * <p>Title: SpringUtils</p>
 * <p>Description: spring工具类</p>
 * @author 王波洋
 * @date 2016年3月29日   上午11:51:38
 */
public class SpringUtils {

	private static volatile ApplicationContext context;
	
	public static Object getBean(String name) {
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		ApplicationContext ctx = getContext();
		return ctx.getBean(name);
	}
	
	public static ApplicationContext getContext(){
		if(context != null){
			return context;
		}
		
		synchronized (SpringUtils.class) {
			if (context == null) {
				context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
			}
			
			if (context instanceof ConfigurableApplicationContext) {
				((ConfigurableApplicationContext)context).registerShutdownHook();
			}
		}
		return context;
	}
}
