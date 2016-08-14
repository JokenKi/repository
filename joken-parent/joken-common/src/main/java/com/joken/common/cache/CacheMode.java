/*
 * @(#)CacheMode.java	2015-10-16 下午7:29:13
 *
 * Copyright 2015 www.ifood517.com. All rights reserved.
 * www.ifood517.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.joken.common.cache;

import com.joken.common.utils.JSONUtils;

/**
 * 
 * 缓存保存模式
 * 
 * @version V1.0.0, 2015-10-16
 * @author 欧阳增高
 * @since V1.0.0
 */
public enum CacheMode {
	/**
	 * 以Map Hash方式缓存数据
	 */
	MAP,
	/**
	 * 以list方式缓存数据
	 */
	LIST,
	/**
	 * 以SET方式缓存数据
	 */
	SET,

	/**
	 * json格式数据
	 */
	JSON {
		/**
		 * 转换为JSON
		 * 
		 * @param value
		 * @return
		 * @author 欧阳增高
		 * @date 2015-10-16 下午9:08:34
		 */
		public Object cast(Object value) {
			return JSONUtils.parseObject(value).toString();
		}
	},
	/**
	 * json数组格式
	 */
	JSONARRAY {
		/**
		 * 转换为JSON
		 * 
		 * @param value
		 * @return
		 * @author 欧阳增高
		 * @date 2015-10-16 下午9:08:34
		 */
		public Object cast(Object value) {
			return JSONUtils.parseArray(value).toString();
		}
	},
	/**
	 * 以字符串缓存数据
	 */
	STRING;

	/**
	 * 转换为JSON
	 * 
	 * @param value
	 * @return Object
	 * @author 欧阳增高
	 * @date 2015-10-16 下午9:08:34
	 */
	public Object cast(Object value) {
		return value;
	}
}
