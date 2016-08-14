/*
 * @(#)RedisCacheableAop.java	2015-9-29 下午4:12:52
 *
 * Copyright 2015 www.ifood517.com. All rights reserved.
 * www.ifood517.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.joken.base.spring.aop;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import com.joken.common.cache.CacheHandleMode;
import com.joken.common.cache.CacheKey;
import com.joken.common.cache.Cacheable;
import com.joken.common.cache.ExpireKey;
import com.joken.common.cache.RedisTemplate;
import com.joken.common.logger.Logger;
import com.joken.common.logger.LoggerFactory;
import com.joken.common.model.ResponseModel;
import com.joken.common.properties.MsgProperties;
import com.joken.common.properties.SystemGlobal;
import com.joken.common.utils.JSONUtils;
import com.joken.common.utils.StringUtils;

/**
 * 缓存切面实现类
 * 
 * @version V1.0.0, 2015-9-29
 * @author 欧阳增高
 * @since V1.0.0
 */
@Aspect
@Component
public class CacheableAop {
	/**
	 * 初始日志记录实例
	 */
	private static Logger logger = LoggerFactory.getLogger("CacheableAop");

	/**
	 * 注解缓存操作类
	 */
	@Autowired(required = false)
	private RedisTemplate redisTemplate;

	/**
	 * 传入的过期时间值
	 */
	private static ThreadLocal<Integer> THREAD_CACHE_EXPIRE = new ThreadLocal<Integer>();

	/**
	 * 缓存AOP切面方法
	 * 
	 * @param pjp
	 *            切面调用方法对象
	 * @param cache
	 *            缓存注解对象
	 * @return 获取到的缓存值
	 * @throws Throwable
	 * @date 2015-10-10 下午8:29:11
	 */
	@Around("@annotation(cache)")
	public Object cached(final ProceedingJoinPoint pjp, Cacheable cache)
			throws Throwable {
		THREAD_CACHE_EXPIRE.remove();
		if (redisTemplate == null) {
			return pjp.proceed();
		}

		// 获取缓存键名
		String key = getCacheKey(pjp, cache);
		// 如果未设置键名，则不进行缓存操作
		if (StringUtils.isEmpty(key)) {
			return pjp.proceed();
		}
		logger.info("获取到切面缓存名：" + key);

		// 删除缓存数据
		if (cache.handleMode() == CacheHandleMode.delete) {
			this.deleteCaceh(key, cache);
			return pjp.proceed();
		}

		MethodSignature method = (MethodSignature) pjp.getSignature();
		Class<?> clazz = method.getReturnType();

		Object value;
		if (!(cache.handleMode() == CacheHandleMode.append)) {
			// 从缓存获取数据
			value = this.getCache(key, cache);
			if (value != null) {
				return this.castValue(clazz, value);
			}
		}

		// 跳过缓存,到后端查询数据
		value = pjp.proceed();

		// 保存缓存数据
		if (value != null && cache.handleMode() != CacheHandleMode.nil) {
			// 设置缓存值
			Object cacheValue = value;
			if (value instanceof ResponseModel) {
				cacheValue = ((ResponseModel) value).getData();
			}

			// 检查是否需要缓存
			if (cacheValue != null) {
				logger.info("保存切面缓存：" + key);
				this.setCache(key, cacheValue, cache);
			}
		}

		return value;
	}

	/**
	 * 获取缓存的key值
	 * 
	 * @param pjp
	 *            切点对象
	 * @param cache
	 *            注解对象
	 * @return 缓存键
	 */
	private String getCacheKey(ProceedingJoinPoint pjp, Cacheable cache) {
		Annotation[][] pas = ((MethodSignature) pjp.getSignature()).getMethod()
				.getParameterAnnotations();
		StringBuilder buf = new StringBuilder();
		Object[] args = pjp.getArgs();
		Object objVal;
		String val;
		BigDecimal expire;
		List<Object> params = new ArrayList<Object>();
		for (int i = 0; i < pas.length; i++) {
			objVal = args[i];
			if (objVal == null) {
				continue;
			}
			val = objVal.toString();
			val = val.replaceAll(",", "-");
			for (Annotation an : pas[i]) {
				if (an instanceof CacheKey) {
					if (buf.length() > 0) {
						buf.append(":");
					}
					buf.append(val);
					params.add(val);
					break;
				} else if (an instanceof ExpireKey) {// 检查过期时间设置，如果有多个设置则最后一个有效
					try {
						expire = new BigDecimal(val);
						THREAD_CACHE_EXPIRE.set(expire.intValue());
					} catch (Exception e) {
					}
				}
			}
		}

		if (StringUtils.isEmpty(cache.keyName())) {
			return buf.toString();
		}

		if (cache.keyArray()) {
			return StringUtils.format(SystemGlobal.get(cache.keyName()),
					params.toArray(new Object[0]));
		}
		return StringUtils.format(SystemGlobal.get(cache.keyName()),
				buf.toString());

	}

	/**
	 * 获取缓存数据
	 * 
	 * @param key
	 *            需要获取缓存的键名
	 * @param cache
	 *            缓存配置
	 * @return 取到的缓存值
	 * @author 欧阳增高
	 * @date 2015-10-16 下午8:25:33
	 */
	private Object getCache(String key, Cacheable cache) {
		Object value = null;
		if (!redisTemplate.isExist(key)) {
			return value;
		}
		switch (cache.cacheMode()) {
		case LIST:
			value = getCollectionJSON(redisTemplate.lrange(key), cache);
			break;
		case SET:
			value = getCollectionJSON(redisTemplate.sinter(key), cache);
			break;
		case MAP:
			Map<String, String> m = redisTemplate.getMap(key);
			if (m != null && m.size() > 0) {
				value = JSONUtils.parseObject(redisTemplate.getMap(key));
			}
			break;
		case JSON:
			value = JSONUtils.parseObject(redisTemplate.getValue(key));
			break;
		case JSONARRAY:
			value = JSONUtils.parseArray(redisTemplate.getValue(key));
			break;
		default:
			value = redisTemplate.getValue(key);
			break;
		}
		return value;
	}

