package com.joken.quartz;

import com.joken.quartz.context.SpringSocketContext;

/**
 * 定时任务启动类
 * 
 * @version V1.0.0, 2015-11-9
 * @author 欧阳增高
 * @since V1.0.0
 */
public class MainQuartz {
	/**
	 * 应用启动入口方法
	 * 
	 * @param args
	 *            参数集合
	 * @author 欧阳增高
	 * @date 2016-3-15 上午10:35:14
	 */
	public static void main(String[] args) {
		SpringSocketContext.getInstance();
	}
}
