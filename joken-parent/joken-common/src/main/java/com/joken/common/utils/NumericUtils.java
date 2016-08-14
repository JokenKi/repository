/**
 * 通用数字操作静态类
 */
package com.joken.common.utils;

import java.math.BigDecimal;

import com.joken.common.properties.SystemGlobal;

/**
 * 通用数字工具类
 * 
 * @author Administrator
 * 
 */
public class NumericUtils {
	/**
	 * 私有化构造方法，防止用户实例化
	 */
	private NumericUtils() {
	}

	/**
	 * 计算排列组合总次数
	 * 
	 * @param maxLength
	 *            排列组合字符总长度
	 * @param count
	 *            组合最小长度
	 * @return long
	 */
	public static long Factorial(int maxLength, int count) {
		long sum = 1;
		if (count > 1) {
			int num = maxLength;
			sum = num;
			for (int i = 0; i < count - 1; i++) {
				sum *= (num - 1);
				num--;
			}
		} else if (count == 1) {
			return maxLength;
		} else {
			return 0;
		}
		return sum;
	}

	/**
	 * 排列组合算法，Cmn，up是m,down是n
	 * 
	 * @param down
	 *            代表n
	 * @param up
	 *            代表m
	 * @return 返回组合组合数
	 */
	public static long Combination(int down, int up) {
		if (down < 1 || up < 1) {
			return 0;
		}
		return Factorial(down, up) / Factorial(up, up);
	}

	/**
	 * 将数字转换按平台配置转换比例进行转换
	 * 
	 * @param amount
	 *            需要转换的数字
	 * @return long
	 */
	public static long convertAmountUnit(Number amount) {
		if (amount == null)
			return 0;
		BigDecimal result = new BigDecimal(amount.toString());
		result.multiply(new BigDecimal(SystemGlobal
				.getInteger("tender.amount.unit")));
		return result.longValue();
	}

	/**
	 * 检查指定对象是否可转为数据类型
	 * 
	 * @param value
	 *            需要检查的对象
	 * @return boolean
	 */
	public static boolean isNumeric(Object value) {
		if (value == null)
			return false;
		try {
			new BigDecimal(value.toString());
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
