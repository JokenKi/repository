package com.joken.common.utils;

import java.math.BigDecimal;

/**
 * Object工具类
 * 
 * @author wangby
 * @date 2016年5月26日下午4:12:43
 */
public class ObjectUtils {

	private ObjectUtils() {
	}

	/**
	 * 判断两个Object对象是否相等
	 * 
	 * @return Boolean
	 * @date 2016年5月27日上午11:20:45
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Boolean compare2Obj(Object o1, Object o2) {
		if (o1 == null || o2 == null) {
			return false;
		}

		if (o1.getClass() != o2.getClass()) {
			return false;
		}

		if (o1 instanceof Comparable) {
			return ((Comparable) o1).compareTo(((Comparable) o2)) == 0 ? true
					: false;
		}

		return o1.toString().equals(o2.toString());
	}

	/**
	 * 
	 * 两数相加
	 * 
	 * @return Object
	 * @date 2016年5月26日下午4:13:59
	 */
	public static Object add(Object o1, Object o2) {
		if (o1 == null) {
			return null;
		}

		if (o2 == null) {
			return o1;
		}

		if (o1.getClass() != o2.getClass()) {
			return null;
		}
		Class<? extends Object> clazz = o1.getClass();
		switch (clazz.getName()) {
		case "java.lang.Integer":
			return ((Integer) o1 + (Integer) o2);
		case "java.lang.Long":
			return ((Long) o1 + (Long) o2);
		case "java.math.BigDecimal":
			return (BigDecimal.valueOf(((BigDecimal) o1).doubleValue()
					+ ((BigDecimal) o2).doubleValue()));
		case "java.lang.Byte":
			return ((Byte) o1 + (Byte) o2);
		case "java.lang.Double":
			return ((Double) o1 + (Double) o2);
		case "java.lang.Float":
			return ((Float) o1 + (Float) o2);
		default:
			return null;
		}
	}

	/**
	 * 
	 * 两数相除,o1/o2
	 * 
	 * @return Object
	 * @date 2016年5月26日下午4:13:59
	 */
	public static Object divide(Object o1, Object o2) {
		if (o1 == null) {
			return null;
		}

		if (o2 == null) {
			return o1;
		}
		if (o1.getClass() != o2.getClass()) {
			return null;
		}
		Class<? extends Object> clazz = o1.getClass();
		switch (clazz.getName()) {
		case "java.lang.Integer":
			return ((Integer) o1 / (Integer) o2);
		case "java.lang.Long":
			return ((Long) o1 / (Long) o2);
		case "java.math.BigDecimal":
			return (BigDecimal.valueOf(((BigDecimal) o1).doubleValue()
					/ ((BigDecimal) o2).doubleValue()));
		case "java.lang.Byte":
			return ((Byte) o1 / (Byte) o2);
		case "java.lang.Double":
			return ((Double) o1 / (Double) o2);
		case "java.lang.Float":
			return ((Float) o1 / (Float) o2);
		default:
			return null;
		}
	}

	/**
	 * 
	 * 两数相乘
	 * 
	 * @return Object
	 * @date 2016年5月26日下午4:13:59
	 */
	public static Object multiply(Object o1, Object o2) {
		if (o1 == null) {
			return null;
		}

		if (o2 == null) {
			return o1;
		}
		if (o1.getClass() != o2.getClass()) {
			return null;
		}
		Class<? extends Object> clazz = o1.getClass();
		switch (clazz.getName()) {
		case "java.lang.Integer":
			return ((Integer) o1 * (Integer) o2);
		case "java.lang.Long":
			return ((Long) o1 * (Long) o2);
		case "java.math.BigDecimal":
			return (BigDecimal.valueOf(((BigDecimal) o1).doubleValue()
					* ((BigDecimal) o2).doubleValue()));
		case "java.lang.Byte":
			return ((Byte) o1 * (Byte) o2);
		case "java.lang.Double":
			return ((Double) o1 * (Double) o2);
		case "java.lang.Float":
			return ((Float) o1 * (Float) o2);
		default:
			return null;
		}
	}

	/**
	 * 
	 * 两数相减
	 * 
	 * @return Object
	 * @date 2016年5月26日下午4:13:59
	 */
	public static Object subtract(Object o1, Object o2) {
		if (o1 == null) {
			return null;
		}

		if (o2 == null) {
			return o1;
		}
		if (o1.getClass() != o2.getClass()) {
			return null;
		}
		Class<? extends Object> clazz = o1.getClass();
		switch (clazz.getName()) {
		case "java.lang.Integer":
			return ((Integer) o1 - (Integer) o2);
		case "java.lang.Long":
			return ((Long) o1 - (Long) o2);
		case "java.math.BigDecimal":
			return (BigDecimal.valueOf(((BigDecimal) o1).doubleValue()
					+ ((BigDecimal) o2).doubleValue()));
		case "java.lang.Byte":
			return ((Byte) o1 - (Byte) o2);
		case "java.lang.Double":
			return ((Double) o1 - (Double) o2);
		case "java.lang.Float":
			return ((Float) o1 - (Float) o2);
		default:
			return null;
		}
	}
}
