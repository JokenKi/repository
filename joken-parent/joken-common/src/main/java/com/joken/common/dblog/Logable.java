package com.joken.common.dblog;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 日志记录注解
 * 
 * @author wangby
 * @date 2016年5月23日上午10:43:34
 */
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Logable {

	/**
	 * 
	 * 需要记录日志的属性名
	 * 
	 * @return String[]
	 * @date 2016年5月23日上午10:46:02
	 */
	String[] logFields();

	/**
	 * 对需要记录日志属性进行的运算
	 * 
	 * @return CalOprator[]
	 * @date 2016年5月23日上午10:47:05
	 */
	CalOprator[] logFieldsOp() default {};

	/**
	 * 表名
	 * 
	 * @return String
	 * @date 2016年5月23日上午10:46:29
	 */
	String tableName();

	/**
	 * 作为查询条件的属性名
	 * 
	 * @return String[]
	 * @date 2016年5月23日上午10:46:45
	 */
	String[] whereFields();

	/**
	 * 作为查询条件的属性对应的数据库操作符
	 * 
	 * @return DbOperator[]
	 * @date 2016年5月23日上午10:47:05
	 */
	DbOperator[] whereFieldsOp() default {};
}
