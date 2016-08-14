/**
 *
 */
package com.joken.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 内容加密工具类
 * 
 * @author 欧阳增高
 * @date 2012-10-26 上午01:48:12
 */
public class EncryptUtils {
	/**
	 * 
	 * 加密配置枚举类
	 * 
	 * @version V1.0.0, 2016-3-15
	 * @author 欧阳增高
	 * @since V1.0.0
	 */
	enum EncryptConfig {
		/**
		 * 字符编码
		 */
		SYSTEM_ENCODING("UTF-8"),

		/**
		 * MD5
		 */
		SYSTEM_SECURITY_ENCRYPT_MD5("MD5"),

		/**
		 * SHA-1
		 */
		SYSTEM_SECURITY_ENCRYPT_SHA1("SHA-1"),

		/**
		 * DESede
		 */
		SYSTEM_SECURITY_ENCRYPT_3DES("DESede"),

		/**
		 * 3DES加密私钥
		 */
		SYSTEM_SECURITY_ENCRYPT_3DES_KEYPWD("a!s@g#a$r%d^g&a*m*e(");

		private final String value;

		/**
		 * 获取枚举值
		 * 
		 * @return String
		 */
		@Override
		public String toString() {
			return value.toString();
		}

		/**
		 * 枚举构造
		 * 
		 * @param value
		 *            枚举值
		 */
		EncryptConfig(String value) {
			this.value = value;
		}
	}

	/**
	 * 十六进制字符集合
	 */
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	/**
	 * 私有构造函数
	 */
	private EncryptUtils() {
	}

	/**
	 * MD5数据加密
	 * 
	 * @param origin
	 *            需要加密的字符串
	 * @return String
	 */
	public static String MD5Encode(String origin) {
		try {
			// 设置MD5加密算法，MD5、SHA-1
			MessageDigest md = MessageDigest
					.getInstance(EncryptConfig.SYSTEM_SECURITY_ENCRYPT_MD5
							.toString());
			// 设置编码
			byte[] b = md.digest(origin.getBytes(EncryptConfig.SYSTEM_ENCODING
					.toString()));
			return byteArrayToString(b);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 3DES数据加密
	 * 
	 * @param origin
	 *            需要加密的字符串
	 * @return String
	 */
	public static String DESEncode(String origin) {
		return byteArrayToString(doFianal(Cipher.ENCRYPT_MODE, origin,
				EncryptConfig.SYSTEM_SECURITY_ENCRYPT_3DES_KEYPWD.toString()));
	}

	/**
	 * 3DES数据加密
	 * 
	 * @param origin
	 *            需要加密的字符串
	 * @param prvSourceKey
	 *            加密私钥
	 * @return String
	 */
	public static String DESEncode(String origin, String prvSourceKey) {
		// 对私钥进行加密
		String sourceKey = DESEncode(prvSourceKey);
		// 对数据进行加密
		return byteArrayToString(doFianal(Cipher.ENCRYPT_MODE, origin,
				sourceKey));
	}

	/**
	 * 3DES数据解密
	 * 
	 * @param origin
	 *            需要解密的字符
	 * @return String
	 */
	public static String DESDecode(String origin) {
		return new String(doFianal(Cipher.DECRYPT_MODE, origin,
				EncryptConfig.SYSTEM_SECURITY_ENCRYPT_3DES_KEYPWD.toString()));
	}

	/**
	 * 3DES数据解密
	 * 
	 * @param origin
	 *            需要解密的字符
	 * @param prvSourceKey
	 *            私钥
	 * @return String
	 */
	public static String DESDecode(String origin, String prvSourceKey) {
		// 对私钥进行加密
		String sourceKey = DESEncode(prvSourceKey);
		return new String(doFianal(Cipher.DECRYPT_MODE, origin, sourceKey));
	}

	public static String SHA1(String decript) {
		MessageDigest digest;
		try {
			digest = java.security.MessageDigest
					.getInstance(EncryptConfig.SYSTEM_SECURITY_ENCRYPT_SHA1
							.toString());
			digest.update(decript.getBytes());
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 按指定密钥对数据进行加密解密
	 * 
	 * @param cipherMode
	 *            加/解模式
	 * @param origin
	 *            加/解密数据
	 * @param secureKey
	 *            私钥
	 * @return byte[]
	 */
	private static byte[] doFianal(int cipherMode, String origin,
			String secureKey) {
		try {
			// 设置3DES加密算法，DESede/CBC/PKCS5Padding
			String des = EncryptConfig.SYSTEM_SECURITY_ENCRYPT_3DES.toString();
			// 使用DESede算法获得密钥生成器
			KeyGenerator kgen = KeyGenerator.getInstance(des);
			// 初始化密钥生成器，设置密钥的长度为168长度
			kgen.init(168, new SecureRandom(secureKey.getBytes()));

			// 创建密钥
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, des);

			Cipher cipher = Cipher.getInstance(des);// 创建密码器
			cipher.init(cipherMode, key);// 初始化

			// 获取数据的byte数据
			byte[] byteContent = origin.getBytes(EncryptConfig.SYSTEM_ENCODING
					.toString());
			if (cipherMode == Cipher.DECRYPT_MODE) {
				byteContent = strToByteArray(origin);
			}

			return cipher.doFinal(byteContent);
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 将byte[]转化成16进制字符串
	 * 
	 * @param buf
	 *            需要转换的byte数组
	 * @return String
	 */
	private static String byteArrayToString(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (byte element : buf) {
			sb.append(byteToString(element));
		}
		return sb.toString();
	}

	/**
	 * 将byte转化成16进制字符串
	 * 
	 * @param b
	 *            需要转换的byte值
	 * @return String
	 */
	private static String byteToString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * 将16进制字符串转化成byte[]
	 * 
	 * @param buf
	 *            需要转换的16进制字符串
	 * @return byte[]
	 */
	private static byte[] strToByteArray(String str) {
		int len = str.length();
		if (len < 1) {
			return null;
		}
		int lenResidue = len / 2;
		byte[] result = new byte[lenResidue];
		for (int i = 0; i < lenResidue; i++) {
			int start = i * 2;
			int high = Integer.parseInt(str.substring(start, start + 1), 16);
			int low = Integer.parseInt(str.substring(start + 1, start + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	// public static void main(String[] args) {
	// System.out.println(Calendar.getInstance().getTimeInMillis());
	// System.out.println(DESEncode("1427357852021inkcar123456"));
	// System.out
	// .println(DESDecode("c7753265a83c7910ed40ab3c6365305def04c669563d104a8aed0c4cb74b0f4b"));
	// System.out.println(MD5Encode("8545123456"));
	// }
}
