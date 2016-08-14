/**
 * 
 */
package com.joken.common.utils;

import java.io.IOException;
import java.io.InputStream;

import com.joken.common.properties.SystemProperties;

/**
 * 转码工具类
 * 
 * @author WangBy 2015-8-22 QQ:496710437 mail:496710437@qq.com
 */

public class CodeConvertUtils {

	private CodeConvertUtils() {
	}

	/**
	 * 将16进制字符串转为可见字符
	 * 
	 * @param s
	 *            需要转换的字符串
	 * @return String
	 */
	public static String hexString2String(String s) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(
						s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			s = new String(baKeyword, "gb2312");// gb231:国际通用编码
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}

	/**
	 * 将二进制转为ASCII码
	 * 
	 * @param bytes
	 *            需要转换的byte数组
	 * @return String
	 */
	public static String byte2Ausii(byte[] bytes) {
		byte[] messages = new byte[bytes.length];
		System.arraycopy(bytes, 0, messages, 0, bytes.length);
		StringBuffer tStringBuf = new StringBuffer();
		char[] tChars = new char[messages.length];
		for (int i = 0; i < messages.length - 1; i++) {
			tChars[i] = (char) messages[i];
		}
		tStringBuf.append(tChars);
		return tStringBuf.toString();
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
	 * 将输入流转换为byte数组值
	 * 
	 * @param is
	 *            需要转换的数据流
	 * @param contentLen
	 *            内容长度
	 * @return byte[]
	 * @author 欧阳增高
	 * @date 2015-10-8 下午4:28:24
	 */
	public static final byte[] readBytes(InputStream is, int contentLen) {
		if (contentLen > 0) {
			int readLen = 0;

			int readLengthThisTime = 0;

			byte[] message = new byte[contentLen];

			try {

				while (readLen != contentLen) {

					readLengthThisTime = is.read(message, readLen, contentLen
							- readLen);

					if (readLengthThisTime == -1) {
						break;
					}

					readLen += readLengthThisTime;
				}

				return message;
			} catch (IOException e) {
			}
		}

		return new byte[] {};
	}

	/**
	 * 将输入流转换为byte数组值
	 * 
	 * @param is
	 *            需要转换的数据流
	 * @param contentLen
	 *            内容长度
	 * @return byte[]
	 * @author 欧阳增高
	 * @date 2015-10-8 下午4:28:24
	 */
	public static final String readString(InputStream is, int contentLen) {
		if (contentLen > 0) {
			int readLen = 0;

			int readLengthThisTime = 0;

			byte[] message = new byte[contentLen];

			try {

				while (readLen != contentLen) {

					readLengthThisTime = is.read(message, readLen, contentLen
							- readLen);

					if (readLengthThisTime == -1) {
						break;
					}

					readLen += readLengthThisTime;
				}

				return new String(message, SystemProperties.getEncoding());
			} catch (IOException e) {
			}
		}
		return StringUtils.EMPTY;
	}

	public static String decodeUnicode(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
									"Malformed   \\uxxxx   encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);
		}
		return outBuffer.toString();
	}
}
