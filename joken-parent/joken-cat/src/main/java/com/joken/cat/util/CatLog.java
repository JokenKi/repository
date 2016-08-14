package com.joken.cat.util;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;

/**
 * cat日志类
 * @Auther Hanzibin
 * @date 11:39:31 AM,Mar 29, 2016
 */
public class CatLog {
	
	private CatLog(){
	}
	
	/**
	 * 记录事件
	 * @Auther Hanzibin
	 * @date 11:39:41 AM,Mar 29, 2016
	 * @param type 类型
	 * @param name 名称
	 * @param event 事件类型
	 * @param nameValuePairs 信息
	 */
	public static void logEvent(final String type, final String name, final String status, final String nameValuePairs){
		new Thread() {
            public void run() {
            	Cat.logEvent(type, name, status, nameValuePairs);
            }
		 }.start();
	}
	
	
	/**
	 * 记录事件
	 * @Auther Hanzibin
	 * @date 11:43:10 AM,Mar 29, 2016
	 * @param type 类型
	 * @param name 名称
	 * @param nameValuePairs 信息
	 */
	public static void logEvent(String type, String name, String nameValuePairs){
		logEvent(type, name, Event.SUCCESS, nameValuePairs);
	}
	
}