	/**
	 * 设置缓存值
	 * 
	 * @param key
	 *            缓存键名
	 * @param value
	 *            缓存值
	 * @param cache
	 *            缓存注解配置
	 * @author 欧阳增高
	 * @date 2015-10-16 下午8:49:17
	 */
	private void setCache(String key, Object value, Cacheable cache) {
		// 获取配置文件中的失效时间，如果未设置则为注解中默认时间
		int expire = SystemGlobal.getInteger(cache.keyName() + ".expire");
		// 获取缓存关联的过期时间
		if (expire < 1) {
			expire = cache.expire();
		}
		if (THREAD_CACHE_EXPIRE.get() != null && THREAD_CACHE_EXPIRE.get() > 0) {
			expire = THREAD_CACHE_EXPIRE.get();
		}
		String tmp = null;
		switch (cache.cacheMode()) {
		case LIST:
			List<String> list = this.getCollectionString(value);
			redisTemplate.lpush(key, expire, list.toArray(new String[0]));
			return;
		case SET:
			List<String> vals = this.getCollectionString(value);
			redisTemplate.sadd(key, expire, vals.toArray(new String[0]));
			return;
		case MAP:
			redisTemplate.setObjectMap(key, expire,
					JSONUtils.parse(JSONUtils.toJSONString(value)));
			return;
		case JSON:
			tmp = JSONUtils.toJSONString(value);
			break;
		case JSONARRAY:
			tmp = JSONUtils.toJSONArray(JSONUtils.toJSONString(value))
					.toJSONString();
			break;
		default:
			tmp = value.toString();
			break;
		}
		logger.info("设置切面缓存：" + key + ",有效期：" + expire);
		redisTemplate.setValue(key, tmp, expire);
	}

	/**
	 * 删除缓存数据
	 * 
	 * @param key
	 *            需要删除缓存的键
	 * @param cache
	 * @author 欧阳增高
	 * @date 2015-10-28 下午3:19:58
	 */
	private boolean deleteCaceh(String key, Cacheable cache) {
		Integer expire = THREAD_CACHE_EXPIRE.get();
		if (expire != null && expire > 0) {
			redisTemplate.expire(key, expire);
			return true;
		}
		return redisTemplate.del(key);
	}

	/**
	 * 转换集合对象
	 * 
	 * @param value
	 *            需要转换的原型集合对象
	 * @return List<String>
	 * @author 欧阳增高
	 * @date 2015-10-18 上午10:05:42
	 */
	private List<String> getCollectionString(Object value) {
		JSONArray arr = JSONUtils.parseArray(value);
		// JSONUtils.toJSONArray(JSONUtils.toJSONString(value));
		List<String> valArr = new ArrayList<String>(arr.size());
		for (int i = 0; i < arr.size(); i++) {
			valArr.add(JSONUtils.toJSONString(arr.get(i)));
		}
		return valArr;
	}

	/**
	 * 将集合缓存转换为JSON格式对象
	 * 
	 * @param arr
	 *            需要转换的集合缓存
	 * @return List<Object>
	 * @author 欧阳增高
	 * @date 2015-10-18 下午3:40:29
	 */
	private List<Object> getCollectionJSON(Collection<String> arr,
			Cacheable cache) {
		List<Object> os = new ArrayList<Object>(arr.size());
		JSONObject json;
		Class<?> clazz = cache.returnMode();
		for (String s : arr) {
			try {
				json = JSONUtils.parse(s);
				if (clazz.isAssignableFrom(Boolean.class)) {
					os.add(json);
				} else {
					os.add(JSONUtils.toBean(json, clazz));
				}
			} catch (Exception e) {
				os.add(s);
			}
		}
		return os;
	}

	/**
	 * 缓存值转换
	 * 
	 * @param clazz
	 *            切面方法返回值类型
	 * @param value
	 *            缓存的值
	 * @return Object
	 * @author 欧阳增高
	 * @date 2015-10-16 下午8:26:35
	 */
	@SuppressWarnings("unchecked")
	private Object castValue(Class<?> clazz, Object value) {
		if (value == null) {
			return value;
		}

		// 返回集合对象
		if (List.class.isAssignableFrom(clazz)) {
			return value;
		}

		// 返回集合对象
		if (Set.class.isAssignableFrom(clazz)) {
			Set<Object> ss = new HashSet<Object>();
			ss.addAll((List<Object>) value);
			return value;
		}

		// 返回JSON对象
		if (clazz.isAssignableFrom(ResponseModel.class)) {
			return MsgProperties.getSuccess(value);
		}

		// 字符串
		if (clazz.isAssignableFrom(String.class)) {
			return value.toString();
		}

		// 返回JSON对象
		if (clazz.isAssignableFrom(JSONObject.class)) {
			return JSONUtils.parse(value.toString());
		}

		// 返回值为Object对象
		if (clazz.isAssignableFrom(Object.class)) {
			return value;
		}
		try {
			// 返回实体对象
			return JSONUtils.toBean(JSONUtils.parse(value.toString()), clazz);
		} catch (Exception e) {
			return TypeUtils.cast(value, clazz,
					ParserConfig.getGlobalInstance());
		}
	}
}
