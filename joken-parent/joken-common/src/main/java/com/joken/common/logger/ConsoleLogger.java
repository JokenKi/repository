package com.joken.common.logger;

import java.io.PrintWriter;

/**
 * 
 * 控制台输出日志实现类
 * 
 * @version V1.0.0, 2016-3-15
 * @author 欧阳增高
 * @since V1.0.0
 */
public class ConsoleLogger extends LoggerImpl {

	/**
	 * 控制台输出对象
	 */
	private PrintWriter out;

	/**
	 * 构造
	 * 
	 * @param name
	 *            日志定义名称
	 */
	public ConsoleLogger(String name) {
		super(name);
		out = new PrintWriter(System.out);
	}

	protected void process(String o) {
		out.println(o);
		out.flush();
	}
}
