package com.joken.ice.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.UrlEncoded;

import Ice.Current;

import com.alibaba.fastjson.JSONObject;
import com.joken.common.model.BaseModel;
import com.joken.common.rpc.NullValueException;
import com.joken.common.rpc.SortModel;
import com.joken.common.utils.DateUtils;
import com.joken.common.utils.JSONUtils;
import com.joken.common.utils.ReflectUtils;
import com.joken.common.utils.StringUtils;

/**
 * ice实体工具类
 * 
 * @author wangby
 * @date 2016年4月13日 上午10:50:15
 */
public class IceBeanUtils {

	private IceBeanUtils() {
	}

	private final static String READ_PREFFIX = "get";
	private final static String WRITE_PREFFIX = "set";
	private final static String PROPERTY_PREFFIX_REG = "^[a-z].*";
	private final static String TIME_FIELD_SUFFIX_RGE = ".*Time$";
	private final static String UPPER_CASE_SPLIT_REG = "(?=[A-Z])";
	private final static String UPPER_CASE_REG = "([A-Z])";
	public final static String QUERY_KEY = "_queryParams";

	/**
	 * 获取指定的enum对象数组的 soaModel
	 * 
	 * @param iceModel
	 *            ice定义的Model
	 * @param soaModel
	 *            spring项目中的Model 属性类型都是包装类
	 * @param eums
	 *            ice自动生成的eum对象数组
	 * @return void
	 * @date 2016年6月24日上午11:21:15
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object getTargetModle(Object iceModel, Class soaClass,
			Object[] eums) {

		if (iceModel == null || soaClass == null || eums == null
				|| eums.length < 0) {
			return null;
		}
		Object soaModel = null;
		try {
			soaModel = soaClass.newInstance();
			Class clazz = eums[0].getClass();
			Method method = clazz.getDeclaredMethod("name");
			String propertyName = null;
			for (Object object : eums) {
				propertyName = method.invoke(object).toString();
				if (propertyName == null) {
					continue;
				}
				setPropertyValue(iceModel, soaModel, propertyName);
			}
		} catch (Exception e) {
		}
		return soaModel;
	}

	/**
	 * 设置soaModel属性值
	 * 
	 * @param iceModel
	 *            ice定义的Model
	 * @param soaModel
	 *            spring项目中的Model 属性类型都是包装类
	 * @param propertyName
	 *            属性名
	 * @return void
	 * @date 2016年6月24日上午11:21:15
	 */
	public static void setPropertyValue(Object iceModel, Object soaModel,
			String propertyName) {

		if (iceModel == null || soaModel == null
				|| StringUtils.isEmpty(propertyName)) {
			return;
		}
		Object o = null;
		try {
			o = ReflectUtils.getFieldValue(propertyName, iceModel);
		} catch (RuntimeException e) {
		}

		if (o == null) {
			return;
		}
		if (o instanceof Double) {
			o = BigDecimal.valueOf((Double) o);
			try {
				ReflectUtils.setFieldValue(soaModel, propertyName, o);
			} catch (RuntimeException e) {
			}
			return;
		}
		if (o instanceof Long) {
			o = BigDecimal.valueOf((Long) o);
			try {
				ReflectUtils.setFieldValue(soaModel, propertyName, o);
			} catch (RuntimeException e) {
			}
			return;
		}
		if (o instanceof Float) {
			o = BigDecimal.valueOf((Float) o);
			try {
				ReflectUtils.setFieldValue(soaModel, propertyName, o);
			} catch (RuntimeException e) {
			}
			return;
		}
		try {
			ReflectUtils.setFieldValue(soaModel, propertyName, o);
		} catch (RuntimeException e) {
		}

	}

