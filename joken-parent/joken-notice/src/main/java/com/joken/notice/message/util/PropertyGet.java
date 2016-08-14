/*
 * @(#)PropertyGetter.java	V0.0.1 2015-4-22, 下午5:05:20
 *
 * Copyright 2015 www.ifood517.com. All rights reserved.
 * www.ifood517.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.joken.notice.message.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * <p>。
 * @version V0.0.1, 2015-4-22
 * @author  李付华(Frank Li)，Email：fuhua_lee@163.com，QQ：746611085。
 * @since   V0.0.1
 */
public class PropertyGet {

	
	/**
	 * 获取message.properties文件中的配置信息
	 * @Auther Hanzibin
	 * @date 3:18:48 PM,Mar 11, 2016
	 * @param key 配置文件key
	 * @return String key对应的值
	 */
	public static String getApplicationProperty(String key){
		String value = "";
		Properties prop = new Properties();
		InputStream in = null;
		try {
			in = PropertyGet.class.getClassLoader().getResourceAsStream("properties/message_default.properties");
			prop.load(in);
			value = prop.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeIO(in);
		}
		return value;
	}
	
	/**
	 * 根据文件,key获取配置文件内容
	 * @author Hanzibin
	 * @date 3:18:48 PM,Mar 11, 2016
	 * @param filePath 配置文件地址
	 * @param key 配置文件的key
	 * @return String key对应的值
	 */
	public static String getApplicationProperty(String filePath, String key){
		String value = "";
		Properties prop = new Properties();
		InputStream in = null;
		try {
			in = PropertyGet.class.getClassLoader().getResourceAsStream(filePath);
			prop.load(in);
			value = prop.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeIO(in);
		}
		return value;
	}
	
	/**
	 * 根据文件路径获取配置文件全部内容
	 * @Auther Hanzibin
	 * @date 3:18:48 PM,Mar 11, 2016
	 * @param filePath 配置文件地址
	 * @return Map 配置文件所有信息的map
	 */
	public static Map<String, String> getApplicationAllProperty(String filePath){
		Properties prop = new Properties();
		InputStream in = null;
		Map<String, String> map = new HashMap<String, String>();
		try {
			in = PropertyGet.class.getClassLoader().getResourceAsStream(filePath);
			prop.load(in);
			Enumeration<?> en = prop.propertyNames(); //得到配置文件的名字
			while(en.hasMoreElements()) {
				String strKey = (String) en.nextElement();
				String strValue = prop.getProperty(strKey);
				map.put(strKey, strValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return map;
		}finally{
			closeIO(in);
		}
		return map;
	}
	
	/**
	 * 关闭io流
	 * @Auther Hanzibin
	 * @date 3:18:48 PM,Mar 11, 2016
	 * @param io
	 */
	public static void closeIO(Closeable io){
		if(io != null){
			try {
				io.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
