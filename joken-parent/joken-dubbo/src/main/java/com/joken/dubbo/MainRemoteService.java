/*
 * @(#)MainRemoteService.java	2015-8-22 下午3:39:27
 *
 * Copyright 2015 www.ifood517.com. All rights reserved.
 * www.ifood517.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.joken.dubbo;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.joken.common.utils.StringUtils;

/**
 * dubbo服务启动入口类
 * 
 * @version V1.0.0, 2015-8-22
 * @author 欧阳增高
 * @since V1.0.0
 */
public class MainRemoteService {
	/**
	 * 容器上下文
	 */
	private static ApplicationContext applicationContext;

	/**
	 * 获取spring容器上下文
	 * 
	 * @return 容器上下文
	 * @author 欧阳增高
	 * @date 2015-8-22 下午3:50:40
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 初始化spring
	 * 
	 * @param conf
	 *            应用配置路径
	 * @author 欧阳增高
	 * @date 2015-8-22 下午3:50:04
	 */
	private static void initApplicationContext(String conf) {
		if (applicationContext == null) {
			// ClassPathXmlApplicationContext app = null;
			try {
				if (!StringUtils.isEmpty(conf)) {
					applicationContext = new ClassPathXmlApplicationContext(
							conf + "/spring/applicationContext.xml");
				}
			} catch (Exception e) {
			}
			if (applicationContext == null) {
				applicationContext = new ClassPathXmlApplicationContext(
						"spring/applicationContext.xml");
			}
			// applicationContext = app;
			// app.start();
		}
	}

	/**
	 * 启动入口方法
	 * 
	 * @param args
	 *            启动参数
	 * @author 欧阳增高
	 * @throws IOException
	 * @date 2015-8-22 下午3:39:28
	 */
	public static void main(String[] args) throws IOException {
		String conf = System.getProperty("conf.dir");
		if (conf == null || conf.length() == 0) {
			String base = StringUtils.getValue(System.getProperty("basedir"),
					"");
			if (!StringUtils.isEmpty(base)) {
				base = base + "/";
			}
			System.setProperty("conf.dir", base + "conf");
		}
		MainRemoteService.initApplicationContext(conf);

		// while (true) {
		// System.out.println(1);
		// System.in.read();
		// }

	}

}
