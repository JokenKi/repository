package com.joken.cat.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Transaction注解
 * @author Hanzibin
 */
@Inherited
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CatTransaction {
	
	/**
	 * Transaction的类型
	 * @return
	 */
	String type() default "";
	
	/**
	 * Transaction的名称
	 * @return
	 */
	String name() default "";
}
