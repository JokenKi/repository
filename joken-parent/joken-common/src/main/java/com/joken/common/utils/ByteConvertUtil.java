/*
 * @(#)ByteConvertUtil.java	V0.0.1 2015-2-3, 下午1:37:07
 *
 * Copyright 2015 www.ifood517.com. All rights reserved.
 * www.ifood517.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.joken.common.utils;

/**
 * <p>
 * byte数组和short、int、long数据的互转工具类。
 * 
 * @version V0.0.1, 2015-2-3
 * @author 李付华(Frank Li)，Email：fuhua_lee@163.com，QQ：746611085。
 * @since V0.0.1
 */
public class ByteConvertUtil {

	/**
	 * 转换byte数组到long类型
	 * 
	 * @param longBytes
	 *            8个长度的数组，如果大于8个长度则只用前8个位置的byte
	 * @return long
	 */
	public static long bytesToLong(byte[] longBytes) {
		byte[] data = new byte[8];
		System.arraycopy(longBytes, 0, data, 0, 8);
		int mask = 0xff;
		long temp = 0;
		long res = 0;
		for (int i = 0; i < 8; i++) {
			res <<= 8;
			temp = data[i] & mask;
			res |= temp;
		}
		return res;
	}

	/**
	 * 转换4个长度的byte数组到无符号long类型
	 * 
	 * @param longBytes
	 *            4个长度的数组，如果大于4个长度则只用前4个位置的byte
	 * @return long
	 */
	public static long bytes4LengthToLong(byte[] longBytes) {
		byte[] data = new byte[8];
		data[0] = 0;
		data[1] = 0;
		data[2] = 0;
		data[3] = 0;
		System.arraycopy(longBytes, 0, data, 4, 4);
		int mask = 0xff;
		long temp = 0;
		long res = 0;
		for (int i = 0; i < 8; i++) {
			res <<= 8;
			temp = data[i] & mask;
			res |= temp;
		}
		return res;
	}

	/**
	 * 根据指定的开始索引位置转换byte数组到long类型
	 * 
	 * @param longBytes
	 *            8个长度的数组，如果大于8个长度则只用前8个位置的byte
	 * @param index
	 *            数组中读取数据的开始索引
	 * @return long
	 */
	public static long bytesToLong(byte[] longBytes, int index) {
		byte[] data = new byte[8];
		System.arraycopy(longBytes, index, data, 0, 8);
		int mask = 0xff;
		long temp = 0;
		long res = 0;
		for (int i = 0; i < 8; i++) {
			res <<= 8;
			temp = data[i] & mask;
			res |= temp;
		}
		return res;
	}

	/**
	 * 根据指定的开始索引位置转换4个长度的byte数组到无符号long类型
	 * 
	 * @param longBytes
	 *            4个长度的数组，如果大于4个长度则只用前4个位置的byte
	 * @param index
	 *            数组中读取数据的开始索引
	 * @return long
	 */
	public static long bytes4LengthToLong(byte[] longBytes, int index) {
		byte[] data = new byte[8];
		data[0] = 0;
		data[1] = 0;
		data[2] = 0;
		data[3] = 0;
		System.arraycopy(longBytes, index, data, 4, 4);
		int mask = 0xff;
		long temp = 0;
		long res = 0;
		for (int i = 0; i < 8; i++) {
			res <<= 8;
			temp = data[i] & mask;
			res |= temp;
		}
		return res;
	}

	/**
	 * 转换低位在前byte数组到long类型。
	 * 
	 * @param longBytes
	 *            8个长度的数组，如果大于8个长度则只用前8个位置的byte
	 * @return long
	 */
	public static long bytesToLongLowOrder(byte[] longBytes) {
		byte[] data = new byte[8];
		System.arraycopy(longBytes, 0, data, 0, 8);
		int mask = 0xff;
		long temp = 0;
		long res = 0;
		for (int i = 7; i > -1; i--) {
			res <<= 8;
			temp = data[i] & mask;
			res |= temp;
		}
		return res;
	}

