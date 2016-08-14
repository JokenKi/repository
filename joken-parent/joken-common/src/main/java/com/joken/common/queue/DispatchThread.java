package com.joken.common.queue;

import com.joken.common.utils.StringUtils;

/**
 * 
 * 调度线程类
 * 
 * @version V1.0.0, 2016-3-15
 * @author 欧阳增高
 * @since V1.0.0
 */
public class DispatchThread extends Thread {

	/**
	 * 运行状态
	 */
	private boolean blnFlag;
	/**
	 * 线程ID
	 */
	private int threadID;
	/**
	 * 调度监听器
	 */
	private DispatchListener _listener;
	/**
	 * 线程依附对象
	 */
	private Object attachment;

	/**
	 * 获取线程依附对象
	 * 
	 * @return Object
	 */
	public Object getAttachment() {
		return attachment;
	}

	/**
	 * 设置线程依附对象
	 * 
	 * @param attachment
	 *            依附对象
	 */
	public void setAttachment(Object attachment) {
		this.attachment = attachment;
	}

	/**
	 * 构造
	 * 
	 * @param threadId
	 *            线程ID
	 * @param desc
	 *            线程描述
	 */
	public DispatchThread(int threadId, String desc) {
		this.threadID = threadId;
		blnFlag = true;
		StringBuilder sb = new StringBuilder(desc);
		sb.append("-").append(StringUtils.leftPad(String.valueOf(threadId), 2));
		setName(sb.toString());
	}

	public void run() {
		while (blnFlag)
			if (_listener != null)
				_listener.process(threadID);
			else
				try {
					sleep(1000L);
				} catch (Exception exception) {
				}
	}

	/**
	 * 停止线程执行
	 */
	public void stopThread() {
		blnFlag = false;
		interrupt();
	}

	/**
	 * 注册调度监听器
	 * 
	 * @param listener
	 *            调度监听对象
	 */
	public void registerListener(DispatchListener listener) {
		_listener = listener;
	}
}