	/**
	 * 将参数params中的值 赋给soaModel中的params
	 * 
	 * @param params
	 *            Map类型 key为:_queryParams
	 *            值如:between_times_start=1&between_times_end
	 *            =10&gt_dicimal=10.26&
	 *            in_times=1&in_times=2&in_times=10&in_times=20
	 * @param soaModel
	 *            继承BaseModel
	 * @return 将params参数赋值的soaModel
	 * @author wangby
	 */
	public static Object generateQueryParams(Map<String, String> params,
			Object soaModel) {
		if (params == null || params.size() < 1) {
			return soaModel;
		}

		if (soaModel == null) {
			return null;
		}

		if (!(soaModel instanceof BaseModel)) {
			return soaModel;
		}
		BaseModel base = (BaseModel) soaModel;
		if (base.getParams() == null) {
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			base.setParams(hashMap);
		}
		if (!params.containsKey(QUERY_KEY)) {
			base.getParams().put("sort", getSortString(params));
			return soaModel;
		}
		MultiMap<String> multiMap = new MultiMap<String>();
		UrlEncoded.decodeTo(params.get(QUERY_KEY), multiMap, "UTF-8", 1000);

		Class<? extends Object> clazz = soaModel.getClass();
		Set<String> keySet = multiMap.keySet();
		String[] split = null;
		String fieldName = null;
		Field field = null;
		Method method = null;
		String value = null;
		Object o = null;
		List<String> list = null;
		List<Object> paramList = null;
		for (String key : keySet) {
			try {
				split = key.split("_");
				if (split == null || split.length < 2) {
					continue;
				}
				fieldName = split[1];
				field = clazz.getDeclaredField(fieldName);
				if (field == null) {
					continue;
				}
				list = multiMap.get(key);
				if (list.size() > 1 || key.indexOf("in") != -1) {
					paramList = new ArrayList<Object>();
					for (String string : multiMap.get(key)) {
						if (field.getName().matches(TIME_FIELD_SUFFIX_RGE)
								&& DateUtils.checkDateTimeStringLength(string)
								&& DateUtils.DATE_TIME_CHECK_PATTERN.matcher(
										string).matches()) {
							paramList.add(DateUtils.dateString2Date(string));
							continue;
						}
						if (field.getType() == BigDecimal.class) {
							paramList.add(BigDecimal.class.getConstructor(
									String.class).newInstance(string));
							continue;
						}
						if (field.getType() == String.class) {
							paramList.add(string);
							continue;
						}

						method = field.getType().getMethod("valueOf",
								String.class);
						if (method == null) {
							continue;
						}
						o = method.invoke(null, string);
						paramList.add(o);
					}
					base.getParams().put(key, paramList);
					continue;
				}
				value = list.get(0);
				if (field.getName().matches(TIME_FIELD_SUFFIX_RGE)
						&& DateUtils.checkDateTimeStringLength(value)
						&& DateUtils.DATE_TIME_CHECK_PATTERN.matcher(value)
								.matches()) {
					base.getParams().put(key, DateUtils.dateString2Date(value));
					continue;
				}
				if (field.getType() == BigDecimal.class) {
					o = BigDecimal.class.getConstructor(String.class)
							.newInstance(value);
					base.getParams().put(key, o);
					continue;
				}
				if (field.getType() == String.class) {
					base.getParams().put(key, o);
					continue;
				}
				method = field.getType().getMethod("valueOf", String.class);
				if (method == null) {
					continue;
				}
				o = method.invoke(null, value);
				base.getParams().put(key, o);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		base.getParams().put("sort", getSortString(params));
		return soaModel;
	}

	/**
	 * 将属性是基本类型的model对象 赋值给属性是包装类的model对象 其中会过滤掉基本类型的model对象的默认值
	 * 如java.lang.string默认为空串,int默认为0,支持list属性转换.同时将名称以Time结尾,格式为yyyy-mm-dd
	 * hh:mm:ss 或者 yyyy-mm-dd 或者hh:mm:ss的String字符串转为Data类型
	 * 
	 * @param iceModel
	 *            slice2Java 自动生成的model对象,属性是基本类型
	 * @param soaModel
	 *            spring的model对象,属性是包装类
	 * @param ifDefalutValue
	 *            是否过滤默认值
	 * @author wangby
	 * @date 2016年7月6日 上午10:53:26
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object iceModel2SoaModelWithList(Object iceModel,
			Class<? extends Object> clazz, Boolean ifDefalutValue) {
		if (iceModel == null) {
			return null;
		}
		Object instance = null;
		try {
			instance = clazz.newInstance();
		} catch (Exception e1) {
		}
		if (instance == null) {
			return null;
		}
		Class<? extends Object> iceClazz = iceModel.getClass();
		Field[] declaredFields = iceClazz.getDeclaredFields();
		String fieldName = null;
		String fisrt = null;
		Object o = null;
		Class<?> returnType = null;
		Class<?> soaType = null;
		if (iceModel.getClass() == ArrayList.class) {
			List list = (List) iceModel;
			if (list.size() < 1) {
				return null;
			}
			instance = new ArrayList();
			for (Object object : list) {
				((List) instance).add(iceModel2SoaModelWithList(object, clazz,
						ifDefalutValue));
			}
			return instance;
		}
		for (Field field : declaredFields) {
			try {
				fieldName = field.getName();
				if (!fieldName.matches(PROPERTY_PREFFIX_REG)) {
					continue;
				}
				fisrt = fieldName.substring(0, 1);
				fieldName = fieldName.replaceFirst(fisrt, fisrt.toUpperCase());
				Method method = iceClazz.getMethod(READ_PREFFIX + fieldName);
				returnType = method.getReturnType();
				if (returnType.isArray()) {
					continue;
				}
				o = method.invoke(iceModel);
				if (o == null) {
					continue;
				}
				switch (returnType.getName()) {
				case "java.lang.String":
					if (ifDefalutValue && o.equals("")) {
						continue;
					}
					soaType = String.class;
					if (!fieldName.matches(TIME_FIELD_SUFFIX_RGE)
							&& !DateUtils.checkDateTimeStringLength(o
									.toString())) {
						break;
					}
					if (DateUtils.DATE_TIME_CHECK_PATTERN.matcher(o.toString())
							.matches()) {
						method = clazz.getMethod(WRITE_PREFFIX + fieldName,
								Date.class);
						method.invoke(instance,
								DateUtils.dateString2Date(o.toString()));
						continue;
					}
					break;
				case "short":
					if (ifDefalutValue && o.toString().equals("0")) {
						continue;
					}
					soaType = Short.class;
					break;
				case "int":
					if (ifDefalutValue && o.toString().equals("0")) {
						continue;
					}
					soaType = Integer.class;
					break;
				case "long":
					if (ifDefalutValue && o.toString().equals("0")) {
						continue;
					}
					soaType = Long.class;
					break;
				case "double":
					if (ifDefalutValue && o.toString().equals("0.0")) {
						continue;
					}
					method = clazz.getMethod(WRITE_PREFFIX + fieldName,
							BigDecimal.class);
					method.invoke(instance, BigDecimal.valueOf((double) o));
					continue;
				case "float":
					if (ifDefalutValue && o.toString().equals("0.0")) {
						continue;
					}
					soaType = Float.class;
					break;
				default:
					method = clazz.getMethod(READ_PREFFIX + fieldName);
					soaType = method.getReturnType();
					Class<?> type = null;
					if (field.getType().isAssignableFrom(List.class)) {
						field = clazz.getDeclaredField(field.getName());
						Type fc = field.getGenericType();
						if (fc == null)
							continue;
						if (fc instanceof ParameterizedType) {
							ParameterizedType pt = (ParameterizedType) fc;
							type = (Class) pt.getActualTypeArguments()[0];

						}
					}
					o = iceModel2SoaModelWithList(o, type, ifDefalutValue);
					break;
				}
				method = clazz.getMethod(WRITE_PREFFIX + fieldName, soaType);
				method.invoke(instance, o);
			} catch (Exception e) {
			}
		}

		return instance;
	}

	/**
	 * 将属性是基本类型的model对象 赋值给属性是包装类的model对象 其中会过滤掉基本类型的model对象的默认值
	 * 如java.lang.string默认为空串,int默认为0,不支持list属性转换
	 * 
	 * @param iceModel
	 *            slice2Java 自动生成的model对象,属性是基本类型
	 * @param soaModel
	 *            spring的model对象,属性是包装类
	 * @param ifDefalutValue
	 *            是否过滤默认值
	 * @author wangby
	 * @date 2016年4月13日 上午10:53:26
	 */
	public static Object iceModel2SoaModel(Object iceModel,
			Class<? extends Object> clazz, Boolean ifDefalutValue) {
		if (iceModel == null) {
			return null;
		}
		Object instance = null;
		try {
			instance = clazz.newInstance();
		} catch (Exception e) {
		}
		if (instance == null) {
			return null;
		}
		Class<? extends Object> iceClazz = iceModel.getClass();
		Field[] declaredFields = iceClazz.getDeclaredFields();
		String fieldName = null;
		String fisrt = null;
		Object o = null;
		Class<?> returnType = null;
		Class<?> soaType = null;
		for (Field field : declaredFields) {
			try {
				fieldName = field.getName();
				if (!fieldName.matches(PROPERTY_PREFFIX_REG)) {
					continue;
				}
				fisrt = fieldName.substring(0, 1);
				fieldName = fieldName.replaceFirst(fisrt, fisrt.toUpperCase());
				Method method = iceClazz.getMethod(READ_PREFFIX + fieldName);
				returnType = method.getReturnType();
				if (returnType.isArray()) {
					continue;
				}
				o = method.invoke(iceModel);
				if (o == null) {
					continue;
				}
				switch (returnType.getName()) {
				case "java.lang.String":
					if (ifDefalutValue && o.equals("")) {
						continue;
					}
					soaType = String.class;
					if (!fieldName.matches(TIME_FIELD_SUFFIX_RGE)
							&& !DateUtils.checkDateTimeStringLength(o
									.toString())) {
						break;
					}
					if (DateUtils.DATE_TIME_CHECK_PATTERN.matcher(o.toString())
							.matches()) {
						method = clazz.getMethod(WRITE_PREFFIX + fieldName,
								Date.class);
						method.invoke(instance,
								DateUtils.dateString2Date(o.toString()));
						continue;
					}
					break;
				case "short":
					if (ifDefalutValue && o.toString().equals("0")) {
						continue;
					}
					soaType = Short.class;
					break;
				case "int":
					if (ifDefalutValue && o.toString().equals("0")) {
						continue;
					}
					soaType = Integer.class;
					break;
				case "long":
					if (ifDefalutValue && o.toString().equals("0")) {
						continue;
					}
					soaType = Long.class;
					break;
				case "double":
					if (ifDefalutValue && o.toString().equals("0.0")) {
						continue;
					}
					method = clazz.getMethod(WRITE_PREFFIX + fieldName,
							BigDecimal.class);
					method.invoke(instance, BigDecimal.valueOf((double) o));
					continue;
				case "float":
					if (ifDefalutValue && o.toString().equals("0.0")) {
						continue;
					}
					soaType = Float.class;
					break;

				default:
					method = clazz.getMethod(READ_PREFFIX + fieldName);
					soaType = method.getReturnType();
					o = iceModel2SoaModel(o, soaType, ifDefalutValue);
					break;
				}
				method = clazz.getMethod(WRITE_PREFFIX + fieldName, soaType);
				method.invoke(instance, o);
			} catch (Exception e) {
			}
		}
		return instance;
	}

	/**
	 * 根据soa定义的model对象slice2Java 自动生成的model对象 返回 其中会过滤掉soa定义的model对象的null值
	 * sequence对象为数组类型
	 * 
	 * @param clazz
	 *            slice2Java 自动生成的model的Class对象
	 * @param SoaModel
	 *            soa定义的model对象
	 * @author wangby
	 * @date 2016年4月13日 上午10:53:26
	 */
	public static Object soaModel2IceModel(Object soaModel,
			Class<? extends Object> clazz) {
		if (soaModel == null) {
			return null;
		}

		Object instance = null;
		try {
			instance = (clazz == List.class ? ArrayList.class : clazz)
					.newInstance();
		} catch (Exception e) {
		}
		if (instance == null) {
			return null;
		}
		Class<? extends Object> soaClazz = soaModel.getClass();
		Field[] declaredFields = soaClazz.getDeclaredFields();
		String fieldName = null;
		String fisrt = null;
		Object o = null;
		Class<? extends Object> soaType = null;
		for (Field field : declaredFields) {
			try {
				fieldName = field.getName();
				if (!fieldName.matches(PROPERTY_PREFFIX_REG)) {
					continue;
				}
				fisrt = fieldName.substring(0, 1);
				fieldName = fieldName.replaceFirst(fisrt, fisrt.toUpperCase());
				Method method = soaClazz.getMethod(READ_PREFFIX + fieldName);
				o = method.invoke(soaModel);
				if (o == null) {
					continue;
				}
				switch (field.getType().getName()) {
				case "java.lang.String":
					soaType = String.class;
					break;
				case "java.lang.Short":
					soaType = short.class;
					break;
				case "java.lang.Integer":
					soaType = int.class;
					break;
				case "java.lang.Long":
					soaType = long.class;
					break;
				case "java.lang.Double":
					soaType = double.class;
					break;
				case "java.lang.Float":
					soaType = float.class;
					break;
				case "java.util.Date":
					soaType = String.class;
					o = DateUtils.formateDate((Date) o);
					break;
				case "java.math.BigDecimal":
					soaType = double.class;
					method = clazz
							.getMethod(WRITE_PREFFIX + fieldName, soaType);
					method.invoke(instance, ((BigDecimal) o).doubleValue());
					continue;
				default:
					if (field.getType() == List.class) {
						field = clazz.getField(field.getName());
						o = getArrayObject(o, field.getType());
						if (o == null) {
							break;
						}
						soaType = o.getClass();
						break;
					}
					field = clazz.getField(field.getName());
					o = soaModel2IceModel(o, field.getType());
					soaType = o.getClass();
				}
				method = clazz.getMethod(WRITE_PREFFIX + fieldName, soaType);
				method.invoke(instance, o);
			} catch (Exception e) {
			}
		}

		return instance;
	}

	/**
	 * 根据soa定义的model对象slice2Java 自动生成的model对象 返回 其中会过滤掉soa定义的model对象的null值
	 * sequence对象为java.util.List类型
	 * 
	 * @param clazz
	 *            slice2Java 自动生成的model的Class对象
	 * @param SoaModel
	 *            soa定义的model对象
	 * @author wangby
	 * @date 2016年4月13日 上午10:53:26
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object soaModel2IceModelWithList(Object soaModel,
			Class<? extends Object> clazz,
			Class<? extends Object> innerListclazz) {
		if (soaModel == null) {
			return null;
		}
		Object instance = null;
		try {
			instance = (clazz == List.class ? ArrayList.class : clazz)
					.newInstance();
		} catch (Exception e) {
		}
		if (instance == null) {
			return null;
		}
		if (clazz.isAssignableFrom(List.class)) {
			List list = (List) soaModel;
			if (list.size() < 1) {
				return null;
			}
			for (Object object : list) {
				((List) instance).add(soaModel2IceModelWithList(object,
						innerListclazz, null));
			}
			return instance;
		}
		Class<? extends Object> soaClazz = soaModel.getClass();
		Field[] declaredFields = soaClazz.getDeclaredFields();
		String fieldName = null;
		String fisrt = null;
		Object o = null;
		Class<? extends Object> soaType = null;
		for (Field field : declaredFields) {
			try {
				fieldName = field.getName();
				if (!fieldName.matches(PROPERTY_PREFFIX_REG)) {
					continue;
				}
				fisrt = fieldName.substring(0, 1);
				fieldName = fieldName.replaceFirst(fisrt, fisrt.toUpperCase());
				Method method = soaClazz.getMethod(READ_PREFFIX + fieldName);
				o = method.invoke(soaModel);
				if (o == null) {
					continue;
				}
				switch (field.getType().getName()) {
				case "java.lang.String":
					soaType = String.class;
					break;
				case "java.lang.Short":
					soaType = short.class;
					break;
				case "java.lang.Integer":
					soaType = int.class;
					break;
				case "java.lang.Long":
					soaType = long.class;
					break;
				case "java.lang.Double":
					soaType = double.class;
					break;
				case "java.lang.Float":
					soaType = float.class;
					break;
				case "java.util.Date":
					soaType = String.class;
					o = DateUtils.formateDate((Date) o);
					break;
				case "java.math.BigDecimal":
					soaType = double.class;
					method = clazz
							.getMethod(WRITE_PREFFIX + fieldName, soaType);
					method.invoke(instance, ((BigDecimal) o).doubleValue());
					continue;
				default:
					field = clazz.getField(field.getName());
					if (field.getType().isAssignableFrom(List.class)) {
						soaType = List.class;
						o = soaModel2IceModelWithList(o, soaType,
								ReflectUtils.getListInnerClass(field));
						break;
					}
					o = soaModel2IceModelWithList(o, field.getType(), null);
					soaType = o.getClass();
				}
				method = clazz.getMethod(WRITE_PREFFIX + fieldName, soaType);
				method.invoke(instance, o);
			} catch (Exception e) {
			}
		}
		return instance;
	}

	/**
	 * 
	 * 获取数组对象
	 * 
	 * @param o
	 *            soaModel 实现Collection接口
	 * @param targetClass
	 *            目标数组class
	 * @return 数组对象
	 * @author wangby
	 * @date 2016年4月21日 下午5:14:09
	 */
	@SuppressWarnings("unchecked")
	public static Object getArrayObject(Object o,
			Class<? extends Object> targetClass)
			throws NegativeArraySizeException, ClassNotFoundException {
		if (!targetClass.isArray()) {
			return null;
		}
		Collection<? extends Object> c = (Collection<? extends Object>) o;
		Object[] oArr = (Object[]) Array.newInstance(
				targetClass.getComponentType(), c.size());
		int temp = 0;
		Iterator<? extends Object> iterator = c.iterator();
		while (iterator.hasNext()) {
			o = iterator.next();
			Array.set(oArr, temp,
					soaModel2IceModel(o, targetClass.getComponentType()));
			temp++;
		}
		return oArr;
	}

	/**
	 * 获取排序字符串
	 * 
	 * @param sort
	 *            通用排序对象
	 * @return 排序字符串
	 * @author wangby
	 * @date 2016年4月26日 上午9:43:17
	 */
	public static String getSortString(SortModel sort) {
		if (sort == null || sort.getSortMap() == null
				|| sort.getSortMap().size() < 1) {
			return null;
		}

		return getSortString(sort.getSortMap());
	}

	public static String getSortString(Map<String, String> _sortMap) {
		if (_sortMap == null || _sortMap.size() < 1) {
			return null;
		}
		_sortMap.remove(QUERY_KEY);
		Set<String> keySet = _sortMap.keySet();
		Iterator<String> iterator = keySet.iterator();
		String key = null;
		String value = null;
		String[] split = null;
		StringBuffer sBuffer = new StringBuffer();
		String first = null;
		String tmp = null;
		while (iterator.hasNext()) {
			key = iterator.next();
			if (!key.matches(PROPERTY_PREFFIX_REG)) {
				continue;
			}
			value = _sortMap.get(key);
			split = key.split(UPPER_CASE_SPLIT_REG);
			for (int i = 0; i < split.length; i++) {
				tmp = split[i];
				if (tmp.length() < 2) {
					sBuffer.append(tmp);
					continue;
				}
				first = tmp.substring(0, 1);
				if (!first.matches(UPPER_CASE_REG)) {
					sBuffer.append(tmp);
					if (i < split.length - 1) {
						continue;
					}
					sBuffer.append(" ").append(value);
					continue;
				}
				sBuffer.append("_").append(first.toLowerCase());
				sBuffer.append(tmp.substring(1));
				if (i < split.length - 1) {
					continue;
				}
				if (iterator.hasNext()) {
					sBuffer.append(" ").append(value).append(",");
					continue;
				}
				sBuffer.append(" ").append(value);
			}

		}
		if (sBuffer.length() > 0) {
			return sBuffer.toString();
		}

		return null;
	}
	

	/**
	 * 将服务接口上下文内容序列化为指定实体对象
	 * 
	 * @param __current
	 *            上下文对象
	 * @param clazz
	 *            需要转换的实体类
	 * @return V
	 * @throws NullValueException
	 */
	@SuppressWarnings("unchecked")
	public static <V> V getCurrentCxt(Current __current, Class<V> clazz)
			throws NullValueException {
		JSONObject cxt = JSONUtils.parseObject(__current.ctx);
		if (cxt == null || cxt.size() == 0) {
			throw new NullValueException("无可用上下文信息", "上下文信息获取错误");
		}
		return (V) JSONUtils.toBean(cxt, clazz);
	}
}