	/**
	 * 转换低位在前4个长度的byte数组到无符号long类型。
	 * 
	 * @param longBytes
	 *            4个长度的数组，如果大于4个长度则只用前4个位置的byte
	 * @return long
	 */
	public static long bytes4LengthToLongLowOrder(byte[] longBytes) {
		byte[] data = new byte[8];
		data[4] = 0;
		data[5] = 0;
		data[6] = 0;
		data[7] = 0;
		System.arraycopy(longBytes, 0, data, 0, 4);
		int mask = 0xff;
		long temp = 0;
		long res = 0;
		for (int i = 7; i > -1; i--) {
			res <<= 8;
			temp = data[i] & mask;
			res |= temp;
		}
		return res;
	}

	/**
	 * 根据指定的开始索引位置转换低位在前byte数组到long类型。
	 * 
	 * @param longBytes
	 *            8个长度的数组，如果大于8个长度则只用前8个位置的byte
	 * @param index
	 *            数组中读取数据的开始索引
	 * @return long
	 */
	public static long bytesToLongLowOrder(byte[] longBytes, int index) {
		byte[] data = new byte[8];
		System.arraycopy(longBytes, index, data, 0, 8);
		int mask = 0xff;
		long temp = 0;
		long res = 0;
		for (int i = 7; i > -1; i--) {
			res <<= 8;
			temp = data[i] & mask;
			res |= temp;
		}
		return res;
	}

	/**
	 * 根据指定的开始索引位置转换低位在前4个长度的byte数组到无符号long类型。
	 * 
	 * @param longBytes
	 *            4个长度的数组，如果大于4个长度则只用前4个位置的byte
	 * @param index
	 *            数组中读取数据的开始索引
	 * @return long
	 */
	public static long bytes4LengthToLongLowOrder(byte[] longBytes, int index) {
		byte[] data = new byte[8];
		data[4] = 0;
		data[5] = 0;
		data[6] = 0;
		data[7] = 0;
		System.arraycopy(longBytes, index, data, 0, 4);
		int mask = 0xff;
		long temp = 0;
		long res = 0;
		for (int i = 7; i > -1; i--) {
			res <<= 8;
			temp = data[i] & mask;
			res |= temp;
		}
		return res;
	}

	/**
	 * 把long类型数据转换成8个长度的byte数组
	 * 
	 * @param num
	 *            long类型数字
	 * @return long
	 */
	public static byte[] longToBytes(long num) {
		byte[] b = new byte[8];
		for (int i = 0; i < 8; i++) {
			b[i] = (byte) (num >> (56 - i * 8));
		}
		return b;
	}

	/**
	 * 把long类型数据强制转换成int后，再转换成4个长度的byte数组
	 * 
	 * @param num
	 *            long类型数字
	 * @return byte[]
	 */
	public static byte[] longTo4LengthBytes(long num) {
		int i = (int) (num);
		return intToBytes(i);
	}

	/**
	 * 把long类型数据转换成8个长度的byte数组,并根据开始索引放到现有数据数组中的指定位置.
	 * 
	 * @param num
	 *            long类型数字
	 * @param data
	 *            携带数据的现有数组
	 * @param index
	 *            往数组中放数据的开始索引
	 * @return 增加转换后long数据byte数组
	 */
	public static byte[] longToBytes(long num, byte[] data, int index) {
		for (int i = 0; i < 8; i++) {
			data[index + i] = (byte) (num >> (56 - i * 8));
		}
		return data;
	}

	/**
	 * 把long类型数据强制转换成int后，再转换成4个长度的byte数组,并根据开始索引放到现有数据数组中的指定位置.
	 * 
	 * @param num
	 *            long类型数字
	 * @param data
	 *            携带数据的现有数组
	 * @param index
	 *            往数组中放数据的开始索引
	 * @return 增加转换后long数据byte数组
	 */
	public static byte[] longTo4LengthBytes(long num, byte[] data, int index) {
		int i = (int) (num);
		return intToBytes(i, data, index);
	}

