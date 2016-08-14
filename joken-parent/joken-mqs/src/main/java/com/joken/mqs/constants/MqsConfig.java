/*
 * @(#)SystemKeyConfig.java	2016-3-30 下午5:34:52
 *
 * Copyright 2016 www.ifood517.com. All rights reserved.
 * www.ifood517.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.joken.mqs.constants;

import com.joken.common.properties.BaseConfig;
import com.joken.common.properties.SystemGlobal;
import com.joken.common.utils.StringUtils;
import com.joken.mqs.util.PropertyGet;

/**
 * 配置文件键值枚举类
 * 
 * @version V1.0.0, 2016-3-30
 * @author 欧阳增高
 * @since V1.0.0
 */
public enum MqsConfig implements BaseConfig {
	
	/**
	 * 开门、加热制冷下发指令模板
	 */
	MQS_DOOR_CMD_ALL("system.mqs.door.cmd.all"),
	
	/**
	 * 开门、加热制冷下发指令模板
	 */
	MQS_DOOR_CMD("system.mqs.door.cmd"),
	
	/**
	 * MQS订阅名称
	 */
	MQ_SERVICE("joken.mq.service"),
	
	/**
	 * MQS订阅失效时间,单位：毫秒86400000=1天
	 */
	MQ_SERVICE_EXPIRE("joken.mq.service.expire"),
	
	/**
	 * 向机柜下发指令模板
	 */
	MQS_CMD("system.mqs.cmd"),
	
	/**
	 * 开门、加热制冷下发指令模板
	 */
	MQS_DOOR_CMD_KEYS("system.mqs.door.cmd.keys"),
	
	/**
	 * 机柜控制
	 */
	MQ_Destination_Cabinet("joken.mq.service"),
	
	/**
	 * mqs 默认持久化
	 */
	MQ_If_Persist_Type("joken.mq.ifPersist"),

	/**
	 * 默认类型
	 */
	MQ_Destination_Type("joken.mq.destinationType"),
	
	;
	private String value; 

	MqsConfig(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

	/**
	 * 获取配置值
	 * 
	 * @param vals
	 *            需要填充的参数值
	 * @return 格式化填充后的配置值
	 * @author 欧阳增高
	 * @date 2015-9-29 下午6:02:37
	 */
	public String val(Object... vals) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		if(vals==null || vals.length==0){
			return PropertyGet.getApplicationProperty(value);
		}
		return null;
	}
	
	/**
	 * 获取键对应的失效时间
	 * 
	 * @return 失效配置
	 * @author 欧阳增高
	 * @date 2015-9-29 下午6:45:20
	 */
	public int expire() {
		return SystemGlobal.getInteger(value + ".expire");
	}
	
	/**
	 * 获取配置值
	 *
	 * @return Boolean
	 * @author 杨艳芳
	 * @date 2016-4-6 上午10:15:11
	 */
	public Boolean getBoolean(){
		return SystemGlobal.getBoolean(value);
	}
	
	/**
	 * 获取配置值
	 *
	 * @return Integer
	 * @author 杨艳芳
	 * @date 2016-4-6 上午10:15:11
	 */
	public Integer getInteger(){
		return SystemGlobal.getInteger(value);
	}
}
