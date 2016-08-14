package com.joken.mqs.constants;

import com.joken.common.utils.StringUtils;
import com.joken.mqs.util.PropertyGet;


/**
 * mq订阅枚举类
 * @Auther Hanzibin
 * @date 6:09:55 PM,Apr 13, 2016
 */
public enum MqDestinationEnum {
	
	/**
	 * 机柜控制
	 */
	MQ_CABINET("joken.mq.service"),
	
	;
	
	private String value; 

	MqDestinationEnum(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
	
	public String val(Object... vals) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		if(vals==null || vals.length==0){
			return PropertyGet.getApplicationProperty(value);
		}
		return null;
	}
}
