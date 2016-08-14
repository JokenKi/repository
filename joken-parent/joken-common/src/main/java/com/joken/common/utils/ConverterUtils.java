/**
 * @(#)CollectionUtils.java	V1.0.0 2015-8-13 下午5:02:10
 *
 * Copyright 2015 www.ifood517.com. All rights reserved.
 * www.ifood517.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.joken.common.utils;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.beanutils.ConvertUtilsBean;

/***
 * 类描述： 转换工具类
 * 
 * @author 欧阳增高
 * @date 下午04:21:52
 * @since 1.0
 */
public class ConverterUtils extends ConvertUtilsBean {

	/**
	 * 将日期时间转换为字符
	 */
	@Override
	public String convert(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof Date) {
			String date = DateUtils.CHN_DATE_TIME_EXTENDED_FORMAT.format(value);
			if (date.length() > 19) {
				date = date.substring(0, 19);
			}
			return date.replace(" 00:00:00", "");
		}
		return super.convert(value);
	}

	/**
	 * 字符串时：分：秒转当前日期时分秒
	 * 
	 * @param time
	 *            如:08:30:00
	 * @param separator
	 *            则为:
	 * @return 时间戳
	 */
	public static Long string2TimeSeconds(String time, String separator) {
		Calendar cl = Calendar.getInstance();
		String[] times = time.split(separator);
		cl.set(Calendar.HOUR_OF_DAY, Integer.valueOf(times[0]));
		cl.set(Calendar.MINUTE, Integer.valueOf(times[1]));
		cl.set(Calendar.SECOND, Integer.valueOf(times[2]));
		cl.set(Calendar.MILLISECOND, 0);
		return cl.getTimeInMillis() / 1000;
	}
}
