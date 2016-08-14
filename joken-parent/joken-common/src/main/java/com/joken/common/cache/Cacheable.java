/*
 * @(#)Cacheable.java	2015-9-29 下午4:08:52
 *
 * Copyright 2015 www.ifood517.com. All rights reserved.
 * www.ifood517.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.joken.common.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存注解类
 * 
 * @version V1.0.0, 2015-9-29
 * @author 欧阳增高
 * @since V1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Cacheable {

	/**
	 * 缓存key
	 * 
	 * @return
	 * @author 欧阳增高
	 * @date 2015-9-29 下午4:09:35
	 */
	// @Deprecated
	// public CacheableConfig key() default CacheableConfig.NULL;

	/**
	 * 字符串键名
	 * 
	 * @return String
	 * @author 欧阳增高
	 * @date 2015-10-21 上午11:09:13
	 */
	public String keyName() default "";

	/**
	 * 缓存模式
	 * 
	 * @return CacheMode
	 * @author 欧阳增高
	 * @date 2015-9-29 下午4:09:46
	 */
	public CacheMode cacheMode() default CacheMode.STRING;

	/**
	 * 缓存返回类型类
	 * 
	 * @return Class
	 * @author 欧阳增高
	 * @date 2015-10-16 下午8:00:29
	 */
	public Class<?> returnMode() default Boolean.class;

	/**
	 * 是否需要保存拦截方法返回值到缓存中
	 * 
	 * @return
	 * @author 欧阳增高
	 * @date 2015-10-18 上午11:55:32
	 */
	// public boolean saved() default true;

	/**
	 * 删除缓存操作
	 * 
	 * @return boolean
	 * @author 欧阳增高
	 * @date 2015-10-28 下午3:15:36
	 */
	// public boolean delete() default false;

	/**
	 * 缓存多少秒,默认一天
	 * 
	 * @return int
	 * @author 欧阳增高
	 * @date 2015-9-29 下午4:10:00
	 */
	public int expire() default -1;

	/**
	 * 设置以传入参数为键时，传入的值类型,STRING为字符串，LIST为集合（包含List,Set,Array）
	 * 
	 * @return CacheKeyMode
	 * @author 欧阳增高
	 * @date 2015-10-27 下午4:31:41
	 */
	public CacheKeyMode keyMode() default CacheKeyMode.STRING;

	/**
	 * 如果缓存中已存在时，继续将值推送进缓存
	 * 
	 * @return
	 * @author 欧阳增高
	 * @date 2015-12-6 上午10:43:43
	 */
	// public boolean append() default false;

	/**
	 * 缓存操作模式
	 * 
	 * @return CacheHandleMode
	 * @author 欧阳增高
	 * @date 2015-12-21 下午4:46:57
	 */
	public CacheHandleMode handleMode() default CacheHandleMode.save;

	/**
	 * 参数是否以数组方式组装
	 * 
	 * @return boolean
	 */
	public boolean keyArray() default false;

}
