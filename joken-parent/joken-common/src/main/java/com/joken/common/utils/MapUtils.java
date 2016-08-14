package com.joken.common.utils;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtilsBean;

public class MapUtils {
	private MapUtils() {
	}

	/**
	 * 获取map对象的长度
	 * 
	 * @param map
	 *            需要获取的map对象
	 * @return int
	 */
	public static <K, V> int getSize(Map<K, V> map) {
		if (map == null) {
			return 0;
		}
		return map.size();
	}

	/**
	 * 转换request请求中的parameterMap为普通map
	 * 
	 * @param parameterMap
	 *            request.getParameterMap()值
	 * @return Map<String, Object>
	 */
	public static Map<String, Object> convertRequestMap(
			Map<String, String[]> parameterMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (parameterMap != null) {
			String key;
			for (Iterator<String> it = parameterMap.keySet().iterator(); it
					.hasNext();) {
				key = it.next();
				map.put(key, parameterMap.get(key)[0]);
			}
		}
		return map;
	}

	/**
	 * 通过指定值初始化Map对象并返回
	 * 
	 * @param key
	 *            初始化键名
	 * @param value
	 *            初始化值
	 * @return Map<String, Object>
	 */
	public static Map<String, Object> initMap(String key, Object value) {
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put(key, value);
		return map;
	}

	/**
	 * 将url参数转换成map
	 * 
	 * @param param
	 *            url参数，如:aa=11&bb=22&cc=33
	 * @return Map<String, String>
	 */
	public static Map<String, String> getUrlParams(String param) {
		Map<String, String> map = new HashMap<String, String>();
		if (StringUtils.isEmpty(param)) {
			return map;
		}
		String[] params = param.split("&");
		String val;
		int index = 0;
		for (int i = 0; i < params.length; i++) {
			val = params[i];
			index = val.indexOf("=");
			map.put(val.substring(0, index), val.substring(index + 1));
		}
		return map;
	}

	/**
	 * 将Map键值转换为URL字符串
	 * 
	 * @param params
	 *            需要转换的Map对象
	 * @return String
	 */
	public static String mapToUrlParams(Map<String, Object> params) {
		if (params == null || params.size() == 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		String key;
		for (Iterator<String> it = params.keySet().iterator(); it.hasNext();) {
			key = it.next();
			if (sb.length() > 0) {
				sb.append("&");
			}
			sb.append(key).append("=").append(params.get(key));
		}
		return sb.toString();
	}

	/**
	 * 获取Map中是否存在指定key,如Map为null时，为不存在
	 * 
	 * @param params
	 *            需要检测的Map对象
	 * @param key
	 *            需要检查的key
	 * @return boolean
	 */
	public static <K, V> boolean isExists(Map<K, V> params, String key) {
		if (getSize(params) == 0) {
			return false;
		}
		return params.containsKey(key);
	}

	/**
	 * bean转map工具类
	 * @Auther Hanzibin
	 * @date 2:39:19 PM,Jun 15, 2016
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> beanToMap(Object obj) {
		Map<String, Object> params = new HashMap<String, Object>(0);
		try {
			PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
			PropertyDescriptor[] descriptors = propertyUtilsBean
					.getPropertyDescriptors(obj);
			for (int i = 0; i < descriptors.length; i++) {
				String name = descriptors[i].getName();
				if (!"class".equals(name)) {
					params.put(name,
							propertyUtilsBean.getNestedProperty(obj, name));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return params;
	}
}
