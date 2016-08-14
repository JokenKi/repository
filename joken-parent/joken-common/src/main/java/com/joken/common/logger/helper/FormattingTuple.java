package com.joken.common.logger.helper;

/**
 * 
 * 日志格式元件
 * 
 * @version V1.0.0, 2016-3-15
 * @author 欧阳增高
 * @since V1.0.0
 */
public class FormattingTuple {
	/**
	 * Null元件
	 */
	public static FormattingTuple NULL = new FormattingTuple(null);
	/**
	 * 日志内容
	 */
	private String message;
	/**
	 * 异常
	 */
	private Throwable throwable;
	/**
	 * 异常业务数据
	 */
	private Object argArray[];

	/**
	 * 元件构造
	 * 
	 * @param message
	 *            日志内容
	 */
	public FormattingTuple(String message) {
		this(message, null, null);
	}

	/**
	 * 元件构造
	 * 
	 * @param message
	 *            日志内容
	 * @param argArray
	 *            异常业务数据
	 * @param throwable
	 *            异常对象
	 */
	public FormattingTuple(String message, Object argArray[],
			Throwable throwable) {
		this.message = message;
		this.throwable = throwable;
		if (throwable == null)
			this.argArray = argArray;
		else
			this.argArray = trimmedCopy(argArray);
	}

	/**
	 * 复制业务数据
	 * 
	 * @param argArray
	 *            传入的业务数据
	 * @return Object[]
	 */
	static Object[] trimmedCopy(Object argArray[]) {
		if (argArray == null || argArray.length == 0) {
			throw new IllegalStateException(
					"non-sensical empty or null argument array");
		} else {
			int trimemdLen = argArray.length - 1;
			Object trimmed[] = new Object[trimemdLen];
			System.arraycopy(((Object) (argArray)), 0, ((Object) (trimmed)), 0,
					trimemdLen);
			return trimmed;
		}
	}

	/**
	 * 获取日志内容
	 * 
	 * @return 日志内容
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 获取业务数据数组
	 * 
	 * @return 业务数据数组
	 */
	public Object[] getArgArray() {
		return argArray;
	}

	/**
	 * 获取抛出异常对象
	 * 
	 * @return 异常对象
	 */
	public Throwable getThrowable() {
		return throwable;
	}

}
