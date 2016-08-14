package com.joken.common.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存过期时间值注解类
 * 
 * 
 * @version V1.0.0, 2015-9-29
 * @author 欧阳增高
 * @since V1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER })
public @interface ExpireKey {
}