	/**
	 * 把long类型数据转换成低位在前8个长度的byte数组。
	 * 
	 * @param num
	 *            long类型数字
	 * @return byte[]
	 */
	public static byte[] longToBytesLowOrder(long num) {
		byte[] b = new byte[8];
		int index = 0;
		for (int i = 7; i > -1; i--) {
			b[index++] = (byte) (num >> (56 - i * 8));
		}
		return b;
	}

	/**
	 * 把long类型数据强制转换成int后，再转换成低位在前的4个长度byte数组
	 * 
	 * @param num
	 *            long类型数字
	 * @return byte[]
	 */
	public static byte[] longTo4LengthBytesLowOrder(long num) {
		int i = (int) (num);
		return intToBytesLowOrder(i);
	}

	/**
	 * 把long类型数据转换成低位在前8个长度的byte数组，并根据开始索引放到现有数据数组中的指定位置。
	 * 
	 * @param num
	 *            long类型数字
	 * @param data
	 *            携带数据的现有数组
	 * @param index
	 *            往数组中放数据的开始索引
	 * @return 增加转换后long数据byte数组
	 */
	public static byte[] longToBytesLowOrder(long num, byte[] data, int index) {
		for (int i = 7; i > -1; i--) {
			data[index++] = (byte) (num >> (56 - i * 8));
		}
		return data;
	}

	/**
	 * 把long类型数据强制转换成int后，再转换成低位在前的4个长度byte数组，并根据开始索引放到现有数据数组中的指定位置。
	 * 
	 * @param num
	 *            long类型数字
	 * @param data
	 *            携带数据的现有数组
	 * @param index
	 *            往数组中放数据的开始索引
	 * @return 增加转换后long数据byte数组
	 */
	public static byte[] longTo4LengthBytesLowOrder(long num, byte[] data,
			int index) {
		int i = (int) (num);
		return intToBytesLowOrder(i, data, index);
	}

	/**
	 * 转换byte数组到int类型
	 * 
	 * @param intBytes
	 *            4个长度的数组，如果大于4个长度则只用前4个位置的byte
	 * @return int
	 */
	public static int bytesToInt(byte[] intBytes) {
		byte[] data = new byte[4];
		System.arraycopy(intBytes, 0, data, 0, 4);
		return (int) ((data[0] & 0xff) << 24) | ((data[1] & 0xff) << 16)
				| ((data[2] & 0xff) << 8) | ((data[3] & 0xff) << 0);
	}

	/**
	 * 转换两个长度的byte数组到到无符号int类型
	 * 
	 * @param intBytes
	 *            2个长度的数组，如果大于2个长度则只用前2个位置的byte
	 * @return int
	 */
	public static int bytes2LengthToInt(byte[] intBytes) {
		byte[] data = new byte[2];
		System.arraycopy(intBytes, 0, data, 0, 2);
		return (int) ((0 & 0xff) << 24) | ((0 & 0xff) << 16)
				| ((data[0] & 0xff) << 8) | ((data[1] & 0xff) << 0);
	}

	/**
	 * 根据指定的开始索引位置转换byte数组到int类型
	 * 
	 * @param intBytes
	 *            4个长度的数组，如果大于4个长度则只用前4个位置的byte
	 * @param index
	 *            数组中读取数据的开始索引
	 * @return int
	 */
	public static int bytesToInt(byte[] intBytes, int index) {
		byte[] data = new byte[4];
		System.arraycopy(intBytes, index, data, 0, 4);
		return (int) ((data[0] & 0xff) << 24) | ((data[1] & 0xff) << 16)
				| ((data[2] & 0xff) << 8) | ((data[3] & 0xff) << 0);
	}

	/**
	 * 根据指定的开始索引位置转换两个长度的byte数组到到无符号int类型
	 * 
	 * @param intBytes
	 *            2个长度的数组，如果大于2个长度则只用前2个位置的byte
	 * @param index
	 *            数组中读取数据的开始索引
	 * @return int
	 */
	public static int bytes2LengthToInt(byte[] intBytes, int index) {
		byte[] data = new byte[2];
		System.arraycopy(intBytes, index, data, 0, 2);
		return (int) ((0 & 0xff) << 24) | ((0 & 0xff) << 16)
				| ((data[0] & 0xff) << 8) | ((data[1] & 0xff) << 0);
	}

