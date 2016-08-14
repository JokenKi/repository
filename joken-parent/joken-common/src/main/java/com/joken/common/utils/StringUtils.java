/**
 * 字符操作通用静态类
 */
package com.joken.common.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 字符串操作工具类
 * 
 * @author Administrator
 * 
 */
public class StringUtils extends org.apache.commons.lang.StringUtils {
	/**
	 * 私有化默认构造器
	 */
	private StringUtils() {
	};

	private final static String UPPER_CASE_SPLIT_REG = "(?=[A-Z])";
	private final static String UPPER_CASE_REG = "([A-Z])";

	/**
	 * 检查参数值是否为空或""
	 * 
	 * @param value
	 *            需要检查的对象值
	 * @return true: 为空,false:不为空
	 */
	public static boolean isEmpty(Object value) {
		if (value == null || "".equals(value.toString().trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 检查参数值是否为空或""，为空或""时返回默认值
	 * 
	 * @param val
	 *            需要检查的对象值
	 * @param def
	 *            默认值
	 * @return String
	 */
	public static String getValue(String val, String def) {
		if (!isEmpty(val)) {
			return val;
		}
		return def;
	}

	/**
	 * 去除带分割符","里的重复ID
	 * 
	 * @param str
	 *            需要去除的ID串
	 * @return String
	 */
	public static String removeRepeate(String str) {
		String[] arr = str.split(",");
		if (arr.length == 1) {
			return str;
		}
		List<String> list = new LinkedList<String>();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			if (!list.contains(arr[i]) && !"".equals(arr[i])) {
				list.add(arr[i]);
			}
		}
		for (int i = 0; i < list.size(); i++) {
			if (i != 0) {
				buf.append(",");
			}
			buf.append(list.get(i));
		}
		return buf.toString();
	}

	/**
	 * 填充字符串以符合指定最小长度，如果传入值小于最小长度则值前补"0"
	 * 
	 * @param val
	 *            传入值
	 * @param length
	 *            最小长度
	 * @return 填充后的字符串
	 */
	public static final String getFillDigits(String val, long length) {
		if (val == null) {
			val = "";
		}
		length -= val.length();
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < length; i++) {
			sb.append("0");
		}
		sb.append(val);
		return sb.toString();
	}

	/**
	 * 将字符串首字母转换为大写
	 * 
	 * @param str
	 *            需要操作的字符串
	 * @return String
	 */
	public static String firstUpperCase(String str) {
		if (isEmpty(str)) {
			return str;
		}
		String first = str.substring(0, 1);
		return str.replaceFirst(first, first.toUpperCase());
	}

	/**
	 * 将字符串首字母转换为小写
	 * 
	 * @param str
	 *            需要操作的字符串
	 * @return String
	 */
	public static String firstLowerCase(String str) {
		if (isEmpty(str)) {
			return str;
		}
		String first = str.substring(0, 1);
		return str.replaceFirst(first, first.toLowerCase());
	}

	/**
	 * 按指定字符将字符串拆分设置是否每段首字母大写
	 * 
	 * @param str
	 *            需要拆分的字符串
	 * @param tokeChar
	 *            拆分字符
	 * @param isUpperCase
	 *            是否需要大写首字母
	 * @return String
	 */
	public static String Tokenizer(String str, String tokeChar,
			boolean isUpperCase) {
		StringTokenizer tokenObjectName = new StringTokenizer(str, tokeChar);
		StringBuilder sbObjectName = new StringBuilder("");
		String tmpString;
		while (tokenObjectName.hasMoreTokens()) {
			tmpString = tokenObjectName.nextToken();
			sbObjectName.append(isUpperCase ? firstUpperCase(tmpString)
					: tmpString);
		}
		return firstLowerCase(sbObjectName.toString());
	}

	/**
	 * 按指定字符将字符串拆分并返回字符串数组
	 * 
	 * @param str
	 *            需要拆分的字符串
	 * @param tokeChar
	 *            拆分字符
	 * @return List<String>
	 */
	public static List<String> Split(String str, String tokeChar) {
		List<String> results = new ArrayList<String>();
		if (isEmpty(str)) {
			return results;
		}
		StringTokenizer token = new StringTokenizer(str, tokeChar);
		results = new ArrayList<String>(token.countTokens());
		while (token.hasMoreTokens()) {
			results.add(token.nextToken());
		}
		return results;
	}

	/**
	 * 获取字符以指定分隔符拆分后长度
	 * 
	 * @param str
	 *            需要拆分的字符串
	 * @param toke
	 *            分隔符
	 * @return int
	 */
	public static int SplistLength(String str, String toke) {
		if (isEmpty(str)) {
			return 0;
		}
		return str.split(toke).length;
	}

	/**
	 * 查询字符串中是否存在指定字符
	 * 
	 * @param str
	 *            原始字符串
	 * @param chr
	 *            需要查询的字符串
	 * @return int
	 */
	public static int IndexOf(String str, String chr) {
		if (isEmpty(str) || isEmpty(chr)) {
			return -1;
		}
		return str.indexOf(chr);
	}

	/**
	 * 格式化字符串
	 * 
	 * @param format
	 *            预定义字符串
	 * @param values
	 *            需要格式的值
	 * @return String
	 */
	public static String format(String format, Object... values) {
		if (values == null || values.length == 0) {
			values = new Object[] { "" };
		}
		return String.format(format, values);
	}

	/**
	 * 将字符串中指定开始位置及长度的字符替换成传入的屏蔽符(如：*)
	 * 
	 * @param val
	 *            需要保护的字符串
	 * @param chr
	 *            保护位置替换符
	 * @param start
	 *            开始位置
	 * @param convertLength
	 *            保护长度
	 * @return 保护转换后的字符串
	 */
	public static String shield(String val, String chr, int start,
			int convertLength) {
		int length = val.length();
		if (start >= length) {
			return val;
		}
		StringBuffer sb = new StringBuffer(val.substring(0, start));
		int tmpLeng = length - start;
		int len = Math.min(tmpLeng, convertLength);
		for (int i = 0; i < len; i++) {
			sb.append(chr);
		}
		if (length > start + convertLength) {
			sb.append(val.substring(start + convertLength));
		}
		return sb.toString();
	}

	// public static void main(String[] args) {
	// System.out.println(StringUtils.shield("测试用户", "*", 1, 2));
	// System.out
	// .println(StringUtils.shield("123456789098765432", "*", 2, 16));
	// }

	/**
	 * 将model属性字符串 转为数据库字段属性 如:busNameId转为bus_name_id
	 * 
	 * @author wangby
	 * @date 2016年5月19日下午4:36:33
	 */
	public static String modelField2DBProp(String modelFiled) {
		if (StringUtils.isEmpty(modelFiled)) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		String tmp = null;
		String[] split = modelFiled.split(UPPER_CASE_SPLIT_REG);
		String first = null;
		for (int i = 0; i < split.length; i++) {
			tmp = split[i];
			if (tmp.length() < 2) {
				sb.append(tmp);
				continue;
			}
			first = tmp.substring(0, 1);
			if (!first.matches(UPPER_CASE_REG)) {
				sb.append(tmp);
				continue;
			}
			sb.append("_").append(first.toLowerCase());
			sb.append(tmp.substring(1));
			if (i < split.length - 1) {
				continue;
			}
		}
		if (sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}
}
