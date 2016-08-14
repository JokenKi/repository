package com.joken.common.queue;

/**
 * 
 * 队列调度处理器接口
 * 
 * @version V1.0.0, 2016-3-15
 * @author 欧阳增高
 * @since V1.0.0
 */
public interface DispatchQueueProcessor {

	/**
	 * 队列处理
	 * 
	 * @param linklistqueue
	 *            队列
	 * @param obj
	 *            业务对象
	 * @param threadId
	 *            线程id
	 */
	void ProcessQueueElement(LinkListQueue linklistqueue, Object obj,
			int threadId);
}
