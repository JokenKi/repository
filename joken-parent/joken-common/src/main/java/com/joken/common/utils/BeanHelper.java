/**
 * 
 */
package com.joken.common.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;

/***
 * 类描述： bean操作工具类
 * 
 * @author 欧阳增高
 * @date 下午04:41:27
 * @since 1.0
 */
public class BeanHelper extends BeanUtils {
	private static BeanUtilsBean beanUtils = null;

	/**
	 * 静态块
	 */
	static {
		ConverterUtils convertUtils = new ConverterUtils();
		convertUtils.register(new DateConverter(), Date.class);
		beanUtils = new BeanUtilsBean(convertUtils, new PropertyUtilsBean());
	}

	/**
	 * 将Map数据转换成实体对象
	 * 
	 * @param bean
	 *            实体对象实例
	 * @param map
	 *            map数据对象
	 */
	public static void mapToBean(Object bean, Map<String, Object> map) {
		if (map == null || bean == null) {
			return;
		}
		try {
			beanUtils.populate(bean, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将Bean实体对象数据转换成Map对象
	 * 
	 * @param bean
	 *            实体对象实例
	 * @return 转换的Map对象
	 */
	public static Map<String, Object> beanToMap(Object bean) {
		if (bean == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				// 过滤class属性
				if (!key.equals("class")) {
					String simpleName = property.getPropertyType()
							.getSimpleName();
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(bean);
					if (value == null) {
						continue;
					}
					if (simpleName.equals("Date")) {
						String str = DateUtils.CHN_DATE_TIME_EXTENDED_FORMAT
								.format(value);
						if (str.length() > 19) {
							str = str.substring(0, 19);
						}
						value = str.replace(" 00:00:00", "");
					}
					map.put(key, value);
				}

			}
		} catch (Exception e) {
		}
		return map;
	}

	/**
	 * 通过BeanUtilsBean工具设置bean实体指定属性的值
	 * 
	 * @param bean
	 *            bean实体对象
	 * @param propertyName
	 *            属性名
	 * @param propertyValue
	 *            属性值
	 * 
	 * @author inkcar
	 * @date Jul 28, 2011
	 */
	// public static void setProperty(Object bean, String propertyName,
	// Object propertyValue) {
	// try {
	// super.setProperty(bean, propertyName, propertyValue);
	// } catch (Exception e) {
	// }
	// }

	/**
	 * 获取Bean指定属性值
	 * 
	 * @param bean
	 *            实体对象
	 * @param propertyName
	 *            属性名称
	 * @return
	 */
	// public static String getProperty(Object bean, String propertyName) {
	// try {
	// return getProperty(bean, propertyName);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// return null;
	// }

	/**
	 * 属性复制
	 * 
	 * @param dest
	 *            需要赋值的对象
	 * @param orig
	 *            属性值来源
	 */
	// public static void copyProperties(Object dest, Object orig) {
	// try {
	// copyProperties(dest, orig);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
}
