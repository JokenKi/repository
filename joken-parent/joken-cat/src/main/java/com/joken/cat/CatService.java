/*
 * @(#)NoticeService.java	2015-8-21 下午3:25:12
 *
 * Copyright 2015 www.ifood517.com. All rights reserved.
 * www.ifood517.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.joken.cat;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.joken.common.utils.StringUtils;

/**
 * 消息通知方法
 * @version V1.0.0, 2015-8-21
 * @author 欧阳增高
 * @since V1.0.0
 */
public class CatService {
	private static ApplicationContext applicationContext;
	static {
		String confDir = System.getProperty("conf.dir");
		if (StringUtils.isEmpty(confDir)) {
			confDir = System.getenv("conf.dir");
		}
		if (StringUtils.isEmpty(confDir)) {
			confDir = "";
		}
		applicationContext = new ClassPathXmlApplicationContext(confDir
				+ "/applicationContext-dubbo-cat.xml");
	}

	/**
	 * 获取上下文
	 * 
	 * @return ApplicationContext
	 * @author 欧阳增高
	 * @date 2015-8-21 下午3:28:35
	 */
	public static ApplicationContext getInstance() {
		return applicationContext;
	}

	/**
	 * @param args
	 * @author 欧阳增高
	 * @throws IOException
	 * @date 2015-8-21 下午3:25:12
	 */
	public static void main(String[] args) throws IOException {
		System.in.read();
	}

}
