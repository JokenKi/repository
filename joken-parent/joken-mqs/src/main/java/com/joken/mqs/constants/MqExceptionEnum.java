package com.joken.mqs.constants;


/**
 * mq参数异常枚举类
 * @Auther Hanzibin
 * @date 6:09:55 PM,Apr 13, 2016
 */
public enum MqExceptionEnum {
	
	Not_Enough_Params("参数不完整"),
	
	;
	
	private String value; 

	MqExceptionEnum(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
	
	public String val() {
		return value;
	}
}
