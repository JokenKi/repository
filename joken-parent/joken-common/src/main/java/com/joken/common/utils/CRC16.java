/*
 * @(#)CRC16.java	V0.0.1 2015-2-4, 上午10:59:19
 *
 * Copyright 2015 www.ifood517.com. All rights reserved.
 * www.ifood517.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.joken.common.utils;

/**
 * <p>
 * 。
 * 
 * @version V0.0.1, 2015-2-4
 * @author 李付华(Frank Li)，Email：fuhua_lee@163.com，QQ：746611085。
 * @since V0.0.1
 */
public class CRC16 {

	public static final int[] crc_tbl = { 0x0000, 0x1081, 0x2102, 0x3183,
			0x4204, 0x5285, 0x6306, 0x7387, 0x8408, 0x9489, 0xa50a, 0xb58b,
			0xc60c, 0xd68d, 0xe70e, 0xf78f };

	/*
	 * ! \relates QByteArray
	 * 
	 * Returns the CRC-16 checksum of the first len bytes of data.
	 * 
	 * The checksum is independent of the byte order (endianness).
	 * 
	 * \note This function is a 16-bit cache conserving (16 entry table)
	 * implementation of the CRC-16-CCITT algorithm.
	 */
	public static int qChecksum(byte[] data) {
		int crc = 0xffff;
		byte c;
		byte[] p = new byte[data.length];
		System.arraycopy(data, 0, p, 0, data.length);
		for (int i = 0; i < data.length; i++) {
			c = p[i];
			crc = ((crc >> 4) & 0x0fff) ^ crc_tbl[((crc ^ c) & 15)];
			c >>= 4;
			crc = ((crc >> 4) & 0x0fff) ^ crc_tbl[((crc ^ c) & 15)];
		}
		return ~crc & 0xffff;
	}

	/*
	 * ! \relates QByteArray
	 * 
	 * Returns the CRC-16 checksum of the first len bytes of data.
	 * 
	 * The checksum is independent of the byte order (endianness).
	 * 
	 * \note This function is a 16-bit cache conserving (16 entry table)
	 * implementation of the CRC-16-CCITT algorithm.
	 */
	/**
	 * 
	 * @param data
	 * @param index	开始校验的位置
	 * @param length	校验数据的长度
	 * @return int
	 */
	public static int qChecksum(byte[] data, int index, int length) {
		int crc = 0xffff;
		byte c;
		byte[] p = new byte[length];
		System.arraycopy(data, index, p, 0, length);
		for (int i = 0; i < length; i++) {
			c = p[i];
			crc = ((crc >> 4) & 0x0fff) ^ crc_tbl[((crc ^ c) & 15)];
			c >>= 4;
			crc = ((crc >> 4) & 0x0fff) ^ crc_tbl[((crc ^ c) & 15)];
		}
		return ~crc & 0xffff;
	}

	//
	// static const quint16 crc_tbl[16] = {
	// 0x0000, 0x1081, 0x2102, 0x3183,
	// 0x4204, 0x5285, 0x6306, 0x7387,
	// 0x8408, 0x9489, 0xa50a, 0xb58b,
	// 0xc60c, 0xd68d, 0xe70e, 0xf78f
	// };
	//
	// /*!
	// \relates QByteArray
	//
	// Returns the CRC-16 checksum of the first \a len bytes of \a data.
	//
	// The checksum is independent of the byte order (endianness).
	//
	// \note This function is a 16-bit cache conserving (16 entry table)
	// implementation of the CRC-16-CCITT algorithm.
	// */
	//
	// quint16 qChecksum(const char *data, uint len)
	// {
	// register quint16 crc = 0xffff;
	// uchar c;
	// const uchar *p = reinterpret_cast<const uchar *>(data);
	// while (len--) {
	// c = *p++;
	// crc = ((crc >> 4) & 0x0fff) ^ crc_tbl[((crc ^ c) & 15)];
	// c >>= 4;
	// crc = ((crc >> 4) & 0x0fff) ^ crc_tbl[((crc ^ c) & 15)];
	// }
	// return ~crc & 0xffff;
	// }

}
