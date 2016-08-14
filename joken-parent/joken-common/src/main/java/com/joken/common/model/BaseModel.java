/**
 * @(#)CabController.java	V0.0.1 2015-7-22, 上午9:47:14
 *
 * Copyright 2015 www.ifood517.com. All rights reserved.
 * www.ifood517.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.joken.common.model;

import java.io.Serializable;
import java.util.Map;

/***
 * 类描述：基础简单对象抽象现实类
 * 
 * @version V1.0.0
 * @author 欧阳增高
 */
public abstract class BaseModel implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9011449020663460637L;

	/**
	 * 实体编号值
	 */
	private Object entityKeyValues;

	/**
	 * 获取实体主键值
	 * 
	 * @return the entityKeyValues
	 */
	public Object getEntityKeyValues() {
		return entityKeyValues;
	}

	/**
	 * 设置实体主键值
	 * 
	 * @param entityKeyValues
	 *            the entityKeyValues to set
	 */
	public void setEntityKeyValues(Object entityKeyValues) {
		this.entityKeyValues = entityKeyValues;
	}

	private Map<String, Object> params;

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

}