	/**
	 * 转换低位在前byte数组到int类型。
	 * 
	 * @param intBytes
	 *            4个长度的数组，如果大于4个长度则只用前4个位置的byte
	 * @return int
	 */
	public static int bytesToIntLowOrder(byte[] intBytes) {
		byte[] data = new byte[4];
		System.arraycopy(intBytes, 0, data, 0, 4);
		return (int) ((data[3] & 0xff) << 24) | ((data[2] & 0xff) << 16)
				| ((data[1] & 0xff) << 8) | ((data[0] & 0xff) << 0);
	}

	/**
	 * 转换两个长度低位在前的byte数组到到无符号int类型
	 * 
	 * @param intBytes
	 *            2个长度的数组，如果大于2个长度则只用前2个位置的byte
	 * @return int
	 */
	public static int bytes2LengthToIntLowOrder(byte[] intBytes) {
		byte[] data = new byte[2];
		System.arraycopy(intBytes, 0, data, 0, 2);
		return (int) ((0 & 0xff) << 24) | ((0 & 0xff) << 16)
				| ((data[1] & 0xff) << 8) | ((data[0] & 0xff) << 0);
	}

	/**
	 * 根据指定的开始索引位置转换低位在前byte数组到int类型。
	 * 
	 * @param intBytes
	 *            4个长度的数组，如果大于4个长度则只用前4个位置的byte
	 * @param index
	 *            数组中读取数据的开始索引
	 * @return int
	 */
	public static int bytesToIntLowOrder(byte[] intBytes, int index) {
		byte[] data = new byte[4];
		System.arraycopy(intBytes, index, data, 0, 4);
		return (int) ((data[3] & 0xff) << 24) | ((data[2] & 0xff) << 16)
				| ((data[1] & 0xff) << 8) | ((data[0] & 0xff) << 0);
	}

	/**
	 * 根据指定的开始索引位置转换两个长度低位在前的byte数组到到无符号int类型
	 * 
	 * @param intBytes
	 *            2个长度的数组，如果大于2个长度则只用前2个位置的byte
	 * @param index
	 *            数组中读取数据的开始索引
	 * @return int
	 */
	public static int bytes2LengthToIntLowOrder(byte[] intBytes, int index) {
		byte[] data = new byte[2];
		System.arraycopy(intBytes, index, data, 0, 2);
		return (int) ((0 & 0xff) << 24) | ((0 & 0xff) << 16)
				| ((data[1] & 0xff) << 8) | ((data[0] & 0xff) << 0);
	}

	/**
	 * 把int类型数据转换成4个长度的byte数组
	 * 
	 * @param i
	 *            int类型数字
	 * @return byte[]
	 */
	public static byte[] intToBytes(int i) {
		byte[] intBytes = new byte[4];
		intBytes[0] = (byte) (i >> 24);
		intBytes[1] = (byte) (i >> 16);
		intBytes[2] = (byte) (i >> 8);
		intBytes[3] = (byte) (i >> 0);
		return intBytes;
	}

	/**
	 * 把int类型数据强制转换成short后，再转换成2个长度的byte数组
	 * 
	 * @param i
	 *            int类型数字
	 * @return byte[]
	 */
	public static byte[] intTo2LengthBytes(int i) {
		short s = (short) i;
		return shortToBytes(s);
	}

	/**
	 * 把int类型数据转换成4个长度低位在前的byte数组。
	 * 
	 * @param i
	 *            int类型数字
	 * @return byte[]
	 */
	public static byte[] intToBytesLowOrder(int i) {
		byte[] intBytes = new byte[4];
		intBytes[3] = (byte) (i >> 24);
		intBytes[2] = (byte) (i >> 16);
		intBytes[1] = (byte) (i >> 8);
		intBytes[0] = (byte) (i >> 0);
		return intBytes;
	}

	/**
	 * 把int类型数据强制转换成short后，再转换成2个长度低位在前的byte数组。
	 * 
	 * @param i
	 *            int类型数字
	 * @return byte[]
	 */
	public static byte[] intTo2LengthBytesLowOrder(int i) {
		short s = (short) i;
		return shortToLowOrderBytes(s);
	}

