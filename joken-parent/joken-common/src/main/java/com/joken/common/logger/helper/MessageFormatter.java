package com.joken.common.logger.helper;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 日志内容格式化类
 * 
 * @version V1.0.0, 2016-3-15
 * @author 欧阳增高
 * @since V1.0.0
 */
public final class MessageFormatter {

	static final char DELIM_START = 123;
	static final char DELIM_STOP = 125;
	static final String DELIM_STR = "{}";

	/**
	 * 构造
	 */
	private MessageFormatter() {
	}

	/**
	 * 通过日志内容及业务数据获取格式组件
	 * 
	 * @param messagePattern
	 *            日志内容匹配表达式
	 * @param arg
	 *            日志业务参数
	 * @return FormattingTuple
	 */
	public static final FormattingTuple format(String messagePattern, Object arg) {
		return arrayFormat(messagePattern, new Object[] { arg });
	}

	/**
	 * 通过日志内容及业务数据获取格式组件
	 * 
	 * @param messagePattern
	 *            日志内容匹配表达式
	 * @param arg1
	 *            日志业务参数1
	 * @param arg2
	 *            日志业务参数2
	 * @return FormattingTuple
	 */
	public static final FormattingTuple format(String messagePattern,
			Object arg1, Object arg2) {
		return arrayFormat(messagePattern, new Object[] { arg1, arg2 });
	}

	/**
	 * 获取异常实例对象
	 * 
	 * @param argArray
	 *            异常堆栈
	 * @return Throwable
	 */
	static final Throwable getThrowableCandidate(Object argArray[]) {
		if (argArray == null || argArray.length == 0)
			return null;
		Object lastEntry = argArray[argArray.length - 1];
		if (lastEntry instanceof Throwable)
			return (Throwable) lastEntry;
		else
			return null;
	}

	/**
	 * 通过日志内容及业务数据获取格式组件
	 * 
	 * @param messagePattern
	 *            日志内容匹配表达式
	 * @param argArray
	 *            异常堆栈集合
	 * @return FormattingTuple
	 */
	public static final FormattingTuple arrayFormat(String messagePattern,
			Object argArray[]) {
		Throwable throwableCandidate = getThrowableCandidate(argArray);
		if (messagePattern == null)
			return new FormattingTuple(null, argArray, throwableCandidate);
		if (argArray == null)
			return new FormattingTuple(messagePattern);
		int i = 0;
		StringBuffer sbuf = new StringBuffer(messagePattern.length() + 50);
		int L;
		for (L = 0; L < argArray.length; L++) {
			int j = messagePattern.indexOf("{}", i);
			if (j == -1)
				if (i == 0) {
					return new FormattingTuple(messagePattern, argArray,
							throwableCandidate);
				} else {
					sbuf.append(messagePattern.substring(i,
							messagePattern.length()));
					return new FormattingTuple(sbuf.toString(), argArray,
							throwableCandidate);
				}
			if (isEscapedDelimeter(messagePattern, j)) {
				if (!isDoubleEscaped(messagePattern, j)) {
					L--;
					sbuf.append(messagePattern.substring(i, j - 1));
					sbuf.append('{');
					i = j + 1;
				} else {
					sbuf.append(messagePattern.substring(i, j - 1));
					deeplyAppendParameter(sbuf, argArray[L],
							new HashMap<Object, Object>());
					i = j + 2;
				}
			} else {
				sbuf.append(messagePattern.substring(i, j));
				deeplyAppendParameter(sbuf, argArray[L],
						new HashMap<Object, Object>());
				i = j + 2;
			}
		}

		sbuf.append(messagePattern.substring(i, messagePattern.length()));
		if (L < argArray.length - 1)
			return new FormattingTuple(sbuf.toString(), argArray,
					throwableCandidate);
		else
			return new FormattingTuple(sbuf.toString(), argArray, null);
	}

	/**
	 * 检查表达式中定位符
	 * 
	 * @param messagePattern
	 *            日志内容表达式
	 * @param delimeterStartIndex
	 *            开始位置
	 * @return boolean
	 */
	static final boolean isEscapedDelimeter(String messagePattern,
			int delimeterStartIndex) {
		if (delimeterStartIndex == 0)
			return false;
		char potentialEscape = messagePattern.charAt(delimeterStartIndex - 1);
		return potentialEscape == '\\';
	}

	/**
	 * 检查表达式中双字节字符定位符
	 * 
	 * @param messagePattern
	 *            日志内容表达式
	 * @param delimeterStartIndex
	 *            开始位置
	 * @return boolean
	 */
	static final boolean isDoubleEscaped(String messagePattern,
			int delimeterStartIndex) {
		return delimeterStartIndex >= 2
				&& messagePattern.charAt(delimeterStartIndex - 2) == '\\';
	}

	/**
	 * 追加参数到buffer中
	 * 
	 * @param sbuf
	 *            目标buffer对象
	 * @param o
	 *            业务对象
	 * @param seenMap
	 *            已追加数据
	 */
	private static void deeplyAppendParameter(StringBuffer sbuf, Object o,
			Map<Object, Object> seenMap) {
		if (o == null) {
			sbuf.append("null");
			return;
		}
		if (!o.getClass().isArray())
			safeObjectAppend(sbuf, o);
		else if (o instanceof boolean[])
			booleanArrayAppend(sbuf, (boolean[]) o);
		else if (o instanceof byte[])
			byteArrayAppend(sbuf, (byte[]) o);
		else if (o instanceof char[])
			charArrayAppend(sbuf, (char[]) o);
		else if (o instanceof short[])
			shortArrayAppend(sbuf, (short[]) o);
		else if (o instanceof int[])
			intArrayAppend(sbuf, (int[]) o);
		else if (o instanceof long[])
			longArrayAppend(sbuf, (long[]) o);
		else if (o instanceof float[])
			floatArrayAppend(sbuf, (float[]) o);
		else if (o instanceof double[])
			doubleArrayAppend(sbuf, (double[]) o);
		else
			objectArrayAppend(sbuf, (Object[]) o, seenMap);
	}

