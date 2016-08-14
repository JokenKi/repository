package com.joken.cat.constant;

/**
 * 时间类型 
 * @Auther Hanzibin
 * @date 11:29:52 AM,Mar 29, 2016
 */
public enum CatEvent {

	SUCCESS("0");
	
	private String value;

	public String getValue() {
		return value;
	}
	
	CatEvent(String val){
		this.value = val;
	}
}
