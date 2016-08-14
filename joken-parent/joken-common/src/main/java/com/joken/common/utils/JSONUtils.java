package com.joken.common.utils;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * JSON工具类
 * 
 * @author inkcar
 * @version 0.0.0.1 下午06:25:21
 */
public final class JSONUtils {
	/**
	 * 私有构造器
	 */
	private JSONUtils() {
	}

	/**
	 * 转换特性定义
	 */
	// private static SerializerFeature[] FEATURES;
	private static ParserConfig CONFIG = ParserConfig.getGlobalInstance();

	static {
		CONFIG.putDeserializer(Date.class, DateConverter.instance);
		// FEATURES = new SerializerFeature[] {
		// 初始化
		// SerializerFeature.WriteMapNullValue, // 输出空置字段
		// SerializerFeature.WriteNullListAsEmpty, //
		// list字段如果为null，输出为[]，而不是null
		// SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
		// SerializerFeature.WriteNullBooleanAsFalse, //
		// Boolean字段如果为null，输出为false，而不是null
		// SerializerFeature.WriteNullStringAsEmpty, //
		// 字符类型字段如果为null，输出为""，而不是null
		// SerializerFeature.WriteDateUseDateFormat };

	}

	/**
	 * 将对象转换成JSON字符串
	 * 
	 * @param object
	 *            需要转换的对象
	 * @return String
	 */
	public static String toJSONString(Object object) {
		return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss",
				new SerializerFeature[0]);
	}

	/**
	 * 将字符串对象转换成JSON对象
	 * 
	 * @param obj
	 *            需要转换为JSON对象的JAVA Bean
	 * @return JSONObject
	 */
	public static JSONObject parseObject(Object obj) {
		return (JSONObject) JSON.toJSON(obj);
	}

	/**
	 * 将字符串对象转换成JSON对象
	 * 
	 * @param text
	 *            需要转换为JSON的字符串
	 * @return JSONObject
	 */
	public static JSONObject parse(String text) {
		return toJSON(text);
	}

	/**
	 * 将字符串对象转换成JSON对象
	 * 
	 * @param text
	 *            需要转换为JSON的字符串
	 * @return JSONObject
	 */
	public static JSONObject toJSON(String text) {
		return JSON.parseObject(text, Feature.values());
	}

	/**
	 * 将字符串对象转换成JSON对象
	 * 
	 * @param obj
	 *            需要转换为JSON对象的JAVA Bean
	 * @return JSONObject
	 */
	public static JSONArray parseArray(Object obj) {
		return (JSONArray) JSON.toJSON(obj);
	}

	/**
	 * 将字符串对象转换成JSON数组对象
	 * 
	 * @param text
	 *            需要转换为JSON的字符串
	 * @return String
	 */
	public static JSONArray toJSONArray(String text) {
		return JSON.parseArray(text);
	}

	/**
	 * 将JSON对象转为实体Bean
	 * 
	 * @param json
	 *            json对象
	 * @param clazz
	 *            需要转换的class
	 * @return class实例
	 */
	public static Object toBeanByObject(Object json, Class<?> clazz) {
		return JSON.toJavaObject(parseObject(json), clazz);
	}

	/**
	 * 将JSON对象转为实体Bean
	 * 
	 * @param json
	 *            json对象
	 * @param clazz
	 *            需要转换的class
	 * @return class实例
	 */
	public static Object toBean(JSONObject json, Class<?> clazz) {
		return JSON.toJavaObject(json, clazz);
	}

	/**
	 * 将指定JSONArray字符串格式转为指定javaObject实体对象
	 * 
	 * @param arr
	 *            需要转换的JSONArray对象
	 * @param clazz
	 *            javaObject class对象
	 * @return List<?>
	 */
	public static List<?> parseArray(JSONArray arr, Class<?> clazz) {
		return parseArray(arr.toJSONString(), clazz);
	}

	/**
	 * 将指定JSONArray字符串格式转为指定javaObject实体对象
	 * 
	 * @param text
	 *            需要转换的JSONArray字符串对象
	 * @param clazz
	 *            javaObject class对象
	 * @return List<?>
	 */
	public static List<?> parseArray(String text, Class<?> clazz) {
		List<?> list = JSON.parseArray(text, clazz);
		return list == null ? Collections.emptyList() : list;
	}
}
