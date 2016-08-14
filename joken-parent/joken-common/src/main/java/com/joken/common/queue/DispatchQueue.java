package com.joken.common.queue;

import java.util.Hashtable;
import java.util.Iterator;

/**
 * 
 * 队列调度实现类
 * 
 * @version V1.0.0, 2016-3-15
 * @author 欧阳增高
 * @since V1.0.0
 */
public class DispatchQueue extends LinkListQueue implements DispatchListener {

	/**
	 * 调度池
	 */
	private Hashtable<Object, Object> hThreads;
	/**
	 * 队列调度处理对象
	 */
	private DispatchQueueProcessor processor;

	/**
	 * 构造
	 * 
	 * @param processor
	 *            调度器对象
	 * @param num
	 *            线程数量
	 * @param desc
	 *            说明
	 */
	public DispatchQueue(DispatchQueueProcessor processor, int num, String desc) {
		this.processor = processor;
		hThreads = new Hashtable<Object, Object>();
		for (int i = 0; i < num; i++) {
			DispatchThread dt = new DispatchThread(i, desc);
			dt.registerListener(this);
			dt.start();
			hThreads.put((new StringBuilder(String.valueOf(i))).toString(), dt);
		}

	}

	public void process(int threadID) {
		try {
			Object obj = take();
			if (obj != null)
				if (processor == null) {
					offer(obj);
					Thread.yield();
				} else {
					processor.ProcessQueueElement(this, obj, threadID);
				}
		} catch (Exception exception) {
		}
	}

	/**
	 * 获取指定id的处理线程
	 * 
	 * @param id
	 *            处理线程的id
	 * @return Object
	 */
	public Object getThreadAttach(String id) {
		return ((DispatchThread) hThreads.get(id)).getAttachment();
	}

	/**
	 * put指定id的
	 * 
	 * @param id
	 *            处理线程的id
	 * @param attach
	 *            附件对象
	 */
	public void putThreadAttach(String id, Object attach) {
		DispatchThread dt = (DispatchThread) hThreads.get(id);
		if (dt != null)
			dt.setAttachment(attach);
	}

	/**
	 * 清除释放调度队列
	 */
	public void dispose() {
		clear();
		for (Iterator<Object> iterator = hThreads.keySet().iterator(); iterator
				.hasNext();) {
			Object key = iterator.next();
			DispatchThread dt = (DispatchThread) hThreads.get(key);
			dt.stopThread();
			dt = null;
		}

		hThreads.clear();
		hThreads = null;
	}
}
