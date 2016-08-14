package com.joken.common.logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.joken.common.logger.helper.FormattingTuple;
import com.joken.common.logger.helper.MessageFormatter;
import com.joken.common.queue.DispatchQueue;
import com.joken.common.queue.DispatchQueueProcessor;
import com.joken.common.queue.LinkListQueue;

/**
 * 
 * 日志输出抽象类
 * 
 * @version V1.0.0, 2016-3-15
 * @author 欧阳增高
 * @since V1.0.0
 */
public abstract class LoggerImpl implements Logger {
	/**
	 * 
	 * 队列调度处理实现类
	 * 
	 * @version V1.0.0, 2016-3-15
	 * @author 欧阳增高
	 * @since V1.0.0
	 */
	class MyProcessImpl implements DispatchQueueProcessor {

		/**
		 * 日志对象
		 */
		final LoggerImpl this$0;

		/**
		 * 要素队列处理
		 */
		public void ProcessQueueElement(LinkListQueue pQueue, Object o,
				int threadID) {
			process((String) o);
		}

		/**
		 * 构造
		 */
		MyProcessImpl() {
			super();
			this$0 = LoggerImpl.this;
		}
	}

	private static SimpleDateFormat formater = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.SSS");
	/**
	 * 日志等级
	 */
	private static final String TRACE = "TRACE";
	private static final String DEBUG = "DEBUG";
	private static final String INFO = "INFO";
	private static final String WARN = "WARN";
	private static final String ERROR = "ERROR";
	/**
	 * 日志等级对应索引值
	 */
	private static final int I_TRACE = 0;
	private static final int I_DEBUG = 1;
	private static final int I_INFO = 2;
	private static final int I_WARN = 3;
	private static final int I_ERROR = 4;
	/**
	 * 日志定义名称
	 */
	protected String logName;
	/**
	 * 日志定义等级
	 */
	private int logLevel;
	/**
	 * 处理队列
	 */
	private DispatchQueue pQueue;

	/**
	 * 构造
	 * 
	 * @param name
	 *            日志定义名称
	 */
	public LoggerImpl(String name) {
		logName = name;
		pQueue = new DispatchQueue(new MyProcessImpl(), 1, name);
	}

	/**
	 * 设置日志等级
	 * 
	 * @param level
	 *            需要设置的等级
	 * @author 欧阳增高
	 * @date 2016-3-15 上午11:47:35
	 */
	public void setLogLevel(String level) {
		logLevel = 2;
		if (TRACE.equalsIgnoreCase(level))
			logLevel = I_TRACE;
		if (DEBUG.equalsIgnoreCase(level))
			logLevel = I_DEBUG;
		if (INFO.equalsIgnoreCase(level))
			logLevel = I_INFO;
		if (WARN.equalsIgnoreCase(level))
			logLevel = I_WARN;
		if (ERROR.equalsIgnoreCase(level))
			logLevel = I_ERROR;
	}

	public String getName() {
		return logName;
	}

	public boolean isDebugEnabled() {
		return logLevel <= 1;
	}

	public boolean isErrorEnabled() {
		return logLevel <= 4;
	}

	public boolean isInfoEnabled() {
		return logLevel <= 2;
	}

	public boolean isTraceEnabled() {
		return logLevel <= 0;
	}

	public boolean isWarnEnabled() {
		return logLevel <= 3;
	}

	public void trace(String msg) {
		if (isTraceEnabled())
			add_log_info(msg, "TRACE");
	}

	public void trace(String format, Object arg) {
		if (isTraceEnabled()) {
			FormattingTuple ft = MessageFormatter.format(format, arg);
			add_log_info(ft.getMessage(), ft.getThrowable(), TRACE);
		}
	}

	public void trace(String msg, Throwable t) {
		if (isTraceEnabled())
			add_log_info(msg, t, TRACE);
	}

	public void debug(String msg) {
		if (isDebugEnabled())
			add_log_info(msg, DEBUG);
	}

	public void debug(String format, Object arg) {
		if (isDebugEnabled()) {
			FormattingTuple ft = MessageFormatter.format(format, arg);
			add_log_info(ft.getMessage(), ft.getThrowable(), DEBUG);
		}
	}

	public void debug(String msg, Throwable t) {
		if (isDebugEnabled())
			add_log_info(msg, t, DEBUG);
	}

	public void info(String msg) {
		if (isInfoEnabled())
			add_log_info(msg, INFO);
	}

	public void info(String format, Object arg) {
		if (isInfoEnabled()) {
			FormattingTuple ft = MessageFormatter.format(format, arg);
			add_log_info(ft.getMessage(), ft.getThrowable(), INFO);
		}
	}

	public void info(String msg, Throwable t) {
		if (isInfoEnabled())
			add_log_info(msg, t, INFO);
	}

	public void warn(String msg) {
		if (isWarnEnabled())
			add_log_info(msg, WARN);
	}

	public void warn(String format, Object arg) {
		if (isWarnEnabled()) {
			FormattingTuple ft = MessageFormatter.format(format, arg);
			add_log_info(ft.getMessage(), ft.getThrowable(), WARN);
		}
	}

	public void warn(String msg, Throwable t) {
		if (isWarnEnabled())
			add_log_info(msg, t, WARN);
	}

	public void error(String msg) {
		if (isErrorEnabled())
			add_log_info(msg, ERROR);
	}

	public void error(String format, Object arg) {
		if (isErrorEnabled()) {
			FormattingTuple ft = MessageFormatter.format(format, arg);
			add_log_info(ft.getMessage(), ft.getThrowable(), ERROR);
		}
	}

	public void error(String msg, Throwable t) {
		if (isErrorEnabled())
			add_log_info(msg, t, ERROR);
	}

	/**
	 * 添加日志
	 * 
	 * @param msg
	 *            日志内容
	 * @param levles
	 *            等级
	 */
	private void add_log_info(String msg, String levles) {
		StringBuffer sb = new StringBuffer();
		sb.append("[").append(formater.format(new Date()));
		sb.append("] [").append(Thread.currentThread().getName());
		sb.append("] ").append(levles).append(" - ").append(msg);
		pQueue.offer(new String(sb));
	}

	/**
	 * 添加日志
	 * 
	 * @param msg
	 *            日志内容
	 * @param throwable
	 *            异常对象
	 * @param levles
	 *            日志等级
	 */
	private void add_log_info(String msg, Throwable throwable, String levles) {
		StringBuffer sb = new StringBuffer();
		sb.append("[").append(formater.format(new Date()));
		sb.append("] [").append(Thread.currentThread().getName());
		sb.append("] ").append(levles).append(" - ").append(msg);
		if (throwable != null) {
			StringWriter sw = new StringWriter();
			throwable.printStackTrace(new PrintWriter(sw));
			sb.append("\r\n").append(sw.toString());
		}
		pQueue.offer(new String(sb));
	}

	/**
	 * 日志输出处理
	 * 
	 * @param msg
	 *            需要处理的日志内容
	 */
	protected abstract void process(String msg);

}
