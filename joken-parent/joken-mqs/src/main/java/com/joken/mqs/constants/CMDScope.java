package com.joken.mqs.constants;

/**
 * 命令的作用范围
 * @Auther Hanzibin
 * @date 6:12:40 PM,Apr 14, 2016
 */
public enum CMDScope {

	/**
	 * 单个
	 */
	SINGLE(1),
	
	ALL(0)
	
	;
	
	private int value; 

	CMDScope(int value) {
		this.value = value;
	}

	public int val() {
		return value;
	}
}
