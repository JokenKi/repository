package com.joken.common.dblog;

/**
 * 数据库运算符
 * 
 * @author wangby
 * @date 2016年5月26日下午2:26:54
 */
public enum DbOperator {
	/**
	 * 等于
	 */
	EQ,
	/**
	 * 等于
	 */
	NOT_EQ,
	/**
	 * 在...中
	 */
	IN,
	/**
	 * 不在...中
	 */
	NOT_IN,
	/**
	 * 大于
	 */
	GT,
	/**
	 * 小于
	 */
	ST,
	/**
	 * 大于等于
	 */
	GTE,
	/**
	 * 小于等于
	 */
	STE,
	/**
	 * 在..到..之间
	 * 
	 * BETWEEN_END;
	 */

}
