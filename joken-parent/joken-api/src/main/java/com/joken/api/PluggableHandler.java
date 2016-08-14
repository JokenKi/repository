/*
 * @(#)PluggableHandler.java	2015-12-16 下午3:10:02
 *
 * Copyright 2015 www.ifood517.com. All rights reserved.
 * www.ifood517.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.joken.api;

/**
 * 可插拔处理器接口
 * 
 * @author hemengmeng
 * @date 2015-12-16
 */
public interface PluggableHandler {

	/**
	 * 处理器处理逻辑
	 * 
	 * @param obj
	 *            传入的需要处理的业务数据
	 */
	public void handle(Object obj);

}
