package com.joken.common.utils;

import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * 随机数工具类
 * 
 * @version V1.0.0, 2016-3-15
 * @author 欧阳增高
 * @since V1.0.0
 */
public class RandomUtils {
	/**
	 * 
	 * 随机数区间枚举定义
	 * 
	 * @version V1.0.0, 2016-3-15
	 * @author 欧阳增高
	 * @since V1.0.0
	 */
	enum RandChar {
		/**
		 * 数字
		 */
		d("0123456789"),
		/**
		 * 小写字母
		 */
		l("abcdefghijklmnopqrstuvwxyz"),
		/**
		 * 大写字母
		 */
		u("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
		/**
		 * 大小写字母
		 */
		C("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"),
		/**
		 * 数字+大小写字母
		 */
		S("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"), ;

		private final String value;

		/**
		 * 构造
		 * 
		 * @param value
		 */
		private RandChar(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return value;
		}
	};

	private RandomUtils() {
	}

	/**
	 * 获取指定数量的随机字符
	 * 
	 * @param amount
	 *            随机字符数量
	 * @param chr
	 *            随机字符类型(类型可组合使用): d为数字; l为小写字母； u为大写字母,C为大小写字母，S为大小写字母及数字；
	 * @return String
	 */
	public static String getRands(int amount, String chr) {
		Lock lock = new ReentrantLock();
		lock.lock();
		try {
			StringBuffer rands = new StringBuffer("");
			int digit = -1;
			StringBuffer sb = new StringBuffer("");
			if (chr != null && !chr.equals("")) {
				char[] c = chr.toCharArray();
				for (int i = 0; i < chr.length(); i++) {
					char ch = c[i];
					try {
						String s = RandChar.valueOf(
								(new String(new char[] { ch }))).toString();
						if (s != null) {
							sb.append(s);
						}
					} catch (IllegalArgumentException e) {
					}
				}
			}
			if (sb.length() == 0) {
				sb.append(RandChar.S);
			}
			digit = sb.length();

			for (int i = 0; i < amount; i++) {
				int rand = (int) (Math.random() * digit);
				rands.append(sb.charAt(rand));
			}
			return rands.toString();
		} catch (Exception e) {
		} finally {
			lock.unlock();
		}
		return null;
	}

	// public static void main(String[] args) {
	// System.out.println(getRands(32, "S"));
	// }

	/**
	 * 获取36位随机码
	 * 
	 * @return String
	 * @date 2016年7月22日下午2:20:22
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().toUpperCase();
	}
}
