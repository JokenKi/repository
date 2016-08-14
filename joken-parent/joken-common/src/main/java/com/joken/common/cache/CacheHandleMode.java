/*
 * @(#)CacheMode.java	2015-10-16 下午7:29:13
 *
 * Copyright 2015 www.ifood517.com. All rights reserved.
 * www.ifood517.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.joken.common.cache;

/**
 * 
 * 缓存保存模式
 * 
 * @version V1.0.0, 2015-10-16
 * @author 欧阳增高
 * @since V1.0.0
 */
public enum CacheHandleMode {
	/**
	 * 不进行操作
	 */
	nil,
	/**
	 * 保存模式
	 */
	save,
	/**
	 * 删除模式
	 */
	delete,
	/**
	 * 追加模式
	 */
	append;

}