	/**
	 * 把int类型数据转换成4个长度的byte数组,并根据开始索引放到现有数据数组中的指定位置.
	 * 
	 * @param i
	 *            int类型数字
	 * @param data
	 *            携带数据的现有数组
	 * @param index
	 *            往数组中放数据的开始索引
	 * @return 增加转换后int数据byte数组
	 */
	public static byte[] intToBytes(int i, byte[] data, int index) {
		data[index] = (byte) (i >> 24);
		data[index + 1] = (byte) (i >> 16);
		data[index + 2] = (byte) (i >> 8);
		data[index + 3] = (byte) (i >> 0);
		return data;
	}

	/**
	 * 把int类型数据强制转换成short后，再转换成2个长度的byte数组,并根据开始索引放到现有数据数组中的指定位置.
	 * 
	 * @param i
	 *            int类型数字
	 * @param data
	 *            携带数据的现有数组
	 * @param index
	 *            往数组中放数据的开始索引
	 * @return 增加转换后int数据byte数组
	 */
	public static byte[] intTo2LengthBytes(int i, byte[] data, int index) {
		short s = (short) i;
		return shortToBytes(s, data, index);
	}

	/**
	 * 把int类型数据转换成4个长度低位在前的byte数组,并根据开始索引放到现有数据数组中的指定位置。
	 * 
	 * @param i
	 *            int类型数字
	 * @param data
	 *            携带数据的现有数组
	 * @param index
	 *            往数组中放数据的开始索引
	 * @return 增加转换后int数据byte数组
	 */
	public static byte[] intToBytesLowOrder(int i, byte[] data, int index) {
		data[index + 3] = (byte) (i >> 24);
		data[index + 2] = (byte) (i >> 16);
		data[index + 1] = (byte) (i >> 8);
		data[index] = (byte) (i >> 0);
		return data;
	}

	/**
	 * 把int类型数据强制转换成short后，再转换成2个长度低位在前的byte数组,并根据开始索引放到现有数据数组中的指定位置。
	 * 
	 * @param i
	 *            int类型数字
	 * @param data
	 *            携带数据的现有数组
	 * @param index
	 *            往数组中放数据的开始索引
	 * @return 增加转换后int数据byte数组
	 */
	public static byte[] intTo2LengthBytesLowOrder(int i, byte[] data, int index) {
		short s = (short) i;
		return shortToLowOrderBytes(s, data, index);
	}

	/**
	 * 转换byte数组到short类型
	 * 
	 * @param shortBytes
	 *            两个长度的数组，如果大于两个长度则只用前两个位置的byte
	 * @return short
	 */
	public static short bytesToShort(byte[] shortBytes) {
		byte[] data = new byte[2];
		System.arraycopy(shortBytes, 0, data, 0, 2);
		return (short) (((data[0] << 8) | data[1] & 0xff));
	}

	/**
	 * 转换byte到无符号short类型
	 * 
	 * @param data
	 *            有符号的byte
	 * @return short
	 */
	public static short byteToShort(byte data) {
		return (short) (((0 << 8) | data & 0xff));
	}

	/**
	 * 根据指定的开始索引位置转换byte数组到short类型
	 * 
	 * @param shortBytes
	 *            两个长度的数组，如果大于两个长度则只用前两个位置的byte
	 * @param index
	 *            数组中读取数据的开始索引
	 * @return short
	 */
	public static short bytesToShort(byte[] shortBytes, int index) {
		byte[] data = new byte[2];
		System.arraycopy(shortBytes, index, data, 0, 2);
		return (short) (((data[0] << 8) | data[1] & 0xff));
	}

	/**
	 * 低位在前byte数组到short类型。
	 * 
	 * @param shortBytes
	 *            两个长度的数组，如果大于两个长度则只用前两个位置的byte
	 * @return short
	 */
	public static short bytesToShortLowOrder(byte[] shortBytes) {
		byte[] data = new byte[2];
		System.arraycopy(shortBytes, 0, data, 0, 2);
		return (short) ((data[1] << 8) | data[0] & 0xff);
	}

