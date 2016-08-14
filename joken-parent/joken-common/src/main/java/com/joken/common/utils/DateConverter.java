/**
 * 时间转换类
 */
package com.joken.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.time.DateUtils;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.asm.Type;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.parser.deserializer.DateDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

/**
 * Bean时间对象转换现实类--<code>DateConverter</code>
 * 
 * @author 欧阳增高
 * @date 2012-05-10 10:03
 */
public class DateConverter extends DateDeserializer implements Converter,
		ObjectDeserializer {
	/**
	 * 时间转换类
	 */
	public static final DateConverter instance = new DateConverter();

	/**
	 * 构造
	 */
	public DateConverter() {
	}

	/**
	 * 值转换
	 */
	protected Object cast(DefaultJSONParser parser, Type clazz,
			Object fieldName, Object val) {
		if (val == null)
			return null;
		if (val instanceof Date)
			return val;
		if (val instanceof Number)
			return new Date(((Number) val).longValue());
		if (val instanceof String) {
			String strVal = (String) val;
			if (strVal.length() == 0)
				return null;
			JSONScanner dateLexer = new JSONScanner(strVal);
			if (dateLexer.scanISO8601DateIfMatch())
				return dateLexer.getCalendar().getTime();
			DateFormat dateFormat = parser.getDateFormat();
			long longVal;
			try {
				return dateFormat.parse(strVal);
			} catch (ParseException e) {
				longVal = Long.parseLong(strVal);
			}
			return new Date(longVal);
		} else {
			throw new JSONException("parse error");
		}
	}

	/**
	 * 时间类型转换
	 */
	@SuppressWarnings("rawtypes")
	public Object convert(Class type, Object value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}

		if (value instanceof Date) {
			return value;
		}

		if (value instanceof Long) {
			Long longValue = (Long) value;
			return new Date(longValue);
		}

		if (value instanceof String) {
			Date date = null;
			try {
				date = DateUtils.parseDate(value.toString(),
						new String[] { "yyyy-MM-dd HH:mm:ss",
								"yyyy-M-d H:mm:ss", "yyyy-MM-dd HH:mm",
								"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss.SSS",
								"MM/dd/yyyy HH:mm:ss.SSS",
								"MM/dd/yyyy HH:mm:ss", "MM/dd/yyyy HH:mm",
								"MM/dd/yyyy" });
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return date;
		}

		return null;
	}

	// public static void main(String[] args) {
	// System.out.println("2014-08-22 10:55:42.0".substring(0, 19));
	// }
}
