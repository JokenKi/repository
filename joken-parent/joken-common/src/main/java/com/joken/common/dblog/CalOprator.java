package com.joken.common.dblog;

/**
 * 计算运算符
 * 
 * @author wangby
 * @date 2016年5月26日下午2:27:18
 */
public enum CalOprator {

	/**
	 * 无操作
	 */
	NONE(""),
	/**
	 * 加
	 */
	ADD("add"),
	/**
	 * 减
	 */
	SUBTRACT("subtract"),
	/**
	 * 乘
	 */
	MULTIPLY("multiply"),
	/**
	 * 除
	 */
	DIVIDE("divide");

	private String method;

	private CalOprator(String method) {
		this.method = method;
	}

	public String method() {
		return this.method;
	}
}