	/**
	 * 追加数据到buffer中
	 * 
	 * @param sbuf
	 *            目标buffer
	 * @param o
	 *            需要追加的数据
	 */
	private static void safeObjectAppend(StringBuffer sbuf, Object o) {
		try {
			String oAsString = o.toString();
			sbuf.append(oAsString);
		} catch (Throwable t) {
			System.err
					.println((new StringBuilder(
							"SLF4J: Failed toString() invocation on an object of type ["))
							.append(o.getClass().getName()).append("]")
							.toString());
			t.printStackTrace();
			sbuf.append("[FAILED toString()]");
		}
	}

	/**
	 * 追加数据数组到buffer中
	 * 
	 * @param sbuf
	 *            目标buffer
	 * @param arry
	 *            需要追加的业务数据数组
	 * @param seenMap
	 *            已追加数据标记
	 */
	private static void objectArrayAppend(StringBuffer sbuf, Object arry[],
			Map<Object, Object> seenMap) {
		sbuf.append('[');
		if (!seenMap.containsKey(arry)) {
			seenMap.put(arry, null);
			int len = arry.length;
			for (int i = 0; i < len; i++) {
				deeplyAppendParameter(sbuf, arry[i], seenMap);
				if (i != len - 1)
					sbuf.append(", ");
			}

			seenMap.remove(((Object) (arry)));
		} else {
			sbuf.append("...");
		}
		sbuf.append(']');
	}

	/**
	 * 追加布尔值到buffer中
	 * 
	 * @param sbuf
	 *            目标buffer
	 * @param a
	 *            需要追加的值
	 */
	private static void booleanArrayAppend(StringBuffer sbuf, boolean a[]) {
		sbuf.append('[');
		int len = a.length;
		for (int i = 0; i < len; i++) {
			sbuf.append(a[i]);
			if (i != len - 1)
				sbuf.append(", ");
		}

		sbuf.append(']');
	}

	/**
	 * 追加byte到buffer中
	 * 
	 * @param sbuf
	 *            目标buffer
	 * @param a
	 *            需要追加的值
	 */
	private static void byteArrayAppend(StringBuffer sbuf, byte a[]) {
		sbuf.append('[');
		int len = a.length;
		for (int i = 0; i < len; i++) {
			sbuf.append(a[i]);
			if (i != len - 1)
				sbuf.append(", ");
		}

		sbuf.append(']');
	}

	/**
	 * 追加char到buffer中
	 * 
	 * @param sbuf
	 *            目标buffer
	 * @param a
	 *            需要追加的值
	 */
	private static void charArrayAppend(StringBuffer sbuf, char a[]) {
		sbuf.append('[');
		int len = a.length;
		for (int i = 0; i < len; i++) {
			sbuf.append(a[i]);
			if (i != len - 1)
				sbuf.append(", ");
		}

		sbuf.append(']');
	}

	/**
	 * 追加short到buffer中
	 * 
	 * @param sbuf
	 *            目标buffer
	 * @param a
	 *            需要追加的值
	 */
	private static void shortArrayAppend(StringBuffer sbuf, short a[]) {
		sbuf.append('[');
		int len = a.length;
		for (int i = 0; i < len; i++) {
			sbuf.append(a[i]);
			if (i != len - 1)
				sbuf.append(", ");
		}

		sbuf.append(']');
	}

	/**
	 * 追加int到buffer中
	 * 
	 * @param sbuf
	 *            目标buffer
	 * @param a
	 *            需要追加的值
	 */
	private static void intArrayAppend(StringBuffer sbuf, int a[]) {
		sbuf.append('[');
		int len = a.length;
		for (int i = 0; i < len; i++) {
			sbuf.append(a[i]);
			if (i != len - 1)
				sbuf.append(", ");
		}

		sbuf.append(']');
	}

	/**
	 * 追加long到buffer中
	 * 
	 * @param sbuf
	 *            目标buffer
	 * @param a
	 *            需要追加的值
	 */
	private static void longArrayAppend(StringBuffer sbuf, long a[]) {
		sbuf.append('[');
		int len = a.length;
		for (int i = 0; i < len; i++) {
			sbuf.append(a[i]);
			if (i != len - 1)
				sbuf.append(", ");
		}

		sbuf.append(']');
	}

	/**
	 * 追加float到buffer中
	 * 
	 * @param sbuf
	 *            目标buffer
	 * @param a
	 *            需要追加的值
	 */
	private static void floatArrayAppend(StringBuffer sbuf, float a[]) {
		sbuf.append('[');
		int len = a.length;
		for (int i = 0; i < len; i++) {
			sbuf.append(a[i]);
			if (i != len - 1)
				sbuf.append(", ");
		}

		sbuf.append(']');
	}

	/**
	 * 追加double到buffer中
	 * 
	 * @param sbuf
	 *            目标buffer
	 * @param a
	 *            需要追加的值
	 */
	private static void doubleArrayAppend(StringBuffer sbuf, double a[]) {
		sbuf.append('[');
		int len = a.length;
		for (int i = 0; i < len; i++) {
			sbuf.append(a[i]);
			if (i != len - 1)
				sbuf.append(", ");
		}

		sbuf.append(']');
	}

	// public static void main(String args[]) {
	// FormattingTuple ft = format("Hello World {}", "chenzhurong");
	// System.out.println(ft.getMessage());
	// System.out.println(ft.getThrowable());
	// }
}
