package com.joken.common.queue;

/**
 * 
 * 调度监听接口类
 * 
 * @version V1.0.0, 2016-3-15
 * @author 欧阳增高
 * @since V1.0.0
 */
public interface DispatchListener {

	/**
	 * 调度处理接口
	 * 
	 * @param index
	 *            位置
	 */
	abstract void process(int index);
}