	/**
	 * 根据指定的开始索引位置转换低位在前byte数组到short类型。
	 * 
	 * @param shortBytes
	 *            两个长度的数组，如果大于两个长度则只用前两个位置的byte
	 * @param index
	 *            数组中读取数据的开始索引
	 * @return short
	 */
	public static short bytesToShortLowOrder(byte[] shortBytes, int index) {
		byte[] data = new byte[2];
		System.arraycopy(shortBytes, index, data, 0, 2);
		return (short) (((data[1] << 8) | data[0] & 0xff));
	}

	/**
	 * 把short类型数据转换成2个长度的byte数组
	 * 
	 * @param s
	 *            short类型数字
	 * @return byte[]
	 */
	public static byte[] shortToBytes(short s) {
		byte[] shortBytes = new byte[2];
		shortBytes[0] = (byte) (s >> 8);
		shortBytes[1] = (byte) (s >> 0);
		return shortBytes;
	}

	/**
	 * 把short类型数据转换成2个长度的byte数组,并根据开始索引放到现有数据数组中的指定位置.
	 * 
	 * @param s
	 *            short类型数字
	 * @param data
	 *            携带数据的现有数组
	 * @param index
	 *            往数组中放数据的开始索引
	 * @return 增加转换后short数据byte数组
	 */
	public static byte[] shortToBytes(short s, byte[] data, int index) {
		data[index] = (byte) (s >> 8);
		data[index + 1] = (byte) (s >> 0);
		return data;
	}

	/**
	 * 把short类型数据转换成2个长度的byte数组，低位在前。
	 * 
	 * @param s
	 *            short类型数字
	 * @return byte[]
	 */
	public static byte[] shortToLowOrderBytes(short s) {
		byte[] shortBytes = new byte[2];
		shortBytes[1] = (byte) (s >> 8);
		shortBytes[0] = (byte) (s >> 0);
		return shortBytes;
	}

	/**
	 * 把short类型数据转换成2个长度的byte数组，低位在前，并根据开始索引放到现有数据数组中的指定位置。
	 * 
	 * @param s
	 *            short类型数字
	 * @param data
	 *            携带数据的现有数组
	 * @param index
	 *            往数组中放数据的开始索引
	 * @return 增加转换后short数据byte数组
	 */
	public static byte[] shortToLowOrderBytes(short s, byte[] data, int index) {
		data[index + 1] = (byte) (s >> 8);
		data[index] = (byte) (s >> 0);
		return data;
	}

	/**
	 * 把byte类型数组转换成16进制字符串
	 * 
	 * @param b
	 *            byte数组
	 * @return String
	 */
	public static String byte2HexStrLowOrder(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = b.length - 1; n > -1; n--) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}

	/**
	 * 把byte类型数组转换成16进制字符串
	 * 
	 * @param b
	 *            byte数组
	 * @return String
	 */
	public static String byte2HexStr(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}

	/**
	 * 将16进制的数组成的字符串转换成byte类型的数组
	 * 
	 * @param src
	 *            16进制的数组成的字符串
	 * @return byte[]
	 */
	public static byte[] hexStr2Bytes(String src) {
		int m = 0, n = 0;
		int l = src.length() / 2;
		System.out.println(l);
		byte[] ret = new byte[l];
		for (int i = 0; i < l; i++) {
			m = i * 2 + 1;
			n = m + 1;
			ret[i] = uniteBytes(src.substring(i * 2, m), src.substring(m, n));
		}
		return ret;
	}

	/**
	 * 将字符串转为byte数据
	 * 
	 * @param src0
	 *            字符0
	 * @param src1
	 *            字符1
	 * @return byte
	 */
	private static byte uniteBytes(String src0, String src1) {
		byte b0 = Byte.decode("0x" + src0).byteValue();
		b0 = (byte) (b0 << 4);
		byte b1 = Byte.decode("0x" + src1).byteValue();
		byte ret = (byte) (b0 | b1);
		return ret;
	}

}
